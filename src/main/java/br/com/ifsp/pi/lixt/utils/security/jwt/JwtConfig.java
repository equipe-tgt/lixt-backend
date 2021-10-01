package br.com.ifsp.pi.lixt.utils.security.jwt;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(JwtConfig.class)
@ConfigurationProperties(prefix = "lixt.jwt")
@NoArgsConstructor
@Getter
@Setter
public class JwtConfig {

    private String secretKey;
    private String tokenPrefix;
    private Integer tokenExpirationAfterMinutes;
}
