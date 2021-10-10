package br.com.ifsp.pi.lixt.utils.security.jwt;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtService {
	
	private final JwtConfig jwtConfig;
	private final JwtSecretKey jwtSecretKey;
	
	public String createJwtToken(String subject) {
		return JWT.create()
				.withSubject(subject)
				.withExpiresAt(new Date(System.currentTimeMillis() + jwtConfig.getTokenExpirationAfterMillis()))
				.sign(jwtSecretKey.secretKey());
	}
	
	public String getSubjectByJwtToken(String token) {
		JWTVerifier verifier = JWT.require(jwtSecretKey.secretKey()).build();
		DecodedJWT decodedJWT = verifier.verify(token);
		return decodedJWT.getSubject();
	}

}
