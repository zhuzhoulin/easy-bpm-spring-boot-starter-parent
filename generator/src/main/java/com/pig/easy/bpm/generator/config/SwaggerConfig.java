package com.pig.easy.bpm.generator.config;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * todo: swagger 配置 可放入
 *
 * @author : pig
 * @date : 2020/5/15 14:00
 */
@Configuration
@EnableSwagger2
@Profile({"local", "test", "prod"})
public class SwaggerConfig {

    @Value(value = "${swagger.controller:}")
    private String controller;
    @Value(value = "${swagger.title:}")
    private String title;
    @Value(value = "${swagger.description:}")
    private String description;
    @Value(value = "${swagger.version:}")
    private String version;
    @Value(value = "${swagger.license:}")
    private String license;
    @Value(value = "${swagger.licenseUrl:}")
    private String licenseUrl;
    @Value(value = "${swagger.author:}")
    private String author;
    @Value(value = "${swagger.authorBlogUrl:}")
    private String authorBlogUrl;
    @Value(value = "${swagger.email:}")
    private String email;

    @Bean
    public Docket createRestApi() {
        checkData();
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(controller))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(new ApiInfoBuilder()
                        .title(title)
                        .description(description)
                        .version(version)
                        .license(license)
                        .licenseUrl(licenseUrl)
                        .contact(new Contact(author, authorBlogUrl, email))
                        .build());
    }

    private void checkData() {
        if (StringUtils.isEmpty(controller) || StringUtils.isEmpty(title)) {
            throw new RuntimeException("SwaggerConfig init fail, please config on nacos");
        }
        System.out.println("SwaggerConfig  ############################## = " + title);
    }
}
