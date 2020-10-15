package br.com.partner.configs;

import com.google.common.collect.Sets;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static io.swagger.models.Scheme.HTTP;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;

@Data
@EnableSwagger2
@Configuration
@ConfigurationProperties(prefix = "swagger")
public class SwaggerConfig extends DelegatingWebMvcConfiguration {

    private String title;
    private String description;
    private String version;
    private String controllerPackage;
    private String contactEmail;
    private String licenseName;
    private String licenseUrl;

    @Bean
    public Docket swaggerApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .directModelSubstitute(ResponseEntity.class, Void.class)
                .directModelSubstitute(Object.class, Void.class)
                .select()
                .apis(basePackage(controllerPackage))
                .paths(PathSelectors.ant("/**"))
                .build()
                .consumes(Sets.newHashSet(APPLICATION_JSON_VALUE))
                .produces(Sets.newHashSet(APPLICATION_JSON_VALUE))
                .protocols(Sets.newHashSet(HTTP.name()))
                .apiInfo(metaData());
    }

    private Contact createContact() {
        return new Contact(null, null, contactEmail);
    }

    private ApiInfo metaData() {
        return new ApiInfoBuilder()
                .license(licenseName)
                .licenseUrl(licenseUrl)
                .contact(createContact())
                .title(title)
                .description(description)
                .version(version)
                .build();
    }

    @Override
    protected void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        super.addResourceHandlers(registry);
    }

}
