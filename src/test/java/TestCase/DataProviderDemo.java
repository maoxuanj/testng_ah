package TestCase;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class dataProviteDemo {
    /*
    这个demo主要是为了演示参数构造器的使用
     */


    public class TestLogin {
        @Test(dataProvider = "testlogin")
        public void testlogin(String username, String password) {
            System.out.println("用户名为" + username + "  " + "密码为" + password);
        }

        @DataProvider(name = "testlogin")
        public Object[][] testlogin() {
            return new Object[][]{
                    {"", "12345678"}, {"dandan", ""}, {"dandan", "12345678"}
            };
        }
    }

}
