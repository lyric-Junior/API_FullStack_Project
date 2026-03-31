package com.host.server;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class ServerApplicationTests {

	@Test
	void contextLoads() {
	}
	@Test
	public void createHash() {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String senha = "mauroBR@6774";
		String hashedPassword = encoder.encode(senha);
		System.out.println(hashedPassword);
	}
}
