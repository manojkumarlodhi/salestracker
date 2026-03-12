package com.company.salestracker.controller;

import org.springframework.http.HttpHeaders;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.salestracker.dto.request.LoginRequest;
import com.company.salestracker.dto.request.LogoutRequest;
import com.company.salestracker.dto.response.ApiResponse;
import com.company.salestracker.dto.response.JwtResponse;
import com.company.salestracker.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
	
	private final AuthService authService;

	// ==============================
	// LOGIN
	// ==============================
	@PostMapping("/login")
	public ResponseEntity<ApiResponse<JwtResponse>> login(@RequestBody @Valid LoginRequest loginRequest,
			HttpServletResponse response) {

		JwtResponse jwtResponse = authService.loginUser(loginRequest);

		ResponseCookie cookie = ResponseCookie.from("refreshToken", jwtResponse.getRefreshToken()).httpOnly(true)
				.secure(true).path("/").maxAge(7 * 24 * 60 * 60).sameSite("Strict").build();

		response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
		jwtResponse.setRefreshToken(null);

		return ResponseEntity.ok(ApiResponse.success("Login Successful", jwtResponse));
	}
	
	 
	// ==============================
		// REFRESH TOKEN
	// ==============================
		@PostMapping("/refresh-token")
		public ResponseEntity<ApiResponse<JwtResponse>> refreshToken(
				@CookieValue(name = "refreshToken", required = false) String refreshToken,HttpServletResponse response) {
			if (refreshToken == null) {
			    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			}
			System.err.println(refreshToken);
			JwtResponse jwtResponse = authService.refreshToken(refreshToken);
			ResponseCookie cookie = ResponseCookie.from("refreshToken", jwtResponse.getRefreshToken()).httpOnly(true)
					.secure(true).path("/").maxAge(7 * 24 * 60 * 60).sameSite("Strict").build();

			response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
			jwtResponse.setRefreshToken(null);
			return ResponseEntity.ok(ApiResponse.success("Token refreshed", jwtResponse));
		}
		
		
		// ==============================
		// LOGOUT
		// ==============================
		@PostMapping("/logout")
		@PreAuthorize("isAuthenticated()")
		public ResponseEntity<ApiResponse<String>> logout(HttpServletRequest request, HttpServletResponse response,
				@CookieValue(name = "refreshToken", required = false) String refreshToken) {
			if (refreshToken == null) {
			    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			}
			String authHeader = request.getHeader("Authorization");
			String accessToken = null;
			if (authHeader != null && authHeader.startsWith("Bearer ")) {
				accessToken = authHeader.substring(7);
			}

			// Clear refresh token cookie
			ResponseCookie cookie = ResponseCookie.from("refreshToken", "").httpOnly(true).secure(true).path("/").maxAge(0)
					.build();
 
		 	response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
			authService.logout(LogoutRequest.builder().accessToken(accessToken).refreshToken(accessToken)
					.refreshToken(refreshToken).build());
			return ResponseEntity.ok(ApiResponse.success("Logout successful", null));
		}


}
