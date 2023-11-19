package com.zayaanit.mm.service;

import com.zayaanit.mm.service.exception.ServiceException;
import com.zayaanit.mm.util.Response;

public interface BaseService<REQ, RES> {

	public Response<RES> find(Long id) throws ServiceException;

	public Response<RES> save(REQ reqDto) throws ServiceException;

	public Response<RES> update(Long id, REQ reqDto) throws ServiceException;

	public Response<RES> getAll() throws ServiceException;

	public Response<RES> delete(Long id) throws ServiceException;
}
