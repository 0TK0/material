package test;

import com.zyq.MaterialApplication;
import com.zyq.dao.UserDao;
import com.zyq.model.User;
import com.zyq.service.UserService;
import com.zyq.util.MaterialUtil;
import com.zyq.util.SpringContextUtil;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by TK on 2016/12/6.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MaterialApplication.class)
public class UserTest {
    @Autowired
    UserService userService;
    @Autowired
    UserDao userDao;

    @Test
    public void userDaoTest(){

        User user = new User();
        user.setName("tom");
        user.setPassword("123456");
        user.setSalt(UUID.randomUUID().toString().substring(0,5));
        user.setPassword(MaterialUtil.MD5(user.getPassword()+user.getSalt()));
        user.setType(1);
        userDao.addUser(user);
    }

    @Before
    public void setUp(){
        //与test一一对应，有多少test就有多少before
        //在test之前执行
    }

    @After
    public void clear(){
        //与test一一对应，有多少test就有多少before
        //在test之后执行
    }

    @BeforeClass
    public void beforeClass(){
        //全局的 只有一次，最开始执行
    }

    @AfterClass
    public void afterClass(){
        //全局的，只有一次，最后执行
    }

}
