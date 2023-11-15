package com.test.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


public class GetidUtil {

    //每次通过name去获取id太麻烦了，封装一个方法，以后调用即可
    public static final String getId(String exceptName, String actName , JSONArray objectArray, Integer totallist){
        String id = null;
       for(int i=0;i<totallist;i++){
           if(objectArray.getJSONObject(i).get(actName).toString().equals(exceptName)){
               id = objectArray.getJSONObject(i).get("id").toString();
               break;
           }
       }
        return id;
    }

    //下面这个方式入参写起来更轻松，为了方便看懂上面方法依旧保留
    public static final String getId_new(String exceptName, String actName , JSONObject json){
        String id = null;
        JSONArray objectArray = json.getJSONObject("data").getJSONArray("list");
        Integer total = new Integer(json.getJSONObject("data").get("total").toString());
        for(int i=0;i<total;i++){
            if(objectArray.getJSONObject(i).get(actName).toString().equals(exceptName)){
                id = objectArray.getJSONObject(i).get("id").toString();
                break;
            }
        }
        return id;
    }


    //这个是因为有时候data不是一个jsonArray而是一个JsonObject
    public static final String getId_no_list(String exceptName, String actName , JSONObject json){
        String id = null;
        JSONArray objectArray = json.getJSONArray("data");
        Integer total = new Integer(json.getJSONArray("data").size());
        for(int i=0;i<total;i++){
            if(objectArray.getJSONObject(i).get(actName).toString().equals(exceptName)){
                id = objectArray.getJSONObject(i).get("id").toString();
                break;
            }
        }
        return id;
    }




}
