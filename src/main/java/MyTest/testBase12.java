package MyTest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class testBase12 {
    public String url;
    public String userName;
    public String password;
    public String ProdName;
    public String version;
    public String pathName;
    public BufferedReader bufferedReader;
    public Properties properties = new Properties();

    public  void init() throws IOException {
        new Startup();
        //此处对产品名称，版本赋值
        ProdName = "EDR";
        version = "V3_0_1_2";
        if(ProdName.isEmpty()){
            //为空时设置为EDR模块
            ProdName = "EDR";
        }

        if(ProdName!=null && ProdName.equals("EDR")){

            pathName = " C:\\Users\\admin\\IdeaProjects\\Test_Edr\\src\\main\\resources\\EDR";
        }


        if(version != null){
            InputStream in = testBase12.class.getClassLoader().getResourceAsStream("EDR/v3_0_1_2.properties");
            System.out.println(in);
            properties.load(in);
            System.out.println(properties);
        }else if(version.isEmpty() || Thread.currentThread().getContextClassLoader().getResource(pathName+"\\"+version) == null){
            bufferedReader = new BufferedReader(new FileReader("C:\\Users\\admin\\IdeaProjects\\Test_Edr\\src\\main\\resources\\EDR\\V3_0_1_2.properties"));
            properties.load(bufferedReader);
        }
        url = properties.getProperty("url");




    }
}
