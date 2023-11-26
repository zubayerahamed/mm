package com.zayaanit.mm.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.zayaanit.mm.entity.BaseEntity;
import com.zayaanit.mm.entity.User;
import com.zayaanit.mm.model.MyUserDetail;
import com.zayaanit.mm.repo.ServiceRepository;
import com.zayaanit.mm.repo.UserRepo;

import jakarta.persistence.EntityManager;

public abstract class AbstractBaseService<E extends BaseEntity, REQ, RES> extends CommonFunctionsImpl<RES>{

	protected static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	protected static final String PRODUCT_NAME = "SMS";
	protected static final String STORE_NAME = "SMS";

	@Autowired protected ApplicationContext appContext;
	@Autowired private UserRepo userRepo;
	@Autowired protected EntityManager em;
	@Autowired protected JdbcTemplate jdbcTemplate;
	@Autowired protected BCryptPasswordEncoder passwordEncoder;
	private final ServiceRepository<E> repository;
	private ModelMapper modelMapper;

	protected AbstractBaseService(ServiceRepository<E> repository) {
		this.repository = repository;
		modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setAmbiguityIgnored(true);
	}

	protected List<E> createAllEntity(List<E> entities) {
		for(E entity : entities) {
			entity.setCreatedBy(getLoggedInUserDetails().getUsername());
			entity.setCreatedOn(new Date());
			entity.setUpdatedBy(getLoggedInUserDetails().getUsername());
			entity.setUpdatedOn(new Date());
			entity.setUser(getLoggedInUser());
		}
		return repository.saveAll(entities);
	}

	protected E createEntity(E entity) {
		entity.setCreatedBy(getLoggedInUserDetails().getUsername());
		entity.setCreatedOn(new Date());
		entity.setUpdatedBy(getLoggedInUserDetails().getUsername());
		entity.setUpdatedOn(new Date());
		entity.setUser(getLoggedInUser());
		return repository.save(entity);
	}

	protected E updateEntity(E entity) {
		entity.setUpdatedBy(getLoggedInUserDetails().getUsername());
		entity.setUpdatedOn(new Date());
		entity.setUser(getLoggedInUser());
		return repository.save(entity);
	}

	protected MyUserDetail getLoggedInUserDetails() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth == null || !auth.isAuthenticated()) return null;

		Object principal = auth.getPrincipal();
		if(principal instanceof MyUserDetail) {
			return (MyUserDetail) principal;
		}
		return null;
	}

	protected User getLoggedInUser() {
		Optional<User> userOp = userRepo.findById(getLoggedInUserDetails().getId());
		return userOp.isPresent() ? userOp.get() : null;
	}

}
