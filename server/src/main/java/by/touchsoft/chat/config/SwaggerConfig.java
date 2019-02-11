package by.touchsoft.chat.config;

import by.touchsoft.chat.controller.UserController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
@ComponentScan(basePackageClasses = UserController.class)
public class SwaggerConfig {

    private static final String SWAGGER_API_VERSION = "1.8";
    private static final String LICENSE_TEXT = "License";
    private static final String TITLE = "chat REST API";
    private static final String DESCRIPTION = "REST API for chat";

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(TITLE)
                .description(DESCRIPTION)
                .license(LICENSE_TEXT)
                .version(SWAGGER_API_VERSION)
                .build();
    }

    @Bean
    public Docket chatApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .pathMapping("/")
                .select()
                .paths(PathSelectors.regex("/chat-1.0-SNAPSHOT.*"))
                .build();
    }


}
