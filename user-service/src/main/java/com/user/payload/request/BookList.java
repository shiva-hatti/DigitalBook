package com.user.payload.request;

import java.util.List;

import lombok.Data;

@Data
public class BookList {
	private List<BookRequest> bookList;
}
