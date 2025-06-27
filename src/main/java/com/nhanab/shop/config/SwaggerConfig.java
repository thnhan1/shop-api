package com.nhanab.shop.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Fashion Shop E-Commerce API")
                        .version("v1.0")
                        .description("API documentation for the E-Commerce system")
                        .contact(new Contact()
                                .name("Tran Huu Nhan")
                                .email("nhandev10@gmail.com")
                                .url("https://nhanab.xyz"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")));
    }
}
