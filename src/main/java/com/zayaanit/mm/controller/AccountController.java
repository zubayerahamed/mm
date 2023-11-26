package com.zayaanit.mm.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zayaanit.mm.annotations.RestApiController;
import com.zayaanit.mm.dto.req.AccountReqDto;
import com.zayaanit.mm.dto.res.AccountResDto;
import com.zayaanit.mm.entity.Account;
import com.zayaanit.mm.enums.AccountType;
import com.zayaanit.mm.service.AccountService;
import com.zayaanit.mm.util.Response;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Zubayer Ahamed
 * @since Nov 18, 2023
 */
@Slf4j
@RestApiController
@RequestMapping("/api/v1/account")
@Tag(
	name = "Account", 
	description = "The Account API. Contains all the operations that can be performed on an Account."
)
public class AccountController extends AbstractBaseController<Account, AccountReqDto, AccountResDto> {

	private AccountService<AccountReqDto, AccountResDto> accountService;

	AccountController(AccountService<AccountReqDto, AccountResDto> accountService){
		super(accountService);
		this.accountService = accountService;
	}

	@Operation(summary = "Get all", description = "Get all account by account type")
	@GetMapping("/all/{accountType}")
	public Response<AccountResDto> getAllByAccountType(@PathVariable AccountType accountType) {
		try {
			return accountService.getAllByAccountType(accountType);
		} catch (Exception e) {
			log.error("Error is {}, {}", e.getMessage(), e);
			return getErrorResponse(e.getMessage());
		}
	}

}
