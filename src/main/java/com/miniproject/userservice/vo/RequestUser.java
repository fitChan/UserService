package com.miniproject.userservice.vo;


import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RequestUser {

    @NotNull(message = "Email is essential")
    @Size(min = 2,message = "email should be at least two words")
    @Email
    private String email;

    @NotNull(message = "Name is essential")
    @Size(min = 1, message = "name should be at least one words")
    private String name;

    @NotNull(message = "Password is essential")
    @Size(min = 3, message = "Password should be at least three words")
    private String pwd;
}
