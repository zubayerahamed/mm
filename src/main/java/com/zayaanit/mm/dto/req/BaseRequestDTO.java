package com.zayaanit.mm.dto.req;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zayaanit.mm.dto.BaseDTO;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public abstract class BaseRequestDTO<E> implements BaseDTO<E> {

	protected Long id;

	@JsonIgnore
	public abstract E getBean();

}
