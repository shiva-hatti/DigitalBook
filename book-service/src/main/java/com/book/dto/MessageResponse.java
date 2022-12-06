package com.book.dto;

import lombok.Data;

@Data
public class MessageResponse {
	private String message;
	private Boolean status;

	public MessageResponse(String message, Boolean status) {
		this.message = message;
		this.status = status;
	}
}
