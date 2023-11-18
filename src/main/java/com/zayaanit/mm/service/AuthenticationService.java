package com.zayaanit.mm.service;

import com.zayaanit.mm.service.exception.ServiceException;
import com.zayaanit.mm.util.Response;

public interface AuthenticationService <REQ, RES> extends BaseService<REQ, RES> {

	public Response<RES> generateToken(REQ reqDto) throws ServiceException;
}
