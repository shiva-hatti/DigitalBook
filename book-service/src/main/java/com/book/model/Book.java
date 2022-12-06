package com.book.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
@Table(name = "book",uniqueConstraints = { 
		@UniqueConstraint(columnNames = "code")
	})
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 20)
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
	private Date updatedon;

	private Date publisheddate;

	@NotBlank
	private String logopath;

	@NotBlank
	private String cotent;

	public Book(String title, String code, String authorname, int price, String category, String publisher,
			boolean isActive, Date updatedon, Date publisheddate, String logopath, String cotent) {
		this.title = title;
		this.code = code;
		this.authorname = authorname;
		this.price = price;
		this.category = category;
		this.publisher = publisher;
		this.isActive = isActive;
		this.updatedon = updatedon;
		this.publisheddate = publisheddate;
		this.logopath = logopath;
		this.cotent = cotent;
	}

	public Book() {
	}

}
