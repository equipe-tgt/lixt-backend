package br.com.ifsp.pi.lixt.utils.security.oauth.objects;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("serial")
public class UserAuthenticated implements Authentication {

	private OauthUserDto user;

	@Override
	public String getName() {
		return user.getName();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return user.getAuthorities();
	}

	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public String getDetails() {
		return null;
	}

	@Override
	public OauthUserDto getPrincipal() {
		return user;
	}

	@Override
	public boolean isAuthenticated() {
		return user != null;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		if (isAuthenticated) {
			if (user == null)
				throw new IllegalArgumentException("Can't not authenticate");
			return;
		}
		user = null;
	}
}