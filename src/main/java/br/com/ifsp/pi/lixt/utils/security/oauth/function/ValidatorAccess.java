package br.com.ifsp.pi.lixt.utils.security.oauth.function;

import br.com.ifsp.pi.lixt.utils.security.Users;

public abstract class ValidatorAccess {
	
	public static boolean canAcces(Long id) {
		return Users.getUserId().equals(id);
	}

}
