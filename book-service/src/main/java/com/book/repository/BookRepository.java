package com.book.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.book.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

	boolean existsBycode(String code);

	@Query(value ="from Book where id = :bookId and authorid = :authorid")
	Book findByIdAndAuthorname(@Param("bookId") Long bookId, @Param("authorid") Long authorid);

	@Query(value ="from Book where authorid = :authorid and category = :category and price = :price and publisher = :publisher and title = :title")
	List<Book> searchBook(@Param("authorid") Long authorid,@Param("category") String category,@Param("price") int price,@Param("publisher") String publisher,@Param("title") String title);

}
