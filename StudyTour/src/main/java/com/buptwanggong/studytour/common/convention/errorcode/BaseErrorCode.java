package com.buptwanggong.studytour.common.convention.errorcode;

/**
 * 基础错误码定义
 */
public enum BaseErrorCode implements IErrorCode {

    // ==========  客户端错误 ==========
    CLIENT_ERROR("A000001", "用户端错误"),

    // ==========  用户注册错误 ==========
    USER_REGISTER_ERROR("A000100", "用户注册错误"),
    USER_NAME_VERIFY_ERROR("A000110", "用户名校验失败"),
    USER_NAME_EXIST_ERROR("A000111", "用户名已存在"),
    PASSWORD_VERIFY_ERROR("A000120", "密码校验失败"),

    // ==========  系统请求缺少幂等Token ==========
    IDEMPOTENT_TOKEN_NULL_ERROR("A000200", "幂等Token为空"),
    IDEMPOTENT_TOKEN_DELETE_ERROR("A000201", "幂等Token已被使用或失效"),

    // ==========  系统请求操作频繁 ==========
    FLOW_LIMIT_ERROR("A000300", "当前系统繁忙，请稍后再试"),

    // ==========  系统执行出错 ==========
    SERVICE_ERROR("B000001", "系统执行出错"),
    // ==========  系统执行超时 ==========
    SERVICE_TIMEOUT_ERROR("B000100", "系统执行超时"),

    // ========== 调用第三方服务出错 ==========
    REMOTE_ERROR("C000001", "调用第三方服务出错");

    private final String code;

    private final String message;

    BaseErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}
