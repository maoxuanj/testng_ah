package com.test.utils;

import com.alibaba.fastjson.JSONObject;


public class AssertUtil {

    //判断接口调用是否成功，一般根据code为200或者message为success
    public static final boolean ifsuccess(JSONObject object){
        Boolean ifsuccess =false;
       if(object.get("error_code")!=null){
           if(object.get("error_code").equals("200")){
               ifsuccess =true;
           }
       }
        if(object.get("message")!=null){
            if(object.get("message").equals("success")){
                ifsuccess =true;
            }
        }

        if(ifsuccess==false){
            message(object);
        }
        //后续有其他方式可以继续补充
        return ifsuccess;
    }

    public static final String message(JSONObject object){
        String message ="用例失败，失败原因为：";
        if(object.get("message")!=null){
            message = object.get("message").toString();
        }
        //后续有其他方式可以继续补充
        return message;
    }

}
