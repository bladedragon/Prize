package team.redrock.prize.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.redrock.prize.bean.StudentA;
import team.redrock.prize.bean.StudentB;
import team.redrock.prize.mapper.GetPrizerMapper;
import team.redrock.prize.mapper.SpecifiedTypeMapper;
import team.redrock.prize.pojo.response.GetPrizeResponse;

import java.util.List;

@Service
@Slf4j
public class GetprizeAService {

    @Autowired
    GetPrizerMapper getPrizerMapper;
    @Autowired
    SpecifiedTypeMapper specifiedTypeMapper;

    public GetPrizeResponse getPrizeA(String openid,String actid ){




        List<StudentA> students = getPrizerMapper.findStudentA(openid,actid);

        if(students.isEmpty()){
                log.error("no message");
            return new GetPrizeResponse(-3,"No prizer's info");
        }else {
            StudentA student = students.get(0);

            if(students.size()!=1){
                log.error("ZLOG==>Data has repeated insert!" );
            }

            if(student.getStatus()!=0){
                log.info("you has gotten it");
                return new GetPrizeResponse(1,"Fail to get again");
            }else{
                Boolean boo = getPrizerMapper.updateSpecified_type(openid,1);
                System.out.println(boo);
                return new GetPrizeResponse(0,"success");
            }
        }

    }

    public GetPrizeResponse getPrizeB(String openid,String actid){
        StudentB studentB = getPrizerMapper.findStudentB(openid,actid);
        if(studentB.getStuname()!=null){
            return new GetPrizeResponse(1,"Fail to get again ");
        }else{
            getPrizerMapper.insertNonSpecified_type(studentB);
            return new GetPrizeResponse(0,"success");
        }
    }
}
