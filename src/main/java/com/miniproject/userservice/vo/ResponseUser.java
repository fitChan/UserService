package com.miniproject.userservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.miniproject.userservice.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseUser {

    private String email;
    private String name;
    private String userId;
    private List<ResponseOrder> orderList;

    public ResponseUser(UserDto userDto) {
        this.email = userDto.getEmail();
        this.name = userDto.getName();
        this.userId = userDto.getUserId();
    }
}
