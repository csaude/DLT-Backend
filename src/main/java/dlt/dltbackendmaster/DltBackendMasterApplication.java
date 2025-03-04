package dlt.dltbackendmaster;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import dlt.dltbackendmaster.cfgs.AppConfig;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
@EnableJpaAuditing
public class DltBackendMasterApplication extends SpringBootServletInitializer{

	@Autowired
	private AppConfig appConfig;

	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		// TODO Auto-generated method stub
		return builder.sources(DltBackendMasterApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(DltBackendMasterApplication.class, args);
	}
	
	@Bean
	public OpenAPI customOpenAPI(@Value("${springdoc.version}") String appVersion) {
	    final String securitySchemeName = "bearerAuth";
	    return new OpenAPI().info(new Info()
	      .title("DLT API")
	      .version(appVersion)
	      .description("DLT API Specification")
	      .contact(new Contact().name("HelpDesk")
		  .url("https://helpdeskmoz.sis.org.mz")))
	      .addServersItem(new Server()
    		.url(appConfig.getApiHome()))
	      .addSecurityItem(new SecurityRequirement()
    		.addList(securitySchemeName))
	    	.components(new Components()
	    		.addSecuritySchemes(securitySchemeName, new SecurityScheme()
	    			.name(securitySchemeName)
	    			.type(SecurityScheme.Type.HTTP)
	    			.scheme("bearer")
	    			.bearerFormat("JWT")));
	}
}
