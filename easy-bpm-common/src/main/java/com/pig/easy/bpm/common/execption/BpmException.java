package com.pig.easy.bpm.common.execption;

import com.pig.easy.bpm.common.entityError.EntityError;
import lombok.Builder;

import java.io.Serializable;

/**
 * todo:
 *
 * @author : pig
 * @date : 2020/5/14 16:01
 */
@Builder
public class BpmException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = -1252245323939553787L;

    private EntityError entityError;

    public EntityError getEntityError() {
        return entityError;
    }

    public void setEntityError(EntityError entityError) {
        this.entityError = entityError;
    }

    public BpmException(EntityError entityError) {
        this.entityError = entityError;
    }

    public BpmException(String message, EntityError entityError) {
        super(message);
        this.entityError = entityError;
    }

    public BpmException(String message, Throwable cause, EntityError entityError) {
        super(message, cause);
        this.entityError = entityError;
    }

    public BpmException(Throwable cause, EntityError entityError) {
        super(cause);
        this.entityError = entityError;
    }

    public BpmException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, EntityError entityError) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.entityError = entityError;
    }
}
