package com.baseline.common.constant;

/**
 * @author bryant
 * @date 2025/11/26
 **/
public class MessageInfoConstants {
    /**
     * 消息保存类型
     */
    public static class SaveType {
        /**
         * 按照用户
         */
        public static final Integer USER = 1;

        /**
         * 按照角色
         */
        public static final Integer ROLE = 2;

        /**
         * 全部
         */
        public static final Integer ALL = 3;
    }

    /**
     * 阅读状态
     */
    public static class ReadStatus {
        /**
         * 未读
         */
        public static final Integer UNREAD = 0;
        /**
         * 已读
         */
        public static final Integer READ = 1;
    }

    /**
     * 用户类型
     */
    public static class UserType {
        /**
         * 后台
         */
        public static final Integer ADMIN = 1;
        /**
         * 微信小程序
         */
        public static final Integer WECHAT = 2;
    }
}
