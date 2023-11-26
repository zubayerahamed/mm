package com.zayaanit.mm.dto.res;

import org.modelmapper.ModelMapper;

import com.zayaanit.mm.entity.Token;
import com.zayaanit.mm.enums.TokenType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Zubayer Ahamed
 * @since Nov 26, 2023
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class TokenResDto extends BaseResponseDTO<Token> {

	public TokenResDto(Token token) {
		new ModelMapper().map(token, this);
	}

	private String token;
	private TokenType tokenType;
	private boolean revoked;
	private boolean expired;

}
