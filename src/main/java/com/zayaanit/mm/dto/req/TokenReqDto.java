package com.zayaanit.mm.dto.req;

import org.springframework.beans.BeanUtils;

import com.zayaanit.mm.entity.Token;
import com.zayaanit.mm.enums.TokenType;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Zubayer Ahamed
 * @since Nov 26, 2023
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TokenReqDto extends BaseRequestDTO<Token> {

	private String token;
	private TokenType tokenType;
	private boolean revoked;
	private boolean expired;

	@Override
	public Token getBean() {
		Token t = new Token();
		BeanUtils.copyProperties(this, t);
		return t;
	}
}
