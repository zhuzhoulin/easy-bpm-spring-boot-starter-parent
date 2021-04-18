package com.pig.easy.bpm.api.dto.response;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * todo:
 *
 * @author : pig
 * @date : 2020/5/14 15:47
 */
@ToString
@Data
public class BaseResponseDTO implements Serializable {

    private static final long serialVersionUID = -1080540871850206177L;

    protected Integer validState;

    protected LocalDateTime createTime;

    protected LocalDateTime updateTime;

}
