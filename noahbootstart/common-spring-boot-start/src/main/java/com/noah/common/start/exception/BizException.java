package com.noah.common.start.exception;

import com.noah.common.start.respone.ResponseCode;
import lombok.Getter;

/**
 * 业务异常
 *
 * @author noah
 */
public class BizException extends RuntimeException {
    @Getter
    private Integer code;

    public BizException(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.code = responseCode.getCode();
    }

    public BizException(ResponseCode responseCode, String message) {
        super(message);
        this.code = responseCode.getCode();
    }

    public BizException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public BizException(String message) {
        super(message);
    }
}
