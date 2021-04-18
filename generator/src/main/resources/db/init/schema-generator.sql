/*
Navicat MySQL Data Transfer

Source Server         : zzlAliYun
Source Server Version : 50729

Target Server Type    : MYSQL
Target Server Version : 50729
File Encoding         : 65001

Date: 2021-02-03 10:28:08
*/

CREATE DATABASE IF NOT EXISTS `easy_bpm_generator` DEFAULT CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_general_ci;
USE `easy_bpm_generator`;

-- ----------------------------
-- Table structure for code_column
-- ----------------------------
DROP TABLE IF EXISTS `code_column`;
CREATE TABLE `code_column` (
  `column_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '列序号',
  `db_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '数据源ID',
  `table_name` varchar(255) NOT NULL DEFAULT '' COMMENT '表名',
  `column_name` varchar(255) NOT NULL DEFAULT '' COMMENT '字段名',
  `collation` varchar(255) NOT NULL DEFAULT '' COMMENT '编码',
  `extra` varchar(255) NOT NULL DEFAULT '' COMMENT '额外参数',
  `privileges` varchar(255) NOT NULL DEFAULT '' COMMENT '权限',
  `default_value` varchar(255) DEFAULT '' COMMENT '默认值',
  `column_comment` varchar(255) DEFAULT NULL COMMENT '字段描述',
  `form_label` varchar(255) DEFAULT NULL COMMENT '标签名称',
  `property_type` varchar(255) NOT NULL DEFAULT '' COMMENT 'Java字段类型',
  `property_name` varchar(255) NOT NULL DEFAULT '' COMMENT '字段属性名',
  `column_type` varchar(255) NOT NULL DEFAULT '' COMMENT '字段类型',
  `key_flag` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '主键 1 是 0 否',
  `key_identity_flag` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '自增列 1 是 0 否',
  `not_null` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '必填 1 是 0 否',
  `query_flag` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '查询条件 1 是 0 否',
  `query_method` varchar(255) NOT NULL DEFAULT '' COMMENT '查询方式',
  `insert_flag` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT '插入是否显示 1 是 0 否',
  `update_flag` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT '更新是否显示 1 是 0 否',
  `list_flag` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT '查询是否显示 1 是 0 否',
  `download_flag` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT '下载是否显示 1 是 0 否',
  `validate_type` varchar(64) NOT NULL DEFAULT '' COMMENT '校验类型',
  `validate_max_length` varchar(128) NOT NULL DEFAULT '' COMMENT '校验 最大长度',
  `validate_min_length` varchar(128) NOT NULL DEFAULT '' COMMENT '校验 最小长度',
  `validate_max_value` varchar(128) NOT NULL DEFAULT '' COMMENT '校验 最大值',
  `validate_min_value` varchar(128) NOT NULL DEFAULT '' COMMENT '校验 最小值',
  `form_type` varchar(255) NOT NULL DEFAULT '' COMMENT '表单类型',
  `dict_code` varchar(255) NOT NULL DEFAULT '' COMMENT '关联字典编码',
  `dict_name` varchar(255) NOT NULL DEFAULT '' COMMENT '关联字典名称',
  `tenant_id` varchar(64) NOT NULL DEFAULT '' COMMENT '租户编号',
  `remarks` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  `valid_state` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT '状态 1 有效 0 失效',
  `operator_id` int(11) NOT NULL DEFAULT '0' COMMENT '操作人工号',
  `operator_name` varchar(128) NOT NULL DEFAULT '' COMMENT '操作人姓名',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`column_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='字段表';

-- ----------------------------
-- Table structure for code_db_config
-- ----------------------------
DROP TABLE IF EXISTS `code_db_config`;
CREATE TABLE `code_db_config` (
  `db_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '数据源编号',
  `db_name` varchar(128) NOT NULL DEFAULT '' COMMENT '数据源名称',
  `db_url` varchar(2000) NOT NULL DEFAULT '' COMMENT 'url',
  `db_username` varchar(128) NOT NULL DEFAULT '' COMMENT '用户名',
  `db_password` varchar(255) NOT NULL DEFAULT '' COMMENT '密码',
  `db_type` varchar(255) NOT NULL DEFAULT '' COMMENT '数据源类型',
  `db_catalog` varchar(255) NOT NULL DEFAULT '' COMMENT '登记目录',
  `db_schema_name` varchar(255) NOT NULL DEFAULT '' COMMENT '数据源库名',
  `db_driver_name` varchar(255) NOT NULL DEFAULT '' COMMENT '驱动名称',
  `tenant_id` varchar(128) NOT NULL DEFAULT '' COMMENT '租户编号',
  `remarks` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  `valid_state` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '状态 1 有效 0 失效',
  `operator_id` int(20) unsigned NOT NULL DEFAULT '0' COMMENT '操作人工号',
  `operator_name` varchar(255) NOT NULL DEFAULT '' COMMENT '操作人姓名',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`db_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='数据源表';

-- ----------------------------
-- Table structure for code_table_strategy_config
-- ----------------------------
DROP TABLE IF EXISTS `code_table_strategy_config`;
CREATE TABLE `code_table_strategy_config` (
  `config_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '配置编号',
  `db_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '数据源ID',
  `table_name` varchar(100) NOT NULL DEFAULT '' COMMENT '表名称',
  `author` varchar(128) NOT NULL DEFAULT '' COMMENT '作者',
  `context_path` varchar(128) NOT NULL DEFAULT '' COMMENT '项目路径',
  `module_name` varchar(128) NOT NULL DEFAULT '' COMMENT '模块名称',
  `parent_package` varchar(512) NOT NULL DEFAULT '' COMMENT '公共父级包名',
  `table_prefix` varchar(128) NOT NULL DEFAULT '' COMMENT '表名前缀',
  `field_prefix` varchar(128) NOT NULL DEFAULT '' COMMENT '字段前缀',
  `project_path` varchar(2000) NOT NULL DEFAULT '' COMMENT '后端路径',
  `vue_path` varchar(2000) NOT NULL DEFAULT '' COMMENT '前端路径',
  `override` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '是否覆盖 1 是 0 否',
  `parent_menu_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '父级菜单编号',
  `controller_name` varchar(255) NOT NULL DEFAULT '' COMMENT 'controller 命名规则',
  `service_impl_name` varchar(255) NOT NULL DEFAULT '' COMMENT 'impl命名规则',
  `service_name` varchar(255) NOT NULL DEFAULT '' COMMENT 'service 命名规则',
  `xml_name` varchar(255) NOT NULL DEFAULT '' COMMENT 'xml 命名规则',
  `mapper_name` varchar(255) NOT NULL DEFAULT '' COMMENT 'mapper 命名规则',
  `entity_name` varchar(255) NOT NULL DEFAULT '' COMMENT '实体类 命名规则',
  `super_entity_class` varchar(255) NOT NULL DEFAULT '' COMMENT '自定义实体父类',
  `super_mapper_class` varchar(255) NOT NULL DEFAULT '' COMMENT '自定义 mapper 父类',
  `super_service_class` varchar(255) NOT NULL DEFAULT '' COMMENT 'service 父类',
  `super_service_impl_class` varchar(255) NOT NULL DEFAULT '' COMMENT 'service 实现类父类',
  `super_controller_class` varchar(255) NOT NULL DEFAULT '' COMMENT 'controller 父类',
  `tenant_id` varchar(128) NOT NULL DEFAULT '' COMMENT '租户编号',
  `remarks` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  `valid_state` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '状态 1 有效 0 失效',
  `operator_id` int(20) unsigned NOT NULL DEFAULT '0' COMMENT '操作人工号',
  `operator_name` varchar(255) NOT NULL DEFAULT '' COMMENT '操作人姓名',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`config_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='配置策略表';

-- ----------------------------
-- Table structure for code_template
-- ----------------------------
DROP TABLE IF EXISTS `code_template`;
CREATE TABLE `code_template` (
  `template_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '模板编号',
  `template_name` varchar(100) NOT NULL DEFAULT '' COMMENT '模板名称',
  `template_content` text NOT NULL COMMENT '模板内容',
  `template_type` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT '模板类型 1 后端 0 前端',
  `output_file_name` varchar(2000) NOT NULL DEFAULT '' COMMENT '自定义输入文件名称',
  `template_path` varchar(2000) NOT NULL DEFAULT '' COMMENT '模板路径',
  `template_group_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '模板组编号',
  `tenant_id` varchar(128) NOT NULL DEFAULT '' COMMENT '租户编号',
  `remarks` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  `valid_state` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '状态 1 有效 0 失效',
  `operator_id` int(20) unsigned NOT NULL DEFAULT '0' COMMENT '操作人工号',
  `operator_name` varchar(255) NOT NULL DEFAULT '' COMMENT '操作人姓名',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`template_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COMMENT='模板表';

-- ----------------------------
-- Table structure for code_template_group
-- ----------------------------
DROP TABLE IF EXISTS `code_template_group`;
CREATE TABLE `code_template_group` (
  `template_group_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '模板组编号',
  `template_group_code` varchar(128) NOT NULL DEFAULT '' COMMENT '模板组编码',
  `template_group_name` varchar(100) NOT NULL DEFAULT '' COMMENT '模板组名称',
  `tenant_id` varchar(128) NOT NULL DEFAULT '' COMMENT '租户编号',
  `remarks` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  `valid_state` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '状态 1 有效 0 失效',
  `operator_id` int(20) unsigned NOT NULL DEFAULT '0' COMMENT '操作人工号',
  `operator_name` varchar(255) NOT NULL DEFAULT '' COMMENT '操作人姓名',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`template_group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='模板分组表';


-- ----------------------------
-- Table structure for code_version
-- ----------------------------
DROP TABLE IF EXISTS `code_version`;
CREATE TABLE `code_version` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '版本名称',
  `value` varchar(255) NOT NULL DEFAULT '' COMMENT '版本值',
  `remarks` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  `valid_state` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '状态 1 有效 0 失效',
  `operator_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '操作人工号',
  `operator_name` varchar(128) NOT NULL DEFAULT '' COMMENT '操作人姓名',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='版本表';

