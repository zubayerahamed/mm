package com.zayaanit.mm.service;

import java.util.List;

import com.zayaanit.mm.util.Response;

public interface CommonFunctions<RES> {
	Response<RES> getSuccessResponse(String message);

	Response<RES> getSuccessResponse(String code, String message);

	Response<RES> getSuccessResponse(String message, RES r);

	Response<RES> getSuccessResponse(String code, String message, RES r);

	Response<RES> getSuccessResponse(String message, List<RES> list);

	Response<RES> getSuccessResponse(String code, String message, List<RES> list);

	Response<RES> getSuccessResponse(String message, Response<RES> response);

	Response<RES> getSuccessResponse(String code, String message, Response<RES> response);

	Response<RES> getErrorResponse(String message);

	Response<RES> getErrorResponse(String code, String message);

	Response<RES> getErrorResponse(String code, String message, Response<RES> response);
}
