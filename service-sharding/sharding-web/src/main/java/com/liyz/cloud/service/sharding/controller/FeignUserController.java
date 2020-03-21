package com.liyz.cloud.service.sharding.controller;

import com.liyz.cloud.common.base.Result.PageResult;
import com.liyz.cloud.common.base.Result.Result;
import com.liyz.cloud.common.model.bo.page.PageBaseBO;
import com.liyz.cloud.common.model.bo.sharding.UserBO;
import com.liyz.cloud.service.sharding.model.UserDO;
import com.liyz.cloud.service.sharding.remote.RemoteUserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Api(value = "sharding-jdbc-demo", tags = "sharding-jdbc-demo")
@RestController
@RequestMapping("/sharding")
public class FeignUserController {
	
	@Autowired
	private RemoteUserService remoteUserService;
	
	@GetMapping("/users")
	public Result<List<UserBO>> list() {
		return Result.success(remoteUserService.list());
	}
	
	@GetMapping("/add")
	public Result add() {
		for (long i = 10; i < 100; i++) {
			UserDO user = new UserDO();
			user.setId(i);
			user.setCity("深圳");
			user.setName("李四");
			remoteUserService.addUser(user);
		}
		return Result.success();
	}
	
	@GetMapping("/users/{id}")
	public Result<UserBO> get(@PathVariable Long id) {
		log.info("this is info level....");
		log.warn("this is warn level....");
		log.error("this is error level....");
		return Result.success(remoteUserService.findById(id));
	}
	
	@GetMapping("/users/query")
	public Result<UserBO> get(String name) {
		return Result.success(remoteUserService.findByName(name));
	}

	@GetMapping("/users/page")
	public PageResult<UserBO> page(PageBaseBO pageBaseBO) {
		return PageResult.success(remoteUserService.page(pageBaseBO));
	}
	
}
