package br.com.ifsp.pi.lixt.utils.security.oauth.function;

import java.util.List;

import br.com.ifsp.pi.lixt.utils.security.Users;

public abstract class ValidatorAccess {
	
	public static boolean canAcces(Long id) {
		return Users.getUserId().equals(id);
	}
	
	public static boolean canAcces(List<Long> ids) {
		return ids.contains(Users.getUserId());
	}

}
