package com.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.book.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

	boolean existsBycode(String code);

	@Query(value ="from Book where id = :bookId and authorname = :username")
	Book findByIdAndAuthorname(@Param("bookId") Long bookId, @Param("username") String username);

}
