package team.redrock.prize.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import team.redrock.prize.bean.StudentA;
import team.redrock.prize.bean.StudentB;
import team.redrock.prize.exception.ValidException;
import team.redrock.prize.mapper.SpecifiedTypeMapper;
import team.redrock.prize.pojo.response.ShowPrizerAResponse;
import team.redrock.prize.pojo.response.ShowPrizerBResponse;


import java.util.List;

@Service
@Slf4j
public class ShowPrizerService {

    @Autowired
    SpecifiedTypeMapper specifiedTypeMapper;

    public ShowPrizerAResponse showPrizerA(String actid, int start, int pagesize) throws ValidException {
        int total;
        PageHelper.startPage(start, pagesize);
        List<StudentA> studentAS = null;
        List<StudentB> studentBS = null;


                studentAS = specifiedTypeMapper.findStudentA(actid);



                PageInfo<StudentA> page = new PageInfo(studentAS);
                int sum = (int) page.getTotal();

                if (sum % pagesize != 0) {
                    total = sum / pagesize + 1;
                } else {
                    total = sum / pagesize;
                }
                return new ShowPrizerAResponse(200, "success", total, actid, page.getList());
        }


    public ShowPrizerBResponse showPrizerB(String actid, int start, int pagesize) throws ValidException {
        int total;
        PageHelper.startPage(start, pagesize);
        List<StudentB> studentBS = null;


        studentBS = specifiedTypeMapper.findStudentB(actid);


        PageInfo<StudentB> page = new PageInfo(studentBS);
        int sum = (int) page.getTotal();

        if (sum % pagesize != 0) {
            total = sum / pagesize + 1;
        } else {
            total = sum / pagesize;
        }
        return new ShowPrizerBResponse(200, "success", total, actid, page.getList());
    }


}
