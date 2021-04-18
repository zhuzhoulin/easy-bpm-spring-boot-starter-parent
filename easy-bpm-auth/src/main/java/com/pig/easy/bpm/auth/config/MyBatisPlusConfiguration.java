package com.pig.easy.bpm.auth.config;

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
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
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
@AutoConfigureAfter(MybatisPlusProperties.class)
public class MyBatisPlusConfiguration {

    /**
     * 多租户字字段
     */
    private static final String SYSTEM_TENANT_ID = "tenant_id";
    /**
     * 不参与添加多租户表
     */
//    private static final List<String> IGNORE_TENANT_TABLES = new ArrayList("bpm_user","bpm_company");

    private static final String USER_TENANT_PREFIX = "best:bpm:tenantId:";

    @Autowired
    MybatisPlusProperties mybatisPlusProperties;


    @Bean
    @ConditionalOnMissingBean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    @Bean
    @Profile({"local", "test", "prod"})
    @ConditionalOnMissingBean
    public SqlExplainInterceptor sqlExplainInterceptor() {
        SqlExplainInterceptor sqlExplainInterceptor = new SqlExplainInterceptor();
        List<ISqlParser> sqlParserList = new ArrayList<>();
        sqlParserList.add(new BlockAttackSqlParser());
        sqlExplainInterceptor.setSqlParserList(sqlParserList);

        return sqlExplainInterceptor;
    }

//    @Bean(name = "sqlSessionFactory")
//    @ConditionalOnMissingBean
//    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
//        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//        bean.setDataSource(dataSource);
//
//        // 加入sql语句拦截器
//        bean.setPlugins(new Interceptor[]{new PerformanceInterceptor()});
//        bean.setTypeAliasesPackage(this.mybatisPlusProperties.getTypeAliasesPackage());
//        bean.setTypeHandlersPackage(this.mybatisPlusProperties.getTypeHandlersPackage());
////        bean.setMapperLocations(this.mybatisPlusProperties.getMapperLocations());
//
//        return bean.getObject();
//    }

    @PostConstruct
    public void setMybatisPlusProperties() {
        // 设置mapper路径
        mybatisPlusProperties.setMapperLocations(new String[]{"classpath*:/mapper/*Mapper.xml", "classpath*:/mapper/**/*Mapper.xml"});
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


        mybatisPlusProperties.setConfiguration(mybatisConfiguration);


    }

}
