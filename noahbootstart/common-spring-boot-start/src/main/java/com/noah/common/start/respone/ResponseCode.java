package com.noah.common.start.respone;

/**
 * response接口
 *
 * @author noah
 */
public interface ResponseCode {
    /**
     * 获取返回code
     *
     * @return
     */
    int getCode();

    /**
     * 获取返回的msg
     *
     * @return
     */
    String getMessage();
}
