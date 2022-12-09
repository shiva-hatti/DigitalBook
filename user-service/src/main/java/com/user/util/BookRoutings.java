package com.user.util;

import org.springframework.beans.factory.annotation.Value;

public class BookRoutings {
	@Value("${book.port}")
	static String bookServicePort ="8081";
	public static final String CREATE_BOOK = "http://localhost:"+bookServicePort+"/book/createBook";
	public static final String UPDATE_BOOK = "http://localhost:"+bookServicePort+"/book/editBook";
	public static final String BOOK_BLOCK_UNBLOCK = "http://localhost:"+bookServicePort+"/book/author";
	public static final String SEARCH_BOOK = "http://localhost:"+bookServicePort+"/book/searchBook";
}
