package com.user.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.user.models.ERole;
import com.user.models.Role;
import com.user.models.User;
import com.user.payload.request.BookRequest;
import com.user.payload.request.LoginRequest;
import com.user.payload.request.SignupRequest;
import com.user.payload.response.JwtResponse;
import com.user.payload.response.MessageResponse;
import com.user.security.jwt.JwtUtils;
import com.user.security.services.UserDetailsImpl;
import com.user.service.CallBookServices;
import com.user.service.RoleService;
import com.user.service.UserService;
import com.user.util.CommanConstant;
import com.user.util.UserRoutings;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class UserController {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;
	
	@Autowired
	private CallBookServices bookService;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private JwtUtils jwtUtils;

	@PostMapping(value = UserRoutings.SIGN_IN)
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt, 
												 userDetails.getId(), 
												 userDetails.getUsername(), 
												 userDetails.getEmail(), 
												 roles));
	}

	@PostMapping(value = UserRoutings.SIGN_UP)
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userService.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!",false));
		}

		if (userService.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!",false));
		}
		
		User user = new User(signUpRequest.getUsername(), 
							 signUpRequest.getEmail(),
							 encoder.encode(signUpRequest.getPassword()));

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleService.findByName(ERole.AUTHOR);
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "AUTHOR":
					Role adminRole = roleService.findByName(ERole.AUTHOR);
					roles.add(adminRole);

					break;
				default:
					Role userRole = roleService.findByName(ERole.READER);
					roles.add(userRole);
				}
			});
		}
		user.setRoles(roles);
		userService.save(user);
		return ResponseEntity.ok(new MessageResponse("User registered successfully!",true));
	}
	
	@PostMapping(value = UserRoutings.CREATE_BOOK)
	public ResponseEntity<?> createBook(@PathVariable Long authorId,@Valid @RequestBody BookRequest bookRequest) {
		Optional<User> user = userService.findById(authorId);
		if (!user.get().getRoles().stream().anyMatch(r->r.getName().equals(ERole.AUTHOR))) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Author only creates the Book!",false));
		}
		bookRequest.setAuthorid(authorId);
		return ResponseEntity.ok(bookService.bookServiceCall(bookRequest,CommanConstant.CREATE.getName()));
	}
	
	@PutMapping(value = UserRoutings.UPDATE_BOOK)
	public ResponseEntity<?> editBook(@PathVariable Long authorId,@PathVariable Long bookId,@Valid @RequestBody BookRequest bookRequest) {
		Optional<User> user = userService.findById(authorId);
		if (!user.get().getRoles().stream().anyMatch(r->r.getName().equals(ERole.AUTHOR))) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Author only edit the Book!",false));
		}
		bookRequest.setBookId(bookId);
		bookRequest.setAuthorid(authorId);
		return ResponseEntity.ok(bookService.bookServiceCall(bookRequest,CommanConstant.UPDATE.getName()));
	}
	
	@PostMapping(value = UserRoutings.BOOK_BLOCK_UNBLOCK)
	public ResponseEntity<?> blockAndUnBlockBook(@PathVariable Long authorId,@PathVariable Long bookId,@RequestParam String block) {
		return ResponseEntity.ok(bookService.blockUnblockServiceCall(authorId,bookId,block));
	}
	
	@GetMapping("/search")
	public List<BookRequest> searchBook(@RequestParam String category,@RequestParam String title,@RequestParam String author,@RequestParam int price,@RequestParam String publisher) {
		Optional<User> user = userService.findByUsername(author);
		BookRequest bookRequest = new BookRequest();
		bookRequest.setAuthorid(user.get().getId());
		bookRequest.setTitle(title);
		bookRequest.setCategory(category);
		bookRequest.setPrice(price);
		bookRequest.setPublisher(publisher);
		bookService.searchBook(bookRequest);
		return new ArrayList<BookRequest>();
	}
}
