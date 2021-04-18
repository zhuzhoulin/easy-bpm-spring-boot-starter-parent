package com.pig.easy.bpm.api.dto.request;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * todo:
 *
 * @author : pig
 * @date : 2020/5/28 20:32
 */

@Data
@ToString
public class BaseRequestPageDTO implements Serializable {

    private static final long serialVersionUID = 8286466517414987086L;

    protected String system;

    protected String accessToken;

    protected Long  currentUserId;

    protected String currentUserName;

    protected String currentRealName;

    protected String tenantId;

    protected String platform;

    protected Integer pageIndex;

    protected Integer pageSize;

}
