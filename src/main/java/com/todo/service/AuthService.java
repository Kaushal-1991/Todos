package com.todo.service;

import com.todo.dto.LoginDto;
import com.todo.dto.RegisterDto;

public interface AuthService {
	
	public String register(RegisterDto registerDto);
	
	public String login(LoginDto loginDto);

}
