package com.zakiis.security.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.zakiis.core.RedisAutoFlushedCounter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RedisAutoFlushedCounterTest extends SecurityDemoApplicationTests {

	@Autowired
	private StringRedisTemplate redisTemplate;
	
	@Test
	public void test() throws InterruptedException {
		Long contentId = 10012L;
		RedisAutoFlushedCounter counter = new RedisAutoFlushedCounter(5, 10 * 1000L, (key, count) -> {
			log.info("start persistence content share data, key:{}, count:{}", key, count);
		}, redisTemplate, "content:share");
		Thread.sleep(2000L);
		counter.incre(contentId);
		counter.incre(contentId);
		Thread.sleep(10 * 1000L);
		counter.incre(contentId);
		counter.incre(contentId);
		counter.incre(contentId);
		counter.incre(contentId);
		counter.incre(contentId);
		counter.incre(contentId);
		Thread.sleep(20 * 1000L);
	}
}
