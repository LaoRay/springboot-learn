package com.zhengtoon.framework.web.configuration;

import com.google.common.base.Predicates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
@EnableConfigurationProperties(SwaggerProperties.class)
public class CommonSwaggerConfig {

    @Autowired
    private SwaggerProperties swaggerProperties;

    @Bean
    @ConditionalOnMissingBean
    public Docket createRestApi() {

        /*
         * 全局配置
         */
        Parameter uiasParam = new ParameterBuilder().name("sessionId").description("uias会话Id").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        Parameter authTokenParam = new ParameterBuilder().name("Authorization").description("安全认证").modelRef(new ModelRef("string")).parameterType("header").required(false).build();

        List<Parameter> aParameters = new ArrayList<>();
        aParameters.add(uiasParam);
        aParameters.add(authTokenParam);

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(Predicates.or(RequestHandlerSelectors.basePackage("com.zhengtoon")
                        ,RequestHandlerSelectors.basePackage(swaggerProperties.getBasePackage())))
                .paths(PathSelectors.any())
                .build().enable(swaggerProperties.isEnable())
                .globalOperationParameters(aParameters);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(swaggerProperties.getTitle() + swaggerProperties.getVersion())
                .description(swaggerProperties.getDescription())
                .version( swaggerProperties.getVersion())
                .build();
    }
}