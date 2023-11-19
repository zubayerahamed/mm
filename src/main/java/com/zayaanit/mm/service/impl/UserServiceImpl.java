package com.zayaanit.mm.service.impl;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zayaanit.mm.dto.req.UserReqDto;
import com.zayaanit.mm.dto.res.UserResDto;
import com.zayaanit.mm.entity.User;
import com.zayaanit.mm.model.MyUserDetail;
import com.zayaanit.mm.repo.UserRepo;
import com.zayaanit.mm.service.UserService;
import com.zayaanit.mm.service.exception.ServiceException;
import com.zayaanit.mm.util.Response;

/**
 * @author Zubayer Ahamed
 * @since Nov 17, 2023
 */
@Component
public class UserServiceImpl extends AbstractBaseService<User, UserReqDto, UserResDto>  implements UserDetailsService, UserService<UserReqDto, UserResDto> {

	private UserRepo userRepo;
	
	@Autowired BCryptPasswordEncoder passwordEncoder;

	UserServiceImpl(UserRepo userRepo){
		super(userRepo);
		this.userRepo = userRepo;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<User> userOp = userRepo.findByUsername(username);
		if (!userOp.isPresent())
			throw new UsernameNotFoundException("User not found");

		return new MyUserDetail(userOp.get());
	}

	@Override
	public Response<UserResDto> find(Long id) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	@Override
	public Response<UserResDto> save(UserReqDto reqDto) throws ServiceException {
		if(reqDto == null) throw new ServiceException("Empty request");
		if(StringUtils.isBlank(reqDto.getUsername())) throw new ServiceException("Username required");
		if(StringUtils.isBlank(reqDto.getPassword())) throw new ServiceException("Password required");

		Optional<User> userOp = userRepo.findByUsername(reqDto.getUsername());
		if(userOp.isPresent()) throw new ServiceException("Username already exist");

		User user = reqDto.getBean();
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user = createEntity(user);
		if(user == null) throw new ServiceException("User creation failed");

		return getSuccessResponse("User created successfully", new UserResDto(user));
	}

	@Transactional
	@Override
	public Response<UserResDto> update(Long id, UserReqDto reqDto) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response<UserResDto> getAll() throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	@Override
	public Response<UserResDto> delete(Long id) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

}
