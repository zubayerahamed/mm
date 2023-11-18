package com.zayaanit.mm.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.zayaanit.mm.enums.TransactionType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Zubayer Ahamed
 * @since Nov 15, 2023
 */
@Data
@Entity
@Table(name = "TRN_HISTORY")
@EqualsAndHashCode(callSuper = true)
public class TransactionHistory extends IdentityIdGenerator {

	@Enumerated(EnumType.STRING)
	private TransactionType transactionType;

	private BigDecimal amount;

	private BigDecimal transactionCharge;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "from_account_id", nullable = true)
	private Account fromAccount;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "to_account_id", nullable = true)
	private Account toAccount;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "income_source_id", nullable = true)
	private Account incomeSource;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "expense_type_id", nullable = true)
	private Account expenseType;

	@Temporal(TemporalType.DATE)
	private Date transactionDate;

	@Temporal(TemporalType.TIME)
	private Date transactionTime;

	private String year;
	private String month;
	private String note;
}
