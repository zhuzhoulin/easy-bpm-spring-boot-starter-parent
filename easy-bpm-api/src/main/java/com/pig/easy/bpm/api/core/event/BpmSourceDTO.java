package com.pig.easy.bpm.api.core.event;

import lombok.*;

import java.io.Serializable;

/**
 * todo:
 *
 * @author : pig
 * @date : 2020/5/20 17:18
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BpmSourceDTO<T> implements Serializable {

    private static final long serialVersionUID = 2654005152235729593L;

    /**
     * 监听类型
     *
     */
    private BpmEventType eventType;

    /**
     * 数据
     *
     */
    private T data;

    private Class classClz;
}
