package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class testBase {
    String url;
    String userName;
    String password;
    String ProdName;
    String version;
    String path;
    BufferedReader bufferedReader;
    Properties properties = new Properties();

    public void init() throws IOException {
        //此处对产品名称，版本赋值
        ProdName = "EDR";
        version = "V3_0_1_2";
        if(ProdName.isEmpty()){
            //为空时设置为EDR模块
            ProdName = "EDR";
        }

        if(ProdName!=null && ProdName.equals("EDR")){

            path = " C:\\Users\\admin\\IdeaProjects\\Test_Edr\\src\\main\\resources\\EDR";
        }


        if(version != null){
            // 使用InPutStream流读取properties文件
             bufferedReader = new BufferedReader(new FileReader(path+"\\"+version));
            properties.load(bufferedReader);
            // 获取key对应的value值
        }
        if(version.isEmpty() || Thread.currentThread().getContextClassLoader().getResource(path+"\\"+version) == null){
            bufferedReader = new BufferedReader(new FileReader("C:\\Users\\admin\\IdeaProjects\\Test_Edr\\src\\main\\resources\\EDR\\V3_0_1_2"));
            properties.load(bufferedReader);
        }
        url = properties.getProperty("url");


    }

}
