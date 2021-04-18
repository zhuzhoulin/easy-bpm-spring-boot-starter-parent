package com.pig.easy.bpm.generator.config;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.parsers.BlockAttackSqlParser;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.SqlExplainInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * todo:
 *
 * @author : zhoulin.zhu
 * @date : 2021/2/2 17:24
 */
@Slf4j
@Configuration
public class MyBatisPlusConfiguration {

    /** 多租户字字段 */
    private static final String SYSTEM_TENANT_ID = "tenant_id";

    private static final String USER_TENANT_PREFIX = "best:bpm:tenantId:";

    @Autowired
    MybatisPlusProperties mybatisPlusProperties;


    @Bean
    @ConditionalOnMissingBean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }



    /**
     * SQL 执行效率插件
     * 设置 dev test 环境开启
     */
//    @Bean
//    @Profile( {"local","test","prod"})
//    @ConditionalOnMissingBean
//    public PerformanceInterceptor performanceInterceptor() {
//        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
//        performanceInterceptor.setMaxTime(10000);
//        performanceInterceptor.setFormat(true);
//        return performanceInterceptor;
//    }
//
//    @Bean
//    public ISqlInjector sqlInjector() {
//        return new LogicSqlInjector();
//    }

    @Bean
    @Profile( {"local","test","prod"})
    @ConditionalOnMissingBean
    public SqlExplainInterceptor sqlExplainInterceptor(){
        SqlExplainInterceptor sqlExplainInterceptor = new SqlExplainInterceptor();
        List<ISqlParser> sqlParserList = new ArrayList<>();
        sqlParserList.add(new BlockAttackSqlParser());
        sqlExplainInterceptor.setSqlParserList(sqlParserList);

        return sqlExplainInterceptor;
    }

    @PostConstruct
    public void setMybatisPlusProperties() {
        // 设置mapper路径
        mybatisPlusProperties.setMapperLocations(new String[] {"classpath*:/mapper/*Mapper.xml", "classpath*:/mapper/**/*Mapper.xml"});
        // 设置关闭下划线，关闭大写，主键自增长
        GlobalConfig globalConfig = new GlobalConfig();

        GlobalConfig.DbConfig dbConfig = new GlobalConfig.DbConfig();
        dbConfig.setCapitalMode(false);
        dbConfig.setIdType(IdType.AUTO);
        dbConfig.setTableUnderline(true);
        globalConfig.setDbConfig(dbConfig);
        mybatisPlusProperties.setGlobalConfig(globalConfig);
        MybatisConfiguration mybatisConfiguration = new MybatisConfiguration();
        mybatisConfiguration.setMapUnderscoreToCamelCase(true);
//        //配置slq打印日志
//         mybatisConfiguration.setLogImpl(org.apache.ibatis.logging.stdout.StdOutImpl.class);

        mybatisPlusProperties.setConfiguration(mybatisConfiguration);

    }

}
