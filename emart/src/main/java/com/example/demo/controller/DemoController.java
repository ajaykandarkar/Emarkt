package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.LoginDto;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.service.DemoService;

@RestController
public class DemoController {
	
	@Autowired
	DemoService demoService;

	@GetMapping("/")
	public String hello() {
		return demoService.hello();
	}
	
	@GetMapping("/getAll")
	public List<User> getAll(){
		return demoService.getAll();
	}
	
	@PutMapping("/updatesalary/{salary}/{id}")
	public String updateSalary(@PathVariable("salary")  double salary,@PathVariable("id")   int id) {
		demoService.updateSalary(salary,id);
		return "Success";
	}
	
	@PostMapping("/createUser")
	public String createUser(@RequestBody UserDto userDto) {
		demoService.createUser(userDto);
		return "User created successsfully";
	}
	
	@PostMapping("/login")
	public String login(@RequestBody LoginDto loginDto) {
		return demoService.login(loginDto);
	}
}