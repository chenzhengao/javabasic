import com.sun.org.apache.bcel.internal.util.ClassPath;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;

/**
 * @program: tx_springdataredis
 * @description:
 * @author: czg
 * @create: 2019-10-11 17:16
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-redis.xml"})
public class RedisTest {

    @Autowired
    RedisTemplate redisTemplate;



    @Test
    public void testRedis(){
        //字符串操作
        redisTemplate.boundValueOps("redis").set("hello springDataRedis");

        redisTemplate.boundValueOps("redis").get();

        redisTemplate.delete("redis");

        //set操作
        redisTemplate.boundSetOps("set").add("setValue");
        redisTemplate.boundSetOps("set").add("setValue1");
        redisTemplate.boundSetOps("set").add("setValue2");
        redisTemplate.boundSetOps("set").add("setValue3");

        //查看
        Set set = redisTemplate.boundSetOps("set").members();

        //删除元素
        redisTemplate.boundSetOps("set").remove("setValue");

        //删除key
        redisTemplate.delete("set");

    }


    @Test
    public void testRedisList(){
        //List操作(栈操作)
        //左压栈
        redisTemplate.boundListOps("key").leftPush("左压1");
        redisTemplate.boundListOps("key").leftPush("左压2");
        redisTemplate.boundListOps("key").leftPush("左压3");
        redisTemplate.boundListOps("key").leftPush("左压4");
        //起始下标0-结束下标5的值
        redisTemplate.boundListOps("key").range(0,5);//输出左压4321

        String key = (String) redisTemplate.boundListOps("key").leftPop();//左弹出  输出左压4

        String key1 = (String) redisTemplate.boundListOps("key").rightPop();//右弹出  输出左压1
        //删除指定下标的值
        redisTemplate.boundListOps("key").remove(0,"左压2");


        //左压栈
        redisTemplate.boundListOps("key").rightPush("右压1");
        redisTemplate.boundListOps("key").rightPush("右压2");
        redisTemplate.boundListOps("key").rightPush("右压3");
        redisTemplate.boundListOps("key").rightPush("右压4");
        //起始下标0-结束下标5的值
        redisTemplate.boundListOps("key").range(0,5);//右压1234
        //删除
        redisTemplate.delete("key");
        redisTemplate.boundListOps("key").range(0,-1);//输出全部
    }


    @Test
    public void testRedisHash(){
        //Hash操作
        //添加元素key-map
        redisTemplate.boundHashOps("MapKey").put("key1","value1");
        redisTemplate.boundHashOps("MapKey").put("key2","value2");
        redisTemplate.boundHashOps("MapKey").put("key3","value3");
        redisTemplate.boundHashOps("MapKey").put("key4","value4");

        //获得指定key对应的值
        Object o = redisTemplate.boundHashOps("MapKey").get("key1");
        //删除多个元素
        redisTemplate.boundHashOps("MapKey").delete("key1","key2");

        //获得全部的key，也就是key集合
        Set mapKey1 = redisTemplate.boundHashOps("MapKey").keys();
        //获得全部的value，也就是valu集合
        List values = redisTemplate.boundHashOps("MapKey").values();
    }

    @Test
    public void testRedisZset(){
        //v1是下标
        redisTemplate.boundZSetOps("zSet").add("value1",1);
        redisTemplate.boundZSetOps("zSet").add("value2",2);
        redisTemplate.boundZSetOps("zSet").add("value4",4);
        redisTemplate.boundZSetOps("zSet").add("value3",3);
        //获得集合元素的大小
        Long card = redisTemplate.boundZSetOps("zSet").zCard();//4

        //输出全部
        Set zSet = redisTemplate.boundZSetOps("zSet").range(0, -1);
        //获得对应的v1也就是下标
        Long rank = redisTemplate.boundZSetOps("zSet").rank("value1");

        //移除多项
        redisTemplate.boundZSetOps("zSet").remove("value1","value2");
        //从下标1开始移除到下标2（包括2）
        Long zSet1 = redisTemplate.boundZSetOps("zSet").removeRange(1, 2);

        redisTemplate.delete("zSet");
    }

    @Test
    public void testRedis2(){
        Jedis jedis=new Jedis("192.168.74.129",6379);

        System.out.println(jedis);

    }


}
