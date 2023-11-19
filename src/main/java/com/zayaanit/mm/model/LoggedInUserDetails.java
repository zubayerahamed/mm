package com.zayaanit.mm.model;

import lombok.Data;

/**
 * @author Zubayer Ahamed
 * @since Oct 2, 2022
 */
@Data
public class LoggedInUserDetails {

	private Long id;
	private String username;
	private String roles;
}
