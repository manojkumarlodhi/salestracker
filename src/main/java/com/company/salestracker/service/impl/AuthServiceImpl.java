package com.company.salestracker.service.impl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.company.salestracker.dto.request.LoginRequest;
import com.company.salestracker.dto.request.UserRequest;
import com.company.salestracker.dto.response.JwtResponse;
import com.company.salestracker.dto.response.UserResponse;
import com.company.salestracker.entity.User;
import com.company.salestracker.exception.BadRequestException;
import com.company.salestracker.exception.ResourceNotFoundException;
import com.company.salestracker.repository.RoleRepository;
import com.company.salestracker.repository.UserRepository;
import com.company.salestracker.security.JwtTokenProvider;
import com.company.salestracker.service.AuthService;
import com.company.salestracker.service.RefreshTokenService;
import com.company.salestracker.util.AppConstant;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
	
	
	private final JwtTokenProvider jwtTokenProvider;
	private final UserRepository userRepo;
	private final BCryptPasswordEncoder encoder;
	private final RoleRepository roleRepo;
	private final RefreshTokenService refreshTokenService;

	@Override
	public UserResponse createUser(UserRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JwtResponse loginUser(LoginRequest request) {
		// TODO Auto-generated method stub
		User user = userRepo.findByEmail(request.getEmail())
				.orElseThrow(()-> new ResourceNotFoundException(AppConstant.USER_NOT_FOUND));
		if(user.getIsDelete()) {
			throw new ResourceNotFoundException(AppConstant.USER_NOT_FOUND);
		}
		if(!encoder.matches(request.getPassword(), user.getPassword())) {
			throw new BadRequestException(AppConstant.INVALID_CREDENTIAL);
		}
		return null;
	}

	@Override
	public JwtResponse refreshToken(String refreshToken) {
		// TODO Auto-generated method stub
		return null;
	}

}
