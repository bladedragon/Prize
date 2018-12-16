package team.redrock.prize.Access_token;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class RedisTokenHelper {

    @Autowired(required = true)
    RedisTemplate<String, String> stringRedisTemplate;

    @Autowired
    RedisTemplate<Object, Object> redisTemplate;


    /**
     * 键值对存储 字符串 ：有效时间3分钟
     *
     * @param tokenType Token的key
     * @param Token     Token的值
     */

    public void save(String tokenType, String Token) {
        this.stringRedisTemplate.opsForValue().set(tokenType, Token, 5400, TimeUnit.SECONDS);

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
           log.error("ZLOG==>NullPoint Exception");
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
