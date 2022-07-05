package com.test.utils;

import com.alibaba.fastjson.JSONObject;

public class JSONUtils {
    /*
    做一个封装，把string装换成json，然后根据key值获取其中呃value，最后转换成string输出
    否则每次都要做格式转换看起来不舒服，尤其是断言中经常会使用到转换   后续可以考虑将result结果封装
     */
    public static String JSONchange(String string_toJson,String key){
        JSONObject params = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(string_toJson);
        if(jsonObject.get(key).toString() ==null){
            return key+"没有返回值";
        }
        return jsonObject.get(key).toString();
    }
}
