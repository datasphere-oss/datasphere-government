package com.datasphere.government.dl.common;

import java.util.*;

/**
 * json封装类
 */
public class JsonWrapper {
    public static int DEFAULT_SUCCESS_CODE;
    public static int DEFAULT_FAILURE_CODE;
    
    static {
        JsonWrapper.DEFAULT_SUCCESS_CODE = 0;
        JsonWrapper.DEFAULT_FAILURE_CODE = -1;
    }

    /**
     * 默认成功的状态码：0，自定义message
     * @param data
     * @param message
     * @return
     */
    public static Map<String, Object> defaultSuccessWrapper(final Object data, final Object message) {
        final HashMap<String, Object> map = new HashMap<>();
        map.put("code", JsonWrapper.DEFAULT_SUCCESS_CODE);
        map.put("data", data);
        map.put("message", message);
        return map;
    }

    /**
     * 成功：无返回数据，不需要自定义message
     * @return
     */
    public static Map<String, Object> successWrapper() {
        return defaultSuccessWrapper(null, "成功");
    }

    /**
     * 默认失败的状态码：-1
     * @param data
     * @return
     */
    public static Map<String, Object> defaultFailureWrapper(final Object data) {
        return failureWrapper(JsonWrapper.DEFAULT_FAILURE_CODE, data);
    }

    /**
     * 自定义失败的状态码
     * @param stateNo
     * @param message
     * @return
     */
    public static Map<String, Object> failureWrapper(final int stateNo, final Object message) {
        final HashMap<String, Object> map = new HashMap<>();
        map.put("code", stateNo);
        map.put("data", null);
        map.put("message", message);
        return map;
    }


}
