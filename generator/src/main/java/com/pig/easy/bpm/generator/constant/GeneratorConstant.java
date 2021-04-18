package com.pig.easy.bpm.generator.constant;

import java.io.File;
import java.util.Arrays;

/**
 * todo:
 *
 * @author : zhoulin.zhu
 * @date : 2021/2/20 16:50
 */
public class GeneratorConstant {

    /** 默认父包名 */
    public static final String DEFAYLT_PARENT_PACKAGE = "com.pig.easy.bpm.generator";
    /** 默认作者 */
    public static final String DEFAYLT_AUTHOR = "pig";

    public static final  String JAVA_TMPDIR = "java.io.tmpdir";

    /** 后端项目路径 */
    public static final  String CODE_PATH = JAVA_TMPDIR + "/project";

    /** 前端项目路径 */
    public static final  String VUE_PATH = JAVA_TMPDIR + "/vue";

    public static final String  SRC_MAIN_JAVA = String.join(File.separator, Arrays.asList("src", "main", "java"));

    /** 命名规则 */
    public static final  String CONTROLLER_NAME = "%sController";

    public static final  String SERVICE_NAME = "%sService";

    public static final  String ENTITY_NAME = "%s";

    public static final  String SERVICE_IMPL_NAME = "%sServiceImpl";

    public static final  String MAPPER_NAME = "%sMapper";

    public static final  String XML_NAME = "%sMapper";

    public static final String PRE_VIEW = "preView";

    public static final String SELECT_TEMPLATE_PATH_LIST = "selectTemplatePathList";

    public static final String CREATE_TIME ="create_time";

    public static final String UPDATE_TIME ="update_time";

    public static final String DDL = "ddl";

    public static final String CREATE_TABLE = "Create Table";

    public static final String PATH = "_path";

    public static final String ZIP = "zip";

}
