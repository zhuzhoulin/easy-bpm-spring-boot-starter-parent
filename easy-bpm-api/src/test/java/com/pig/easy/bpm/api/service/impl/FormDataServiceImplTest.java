package com.pig.easy.bpm.api.service.impl;

import com.pig.easy.bpm.api.EasyBpmApiApplication;
import com.pig.easy.bpm.api.dto.response.FormDataDTO;
import com.pig.easy.bpm.api.mapper.FormDataMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(classes = EasyBpmApiApplication.class)

class FormDataServiceImplTest {

    @Autowired
    FormDataMapper formDataMapper;

    @Test
    void test(){

    }

    @Test
    void getListPageByCondition() {
    }

    @Test
    void getListByCondition() {
    }

    @Test
    void insertFormData() {
    }

    @Test
    void getFormDataById() {
    }
}