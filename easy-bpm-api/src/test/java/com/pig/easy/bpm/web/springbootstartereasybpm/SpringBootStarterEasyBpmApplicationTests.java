package com.pig.easy.bpm.web.springbootstartereasybpm;

import com.pig.easy.bpm.api.EasyBpmApiApplication;
import com.pig.easy.bpm.api.mapper.CheckTableMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest(classes = EasyBpmApiApplication.class)
class SpringBootStarterEasyBpmApplicationTests {

    @Autowired
    CheckTableMapper checkTableMapper;

    @Test
    void contextLoads() {
    }

    @Test
    void testCheckTable(){
        Integer integer = checkTableMapper.checkTableExistsWithSchema("t_user", "t_user");
        System.out.println("integer = " + integer);
    }

    @Test
    void listTable(){
        List<Map> maps = checkTableMapper.listTable();
        System.out.println("intemapsger = " + maps);
    }

    @Test
    void listTableColumn(){
        List<Map> maps = checkTableMapper.listTableColumn("t_auto_create_table");
        System.out.println("intemapsger = " + maps);
    }
}
