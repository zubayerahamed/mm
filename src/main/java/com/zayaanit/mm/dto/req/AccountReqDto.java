package com.zayaanit.mm.dto.req;

import java.math.BigDecimal;

import org.springframework.beans.BeanUtils;

import com.zayaanit.mm.entity.Account;
import com.zayaanit.mm.enums.AccountType;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Zubayer Ahamed
 * @since Nov 18, 2023
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AccountReqDto extends BaseRequestDTO<Account> {

	private String name;
	private String note;
	private BigDecimal amount = BigDecimal.ZERO;
	private AccountType accountType;

	@Override
	public Account getBean() {
		Account a = new Account();
		BeanUtils.copyProperties(this, a);
		return a;
	}

}
