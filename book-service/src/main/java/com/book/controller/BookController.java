package com.book.controller;

import java.util.Date;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.book.dto.BookRequest;
import com.book.dto.MessageResponse;
import com.book.dto.User;
import com.book.model.Book;
import com.book.model.ERole;
import com.book.repository.BookRepository;
import com.book.service.BookService;


@RestController
@RequestMapping("/book")
public class BookController {

	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private BookService bookService;
	
	@PostMapping("/createBook/{authorId}")
	public ResponseEntity<?> createBook(@PathVariable Long authorId,@Valid @RequestBody BookRequest bookRequest) {
		if (bookRepository.existsBycode(bookRequest.getCode())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Book Code is already Used!",false));
		}
		User user = bookService.getUserById(authorId);
		if (!user.getRoles().stream().anyMatch(r->r.getName().equals(ERole.AUTHOR))) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Author only creates the Book!",false));
		}
		Book book = new Book(bookRequest.getTitle(), bookRequest.getCode(), user.getUsername(), bookRequest.getPrice(), bookRequest.getCategory(), bookRequest.getPublisher(), true, new Date(), new Date(), bookRequest.getLogopath(), bookRequest.getCotent());
		bookRepository.save(book);
		return ResponseEntity.ok(new MessageResponse("Book Created Successfully!",true));
	}
	
	@PutMapping("/author/{authorId}/books/{bookId}")
	public ResponseEntity<?> editBook(@PathVariable Long authorId,@PathVariable Long bookId,@Valid @RequestBody BookRequest bookRequest) {
		User user = bookService.getUserById(authorId);
		if (!user.getRoles().stream().anyMatch(r->r.getName().equals(ERole.AUTHOR))) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Author only edit the Book!",false));
		}
		Book book = bookRepository.findByIdAndAuthorname(bookId,user.getUsername());
		if(Objects.isNull(book)) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Book Not Found!",false));
		}
		if(!book.getCode().equals(bookRequest.getCode())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Invalid Book Code!",false));
		}
		Book bookObj = new Book(bookRequest.getTitle(), bookRequest.getCode(), user.getUsername(), bookRequest.getPrice(), bookRequest.getCategory(), bookRequest.getPublisher(), bookRequest.isActive(), new Date(), new Date(), bookRequest.getLogopath(), bookRequest.getCotent());
		bookObj.setId(book.getId());
		bookRepository.save(bookObj);
		return ResponseEntity.ok(new MessageResponse("Book Updated Successfully!",true));
	}
	
	@PostMapping("/author/{authorId}/books/{bookId}/block")
	public ResponseEntity<?> blockAndUnBlockBook(@PathVariable Long authorId,@PathVariable Long bookId,@RequestParam String block) {
		boolean blockStatus = true;
		User user = bookService.getUserById(authorId);
		Book book = bookRepository.findByIdAndAuthorname(bookId,user.getUsername());
		if(Objects.isNull(book)) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Book Not Found!",false));
		}
		if(block.equalsIgnoreCase("yes")) 
			blockStatus = false;
		if(book.isActive() == blockStatus) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Book is already blocked!",false));
		}
		book.setActive(blockStatus);
		bookRepository.save(book);
		if(!blockStatus) {
			return ResponseEntity.ok(new MessageResponse("Book Blocked!",true));
		} else {
			return ResponseEntity.ok(new MessageResponse("Book Un-Blocked!",true));
		}
	}
}
