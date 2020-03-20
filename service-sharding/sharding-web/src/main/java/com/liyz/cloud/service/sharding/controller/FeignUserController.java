package com.liyz.cloud.service.sharding.controller;

import com.liyz.cloud.service.sharding.model.UserDO;
import com.liyz.cloud.service.sharding.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "sharding-jdbc-demo", tags = "sharding-jdbc-demo")
@RestController
@RequestMapping("/sharding")
public class FeignUserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/users")
	public Object list() {
		return userService.list();
	}
	
	@GetMapping("/add")
	public Object add() {
		for (long i = 0; i < 100; i++) {
			UserDO user = new UserDO();
			user.setId(i);
			user.setCity("深圳");
			user.setName("李四");
			userService.addUser(user);
		}
		return "success";
	}
	
	@GetMapping("/users/{id}")
	public Object get(@PathVariable Long id) {
		return userService.findById(id);
	}
	
	@GetMapping("/users/query")
	public Object get(String name) {
		return userService.findByName(name);
	}
	
}
