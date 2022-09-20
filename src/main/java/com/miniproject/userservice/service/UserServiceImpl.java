package com.miniproject.userservice.service;

import com.miniproject.userservice.dto.UserDto;
import com.miniproject.userservice.model.UserEntity;
import com.miniproject.userservice.repository.UserRepository;
import com.miniproject.userservice.vo.ResponseOrder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
        String ecrytedPwd = passwordEncoder.encode(userDto.getPwd());
        userEntity.setEncrytedPwd(ecrytedPwd);

        userRepository.save(userEntity);


        return modelMapper.map(userEntity, UserDto.class);
    }

    @Override
    public UserDto getUserByUserId(String userId) {
        UserEntity user = userRepository.findByUserId(userId);

        if (ObjectUtils.isEmpty(user)) {
            throw new UsernameNotFoundException(String.format("%s is not exist", userId));
        }

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDto map = modelMapper.map(user, UserDto.class);
        List<ResponseOrder> orderList = new ArrayList<>();
//        ResponseOrder exam = new ResponseOrder("example", 3, 2000, 6000, "example", null);
//
//        orderList.add(exam);
        map.setOrderList(orderList);

        return map;
    }

    @Override
    public Iterable<UserEntity> getUserByAll() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(username);
        if (userEntity == null) {
            throw new UsernameNotFoundException("안돼 돌아가");
        }
        return new User(userEntity.getEmail(), userEntity.getEncrytedPwd(), true, true,
                true, true, new ArrayList<>());
    }
}
