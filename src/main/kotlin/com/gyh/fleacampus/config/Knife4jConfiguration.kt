/*
package com.gyh.fleacampus.config

import io.swagger.annotations.ApiOperation
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.*
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2


*/
/**
 * Created by GYH on 2021/4/16
 *//*

@Configuration
@EnableSwagger2
class Knife4jConfiguration {
    @Bean(value = ["defaultApi2"])
    fun defaultApi2(): Docket {
        return Docket(DocumentationType.OAS_30)
            .apiInfo(
                ApiInfoBuilder() //.title("swagger-bootstrap-ui-demo RESTful APIs")
                    .description("# swagger-bootstrap-ui-demo RESTful APIs")
                    .termsOfServiceUrl("http://www.xx.com/")
                    .contact(Contact("GYH", "#", "2350871838@qq.com"))
                    .version("1.0")
                    .build()
            ) //分组名称
            .groupName("1.0版本")
            .select() //这里指定Controller扫描包路径
            .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation::class.java))
            .paths(PathSelectors.any())
            .build()
            .securityContexts(securityContexts())
            .securitySchemes(securitySchemes())
    }

    private fun securitySchemes(): List<ApiKey> {
        return listOf(ApiKey("Authorization", "Authorization", "header"))
    }

    private fun securityContexts(): List<SecurityContext> {
        return listOf(
            SecurityContext.builder()
                .securityReferences(defaultAuth())
                .operationSelector {
                    it.requestMappingPattern().matches("\\/.*".toRegex())
                }.build()
        )
    }

    private fun defaultAuth(): List<SecurityReference> {
        val authorizationScope = AuthorizationScope("global", "accessEverything")
        return listOf(SecurityReference("Authorization", arrayOf(authorizationScope)))
    }

}*/
