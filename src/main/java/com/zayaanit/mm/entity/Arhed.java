package com.zayaanit.mm.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
@Table(name = "ARHED")
@EqualsAndHashCode(callSuper = true)
public class Arhed extends IdentityIdGenerator {

	@JsonBackReference
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "account_id", nullable = true)
	private Account account;

	private int rowSign;

	private BigDecimal amount;

	private BigDecimal transactionCharge;

	@Temporal(TemporalType.DATE)
	private Date transactionDate;

	@Temporal(TemporalType.TIME)
	private Date transactionTime;

	@OneToOne
	@JoinColumn(name = "trn_history_id", nullable = true)
	private TrnHistory trnHistory;
}
