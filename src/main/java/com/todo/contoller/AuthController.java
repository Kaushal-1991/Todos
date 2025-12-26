package com.todo.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todo.dto.JwtAuthResponseDto;
import com.todo.dto.LoginDto;
import com.todo.dto.RegisterDto;
import com.todo.service.AuthService;

@CrossOrigin("*")
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
	public ResponseEntity<JwtAuthResponseDto> loginUser(@RequestBody LoginDto logindto){
		System.out.println("=======>"+logindto);
		String token = authService.login(logindto);
		JwtAuthResponseDto jwtAuthResponse = new JwtAuthResponseDto();
        jwtAuthResponse.setAccessToken(token);

        return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK); 
	}
}
