package com.zakiis.auditlog.client.demo.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.zakiis.auditlog.service.RequestService;

@Service
public class FakeRequestService implements RequestService {

	@Override
	public String getIp() {
		return "192.168.137.2";
	}

	@Override
	public String getUserAgent() {
		return "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/111.0.0.0 Safari/537.36";
	}

	@Override
	public String getTraceId() {
		return UUID.randomUUID().toString();
	}

}
