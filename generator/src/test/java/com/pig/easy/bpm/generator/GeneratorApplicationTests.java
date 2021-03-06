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


            //????????????
            GlobalConfig config = new GlobalConfig();
            //??????????????????AR??????
            config.setActiveRecord(true)
                    //???????????????????????????
                    .setAuthor("pig")
                    //???????????????????????????
                    .setOutputDir(outputDir)
                    .setOpen(false)
                    /*XML ?????????????????? ResultMap*/
                    .setBaseResultMap(true)
                    // ?????????xml???????????????????????????,??????false
                    .setEnableCache(false)
                    /*XML ???????????? ColumnList*/
                    .setBaseColumnList(true)
                    /*???????????????????????????????????????????????????????????????*/
                    .setFileOverride(true)
                    /*?????????????????????????????? %s ?????????????????????????????????*/
                    .setControllerName("%sController")
                    .setServiceName("%sService")
                    .setServiceImplName("%sServiceImpl")
                    .setEntityName("%s")
                    .setMapperName("%sMapper")
                    .setXmlName("%sMapper")
            ;

            //******************************???????????????***************************************
            //???????????????url
            String dbUrl = "jdbc:mysql://120.77.218.141:3306/gen?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull";
            //???????????????
            DataSourceConfig dataSourceConfig = new DataSourceConfig();
            //??????????????? ??????
            dataSourceConfig
                    //??????url
                    .setUrl(dbUrl)
                    //???????????????
                    .setUsername("root")
                    //????????????
                    .setPassword("123456")
                    //?????????????????????
                    .setDriverName("com.mysql.cj.jdbc.Driver")
                    .setDbQuery(new CustomMySqlQuery());
            dataSourceConfig.setDbType(dataSourceConfig.getDbType());

            //******************************????????????******************************************************
            // ?????????????????????????????? ?????????????????????
            List<TableFill> tableFillList = new ArrayList<>();
            tableFillList.add(new TableFill(GeneratorConstant.CREATE_TIME, FieldFill.INSERT));
            tableFillList.add(new TableFill(GeneratorConstant.UPDATE_TIME, FieldFill.INSERT_UPDATE));

            //????????????
            StrategyConfig strategyConfig = new StrategyConfig();
            strategyConfig
                    //??????????????????????????????
                    .setCapitalMode(true)
                    //?????????????????????lombok??????
                    .setEntityLombokModel(true)
                    //?????????????????? ??????????????????
                    .setNaming(NamingStrategy.underline_to_camel)
                    //??????????????????
                    .setTableFillList(tableFillList)
                    .setTablePrefix("code_")
                    /* ???????????????xxxController??????xxService?????????????????????????????????????????????t_user -> user -> UserController*/
                    .setFieldPrefix("code_")
                    .setVersionFieldName("version")
                    //?????????????????????????????????????????????????????????
                    .setInclude(new String[]{"code_table_strategy_config"})
                    .setEntityBooleanColumnRemoveIsPrefix(true)
                    .setTablePrefix(null)
                    /*??????????????????????????????*/
                    .setSuperEntityColumns(null)
                    /*????????? mapper ??????*/
                    .setSuperMapperClass(null)
                    .setSuperServiceImplClass("com.pig.easy.bpm.common.generator.BaseServiceImpl")
                    /*????????? service ??????*/
                    .setSuperServiceClass("com.pig.easy.bpm.common.generator.BaseService")
                    /*????????? controller ??????*/
//                    .setSuperControllerClass(null)
                    /*????????????????????????????????????????????? false???public static final String ID = "test_id";*/
                    .setEntityColumnConstant(false)//
                    /*????????????????????????????????????????????? false???public User setName(String name) {this.name = name; return this;}*/
                    .setEntityBuilderModel(true)
                    /*?????????????????????lombok??????????????? false???*/
                    .setEntityLombokModel(true)
            ;

            //??????????????????
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
                    // ???????????????????????????
                    return outputDir + File.separator + parentPackage.replaceAll("\\.", "\\\\") + File.separator + String.join(File.separator, Arrays.asList("entity"))
                            + File.separator + tableInfo.getEntityName() + "DO.java";
                }
            });
            focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
                @Override
                public String outputFile(com.baomidou.mybatisplus.generator.config.po.TableInfo tableInfo) {
                    // ???????????????????????????
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
            //??????????????????
            new CustomAutoGenerator(null).setGlobalConfig(config)
                    //?????????????????????
                    .setDataSource(dataSourceConfig)
                    //??????????????????
                    .setStrategy(strategyConfig)
                    //??????????????????
                    .setPackageInfo(
                            new PackageConfig()
                                    //????????????????????????
                                    .setParent(parentPackage)
                                    //??????controller??????
                                    .setController("controller")
//                                .setModuleName("dbConfig")
                                    .setMapper("mapper")
                                    .setServiceImpl("service.impl")
                                    .setService("service")
                                    //?????????????????????
                                    .setEntity("entity")
//                                .setXml("mapper")
                    )
                    .setTemplateEngine(customFreemarkerTemplateEngine)
                    .setCfg(cfg)
                    //?????????????????????
                    .setTemplate(
                            new TemplateConfig()
                                    //.setXml(null)//???????????????????????????, ?????????/resources/templates/entity2.java.ftl(?????????.vm)
                                    //??????????????????.ftl(?????????.vm), ??????????????????????????????????????????
                                    // ???????????????????????????????????????????????? /mybatis-plus/src/main/resources/template ?????? copy
                                    // ???????????? src/main/resources/template ???????????????????????????????????????????????????
                                    .setController("templates/controller.java")
                                    .setEntity(null)
                                    .setXml(null)
                                    .setMapper("templates/mapper.java")
                                    .setService("templates/service.java")
                                    .setServiceImpl("templates/serviceImpl.java")
                    )
                    //????????????????????????
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
            // ????????????
            map.put("apiAlias", "");
            // ?????????
            map.put("package", "com.pig.easy.bpm");
            // ????????????
            map.put("moduleName", "bpm");
            // ??????
            map.put("author", "zhuzl002");
            // ????????????
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
