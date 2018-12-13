package team.redrock.prize.Access_token;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
public class RedisTokenHelper {

    @Autowired(required = true)
    RedisTemplate<String, String> stringRedisTemplate;

    @Autowired
    RedisTemplate<Object, Object> redisTemplate;

//    @Resource(name="stringRedisTemplate")
//    ValueOperations<String, String> ops;
//
//    @Resource(name="redisTemplate")
//    ValueOperations<Object, Object> objOps;

    /**
     * 键值对存储 字符串 ：有效时间3分钟
     *
     * @param tokenType Token的key
     * @param Token     Token的值
     */

    public void save(String tokenType, String Token) {
        this.stringRedisTemplate.opsForValue().set(tokenType, Token, 180, TimeUnit.SECONDS);
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }

    /**
     * 根据key从redis获取value
     *
     * @param tokenType
     * @return String
     */
    public String getToken(String tokenType) {
        return stringRedisTemplate.opsForValue().get(tokenType);
    }

    /**
     * redis 存储一个对象
     *
     * @param key
     * @param obj
     * @param timeout 过期时间  单位：s
     */
    public void saveObject(String key, Object obj, long timeout) {
        this.redisTemplate.opsForValue().set(key, obj, timeout, TimeUnit.SECONDS);
    }

    /**
     * redis 存储一个对象  ,不过期
     *
     * @param key
     * @param
     */
    public void saveObject(String key, String string) {
        this.stringRedisTemplate.opsForValue().set(key, string);
    }

    /**
     * 从redis取出一个对象
     *
     * @param key
     */
    public String getObject(String key) {
        String str;
        try {
            str = this.stringRedisTemplate.opsForValue().get(key);
            if(str==null){
                return "1";
            }
        }catch(NullPointerException e){
           // e.printStackTrace();
            System.out.println("空指针异常");
            return "1";
        }

        return str;
    }

    /**
     * 根据Key删除Object
     *
     * @param key
     */
    public void removeObject(String key) {
        redisTemplate.delete(key);
    }
}
