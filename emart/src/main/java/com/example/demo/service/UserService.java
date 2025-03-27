package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.LoginDto;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.DemoRepository;
import com.example.demo.security.JwtTokenProvider;

@Service
public class UserService {
	
	@Autowired
	private DemoRepository repo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	public String hello() {
		return "Hello";
	}
	
	public List<User> getAll(){
		return repo.findAll();
	}
	
	public void updateSalary(double salary,int id) {
		repo.findById(id).orElseThrow(() -> new UserNotFoundException("user with given id is not present"));
		repo.updateSalary(salary,id);
	}
	
	public void createUser(UserDto userDto) {
		User user = User.builder()
				.name(userDto.getName())
				.age(userDto.getAge())
				.salary(userDto.getSalary())
				.email(userDto.getEmail())
				.password(passwordEncoder.encode(userDto.getPassword()))
				.role(Role.valueOf("ROLE_USER"))
				.build();
		repo.save(user);
	
	}

	public String login(LoginDto loginDto) {
		Authentication authentication = null;
		authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
		return  "Token is= " + jwtTokenProvider.generateToken(authentication);
	}
}