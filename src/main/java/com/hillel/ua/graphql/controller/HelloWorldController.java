package com.hillel.ua.graphql.controller;

import com.hillel.ua.graphql.entities.message.Message;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HelloWorldController {

    List<String> messageList = new ArrayList<>() {{
        add("message 0");
        add("message 1");
        add("message 2");
    }};

    @QueryMapping
    public String helloWorld() {
        return "Hello World!";
    }

    @QueryMapping
    public String getHelloWorld() {
        return "Hello World!";
    }

    @QueryMapping
    public String greet(@Argument String name) {
        return "Hello, " + name + "!";
    }

    @QueryMapping
    public Message getMessage(@Argument Long id) {
        String message = this.messageList.get(id.intValue());
        return new Message(id, message, "author");
    }
}
