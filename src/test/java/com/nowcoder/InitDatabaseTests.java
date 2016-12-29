package com.nowcoder;

import com.zyq.MaterialApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MaterialApplication.class)
@Sql("/init-schema.sql")
public class InitDatabaseTests {



	@Test
	public void contextLoads() {

	}
}
