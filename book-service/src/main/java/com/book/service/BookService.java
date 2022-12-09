package com.book.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.book.dto.BookRequest;
import com.book.model.Book;
import com.book.repository.BookRepository;

@Service
public class BookService {
	@Autowired
	private BookRepository bookRepository;

	public boolean existsBycode(String code) {
		return bookRepository.existsBycode(code);
	}

	public void save(Book book) {
		bookRepository.save(book);
	}

	public Book findByIdAndAuthorname(Long bookId, Long authorid) {
		return bookRepository.findByIdAndAuthorname(bookId,authorid);
	}

	public List<Book> searchBooks(BookRequest bookRequest) {
		List<Book> bookList =bookRepository.searchBook(bookRequest.getAuthorid(),bookRequest.getCategory(),bookRequest.getPrice(),bookRequest.getPublisher(),bookRequest.getTitle());
		return bookList;
	}
}
