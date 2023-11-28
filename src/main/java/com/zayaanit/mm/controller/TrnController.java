package com.zayaanit.mm.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zayaanit.mm.annotations.RestApiController;
import com.zayaanit.mm.dto.req.TrnHistoryReqDto;
import com.zayaanit.mm.dto.res.TrnHistoryResDto;
import com.zayaanit.mm.entity.TrnHistory;
import com.zayaanit.mm.service.TrnHistoryService;
import com.zayaanit.mm.util.Response;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * @author Zubayer Ahamed
 * @since Nov 18, 2023
 */
@RestApiController
@RequestMapping("/api/v1/trn")
@Tag(
		name = "Transaction", 
		description = "The Transaction API. Contains all the operations that can be performed on transaction."
	)
public class TrnController extends AbstractBaseController<TrnHistory, TrnHistoryReqDto, TrnHistoryResDto> {

	TrnController(TrnHistoryService<TrnHistoryReqDto, TrnHistoryResDto> trnService){
		super(trnService);
	}

	@GetMapping("/all/{year}")
	public Response<TrnHistoryResDto> getAllByYear(@PathVariable String year){
		return null;
	}

	@Operation(summary = "Get all", description = "Get all account by account type")
	@GetMapping("/all/{year}/{month}")
	public Response<TrnHistoryResDto> getAllByYearAndMonth(@PathVariable String year, @PathVariable String month){
		return null;
	}

	@GetMapping("/all/{trntype}/{year}/{month}")
	public Response<TrnHistoryResDto> getAllByTransactionTypeAndYearAndMonth(@PathVariable String trntype, @PathVariable String year, @PathVariable String month){
		return null;
	}

	@GetMapping("/all/{trntype}/{accountid}/{year}/{month}")
	public Response<TrnHistoryResDto> getAllTransactionsOfAccountByYearAndMonth(@PathVariable Long incomesourceid, @PathVariable String year, @PathVariable String month){
		return null;
	}

}
