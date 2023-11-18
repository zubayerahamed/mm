/**
 * 
 */
package com.zayaanit.mm.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zayaanit.mm.annotations.RestApiController;
import com.zayaanit.mm.dto.req.AuthenticationReqDTO;
import com.zayaanit.mm.dto.res.AuthenticationResDTO;
import com.zayaanit.mm.entity.User;
import com.zayaanit.mm.service.AuthenticationService;
import com.zayaanit.mm.util.Response;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Zubayer Ahamed
 * @since Nov 17, 2023
 */
@Slf4j
@RestApiController
@RequestMapping("/api/auth")
public class AuthController extends AbstractBaseController<User, AuthenticationReqDTO, AuthenticationResDTO> {

	private AuthenticationService<AuthenticationReqDTO, AuthenticationResDTO> authservice;

	public AuthController(AuthenticationService<AuthenticationReqDTO, AuthenticationResDTO> authservice) {
		super(authservice);
		this.authservice = authservice;
	}

	@PostMapping("/token")
	public Response<AuthenticationResDTO> generateToken(@RequestBody AuthenticationReqDTO reqDto) {
		try {
			return authservice.generateToken(reqDto);
		} catch (Exception e1) {
			log.error("Error is {}, {}", e1.getMessage(), e1);
			return getErrorResponse(e1.getMessage());
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
