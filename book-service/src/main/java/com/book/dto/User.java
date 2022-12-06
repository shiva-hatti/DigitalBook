package com.book.dto;

import java.util.HashSet;
import java.util.Set;

import com.book.model.Role;

import lombok.Data;

@Data
public class User {
	private Long id;
	private String username;
	private String email;
	private String password;
	private Set<Role> roles = new HashSet<>();
}
