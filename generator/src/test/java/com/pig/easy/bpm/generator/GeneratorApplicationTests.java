package com.pig.easy.bpm.generator;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.pig.easy.bpm.generator.constant.GeneratorConstant;
import com.pig.easy.bpm.generator.dto.response.ColumnInfo;
import com.pig.easy.bpm.generator.dto.response.TableConfig;
import com.pig.easy.bpm.generator.dto.response.TableFieldInfo;
import com.pig.easy.bpm.generator.dto.response.TableInfo;
import com.pig.easy.bpm.generator.generator.CustomAutoGenerator;
import com.pig.easy.bpm.generator.generator.CustomFreemarkerTemplateEngine;
import com.pig.easy.bpm.generator.generator.CustomMySqlQuery;
import com.pig.easy.bpm.generator.mapper.CheckTableMapper;
import com.pig.easy.bpm.generator.service.CodeTableService;
import com.pig.easy.bpm.generator.service.ColumnService;
import com.pig.easy.bpm.generator.utils.GeneratorUtils;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@SpringBootTest(classes = GeneratorApplication.class)
class GeneratorApplicationTests {

    @Autowired
    CheckTableMapper checkTableMapper;
    @Autowired
    private ColumnService codeColumnService;
    @Autowired
    private CodeTableService codeTableService;

    @Test
    void contextLoads() {
    }

    @Test
    void listTable() {
        List<TableInfo> maps = checkTableMapper.listTable();
        System.out.println("intemapsger = " + maps);
    }

    @Test
    void TableInfo() {
        TableInfo tableInfo = checkTableMapper.getTableInfo("t_auto_create_table");
        System.out.println("intemapsger = " + tableInfo);
    }

    @Test
    void listTableColumn() {
        List<ColumnInfo> maps = checkTableMapper.listTableColumn("t_auto_create_table");
        System.out.println("intemapsger = " + maps);
    }

    @Test
    void testGenerator(){

            String templateNames = "mapper,api";
            String codePath = "E:\\work\\iedaworkspace\\project\\parent\\new 2\\spring-boot-starter-easy-bpm-parent\\generator";
            String outputDir = codePath + File.separator + String.join(File.separator, Arrays.asList("src", "main", "java"));
            String vuePath = "C:\\Users\\Administrator\\Downloads\\vue-admin-template-master (1)\\vue-admin-template\\src";
            String contentPath = "code";
            String parentPackage = "com.pig.easy.bpm.generator";
            String DTO_RESPONSE = parentPackage + ".dto.response";
            String DTO_REQUEST = parentPackage + ".dto.request";
            String VO_REQUEST = parentPackage + ".vo.request";


            //全局配置
            GlobalConfig config = new GlobalConfig();
            //设置是否支持AR模式
            config.setActiveRecord(true)
                    //设置生成代码的作者
                    .setAuthor("pig")
                    //设置输出代码的位置
                    .setOutputDir(outputDir)
                    .setOpen(false)
                    /*XML 设置映射结果 ResultMap*/
                    .setBaseResultMap(true)
                    // 是否在xml中添加二级缓存配置,默认false
                    .setEnableCache(false)
                    /*XML 设置表列 ColumnList*/
                    .setBaseColumnList(true)
                    /*每次生成，是否覆盖之前的文件（慎重考虑啊）*/
                    .setFileOverride(true)
                    /*自定义文件命名，注意 %s 会自动填充表实体属性！*/
                    .setControllerName("%sController")
                    .setServiceName("%sService")
                    .setServiceImplName("%sServiceImpl")
                    .setEntityName("%s")
                    .setMapperName("%sMapper")
                    .setXmlName("%sMapper")
            ;

            //******************************数据源配置***************************************
            //数据库连接url
            String dbUrl = "jdbc:mysql://120.77.218.141:3306/gen?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull";
            //数据源配置
            DataSourceConfig dataSourceConfig = new DataSourceConfig();
            //数据库类型 枚举
            dataSourceConfig
                    //设置url
                    .setUrl(dbUrl)
                    //设置用户名
                    .setUsername("root")
                    //设置密码
                    .setPassword("123456")
                    //设置数据库驱动
                    .setDriverName("com.mysql.cj.jdbc.Driver")
                    .setDbQuery(new CustomMySqlQuery());
            dataSourceConfig.setDbType(dataSourceConfig.getDbType());

            //******************************策略配置******************************************************
            // 自定义需要填充的字段 数据库中的字段
            List<TableFill> tableFillList = new ArrayList<>();
            tableFillList.add(new TableFill(GeneratorConstant.CREATE_TIME, FieldFill.INSERT));
            tableFillList.add(new TableFill(GeneratorConstant.UPDATE_TIME, FieldFill.INSERT_UPDATE));

            //策略配置
            StrategyConfig strategyConfig = new StrategyConfig();
            strategyConfig
                    //全局大写命名是否开启
                    .setCapitalMode(true)
                    //【实体】是否为lombok模型
                    .setEntityLombokModel(true)
                    //表名生成策略 下划线转驼峰
                    .setNaming(NamingStrategy.underline_to_camel)
                    //自动填充设置
                    .setTableFillList(tableFillList)
                    .setTablePrefix("code_")
                    /* 生成指定的xxxController、、xxService等是否去掉数据库表名的前缀，如t_user -> user -> UserController*/
                    .setFieldPrefix("code_")
                    .setVersionFieldName("version")
                    //修改替换成你需要的表名，多个表名传数组
                    .setInclude(new String[]{"code_table_strategy_config"})
                    .setEntityBooleanColumnRemoveIsPrefix(true)
                    .setTablePrefix(null)
                    /*自定义实体，公共字段*/
                    .setSuperEntityColumns(null)
                    /*自定义 mapper 父类*/
                    .setSuperMapperClass(null)
                    .setSuperServiceImplClass("com.pig.easy.bpm.common.generator.BaseServiceImpl")
                    /*自定义 service 父类*/
                    .setSuperServiceClass("com.pig.easy.bpm.common.generator.BaseService")
                    /*自定义 controller 父类*/
//                    .setSuperControllerClass(null)
                    /*【实体】是否生成字段常量（默认 false）public static final String ID = "test_id";*/
                    .setEntityColumnConstant(false)//
                    /*【实体】是否为构建者模型（默认 false）public User setName(String name) {this.name = name; return this;}*/
                    .setEntityBuilderModel(true)
                    /*【实体】是否为lombok模型（默认 false）*/
                    .setEntityLombokModel(true)
            ;

            //集成注入设置
            InjectionConfig cfg = new InjectionConfig() {
                @Override
                public void initMap() {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put(GeneratorConstant.PRE_VIEW, false);
                    map.put(GeneratorConstant.SELECT_TEMPLATE_PATH_LIST, templateNames);
                    map.put("dtoResponse", DTO_RESPONSE);
                    map.put("dtoRequest", DTO_REQUEST);
                    map.put("voRequest", VO_REQUEST);
                    map.put("contentPath",contentPath);
                    this.setMap(map);
                }
            };
            List<FileOutConfig> focList = new ArrayList<>();
            focList.add(new FileOutConfig("/templates/entity.java.ftl") {
                @Override
                public String outputFile(com.baomidou.mybatisplus.generator.config.po.TableInfo tableInfo) {
                    // 自定义输入文件名称
                    return outputDir + File.separator + parentPackage.replaceAll("\\.", "\\\\") + File.separator + String.join(File.separator, Arrays.asList("entity"))
                            + File.separator + tableInfo.getEntityName() + "DO.java";
                }
            });
            focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
                @Override
                public String outputFile(com.baomidou.mybatisplus.generator.config.po.TableInfo tableInfo) {
                    // 自定义输入文件名称
                    return codePath + File.separator + String.join(File.separator, Arrays.asList("src", "main", "resources", "mapper"))
                            + "/" + tableInfo.getMapperName() + ".xml";
                }
            });
            focList.add(new FileOutConfig("/templates/queryVO.java.ftl") {
                @Override
                public String outputFile(com.baomidou.mybatisplus.generator.config.po.TableInfo tableInfo) {
                    return outputDir + File.separator + parentPackage.replaceAll("\\.", "\\\\") + File.separator + String.join(File.separator, Arrays.asList("vo", "request"))
                            + File.separator + tableInfo.getEntityName() + "QueryVO.java";
                }
            });
            focList.add(new FileOutConfig("/templates/saveOrUpdateVO.java.ftl") {
                @Override
                public String outputFile(com.baomidou.mybatisplus.generator.config.po.TableInfo tableInfo) {
                    return outputDir + File.separator + parentPackage.replaceAll("\\.", "\\\\") + File.separator + String.join(File.separator, Arrays.asList("vo", "request"))
                            + File.separator + tableInfo.getEntityName() + "SaveOrUpdateVO.java";
                }
            });
            focList.add(new FileOutConfig("/templates/queryDTO.java.ftl") {
                @Override
                public String outputFile(com.baomidou.mybatisplus.generator.config.po.TableInfo tableInfo) {
                    return outputDir + File.separator + parentPackage.replaceAll("\\.", "\\\\") + File.separator + String.join(File.separator, Arrays.asList("dto", "request"))
                            + File.separator + tableInfo.getEntityName() + "QueryDTO.java";
                }
            });
            focList.add(new FileOutConfig("/templates/saveOrUpdateDTO.java.ftl") {
                @Override
                public String outputFile(com.baomidou.mybatisplus.generator.config.po.TableInfo tableInfo) {
                    return outputDir + File.separator + parentPackage.replaceAll("\\.", "\\\\") + File.separator + String.join(File.separator, Arrays.asList("dto", "request"))
                            + File.separator + tableInfo.getEntityName() + "SaveOrUpdateDTO.java";
                }
            });
            focList.add(new FileOutConfig("/templates/dto.java.ftl") {
                @Override
                public String outputFile(com.baomidou.mybatisplus.generator.config.po.TableInfo tableInfo) {
                    return outputDir + File.separator + parentPackage.replaceAll("\\.", "\\\\") + File.separator + String.join(File.separator, Arrays.asList("dto", "response"))
                            + File.separator + tableInfo.getEntityName() + "DTO.java";
                }
            });
            focList.add(new FileOutConfig("/templates/api.ftl") {
                @Override
                public String outputFile(com.baomidou.mybatisplus.generator.config.po.TableInfo tableInfo) {
                    return vuePath + File.separator + "api" + File.separator + tableInfo.getEntityPath() + ".js";
                }
            });
            focList.add(new FileOutConfig("/templates/index.ftl") {
                @Override
                public String outputFile(com.baomidou.mybatisplus.generator.config.po.TableInfo tableInfo) {
                    return vuePath + File.separator + "views" + File.separator + tableInfo.getEntityPath() + File.separator  + "index.vue";
                }
            });
            cfg.setFileOutConfigList(focList);

            String mapperFolder = codePath + File.separator + String.join(File.separator, Arrays.asList("src", "main", "resources"));
            File mapperDir = new File(mapperFolder);
            mapperDir.mkdirs();

            CustomFreemarkerTemplateEngine customFreemarkerTemplateEngine = new CustomFreemarkerTemplateEngine();
            //注入全局设置
            new CustomAutoGenerator(null).setGlobalConfig(config)
                    //注入数据源配置
                    .setDataSource(dataSourceConfig)
                    //注入策略配置
                    .setStrategy(strategyConfig)
                    //设置包名信息
                    .setPackageInfo(
                            new PackageConfig()
                                    //提取公共父级包名
                                    .setParent(parentPackage)
                                    //设置controller信息
                                    .setController("controller")
//                                .setModuleName("dbConfig")
                                    .setMapper("mapper")
                                    .setServiceImpl("service.impl")
                                    .setService("service")
                                    //设置实体类信息
                                    .setEntity("entity")
//                                .setXml("mapper")
                    )
                    .setTemplateEngine(customFreemarkerTemplateEngine)
                    .setCfg(cfg)
                    //设置自定义模板
                    .setTemplate(
                            new TemplateConfig()
                                    //.setXml(null)//指定自定义模板路径, 位置：/resources/templates/entity2.java.ftl(或者是.vm)
                                    //注意不要带上.ftl(或者是.vm), 会根据使用的模板引擎自动识别
                                    // 自定义模板配置，模板可以参考源码 /mybatis-plus/src/main/resources/template 使用 copy
                                    // 至您项目 src/main/resources/template 目录下，模板名称也可自定义如下配置
                                    .setController("templates/controller.java")
                                    .setEntity(null)
                                    .setXml(null)
                                    .setMapper("templates/mapper.java")
                                    .setService("templates/service.java")
                                    .setServiceImpl("templates/serviceImpl.java")
                    )
                    //开始执行代码生成
                    .execute();


            System.out.println("customFreemarkerTemplateEngine.preViewMap = " + customFreemarkerTemplateEngine.preViewMap);
        }


    @Test
    void generator() {
        final Configuration cfg = new Configuration(Configuration.getVersion());
        cfg.setLocalizedLookup(false);
        cfg.setDefaultEncoding("utf-8");
        cfg.setTemplateLoader(new ClassTemplateLoader(GeneratorUtils.class.getClass(), "/templates/generator/front"));

        try {
            Template template = cfg.getTemplate("index.ftl");

            String tableName = "t_auto_create_table";
            TableInfo tableInfo = checkTableMapper.getTableInfo(tableName);
            TableConfig tableConfig = new TableConfig();
            BeanUtils.copyProperties(tableInfo, tableConfig);

            List<ColumnInfo> infoList = checkTableMapper.listTableColumn(tableName);

            String preFix = "t_";
            Map<String, Object> map = new HashMap<>(16);
            // 接口别名
            map.put("apiAlias", "");
            // 包名称
            map.put("package", "com.pig.easy.bpm");
            // 模块名称
            map.put("moduleName", "bpm");
            // 作者
            map.put("author", "zhuzl002");
            // 创建日期
            map.put("date", LocalDate.now().toString());
            if (StringUtils.isNotEmpty(preFix)) {
                try {
                    tableName = tableName.split(preFix)[1];
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            String className = GeneratorUtils.toCapitalizeCamelCase(tableName);
            String camelClassName = GeneratorUtils.toCamelCase(tableName);
            map.put("className", className);
            map.put("camelClassName", className);
            map.put("entity", className);

            List<TableFieldInfo> tableFieldInfos = new ArrayList<>();
            TableFieldInfo tableFieldInfo = new TableFieldInfo();
            for (ColumnInfo columnInfo : infoList ) {
                BeanUtils.copyProperties(columnInfo,tableFieldInfo);
                tableFieldInfo.setColumn(columnInfo.getColumnName());
                tableFieldInfo.setProperty(GeneratorUtils.toCamelCase(columnInfo.getColumnName()));
                tableFieldInfo.setPropertyType(GeneratorUtils.MYSQL_TYPE_TO_JAVA_TYPE_MAP.get(columnInfo.getDataType().toUpperCase()));
                tableFieldInfo.setPropertyTypeClass(GeneratorUtils.MYSQL_TYPE_TO_JAVA_TYPE_CLASS_MAP.get(columnInfo.getDataType().toUpperCase()));

                if(GeneratorUtils.PK.equals(tableFieldInfo.getColumnKey())){
                    tableConfig.setKeyColumn(tableFieldInfo.getColumn());
                    tableConfig.setKeyProperty(tableFieldInfo.getProperty());
                    tableConfig.setKeyType(tableFieldInfo.getPropertyTypeClass());
                }
                tableFieldInfos.add(tableFieldInfo);
            }

            tableConfig.setFields(tableFieldInfos);

            map.put("table",tableConfig);
           List<TableFieldInfo> queryFields = new ArrayList<TableFieldInfo>();
            queryFields.add(tableFieldInfos.get(0));
            map.put("queryFields",queryFields);
            String s = GeneratorUtils.process(template, map);
            System.out.println("s = " + s);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
