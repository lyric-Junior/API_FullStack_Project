package com.host.server;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

@SpringBootTest
class ServerApplicationTests {

	@Test
	void contextLoads() {
	}
		public static void main(String[] args) {
			System.out.println(LocalDateTime.now());
		}
}
