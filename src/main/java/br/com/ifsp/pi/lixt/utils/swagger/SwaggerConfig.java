package br.com.ifsp.pi.lixt.utils.swagger;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket generateDocumentationSwagger(@Value("${lixt.version}") String version) {	

		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("br.com.ifsp.pi.lixt"))
				.paths(PathSelectors.ant("/**"))
				.build()
				.globalOperationParameters(
						List.of(
								new ParameterBuilder()
									.name("Authorization")
									.description("Token")
									.modelRef(new ModelRef("string"))
									.parameterType("header")
									.required(false)
									.build()
						)
				)
				.apiInfo(this.metaInfo(version));
	}
	
	private ApiInfo metaInfo(String version) {
		Contact contact = new Contact("The Graduation Team (TGT)", null, "thegraduationteam@gmail.com");
		return new ApiInfoBuilder().title("Lixt - Backend").version(version).contact(contact).build();
	}

}
