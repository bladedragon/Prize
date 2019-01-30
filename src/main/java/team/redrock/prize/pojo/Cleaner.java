package team.redrock.prize.pojo;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import team.redrock.prize.bean.TempAct;
import team.redrock.prize.mapper.ActivityMapper;

import java.util.Set;


@Component
@Slf4j
public class Cleaner {

    @Autowired
    ActivityMapper activityMapper;
    @Autowired
    private RedisTemplate<Object, TempAct> tempActRedisTemplate;

//    @Scheduled(fixedDelay = 1000)
    public void RedisCleaner(){
        System.out.println("定时启动");

        Set<Object> keys = tempActRedisTemplate.keys("*");
        for(Object key:keys){
            log.warn((String) key);
        }

    }

}
