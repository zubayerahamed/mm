package com.zayaanit.mm.service.impl;

import java.util.Collections;
import java.util.List;

import com.zayaanit.mm.enums.ResponseStatus;
import com.zayaanit.mm.service.CommonFunctions;
import com.zayaanit.mm.util.Response;

/**
 * @author Zubayer Ahamed
 * @sincce Sep 29, 2022
 */
public class CommonFunctionsImpl<RES> implements CommonFunctions<RES> {

	@Override
	public Response<RES> getSuccessResponse(String message) {
		return getSuccessResponse(null, message);
	}

	@Override
	public Response<RES> getSuccessResponse(String code, String message) {
		Response<RES> response = new Response<RES>();
		response.setStatus(ResponseStatus.SUCCESS);
		response.setCode(code);
		response.setMessage(message);
		return response;
	}

	@Override
	public Response<RES> getSuccessResponse(String message, RES r) {
		return getSuccessResponse(null, message, r);
	}

	@Override
	public Response<RES> getSuccessResponse(String code, String message, RES r) {
		Response<RES> response = new Response<RES>();
		response.setStatus(ResponseStatus.SUCCESS);
		response.setCode(code);
		response.setMessage(message);
		response.setObj(r);
		return response;
	}

	@Override
	public Response<RES> getSuccessResponse(String message, List<RES> list) {
		return getSuccessResponse(null, message, list);
	}

	@Override
	public Response<RES> getSuccessResponse(String code, String message, List<RES> list) {
		Response<RES> response = new Response<RES>();
		response.setStatus(ResponseStatus.SUCCESS);
		response.setCode(code);
		response.setMessage(message);
		response.setItems(list);
		return response;
	}

	@Override
	public Response<RES> getSuccessResponse(String message, Response<RES> response) {
		return getSuccessResponse(null, message, response);
	}

	@Override
	public Response<RES> getSuccessResponse(String code, String message, Response<RES> response) {
		response.setStatus(ResponseStatus.SUCCESS);
		response.setCode(code);
		response.setMessage(message);
		return response;
	}

	@Override
	public Response<RES> getErrorResponse(String message) {
		Response<RES> response = new Response<RES>();
		response.setStatus(ResponseStatus.ERROR);
		response.setMessage(message);
		response.setItems(Collections.emptyList());
		response.setObj(null);
		response.setModel(Collections.emptyMap());
		response.setPage(null);
		return response;
	}

	@Override
	public Response<RES> getErrorResponse(String code, String message) {
		Response<RES> response = new Response<RES>();
		response.setStatus(ResponseStatus.ERROR);
		response.setCode(code);
		response.setMessage(message);
		response.setItems(Collections.emptyList());
		response.setObj(null);
		response.setModel(Collections.emptyMap());
		response.setPage(null);
		return response;
	}

	@Override
	public Response<RES> getErrorResponse(String code, String message, Response<RES> response) {
		response.setStatus(ResponseStatus.ERROR);
		response.setCode(code);
		response.setMessage(message);
		if(response.getItems() == null || response.getItems().isEmpty()) response.setItems(Collections.emptyList());
		if(response.getObj() == null) response.setObj(null);
		if(response.getModel() == null || response.getModel().isEmpty()) response.setModel(Collections.emptyMap());
		if(response.getPage() == null) response.setPage(null);
		return response;
	}

}
