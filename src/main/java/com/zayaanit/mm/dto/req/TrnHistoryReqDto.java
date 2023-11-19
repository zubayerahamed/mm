package com.zayaanit.mm.dto.req;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.zayaanit.mm.entity.Account;
import com.zayaanit.mm.entity.TrnHistory;
import com.zayaanit.mm.enums.TransactionType;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Zubayer Ahamed
 * @since Nov 18, 2023
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TrnHistoryReqDto extends BaseRequestDTO<TrnHistory> {

	private TransactionType transactionType;
	private BigDecimal amount;
	private BigDecimal transactionCharge;
	private Account fromAccount;
	private Account toAccount;
	private Account incomeSource;
	private Account expenseType;
	private Date trnDate;
	private String trnTime;
	private String year;
	private String month;
	private String note;

	@Override
	public TrnHistory getBean() {
		TrnHistory t = new TrnHistory();
		BeanUtils.copyProperties(this, t);
		return t;
	}

}
