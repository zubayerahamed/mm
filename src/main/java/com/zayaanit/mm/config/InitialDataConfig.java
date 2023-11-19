package com.zayaanit.mm.config;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.zayaanit.mm.entity.User;
import com.zayaanit.mm.repo.UserRepo;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Zubayer Ahamed
 * @sincce Sep 22, 2022
 */
@Slf4j
@Component
public class InitialDataConfig implements ApplicationRunner {

	private static final String SYSTEM_ADMIN_USERNAME = "admin";
	private static final String SYSTEM_ADMIN_PASSWORD = "1234";

	@Autowired private UserRepo userRepo;
	@Autowired private BCryptPasswordEncoder passwordEncoder;

	@Transactional
	@Override
	public void run(ApplicationArguments args) throws Exception {

		log.info("===========> Search System admin user info from DB ===========");
		Optional<User> userOp = userRepo.findByUsername(SYSTEM_ADMIN_USERNAME);
		if(!userOp.isPresent()) {
			User u = new User();
			u.setUsername(SYSTEM_ADMIN_USERNAME);
			u.setPassword(passwordEncoder.encode(SYSTEM_ADMIN_PASSWORD));
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.YEAR, 1);
			u.setCreatedBy(SYSTEM_ADMIN_USERNAME);
			u.setCreatedOn(new Date());
			u.setUpdatedBy(SYSTEM_ADMIN_USERNAME);
			u = userRepo.save(u);
			log.info("===========> System Admin Created ===========");
		}

	}
}
