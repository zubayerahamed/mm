package com.zayaanit.mm.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.zayaanit.mm.dto.req.AuthenticationReqDTO;
import com.zayaanit.mm.dto.res.AuthenticationResDTO;
import com.zayaanit.mm.entity.User;
import com.zayaanit.mm.enums.ResponseStatus;
import com.zayaanit.mm.model.MyUserDetail;
import com.zayaanit.mm.repo.UserRepo;
import com.zayaanit.mm.service.AuthenticationService;
import com.zayaanit.mm.service.exception.ServiceException;
import com.zayaanit.mm.util.JwtUtilities;
import com.zayaanit.mm.util.Response;

/**
 * @author Zubayer Ahamed
 * @since Nov 17, 2023
 */
@Service
public class AuthenticationServiceImpl extends AbstractBaseService<User, AuthenticationReqDTO, AuthenticationResDTO> implements AuthenticationService<AuthenticationReqDTO, AuthenticationResDTO> {

	@Autowired private AuthenticationManager authenticationManager;
	@Autowired private JwtUtilities jwtUtil;
	private UserRepo userRepo;

	AuthenticationServiceImpl(UserRepo userRepo){
		super(userRepo);
		this.userRepo = userRepo;
	}

	@Override
	public Response<AuthenticationResDTO> find(Long id) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Response<AuthenticationResDTO> save(AuthenticationReqDTO reqDto) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Response<AuthenticationResDTO> update(AuthenticationReqDTO reqDto) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Response<AuthenticationResDTO> getAll() throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Response<AuthenticationResDTO> getAllByChunk(int limit, int offset) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Response<AuthenticationResDTO> delete(AuthenticationReqDTO reqDto) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Response<AuthenticationResDTO> delete(Long id) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response<AuthenticationResDTO> generateToken(AuthenticationReqDTO reqDto) throws ServiceException {
		Response<AuthenticationResDTO> response = new Response<AuthenticationResDTO>();

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(reqDto.getUsername(), reqDto.getPassword()));
		} catch (BadCredentialsException e) {
			response.setStatus(ResponseStatus.ERROR);
			response.setMessage(e.getMessage());
			return response;
		}

		Optional<User> userOp = userRepo.findByUsername(reqDto.getUsername());
		if(!userOp.isPresent()) {
			response.setStatus(ResponseStatus.ERROR);
			response.setMessage("User not found");
			return response;
		}

		final String jwt = jwtUtil.generateToken(new MyUserDetail(userOp.get()));

		response.setStatus(ResponseStatus.SUCCESS);
		response.setMessage("Token generated successfully");
		response.setObj(new AuthenticationResDTO(jwt));
		return response;
	}

}
