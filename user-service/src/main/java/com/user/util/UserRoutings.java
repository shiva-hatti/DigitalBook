package com.user.util;

public class UserRoutings {
	public static final String SIGN_IN = "/signin";
	public static final String SIGN_UP = "/signup";
	public static final String CREATE_BOOK = "/createBook/{authorId}";
	public static final String UPDATE_BOOK = "/author/{authorId}/books/{bookId}";
	public static final String BOOK_BLOCK_UNBLOCK = "/author/{authorId}/books/{bookId}/block";
}
