package com.zayaanit.mm.service.impl;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

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

	@Override
	public Response<UserResDto> save(UserReqDto reqDto) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response<UserResDto> update(UserReqDto reqDto) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response<UserResDto> getAll() throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response<UserResDto> getAllByChunk(int limit, int offset) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response<UserResDto> delete(UserReqDto reqDto) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response<UserResDto> delete(Long id) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

}
