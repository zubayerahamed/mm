package com.zayaanit.mm.dto.res;

import com.zayaanit.mm.dto.BaseDTO;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public class BaseResponseDTO<E> implements BaseDTO<E> {

	private Long id;
}
