package com.pig.easy.bpm.generator.service.impl;

import com.pig.easy.bpm.generator.GeneratorApplication;
import com.pig.easy.bpm.generator.service.GeneratorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = GeneratorApplication.class)
class GeneratorServiceImplTest {

    @Autowired
    GeneratorService generatorService;
    @Test
    void initAndExecute() {

        generatorService.initAndExecute(2L,1L,null,false,false,"code_db_config");
    }

}