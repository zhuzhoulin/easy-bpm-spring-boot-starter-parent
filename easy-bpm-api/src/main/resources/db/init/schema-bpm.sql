/*
Navicat MySQL Data Transfer

Source Server         : zzlAliYun
Source Server Version : 50729
Source Database       : easy_bpm

Target Server Type    : MYSQL
Target Server Version : 50729
File Encoding         : 65001

Date: 2021-04-18 16:12:36
*/
CREATE DATABASE IF NOT EXISTS `easy_bpm` DEFAULT CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_general_ci;
USE `easy_bpm`;

-- ----------------------------
-- Table structure for bpm_apply
-- ----------------------------
DROP TABLE IF EXISTS `bpm_apply`;
CREATE TABLE `bpm_apply` (
  `apply_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `apply_sn` varchar(64) NOT NULL DEFAULT '' COMMENT '单据编号',
  `proc_inst_id` varchar(64) NOT NULL DEFAULT '' COMMENT '流程实例编号',
  `apply_title` varchar(255) NOT NULL DEFAULT '' COMMENT '申请标题',
  `apply_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '申请人工号',
  `apply_real_name` varchar(128) NOT NULL DEFAULT '' COMMENT '申请人姓名',
  `apply_dept_id` bigint(20) NOT NULL COMMENT '部门编号',
  `apply_dept_code` varchar(255) NOT NULL DEFAULT '' COMMENT '申请部门工号',
  `apply_dept_name` varchar(255) NOT NULL DEFAULT '' COMMENT '申请部门名称',
  `apply_company_id` bigint(20) NOT NULL COMMENT '申请人公司编号',
  `apply_company_code` varchar(255) NOT NULL DEFAULT '' COMMENT '申请人公司编码',
  `apply_company_name` varchar(255) NOT NULL DEFAULT '' COMMENT '申请人公司名称',
  `tenant_id` varchar(100) NOT NULL DEFAULT '' COMMENT '租户编号',
  `process_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '流程编号',
  `process_key` varchar(64) NOT NULL DEFAULT '' COMMENT '流程编码',
  `process_name` varchar(255) NOT NULL DEFAULT '' COMMENT '流程名称',
  `definition_id` varchar(128) NOT NULL DEFAULT '' COMMENT '发起流程版本ID',
  `form_key` varchar(64) NOT NULL DEFAULT '' COMMENT '表单KEY',
  `system` varchar(64) NOT NULL DEFAULT '' COMMENT '来源系统',
  `platform` varchar(64) NOT NULL DEFAULT '' COMMENT '来源编码',
  `parent_apply_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '父级申请编码',
  `apply_status` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '流程状态 1 草稿 2 审批中 3 审批通过 4 审批拒绝  5 已取消',
  `pre_process_link` varchar(1000) NOT NULL DEFAULT '' COMMENT '演算审批链',
  `complete_time` datetime DEFAULT NULL COMMENT '审批完成时间',
  `remarks` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  `valid_state` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT '状态 1 有效 0 失效',
  `operator_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '操作人工号',
  `operator_name` varchar(128) NOT NULL DEFAULT '' COMMENT '操作人工姓名',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '申请时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`apply_id`),
  KEY `idx_apply_sn` (`apply_sn`) USING BTREE,
  KEY `idx_proc_inst_id` (`proc_inst_id`) USING BTREE,
  KEY `idx_apply_name` (`apply_user_id`,`apply_real_name`) USING BTREE,
  KEY `idx_dept_id` (`apply_dept_id`) USING BTREE,
  KEY `idx_company_id` (`apply_company_id`) USING BTREE,
  KEY `idx_tenant` (`tenant_id`) USING BTREE,
  KEY `idx_process_id` (`process_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='申请表';

-- ----------------------------
-- Table structure for bpm_company
-- ----------------------------
DROP TABLE IF EXISTS `bpm_company`;
CREATE TABLE `bpm_company` (
  `company_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '公司编码',
  `company_code` varchar(64) DEFAULT '' COMMENT '部门编码',
  `company_parent_id` bigint(20) DEFAULT '0' COMMENT '上级公司编号',
  `company_parent_code` varchar(64) DEFAULT '' COMMENT '上级公司编码',
  `company_name` varchar(128) DEFAULT '' COMMENT '公司名称',
  `company_abbr` varchar(64) DEFAULT '' COMMENT '公司简称',
  `company_level` tinyint(3) unsigned DEFAULT '1' COMMENT '公司承继',
  `company_order` smallint(5) DEFAULT '1' COMMENT '排序',
  `company_icon` varchar(255) DEFAULT '' COMMENT '公司展示图标',
  `company_url` varchar(255) DEFAULT '' COMMENT '公司展示url',
  `tenant_id` varchar(64) NOT NULL DEFAULT '' COMMENT '租户编号',
  `company_status` tinyint(3) DEFAULT '1' COMMENT '1 正常开业  2 拟筹 2 已关停',
  `valid_state` tinyint(2) unsigned DEFAULT '1' COMMENT '有效状态；0表示无效，1表示有效',
  `operator_id` bigint(20) DEFAULT '0' COMMENT '操作人工号',
  `operator_name` varchar(255) DEFAULT '' COMMENT '操作人姓名',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`company_id`),
  UNIQUE KEY `uniq_company_code` (`company_code`) USING BTREE,
  KEY `idx_tenant` (`tenant_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COMMENT='公司表';

-- ----------------------------
-- Table structure for bpm_config
-- ----------------------------
DROP TABLE IF EXISTS `bpm_config`;
CREATE TABLE `bpm_config` (
  `config_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '配置编码',
  `config_code` varchar(64) DEFAULT '' COMMENT '模板编号',
  `config_name` varchar(255) DEFAULT '' COMMENT '模板名称',
  `template_id` bigint(20) unsigned DEFAULT NULL COMMENT '模板编号',
  `config_key` varchar(64) DEFAULT '' COMMENT '模板key',
  `config_value` varchar(255) DEFAULT '' COMMENT '模板值',
  `config_type` varchar(255) DEFAULT '' COMMENT '模板类型',
  `tenant_id` varchar(128) DEFAULT '' COMMENT '租户编号',
  `remarks` varchar(255) DEFAULT '' COMMENT '备注',
  `valid_state` tinyint(3) unsigned DEFAULT '1' COMMENT '状态 1 有效 0 失效',
  `operator_id` bigint(20) unsigned DEFAULT NULL COMMENT '操作人工号',
  `operator_name` varchar(255) DEFAULT '' COMMENT '操作人姓名',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`config_id`),
  UNIQUE KEY `uniq_code` (`config_code`) USING BTREE,
  KEY `idx_tenant` (`template_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for bpm_config_template
-- ----------------------------
DROP TABLE IF EXISTS `bpm_config_template`;
CREATE TABLE `bpm_config_template` (
  `template_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '模板编号',
  `template_code` varchar(64) DEFAULT '' COMMENT '模板编号',
  `template_name` varchar(255) DEFAULT '' COMMENT '模板名称',
  `template_key` varchar(64) DEFAULT '' COMMENT '模板key',
  `template_value` varchar(255) DEFAULT '' COMMENT '模板值',
  `template_type` varchar(255) DEFAULT '' COMMENT '模板类型',
  `template_status` tinyint(2) unsigned DEFAULT '1' COMMENT '模板字段状态 1 未发布 2 已发布',
  `remarks` varchar(255) DEFAULT '' COMMENT '备注',
  `valid_state` tinyint(3) unsigned DEFAULT '1' COMMENT '状态 1 有效 0 失效',
  `operator_id` bigint(20) unsigned DEFAULT NULL COMMENT '操作人工号',
  `operator_name` varchar(255) DEFAULT '' COMMENT '操作人姓名',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`template_id`),
  UNIQUE KEY `uniq_code` (`template_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for bpm_dept
-- ----------------------------
DROP TABLE IF EXISTS `bpm_dept`;
CREATE TABLE `bpm_dept` (
  `dept_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '部门编号',
  `dept_code` varchar(64) NOT NULL DEFAULT '' COMMENT '部门编码',
  `dept_name` varchar(128) NOT NULL DEFAULT '' COMMENT '部门名称',
  `company_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '所属公司编码',
  `company_code` varchar(64) NOT NULL DEFAULT '' COMMENT '所属公司编码',
  `business_line` varchar(64) NOT NULL DEFAULT '' COMMENT '归属业务条线',
  `tenant_id` varchar(100) NOT NULL DEFAULT '' COMMENT '所属租户',
  `dept_parent_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '上级部门编号',
  `dept_parent_code` varchar(64) NOT NULL DEFAULT '' COMMENT '上级部门编码',
  `dept_level` tinyint(3) NOT NULL DEFAULT '1' COMMENT '部门层级',
  `dept_type` varchar(64) NOT NULL DEFAULT '' COMMENT '部门类型',
  `dept_type_code` varchar(64) NOT NULL DEFAULT '' COMMENT '部门类型编码',
  `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  `dept_order` smallint(5) unsigned NOT NULL DEFAULT '1' COMMENT '排序',
  `valid_state` tinyint(2) NOT NULL DEFAULT '1' COMMENT '状态 1 有效 0 失效',
  `operator_id` bigint(20) DEFAULT '0' COMMENT '操作人工号',
  `operator_name` varchar(255) DEFAULT '' COMMENT '操作人姓名',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`dept_id`),
  UNIQUE KEY `uniq_code` (`dept_code`) USING BTREE,
  KEY `idx_tenant` (`tenant_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COMMENT='部门表';

-- ----------------------------
-- Table structure for bpm_dict
-- ----------------------------
DROP TABLE IF EXISTS `bpm_dict`;
CREATE TABLE `bpm_dict` (
  `dict_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `dict_code` varchar(64) NOT NULL DEFAULT '' COMMENT '字典编码',
  `dict_name` varchar(100) NOT NULL DEFAULT '' COMMENT '字典名称',
  `dict_tree` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '是否为树 1 是 0 否',
  `tenant_id` varchar(100) NOT NULL DEFAULT '' COMMENT '租户编号',
  `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  `valid_state` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT '状态 1 有效 0 失效',
  `operator_id` bigint(20) unsigned DEFAULT '0' COMMENT '操作人工号',
  `operator_name` varchar(255) DEFAULT '' COMMENT '操作人姓名',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`dict_id`),
  UNIQUE KEY `uniq_dict_code` (`dict_code`) USING BTREE,
  KEY `idx_tenant` (`tenant_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COMMENT='字典表';

-- ----------------------------
-- Table structure for bpm_dict_item
-- ----------------------------
DROP TABLE IF EXISTS `bpm_dict_item`;
CREATE TABLE `bpm_dict_item` (
  `item_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `dict_id` bigint(20) unsigned NOT NULL COMMENT '字典编号',
  `item_value` varchar(100) NOT NULL DEFAULT '' COMMENT '字典项值',
  `item_text` varchar(100) NOT NULL DEFAULT '' COMMENT '字典项文本',
  `parent_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '父级编号',
  `tenant_id` varchar(64) NOT NULL DEFAULT '' COMMENT '租户编号',
  `sort` mediumint(8) unsigned NOT NULL DEFAULT '1' COMMENT '排序',
  `remark` varchar(255) DEFAULT '' COMMENT '备注',
  `valid_state` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT '有效状态；0表示无效，1表示有效',
  `operator_id` bigint(20) unsigned DEFAULT NULL COMMENT '操作人工号',
  `operator_name` varchar(100) DEFAULT '' COMMENT '操作人姓名',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`item_id`),
  KEY `idx_dict_id` (`dict_id`) USING BTREE,
  KEY `idx_tenant` (`tenant_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COMMENT='字典详细表';

-- ----------------------------
-- Table structure for bpm_event
-- ----------------------------
DROP TABLE IF EXISTS `bpm_event`;
CREATE TABLE `bpm_event` (
  `event_id` int(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '事件编号',
  `event_code` varchar(64) DEFAULT '' COMMENT '事件编码',
  `event_name` varchar(128) DEFAULT '' COMMENT '事件名称',
  `event_type` varchar(64) DEFAULT '' COMMENT '事件类型',
  `process_id` int(20) DEFAULT NULL COMMENT '流程编号',
  `node_id` varchar(255) DEFAULT '' COMMENT '触发节点编号列表',
  `request_url` varchar(255) DEFAULT '' COMMENT '请求地址',
  `request_method` varchar(64) DEFAULT '' COMMENT '请求方法',
  `request_headers` text COMMENT '请求头（json 格式）',
  `request_body` text COMMENT '请求体',
  `request_async` tinyint(2) DEFAULT '0' COMMENT '是否异步请求 0同步1异步 ',
  `response_save` tinyint(2) DEFAULT '1' COMMENT '1 保存返回结果至流程变量 0 不保存结果至流程变量',
  `response_var_name` varchar(64) DEFAULT '' COMMENT '接收返回类型变量名称',
  `order_no` int(10) DEFAULT '1' COMMENT '序号',
  `tenant_id` varchar(100) DEFAULT '' COMMENT '租户编号',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `valid_state` tinyint(2) DEFAULT '1' COMMENT '状态码 1有效 0 失效',
  `operator_id` int(20) DEFAULT NULL COMMENT '操作人工号',
  `operator_name` varchar(128) DEFAULT NULL COMMENT '操作人姓名',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`event_id`),
  UNIQUE KEY `uniq_event_code` (`event_code`) USING BTREE,
  KEY `idx_process_id` (`process_id`) USING BTREE,
  KEY `idx_node_id` (`node_id`) USING BTREE,
  KEY `idx_event_type` (`event_type`) USING BTREE,
  KEY `idx_tenant` (`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for bpm_file
-- ----------------------------
DROP TABLE IF EXISTS `bpm_file`;
CREATE TABLE `bpm_file` (
  `file_id` int(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '编号',
  `file_instance_code` varchar(64) DEFAULT '' COMMENT '文件实例编号',
  `file_name` varchar(100) DEFAULT '' COMMENT '文件名称',
  `file_md5_name` varchar(255) DEFAULT '' COMMENT '加密后文件名称',
  `file_path` varchar(255) DEFAULT '' COMMENT '文件路径',
  `file_size` int(11) DEFAULT NULL COMMENT '文件大小',
  `file_extend` varchar(32) DEFAULT NULL COMMENT '文件后缀',
  `file_status` tinyint(2) unsigned DEFAULT '1' COMMENT '状态 1： 文件生成中 2：文件生成成功 3：文件生成失败',
  `file_system_code` varchar(50) DEFAULT '' COMMENT '所属系统',
  `file_paltform` varchar(50) DEFAULT '' COMMENT '所属平台',
  `file_service_name` varchar(100) DEFAULT '' COMMENT '所属服务名称',
  `file_method_name` varchar(100) DEFAULT '' COMMENT '所属于哪个方法',
  `file_owner_id` int(20) unsigned DEFAULT NULL COMMENT '文件所属人员',
  `file_owner_name` varchar(100) DEFAULT '' COMMENT '文件所属人姓名',
  `tenant_id` varchar(100) DEFAULT '' COMMENT '租户编号',
  `error_message` text COMMENT '错误日志',
  `valid_state` tinyint(2) DEFAULT '1' COMMENT '状态 1 有效 0 失效',
  `operator_id` int(20) DEFAULT NULL COMMENT '操作人工号',
  `operator_name` varchar(100) DEFAULT '' COMMENT '操作人姓名',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`file_id`),
  KEY `idx_system_code` (`file_system_code`) USING BTREE,
  KEY `idx_file_name` (`file_name`) USING BTREE,
  KEY `idx_instance_id` (`file_instance_code`) USING BTREE,
  KEY `idx_tenant` (`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for bpm_file_template
-- ----------------------------
DROP TABLE IF EXISTS `bpm_file_template`;
CREATE TABLE `bpm_file_template` (
  `tempalte_id` char(32) NOT NULL COMMENT 'GUID主键',
  `process_id` int(20) unsigned NOT NULL DEFAULT '0' COMMENT '流程编号',
  `tenant_id` varchar(64) NOT NULL DEFAULT '' COMMENT '租户编号',
  `file_name` varchar(255) DEFAULT '' COMMENT '模板文件名称',
  `file_md5_name` varchar(255) DEFAULT '' COMMENT '模板文件名称',
  `file_extend` varchar(255) DEFAULT '' COMMENT '文件后缀',
  `file_path` varchar(255) DEFAULT '' COMMENT '文件路径',
  `file_size` decimal(20,2) DEFAULT '0.00' COMMENT '文件大小',
  `remarks` varchar(255) DEFAULT '' COMMENT '备注',
  `sort` smallint(5) unsigned DEFAULT '1' COMMENT '排序值',
  `valid_state` tinyint(2) unsigned DEFAULT '1' COMMENT '状态 1 有效 0 失效',
  `operator_id` int(20) unsigned DEFAULT '0' COMMENT '操作人工号',
  `operator_name` varchar(128) DEFAULT '' COMMENT '操作人姓名',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后一次更新时间',
  PRIMARY KEY (`tempalte_id`),
  KEY `idx_process_id` (`process_id`) USING BTREE,
  KEY `idx_tenant_id` (`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='模板文件表';

-- ----------------------------
-- Table structure for bpm_form
-- ----------------------------
DROP TABLE IF EXISTS `bpm_form`;
CREATE TABLE `bpm_form` (
  `form_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '表单编号',
  `form_key` varchar(64) NOT NULL DEFAULT '' COMMENT '表单KEY',
  `form_name` varchar(100) NOT NULL DEFAULT '' COMMENT '表单名称',
  `tenant_id` varchar(100) NOT NULL DEFAULT '' COMMENT '租户编号',
  `form_type` tinyint(3) NOT NULL DEFAULT '1' COMMENT '表单类型 1 PC 表单 2 移动表单',
  `sort` smallint(5) NOT NULL DEFAULT '1' COMMENT '排序',
  `form_data` text COMMENT '表单json数据',
  `remarks` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  `valid_state` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT '状态 1 有效 0 失效',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `operator_id` bigint(20) DEFAULT '0' COMMENT '操作人工号',
  `operator_name` varchar(100) DEFAULT '' COMMENT '操作人姓名',
  PRIMARY KEY (`form_id`),
  UNIQUE KEY `uniq_form_key` (`form_key`) USING BTREE,
  KEY `idx_form_key` (`form_key`) USING BTREE,
  KEY `idx_tenant_id` (`tenant_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=243 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for bpm_form_data
-- ----------------------------
DROP TABLE IF EXISTS `bpm_form_data`;
CREATE TABLE `bpm_form_data` (
  `data_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '数据ID',
  `data_key` varchar(64) NOT NULL DEFAULT '' COMMENT '数据KEY',
  `data_name` varchar(255) NOT NULL DEFAULT '' COMMENT '数据中文名称',
  `string_value` varchar(4000) DEFAULT '' COMMENT '字符串数据值',
  `boolean_value` tinyint(1) DEFAULT NULL COMMENT 'boolean 值',
  `number_value` decimal(50,4) DEFAULT NULL COMMENT '数值值',
  `number_format` varchar(50) DEFAULT '' COMMENT '日期格式',
  `date_value` datetime DEFAULT NULL COMMENT '日期格式',
  `date_pattern` varchar(64) DEFAULT 'yyyy-MM-dd' COMMENT '日期格式',
  `select_value` varchar(50) DEFAULT '' COMMENT 'select 选中值',
  `select_item` varchar(2000) DEFAULT '' COMMENT 'SELECT 下拉选项',
  `text_value` longtext COMMENT '大字段',
  `data_type` varchar(64) DEFAULT '' COMMENT '数据类型',
  `form_id` bigint(20) DEFAULT NULL COMMENT '表单编号',
  `form_key` varchar(255) DEFAULT NULL COMMENT '表单KEY',
  `tenant_id` varchar(100) DEFAULT '' COMMENT '租户',
  `process_id` bigint(20) DEFAULT NULL COMMENT '流程编号',
  `apply_id` bigint(20) DEFAULT NULL COMMENT '申请编号',
  `proc_inst_id` varchar(64) DEFAULT NULL COMMENT '流程实例编号',
  `task_id` bigint(20) DEFAULT NULL COMMENT '任务编号',
  `valid_state` tinyint(2) unsigned DEFAULT '1' COMMENT '有效状态；0表示无效，1表示有效',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT 'create_time',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `operator_id` bigint(20) unsigned DEFAULT '0' COMMENT '操作人工号',
  `operator_name` varchar(64) DEFAULT '' COMMENT 'operator_name',
  PRIMARY KEY (`data_id`),
  KEY `idx_apply_id` (`apply_id`) USING BTREE,
  KEY `idx_tenant_id` (`tenant_id`) USING BTREE,
  KEY `idx_proc_inst_id` (`proc_inst_id`) USING BTREE,
  KEY `idx_process_id` (`process_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for bpm_form_data_0
-- ----------------------------
DROP TABLE IF EXISTS `bpm_form_data_0`;
CREATE TABLE `bpm_form_data_0` (
  `data_id` bigint(20) unsigned NOT NULL COMMENT '数据ID',
  `data_key` varchar(64) NOT NULL DEFAULT '' COMMENT '数据KEY',
  `data_name` varchar(255) NOT NULL DEFAULT '' COMMENT '数据中文名称',
  `string_value` varchar(4000) DEFAULT '' COMMENT '字符串数据值',
  `boolean_value` tinyint(1) DEFAULT NULL COMMENT 'boolean 值',
  `number_value` decimal(50,4) DEFAULT NULL COMMENT '数值值',
  `number_format` varchar(50) DEFAULT '' COMMENT '日期格式',
  `date_value` datetime DEFAULT NULL COMMENT '日期格式',
  `date_pattern` varchar(64) DEFAULT 'yyyy-MM-dd' COMMENT '日期格式',
  `select_value` varchar(50) DEFAULT '' COMMENT 'select 选中值',
  `select_item` varchar(2000) DEFAULT '' COMMENT 'SELECT 下拉选项',
  `text_value` longtext COMMENT '大字段',
  `data_type` varchar(64) DEFAULT '' COMMENT '数据类型',
  `form_id` bigint(20) DEFAULT NULL COMMENT '表单编号',
  `form_key` varchar(255) DEFAULT NULL COMMENT '表单KEY',
  `tenant_id` varchar(100) DEFAULT '' COMMENT '租户',
  `process_id` bigint(20) DEFAULT NULL COMMENT '流程编号',
  `apply_id` bigint(20) DEFAULT NULL COMMENT '申请编号',
  `proc_inst_id` varchar(64) DEFAULT NULL COMMENT '流程实例编号',
  `task_id` bigint(20) DEFAULT NULL COMMENT '任务编号',
  `valid_state` tinyint(2) unsigned DEFAULT '1' COMMENT '有效状态；0表示无效，1表示有效',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT 'create_time',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `operator_id` bigint(20) unsigned DEFAULT '0' COMMENT '操作人工号',
  `operator_name` varchar(64) DEFAULT '' COMMENT 'operator_name',
  PRIMARY KEY (`data_id`),
  KEY `idx_apply_id` (`apply_id`) USING BTREE,
  KEY `idx_tenant_id` (`tenant_id`) USING BTREE,
  KEY `idx_proc_inst_id` (`proc_inst_id`) USING BTREE,
  KEY `idx_process_id` (`process_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for bpm_form_data_1
-- ----------------------------
DROP TABLE IF EXISTS `bpm_form_data_1`;
CREATE TABLE `bpm_form_data_1` (
  `data_id` bigint(20) unsigned NOT NULL COMMENT '数据ID',
  `data_key` varchar(64) NOT NULL DEFAULT '' COMMENT '数据KEY',
  `data_name` varchar(255) NOT NULL DEFAULT '' COMMENT '数据中文名称',
  `string_value` varchar(4000) DEFAULT '' COMMENT '字符串数据值',
  `boolean_value` tinyint(1) DEFAULT NULL COMMENT 'boolean 值',
  `number_value` decimal(50,4) DEFAULT NULL COMMENT '数值值',
  `number_format` varchar(50) DEFAULT '' COMMENT '日期格式',
  `date_value` datetime DEFAULT NULL COMMENT '日期格式',
  `date_pattern` varchar(64) DEFAULT 'yyyy-MM-dd' COMMENT '日期格式',
  `select_value` varchar(50) DEFAULT '' COMMENT 'select 选中值',
  `select_item` varchar(2000) DEFAULT '' COMMENT 'SELECT 下拉选项',
  `text_value` longtext COMMENT '大字段',
  `data_type` varchar(64) DEFAULT '' COMMENT '数据类型',
  `form_id` bigint(20) DEFAULT NULL COMMENT '表单编号',
  `form_key` varchar(255) DEFAULT NULL COMMENT '表单KEY',
  `tenant_id` varchar(100) DEFAULT '' COMMENT '租户',
  `process_id` bigint(20) DEFAULT NULL COMMENT '流程编号',
  `apply_id` bigint(20) DEFAULT NULL COMMENT '申请编号',
  `proc_inst_id` varchar(64) DEFAULT NULL COMMENT '流程实例编号',
  `task_id` bigint(20) DEFAULT NULL COMMENT '任务编号',
  `valid_state` tinyint(2) unsigned DEFAULT '1' COMMENT '有效状态；0表示无效，1表示有效',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT 'create_time',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `operator_id` bigint(20) unsigned DEFAULT '0' COMMENT '操作人工号',
  `operator_name` varchar(64) DEFAULT '' COMMENT 'operator_name',
  PRIMARY KEY (`data_id`),
  KEY `idx_apply_id` (`apply_id`) USING BTREE,
  KEY `idx_tenant_id` (`tenant_id`) USING BTREE,
  KEY `idx_proc_inst_id` (`proc_inst_id`) USING BTREE,
  KEY `idx_process_id` (`process_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for bpm_history
-- ----------------------------
DROP TABLE IF EXISTS `bpm_history`;
CREATE TABLE `bpm_history` (
  `history_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '审批记录表',
  `apply_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '申请编号',
  `task_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '任务编号',
  `task_name` varchar(255) NOT NULL DEFAULT '' COMMENT '任务名称',
  `tenant_id` varchar(128) NOT NULL DEFAULT '' COMMENT '租户编号',
  `system` varchar(64) DEFAULT '' COMMENT '来源系统',
  `platform` varchar(64) DEFAULT '' COMMENT '来源平台',
  `approve_action_code` varchar(64) DEFAULT '' COMMENT '审批动作编码',
  `approve_action_name` varchar(255) DEFAULT '' COMMENT '审批动作名称',
  `approve_opinion` varchar(4000) DEFAULT '' COMMENT '审批意见',
  `approve_user_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '审批人工号',
  `approve_real_name` varchar(128) NOT NULL DEFAULT '' COMMENT '审批人姓名',
  `remarks` varchar(255) DEFAULT '' COMMENT '备注',
  `valid_state` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT '状态 1 有效 0 失效',
  `operator_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '操作人工号',
  `operator_name` varchar(255) NOT NULL DEFAULT '' COMMENT '操作人工姓名',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`history_id`),
  KEY `idx_apply_id` (`apply_id`) USING BTREE,
  KEY `idx_task_id` (`task_id`) USING BTREE,
  KEY `idx_tenant` (`tenant_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='审批历史表';

-- ----------------------------
-- Table structure for bpm_menu
-- ----------------------------
DROP TABLE IF EXISTS `bpm_menu`;
CREATE TABLE `bpm_menu` (
  `menu_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '编号',
  `menu_code` varchar(128) NOT NULL DEFAULT '' COMMENT '资源编码',
  `menu_name` varchar(128) NOT NULL DEFAULT '' COMMENT '菜单名称',
  `menu_icon` varchar(255) NOT NULL DEFAULT '' COMMENT '菜单编码',
  `menu_url` varchar(255) NOT NULL DEFAULT '' COMMENT '菜单URL',
  `menu_type` varchar(32) NOT NULL DEFAULT 'menu' COMMENT '菜单类型 menu: 菜单',
  `meta` varchar(255) NOT NULL DEFAULT '' COMMENT '元数据',
  `always_show` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '是否总是显示 1 是 0 否',
  `parent_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '上级编号 0为 1级',
  `tenant_id` varchar(128) NOT NULL DEFAULT '' COMMENT '租户编号',
  `component` varchar(255) NOT NULL DEFAULT '' COMMENT '组件地址',
  `hidden` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '是否隐藏 0 不隐藏 1 隐藏',
  `sort` smallint(5) unsigned NOT NULL DEFAULT '1' COMMENT '排序',
  `redirect` varchar(255) NOT NULL DEFAULT '' COMMENT '重定向值',
  `remarks` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  `valid_state` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT '状态 0 失效 1 有效',
  `operator_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '操作者工号',
  `operator_name` varchar(128) NOT NULL DEFAULT '' COMMENT '操作人名称',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`menu_id`),
  UNIQUE KEY `uniq_code` (`menu_code`) USING BTREE,
  KEY `idx_tenant` (`tenant_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8mb4 COMMENT='菜单表';

-- ----------------------------
-- Table structure for bpm_message_content
-- ----------------------------
DROP TABLE IF EXISTS `bpm_message_content`;
CREATE TABLE `bpm_message_content` (
  `content_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '通知编号',
  `content_code` varchar(128) NOT NULL DEFAULT '' COMMENT '内容编码',
  `tenant_id` varchar(128) NOT NULL DEFAULT '' COMMENT '租户编号',
  `message_title` varchar(255) NOT NULL DEFAULT '' COMMENT '通知标题',
  `message_content` text COMMENT '通知内容',
  `message_type_code` varchar(64) NOT NULL DEFAULT 'HTML' COMMENT '通知类型编号 ’HTML‘:网页,''TEXT''：文本',
  `message_system_code` varchar(255) NOT NULL DEFAULT '' COMMENT '通知系统',
  `message_platform` varchar(255) NOT NULL DEFAULT '' COMMENT '通知平台 ',
  `process_id` int(20) unsigned NOT NULL DEFAULT '0' COMMENT '流程编号',
  `default_falg` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '是否为默认版本 0 否，1 是',
  `event_codes` varchar(1000) NOT NULL DEFAULT '' COMMENT '触发事件编码',
  `event_names` varchar(1000) NOT NULL DEFAULT '' COMMENT '触发事件名称',
  `order` smallint(5) unsigned NOT NULL DEFAULT '1' COMMENT '排序',
  `remarks` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  `valid_state` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT '状态 1 有效 0 失效',
  `operator_id` int(20) unsigned NOT NULL DEFAULT '0' COMMENT '操作人工号',
  `operator_name` varchar(128) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '操作人工姓名',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`content_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='通知内容表';

-- ----------------------------
-- Table structure for bpm_message_white_list
-- ----------------------------
DROP TABLE IF EXISTS `bpm_message_white_list`;
CREATE TABLE `bpm_message_white_list` (
  `white_id` varchar(64) CHARACTER SET utf8 NOT NULL COMMENT '编号',
  `tenant_id` varchar(128) NOT NULL DEFAULT '' COMMENT '租户编号',
  `process_id` int(20) unsigned NOT NULL DEFAULT '0' COMMENT '作用于流程编号， 0为所有流程',
  `white_type_code` varchar(32) NOT NULL DEFAULT 'ALL' COMMENT '白名单类型 ALL：所有，APPROVE：审批，OVERTIME：超时',
  `white_list` varchar(2000) NOT NULL DEFAULT '' COMMENT '白名单',
  `order` smallint(5) unsigned NOT NULL DEFAULT '1' COMMENT '排序',
  `remarks` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  `valid_state` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT '状态 1 有效 0 失效',
  `operator_id` int(20) unsigned NOT NULL DEFAULT '0' COMMENT '操作人工号',
  `operator_name` varchar(128) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '操作人姓名',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`white_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='通知白名单';

-- ----------------------------
-- Table structure for bpm_node
-- ----------------------------
DROP TABLE IF EXISTS `bpm_node`;
CREATE TABLE `bpm_node` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `node_id` varchar(64) NOT NULL DEFAULT '' COMMENT '节点编号',
  `node_name` varchar(64) NOT NULL DEFAULT '' COMMENT '节点名称',
  `definition_id` varchar(64) NOT NULL DEFAULT '' COMMENT '版本号',
  `process_id` bigint(20) DEFAULT NULL COMMENT '流程编号',
  `process_key` varchar(64) NOT NULL DEFAULT '' COMMENT '流程key',
  `tenant_id` varchar(100) NOT NULL DEFAULT '' COMMENT '租户编号',
  `node_type` varchar(64) NOT NULL DEFAULT '' COMMENT '节点类型',
  `task_type` varchar(64) DEFAULT 'approve' COMMENT '任务类型 当前任务类型  start 起草 approve 审批  record  备案 archive 归档',
  `priority` smallint(5) unsigned NOT NULL DEFAULT '1' COMMENT '优先级',
  `form_key` varchar(64) NOT NULL DEFAULT '' COMMENT '关联表单KEY',
  `form_name` varchar(255) NOT NULL DEFAULT '' COMMENT '表单名称',
  `form_type` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT '1 内置表单, 2 外置表单',
  `user_id_list` varchar(255) NOT NULL DEFAULT '' COMMENT '节点人员编号列表',
  `user_name_list` varchar(255) NOT NULL DEFAULT '' COMMENT '节点人员名称列表，。多个人以 , 区分',
  `role_group_code` varchar(64) NOT NULL DEFAULT '' COMMENT '节点角色组编号',
  `role_group_name` varchar(255) NOT NULL DEFAULT '' COMMENT '角色组名称',
  `role_code` varchar(64) NOT NULL DEFAULT '' COMMENT '角色编码',
  `role_name` varchar(255) NOT NULL DEFAULT '' COMMENT '角色名称',
  `find_user_type` tinyint(4) NOT NULL DEFAULT '4' COMMENT '节点用户类型 组合方式：1  角色组 2. 角色 3. 固定人员 4. 前端指定人员 5:申请人 6:同节点人员',
  `combine_type` tinyint(2) unsigned DEFAULT '2' COMMENT '组合方式：1 正常(找不到节点人员提示异常) 2 正常（找不到节点人员就跳过当前环节） ',
  `assignee_field` varchar(64) DEFAULT '' COMMENT '用户节点人员分配字段名称',
  `select_path` tinyint(2) DEFAULT '0' COMMENT '是否选择路径 0 否 1 是',
  `handler_strategy` varchar(64) DEFAULT 'skip' COMMENT '节点处理策略 skip: 执行人为空跳过,admin: 为空时管理员处理,error:为空时报错',
  `relation_node_id` varchar(64) DEFAULT '' COMMENT '依赖节点',
  `action_list` varchar(255) DEFAULT 'pass,reject' COMMENT '动作集合',
  `skip_expression` varchar(1000) DEFAULT '' COMMENT '用户任务条件跳过表达式',
  `expression` varchar(1000) DEFAULT '' COMMENT '连线表达式',
  `source_ref` varchar(64) DEFAULT '' COMMENT '连线来源节点NodeId',
  `target_ref` varchar(64) DEFAULT '' COMMENT '连线目标节点NodeId',
  `sequential` varchar(32) DEFAULT 'parallel' COMMENT '用户任务 多实例属性 parallel 并行审批，sequential 串行审批',
  `proportion` varchar(4) DEFAULT '' COMMENT '通过比例',
  `custom_sql` text COMMENT '自定义查找人SQL',
  `custom_data` varchar(2000) DEFAULT '' COMMENT '自定义拓展数据集合JSON对象格式',
  `remarks` varchar(255) DEFAULT '' COMMENT '备注',
  `valid_state` tinyint(2) unsigned DEFAULT '1' COMMENT '状态 1 有效 0 失效',
  `operator_id` bigint(20) DEFAULT NULL COMMENT '操作人工号',
  `operator_name` varchar(64) DEFAULT '' COMMENT '操作人姓名',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_nid_pid` (`node_id`,`process_id`,`definition_id`) USING BTREE,
  KEY `idx_process_id` (`process_id`) USING BTREE,
  KEY `idx_process_key` (`process_key`) USING BTREE,
  KEY `idx_pid_nid` (`node_id`,`process_id`) USING BTREE,
  KEY `idx_tenant` (`tenant_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='流程节点表';

-- ----------------------------
-- Table structure for bpm_node_user
-- ----------------------------
DROP TABLE IF EXISTS `bpm_node_user`;
CREATE TABLE `bpm_node_user` (
  `node_user_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '节点人员名称',
  `apply_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '申请编号',
  `proc_inst_id` varchar(64) NOT NULL DEFAULT '' COMMENT '流程实例编号',
  `process_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '流程编号',
  `process_key` varchar(64) NOT NULL DEFAULT '' COMMENT '流程KEY',
  `node_id` varchar(64) NOT NULL DEFAULT '' COMMENT '节点编号',
  `node_name` varchar(64) NOT NULL DEFAULT '' COMMENT '节点名称',
  `parent_node_id` varchar(64) DEFAULT '' COMMENT '父节点编号',
  `parent_node_name` varchar(64) DEFAULT '' COMMENT '父节点名称',
  `definition_id` varchar(64) NOT NULL DEFAULT '' COMMENT '流程定义编号',
  `tenant_id` varchar(100) NOT NULL DEFAULT '' COMMENT '租户编号',
  `assignee_user_id_list` varchar(255) NOT NULL DEFAULT '' COMMENT '节点分配人员工号 多个人以 , 区分',
  `assignee_user_name_list` varchar(255) NOT NULL DEFAULT '' COMMENT '节点分配人员姓名 多个人以 , 区分',
  `skip` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '当处理策略为 节点找不到人时 跳过则为 1，否则为 0',
  `default_set_admin` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '当处理策略为 节点找不到人时 管理员处理则为 1，否则为 0',
  `error` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '当处理策略为 节点找不到人时 抛出异常处理则为 1，否则为 0',
  `remarks` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  `valid_state` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT '状态 1 有效 0 失效',
  `operator_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '操作人工号',
  `operator_name` varchar(255) NOT NULL DEFAULT '' COMMENT '操作人姓名',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`node_user_id`),
  KEY `idx_apply_id` (`apply_id`) USING BTREE,
  KEY `idx_proc_inst_id` (`proc_inst_id`) USING BTREE,
  KEY `idx_definition_id` (`definition_id`) USING BTREE,
  KEY `idx_tenant_id` (`tenant_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='节点人员表';

-- ----------------------------
-- Table structure for bpm_operator_log
-- ----------------------------
DROP TABLE IF EXISTS `bpm_operator_log`;
CREATE TABLE `bpm_operator_log` (
  `log_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '日志编号',
  `user_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '操作用户工号',
  `nickname` varchar(64) NOT NULL DEFAULT '' COMMENT '操作用户',
  `trace_id` varchar(32) NOT NULL DEFAULT '' COMMENT '追踪编号',
  `business_type` varchar(32) NOT NULL DEFAULT '' COMMENT '业务类型',
  `module_name` varchar(64) NOT NULL DEFAULT '' COMMENT '模块名称',
  `class_name` varchar(512) NOT NULL DEFAULT '' COMMENT '请求类名称',
  `method_name` varchar(512) NOT NULL DEFAULT '' COMMENT '请求方法名称',
  `request_method` varchar(32) NOT NULL DEFAULT '' COMMENT '请求方式(POST/GET)',
  `ip_address` varchar(512) NOT NULL DEFAULT '' COMMENT '操作地址',
  `detail_address` varchar(512) NOT NULL DEFAULT '' COMMENT '详细地址',
  `browser` varchar(255) NOT NULL DEFAULT '' COMMENT '浏览器',
  `os` varchar(255) NOT NULL DEFAULT '' COMMENT '操作系统',
  `operator_type` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT '操作类别0其它 1后台用户 2手机端用户',
  `param` varchar(2000) NOT NULL DEFAULT '' COMMENT '请求参数',
  `code` smallint(5) unsigned NOT NULL DEFAULT '200' COMMENT '状态码',
  `message` varchar(255) NOT NULL DEFAULT '' COMMENT '返回描述',
  `data` text NOT NULL COMMENT '返回值',
  `remarks` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  `valid_state` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT '有效状态；0表示无效，1表示有效',
  `operator_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '操作人工号',
  `operator_name` varchar(255) NOT NULL DEFAULT '' COMMENT '操作人姓名',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for bpm_process
-- ----------------------------
DROP TABLE IF EXISTS `bpm_process`;
CREATE TABLE `bpm_process` (
  `process_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '流程编码',
  `process_key` varchar(64) NOT NULL DEFAULT '' COMMENT '流程key',
  `process_name` varchar(255) NOT NULL DEFAULT '' COMMENT '流程名称',
  `process_menu_id` bigint(20) unsigned DEFAULT NULL COMMENT '流程归属菜单编号',
  `process_abbr` varchar(64) DEFAULT '' COMMENT '流程简称',
  `process_type` tinyint(2) unsigned DEFAULT '1' COMMENT '流程分类 1 正常',
  `company_id` bigint(20) DEFAULT NULL COMMENT '所属公司',
  `company_code` varchar(64) DEFAULT '' COMMENT '公司编码',
  `sort` smallint(6) unsigned DEFAULT '1' COMMENT '排序',
  `tenant_id` varchar(128) DEFAULT '' COMMENT '租户编号',
  `process_detail_id` bigint(20) unsigned DEFAULT '0' COMMENT '默认流程详细编号',
  `process_status` tinyint(3) unsigned DEFAULT '1' COMMENT '流程状态: 1 未发布 2 已发布',
  `remarks` varchar(255) DEFAULT '' COMMENT '流程备注',
  `valid_state` tinyint(2) DEFAULT '1' COMMENT '状态 1 有效 0 失效',
  `operator_id` bigint(20) DEFAULT NULL COMMENT '操作人工号',
  `operator_name` varchar(255) DEFAULT '' COMMENT '操作人姓名',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`process_id`),
  UNIQUE KEY `uniq_process_key` (`process_key`) USING BTREE,
  KEY `index_process_key` (`process_key`) USING BTREE,
  KEY `idx_tenant` (`tenant_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='流程表';

-- ----------------------------
-- Table structure for bpm_process_detail
-- ----------------------------
DROP TABLE IF EXISTS `bpm_process_detail`;
CREATE TABLE `bpm_process_detail` (
  `process_detail_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '流程详细编号',
  `tenant_id` varchar(128) NOT NULL DEFAULT '' COMMENT '租户编号',
  `process_id` bigint(20) unsigned NOT NULL DEFAULT '0',
  `process_xml` mediumtext COMMENT '流程XML格式数据',
  `definition_id` varchar(128) NOT NULL DEFAULT '' COMMENT '默认版本号',
  `apply_title_rule` varchar(255) DEFAULT '' COMMENT '申请标题规则',
  `apply_due_date` datetime DEFAULT NULL COMMENT '流程到期时间',
  `auto_complete_first_node` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT '是否自动完成第一个节点任务 1 是 0 否',
  `publish_status` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT '1 未发布 2 已发布 ',
  `main_version` tinyint(2) unsigned NOT NULL DEFAULT '2' COMMENT '1 默认版本 2 非默认版本',
  `remarks` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  `valid_state` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT '状态 1 有效 0 失效',
  `operator_id` bigint(20) unsigned NOT NULL DEFAULT '0',
  `operator_name` varchar(255) NOT NULL DEFAULT '' COMMENT '操作人姓名',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`process_detail_id`),
  KEY `idx_process_id` (`process_id`) USING BTREE,
  KEY `idx_def_id` (`definition_id`) USING BTREE,
  KEY `idx_tenant_id` (`tenant_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for bpm_process_rule
-- ----------------------------
DROP TABLE IF EXISTS `bpm_process_rule`;
CREATE TABLE `bpm_process_rule` (
  `rule_id` char(32) NOT NULL DEFAULT '' COMMENT '规则 guid',
  `rule_name` varchar(255) NOT NULL DEFAULT '' COMMENT '规则名称',
  `rule_code` varchar(128) NOT NULL DEFAULT '' COMMENT '规则编号',
  `rule_type` smallint(5) NOT NULL DEFAULT '1' COMMENT '规则类型 1 人员规则',
  `tenant_id` varchar(64) NOT NULL,
  `process_id` int(20) unsigned NOT NULL DEFAULT '0' COMMENT '流程编号',
  `role_group_id` int(20) unsigned NOT NULL DEFAULT '0' COMMENT '角色组编号',
  `role_id` int(20) unsigned NOT NULL DEFAULT '0' COMMENT '角色编号',
  `user_id` int(20) unsigned NOT NULL DEFAULT '0' COMMENT '用户编号',
  `rule_expression` varchar(255) NOT NULL DEFAULT '' COMMENT '规则表达式',
  `rule_action` varchar(255) NOT NULL DEFAULT '' COMMENT '规则动作',
  `rule_users` varchar(255) NOT NULL DEFAULT '' COMMENT '规则涉及人员（新增/减少）多个人以‘,’区分',
  `remarks` varchar(255) NOT NULL,
  `valid_state` tinyint(2) NOT NULL,
  `operator_id` int(20) NOT NULL,
  `operator_name` varchar(128) NOT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`rule_id`),
  UNIQUE KEY `uniq_rule_code` (`rule_code`) USING BTREE,
  KEY `idx_tenant` (`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for bpm_role
-- ----------------------------
DROP TABLE IF EXISTS `bpm_role`;
CREATE TABLE `bpm_role` (
  `role_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '角色编号',
  `role_code` varchar(64) NOT NULL DEFAULT '' COMMENT '角色编码',
  `role_name` varchar(255) NOT NULL DEFAULT '' COMMENT '角色名称',
  `role_level` tinyint(3) NOT NULL DEFAULT '1' COMMENT '角色级别',
  `role_abbr` varchar(64) DEFAULT '' COMMENT '角色简称',
  `role_alias_name` varchar(255) DEFAULT '' COMMENT '角色别名',
  `tenant_id` varchar(128) DEFAULT '' COMMENT '租户编号',
  `company_id` bigint(20) unsigned DEFAULT '0' COMMENT '归属公司编号',
  `dept_id` bigint(20) unsigned DEFAULT '0' COMMENT '归属部门编号',
  `remarks` varchar(255) DEFAULT '' COMMENT '备注',
  `valid_state` tinyint(3) unsigned DEFAULT '1' COMMENT '状态 1 有效 0 失效',
  `operator_id` bigint(20) unsigned DEFAULT NULL COMMENT '操作人工号',
  `operator_name` varchar(255) DEFAULT '' COMMENT '操作人姓名',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `uniq_code` (`role_code`) USING BTREE,
  KEY `idx_tenant` (`tenant_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- ----------------------------
-- Table structure for bpm_role_group
-- ----------------------------
DROP TABLE IF EXISTS `bpm_role_group`;
CREATE TABLE `bpm_role_group` (
  `role_group_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `role_group_code` varchar(64) NOT NULL DEFAULT '' COMMENT '角色组编码',
  `role_group_name` varchar(255) NOT NULL DEFAULT '' COMMENT '角色组名称',
  `role_group_abbr` varchar(64) NOT NULL DEFAULT '' COMMENT '角色组简称',
  `business_line` varchar(64) NOT NULL DEFAULT '' COMMENT '所属条线，如 level 2 会根据 条线 找 level 1',
  `role_group_level` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '角色组等级',
  `role_group_type` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '角色组类别',
  `tenant_id` varchar(128) NOT NULL DEFAULT '' COMMENT '租户编号',
  `remarks` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  `valid_state` tinyint(3) unsigned DEFAULT '1' COMMENT '状态 1 有效 0 失效',
  `operator_id` bigint(20) unsigned DEFAULT NULL COMMENT '操作人工号',
  `operator_name` varchar(255) DEFAULT '' COMMENT '操作人姓名',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`role_group_id`),
  UNIQUE KEY `uniq_code` (`role_group_code`) USING BTREE,
  KEY `idx_tenant` (`tenant_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COMMENT='角色组';

-- ----------------------------
-- Table structure for bpm_role_group_to_role
-- ----------------------------
DROP TABLE IF EXISTS `bpm_role_group_to_role`;
CREATE TABLE `bpm_role_group_to_role` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '编号',
  `role_group_id` bigint(20) unsigned DEFAULT NULL COMMENT '角色组编号',
  `role_id` bigint(20) unsigned DEFAULT NULL COMMENT '角色编号',
  `tenant_id` varchar(128) DEFAULT '' COMMENT '租户编号',
  `remarks` varchar(255) DEFAULT '' COMMENT '备注',
  `valid_state` tinyint(3) unsigned DEFAULT '1' COMMENT '状态 1 有效 0 失效',
  `operator_id` bigint(20) DEFAULT NULL,
  `operator_name` varchar(255) DEFAULT '' COMMENT '操作人姓名',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_tenant_id` (`tenant_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for bpm_sql_log
-- ----------------------------
DROP TABLE IF EXISTS `bpm_sql_log`;
CREATE TABLE `bpm_sql_log` (
  `log_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `log_type` varchar(32) NOT NULL DEFAULT '' COMMENT '操作类型',
  `log_sql` text NOT NULL COMMENT 'sql',
  `spend_time` bigint(10) NOT NULL DEFAULT '0' COMMENT '花费时间（ms）',
  `remarks` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  `valid_state` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT '有效状态；0表示无效，1表示有效',
  `operator_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '操作人工号',
  `operator_name` varchar(255) NOT NULL DEFAULT '' COMMENT '操作人姓名',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='SQL日志表';

-- ----------------------------
-- Table structure for bpm_tenant
-- ----------------------------
DROP TABLE IF EXISTS `bpm_tenant`;
CREATE TABLE `bpm_tenant` (
  `tenant_id` varchar(64) NOT NULL COMMENT '租户编号',
  `tenant_name` varchar(255) NOT NULL DEFAULT '' COMMENT '租户名称',
  `effective_start_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '有效期开始时间',
  `effective_end_time` datetime DEFAULT NULL COMMENT '有效期结束时间',
  `remarks` varchar(255) NOT NULL COMMENT '备注',
  `valid_state` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT '有效状态；0表示无效，1表示有效',
  `operator_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '操作人工号',
  `operator_name` varchar(255) NOT NULL DEFAULT '' COMMENT '操作人姓名',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='租户表';

-- ----------------------------
-- Table structure for bpm_user
-- ----------------------------
DROP TABLE IF EXISTS `bpm_user`;
CREATE TABLE `bpm_user` (
  `id` varchar(32) NOT NULL,
  `user_id` bigint(20) NOT NULL COMMENT '用户编号',
  `user_name` varchar(128) NOT NULL DEFAULT '' COMMENT '用户账户',
  `real_name` varchar(128) NOT NULL DEFAULT '' COMMENT '用户名称',
  `email` varchar(128) NOT NULL COMMENT '邮箱',
  `phone` varchar(32) DEFAULT NULL COMMENT '联系方式',
  `position_code` varchar(64) NOT NULL DEFAULT '' COMMENT '岗位编码',
  `position_name` varchar(64) NOT NULL DEFAULT '' COMMENT '岗位名称',
  `gender` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '性别，0表示未知，1表示男，2表示女',
  `avatar` varchar(255) DEFAULT '' COMMENT '头像图片链接',
  `birth_date` datetime DEFAULT NULL COMMENT '生日',
  `company_id` bigint(20) DEFAULT '0' COMMENT '公司编号',
  `dept_id` bigint(20) DEFAULT '0' COMMENT '部门编号',
  `tenant_id` varchar(100) DEFAULT '' COMMENT '当前用户活动租户编号',
  `password` varchar(64) NOT NULL DEFAULT '' COMMENT '密码',
  `entry_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '入职时间',
  `leave_time` datetime DEFAULT NULL COMMENT '离职时间',
  `hire_status` tinyint(2) DEFAULT '1' COMMENT '雇佣状态 1 在职 2 离职',
  `source` varchar(64) NOT NULL DEFAULT 'system' COMMENT '来源： system 系统，people：人工 ldap：ldap',
  `lock_status` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '是否锁定 1 锁定 0 未锁定',
  `valid_state` tinyint(2) DEFAULT '1' COMMENT '有效状态；0表示无效，1表示有效',
  `operator_id` bigint(20) unsigned DEFAULT '0' COMMENT '操作人工号',
  `operator_name` varchar(255) DEFAULT '' COMMENT '操作人姓名',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`) USING BTREE,
  KEY `idx_user_name` (`user_name`,`phone`) USING BTREE,
  KEY `idx_tenant` (`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for bpm_user_role
-- ----------------------------
DROP TABLE IF EXISTS `bpm_user_role`;
CREATE TABLE `bpm_user_role` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` bigint(20) unsigned DEFAULT NULL COMMENT '用户工号',
  `role_id` bigint(20) unsigned DEFAULT NULL COMMENT '角色编号',
  `alias_name` varchar(255) DEFAULT NULL COMMENT '用户角色 别名',
  `tenant_id` varchar(128) DEFAULT '' COMMENT '租户编号',
  `remarks` varchar(255) DEFAULT '' COMMENT '备注',
  `valid_state` tinyint(3) unsigned DEFAULT '1' COMMENT '状态 1 有效 0 失效',
  `operator_id` bigint(20) unsigned DEFAULT NULL COMMENT '操作人工号',
  `operator_name` varchar(255) DEFAULT '' COMMENT '操作人姓名',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_tenant_id` (`tenant_id`) USING BTREE,
  KEY `idx_user_id` (`user_id`) USING BTREE,
  KEY `idx_role_id` (`role_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for bpm_user_task
-- ----------------------------
DROP TABLE IF EXISTS `bpm_user_task`;
CREATE TABLE `bpm_user_task` (
  `task_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `act_task_id` varchar(64) DEFAULT '' COMMENT 'flowable 任务编号',
  `apply_id` bigint(20) unsigned DEFAULT NULL COMMENT '申请编号',
  `process_id` bigint(20) DEFAULT '0' COMMENT '流程编号',
  `proc_inst_id` varchar(64) DEFAULT '' COMMENT '流程实例编号',
  `task_type` varchar(64) DEFAULT '' COMMENT '用户类型',
  `tenant_id` varchar(128) DEFAULT '' COMMENT '所属租户',
  `task_name` varchar(255) DEFAULT '' COMMENT '任务名称',
  `task_node_code` varchar(64) DEFAULT '' COMMENT '任务对应节点编号',
  `parent_task_id` bigint(20) unsigned DEFAULT NULL COMMENT '父级任务编号',
  `task_status` tinyint(5) DEFAULT '1' COMMENT '任务状态 1  表示未认领 2 表示已认领 3 表示已完成 4表示已取消  5:找不到人员系统自动完成任务',
  `role_group_code` bigint(20) unsigned DEFAULT NULL COMMENT '角色组编号',
  `role_code` bigint(20) unsigned DEFAULT NULL COMMENT '角色编号',
  `role_name` varchar(255) DEFAULT '' COMMENT '角色名称',
  `task_owner_user_id` bigint(20) unsigned DEFAULT NULL COMMENT '任务归属人工号',
  `task_owner_real_name` varchar(255) DEFAULT '' COMMENT '任务归属人姓名',
  `task_assignee_user_id` bigint(20) unsigned DEFAULT NULL COMMENT '任务处理人工号',
  `task_assignee_real_name` varchar(255) DEFAULT '' COMMENT '任务处理人姓名',
  `task_priority` tinyint(3) DEFAULT '10' COMMENT '任务优先等级',
  `form_key` varchar(64) DEFAULT '' COMMENT '表单关联KEY',
  `form_type` tinyint(2) unsigned DEFAULT '1' COMMENT '1 内置表单 2 外部表单',
  `claim_time` datetime DEFAULT NULL COMMENT '任务认领时间',
  `due_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '任务到期时间',
  `approve_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '任务审批时间',
  `system` varchar(64) DEFAULT '' COMMENT '来源系统',
  `platform` varchar(64) DEFAULT '' COMMENT '来源平台',
  `remarks` varchar(255) DEFAULT '' COMMENT '任务备注',
  `valid_state` tinyint(3) DEFAULT '1' COMMENT '状态 1 有效 0 失效',
  `operator_id` bigint(20) DEFAULT NULL COMMENT 'operator_id',
  `operator_name` varchar(255) DEFAULT '' COMMENT '操作人工姓名',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`task_id`),
  KEY `idx_apply_id` (`apply_id`) USING BTREE,
  KEY `idx_proc_inst_id` (`proc_inst_id`) USING BTREE,
  KEY `idx_process_id` (`process_id`) USING BTREE,
  KEY `idx_tenant_id` (`tenant_id`) USING BTREE,
  KEY `idx_task_status` (`task_status`) USING BTREE,
  KEY `idx_user_id` (`task_owner_user_id`,`task_assignee_user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for bpm_variable_dict
-- ----------------------------
DROP TABLE IF EXISTS `bpm_variable_dict`;
CREATE TABLE `bpm_variable_dict` (
  `variable_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '编号 ',
  `process_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '流程编号',
  `tenant_id` varchar(128) NOT NULL DEFAULT '' COMMENT '租户编号',
  `data_key` varchar(255) NOT NULL DEFAULT '' COMMENT '字段英文名称',
  `data_name` varchar(255) NOT NULL DEFAULT '' COMMENT '字段中文名称',
  `data_type` varchar(255) NOT NULL DEFAULT '' COMMENT '数据类型',
  `check_rule` varchar(255) DEFAULT '' COMMENT '校验规则（填写JUEL表达式）',
  `special_value` varchar(255) DEFAULT '' COMMENT '特殊值1',
  `special_value2` varchar(255) DEFAULT '' COMMENT '特殊值2',
  `process_data` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '1 流程变量 0 非流程变量',
  `sort` smallint(5) unsigned NOT NULL DEFAULT '1' COMMENT '排序值',
  `allow_edit_node_id` varchar(255) DEFAULT '' COMMENT '允许编辑节点 默认发起人可以编辑字段',
  `hidden_node_id` varchar(255) DEFAULT '' COMMENT '不允许读取字段 默认所有节点都可以读取',
  `required_node_id` varchar(255) DEFAULT '' COMMENT '哪些节点当前字段必须传',
  `remarks` varchar(255) DEFAULT '' COMMENT '备注',
  `valid_state` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT '状态 1 有效 0 失效',
  `operator_id` bigint(20) unsigned DEFAULT '0' COMMENT '操作人工号',
  `operator_name` varchar(128) DEFAULT '' COMMENT '操作人姓名',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`variable_id`),
  KEY `idx_tenant_id` (`tenant_id`) USING BTREE,
  KEY `idx_process_id` (`process_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='变量表';
SET FOREIGN_KEY_CHECKS=1;
