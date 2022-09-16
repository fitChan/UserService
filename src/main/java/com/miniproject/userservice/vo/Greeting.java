package com.miniproject.userservice.vo;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class Greeting {

    @Value("${greeting.messages}")
    private String message;

}
