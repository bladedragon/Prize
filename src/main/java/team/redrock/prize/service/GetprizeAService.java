package team.redrock.prize.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.redrock.prize.bean.responseBean.StuInfoResponseBean;
import team.redrock.prize.bean.StudentA;
import team.redrock.prize.bean.StudentB;

import team.redrock.prize.exception.ValidException;
import team.redrock.prize.mapper.ActivityMapper;
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
    @Autowired
    ActivityMapper activityMapper;
    @Autowired
    PosterUtil posterUtil;

    public GetPrizeResponse getPrizeA(String openid,String actid,String rewardid ){

        System.out.println(rewardid);
        List<StudentA> students = getPrizerMapper.findStudentA(openid,actid,rewardid);



        String reward = activityMapper.SelectReward(actid,rewardid);

        if(null==reward||reward.equals("")){
            return new GetPrizeResponse(-4,"Can't find activity or reward");
        }

        List<Integer> statuses = activityMapper.SelectStatus(actid);

        if(statuses.get(0)==3){
            return new GetPrizeResponse(-5,"Activity has ended");
        }

        if(null==students||students.size()==0){
            return new GetPrizeResponse(-3,"No prizer's info");
        }else {

            if(students.size()!=1){
                log.error("ZLOG==>Data has repeated insert!" );
            }

            StudentA student = students.get(0);
            if(student.getStatus()!=0){
                System.out.println(student.getStatus());
                return new GetPrizeResponse(1,"Fail to get again");

            }else{
                Boolean boo = getPrizerMapper.updateSpecified_type(openid,1);
                return new GetPrizeResponse(200,"success");
            }
        }

    }

    public GetPrizeResponse getPrizeB(String openid, String actid,String rewardid) throws ValidException {
//        String openid = wxAccount.getOpenid();            //利用openid获取学生的真实姓名
        if(null==openid||openid.equals("")){

            return new GetPrizeResponse(-2, "Fail to authorize");
        }

        String reward = activityMapper.SelectReward(actid,rewardid);

        if(null==reward||reward.equals("")){
            return new GetPrizeResponse(-4,"Can't find activity or reward");
        }

        List<Integer> statuses = activityMapper.SelectStatus(actid);

        if(statuses.get(0)==3){
            return new GetPrizeResponse(-5,"Activity has ended");
        }

        StuInfoResponseBean sirbean;
        StudentB studentB = getPrizerMapper.findStudentB(openid,actid,rewardid);
        SimpleDateFormat f_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = f_date.format(new Date());

        if(studentB!=null){
            return new GetPrizeResponse(1,"Fail to get again ");
        }else{
            sirbean = posterUtil.getStuInfo(openid);
            if(null==sirbean||sirbean.equals("")){
                log.error("ZLOG==>Fail to use Api");
                throw new ValidException("Fail to get stuNum");
            }


            log.info("info==》"+sirbean.getInfo());

            getPrizerMapper.insertNonSpecified_type(new StudentB(sirbean.getRealname(),sirbean.getStuId(),openid,actid,date,reward,rewardid,1,0));

            return new GetPrizeResponse(200,"success");
        }
    }

    private  static String  getID(String activity){
        String longID = SessionUtil.getMD5(activity);
        String actID = longID.substring(0,6);
        return actID;
    }
}
