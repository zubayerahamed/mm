package com.zayaanit.mm.util;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.zayaanit.mm.enums.ResponseStatus;

import lombok.Data;

/**
 * @author Zubayer Ahamed
 * @sincce Oct 24, 2022
 */
@Data
public class Response<R> {

	private ResponseStatus status = ResponseStatus.SUCCESS;
	private String code;
	private String message;

	private Map<String, R> model;
	private List<R> items;
	private R obj;
	private Page<R> page;
}
