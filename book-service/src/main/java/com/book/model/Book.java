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

	private boolean isactive;

	@NotNull
	private Date updatedon;

	private Date publisheddate;

	@NotBlank
	private String logopath;

	@NotBlank
	private String cotent;

	public Book(String title, String code, Long authorid, int price, String category, String publisher,
			boolean isactive, Date updatedon, Date publisheddate, String logopath, String cotent) {
		this.title = title;
		this.code = code;
		this.authorid = authorid;
		this.price = price;
		this.category = category;
		this.publisher = publisher;
		this.isactive = isactive;
		this.updatedon = updatedon;
		this.publisheddate = publisheddate;
		this.logopath = logopath;
		this.cotent = cotent;
	}

	public Book() {
	}

}
