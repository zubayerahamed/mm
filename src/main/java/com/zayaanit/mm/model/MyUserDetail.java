package com.zayaanit.mm.model;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.zayaanit.mm.entity.User;

import lombok.Data;

/**
 * @author Zubayer Ahamed
 * @since Nov 17, 2023
 */
@Data
public class MyUserDetail implements UserDetails {

	private static final long serialVersionUID = 5990902799272840367L;

	private Long id;
	private String username;
	private String password;

	public MyUserDetail(User user) {
		this.id = user.getId();
		this.username = user.getUsername();
		this.password = user.getPassword();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.emptyList();
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
