package com.zayaanit.mm.dto.res;

import java.math.BigDecimal;
import java.util.Date;

import org.modelmapper.ModelMapper;

import com.zayaanit.mm.entity.Account;
import com.zayaanit.mm.entity.TrnHistory;
import com.zayaanit.mm.enums.TransactionType;

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
public class TrnHistoryResDto extends BaseResponseDTO<TrnHistory>{

	public TrnHistoryResDto(TrnHistory t){
		new ModelMapper().map(t, this);
	}

	private TransactionType transactionType;
	private BigDecimal amount;
	private BigDecimal transactionCharge;
	private Account fromAccount;
	private Account toAccount;
	private Account incomeSource;
	private Account expenseType;
	private Date transactionDate;
	private Date transactionTime;
	private String year;
	private String month;
	private String note;
}
