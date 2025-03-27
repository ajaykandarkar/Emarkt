package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.DemoRepository;

@Service
public class DemoService {
	
	@Autowired
	private DemoRepository repo;

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
				.password(userDto.getPassword())
				.build();
		repo.save(user);
	
	}
}