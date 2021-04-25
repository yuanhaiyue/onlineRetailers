package com.yuan.online.exception

import lombok.Data

/**
 *  描述     异常枚举
 */
@Data
enum class MallExceptionEnum(
    /**
     *异常码
     */
    var code: Int, var msg: String
) {
    NEED_USER_NAME(10001,"用户名不能为空"),
    NEED_PASSWORD(10002,"密码不能为空"),
    PASSWORD_TOO_SHORT(10002,"密码长度不能小于8位"),
    NAME_EXISTED(10004,"不允许重名,注册失败"),
    INSERT_FAILED(10005,"插入失败，请重试"),
    WRONG_PASSWORD(10006,"密码错误"),
    SYSTEM_ERROR(20000,"系统异常");

}