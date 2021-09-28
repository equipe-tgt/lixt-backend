package br.com.ifsp.pi.lixt.utils.security.oauth.entity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Oauth2EntitiesScripts {
	
	public static String createTableAccessToken() {
		StringBuilder stringBuilder = new StringBuilder();
		
		stringBuilder = stringBuilder.append("create table if not exists oauth_access_token (")
				.append("	token_id VARCHAR(255),")
				.append("	token LONG VARBINARY,")
				.append("	authentication_id VARCHAR(255) PRIMARY KEY,")
				.append("	user_name VARCHAR(255),")
				.append("	client_id VARCHAR(255),")
				.append("	authentication LONG VARBINARY,")
				.append("	refresh_token VARCHAR(255)")
				.append(");");
		
		return stringBuilder.toString();
	}
	
	public static String createTableRefreshToken() {
		StringBuilder stringBuilder = new StringBuilder();
		
		stringBuilder = stringBuilder.append("create table  if not exists oauth_refresh_token (")
				.append("	token_id VARCHAR(255),")
				.append("	token LONG VARBINARY,")
				.append("	authentication LONG VARBINARY")
				.append(");");
		
		return stringBuilder.toString();
	}

}
