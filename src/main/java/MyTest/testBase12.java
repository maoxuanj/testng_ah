package MyTest;


import com.edr.cloud.core.service.AccessService;
import com.edr.cloud.core.service.AgentConfService;
import com.edr.cloud.core.service.impl.AccessServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
//import com.edr.cloud.base.service.AboutService;
public class testBase12 {
    public static String url;
    public String userName;
    public String password;
    public static String token;
    public static String token_admin;
    public String ProdName;
    public String version;
    public String pathName;
    public BufferedReader bufferedReader;
    public Properties properties = new Properties();
    //jar包为spring boot项目，没法直接使用
//    public AboutService aboutService;
    public AccessService accessService;
    public String linux;
    public String linux_username;
    public String linux_password;
    public String linux_name;
    public static String user_name;

    @Autowired
    public AccessServiceImpl accessServiceImpl;
    @Autowired
    AgentConfService agentConfService;

    public  void  init() throws IOException {
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
        linux = properties.getProperty("linux");
        linux_username = properties.getProperty("linux_username");
        linux_password = properties.getProperty("linux_password");
        linux_name = properties.getProperty("linux_name");
        user_name = properties.getProperty("user_name");
        //url = "https://10.50.38.93";
        if(url ==null){
          System.out.println("地址为空");
          return ;
        }


//        public void getAboutService(AboutService aboutService){
//
//                    this.aboutService = aboutService;
//        }

    }
//
    public AccessService getAccessService(){
        if(accessServiceImpl ==null){
            accessServiceImpl = new AccessServiceImpl();
        }
        return accessServiceImpl;
    }

}
