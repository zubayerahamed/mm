package com.zayaanit.mm.dto.req;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zayaanit.mm.entity.User;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Zubayer Ahamed
 * @since Nov 18, 2023
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserReqDto extends BaseRequestDTO<User> {
	private String username;
	private String password;

	@JsonIgnore
	@Override
	public User getBean() {
		User u = new User();
		BeanUtils.copyProperties(this, u);
		return u;
	}
}
