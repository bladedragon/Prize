package team.redrock.prize.config;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.net.UnknownHostException;

@Configuration
public class RedisConfig {


    @Bean
    public RedisTemplate<Object, Object> objectRedisTemplate(
            RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<Object, Object>();
        template.setConnectionFactory(redisConnectionFactory);
        //使用json的序列化器
        Jackson2JsonRedisSerializer ser = new Jackson2JsonRedisSerializer<Object>(Object.class);
//        JdkSerializationRedisSerializer ser = new JdkSerializationRedisSerializer();
        template.setDefaultSerializer(ser);                 //相当于key的序列化类型和value的序列化类型
        return template;
    }

    @Bean
    public RedisTemplate<String, String> StringRedistemplate(
            RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
        RedisTemplate<String, String> template = new RedisTemplate<String, String>();
        template.setConnectionFactory(redisConnectionFactory);
        //使用json的序列化器
        Jackson2JsonRedisSerializer ser = new Jackson2JsonRedisSerializer<String>(String.class);
//       JdkSerializationRedisSerializer ser = new JdkSerializationRedisSerializer();
        template.setDefaultSerializer(ser);                 //相当于key的序列化类型和value的序列化类型
        return template;
    }
}




