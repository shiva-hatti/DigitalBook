package com.book.controller;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.book.dto.BookRequest;
import com.book.dto.MessageResponse;
import com.book.model.Book;
import com.book.service.BookService;


@RestController
@RequestMapping("/book")
public class BookController {
	
	@Autowired
	private BookService bookService;
	
	@PostMapping("/createBook")
	public ResponseEntity<?> createBook(@Valid @RequestBody BookRequest bookRequest) {
		if (bookService.existsBycode(bookRequest.getCode())) {
			return ResponseEntity.ok(new MessageResponse("Error: Book Code is already Used!",false));
		}
		Book book = new Book(bookRequest.getTitle(), bookRequest.getCode(), bookRequest.getAuthorid(), bookRequest.getPrice(), bookRequest.getCategory(), bookRequest.getPublisher(), true, new Date(), new Date(), bookRequest.getLogopath(), bookRequest.getCotent());
		bookService.save(book);
		return ResponseEntity.ok(new MessageResponse("Book Created Successfully!",true));
	}
	
	@PutMapping("/editBook")
	public ResponseEntity<?> editBook(@Valid @RequestBody BookRequest bookRequest) {
	
		Book book = bookService.findByIdAndAuthorname(bookRequest.getBookId(),bookRequest.getAuthorid());
		if(Objects.isNull(book)) {
			return ResponseEntity.ok(new MessageResponse("Error: Book Not Found!",false));
		}
		if(!book.getCode().equals(bookRequest.getCode())) {
			return ResponseEntity.ok(new MessageResponse("Error: Invalid Book Code!",false));
		}
		Book bookObj = new Book(bookRequest.getTitle(), bookRequest.getCode(), bookRequest.getAuthorid(), bookRequest.getPrice(), bookRequest.getCategory(), bookRequest.getPublisher(), bookRequest.isIsactive(), new Date(), new Date(), bookRequest.getLogopath(), bookRequest.getCotent());
		bookObj.setId(book.getId());
		bookService.save(bookObj);
		return ResponseEntity.ok(new MessageResponse("Book Updated Successfully!",true));
	}
	
	@GetMapping("/author/{authorId}/books/{bookId}/{block}")
	public ResponseEntity<?> blockAndUnBlockBook(@PathVariable Long authorId,@PathVariable Long bookId,@PathVariable String block) {
		boolean blockStatus = true;
		Book book = bookService.findByIdAndAuthorname(bookId,authorId);
		if(Objects.isNull(book)) {
			return ResponseEntity.ok(new MessageResponse("Error: Book Not Found!",false));
		}
		if(block.equalsIgnoreCase("yes")) 
			blockStatus = false;
		if(book.isIsactive() == blockStatus) {
			return ResponseEntity.ok(new MessageResponse("Error: Book is already blocked!",false));
		}
		book.setIsactive(blockStatus);
		bookService.save(book);
		if(!blockStatus) {
			return ResponseEntity.ok(new MessageResponse("Book Blocked!",true));
		} else {
			return ResponseEntity.ok(new MessageResponse("Book Un-Blocked!",true));
		}
	}
	
	@PostMapping("/searchBook")
	public List<BookRequest> searchBook(@RequestBody BookRequest bookRequest) {
		List<Book> bookList = bookService.searchBooks(bookRequest);
		 List<BookRequest> employeRise = bookList.stream()
                 .map(emp -> new BookRequest(emp.getCode(),emp.getTitle()))
                 .collect(Collectors.toList());
		if(CollectionUtils.isEmpty(employeRise)) {
			return employeRise;
		}
		return employeRise;
	}
}
