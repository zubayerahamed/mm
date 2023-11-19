package com.zayaanit.mm.controller;

import org.springframework.web.bind.annotation.RequestMapping;

import com.zayaanit.mm.annotations.RestApiController;
import com.zayaanit.mm.dto.req.TrnHistoryReqDto;
import com.zayaanit.mm.dto.res.TrnHistoryResDto;
import com.zayaanit.mm.entity.TrnHistory;
import com.zayaanit.mm.service.TrnHistoryService;

/**
 * @author Zubayer Ahamed
 * @since Nov 18, 2023
 */
@RestApiController
@RequestMapping("/api/trn")
public class TrnController extends AbstractBaseController<TrnHistory, TrnHistoryReqDto, TrnHistoryResDto> {

	private TrnHistoryService<TrnHistoryReqDto, TrnHistoryResDto> trnService;

	TrnController(TrnHistoryService<TrnHistoryReqDto, TrnHistoryResDto> trnService){
		super(trnService);
		this.trnService = trnService;
	}
}