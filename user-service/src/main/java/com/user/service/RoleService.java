package com.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.models.ERole;
import com.user.models.Role;
import com.user.repository.RoleRepository;
@Service
public class RoleService {
	@Autowired
	RoleRepository roleRepository;
	public Role findByName(ERole author) {
		return roleRepository.findByName(ERole.AUTHOR)
				.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
	}

}
