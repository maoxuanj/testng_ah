package TestCase;

import MyTest.testBase12;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class openUrl  extends testBase12 {


    @BeforeClass
    public void inittest(){

    }

    @Test(description = "简单的测试",parameters = "")
    public void getUrl() throws IOException {
        init();
        System.out.println(url);

    }


    @Test
    public void tt(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        String fileName = sdf.format(new Date())+"file.txt";
        Double ran;
        ran = Math.random();
        File file = new File("D:\\gongju\\html\\text\\"+ran+fileName);
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("创建成功");
    }







}
