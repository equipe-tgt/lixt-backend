package br.com.ifsp.pi.lixt.utils.security.oauth.entity;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class Oauth2TableGenerator {
	
	private final JdbcTemplate jdbcTemplate;
	
	public void createOauth2Tables() {	
		jdbcTemplate.execute(Oauth2EntitiesScripts.createTableAccessToken());
		jdbcTemplate.execute(Oauth2EntitiesScripts.createTableRefreshToken());
	}

}
