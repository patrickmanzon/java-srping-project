package com.crud.project.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.crud.project.entity.User;


public class CustomUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;

	private String username;

	private String password;

	private Long clientId;

	private Collection<? extends GrantedAuthority> authorities;

	public CustomUserDetails(String username, String password, Long clientId, Collection<? extends GrantedAuthority> authorities) {
		this.username = username;
		this.password = password;
		this.authorities = authorities;
		this.clientId = clientId;
	}

	public static CustomUserDetails build(User user) {
		List<GrantedAuthority> authorities = user.getRoles().stream()
		    .map(role -> new SimpleGrantedAuthority(role.getName()))
		    .collect(Collectors.toList());

		return new CustomUserDetails(
		    user.getUsername(),
		    user.getPassword(),
		    user.getClientId(),
		    authorities);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public List<String> getListAuthorities() {
		List<String> roles = new ArrayList<>();
		for (GrantedAuthority role : authorities) {
			roles.add(role.toString());
		}
		return roles;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	public Long getClientId() {
		return clientId;
	}

}
