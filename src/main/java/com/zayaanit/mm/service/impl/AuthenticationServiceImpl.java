package com.zayaanit.mm.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zayaanit.mm.dto.req.AuthenticationReqDTO;
import com.zayaanit.mm.dto.res.AuthenticationResDTO;
import com.zayaanit.mm.entity.Token;
import com.zayaanit.mm.entity.User;
import com.zayaanit.mm.enums.ResponseStatus;
import com.zayaanit.mm.enums.TokenType;
import com.zayaanit.mm.model.MyUserDetail;
import com.zayaanit.mm.repo.TokenRepo;
import com.zayaanit.mm.repo.UserRepo;
import com.zayaanit.mm.service.AuthenticationService;
import com.zayaanit.mm.service.exception.ServiceException;
import com.zayaanit.mm.util.JwtService;
import com.zayaanit.mm.util.Response;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;

/**
 * @author Zubayer Ahamed
 * @since Nov 17, 2023
 */
@Service
public class AuthenticationServiceImpl extends AbstractBaseService<User, AuthenticationReqDTO, AuthenticationResDTO> implements AuthenticationService<AuthenticationReqDTO, AuthenticationResDTO> {

	@Autowired private AuthenticationManager authenticationManager;
	@Autowired private JwtService jwtService;
	@Autowired private TokenRepo tokenRepo;
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
	public Response<AuthenticationResDTO> update(Long id, AuthenticationReqDTO reqDto) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Response<AuthenticationResDTO> getAll() throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public Response<AuthenticationResDTO> delete(Long id) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
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

		final String jwt = jwtService.generateToken(new MyUserDetail(userOp.get()));
		final String refreshToken = jwtService.generateRefreshToken(new MyUserDetail(userOp.get()));

		revokeAllUserTokens(userOp.get());
		saveUserToken(userOp.get(), jwt);

		response.setStatus(ResponseStatus.SUCCESS);
		response.setMessage("Token generated successfully");
		response.setObj(new AuthenticationResDTO(jwt, refreshToken));
		return response;
	}

	@Transactional
	private void revokeAllUserTokens(User user) {
		var validUserTokens = tokenRepo.findAllValidTokenByUser(user.getId());
		if (validUserTokens.isEmpty()) return;
		validUserTokens.forEach(token -> {
			token.setExpired(true);
			token.setRevoked(true);

			token.setUpdatedBy("SYSTEM");
			token.setUpdatedOn(new Date());
			token.setUser(user);
		});
		tokenRepo.saveAll(validUserTokens);
	}

	@Transactional
	private void saveUserToken(User user, String jwtToken) {
		Token token = new Token();
		token.setToken(jwtToken);
		token.setTokenType(TokenType.BEARER);
		token.setRevoked(false);
		token.setExpired(false);

		token.setCreatedBy("SYSTEM");
		token.setCreatedOn(new Date());
		token.setUpdatedBy("SYSTEM");
		token.setUpdatedOn(new Date());
		token.setUser(user);

		tokenRepo.save(token);
	}

	@Transactional
	@Override
	public Response<AuthenticationResDTO> registerUser(AuthenticationReqDTO reqDto) throws ServiceException {
		Response<AuthenticationResDTO> response = new Response<AuthenticationResDTO>();

		if(reqDto == null) throw new ServiceException("Empty request");
		if(StringUtils.isBlank(reqDto.getUsername())) throw new ServiceException("Username required");
		if(StringUtils.isBlank(reqDto.getPassword())) throw new ServiceException("Password required");

		Optional<User> userOp = userRepo.findByUsername(reqDto.getUsername());
		if(userOp.isPresent()) throw new ServiceException("Username already exist");

		User user = new User();
		user.setUsername(reqDto.getUsername());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user = createEntity(user);
		if(user == null) throw new ServiceException("Registration failed");

		var jwtToken = jwtService.generateToken(new MyUserDetail(user));
		var refreshToken = jwtService.generateRefreshToken(new MyUserDetail(user));

		saveUserToken(user, jwtToken);

		response.setStatus(ResponseStatus.SUCCESS);
		response.setMessage("Token generated successfully");
		response.setObj(new AuthenticationResDTO(jwtToken, refreshToken));
		return response;
	}

	@Override
	public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws ServiceException, IOException {
		final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		final String refreshToken;
		final String username;
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			return;
		}
		refreshToken = authHeader.substring(7);
		username = jwtService.extractUsername(refreshToken);
		if (username != null) {
			Optional<User> userOp = userRepo.findByUsername(username);
			if(!userOp.isPresent()) {
				throw new ServiceException("User not found");
			}

			if (jwtService.isTokenValid(refreshToken, new MyUserDetail(userOp.get()))) {
				String accessToken = jwtService.generateToken(new MyUserDetail(userOp.get()));
				revokeAllUserTokens(userOp.get());
				saveUserToken(userOp.get(), accessToken);
				AuthenticationResDTO responseDto = new AuthenticationResDTO(accessToken, refreshToken);
				new ObjectMapper().writeValue(response.getOutputStream(), responseDto);
			}
		}
	}

}
