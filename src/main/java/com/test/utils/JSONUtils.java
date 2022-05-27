package com.test.utils;

import com.alibaba.fastjson.JSONObject;

public class JSONUtils {
    /*
    做一个封装，把string装换成json，然后根据key值获取其中呃value，最后转换成string输出
     */
    public static String JSONchange(String string_toJson){
        JSONObject params = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(string_toJson);
        return jsonObject.toJSONString();
    }
}
