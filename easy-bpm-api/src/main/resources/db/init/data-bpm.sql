/*
Navicat MySQL Data Transfer

Source Server         : zzlAliYun
Source Server Version : 50729
Source Host           : 120.77.218.141:3306
Source Database       : easy_bpm

Target Server Type    : MYSQL
Target Server Version : 50729
File Encoding         : 65001

Date: 2021-04-18 16:33:36
*/

-- ----------------------------
-- Records of bpm_company
-- ----------------------------
INSERT INTO `bpm_company` VALUES ('1', 'pig:company:testCompany', '0', '', '测试集团', 'test', '1', '1', '', '', 'pig', '1', '1', '0', '', '2020-05-19 09:26:10', '2020-06-24 13:53:10');
INSERT INTO `bpm_company` VALUES ('2', 'pig:company:bondCompany', '1', 'pig:company:testCompany', '测试证券投资有限公司', 'bond', '1', '1', '', '', 'pig', '1', '1', '0', '', '2020-06-24 11:29:50', '2020-06-24 13:53:22');
INSERT INTO `bpm_company` VALUES ('3', 'pig:company:bankCompany', '1', 'pig:company:testCompany', '测试银行有限公司', 'bank', '1', '1', '', '', 'pig', '1', '1', '0', '', '2020-06-24 11:29:50', '2020-06-24 13:53:23');
INSERT INTO `bpm_company` VALUES ('4', 'pig:company:gdBranchCompany', '1', 'pig:company:testCompany', '测试集团广东分公司', 'gdBranch', '1', '1', '', '', 'pig', '1', '1', '0', '', '2020-06-24 11:33:46', '2020-07-30 14:29:41');

-- ----------------------------
-- Records of bpm_config
-- ----------------------------
INSERT INTO `bpm_config` VALUES ('1', 'config:pig:tenantId', '租户名称', '1', 'tenantId', 'pig', 'string', 'pig', '', '1', null, '', '2020-06-26 10:17:02', '2020-06-26 10:17:02');
INSERT INTO `bpm_config` VALUES ('2', 'config:pig:system', '来源系统', '2', 'system', 'bestBpmAdmin', 'string', 'pig', '', '1', null, '', '2020-06-26 10:17:45', '2020-06-26 10:17:45');
INSERT INTO `bpm_config` VALUES ('3', 'config:pig:paltform', '来源平台', '3', 'paltform', 'pc', 'string', 'pig', '', '1', null, '', '2020-06-26 10:17:48', '2020-06-26 10:17:48');
INSERT INTO `bpm_config` VALUES ('4', 'config:pig:enableNode', '是否启用node表', '4', 'enableNode', '0', 'boolean', 'pig', '', '1', null, '', '2020-06-26 10:17:50', '2020-06-26 10:17:50');
INSERT INTO `bpm_config` VALUES ('5', 'config:pig:flowAdmin', '流程管理员', '5', 'flowAdmin', '2,3,4', 'string', 'pig', '', '1', null, '', '2020-06-26 10:19:52', '2020-06-26 10:19:52');
INSERT INTO `bpm_config` VALUES ('6', 'config:pig:approvedNodeFill', '查看流程图时，已审批通过节点颜色填充色', '6', 'approvedNodeFill', 'white', 'string', 'pig', '', '1', null, '', '2020-07-05 18:00:20', '2020-07-05 18:00:20');
INSERT INTO `bpm_config` VALUES ('7', 'config:pig:approvedNodeStroke', '查看流程图时，已审批通过节点颜色填充色', '7', 'approvedNodeStroke', 'black', 'string', 'pig', '', '1', null, '', '2020-07-05 18:01:11', '2020-07-05 18:01:11');
INSERT INTO `bpm_config` VALUES ('8', 'config:pig:approvedNodeStrokenWidth', '查看流程图时，已审批通过节点颜色填充色', '8', 'approvedNodeStrokenWidth', '2', 'string', 'pig', '', '1', null, '', '2020-07-05 18:01:23', '2020-07-05 18:01:23');



-- ----------------------------
-- Records of bpm_config_template
-- ----------------------------
INSERT INTO `bpm_config_template` VALUES ('1', 'template:tenantId', '默认租户名称', 'tenantId', 'pig', 'string', '1', '默认租户名称', '1', null, '', '2020-05-21 03:14:44', '2020-05-21 03:14:44');
INSERT INTO `bpm_config_template` VALUES ('2', 'template:system', '默认来源系统', 'system', 'bestBpmAdmin', 'string', '1', '默认来源系统', '1', null, '', '2020-05-21 03:22:00', '2020-05-21 03:22:00');
INSERT INTO `bpm_config_template` VALUES ('3', 'template:paltForm', '默认来源平台', 'paltform', 'pc', 'string', '1', '默认来源平台', '1', null, '', '2020-05-21 07:15:38', '2020-05-21 07:15:38');
INSERT INTO `bpm_config_template` VALUES ('4', 'template:enableNode', '是否启用node表', 'node', '0', 'boolean', '1', '默认不启用', '1', null, '', '2020-06-19 06:17:52', '2020-06-19 06:17:52');
INSERT INTO `bpm_config_template` VALUES ('5', 'template:flowAdmin', '默认流程管理员', 'flowAdmin', '2,3,4', 'string', '1', '默认流程管理员', '1', null, '', '2020-06-26 10:20:05', '2020-06-26 10:20:05');
INSERT INTO `bpm_config_template` VALUES ('6', 'template:approvedNodeFill', '查看流程图时，已审批通过节点颜色填充色', 'approvedNodeFill', 'white', 'string', '1', '查看流程图时，已审批通过节点颜色填充色', '1', null, '', '2020-06-26 10:20:05', '2020-06-26 10:20:05');
INSERT INTO `bpm_config_template` VALUES ('7', 'template:approvedNodeStroke', '查看流程图时，已审批通过节点边框颜色', 'approvedNodeStrok', 'black', 'string', '1', '查看流程图时，已审批通过节点边框颜色', '1', null, '', '2020-07-05 17:58:07', '2020-07-05 17:58:07');
INSERT INTO `bpm_config_template` VALUES ('8', 'template:approvedNodeStrokenWidth', '查看流程图时，已审批通过节点边框宽度', 'approvedNodeStrok', '2', 'integer', '1', '查看流程图时，已审批通过节点边框宽度', '1', null, '', '2020-07-05 17:58:07', '2020-07-05 17:58:07');


-- ----------------------------
-- Records of bpm_dept
-- ----------------------------
INSERT INTO `bpm_dept` VALUES ('1', 'pig:dept:testCompany:tech', '总公司信息技术部', '1', 'pig:company:testCompany', 'tech', 'pig', '0', '', '1', '', '', '', '1', '1', '0', '', '2020-05-19 11:56:02', '2020-06-24 16:18:51');
INSERT INTO `bpm_dept` VALUES ('2', 'pig:dept:testCompany:generalManagerOffice', '总公司总经理室', '1', 'pig:company:testCompany', 'manager', 'pig', '0', '', '1', '', '', '', '1', '1', '0', '', '2020-06-24 11:39:04', '2020-06-24 16:18:57');
INSERT INTO `bpm_dept` VALUES ('3', 'pig:dept:testCompany:finance', '总公司财务部', '1', 'pig:company:testCompany', 'finance', 'pig', '0', '', '1', '', '', '', '1', '1', '0', '', '2020-06-24 11:39:04', '2020-06-24 16:19:05');
INSERT INTO `bpm_dept` VALUES ('4', 'pig:dept:gdBranchCompany:tech', '广东分公司信息技术部', '4', 'pig:company:gdBranchCompany', 'tech', 'pig', '0', '', '1', '', '', '', '1', '1', '0', '', '2020-05-19 11:56:02', '2020-06-24 16:19:08');
INSERT INTO `bpm_dept` VALUES ('5', 'pig:dept:gdBranchCompany:generalManagerOffice', '广东分公司总经理室', '4', 'pig:company:gdBranchCompany', 'manager', 'pig', '0', '', '1', '', '', '', '1', '1', '0', '', '2020-06-24 11:39:04', '2020-06-24 16:19:14');
INSERT INTO `bpm_dept` VALUES ('6', 'pig:dept:gdBranchCompany:finance', '广东分公司财务部', '4', 'pig:company:gdBranchCompany', 'finance', 'pig', '0', '', '1', '', '', '', '1', '1', '0', '', '2020-06-24 11:39:04', '2020-06-24 16:19:19');
INSERT INTO `bpm_dept` VALUES ('7', 'pig:dept:testCompany:tech:process', '流程管理室', '1', 'pig:company:testCompany', 'tech', 'pig', '1', '', '1', '', '', '', '1', '1', '0', '', '2020-05-19 11:56:02', '2020-07-31 10:59:52');

-- ----------------------------
-- Records of bpm_dict
-- ----------------------------
INSERT INTO `bpm_dict` VALUES ('1', 'pig:dict:validState', '状态', '0', 'pig', '状态', '1', '0', 'admin', '2020-06-27 12:06:03', '2020-06-27 11:11:27');
INSERT INTO `bpm_dict` VALUES ('2', 'pig:dict:processStatus', '流程状态', '0', 'pig', '流程状态', '1', '0', 'admin', '2020-06-27 20:39:56', '2020-06-27 20:39:56');
INSERT INTO `bpm_dict` VALUES ('3', 'pig:dict:ada', 'das', '0', 'pig', 'sdas', '1', '2', 'admin', '2020-07-02 18:35:35', '2020-07-02 18:28:24');
INSERT INTO `bpm_dict` VALUES ('4', 'pig:dict:ada1', 'das1', '0', 'pig', 'sdas', '1', '2', 'admin', '2020-07-02 18:35:36', '2020-07-02 18:29:58');
INSERT INTO `bpm_dict` VALUES ('5', 'pig:dict:gdgsdgds', 'dfdf', '0', 'pig', 'dgd', '1', '2', 'admin', '2020-07-02 18:35:37', '2020-07-02 18:32:34');
INSERT INTO `bpm_dict` VALUES ('6', 'pig:dict:tryrteyry', 'trr', '0', 'pig', 'ryrtyer', '1', '2', 'admin', '2020-07-02 18:35:38', '2020-07-02 18:33:37');
INSERT INTO `bpm_dict` VALUES ('7', 'pig:dict:YUTUTYU', 'YTUT', '0', 'pig', 'TUTYUT', '1', '2', '管理员', '2020-07-02 20:32:41', '2020-07-02 18:35:04');
INSERT INTO `bpm_dict` VALUES ('8', 'pig:dict:nodeAction', '审批动作', '0', 'pig', '审批动作', '1', '2', '管理员', '2020-08-15 00:04:43', '2020-08-03 17:39:25');
INSERT INTO `bpm_dict` VALUES ('9', 'pig:dict:menuType', '资源类型', '0', 'pig', '资源类型', '1', '2', '管理员', '2021-04-06 15:42:07', '2021-04-06 15:42:10');
INSERT INTO `bpm_dict` VALUES ('10', 'pig:dict:testTree', '测试树形字典', '0', 'pig', '', '1', '0', '', '2021-04-12 13:12:45', null);
INSERT INTO `bpm_dict` VALUES ('11', 'pig:dict:formType', '表单类型', '0', 'pig', '', '1', '0', '', '2021-04-12 13:21:26', null);
INSERT INTO `bpm_dict` VALUES ('12', 'organTree', '机构树', '1', 'pig', '', '1', '0', '', '2021-04-12 13:22:02', null);
INSERT INTO `bpm_dict` VALUES ('13', 'organDeptTree', '机构部门树', '1', 'pig', '', '1', '0', '', '2021-04-08 14:49:35', null);
INSERT INTO `bpm_dict` VALUES ('14', 'organUserTree', '机构人员树', '1', 'pig', '', '1', '0', '', '2021-04-08 14:48:36', null);
INSERT INTO `bpm_dict` VALUES ('15', 'pig:dict:publishStatus', '发布状态', '0', 'pig', '', '1', '0', '', '2021-04-12 13:22:19', null);
INSERT INTO `bpm_dict` VALUES ('16', 'pig:dict:nodeFormType', '节点表单类型', '0', 'pig', '', '1', '0', '', '2021-04-12 13:25:16', null);
INSERT INTO `bpm_dict` VALUES ('17', 'pig:dict:ruleType', '规则类型', '0', 'pig', '', '1', '0', '', '2021-04-12 13:25:02', null);
INSERT INTO `bpm_dict` VALUES ('18', 'pig:dict:processVarType', '变量类型', '0', 'pig', '', '1', '0', '', '2021-04-12 13:25:30', null);
INSERT INTO `bpm_dict` VALUES ('19', 'pig:dict:processDataType', '数据类型', '0', 'pig', '', '1', '0', '', '2021-04-12 13:25:43', null);

-- ----------------------------
-- Records of bpm_dict_item
-- ----------------------------
INSERT INTO `bpm_dict_item` VALUES ('1', '1', '1', '正常', '0', 'pig', '1', '1', '1', '2', 'admin', '2020-07-02 21:41:20', '2020-07-02 21:41:20');
INSERT INTO `bpm_dict_item` VALUES ('2', '1', '0', '停用', '0', 'pig', '1', '1', '1', '2', 'admin', '2020-07-02 21:41:21', '2020-07-02 21:41:21');
INSERT INTO `bpm_dict_item` VALUES ('3', '2', '1', '草稿', '0', 'pig', '1', '', '1', null, '', '2020-06-27 20:40:43', '2020-06-27 20:40:43');
INSERT INTO `bpm_dict_item` VALUES ('4', '2', '2', '审批中', '0', 'pig', '1', '', '1', null, '', '2020-06-27 20:40:59', '2020-06-27 20:40:59');
INSERT INTO `bpm_dict_item` VALUES ('5', '2', '3', '审批通过', '0', 'pig', '1', '', '1', null, '', '2020-06-27 20:41:17', '2020-06-27 20:41:17');
INSERT INTO `bpm_dict_item` VALUES ('6', '2', '4', '审批拒绝', '0', 'pig', '1', '', '1', null, '', '2020-06-27 20:42:13', '2020-06-27 20:42:13');
INSERT INTO `bpm_dict_item` VALUES ('7', '2', '5', '已取消', '0', 'pig', '1', '', '1', null, '', '2020-06-27 20:43:13', '2020-06-27 20:43:13');
INSERT INTO `bpm_dict_item` VALUES ('8', '7', '1', '1', '0', 'pig', '1', '1', '0', '2', '管理员', '2020-07-02 22:18:37', '2020-07-02 22:18:37');
INSERT INTO `bpm_dict_item` VALUES ('9', '7', '1', '2', '0', 'pig', '1', '发人人', '0', '2', '管理员', '2020-07-02 22:18:48', '2020-07-02 22:18:48');
INSERT INTO `bpm_dict_item` VALUES ('10', '7', '12', '二恶烷若翁人', '0', 'pig', '1', 'EQW', '1', '2', '管理员', '2020-07-02 22:42:15', '2020-07-02 22:42:15');
INSERT INTO `bpm_dict_item` VALUES ('11', '7', '2', 'test1', '0', 'pig', '1', '', '1', '2', '管理员', '2020-08-03 17:31:04', '2020-08-03 17:31:05');
INSERT INTO `bpm_dict_item` VALUES ('12', '7', '1', 'test1', '0', 'pig', '1', '', '1', '2', '管理员', '2020-08-03 17:31:10', '2020-08-03 17:31:10');
INSERT INTO `bpm_dict_item` VALUES ('13', '8', 'start', '发起', '0', 'pig', '1', '发起', '0', '2', '管理员', '2021-03-08 16:20:49', '2021-03-08 16:20:48');
INSERT INTO `bpm_dict_item` VALUES ('14', '8', 'draft', '草稿', '0', 'pig', '1', '草稿', '1', '2', '管理员', '2020-08-03 17:40:23', '2020-08-03 17:40:23');
INSERT INTO `bpm_dict_item` VALUES ('15', '8', 'pass', '审批通过', '0', 'pig', '1', '审批通过', '1', '2', '管理员', '2020-08-03 17:41:10', '2020-08-03 17:41:10');
INSERT INTO `bpm_dict_item` VALUES ('16', '8', 'reject', '拒绝', '0', 'pig', '1', '不同意', '1', '2', '管理员', '2020-08-03 17:41:49', '2020-08-03 17:41:49');
INSERT INTO `bpm_dict_item` VALUES ('17', '8', 'addSign', '加签', '0', 'pig', '1', '会签', '1', '2', '管理员', '2020-08-03 18:14:15', '2020-08-03 18:14:16');
INSERT INTO `bpm_dict_item` VALUES ('18', '8', 'addTempNode', '新增临时节点', '0', 'pig', '1', 'addTempNode', '1', '2', '管理员', '2020-08-03 17:42:35', '2020-08-03 17:42:36');
INSERT INTO `bpm_dict_item` VALUES ('19', '8', 'returnNode', '退回', '0', 'pig', '1', '退回', '1', '2', '管理员', '2020-08-03 17:42:52', '2020-08-03 17:42:53');
INSERT INTO `bpm_dict_item` VALUES ('20', '8', 'returnRandomNode', '自由跳转', '0', 'pig', '1', '自由跳转', '1', '2', '管理员', '2020-08-03 17:43:47', '2020-08-03 17:43:48');
INSERT INTO `bpm_dict_item` VALUES ('21', '8', 'counterSign', '会签', '0', 'pig', '1', '', '0', '2', '管理员', '2021-03-16 19:49:55', '2021-03-16 19:49:55');
INSERT INTO `bpm_dict_item` VALUES ('22', '9', 'menu', '菜单', '0', 'pig', '1', '菜单', '1', '2', '管理员', '2021-04-06 15:42:26', '2021-04-06 15:42:29');
INSERT INTO `bpm_dict_item` VALUES ('23', '9', 'component', '组件', '0', 'pig', '1', '组件', '1', '2', '管理员', '2021-04-06 15:42:49', '2021-04-06 15:42:52');
INSERT INTO `bpm_dict_item` VALUES ('24', '9', 'button', '按钮', '0', 'pig', '1', '按钮', '1', '2', '管理员', '2021-04-06 15:43:01', '2021-04-06 15:43:04');
INSERT INTO `bpm_dict_item` VALUES ('25', '10', '1', '测试节点一', '0', 'pig', '1', '', '1', null, '', '2021-04-07 12:37:00', '2021-04-07 11:46:21');
INSERT INTO `bpm_dict_item` VALUES ('26', '10', '2', '测试节点2', '0', 'pig', '1', '', '1', null, '', '2021-04-07 11:58:41', '2021-04-07 11:58:41');
INSERT INTO `bpm_dict_item` VALUES ('27', '10', '3', '测试节点3', '0', 'pig', '1', '', '1', null, '', '2021-04-07 12:20:25', '2021-04-07 12:20:25');
INSERT INTO `bpm_dict_item` VALUES ('28', '10', '4', '测试节点四', '0', 'pig', '1', '', '1', null, '', '2021-04-07 12:36:09', '2021-04-07 12:36:09');
INSERT INTO `bpm_dict_item` VALUES ('29', '10', '5', '测试节点5', '2', 'pig', '1', '', '1', null, '', '2021-04-07 15:14:58', '2021-04-07 15:14:59');
INSERT INTO `bpm_dict_item` VALUES ('30', '10', '6', '测试节点6', '0', 'pig', '1', '', '1', null, '', '2021-04-07 15:17:59', '2021-04-07 15:18:00');
INSERT INTO `bpm_dict_item` VALUES ('31', '10', '7', '测试节点7', '0', 'pig', '1', '', '1', null, '', '2021-04-07 15:19:12', '2021-04-07 15:19:12');
INSERT INTO `bpm_dict_item` VALUES ('32', '10', '8', '测试节点8', '7', 'pig', '1', '', '1', null, '', '2021-04-07 15:24:53', '2021-04-07 15:24:54');
INSERT INTO `bpm_dict_item` VALUES ('33', '11', '1', 'PC表单', '0', 'pig', '1', '', '1', null, '', '2021-04-07 20:02:39', '2021-04-07 20:02:40');
INSERT INTO `bpm_dict_item` VALUES ('34', '11', '2', '移动表单', '0', 'pig', '1', '', '1', null, '', '2021-04-08 10:13:06', '2021-04-08 10:13:07');
INSERT INTO `bpm_dict_item` VALUES ('35', '15', '1', '未发布', '0', 'pig', '1', '', '1', null, '', '2021-04-09 10:23:48', '2021-04-09 10:23:48');
INSERT INTO `bpm_dict_item` VALUES ('36', '15', '2', '已发布', '0', 'pig', '1', '', '1', null, '', '2021-04-09 10:24:09', '2021-04-09 10:23:58');
INSERT INTO `bpm_dict_item` VALUES ('37', '16', '1', '内置表单', '0', 'pig', '1', '', '1', null, '', '2021-04-09 16:51:16', '2021-04-09 16:51:17');
INSERT INTO `bpm_dict_item` VALUES ('38', '16', '2', '外置表单', '0', 'pig', '1', '', '1', null, '', '2021-04-09 16:51:26', '2021-04-09 16:51:27');
INSERT INTO `bpm_dict_item` VALUES ('39', '17', '1', '人员规则', '0', 'pig', '1', '', '1', null, '', '2021-04-09 18:16:25', '2021-04-09 18:16:26');
INSERT INTO `bpm_dict_item` VALUES ('40', '18', '1', '流程变量', '0', 'pig', '1', '', '1', null, '', '2021-04-11 21:08:12', '2021-04-11 21:08:12');
INSERT INTO `bpm_dict_item` VALUES ('41', '18', '0', '普通变量', '0', 'pig', '1', '', '1', null, '', '2021-04-11 21:08:22', '2021-04-11 21:08:23');
INSERT INTO `bpm_dict_item` VALUES ('42', '19', 'serializable', '对象', '0', 'pig', '1', '', '1', null, '', '2021-04-12 13:08:55', '2021-04-12 13:08:55');
INSERT INTO `bpm_dict_item` VALUES ('43', '19', 'java.lang.Integer', '整形', '0', 'pig', '1', '', '1', null, '', '2021-04-12 13:09:16', '2021-04-12 13:09:16');
INSERT INTO `bpm_dict_item` VALUES ('44', '19', 'java.lang.String', '字符串', '0', 'pig', '1', '', '1', null, '', '2021-04-12 13:09:37', '2021-04-12 13:09:37');
INSERT INTO `bpm_dict_item` VALUES ('45', '19', 'date', '日期', '0', 'pig', '1', '', '1', null, '', '2021-04-12 13:09:53', '2021-04-12 13:09:54');
INSERT INTO `bpm_dict_item` VALUES ('46', '19', 'select', '下拉列表', '0', 'pig', '1', '', '1', null, '', '2021-04-12 13:10:08', '2021-04-12 13:10:08');
INSERT INTO `bpm_dict_item` VALUES ('47', '19', 'decimal', '浮点型', '0', 'pig', '1', '', '1', null, '', '2021-04-12 13:10:23', '2021-04-12 13:10:23');
INSERT INTO `bpm_dict_item` VALUES ('48', '19', 'boolean', '布尔值', '0', 'pig', '1', '', '1', null, '', '2021-04-12 13:10:37', '2021-04-12 13:10:38');
INSERT INTO `bpm_dict_item` VALUES ('49', '8', 'personSign', '签名', '0', 'pig', '1', '', '1', null, '', '2021-04-13 18:48:53', '2021-04-13 18:48:23');
INSERT INTO `bpm_dict_item` VALUES ('50', '8', 'counterSign', '会签', '0', 'pig', '1', '', '1', null, '', '2021-04-13 18:50:07', '2021-04-13 18:50:07');


-- ----------------------------
-- Records of bpm_menu
-- ----------------------------
INSERT INTO `bpm_menu` VALUES ('1', 'pig:menu:dashboard', '首页', 'dashboard', '/dashboard', 'component', '', '0', '0', 'pig', 'Layout', '0', '1', '', '首页', '1', '2', '管理员', '2020-07-09 16:23:57', '2021-03-17 21:10:10');
INSERT INTO `bpm_menu` VALUES ('2', 'pig:menu:usertask', '用户任务', 'list', '/usertask', 'menu', '', '0', '0', 'pig', 'Layout', '0', '2', 'todo', '', '1', '2', '管理员', '2020-07-09 16:23:57', '2021-03-13 22:52:05');
INSERT INTO `bpm_menu` VALUES ('3', 'pig:menu:todo', '我的待办', 'tree', '/todo', 'menu', '', '0', '2', 'pig', 'usertask/todo', '0', '1', '', '', '1', '0', '', '2020-07-09 16:23:57', '2020-07-11 15:53:05');
INSERT INTO `bpm_menu` VALUES ('4', 'pig:menu:apply', '我的申请', 'table', '/apply', 'menu', '', '0', '2', 'pig', 'usertask/apply', '0', '2', '', '', '1', '0', '', '2020-07-09 16:23:57', '2020-07-11 08:47:21');
INSERT INTO `bpm_menu` VALUES ('5', 'pig:menu:draft', '我的草稿', 'tree', '/draft', 'menu', '', '0', '2', 'pig', 'usertask/draft', '0', '3', '', '', '1', '0', '', '2020-07-09 16:23:57', '2020-07-11 08:47:22');
INSERT INTO `bpm_menu` VALUES ('6', 'pig:menu:haveDo', '我的已办', 'table', '/haveDo', 'menu', '', '0', '2', 'pig', 'usertask/haveDo', '0', '4', '', '', '1', '0', '', '2020-07-09 16:30:18', '2020-07-11 08:47:23');
INSERT INTO `bpm_menu` VALUES ('8', 'pig:menu:formDesign', '表单设计器', 'form', '/form', 'menu', '', '0', '0', 'pig', 'Layout', '0', '3', 'formList', '', '1', '2', '管理员', '2020-07-09 16:23:57', '2021-03-13 18:14:03');
INSERT INTO `bpm_menu` VALUES ('9', 'pig:menu:process1', '流程设计器', 'tree', '/process', 'menu', '', '0', '0', 'pig', 'Layout', '0', '4', 'processList', '', '1', '0', '', '2020-07-09 16:23:57', '2020-08-12 23:28:23');
INSERT INTO `bpm_menu` VALUES ('10', 'pig:menu:api', 'API接口文档', 'link', 'http://120.77.218.141:9993/api/lowCode/swagger-ui.html', 'menu', '', '0', '14', 'pig', 'iframe', '0', '9999', '', '', '1', '0', '', '2020-07-09 16:38:42', '2021-04-16 11:12:43');
INSERT INTO `bpm_menu` VALUES ('13', 'pig:menu:setting', '系统管理', 'international', '/setting', 'menu', '', '0', '0', 'pig', 'Layout', '0', '5', 'dictList', '', '1', '2', '管理员', '2020-07-09 16:23:57', '2021-03-13 22:53:32');
INSERT INTO `bpm_menu` VALUES ('14', 'pig:menu:iframe', 'API', 'documentation', '/iframe', 'menu', '', '0', '0', 'pig', 'Layout', '0', '99', '', '', '1', '2', '管理员', '2020-07-09 16:23:57', '2021-03-13 22:55:06');
INSERT INTO `bpm_menu` VALUES ('16', 'pig:menu:formBuild', '我的流程', 'education', '/formBuild', 'menu', '', '0', '0', 'pig', 'Layout', '0', '2', '', '测试', '1', '2', '管理员', '2020-07-09 16:23:57', '2021-03-13 22:52:35');
INSERT INTO `bpm_menu` VALUES ('18', 'pig:menu:api:nacos', 'nacos', 'link', 'http://120.77.218.141:8848/nacos/#/', 'menu', '', '0', '14', 'pig', 'iframe', '0', '9999', '', '', '1', '0', '', '2020-07-09 16:38:42', '2020-08-12 23:24:19');
INSERT INTO `bpm_menu` VALUES ('19', 'pig:menu:api:jenkins', 'jenkins', 'link', 'http://120.77.218.141:8083/', 'menu', '', '0', '14', 'pig', 'iframe', '0', '9999', '', '', '1', '0', '', '2020-07-09 16:38:42', '2020-08-12 23:24:14');
INSERT INTO `bpm_menu` VALUES ('20', 'pig:menu:api:maven', 'maven', 'link', 'http://120.77.218.141:8086/repository/maven-snapshots/', 'menu', '', '0', '14', 'pig', 'iframe', '0', '9999', '', '', '1', '2', '管理员', '2020-07-09 16:38:42', '2020-10-23 17:30:51');
INSERT INTO `bpm_menu` VALUES ('21', 'pig:menu:api:sql', 'SQL平台', 'link', 'http://120.77.218.141:9995', 'menu', '', '0', '14', 'pig', '', '0', '9999', '', '', '1', '0', '', '2020-07-09 16:38:42', '2020-08-28 18:04:03');
INSERT INTO `bpm_menu` VALUES ('22', 'pig:menu:menu', '菜单列表', 'form', '/system/menu', 'menu', '', '0', '13', 'pig', '/system/menu', '0', '1', '', '', '1', '0', '', '2020-10-23 16:23:57', '2021-04-06 11:18:50');
INSERT INTO `bpm_menu` VALUES ('28', 'pig:menu:monitor', '系统监控', 'guide', '/monitor', 'menu', '', '0', '0', 'pig', 'Layout', '0', '7', 'server', '', '1', '2', '管理员', '2020-07-09 16:23:57', '2021-03-13 22:55:39');
INSERT INTO `bpm_menu` VALUES ('29', 'pig:menu:monitorServer', '服务器监控', 'el-icon-document', '/server', 'menu', '', '0', '28', 'pig', 'monitor/server', '0', '99', '', '', '1', '0', '', '2020-07-09 16:23:57', '2021-01-27 16:37:41');
INSERT INTO `bpm_menu` VALUES ('31', 'pig:menu:about', '关于', '', '/about', 'menu', '', '0', '0', 'pig', '', '0', '100', '', '', '0', '2', '管理员', '2021-02-25 22:39:35', '2021-03-13 22:55:55');
INSERT INTO `bpm_menu` VALUES ('39', 'pig:menu:codeGenerator', '代码生成器', 'component', '/tool/generator', 'menu', '', '0', '0', 'pig', 'Layout', '0', '4', '/codeTable', '', '1', '2', '管理员', '2021-03-14 12:14:19', '2021-03-14 12:42:45');
INSERT INTO `bpm_menu` VALUES ('40', 'pig:menu:codeDbConfig', '数据源', 'example', '/db', 'menu', '', '0', '39', 'pig', '/tool/generator/codeTable/db', '0', '1', '', '', '1', '2', '管理员', '2021-03-14 12:18:41', '2021-03-14 12:37:04');
INSERT INTO `bpm_menu` VALUES ('41', 'pig:menu:templateGroup', '模板组', 'example', '/templateGroup', 'menu', '', '0', '39', 'pig', '/tool/generator/codeTable/templateGroup', '0', '1', '', '', '1', '2', '管理员', '2021-03-14 12:18:41', '2021-03-14 12:38:55');
INSERT INTO `bpm_menu` VALUES ('42', 'pig:menu:tableStrategyConfig', '策略管理', 'example', '/tableStrategyConfig', 'menu', '', '0', '39', 'pig', '/tool/generator/tableStrategyConfig', '0', '1', '', '', '1', '2', '管理员', '2021-03-14 12:18:41', '2021-03-14 12:38:55');
INSERT INTO `bpm_menu` VALUES ('43', 'pig:menu:codeTable', '表结构', 'example', '/codeTable', 'menu', '', '0', '39', 'pig', '/tool/generator/codeTable', '0', '1', '', '', '1', '2', '管理员', '2021-03-14 12:18:41', '2021-03-14 12:41:57');
INSERT INTO `bpm_menu` VALUES ('44', 'pig:menu:generatorExample', '生成示例', 'component', '/tool/generator/example', 'component', '', '0', '39', 'pig', 'Layout', '0', '1', '/version', '', '1', '2', '管理员', '2021-03-14 12:18:41', '2021-03-14 13:25:42');
INSERT INTO `bpm_menu` VALUES ('45', 'pig:menu:generatorVersion', '版本', 'example', '/version', 'menu', '', '0', '44', 'pig', '/tool/generator/example/version', '0', '1', '', '', '1', '2', '管理员', '2021-03-14 12:18:41', '2021-03-14 13:17:42');
INSERT INTO `bpm_menu` VALUES ('46', 'pig:menu:generatorColumn', '列信息', 'example', '/column', 'menu', '', '0', '44', 'pig', '/tool/generator/example/column', '0', '1', '', '', '1', '2', '管理员', '2021-03-14 12:18:41', '2021-03-14 13:17:42');
INSERT INTO `bpm_menu` VALUES ('47', 'pig:menu:userManager', '用户管理', 'users', '/system/user', 'menu', '', '0', '13', 'pig', 'system/user', '0', '1', '', '', '1', '2', '管理员', '2021-03-19 20:42:33', '2021-03-20 11:12:05');
INSERT INTO `bpm_menu` VALUES ('48', 'pig:menu:monitorCache', '缓存监控', 'el-icon-document', '/cache', 'menu', '', '0', '28', 'pig', 'monitor/cache', '0', '98', '', '', '1', '0', '', '2020-07-09 16:23:57', '2021-01-27 16:37:41');
INSERT INTO `bpm_menu` VALUES ('49', 'pig:menu:companyManager', '公司管理', 'organization', '/system/company', 'menu', '', '0', '13', 'pig', 'system/company', '0', '1', '', '', '1', '2', '管理员', '2021-03-19 20:42:33', '2021-03-20 11:12:08');
INSERT INTO `bpm_menu` VALUES ('50', 'pig:menu:configManager', '配置管理', 'control', '/system/config', 'menu', '', '0', '13', 'pig', 'system/config', '0', '1', '', '', '1', '2', '管理员', '2021-03-19 20:42:33', '2021-03-20 11:12:46');
INSERT INTO `bpm_menu` VALUES ('51', 'pig:menu:configTemplateManager', '全局配置模板管理', 'global', '/system/configTemplate', 'menu', '', '0', '13', 'pig', '/system/configTemplate', '0', '1', '', '', '1', '2', '管理员', '2021-03-19 20:42:33', '2021-03-20 11:12:57');
INSERT INTO `bpm_menu` VALUES ('52', 'pig:menu:deptManager', '部门管理', 'dept', '/system/dept', 'menu', '', '0', '13', 'pig', '/system/dept', '0', '1', '', '', '1', '2', '管理员', '2021-03-19 20:42:33', '2021-03-20 11:13:19');
INSERT INTO `bpm_menu` VALUES ('53', 'pig:menu:fileManager', '附件管理', 'file-search', '/system/file', 'menu', '', '0', '13', 'pig', '/system/file', '0', '1', '', '', '1', '2', '管理员', '2021-03-19 20:42:33', '2021-03-20 11:13:21');
INSERT INTO `bpm_menu` VALUES ('54', 'pig:menu:fileTemplateManager', '流程模板附件管理', 'file-done', '/system/fileTemplate', 'menu', '', '0', '13', 'pig', '/system/fileTemplate', '0', '1', '', '', '1', '2', '管理员', '2021-03-19 20:42:33', '2021-03-20 12:02:59');
INSERT INTO `bpm_menu` VALUES ('55', 'pig:menu:messageManager', '通知模板管理', 'notification', '/system/messageContent', 'menu', '', '0', '13', 'pig', '/system/messageContent', '0', '1', '', '', '1', '2', '管理员', '2021-03-19 20:42:33', '2021-03-20 11:14:07');
INSERT INTO `bpm_menu` VALUES ('56', 'pig:menu:messageWhiteListManager', '通知白名单管理', 'message', '/system/messageWhiteList', 'menu', '', '0', '13', 'pig', '/system/messageWhiteList', '0', '1', '', '', '1', '2', '管理员', '2021-03-19 20:42:33', '2021-03-20 11:14:10');
INSERT INTO `bpm_menu` VALUES ('57', 'pig:menu:roleManager', '角色管理', 'role', '/system/role', 'menu', '', '0', '13', 'pig', '/system/role', '0', '1', '', '', '1', '2', '管理员', '2021-03-19 20:42:33', '2021-03-20 11:14:34');
INSERT INTO `bpm_menu` VALUES ('58', 'pig:menu:roleGroupManager', '角色组管理', 'usergroup-add', '/system/roleGroup', 'menu', '', '0', '13', 'pig', '/system/roleGroup', '0', '1', '', '', '1', '2', '管理员', '2021-03-19 20:42:33', '2021-03-20 11:14:35');
INSERT INTO `bpm_menu` VALUES ('59', 'pig:menu:sqlLogManager', 'SQL日志管理', 'usergroup-add', '/system/sqLog', 'menu', '', '0', '13', 'pig', '/system/sqlLog', '0', '1', '', '', '1', '2', '管理员', '2021-03-19 20:42:33', '2021-03-22 17:33:03');
INSERT INTO `bpm_menu` VALUES ('60', 'pig:menu:operatorLogManager', '操作日志管理', 'usergroup-add', '/system/operatorLog', 'menu', '', '0', '13', 'pig', '/system/operatorLog', '0', '1', '', '', '1', '2', '管理员', '2021-03-19 20:42:33', '2021-03-22 17:32:55');
INSERT INTO `bpm_menu` VALUES ('61', 'pig:menu:systemDict', '字典列表', 'dict', '/system/dict', 'menu', '', '0', '13', 'pig', '/system/dict', '0', '1', '', '', '1', '0', '', '2020-07-09 16:23:57', '2021-04-06 19:59:02');
INSERT INTO `bpm_menu` VALUES ('62', 'pig:menu:systemFormList', '表单列表', 'tree', '/system/form', 'menu', '', '0', '8', 'pig', '/system/form', '0', '3', '', '', '1', '0', '', '2020-07-09 16:23:57', '2020-07-11 08:47:27');
INSERT INTO `bpm_menu` VALUES ('63', 'pig:menu:systemProcessList', '流程列表', 'tree', '/system/process', 'menu', '', '0', '9', 'pig', '/system/process', '0', '1', '', '', '1', '0', '', '2020-07-09 16:23:57', '2021-04-09 10:32:48');
INSERT INTO `bpm_menu` VALUES ('64', 'pig:menu:testProcess20210413', '测试流程1232324', 'example', 'start', 'menu', '{\"processKey\":\"pig-process-testProcess2021\"}', '0', '16', 'pig', 'formBuild/start', '0', '1', '', '', '1', '2', '管理员', '2021-03-04 16:17:04', '2021-04-13 14:17:57');

-- ----------------------------
-- Records of bpm_tenant
-- ----------------------------
INSERT INTO `bpm_tenant` VALUES ('pig', '测试租户', '2020-09-02 11:37:42', '2099-09-12 11:37:42', '测试租户', '1', '0', '', '2020-09-02 11:38:30', '2020-09-02 11:38:30');


-- ----------------------------
-- Records of bpm_user
-- ----------------------------
INSERT INTO `bpm_user` VALUES ('1262383598905909250', '2', 'admin', '管理员', '786289666@qq.com', null, '', '', '1', 'http://wework.qpic.cn/bizmail/xpuWj7ibdYjibN5exHJww3QEoenrnnjfpuQhiaMLzeZsD4dNWIEC4af2g/0', '2020-12-18 17:58:07', '1', '1', 'pig', '$2a$10$z9oouf75V6yCozTR/PFdFuA90EEBcNlxDBBJOwmItHWFknst0MTl2', '2020-05-18 14:04:40', null, '1', 'system', '0', '1', '0', '', '2020-05-18 14:04:40', '2021-01-12 17:12:49');
INSERT INTO `bpm_user` VALUES ('1262383598905909251', '3', 'hairman', '测试集团董事长', '786289666@qq.com', null, '', '', '1', 'http://wework.qpic.cn/bizmail/xpuWj7ibdYjibN5exHJww3QEoenrnnjfpuQhiaMLzeZsD4dNWIEC4af2g/0', '2020-12-17 17:58:10', '1', '2', 'pig', '$2a$10$z9oouf75V6yCozTR/PFdFuA90EEBcNlxDBBJOwmItHWFknst0MTl2', '2020-05-18 14:04:40', null, '1', 'system', '0', '1', '0', '', '2020-05-18 14:04:40', '2021-01-12 17:12:53');
INSERT INTO `bpm_user` VALUES ('1262383598905909252', '4', 'generalManager', '测试集团总经理', '786289666@qq.com', null, '', '', '1', 'http://wework.qpic.cn/bizmail/xpuWj7ibdYjibN5exHJww3QEoenrnnjfpuQhiaMLzeZsD4dNWIEC4af2g/0', '2020-12-23 17:58:13', '1', '2', 'pig', '$2a$10$z9oouf75V6yCozTR/PFdFuA90EEBcNlxDBBJOwmItHWFknst0MTl2', '2020-05-18 14:04:40', null, '1', 'system', '0', '1', '0', '', '2020-05-18 14:04:40', '2021-01-12 17:12:53');
INSERT INTO `bpm_user` VALUES ('1262383598905909253', '5', 'techDirector', '总公司技术部负责人', '786289666@qq.com', null, '', '', '1', 'http://wework.qpic.cn/bizmail/xpuWj7ibdYjibN5exHJww3QEoenrnnjfpuQhiaMLzeZsD4dNWIEC4af2g/0', '2020-12-24 17:58:18', '1', '1', 'pig', '$2a$10$z9oouf75V6yCozTR/PFdFuA90EEBcNlxDBBJOwmItHWFknst0MTl2', '2020-05-18 14:04:40', null, '1', 'system', '0', '1', '0', '', '2020-05-18 14:04:40', '2021-01-12 17:12:56');
INSERT INTO `bpm_user` VALUES ('1262383598905909254', '6', 'techStaff', '总公司技术部职员', '786289666@qq.com', null, '', '', '1', 'http://wework.qpic.cn/bizmail/xpuWj7ibdYjibN5exHJww3QEoenrnnjfpuQhiaMLzeZsD4dNWIEC4af2g/0', '2020-12-17 17:58:21', '1', '1', 'pig', '$2a$10$z9oouf75V6yCozTR/PFdFuA90EEBcNlxDBBJOwmItHWFknst0MTl2', '2020-05-18 14:04:40', null, '1', 'system', '0', '1', '0', '', '2020-05-18 14:04:40', '2021-01-12 17:12:56');
INSERT INTO `bpm_user` VALUES ('1262383598905909255', '7', 'techManager', '总公司技术部经理', '786289666@qq.com', null, '', '', '1', 'http://wework.qpic.cn/bizmail/xpuWj7ibdYjibN5exHJww3QEoenrnnjfpuQhiaMLzeZsD4dNWIEC4af2g/0', '2020-12-17 17:58:25', '1', '7', 'pig', '$2a$10$z9oouf75V6yCozTR/PFdFuA90EEBcNlxDBBJOwmItHWFknst0MTl2', '2020-05-18 14:04:40', null, '1', 'system', '0', '1', '0', '', '2020-05-18 14:04:40', '2021-01-12 17:12:56');
INSERT INTO `bpm_user` VALUES ('1262383598905909256', '8', 'financeDirector', '总公司财务部负责人', '786289666@qq.com', null, '', '', '1', 'http://wework.qpic.cn/bizmail/xpuWj7ibdYjibN5exHJww3QEoenrnnjfpuQhiaMLzeZsD4dNWIEC4af2g/0', '2020-12-17 17:58:28', '1', '3', 'pig', '$2a$10$z9oouf75V6yCozTR/PFdFuA90EEBcNlxDBBJOwmItHWFknst0MTl2', '2020-05-18 14:04:40', null, '1', 'system', '0', '1', '0', '', '2020-05-18 14:04:40', '2021-01-12 17:12:56');
INSERT INTO `bpm_user` VALUES ('1262383598905909257', '9', 'financeManager', '总公司财务部经理', '786289666@qq.com', null, '', '', '1', 'http://wework.qpic.cn/bizmail/xpuWj7ibdYjibN5exHJww3QEoenrnnjfpuQhiaMLzeZsD4dNWIEC4af2g/0', '2020-12-17 17:58:31', '1', '3', 'pig', '$2a$10$z9oouf75V6yCozTR/PFdFuA90EEBcNlxDBBJOwmItHWFknst0MTl2', '2020-05-18 14:04:40', null, '1', 'system', '0', '1', '0', '', '2020-05-18 14:04:40', '2021-01-12 17:12:56');
INSERT INTO `bpm_user` VALUES ('1262383598905909258', '10', 'financeStaff', '总公司财务部职员', '786289666@qq.com', null, '', '', '1', 'http://wework.qpic.cn/bizmail/xpuWj7ibdYjibN5exHJww3QEoenrnnjfpuQhiaMLzeZsD4dNWIEC4af2g/0', '2020-12-16 17:58:35', '1', '3', 'pig', '$2a$10$z9oouf75V6yCozTR/PFdFuA90EEBcNlxDBBJOwmItHWFknst0MTl2', '2020-05-18 14:04:40', null, '1', 'system', '0', '1', '0', '', '2020-05-18 14:04:40', '2021-01-12 17:12:56');
INSERT INTO `bpm_user` VALUES ('1262383598905909259', '11', 'gdBranchTechStaff', '广分技术部职员', '786289666@qq.com', null, '', '', '1', 'http://wework.qpic.cn/bizmail/xpuWj7ibdYjibN5exHJww3QEoenrnnjfpuQhiaMLzeZsD4dNWIEC4af2g/0', null, '4', '4', 'pig', '$2a$10$z9oouf75V6yCozTR/PFdFuA90EEBcNlxDBBJOwmItHWFknst0MTl2', '2020-05-18 14:04:40', null, '1', 'system', '0', '1', '0', '', '2020-05-18 14:04:40', '2021-01-12 17:12:56');
INSERT INTO `bpm_user` VALUES ('1262383598905909260', '12', 'gdBranchTechDirector', '广分技术部负责人', '786289666@qq.com', null, '', '', '1', 'http://wework.qpic.cn/bizmail/xpuWj7ibdYjibN5exHJww3QEoenrnnjfpuQhiaMLzeZsD4dNWIEC4af2g/0', null, '4', '4', 'pig', '$2a$10$z9oouf75V6yCozTR/PFdFuA90EEBcNlxDBBJOwmItHWFknst0MTl2', '2020-05-18 14:04:40', null, '1', 'system', '0', '1', '0', '', '2020-05-18 14:04:40', '2021-01-12 17:12:56');
INSERT INTO `bpm_user` VALUES ('1262383598905909261', '13', 'gdBranchGeneralManager', '广分总经理', '786289666@qq.com', null, '', '', '1', 'http://wework.qpic.cn/bizmail/xpuWj7ibdYjibN5exHJww3QEoenrnnjfpuQhiaMLzeZsD4dNWIEC4af2g/0', null, '4', '5', 'pig', '$2a$10$z9oouf75V6yCozTR/PFdFuA90EEBcNlxDBBJOwmItHWFknst0MTl2', '2020-05-18 14:04:40', null, '1', 'system', '0', '1', '0', '', '2020-05-18 14:04:40', '2021-01-12 17:12:56');
INSERT INTO `bpm_user` VALUES ('1262383598905909262', '14', 'gdBranchFinanceDirector', '广分财务部负责人', '786289666@qq.com', null, '', '', '1', 'http://wework.qpic.cn/bizmail/xpuWj7ibdYjibN5exHJww3QEoenrnnjfpuQhiaMLzeZsD4dNWIEC4af2g/0', '2020-12-17 17:58:39', '4', '6', 'pig', '$2a$10$z9oouf75V6yCozTR/PFdFuA90EEBcNlxDBBJOwmItHWFknst0MTl2', '2020-05-18 14:04:40', null, '1', 'system', '0', '1', '0', '', '2020-05-18 14:04:40', '2021-01-12 17:12:56');

INSERT INTO `easy_bpm`.`bpm_role` (`role_id`, `role_code`, `role_name`, `role_level`, `role_abbr`, `role_alias_name`, `tenant_id`, `company_id`, `dept_id`, `remarks`, `valid_state`, `operator_id`, `operator_name`, `create_time`, `update_time`) VALUES ('1', 'pig:administrastor', '【测试集团】管理员', '1', '管理员', '管理员', 'pig', '1', '1', '', '1', NULL, '', '2020-06-14 06:45:49', '2020-06-14 06:47:03');
INSERT INTO `easy_bpm`.`bpm_role` (`role_id`, `role_code`, `role_name`, `role_level`, `role_abbr`, `role_alias_name`, `tenant_id`, `company_id`, `dept_id`, `remarks`, `valid_state`, `operator_id`, `operator_name`, `create_time`, `update_time`) VALUES ('2', 'pig:role:techDirector', '总公司技术部负责人', '1', '总公司技术部负责人', '总公司技术部负责人', 'pig', '1', '1', '', '1', NULL, '', '2020-06-24 13:52:47', '2020-06-24 14:05:37');
INSERT INTO `easy_bpm`.`bpm_role` (`role_id`, `role_code`, `role_name`, `role_level`, `role_abbr`, `role_alias_name`, `tenant_id`, `company_id`, `dept_id`, `remarks`, `valid_state`, `operator_id`, `operator_name`, `create_time`, `update_time`) VALUES ('3', 'pig:role:techManager', '总公司技术部经理', '1', '总公司技术部经理', '总公司技术部经理', 'pig', '1', '1', '', '1', NULL, '', '2020-06-24 14:04:28', '2020-06-24 14:06:26');
INSERT INTO `easy_bpm`.`bpm_role` (`role_id`, `role_code`, `role_name`, `role_level`, `role_abbr`, `role_alias_name`, `tenant_id`, `company_id`, `dept_id`, `remarks`, `valid_state`, `operator_id`, `operator_name`, `create_time`, `update_time`) VALUES ('4', 'pig:role:techStaff', '总公司技术部职员', '1', '总公司技术部职员', '总公司技术部职员', 'pig', '1', '1', '', '1', NULL, '', '2020-06-24 14:06:23', '2020-06-24 14:07:47');
INSERT INTO `easy_bpm`.`bpm_role` (`role_id`, `role_code`, `role_name`, `role_level`, `role_abbr`, `role_alias_name`, `tenant_id`, `company_id`, `dept_id`, `remarks`, `valid_state`, `operator_id`, `operator_name`, `create_time`, `update_time`) VALUES ('5', 'pig:role:hairman', '董事长', '1', '董事长', '董事长', 'pig', '1', '2', '', '1', NULL, '', '2020-06-24 14:07:31', '2020-06-24 14:08:03');
INSERT INTO `easy_bpm`.`bpm_role` (`role_id`, `role_code`, `role_name`, `role_level`, `role_abbr`, `role_alias_name`, `tenant_id`, `company_id`, `dept_id`, `remarks`, `valid_state`, `operator_id`, `operator_name`, `create_time`, `update_time`) VALUES ('6', 'pig:role:generalManager', '总经理', '1', '总经理', '总经理', 'pig', '1', '2', '', '1', NULL, '', '2020-06-24 14:08:41', '2020-06-24 14:08:41');
INSERT INTO `easy_bpm`.`bpm_role` (`role_id`, `role_code`, `role_name`, `role_level`, `role_abbr`, `role_alias_name`, `tenant_id`, `company_id`, `dept_id`, `remarks`, `valid_state`, `operator_id`, `operator_name`, `create_time`, `update_time`) VALUES ('7', 'pig:role:financeDirector', '总公司财务部负责人', '1', '总公司财务部负责人', '总公司财务部负责人', 'pig', '1', '3', '', '1', NULL, '', '2020-06-24 14:09:00', '2020-06-24 14:09:25');
INSERT INTO `easy_bpm`.`bpm_role` (`role_id`, `role_code`, `role_name`, `role_level`, `role_abbr`, `role_alias_name`, `tenant_id`, `company_id`, `dept_id`, `remarks`, `valid_state`, `operator_id`, `operator_name`, `create_time`, `update_time`) VALUES ('8', 'pig:role:financeManager', '总公司财务部经理', '1', '总公司财务部经理', '总公司财务部经理', 'pig', '1', '3', '', '1', NULL, '', '2020-06-24 14:09:00', '2020-06-24 14:09:25');
INSERT INTO `easy_bpm`.`bpm_role` (`role_id`, `role_code`, `role_name`, `role_level`, `role_abbr`, `role_alias_name`, `tenant_id`, `company_id`, `dept_id`, `remarks`, `valid_state`, `operator_id`, `operator_name`, `create_time`, `update_time`) VALUES ('9', 'pig:role:financeStaff', '总公司财务部职员', '1', '总公司财务部职员', '总公司财务部职员', 'pig', '1', '3', '', '1', NULL, '', '2020-06-24 14:09:00', '2020-06-24 14:09:25');
INSERT INTO `easy_bpm`.`bpm_role` (`role_id`, `role_code`, `role_name`, `role_level`, `role_abbr`, `role_alias_name`, `tenant_id`, `company_id`, `dept_id`, `remarks`, `valid_state`, `operator_id`, `operator_name`, `create_time`, `update_time`) VALUES ('10', 'pig:role:gdBranchCompany:generalManager', '广分总经理', '1', '广分总经理', '广分总经理', 'pig', '4', '5', '', '1', NULL, '', '2020-06-24 14:12:15', '2020-06-24 14:12:24');
INSERT INTO `easy_bpm`.`bpm_role` (`role_id`, `role_code`, `role_name`, `role_level`, `role_abbr`, `role_alias_name`, `tenant_id`, `company_id`, `dept_id`, `remarks`, `valid_state`, `operator_id`, `operator_name`, `create_time`, `update_time`) VALUES ('11', 'pig:role:gdBranchCompany:techDirector', '广分技术部负责人', '1', '广分技术部负责人', '广分技术部负责人', 'pig', '4', '4', '', '1', NULL, '', '2020-06-24 14:12:45', '2020-06-24 14:13:17');
INSERT INTO `easy_bpm`.`bpm_role` (`role_id`, `role_code`, `role_name`, `role_level`, `role_abbr`, `role_alias_name`, `tenant_id`, `company_id`, `dept_id`, `remarks`, `valid_state`, `operator_id`, `operator_name`, `create_time`, `update_time`) VALUES ('12', 'pig:role:gdBranchCompany:techStaff', '广分技术部职员', '1', '广分技术部职员', '广分技术部职员', 'pig', '4', '4', '', '1', NULL, '', '2020-06-24 14:13:50', '2020-06-24 14:15:41');
INSERT INTO `easy_bpm`.`bpm_role` (`role_id`, `role_code`, `role_name`, `role_level`, `role_abbr`, `role_alias_name`, `tenant_id`, `company_id`, `dept_id`, `remarks`, `valid_state`, `operator_id`, `operator_name`, `create_time`, `update_time`) VALUES ('13', 'pig:role:gdBranchCompany:financeDirector', '广分财务部负责人', '1', '广分财务部负责人', '广分财务部负责人', 'pig', '4', '6', '', '1', NULL, '', '2020-06-24 14:13:50', '2020-06-24 14:15:46');
INSERT INTO `easy_bpm`.`bpm_role` (`role_id`, `role_code`, `role_name`, `role_level`, `role_abbr`, `role_alias_name`, `tenant_id`, `company_id`, `dept_id`, `remarks`, `valid_state`, `operator_id`, `operator_name`, `create_time`, `update_time`) VALUES ('14', 'pig:role:gdBranchCompany:financeStaff', '广分财务部职员', '1', '广分财务部职员', '广分财务部职员', 'pig', '4', '6', '', '1', NULL, '', '2020-06-24 14:13:50', '2020-06-24 14:15:48');

INSERT INTO `easy_bpm`.`bpm_role_group` (`role_group_id`, `role_group_code`, `role_group_name`, `role_group_abbr`, `business_line`, `role_group_level`, `role_group_type`, `tenant_id`, `remarks`, `valid_state`, `operator_id`, `operator_name`, `create_time`, `update_time`) VALUES ('1', 'pig:roleGroup:testCompany:techDirector', '总公司技术部负责人', 'CTO', 'tech', '1', '1', 'pig', '', '1', NULL, '', '2020-06-24 11:08:48', '2020-06-24 13:51:50');
INSERT INTO `easy_bpm`.`bpm_role_group` (`role_group_id`, `role_group_code`, `role_group_name`, `role_group_abbr`, `business_line`, `role_group_level`, `role_group_type`, `tenant_id`, `remarks`, `valid_state`, `operator_id`, `operator_name`, `create_time`, `update_time`) VALUES ('2', 'pig:roleGroup:testCompany:techManager', '总公司技术部经理', '', 'tech', '1', '1', 'pig', '', '1', NULL, '', '2020-06-24 11:12:43', '2020-06-24 13:51:53');
INSERT INTO `easy_bpm`.`bpm_role_group` (`role_group_id`, `role_group_code`, `role_group_name`, `role_group_abbr`, `business_line`, `role_group_level`, `role_group_type`, `tenant_id`, `remarks`, `valid_state`, `operator_id`, `operator_name`, `create_time`, `update_time`) VALUES ('3', 'pig:roleGroup:testCompany:techStaff', '总公司技术部职员', '', 'tech', '1', '1', 'pig', '', '1', NULL, '', '2020-06-24 11:12:43', '2020-06-24 13:51:55');
INSERT INTO `easy_bpm`.`bpm_role_group` (`role_group_id`, `role_group_code`, `role_group_name`, `role_group_abbr`, `business_line`, `role_group_level`, `role_group_type`, `tenant_id`, `remarks`, `valid_state`, `operator_id`, `operator_name`, `create_time`, `update_time`) VALUES ('4', 'pig:roleGroup:testCompany:hairman', '董事长', 'CEO', 'manager', '1', '1', 'pig', '', '1', NULL, '', '2020-06-24 11:22:07', '2020-06-24 13:51:57');
INSERT INTO `easy_bpm`.`bpm_role_group` (`role_group_id`, `role_group_code`, `role_group_name`, `role_group_abbr`, `business_line`, `role_group_level`, `role_group_type`, `tenant_id`, `remarks`, `valid_state`, `operator_id`, `operator_name`, `create_time`, `update_time`) VALUES ('5', 'pig:roleGroup:testCompany:generalManager', '总经理', '', 'manager', '1', '1', 'pig', '', '1', NULL, '', '2020-06-24 11:23:22', '2020-06-24 13:52:00');
INSERT INTO `easy_bpm`.`bpm_role_group` (`role_group_id`, `role_group_code`, `role_group_name`, `role_group_abbr`, `business_line`, `role_group_level`, `role_group_type`, `tenant_id`, `remarks`, `valid_state`, `operator_id`, `operator_name`, `create_time`, `update_time`) VALUES ('6', 'pig:roleGroup:testCompany:financeDirector', '总公司财务部负责人', 'CFO', 'finance', '1', '1', 'pig', '', '1', NULL, '', '2020-06-24 11:25:21', '2020-06-24 13:52:02');
INSERT INTO `easy_bpm`.`bpm_role_group` (`role_group_id`, `role_group_code`, `role_group_name`, `role_group_abbr`, `business_line`, `role_group_level`, `role_group_type`, `tenant_id`, `remarks`, `valid_state`, `operator_id`, `operator_name`, `create_time`, `update_time`) VALUES ('7', 'pig:roleGroup:testCompany:financeManager', '总公司财务部经理', '', 'finance', '1', '1', 'pig', '', '1', NULL, '', '2020-06-24 11:26:10', '2020-06-24 13:52:05');
INSERT INTO `easy_bpm`.`bpm_role_group` (`role_group_id`, `role_group_code`, `role_group_name`, `role_group_abbr`, `business_line`, `role_group_level`, `role_group_type`, `tenant_id`, `remarks`, `valid_state`, `operator_id`, `operator_name`, `create_time`, `update_time`) VALUES ('8', 'pig:roleGroup:testCompany:financeStaff', '总公司财务部职员', '', 'finance', '1', '1', 'pig', '', '1', NULL, '', '2020-06-24 11:26:58', '2020-06-24 13:52:08');
INSERT INTO `easy_bpm`.`bpm_role_group` (`role_group_id`, `role_group_code`, `role_group_name`, `role_group_abbr`, `business_line`, `role_group_level`, `role_group_type`, `tenant_id`, `remarks`, `valid_state`, `operator_id`, `operator_name`, `create_time`, `update_time`) VALUES ('9', 'pig:roleGroup:testCompany:staff', '总公司职员', '', '', '1', '2', 'pig', '', '1', NULL, '', '2020-06-24 11:28:07', '2020-06-24 13:52:10');
INSERT INTO `easy_bpm`.`bpm_role_group` (`role_group_id`, `role_group_code`, `role_group_name`, `role_group_abbr`, `business_line`, `role_group_level`, `role_group_type`, `tenant_id`, `remarks`, `valid_state`, `operator_id`, `operator_name`, `create_time`, `update_time`) VALUES ('10', 'pig:roleGroup:testCompany:director', '总公司部门负责人', '', '', '1', '2', 'pig', '', '1', NULL, '', '2020-06-24 11:28:44', '2020-06-24 13:52:12');
INSERT INTO `easy_bpm`.`bpm_role_group` (`role_group_id`, `role_group_code`, `role_group_name`, `role_group_abbr`, `business_line`, `role_group_level`, `role_group_type`, `tenant_id`, `remarks`, `valid_state`, `operator_id`, `operator_name`, `create_time`, `update_time`) VALUES ('11', 'pig:roleGroup:gdBranchCompany:techDirector', '广分技术部负责人', '', 'tech', '2', '1', 'pig', '', '1', NULL, '', '2020-06-24 11:08:48', '2020-06-24 13:52:13');
INSERT INTO `easy_bpm`.`bpm_role_group` (`role_group_id`, `role_group_code`, `role_group_name`, `role_group_abbr`, `business_line`, `role_group_level`, `role_group_type`, `tenant_id`, `remarks`, `valid_state`, `operator_id`, `operator_name`, `create_time`, `update_time`) VALUES ('12', 'pig:roleGroup:gdBranchCompany:techStaff', '广分技术部职员', '', 'tech', '2', '1', 'pig', '', '1', NULL, '', '2020-06-24 11:12:43', '2020-06-24 13:52:17');
INSERT INTO `easy_bpm`.`bpm_role_group` (`role_group_id`, `role_group_code`, `role_group_name`, `role_group_abbr`, `business_line`, `role_group_level`, `role_group_type`, `tenant_id`, `remarks`, `valid_state`, `operator_id`, `operator_name`, `create_time`, `update_time`) VALUES ('13', 'pig:roleGroup:gdBranchCompany:generalManager', '广分总经理', '', 'manager', '2', '1', 'pig', '', '1', NULL, '', '2020-06-24 11:23:22', '2020-06-24 13:52:18');
INSERT INTO `easy_bpm`.`bpm_role_group` (`role_group_id`, `role_group_code`, `role_group_name`, `role_group_abbr`, `business_line`, `role_group_level`, `role_group_type`, `tenant_id`, `remarks`, `valid_state`, `operator_id`, `operator_name`, `create_time`, `update_time`) VALUES ('14', 'pig:roleGroup:gdBranchCompany:financeDirector', '广分财务部负责人', '', 'finance', '2', '1', 'pig', '', '1', NULL, '', '2020-06-24 11:25:21', '2020-06-24 13:52:21');
INSERT INTO `easy_bpm`.`bpm_role_group` (`role_group_id`, `role_group_code`, `role_group_name`, `role_group_abbr`, `business_line`, `role_group_level`, `role_group_type`, `tenant_id`, `remarks`, `valid_state`, `operator_id`, `operator_name`, `create_time`, `update_time`) VALUES ('15', 'pig:roleGroup:gdBranchCompany:financeStaff', '广分财务部职员', '', 'finance', '2', '1', 'pig', '', '1', NULL, '', '2020-06-24 11:26:58', '2020-06-24 13:52:22');
INSERT INTO `easy_bpm`.`bpm_role_group` (`role_group_id`, `role_group_code`, `role_group_name`, `role_group_abbr`, `business_line`, `role_group_level`, `role_group_type`, `tenant_id`, `remarks`, `valid_state`, `operator_id`, `operator_name`, `create_time`, `update_time`) VALUES ('16', 'pig:roleGroup:gdBranchCompany:staff', '分公司职员', '', '', '2', '2', 'pig', '', '1', NULL, '', '2020-06-24 11:28:07', '2020-06-24 14:24:27');
INSERT INTO `easy_bpm`.`bpm_role_group` (`role_group_id`, `role_group_code`, `role_group_name`, `role_group_abbr`, `business_line`, `role_group_level`, `role_group_type`, `tenant_id`, `remarks`, `valid_state`, `operator_id`, `operator_name`, `create_time`, `update_time`) VALUES ('17', 'pig:roleGroup:gdBranchCompany:director', '分公司部门负责人', '', '', '2', '2', 'pig', '', '1', NULL, '', '2020-06-24 11:28:44', '2020-06-24 14:24:44');


INSERT INTO `easy_bpm`.`bpm_role_group_to_role` (`id`, `role_group_id`, `role_id`, `tenant_id`, `remarks`, `valid_state`, `operator_id`, `operator_name`, `create_time`, `update_time`) VALUES ('1', '1', '2', 'pig', '', '1', NULL, '', '2020-06-24 14:16:33', '2020-06-24 14:16:33');
INSERT INTO `easy_bpm`.`bpm_role_group_to_role` (`id`, `role_group_id`, `role_id`, `tenant_id`, `remarks`, `valid_state`, `operator_id`, `operator_name`, `create_time`, `update_time`) VALUES ('2', '2', '3', 'pig', '', '1', NULL, '', '2020-06-24 14:16:55', '2020-06-24 14:17:12');
INSERT INTO `easy_bpm`.`bpm_role_group_to_role` (`id`, `role_group_id`, `role_id`, `tenant_id`, `remarks`, `valid_state`, `operator_id`, `operator_name`, `create_time`, `update_time`) VALUES ('3', '3', '4', 'pig', '', '1', NULL, '', '2020-06-24 14:17:09', '2020-06-24 14:17:15');
INSERT INTO `easy_bpm`.`bpm_role_group_to_role` (`id`, `role_group_id`, `role_id`, `tenant_id`, `remarks`, `valid_state`, `operator_id`, `operator_name`, `create_time`, `update_time`) VALUES ('4', '10', '2', 'pig', '', '1', NULL, '', '2020-06-24 14:17:52', '2020-06-24 14:17:52');
INSERT INTO `easy_bpm`.`bpm_role_group_to_role` (`id`, `role_group_id`, `role_id`, `tenant_id`, `remarks`, `valid_state`, `operator_id`, `operator_name`, `create_time`, `update_time`) VALUES ('5', '9', '3', 'pig', '', '1', NULL, '', '2020-06-24 14:18:38', '2020-06-24 14:18:38');
INSERT INTO `easy_bpm`.`bpm_role_group_to_role` (`id`, `role_group_id`, `role_id`, `tenant_id`, `remarks`, `valid_state`, `operator_id`, `operator_name`, `create_time`, `update_time`) VALUES ('6', '9', '4', 'pig', '', '1', NULL, '', '2020-06-24 14:18:46', '2020-06-24 14:18:46');
INSERT INTO `easy_bpm`.`bpm_role_group_to_role` (`id`, `role_group_id`, `role_id`, `tenant_id`, `remarks`, `valid_state`, `operator_id`, `operator_name`, `create_time`, `update_time`) VALUES ('7', '4', '5', 'pig', '', '1', NULL, '', '2020-06-24 14:19:35', '2020-06-24 14:19:40');
INSERT INTO `easy_bpm`.`bpm_role_group_to_role` (`id`, `role_group_id`, `role_id`, `tenant_id`, `remarks`, `valid_state`, `operator_id`, `operator_name`, `create_time`, `update_time`) VALUES ('8', '5', '6', 'pig', '', '1', NULL, '', '2020-06-24 14:20:18', '2020-06-24 14:20:18');
INSERT INTO `easy_bpm`.`bpm_role_group_to_role` (`id`, `role_group_id`, `role_id`, `tenant_id`, `remarks`, `valid_state`, `operator_id`, `operator_name`, `create_time`, `update_time`) VALUES ('9', '6', '7', 'pig', '', '1', NULL, '', '2020-06-24 14:21:13', '2020-06-24 14:21:33');
INSERT INTO `easy_bpm`.`bpm_role_group_to_role` (`id`, `role_group_id`, `role_id`, `tenant_id`, `remarks`, `valid_state`, `operator_id`, `operator_name`, `create_time`, `update_time`) VALUES ('10', '7', '8', 'pig', '', '1', NULL, '', '2020-06-24 14:21:23', '2020-06-24 14:21:33');
INSERT INTO `easy_bpm`.`bpm_role_group_to_role` (`id`, `role_group_id`, `role_id`, `tenant_id`, `remarks`, `valid_state`, `operator_id`, `operator_name`, `create_time`, `update_time`) VALUES ('11', '8', '9', 'pig', '', '1', NULL, '', '2020-06-24 14:21:29', '2020-06-24 14:21:34');
INSERT INTO `easy_bpm`.`bpm_role_group_to_role` (`id`, `role_group_id`, `role_id`, `tenant_id`, `remarks`, `valid_state`, `operator_id`, `operator_name`, `create_time`, `update_time`) VALUES ('12', '10', '7', 'pig', '', '1', NULL, '', '2020-06-24 14:21:47', '2020-06-24 14:22:02');
INSERT INTO `easy_bpm`.`bpm_role_group_to_role` (`id`, `role_group_id`, `role_id`, `tenant_id`, `remarks`, `valid_state`, `operator_id`, `operator_name`, `create_time`, `update_time`) VALUES ('13', '9', '8', 'pig', '', '1', NULL, '', '2020-06-24 14:21:52', '2020-06-24 14:22:03');
INSERT INTO `easy_bpm`.`bpm_role_group_to_role` (`id`, `role_group_id`, `role_id`, `tenant_id`, `remarks`, `valid_state`, `operator_id`, `operator_name`, `create_time`, `update_time`) VALUES ('14', '9', '9', 'pig', '', '1', NULL, '', '2020-06-24 14:21:55', '2020-06-24 14:22:04');
INSERT INTO `easy_bpm`.`bpm_role_group_to_role` (`id`, `role_group_id`, `role_id`, `tenant_id`, `remarks`, `valid_state`, `operator_id`, `operator_name`, `create_time`, `update_time`) VALUES ('15', '13', '10', 'pig', '', '1', NULL, '', '2020-06-24 14:24:00', '2020-06-24 14:24:00');
INSERT INTO `easy_bpm`.`bpm_role_group_to_role` (`id`, `role_group_id`, `role_id`, `tenant_id`, `remarks`, `valid_state`, `operator_id`, `operator_name`, `create_time`, `update_time`) VALUES ('16', '11', '11', 'pig', '', '1', NULL, '', '2020-06-24 14:25:53', '2020-06-24 14:25:53');
INSERT INTO `easy_bpm`.`bpm_role_group_to_role` (`id`, `role_group_id`, `role_id`, `tenant_id`, `remarks`, `valid_state`, `operator_id`, `operator_name`, `create_time`, `update_time`) VALUES ('17', '12', '12', 'pig', '', '1', NULL, '', '2020-06-24 14:26:19', '2020-06-24 14:26:19');
INSERT INTO `easy_bpm`.`bpm_role_group_to_role` (`id`, `role_group_id`, `role_id`, `tenant_id`, `remarks`, `valid_state`, `operator_id`, `operator_name`, `create_time`, `update_time`) VALUES ('18', '17', '11', 'pig', '', '1', NULL, '', '2020-06-24 14:26:31', '2020-06-24 14:26:31');
INSERT INTO `easy_bpm`.`bpm_role_group_to_role` (`id`, `role_group_id`, `role_id`, `tenant_id`, `remarks`, `valid_state`, `operator_id`, `operator_name`, `create_time`, `update_time`) VALUES ('19', '16', '12', 'pig', '', '1', NULL, '', '2020-06-24 14:26:40', '2020-06-24 14:26:40');
INSERT INTO `easy_bpm`.`bpm_role_group_to_role` (`id`, `role_group_id`, `role_id`, `tenant_id`, `remarks`, `valid_state`, `operator_id`, `operator_name`, `create_time`, `update_time`) VALUES ('20', '14', '13', 'pig', '', '1', NULL, '', '2020-06-24 14:26:58', '2020-06-24 14:27:08');
INSERT INTO `easy_bpm`.`bpm_role_group_to_role` (`id`, `role_group_id`, `role_id`, `tenant_id`, `remarks`, `valid_state`, `operator_id`, `operator_name`, `create_time`, `update_time`) VALUES ('21', '15', '14', 'pig', '', '1', NULL, '', '2020-06-24 14:27:06', '2020-06-24 14:27:11');
INSERT INTO `easy_bpm`.`bpm_role_group_to_role` (`id`, `role_group_id`, `role_id`, `tenant_id`, `remarks`, `valid_state`, `operator_id`, `operator_name`, `create_time`, `update_time`) VALUES ('22', '17', '13', 'pig', '', '1', NULL, '', '2020-06-24 14:27:32', '2020-06-24 14:27:32');
INSERT INTO `easy_bpm`.`bpm_role_group_to_role` (`id`, `role_group_id`, `role_id`, `tenant_id`, `remarks`, `valid_state`, `operator_id`, `operator_name`, `create_time`, `update_time`) VALUES ('23', '16', '14', 'pig', '', '1', NULL, '', '2020-06-24 14:27:43', '2020-06-24 14:27:43');


INSERT INTO `easy_bpm`.`bpm_user_role` (`id`, `user_id`, `role_id`, `alias_name`, `tenant_id`, `remarks`, `valid_state`, `operator_id`, `operator_name`, `create_time`, `update_time`) VALUES ('1', '2', '1', NULL, 'pig', '', '1', NULL, '', '2020-06-14 06:48:22', '2020-06-14 10:59:55');
INSERT INTO `easy_bpm`.`bpm_user_role` (`id`, `user_id`, `role_id`, `alias_name`, `tenant_id`, `remarks`, `valid_state`, `operator_id`, `operator_name`, `create_time`, `update_time`) VALUES ('2', '3', '5', NULL, 'pig', '', '1', NULL, '', '2021-04-18 17:44:30', '2021-04-18 17:44:30');
INSERT INTO `easy_bpm`.`bpm_user_role` (`id`, `user_id`, `role_id`, `alias_name`, `tenant_id`, `remarks`, `valid_state`, `operator_id`, `operator_name`, `create_time`, `update_time`) VALUES ('3', '4', '6', NULL, 'pig', '', '1', NULL, '', '2021-04-18 17:44:46', '2021-04-18 17:44:46');
INSERT INTO `easy_bpm`.`bpm_user_role` (`id`, `user_id`, `role_id`, `alias_name`, `tenant_id`, `remarks`, `valid_state`, `operator_id`, `operator_name`, `create_time`, `update_time`) VALUES ('4', '5', '2', NULL, 'pig', '', '1', NULL, '', '2021-04-18 17:45:10', '2021-04-18 17:46:13');
INSERT INTO `easy_bpm`.`bpm_user_role` (`id`, `user_id`, `role_id`, `alias_name`, `tenant_id`, `remarks`, `valid_state`, `operator_id`, `operator_name`, `create_time`, `update_time`) VALUES ('5', '6', '4', NULL, 'pig', '', '1', NULL, '', '2021-04-18 17:45:36', '2021-04-18 17:45:36');
INSERT INTO `easy_bpm`.`bpm_user_role` (`id`, `user_id`, `role_id`, `alias_name`, `tenant_id`, `remarks`, `valid_state`, `operator_id`, `operator_name`, `create_time`, `update_time`) VALUES ('6', '7', '3', NULL, 'pig', '', '1', NULL, '', '2021-04-18 17:46:28', '2021-04-18 17:46:28');
INSERT INTO `easy_bpm`.`bpm_user_role` (`id`, `user_id`, `role_id`, `alias_name`, `tenant_id`, `remarks`, `valid_state`, `operator_id`, `operator_name`, `create_time`, `update_time`) VALUES ('7', '8', '7', NULL, 'pig', '', '1', NULL, '', '2021-04-18 17:47:38', '2021-04-18 17:47:38');
INSERT INTO `easy_bpm`.`bpm_user_role` (`id`, `user_id`, `role_id`, `alias_name`, `tenant_id`, `remarks`, `valid_state`, `operator_id`, `operator_name`, `create_time`, `update_time`) VALUES ('8', '9', '8', NULL, 'pig', '', '1', NULL, '', '2021-04-18 17:48:26', '2021-04-18 17:48:26');
INSERT INTO `easy_bpm`.`bpm_user_role` (`id`, `user_id`, `role_id`, `alias_name`, `tenant_id`, `remarks`, `valid_state`, `operator_id`, `operator_name`, `create_time`, `update_time`) VALUES ('9', '10', '9', NULL, 'pig', '', '1', NULL, '', '2021-04-18 17:48:42', '2021-04-18 17:48:42');
INSERT INTO `easy_bpm`.`bpm_user_role` (`id`, `user_id`, `role_id`, `alias_name`, `tenant_id`, `remarks`, `valid_state`, `operator_id`, `operator_name`, `create_time`, `update_time`) VALUES ('10', '11', '12', NULL, 'pig', '', '1', NULL, '', '2021-04-18 17:50:00', '2021-04-18 17:50:00');
INSERT INTO `easy_bpm`.`bpm_user_role` (`id`, `user_id`, `role_id`, `alias_name`, `tenant_id`, `remarks`, `valid_state`, `operator_id`, `operator_name`, `create_time`, `update_time`) VALUES ('11', '12', '11', NULL, 'pig', '', '1', NULL, '', '2021-04-18 17:50:07', '2021-04-18 17:50:07');
INSERT INTO `easy_bpm`.`bpm_user_role` (`id`, `user_id`, `role_id`, `alias_name`, `tenant_id`, `remarks`, `valid_state`, `operator_id`, `operator_name`, `create_time`, `update_time`) VALUES ('12', '13', '10', NULL, 'pig', '', '1', NULL, '', '2021-04-18 17:50:31', '2021-04-18 17:50:31');
INSERT INTO `easy_bpm`.`bpm_user_role` (`id`, `user_id`, `role_id`, `alias_name`, `tenant_id`, `remarks`, `valid_state`, `operator_id`, `operator_name`, `create_time`, `update_time`) VALUES ('13', '14', '13', NULL, 'pig', '', '1', NULL, '', '2021-04-18 17:51:02', '2021-04-18 17:51:02');