package com.zakiis.auditlog.client.demo.service;

import org.springframework.stereotype.Service;

import com.zakiis.auditlog.service.LoginUserService;

@Service
public class FakeLoginUserService implements LoginUserService {

	@Override
	public String getUserId() {
		return "1";
	}

	@Override
	public String getLoginAccount() {
		return "zhangsan";
	}

	@Override
	public String getRealName() {
		return "张三";
	}

}
