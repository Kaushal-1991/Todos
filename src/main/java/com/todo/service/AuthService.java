package com.todo.service;

import com.todo.dto.LoginDto;
import com.todo.dto.LoginResponseDto;
import com.todo.dto.RegisterDto;

public interface AuthService {
	
	public String register(RegisterDto registerDto);
	
	public LoginResponseDto login(LoginDto loginDto);

}
