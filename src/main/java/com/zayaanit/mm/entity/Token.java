package com.zayaanit.mm.entity;

import com.zayaanit.mm.enums.TokenType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "token")
@EqualsAndHashCode(callSuper = true)
public class Token extends IdentityIdGenerator{


	@Column(unique = true)
	private String token;

	@Enumerated(EnumType.STRING)
	private TokenType tokenType = TokenType.BEARER;

	private boolean revoked;

	private boolean expired;
}
