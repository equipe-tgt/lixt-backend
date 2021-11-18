package br.com.ifsp.pi.lixt.utils.swagger;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicates;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.OAuth;
import springfox.documentation.service.ResourceOwnerPasswordCredentialsGrant;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Value("${lixt.basic.auth.clientId}") String clientId;
	@Value("${lixt.basic.auth.secretId}") String secretId;
	
	@Bean
	public Docket generateDocumentationSwagger(@Value("${lixt.version}") String version, @Value("${lixt.base.url}") String baseUrl) {	

		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("br.com.ifsp.pi.lixt"))
				.paths(PathSelectors.ant("/**"))
				.build()
				.securitySchemes(Collections.singletonList(securitySchema(baseUrl)))
				.securityContexts(Collections.singletonList(securityContext()))
				.apiInfo(this.metaInfo(version));
	}
	
	private OAuth securitySchema(String baseUrl) {
		return new OAuth("oauth2schema", Collections.emptyList(), List.of(new ResourceOwnerPasswordCredentialsGrant(baseUrl + "/oauth/token")));
	}
	
	private SecurityContext securityContext() {		
		return SecurityContext.builder()
				.securityReferences(Collections.singletonList(new SecurityReference("oauth2schema", new AuthorizationScope[0])))
				.forPaths(Predicates.not(PathSelectors.regex("^(/auth/register|/auth/active-user|/auth/forget-password|/auth/redefine-password|/auth/form/update-password)$")))
				.build();
	}
	
	@Bean
	public SecurityConfiguration securityInfo() {
		return SecurityConfigurationBuilder.builder().clientId(clientId).clientSecret(secretId).build();
	}
	
	private ApiInfo metaInfo(String version) {
		Contact contact = new Contact("The Graduation Team (TGT)", null, "thegraduationteam@gmail.com");
		return new ApiInfoBuilder().title("Lixt - Backend").version(version).contact(contact).build();
	}

}
