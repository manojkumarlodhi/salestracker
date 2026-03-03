package com.company.salestracker.service.impl;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.company.salestracker.dto.request.LoginRequest;
import com.company.salestracker.dto.request.LogoutRequest;
import com.company.salestracker.dto.request.OtpRequest;
import com.company.salestracker.dto.request.ResetPasswordRequest;
import com.company.salestracker.dto.request.UserRequest;
import com.company.salestracker.dto.response.JwtResponse;
import com.company.salestracker.dto.response.OtpResponse;
import com.company.salestracker.dto.response.UserResponse;
import com.company.salestracker.entity.RefreshToken;
import com.company.salestracker.entity.User;
import com.company.salestracker.entity.UserStatus;
import com.company.salestracker.exception.BadRequestException;
import com.company.salestracker.exception.ResourceNotFoundException;
import com.company.salestracker.repository.RoleRepository;
import com.company.salestracker.repository.UserRepository;
import com.company.salestracker.security.JwtTokenProvider;
import com.company.salestracker.service.AuthService;
import com.company.salestracker.service.RefreshTokenService;
import com.company.salestracker.util.AppConstant;

import jakarta.transaction.Transactional;
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
	@Transactional
	public JwtResponse loginUser(LoginRequest request) {
		User user = userRepo.findByEmail(request.getEmail())
				.orElseThrow(()-> new ResourceNotFoundException(AppConstant.USER_NOT_FOUND));
		if(user.getIsDelete()) {
			throw new ResourceNotFoundException(AppConstant.USER_NOT_FOUND);
		}
		if(!encoder.matches(request.getPassword(), user.getPassword())) {
			throw new BadRequestException(AppConstant.INVALID_CREDENTIAL);
		}
		if(!user.getStatus().equals(UserStatus.ACTIVE)) {
			throw new BadRequestException(AppConstant.USER_IS_BLOCKED);
		}
		String accesToken=jwtTokenProvider.generateAccessToken(user);
		RefreshToken refresToken=refreshTokenService.createToken(user);
		return JwtResponse.builder().accessToken(accesToken).refreshToken(refresToken.getToken()).build();
	}
	
	private User currentLoginUser() {

		return userRepo.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
				.filter(u -> !Boolean.TRUE.equals(u.getIsDelete()))
				.orElseThrow(() -> new ResourceNotFoundException(AppConstant.USER_NOT_FOUND));
	}

	@Override
	@Transactional
	public JwtResponse refreshToken(String refreshToken) {
		RefreshToken oldToken = refreshTokenService.verifyToken(refreshToken);
		User user = oldToken.getUser();
		RefreshToken newRefreshToken = refreshTokenService.rotateToken(oldToken);
		String newAccessToken = jwtTokenProvider.generateAccessToken(user);

		return JwtResponse.builder().accessToken(newAccessToken).refreshToken(newRefreshToken.getToken()).build();
	}

	@Override
	public void logout(LogoutRequest request) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resetPassword(ResetPasswordRequest request) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public OtpResponse verifyOtp(OtpRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void forgotPassword(String email) {
		// TODO Auto-generated method stub
		
	}

}
