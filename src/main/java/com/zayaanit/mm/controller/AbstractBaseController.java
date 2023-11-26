package com.zayaanit.mm.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.zayaanit.mm.service.BaseService;
import com.zayaanit.mm.service.impl.CommonFunctionsImpl;
import com.zayaanit.mm.util.Response;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class AbstractBaseController<E, REQ, RES> extends CommonFunctionsImpl<RES> implements BaseController<REQ, RES> {

	protected final BaseService<REQ, RES> service;

	@Operation(summary = "Get all", description = "Get all data")
	@GetMapping
	@Override
	public Response<RES> getAll() {
		try {
			return service.getAll();
		} catch (Exception e) {
			log.error("Error is {}, {}", e.getMessage(), e);
			return getErrorResponse(e.getMessage());
		}
	}

	@Operation(summary = "Save", description = "Store data")
	@PostMapping
	@Override
	public Response<RES> save(@RequestBody REQ req) {
		try {
			return service.save(req);
		} catch (Exception e1) {
			log.error("Error is {}, {}", e1.getMessage(), e1);
			return getErrorResponse(e1.getMessage());
		}
	}

	@Operation(summary = "Update", description = "Edit data")
	@PutMapping("/{id}")
	@Override
	public Response<RES> update(@PathVariable Long id, @RequestBody REQ req) {
		try {
			return service.update(id, req);
		} catch (Exception e1) {
			log.error("Error is {}, {}", e1.getMessage(), e1);
			return getErrorResponse(e1.getMessage());
		}
	}

	@Operation(summary = "Find", description = "Find data")
	@GetMapping("/{id}")
	@Override
	public Response<RES> find(@PathVariable Long id) {
		try {
			return service.find(id);
		} catch (Exception e) {
			log.error("Error is {}, {}", e.getMessage(), e);
			return getErrorResponse(e.getMessage());
		}
	}

	@Operation(summary = "Delete", description = "Delete data")
	@DeleteMapping("/{id}")
	@Override
	public Response<RES> delete(@PathVariable Long id) {
		try {
			return service.delete(id);
		} catch (Exception e1) {
			log.error("Error is {}, {}", e1.getMessage(), e1);
			return getErrorResponse(e1.getMessage());
		}
	}
}
