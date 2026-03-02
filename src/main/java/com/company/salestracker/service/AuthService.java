package com.company.salestracker.service;

import com.company.salestracker.dto.request.LoginRequest;
import com.company.salestracker.dto.request.UserRequest;
import com.company.salestracker.dto.response.JwtResponse;
import com.company.salestracker.dto.response.UserResponse;

public interface AuthService {
	
	
	public UserResponse createUser(UserRequest request);
	public JwtResponse loginUser(LoginRequest request);
	public JwtResponse refreshToken(String refreshToken);

}
