package Service;

import MyTest.ApplicationStartup;
import MyTest.testBase12;
import com.edr.cloud.core.model.vo.LianRuanUrlVO;
import com.edr.cloud.core.service.AccessService;
import com.edr.cloud.core.service.impl.AgentConfServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertNotNull;

public class AccessServiceTest extends testBase12 {
    AccessService accessService;
    @BeforeClass
    public void inittest(){
        getAccessService();
    }



    @Test
    public void Test1() throws Exception {
        LianRuanUrlVO vo = accessServiceImpl.getUrl();
        if(vo.getUrl()==null){
            System.out.println("没有对应的Url，请检查地址");
        }
//        System.out.println(accessServiceImpl.getUrl());
    }

    @Test
    public void Test2() throws Exception {
        AgentConfServiceImpl conf = new AgentConfServiceImpl();
        System.out.println(conf.findById("123456"));
    }



    @SpringBootTest(classes = ApplicationStartup.class)
    public class TestBase {
        @Autowired
        private AccessService accessService;

        @Test
        public void getUrlTest() throws Exception {
            LianRuanUrlVO url = accessService.getUrl();
            assertNotNull(url);
        }
    }
}
