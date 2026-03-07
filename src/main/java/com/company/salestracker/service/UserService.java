package com.company.salestracker.service;

import com.company.salestracker.dto.request.UpdateUserRequest;
import com.company.salestracker.dto.response.PaginationResponse;
import com.company.salestracker.dto.response.UserResponse;

public interface UserService {
	void approveRequest(String userId);

	void rejectRequest(String userId);

	void toggalStatus(String userId);

	void deleteUser(String userId);


	PaginationResponse<?> getAllUserByRole(String roleId, int pageNo, int pageSize);

	UserResponse updateUser(String userId, UpdateUserRequest request);

	PaginationResponse<?> getAll(int pageNo, int pageSize);

	PaginationResponse<?> getAllPendingRequest(int pageNo, int pageSize);

	UserResponse getUserByEmail(String email);

}
