package com.miniproject.userservice.controller;

import com.miniproject.userservice.dto.UserDto;
import com.miniproject.userservice.model.UserEntity;
import com.miniproject.userservice.service.UserService;
import com.miniproject.userservice.vo.Greeting;
import com.miniproject.userservice.vo.RequestUser;
import com.miniproject.userservice.vo.ResponseUser;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class UserController {

    private final Greeting greeting;
    private final Environment env;
    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<ResponseUser> createUser(@RequestBody RequestUser user) {

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDto userDto = modelMapper.map(user, UserDto.class);

        UserDto user1 = userService.createUser(userDto);

        ResponseUser responseUser = new ResponseUser(user1);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);

    }

    @GetMapping("/health_check")
    public String check() {
        return String.format("Port : %s  is ON",
                env.getProperty("local.server.port"));
    }

    @GetMapping("/welcome")
    public String welcome() {
        return greeting.getMessage();
    }


    @GetMapping("/users")
    public ResponseEntity<List<ResponseUser>> getUser() {
        Iterable<UserEntity> userByAll = userService.getUserByAll();
        List<ResponseUser> result = new ArrayList<>();

        userByAll.forEach(n -> result.add(new ModelMapper().map(n, ResponseUser.class)));

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<?> getByUserId(@PathVariable("userId") String userId) {
        UserDto userByUserId = userService.getUserByUserId(userId);
        ResponseUser responseUser = new ModelMapper().map(userByUserId, ResponseUser.class);
        return ResponseEntity.status(200).body(responseUser);
    }

}
