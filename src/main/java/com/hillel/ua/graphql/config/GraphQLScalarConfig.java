package com.hillel.ua.graphql.config;

import com.hillel.ua.graphql.entities.pet.Bird;
import com.hillel.ua.graphql.entities.pet.Cat;
import com.hillel.ua.graphql.entities.pet.Dog;
import graphql.scalars.ExtendedScalars;
import graphql.schema.GraphQLScalarType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@Configuration
public class GraphQLScalarConfig {

    private static final GraphQLScalarType LOCAL_DATE_TIME = GraphQLScalarType.newScalar()
            .name("LocalDateTime")
            .coercing(ExtendedScalars.DateTime.getCoercing())
            .build();

    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return builder -> builder
                .scalar(LOCAL_DATE_TIME)
                .scalar(ExtendedScalars.Url)
                .scalar(ExtendedScalars.GraphQLLong)
                .scalar(ExtendedScalars.Date)
                .type("Pet", typeWiring -> typeWiring
                        .typeResolver(env -> {
                            Object obj = env.getObject();
                            if (obj instanceof Dog)  return env.getSchema().getObjectType("Dog");
                            if (obj instanceof Cat)  return env.getSchema().getObjectType("Cat");
                            if (obj instanceof Bird) return env.getSchema().getObjectType("Bird");
                            return null;
                        }));
    }
}
