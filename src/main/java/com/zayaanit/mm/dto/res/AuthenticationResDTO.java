package com.zayaanit.mm.dto.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Zubayer Ahamed
 * @sincce Sep 28, 2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResDTO {
	String token;
	String refreshToken;
}
