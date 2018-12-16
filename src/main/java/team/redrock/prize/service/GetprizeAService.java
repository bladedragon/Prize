package team.redrock.prize.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.redrock.prize.bean.StuInfoResponseBean;
import team.redrock.prize.bean.StudentA;
import team.redrock.prize.bean.StudentB;
import team.redrock.prize.bean.WXAccount;
import team.redrock.prize.exception.ValidException;
import team.redrock.prize.mapper.GetPrizerMapper;
import team.redrock.prize.mapper.SpecifiedTypeMapper;
import team.redrock.prize.pojo.response.GetPrizeResponse;
import team.redrock.prize.utils.PosterUtil;
import team.redrock.prize.utils.SessionUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class GetprizeAService {


    @Autowired
    GetPrizerMapper getPrizerMapper;
    @Autowired
    SpecifiedTypeMapper specifiedTypeMapper;

    public GetPrizeResponse getPrizeA(String openid,String actid,String reward ){

        System.out.println(reward);
        StudentA students = getPrizerMapper.findStudentA(openid,actid,reward);
        System.out.println(students);
        log.error(String.valueOf(students));

        if(students==null){
            return new GetPrizeResponse(-3,"No prizer's info");
        }else {
//            StudentA student = students.get(0);

//            if(students.size()!=1){
//                log.error("ZLOG==>Data has repeated insert!" );
//            }

            if(students.getStatus()!=0){
                System.out.println(students.getStatus());
                return new GetPrizeResponse(1,"Fail to get again");

            }else{
                Boolean boo = getPrizerMapper.updateSpecified_type(openid,1);
                return new GetPrizeResponse(200,"success");
            }
        }

    }

    public GetPrizeResponse getPrizeB(WXAccount wxAccount, String actid,String reward) throws ValidException {
        String openid = wxAccount.getOpenid();
        if(null==openid||openid.equals("")){

            return new GetPrizeResponse(-2, "Fail to authorize");
        }
        StuInfoResponseBean sirbean;
        StudentB studentB = getPrizerMapper.findStudentB(openid,actid,reward);
        SimpleDateFormat f_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = f_date.format(new Date());

        if(studentB!=null){
            return new GetPrizeResponse(1,"Fail to get again ");
        }else{
            sirbean = PosterUtil.getStuInfo(openid);
            if(null==sirbean||sirbean.equals("")){
                log.error("ZLOG==>Fail to use Api");
                throw new ValidException("Fail to get stuNum");
            }
            getPrizerMapper.insertNonSpecified_type(new StudentB(sirbean.getRealname(),sirbean.getStuId(),openid,actid,date,reward,getID(reward)));
            return new GetPrizeResponse(200,"success");
        }
    }

    private  static String  getID(String activity){
        String longID = SessionUtil.getMD5(activity);
        String actID = longID.substring(0,6);
        return actID;
    }
}
