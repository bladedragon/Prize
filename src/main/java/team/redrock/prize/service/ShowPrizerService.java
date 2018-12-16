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


import java.util.List;

@Service
@Slf4j
public class ShowPrizerService {

    @Autowired
    SpecifiedTypeMapper specifiedTypeMapper;

    public ShowPrizerAResponse showPrizer(String actid, int type, int start, int pagesize) throws ValidException {
        int total;
        PageHelper.startPage(start, pagesize);
        List<StudentA> studentAS = null;
        List<StudentB> studentBS = null;

        switch (type) {

            case 1:
                studentAS = specifiedTypeMapper.findStudentA(actid);
                break;
            case 0:
                studentBS = specifiedTypeMapper.findStudentB(actid);
                break;

            default:
                throw new ValidException("Type cannot be empty");
        }
//                studentAS.addAll(studentBS);

                PageInfo<StudentA> page = new PageInfo<>(studentAS);
                int sum = (int) page.getTotal();

                if (sum % pagesize != 0) {
                    total = sum / pagesize + 1;
                } else {
                    total = sum / pagesize;
                }
                return new ShowPrizerAResponse(200, "success", total, actid, page.getList());
        }


    }
