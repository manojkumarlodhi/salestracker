package com.company.salestracker.dto.response;

import java.util.Set;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Builder
@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
	private String id;

	private String name;

	private String email;

	private String phone;

	private String status;

	private Set<String> roles;

	private Set<String> permissions;

}
