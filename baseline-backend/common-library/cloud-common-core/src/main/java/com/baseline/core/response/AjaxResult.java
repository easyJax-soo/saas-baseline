package com.baseline.core.response;


import com.baseline.jackson.JacksonUtil;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * 统一数据返回结果
 */

@Data
public class AjaxResult {
    private int status;
    private String message;
    private Object data;
    private final Long timestamp = System.currentTimeMillis();

    public AjaxResult() {
    }


    public int getStatus() {
        return this.status;
    }

    public String getMessage() {
        return this.message;
    }


    public Long getTimestamp() {
        return this.timestamp;
    }


    public AjaxResult(int status, String msg) {
        this.status=status;
        this.message=msg;
    }


    @Override
    public String toString() {
        return JacksonUtil.toJsonString(this);
    }

    public AjaxResult(int status, String msg, Object data) {
        this.status=status;
        this.message=msg;
        this.data=data;
    }

    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static AjaxResult success() {
        return AjaxResult.success("操作成功");
    }


    /**
     * 返回成功数据
     *
     * @return 成功消息
     */
    public static AjaxResult success(Object data) {
        return AjaxResult.success("操作成功", data);
    }


    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @return 成功消息
     */
    public static AjaxResult success(String msg) {
        return AjaxResult.success(msg, null);
    }


    /**
     * 返回成功消息
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static AjaxResult success(String msg, Object data) {
        return new AjaxResult(HttpStatus.OK.value(), msg, data);
    }


    /**
     * 返回错误消息
     *
     * @return AjaxResult
     */
    public static AjaxResult error() {
        return AjaxResult.error("操作失败");
    }


    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @return 警告消息
     */
    public static AjaxResult error(String msg) {
        return AjaxResult.error(msg, null);
    }


    /**
     * 返回错误消息
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public static AjaxResult error(String msg, Object data) {
        return new AjaxResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg, data);
    }



    public static AjaxResult unauthorized(String msg){
        return new AjaxResult(HttpStatus.UNAUTHORIZED.value(), msg!=null?msg:"尚未登录");
    }


}
