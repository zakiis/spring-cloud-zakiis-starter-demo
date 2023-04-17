package com.zakiis.auditlog.client.demo.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zakiis.auditlog.annotation.AuditLog;
import com.zakiis.auditlog.client.demo.domain.dto.UserInfo;
import com.zakiis.auditlog.client.demo.service.FakeUserService;
import com.zakiis.core.domain.dto.CommonResp;
import com.zakiis.core.util.AssertUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserInfoController {
	
	private final FakeUserService fakeUserService;

	@GetMapping("/user/detail/{userId}")
	@AuditLog(operateGroup = "System Management", operateTarget = "User", operateType = "VIEW",
			operateTargetIdEL = "#userId", operateDesc = "view user info")
	public CommonResp<UserInfo> userInfo(@PathVariable Long userId) {
		UserInfo dbUser = fakeUserService.selectByUserId(userId);
		AssertUtil.notNull(dbUser, "User not exists");
		return CommonResp.success(dbUser);
	}

	@PutMapping("/user/{userId}")
	@AuditLog(operateGroup = "System Management", operateTarget = "User", operateType = "EDIT",
		operateTargetIdEL = "#userId", operateDesc = "modify user info")
	public CommonResp<Object> modifyUser(@PathVariable Long userId, @RequestBody UserInfo userInfo) throws InterruptedException {
		userInfo.setUserId(userId);
		log.info("Received user modify request, userInfo:{}", userInfo);
		UserInfo dbUser = fakeUserService.selectByUserId(userInfo.getUserId());
		AssertUtil.notNull(dbUser, "User not exists");
		return CommonResp.success();
	}
	
	@DeleteMapping("/user/{userId}")
	@AuditLog(operateGroup = "System Management", operateTarget = "User", operateType = "DELETE",
		operateTargetIdEL = "#userId", operateDesc = "delete user")
	public CommonResp<Object> deleteUser(@PathVariable Long userId) {
		log.info("Received user delete request, user Id:{}", userId);
		return CommonResp.success();
	}
	
	@DeleteMapping("/user/batch")
	@AuditLog(operateGroup = "System Management", operateTarget = "User", operateType = "DELETE",
		operateTargetIdEL = "#join(#userIds)", operateDesc = "delete user")
	public CommonResp<Object> deleteUser(@RequestBody Long[] userIds) {
		log.info("Received user delete request, user Ids:{}", userIds.toString());
		return CommonResp.success();
	}
}
