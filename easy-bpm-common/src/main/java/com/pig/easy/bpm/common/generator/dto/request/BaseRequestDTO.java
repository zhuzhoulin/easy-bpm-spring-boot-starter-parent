package com.pig.easy.bpm.common.generator.dto.request;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * todo:
 *
 * @author : pig
 */
@Data
@ToString
public class BaseRequestDTO implements Serializable {

    private static final long serialVersionUID = -4157414406762155070L;

    protected String refreshAccessToken;

    protected String accessToken;

    protected Long  currentUserId;

    protected String currentUserName;

    protected String currentRealName;

    protected String currentTenantId;

    protected String currentSystem;

    protected String currentPlatform;

}
