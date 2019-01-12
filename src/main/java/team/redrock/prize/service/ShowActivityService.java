package team.redrock.prize.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import team.redrock.prize.bean.ShowAct;

import team.redrock.prize.mapper.ActivityMapper;
import team.redrock.prize.pojo.response.ShowActivityResponse;


import java.util.List;

@Service
@Slf4j
public class ShowActivityService {

    @Autowired
    ActivityMapper activityMapper;

    public ShowActivityResponse showActivity(int start,int pagesize){
long time = System.currentTimeMillis();
        System.out.println("-----------------------"+(System.currentTimeMillis()-time)+"-------------------------");
        PageHelper.startPage(start,pagesize);
        List<ShowAct> activities =activityMapper.SelectActAll();

        System.out.println("-----------------------"+(System.currentTimeMillis()-time)+"-------------------------");
        System.out.println("----"+activities);
        PageInfo<ShowAct> page = new PageInfo<>(activities);

        int sum = (int) page.getTotal();
        int total = 0;
        if(sum%pagesize!=0){
            total = sum/pagesize+1;
        }else{
            total = sum/pagesize;
        }
        System.out.println("-----------------------"+(System.currentTimeMillis()-time)+"-------------------------");
        return new ShowActivityResponse(200,"success",total ,page.getList());
    }
}
