package com.zayaanit.mm.entity;

import com.zayaanit.mm.enums.AccountType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Zubayer Ahamed
 * @since Nov 15, 2023
 */
@Data
@Entity
@Table(name = "ACCOUNT")
@EqualsAndHashCode(callSuper = true)
public class Account extends IdentityIdGenerator {

	private String name;
	private String note;
	@Enumerated(EnumType.STRING)
	private AccountType accountType;
}
