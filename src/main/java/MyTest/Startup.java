package MyTest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Startup {
    public static void main(String[] args) {
        try {
            //这个类主要的目的其实是为了每次启动都能做一个初始化
            SpringApplication.run(Startup.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
