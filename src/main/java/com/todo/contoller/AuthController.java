package com.todo.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todo.dto.LoginDto;
import com.todo.dto.RegisterDto;
import com.todo.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthService authService;
	
	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody RegisterDto registerDto){
		String dto = authService.register(registerDto);
		return new ResponseEntity<>(dto,HttpStatus.CREATED); 
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> loginUser(@RequestBody LoginDto logindto){
		String dto = authService.login(logindto);
		return new ResponseEntity<>(dto,HttpStatus.CREATED); 
	}
}
