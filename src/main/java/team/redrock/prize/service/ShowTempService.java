package team.redrock.prize.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import team.redrock.prize.bean.TempAct;
import team.redrock.prize.exception.ValidException;
import team.redrock.prize.pojo.response.ShowTempReponse;

@Service
public class ShowTempService {
    @Autowired
    RedisTemplate<Object, TempAct> tempActRedisTemplate;

    public ShowTempReponse showTemp(String actid){

    TempAct tempAct = tempActRedisTemplate.opsForValue().get(actid);

    if(tempAct==null){

        return new ShowTempReponse(-2,"cache is not exist",null);
    }
    return new ShowTempReponse(0,"success",tempAct);
    }

}
