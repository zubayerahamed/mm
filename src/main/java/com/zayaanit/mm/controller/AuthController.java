package com.zayaanit.mm.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zayaanit.mm.dto.req.AuthenticationReqDTO;
import com.zayaanit.mm.dto.res.AuthenticationResDTO;
import com.zayaanit.mm.entity.User;
import com.zayaanit.mm.service.AuthenticationService;
import com.zayaanit.mm.util.Response;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Zubayer Ahamed
 * @since Nov 17, 2023
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController extends AbstractBaseController<User, AuthenticationReqDTO, AuthenticationResDTO> {

	private AuthenticationService<AuthenticationReqDTO, AuthenticationResDTO> authservice;

	AuthController(AuthenticationService<AuthenticationReqDTO, AuthenticationResDTO> authservice) {
		super(authservice);
		this.authservice = authservice;
	}

	@PostMapping("/register")
	public Response<AuthenticationResDTO> register(@RequestBody AuthenticationReqDTO reqDto) {
		try {
			return authservice.registerUser(reqDto);
		} catch (Exception e1) {
			log.error("Error is {}, {}", e1.getMessage(), e1);
			return getErrorResponse(e1.getMessage());
		}
	}

	@PostMapping("/authenticate")
	public Response<AuthenticationResDTO> authenticate(@RequestBody AuthenticationReqDTO reqDto) {
		try {
			return authservice.generateToken(reqDto);
		} catch (Exception e1) {
			log.error("Error is {}, {}", e1.getMessage(), e1);
			return getErrorResponse(e1.getMessage());
		}
	}

	@PostMapping("/refresh-token")
	public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
		try {
			authservice.refreshToken(request, response);
		} catch (Exception e1) {
			log.error("Error is {}, {}", e1.getMessage(), e1);
		}
	}

	@Hidden
	@Override
	public Response<AuthenticationResDTO> getAll() {
		// TODO Auto-generated method stub
		return super.getAll();
	}

	@Hidden
	@Override
	public Response<AuthenticationResDTO> save(AuthenticationReqDTO req) {
		// TODO Auto-generated method stub
		return super.save(req);
	}

	@Hidden
	@Override
	public Response<AuthenticationResDTO> update(Long id, AuthenticationReqDTO req) {
		// TODO Auto-generated method stub
		return super.update(id, req);
	}

	@Hidden
	@Override
	public Response<AuthenticationResDTO> find(Long id) {
		// TODO Auto-generated method stub
		return super.find(id);
	}

	@Hidden
	@Override
	public Response<AuthenticationResDTO> delete(Long id) {
		// TODO Auto-generated method stub
		return super.delete(id);
	}

}
