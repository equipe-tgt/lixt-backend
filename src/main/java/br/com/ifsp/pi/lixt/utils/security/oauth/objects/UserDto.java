package br.com.ifsp.pi.lixt.utils.security.oauth.objects;

import java.util.List;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements UserDetails, CredentialsContainer {
	
	private Long id;
	
	private String name;

	private String username;

	private String email;

	private String password;
	
	private List<GrantedAuthority> authorities;
	
	private boolean enabled;

	@Override
	public void eraseCredentials() {
		password = null;
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
	
}