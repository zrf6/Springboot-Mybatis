package com;

import com.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void contextLoads() {
        redisTemplate.opsForValue().set("key", "value");
        String value = (String) redisTemplate.opsForValue().get("key");
        List<User> list = Arrays.asList(new User[]{new User(1, "张三", "1234", new Date(), "D://"), new User(2, "李四", "1234", new Date(), "E://")});
        System.out.println(list);
        //redis存储list
        redisTemplate.opsForList().rightPushAll("list", list);
        //redis取list
        System.out.println("list的数据为：" + redisTemplate.opsForList().range("list", 0, -1));
    }

}
