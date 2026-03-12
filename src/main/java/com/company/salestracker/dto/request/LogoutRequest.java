package com.company.salestracker.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LogoutRequest {
	@NotEmpty
	private String refreshToken;
	@NotEmpty
	private String accessToken;

}
