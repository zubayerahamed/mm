package com.zayaanit.mm.entity;

import org.apache.commons.lang3.StringUtils;

import com.zayaanit.mm.enums.UserRole;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Zubayer Ahamed
 * @since Nov 15, 2023
 */
@Data
@Entity
@Table(name = "US")
@EqualsAndHashCode(callSuper = true)
public class User extends IdentityIdGenerator {

	private String username;
	private String password;

	@Transient
	private String roles;

	public String getRoles() {
		this.roles = "";
		if(StringUtils.isBlank(roles)) return UserRole.ROLE_GENERAL.name();

		int lastComma = roles.lastIndexOf(',');
		String finalString = roles.substring(0, lastComma);
		roles = finalString;
		return roles;
	}
}
