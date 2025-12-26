package com.todo.serviceImpl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.todo.dto.JwtAuthResponseDto;
import com.todo.dto.LoginDto;
import com.todo.dto.RegisterDto;
import com.todo.entity.Role;
import com.todo.entity.User;
import com.todo.exception.TodoApiException;
import com.todo.repository.RoleRepository;
import com.todo.repository.UserRepository;
import com.todo.security.JwtTokenProvider;
import com.todo.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Override
	public String register(RegisterDto registerDto) {
     
		if(userRepository.existsByUsername(registerDto.getUsername())) {
			throw new TodoApiException(HttpStatus.BAD_REQUEST,"Username is already exists");
		}
		
		if(userRepository.existsByEmail(registerDto.getEmail())) {
			throw new TodoApiException(HttpStatus.BAD_REQUEST,"Email is already exists");
		}
		
		User user = new User();
		user.setName(registerDto.getName());
		user.setEmail(registerDto.getEmail());
		user.setUsername(registerDto.getUsername());
		user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
		
		Set<Role> userRole = new HashSet<>();
		
		if(registerDto.getRoles() != null && !registerDto.getRoles().isEmpty()) {
			for(String roleName : registerDto.getRoles()) {
				Role role = roleRepository.findByName(roleName);
				if(role == null) {
					throw new TodoApiException(HttpStatus.BAD_REQUEST,"Role is not available");
				}
				userRole.add(role);
			}
			
		}else {
			Role role = roleRepository.findByName("ROLE_USER");
            userRole.add(role);
		}
		
		user.setUserRole(userRole);
		userRepository.save(user);
		
		return "User Register Sucessfully !!!";
	}

	@Override
	public JwtAuthResponseDto login(LoginDto loginDto) {
		System.out.println("Login Credentils===>"+loginDto.getUsernameOrEmail() + "," +loginDto.getPassword());
	    try {
	        Authentication authentication = authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(
	                        loginDto.getUsernameOrEmail(),
	                        loginDto.getPassword()
	                )
	        );

	        SecurityContextHolder.getContext().setAuthentication(authentication);
	       
	        // Get logged-in user details
	        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		    String role = userDetails.getAuthorities()
			                    .iterator()
			                    .next()
			                    .getAuthority();
		    
		    String token = jwtTokenProvider.genrateToken(authentication); 
		    
		    userRepository.findByUsernameOrEmail(loginDto.getUsernameOrEmail(), loginDto.getUsernameOrEmail());		
		    
			return token;	        
	    } catch (AuthenticationException ex) {
	        throw new TodoApiException(
	                HttpStatus.UNAUTHORIZED,
	                "Invalid username/email or password"
	        );
	    }
	}


}
