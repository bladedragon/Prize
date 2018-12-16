package team.redrock.prize.pojo;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import team.redrock.prize.bean.StudentA;
import team.redrock.prize.mapper.ActivityMapper;
import team.redrock.prize.mapper.SpecifiedTypeMapper;

import javax.annotation.Resource;
import java.util.List;

@Component("test")
@Scope("prototype")
@Slf4j
public class TestThread  extends Thread{

    @Autowired
    private SpecifiedTypeMapper specifiedTypeMapper;

//    @Resource
//    private  templateMessageService;

    public void run() {

        List<StudentA> list = specifiedTypeMapper.findStudentA("93e3ad");

        System.out.println(list.size());
        System.out.println(list.isEmpty());

        log.info("线程:"+Thread.currentThread().getName()+"运行中.....");
    }
}
