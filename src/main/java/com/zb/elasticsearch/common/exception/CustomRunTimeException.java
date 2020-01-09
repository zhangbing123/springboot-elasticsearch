package com.zb.elasticsearch.common.exception;

/**
 * @Description: <p>自定义运行时基础异常类</p>
 * @Author: zhangbing
 * @CreateDate: 2019/5/29 17:11
 */
public class CustomRunTimeException extends RuntimeException {
    /**
     * 错误返回时的错误信息
     */
    private final String message;

    /**
     * 错误返回时的错误码
     */
    private final String code;



    public CustomRunTimeException(String code, String message) {
        super(message);
        this.message = message;
        this.code = code;
    }

    public CustomRunTimeException(String message) {
       this(null,message);
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }
}
