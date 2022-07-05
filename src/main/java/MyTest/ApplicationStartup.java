package MyTest;

import Service.EnableExampleConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * spring-boot startup
 *
 */
@SpringBootApplication

/*
这是引入jar包的方式一
 */
//@ComponentScan(basePackages={"com.edr"})

//方式二
@EnableExampleConfig
public class ApplicationStartup
{
    public static void main(String[] args) {
        SpringApplication.run(ApplicationStartup.class, args);
    }
}