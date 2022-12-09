package com.user.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.user.payload.request.LoginRequest;
import com.user.payload.request.SignupRequest;

@Service
public interface UserServiceMock {
	ResponseEntity<?> registerUser(SignupRequest signUpRequest);
	
	ResponseEntity<?> authenticateUser(LoginRequest loginRequest);

	Object findById(long anyLong);
}
