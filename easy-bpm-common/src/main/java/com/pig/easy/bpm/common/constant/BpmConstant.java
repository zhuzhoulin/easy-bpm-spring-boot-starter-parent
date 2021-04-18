package com.pig.easy.bpm.common.constant;

/**
 * todo:
 *
 * @author : pig
 * @date : 2020/5/21 15:14
 */
public class BpmConstant {

    public static final String SYSTEM = "system";

    public static final String APPLY_SN = "applySn";

    public static final String PLATFORM = "platform";

    public static final String APPLY_ID = "applyId";

    public static final String TENANT_ID = "tenantId";

    public static final String APPLY_USER_INFO = "applyUserInfo";

    public static final String APPLY_DEPT_ID = "applyDeptId";

    public static final String APPLY_COMPANY_ID = "applyCompanyId";

    public static final String APPLY_ROLE_ID = "applyRoleId";

    public static final String APPLY_ROLE_CODE = "applyRoleCode";

    public static final String APPLY_ROLE_NAME = "applyRoleName";

    public static final String BUSINESS_LINE = "businessLine";

    public static final String APPLY_DEPT = "applyDept";

    public static final String FLOW_ADMIN = "flowAdmin";

    public static final String TASK_TYPE_START = "start";

    public static final String TASK_TYPE_APPROVE = "approve";

    public static final String COMMON_CODE_SEPARATION_CHARACTER = ":";

    public static final int INVALID_STATE = 0;

    public static final int VALID_STATE = 1;

    public static final int PROCESS_DETAIL_PUBLISH = 2;

    public static final int PROCESS_DETAIL_UNPUBLISH = 1;

    public static final int PROCESS_DERTAIL_IS_MAIN_VERSION = 1;

    public static final int PROCESS_DERTAIL_NOT_MAIN_VERSION = 2;

    // 节点 找人方式
    // 角色组
    public static final int FIND_USER_TYPE_BY_ROLE_GROUP = 1;
    // 角色
    public static final int FIND_USER_TYPE_BY_ROLE = 2;
    // 固定人员
    public static final int FIND_USER_TYPE_BY_USER = 3;
    // 前端指定人员
    public static final int FIND_USER_TYPE_BY_DESIGNATED_PERSONNEL = 4;
    // 同申请人
    public static final int FIND_USER_TYPE_BY_APPLYER = 5;
    // 同节点人员
    public static final int FIND_USER_TYPE_BY_NODE_USER = 6;

    // 根据 角色组获取关联角色
    public static final int ROLE_GROUP_TYPE_COMMON = 1;
    // 根据 条线及层级获取 角色组 关联角色
    public static final int ROLE_GROUP_TYPE_SPECIAL = 2;

    /**
     * 节点处理策略 skip: 执行人为空跳过,admin: 为空时管理员处理,error:为空时报错
     */
    public static final String HANDLER_STRATEGY_ERROR = "error";
    public static final String HANDLER_STRATEGY_ADMIN = "admin";
    public static final String HANDLER_STRATEGY_SKIP = "skip";

    public static final int SELECT_PATCH_TRUE = 1;
    public static final int HANDLER_STRATEGY_FALSE = 0;

    public static final String NODE_TYPE_USER_TASK = "UserTask";
    public static final String DYNAMIC_KEY_LIST = "dynamicKeyList";
    public static final String DYNAMIC_KEY = "dynamicKey";
    public static final String DYNAMIC = "dynamic";
    public static final String OPTIONS = "options";
    public static final String FORMAT = "format";
    public static final String IS_CHOOSE_TIMES = "isChooseTimes";
    public static final String DISABLED = "disabled";

    public static final int APPLY_STATUS_DRAFT = 1;
    public static final int APPLY_STATUS_APPROVING = 2;
    public static final int APPLY_STATUS_PASS = 3;
    public static final int APPLY_STATUS_REJECT = 4;
    public static final int APPLY_STATUS_DISABLE = 5;

    public static final String FORM_DATA_LIST = "list";

    public static final String FORM_DATA_TYPE_INPUT = "input";
    public static final String FORM_DATA_TYPE_TEXT = "text";
    public static final String FORM_DATA_TYPE_NUMBER = "number";
    public static final String FORM_DATA_TYPE_SELECT = "select";
    public static final String FORM_DATA_TYPE_CASCADER = "cascader";
    public static final String FORM_DATA_TYPE_CHECKBOX = "checkbox";
    public static final String FORM_DATA_TYPE_RADIO = "radio";
    public static final String FORM_DATA_TYPE_DATE = "date";
    public static final String FORM_DATA_TYPE_TIME = "time";
    public static final String FORM_DATA_TYPE_UPLOADFILE = "uploadfile";
    public static final String FORM_DATA_TYPE_UPLOADIMG = "uploadimg";
    public static final String FORM_DATA_TYPE_SWITCH = "switch";
    public static final String FORM_DATA_TYPE_SLIDER = "slider";
    public static final String FORM_DATA_TYPE_CHILDTABLE = "childtable";
    public static final String FORM_DATA_TYPE_P = "p";
    public static final String FORM_DATA_TYPE_SUPER = "super";
    public static final String FORM_DATA_TYPE_BUTTON = "button";
    public static final String FORM_DATA_TYPE_DIVIDER = "divider";
    public static final String FORM_DATA_TYPE_CARD = "card";
    public static final String FORM_DATA_TYPE_GRID = "grid";
    public static final String FORM_DATA_TYPE_TABLE = "table";


    public static final Integer AUTO_COMPLETE_FIRST_NODE = 1;
    public static final Integer NO_AUTO_COMPLETE_FIRST_NODE = 0;

    public static final String HISTORY_START = "发起";


    public static final String MENE_TYPE_MENU = "menu";
    public static final String MENE_TYPE_COMPONENT = "component";
    public static final String MENE_TYPE_BUTTON = "button";

    public static final String TRACE_ID = "traceId";

    public static final String TREE_TYPE_CODE_COMPANY = "company";
    public static final String TREE_TYPE_CODE_DEPT = "dept";
    public static final String TREE_TYPE_CODE_USER = "user";

    public static final int TREE_TYPE_COMPANY = 1;
    public static final int TREE_TYPE_DEPT = 2;
    public static final int TREE_TYPE_USER = 3;
}
