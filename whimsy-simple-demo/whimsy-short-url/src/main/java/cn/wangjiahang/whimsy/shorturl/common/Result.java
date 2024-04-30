package cn.wangjiahang.whimsy.shorturl.common;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class Result<T> implements Serializable {

    public static final int SUCCESS_CODE = 200;
    public static final int ERROR_CODE = 400;

    private int code = SUCCESS_CODE;
    private String msg;
    private T data;

    public Result() {}

    public Result(T data) {
        this.data = data;
    }

    public Result(int code, String msg, T date) {
        this.code = code;
        this.msg = msg;
        this.data = date;
    }

    public static <T> Result<T> ok() {
        return new Result<>();
    }

    public static <T> Result<T> ok(T data) {
        return new Result<>(data);
    }

    public static <T> Result<T> error(String msg, T data) {
        return new Result<>(ERROR_CODE, msg, data);
    }

    public static <T> Result<T> error(String msg) {
        return new Result<>(ERROR_CODE, msg, null);
    }
}
