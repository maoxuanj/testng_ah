import TestCase.DataProviderDemo;
import TestCase.UserTestDemo;
import org.testng.TestNG;

public class testngRun {

    public static void main(String[] args){
        TestNG tng = new TestNG();
        tng.setTestClasses(new Class[] {UserTestDemo.class, DataProviderDemo.class});
        //为了每次运行的结果能保存下来，自定义了结果路径
        tng.setOutputDirectory("C:\\Users\\admin\\IdeaProjects\\Test_Edr\\testng_report_log"+System.currentTimeMillis());
        tng.run();
    }
}