package com.zayaanit.mm.service;

import java.io.IOException;

import com.zayaanit.mm.service.exception.ServiceException;
import com.zayaanit.mm.util.Response;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthenticationService <REQ, RES> extends BaseService<REQ, RES> {

	public Response<RES> generateToken(REQ reqDto) throws ServiceException;

	public Response<RES> registerUser(REQ reqDto) throws ServiceException;

	public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws ServiceException, IOException;
}
