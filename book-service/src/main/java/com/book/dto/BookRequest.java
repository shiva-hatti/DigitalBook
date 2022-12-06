package com.book.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class BookRequest {
	@NotBlank
	@Size(min = 3,max = 20)
	private String title;

	@NotBlank
	@Size(max = 3)
	private String code;

	private String authorname;

	@NotNull
	@Positive(message = "Only Positive Values")
	private int price;

	@NotBlank
	@Size(max = 20)
	private String category;

	@NotBlank
	@Size(max = 20)
	private String publisher;

	private boolean isActive;
	
	@NotNull
	private Date publisheddate;

	@NotBlank
	private String logopath;
	
	@NotBlank
	private String cotent;

}
