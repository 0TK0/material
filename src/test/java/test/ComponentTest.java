package test;

import com.zyq.MaterialApplication;
import com.zyq.service.ComponentsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by tk on 2017/2/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MaterialApplication.class)
public class ComponentTest {

    @Autowired
    ComponentsService componentsService;

    @Test
    public void getAllTest(){
        System.out.println("start");
        componentsService.getAll();
        System.out.println("end");
    }
}
