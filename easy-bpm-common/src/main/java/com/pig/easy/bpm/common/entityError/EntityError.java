package com.pig.easy.bpm.common.entityError;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * todo:
 *
 * @author : pig
 * @date : 2020/5/7 15:37
 */
@Data
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class EntityError implements Serializable {

    private static final long serialVersionUID = 1147559618212462501L;
    private int code;
    private String message;

    public static final EntityError SUCCESS = new EntityError(200, "success");
    public static final EntityError ILLEGAL_ARGUMENT_ERROR = new EntityError(400, "参数非法");
    public static final EntityError ILLEGAL_LOGIN_ERROR = new EntityError(401, "Unauthorized");
    public static final EntityError ILLEGAL_ACCESS_ERROR = new EntityError(403, "ILLEGAL ACCESS ERROR");
    public static final EntityError ILLEGAL_PATH_ERROR = new EntityError(404, "请求路径不存在！");
    public static final EntityError SYSTEM_ERROR = new EntityError(500, "系统错误");
    public static final EntityError ILLEGAL_CHECK_ERROR = new EntityError(400, "参数校验不通过");
    public static final EntityError DATA_NOT_FOUND_ERROR = new EntityError(501, "数据不存在");
    public static final EntityError USERNAME_OR_PASSWORD_ERROR = new EntityError(502, "用户名或密码出错！");

    public static final EntityError UNPUBLISH_PROCESS_DETAIL_DEFAULT_ERROR = new EntityError(503, "未发布流程详细不允许设置为默认版本，请先发布！");
    public static final EntityError UNPUBLISH_PROCESS_START_ERROR = new EntityError(504, "不允许发起未发布流程！");
    public static final EntityError SUBMIT_REPET_APPLY_ERROR = new EntityError(505, "请勿重复提交单据！");
    public static final EntityError SAVE_REPET_APPLY_ERROR = new EntityError(505, "数据已提交，请勿重复保存草稿！");
    public static final EntityError TASK_HAS_COMPLETED = new EntityError(506, "当前任务已完成，请刷新页面！");
    public static final EntityError TASK_CLAIM_BY_OTHER = new EntityError(507, "当前任务已被其他人认领，请刷新页面！");

    public EntityError(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
