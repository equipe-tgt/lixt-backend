package br.com.ifsp.pi.lixt.utils.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String[] AUTH_WHITELIST = {
			"/swagger-resources/**", "/swagger-ui.html", "/v2/api-docs", "/webjars/**", "/actuator/**"
	};

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
				.antMatchers("/auth/register")
				.antMatchers("/auth/forget-password/**")
				.antMatchers("/auth/active-user")
				.antMatchers(HttpMethod.OPTIONS,"/**")
				.antMatchers(AUTH_WHITELIST)
				.antMatchers(HttpMethod.OPTIONS,"/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.requiresChannel()
				.requestMatchers(r -> r.getHeader("X-Forwarded-Proto") != null)
				.requiresSecure();

		http.headers()
				.httpStrictTransportSecurity().maxAgeInSeconds(31536000).includeSubDomains(true).and()
				.contentSecurityPolicy("hello").and()
				.referrerPolicy(ReferrerPolicyHeaderWriter.ReferrerPolicy.SAME_ORIGIN).and()
				.permissionsPolicy().policy("geolocation=(self, \"https://lixt-ws.azure.net\")");

		http.authorizeRequests()
			.antMatchers(AUTH_WHITELIST).permitAll()
			.antMatchers("/auth/register").permitAll()
			.antMatchers("/auth/forget-password/**").permitAll()
			.antMatchers("/auth/active-user").permitAll()
			.anyRequest().authenticated()
			.and()
			.formLogin().disable()
			.csrf().disable()
			.httpBasic();
	}

}