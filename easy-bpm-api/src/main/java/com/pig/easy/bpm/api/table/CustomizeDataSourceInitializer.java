package com.pig.easy.bpm.api.table;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

/**
 * todo: Auto init schema and data
 *
 * @author : zhoulin.zhu
 * @date : 2021/2/3 10:41
 */
@Configuration
@ConditionalOnClass(DataSource.class)
//@ConditionalOnProperty(prefix = InitDataProperties.PREFIX, name = "auto-init-data", havingValue = "true")
public class CustomizeDataSourceInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomizeDataSourceInitializer.class);

    @Value("classpath:db/init/schema-bpm.sql")
    private Resource sqlScriptSchema;
    @Value("classpath:db/init/data-bpm.sql")
    private Resource sqlScriptData;
    @Value("${easy.bpm.api.init-data.continue-on-error:false}")
    private boolean continueOnError;
    @Value("${easy.bpm.api.init-data.sql-script-encoding:utf-8}")
    private String sqlScriptEncoding;
    @Value("${easy.bpm.api.init-data.auto-init-data:false}")
    private boolean autoInitData;

    @Bean
    public DataSourceInitializer dataSourceInitializer(final DataSource dataSource) {
        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource(dataSource);
        dataSourceInitializer.setDatabasePopulator(databasePopulator());
        return dataSourceInitializer;
    }

    private DatabasePopulator databasePopulator() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();

       if(autoInitData){
           populator.addScript(sqlScriptSchema);
           populator.addScript(sqlScriptData);
           populator.setSqlScriptEncoding(sqlScriptEncoding);
           populator.setContinueOnError(continueOnError);
       }

        return populator;
    }
}
