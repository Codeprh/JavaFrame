package com.noah.common.start.respone;

/**
 * 返回枚举code
 *
 * @author noah
 */
public enum ResponseCodeEnum implements ResponseCode {
    SUCCESS(10000, "系统调用成功"),
    PARAM_IS_NULL(40000, "参数不正确"),
    ;

    private final int code;
    private final String message;

    ResponseCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
