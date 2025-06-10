package com.selling;

import com.selling.model.User;
import com.selling.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

@SpringBootApplication
public class DemoApplication {
	private static final
	Logger logger = LoggerFactory.getLogger(DemoApplication.class);

	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	private final UserRepo userRepo;

	@Autowired
	public DemoApplication(UserRepo userRepo) {
		this.userRepo = userRepo;
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@PostConstruct
	public void initUsers() {
		try {
			Optional<User> byEmail = userRepo.findByEmail("nipuna315np@gmail.com");
			if (byEmail.isEmpty()) {
				String encodePassword = passwordEncoder.encode("1234");
				userRepo.save(new User(null,"piyumal", "galle", "nipuna315np@gmail.com", "0754585756", "Admin", "199907502281", String.valueOf(LocalDateTime.now()), "active", "piyumal", encodePassword));
			}

		} catch (Exception e) {
			logger.error("An error occurred during user initialization.", e);
		}

	}
}
