package com.user.payload.request;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class BookRequest {
	
	private Long bookId;
	
	@NotBlank
	@Size(min = 3,max = 20)
	private String title;

	@NotBlank
	@Size(max = 3)
	private String code;

	private Long authorid;

	@NotNull
	@Positive(message = "Only Positive Values")
	private int price;

	@NotBlank
	@Size(max = 20)
	private String category;

	@NotBlank
	@Size(max = 20)
	private String publisher;

	@NotNull
	private Date publisheddate;

	@NotBlank
	private String logopath;
	
	@NotBlank
	private String cotent;
	
	private boolean isactive;

}
