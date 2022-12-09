package com.user.service;

import org.springframework.http.ResponseEntity;

import com.user.payload.request.LoginRequest;
import com.user.payload.request.SignupRequest;

public class UserServiceMockImpl implements UserServiceMock{

	@Override
	public ResponseEntity<?> registerUser(SignupRequest signUpRequest) {
		return null;
	}

	@Override
	public ResponseEntity<?> authenticateUser(LoginRequest loginRequest) {
		return null;
	}

	@Override
	public Object findById(long anyLong) {
		// TODO Auto-generated method stub
		return null;
	}

}
