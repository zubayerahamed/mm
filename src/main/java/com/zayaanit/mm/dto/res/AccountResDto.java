package com.zayaanit.mm.dto.res;

import java.math.BigDecimal;

import org.modelmapper.ModelMapper;

import com.zayaanit.mm.entity.Account;
import com.zayaanit.mm.enums.AccountType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Zubayer Ahamed
 * @since Nov 18, 2023
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class AccountResDto extends BaseResponseDTO<Account> {

	public AccountResDto(Account a) {
		new ModelMapper().map(a, this);
	}

	private String name;
	private String note;
	private AccountType accountType;

	private BigDecimal totalIncome;
	private BigDecimal totalExpense;
	private BigDecimal balance;
}
