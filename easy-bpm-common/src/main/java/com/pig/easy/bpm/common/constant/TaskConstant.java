package com.pig.easy.bpm.common.constant;

/**
 * todo:
 *
 * @author : pig
 * @date : 2020/6/23 13:33
 */
public class TaskConstant {

    /**
     * 未认领
     */
    public static final int TASK_UN_CLAIM = 1;
    /**
     * 已认领
     */
    public static final int TASK_CLAIM = 2;
    /**
     * 已完成
     */
    public static final int TASK_COMPLETED = 3;
    /**
     * 已取消
     */
    public static final int TASK_DISABLE = 4;
    /**
     * 找不到节点人员，系统自动完成
     */
    public static final int TASK_AUTO_COMPLETE = 5;

    public static final String DEFAULT_ASSIGNEE_USER_EXP = "assigneeExp";
    public static final String ASSIGNEE_USER_EXP = "assignee";
    public static final String DEFAULT_ASSIGNEE_LIST_EXP = "assigneeList";

    public static final String NODE_ID = "nodeId";
    public static final String NODE_NAME = "nodeName";
    public static final String NODE_USERS = "nodeUsers";
    public static final String SIGN_TYPE = "signType";
    public static final String SIGN_TYPE_BEFOR = "befor";
    public static final String SIGN_TYPE_AFFTER = "after";

    public static final String APPROVE_ACTION_DESC = "approveAction";
    public static final String APPROVE_ACTION_PASS = "approvePass";
    public static final String APPROVE_ACTION_REJECT = "approveReject";
    public static final String APPROVE_ACTION_FAIL = "approveFail";
    public static final String APPROVE_ACTION_CANCEL = "approveCancel";
    public static final String APPROVE_ACTION_COUNTER_SIGN = "approveCounterSign";
    public static final String APPROVE_ACTION_ADD_SIGN = "approveAddSign";
    public static final String APPROVE_ACTION_RETURN = "approveReturn";
    public static final String APPROVE_ACTION_RANDOM_RETURN = "approveRandomReturn";
    public static final String APPROVE_ACTION_ADD_TEMP_NODE = "approveAddTempNode";

    public static final String TASK_ACTION_START = "start";
    public static final String TASK_ACTION_APPROVE = "approve";
}
