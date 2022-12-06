package com.book.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.book.dto.User;

@Service
public class BookService {
	@Autowired
	RestTemplate restTemplate;

	public User getUserById(Long authorId) {
		return restTemplate.getForObject("http://localhost:8080/api/auth/getUserById/"+authorId,User.class);
	}
}
