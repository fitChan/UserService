package com.miniproject.userservice.service;

import com.miniproject.userservice.dto.UserDto;
import com.miniproject.userservice.model.UserEntity;
import org.springframework.stereotype.Service;


public interface UserService {

    UserDto createUser(UserDto userDto);
    UserDto getUserByUserId(String userId);
    Iterable<UserEntity> getUserByAll();
}
