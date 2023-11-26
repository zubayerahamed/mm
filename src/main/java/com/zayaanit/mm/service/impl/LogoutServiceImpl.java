package com.zayaanit.mm.service.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import com.zayaanit.mm.dto.req.TokenReqDto;
import com.zayaanit.mm.dto.res.TokenResDto;
import com.zayaanit.mm.entity.Token;
import com.zayaanit.mm.repo.TokenRepo;
import com.zayaanit.mm.service.LogoutService;
import com.zayaanit.mm.service.exception.ServiceException;
import com.zayaanit.mm.util.Response;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author Zubayer Ahamed
 * @since Nov 26, 2023
 */
@Service
public class LogoutServiceImpl extends AbstractBaseService<Token, TokenReqDto, TokenResDto> implements LogoutHandler, LogoutService<TokenReqDto, TokenResDto>{

	private TokenRepo tokenRepo;

	protected LogoutServiceImpl(TokenRepo tokenRepo) {
		super(tokenRepo);
		this.tokenRepo = tokenRepo;
	}

	@Override
	public Response<TokenResDto> find(Long id) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response<TokenResDto> save(TokenReqDto reqDto) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response<TokenResDto> update(Long id, TokenReqDto reqDto) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response<TokenResDto> getAll() throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response<TokenResDto> delete(Long id) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		final String authHeader = request.getHeader("Authorization");
		final String jwt;
		if (authHeader == null || !authHeader.startsWith("Bearer ")) return;

		jwt = authHeader.substring(7);

		var storedToken = tokenRepo.findByToken(jwt).orElse(null);

		if (storedToken != null) {
			storedToken.setExpired(true);
			storedToken.setRevoked(true);
			updateEntity(storedToken);
			SecurityContextHolder.clearContext();
		}
	}

}
