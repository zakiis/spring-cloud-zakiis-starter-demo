package com.zakiis.security.demo.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zakiis.core.domain.dto.CommonResp;
import com.zakiis.security.annotation.OptimisticLock;
import com.zakiis.security.annotation.Permission;
import com.zakiis.security.demo.domain.constants.FunctionCode;
import com.zakiis.security.demo.domain.dto.user.UserInfo;
import com.zakiis.security.demo.service.holder.LoginUserHolder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class UserInfoController {

	@GetMapping("/user/info")
	@Permission(code = FunctionCode.USER_INFO_QUERY)
	public CommonResp<UserInfo> userInfo() {
		return CommonResp.success(LoginUserHolder.get());
	}

	@PutMapping("/user/{userId}")
	@Permission(code = FunctionCode.USER_INFO_MODIFY)
	@OptimisticLock(lockKeyEL = "#userId", lockTimeout = 10)
	public CommonResp<Object> modifyUser(@PathVariable Long userId) throws InterruptedException {
		log.info("Received user modify request, userInfo:{}", LoginUserHolder.get());
		Thread.sleep(15000L);
		return CommonResp.success();
	}
	
	@DeleteMapping("/user")
	@Permission(code = FunctionCode.USER_INFO_DELETE)
	public CommonResp<Object> deleteUser() {
		log.info("Received user delete request, userInfo:{}", LoginUserHolder.get());
		return CommonResp.success();
	}
}
