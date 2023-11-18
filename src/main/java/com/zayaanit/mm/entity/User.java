package com.zayaanit.mm.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
}
