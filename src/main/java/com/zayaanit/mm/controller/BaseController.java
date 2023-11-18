package com.zayaanit.mm.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.zayaanit.mm.util.Response;

public interface BaseController<REQ, RES> {

	public Response<RES> getAll();

	public Response<RES> save(@RequestBody REQ e);

	public Response<RES> update(@PathVariable Long id, @RequestBody REQ e);

	public Response<RES> find(@PathVariable Long id);

	public Response<RES> delete(@PathVariable Long id);
}
