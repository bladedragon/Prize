package team.redrock.prize.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.redrock.prize.bean.StudentA;
import team.redrock.prize.mapper.SpecifiedTypeMapper;
import team.redrock.prize.pojo.response.ShowPrizerResponse;

import java.util.List;

@Service
@Slf4j
public class ShowPrizerService {

    @Autowired
    SpecifiedTypeMapper specifiedTypeMapper;

    public ShowPrizerResponse showPrizer(String actid,int start,int pagesize){

        PageHelper.startPage(start,pagesize);

        List<StudentA> studentAS = specifiedTypeMapper.findStudentA(actid);

        PageInfo<StudentA> page = new PageInfo<>(studentAS);

        for(int i =0;i<page.getList().size();i++){
            System.out.println(page.getList().get(i));
        }

        return new ShowPrizerResponse(200,"success",page.getSize(),actid ,page.getList());
    }
}
