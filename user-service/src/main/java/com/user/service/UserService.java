package com.user.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.models.User;
import com.user.repository.UserRepository;
@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	public boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}

	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	public void save(User user) {
		userRepository.save(user);
	}

	public Optional<User> findById(Long userId) {
		return userRepository.findById(userId);
	}
	
	public Optional<User> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
}
