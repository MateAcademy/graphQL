package com.hillel.ua.graphql.config;

import graphql.language.StringValue;
import graphql.scalars.ExtendedScalars;
import graphql.schema.Coercing;
import graphql.schema.GraphQLScalarType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@Configuration
public class GraphQLScalarConfig {

    // Скаляр LocalDateTime — используем ту же реализацию что и DateTime,
    // но регистрируем под именем "LocalDateTime" как указано в схеме
    private static final GraphQLScalarType LOCAL_DATE_TIME = GraphQLScalarType.newScalar()
            .name("LocalDateTime")
            .coercing(ExtendedScalars.DateTime.getCoercing())
            .build();

    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return builder -> builder
                .scalar(LOCAL_DATE_TIME)
                .scalar(ExtendedScalars.Url)
                .scalar(ExtendedScalars.GraphQLLong);
    }
}
