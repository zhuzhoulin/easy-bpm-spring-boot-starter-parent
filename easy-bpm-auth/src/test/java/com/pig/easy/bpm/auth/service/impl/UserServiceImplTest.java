package com.pig.easy.bpm.auth.service.impl;

import com.pig.easy.bpm.auth.dto.request.UserQueryDTO;
import com.pig.easy.bpm.auth.dto.response.UserDTO;
import com.pig.easy.bpm.auth.service.UserService;
import com.pig.easy.bpm.common.utils.Result;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    UserService userService;

    @Test
    void getListByCondition() {
        Result<List<UserDTO>> result = userService.getListByCondition(new UserQueryDTO());
        System.out.println("result = " + result);
    }
}