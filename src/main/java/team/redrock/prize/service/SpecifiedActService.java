package team.redrock.prize.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import team.redrock.prize.bean.Activity;
import team.redrock.prize.bean.PrizeList;
import team.redrock.prize.bean.ReqStudent;
import team.redrock.prize.bean.StudentA;
import team.redrock.prize.mapper.ActivityMapper;
import team.redrock.prize.mapper.SpecifiedTypeMapper;
import team.redrock.prize.pojo.response.SpecifiedActResponse;
import team.redrock.prize.utils.PosterUtil;
import team.redrock.prize.utils.SessionUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class SpecifiedActService {

    @Autowired
    ActivityMapper activityMapper;
    @Autowired
    SpecifiedTypeMapper specifiedTypeMapper;
    @Autowired
    TemplateMessageService templateMessageService;


    public SpecifiedActResponse createSpecifiedAct(List<PrizeList> typeA, List<String> typeB,String activity, String url, HttpServletRequest request) throws SQLException {
        SimpleDateFormat f_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = f_date.format(new Date());
        long time = System.currentTimeMillis();
        HttpSession session = request.getSession();
        String actid;
          List<String>  actids = activityMapper.SelectActivityId(activity);

          if(actids.isEmpty()){
            actid = getactID(activity);
          }else{
               actid = actids.get(0);
          }

          if(actid.equals("0")){
              actid = getactID(activity);
          }


                log.info("________________________________________sessionname = " + session.getAttribute("SESSIONNAME"));


        List<Map<String, String>> failedMsg = new ArrayList<>();
        for (int i = 0; i < typeA.size(); i++) {

//            TestThread testThread = ApplicationContextProvider.getBean("test",TestThread.class);
//            System.out.println(testThread);

            PrizeList prizeList = typeA.get(i);
            activityMapper.insert(new Activity(activity, (String) session.getAttribute("SESSIONNAME"), url, 1, date, actid, prizeList.getReward()));

            for (int j = 0; j < prizeList.getReqStudents().size(); j++) {
                ReqStudent reqStudent = prizeList.getReqStudents().get(j);
                String msg = prizeList.getSendmsg();
                String openid = PosterUtil.getOpenID(reqStudent.getStuid());
                if (openid.equals("0")) {
                    return new SpecifiedActResponse(-2, "Fail to get openid", actid, null);
                }
                System.out.println("---------" + openid + "---------------");
                StudentA student = new StudentA(openid, reqStudent.getStuname(), reqStudent.getCollege(), reqStudent.getStuid(), Integer.parseInt(reqStudent.getTelephone()), actid, date, prizeList.getReward(), 0);
                specifiedTypeMapper.insert(student);
                int result = getFailedSend(templateMessageService.sendMsg(msg, openid, reqStudent.getStuname(), activity, prizeList.getReward(), "未领取"));


                if (result == 1) {
                    Map<String, String> stuMsg = new HashMap<>();
                    stuMsg.put("stuname", student.getStuname());
                    stuMsg.put("college", student.getCollege());
                    stuMsg.put("stuid", student.getStuid());
                    stuMsg.put("telephone", reqStudent.getTelephone());
                    stuMsg.put("reward",prizeList.getReward());
                    failedMsg.add(stuMsg);
                }
            }
        }

            for(int m =0;m<typeB.size();m++ ){
                activityMapper.insert(new Activity(activity, (String) session.getAttribute("SESSIONNAME"), url,  1, date, actid,typeB.get(m)));
            }


        if (failedMsg.isEmpty()) {
            return new SpecifiedActResponse(0, "success", actid, failedMsg);
        }

        return new SpecifiedActResponse(0,"have fail sending!",actid,failedMsg);
    }


    private int getFailedSend(String httpResponse){
        String text = null;
        Pattern pattern = Pattern.compile("\\\"errmsg\\\":\\\"(.*?)\\\"");
        Matcher matcher = pattern.matcher(httpResponse);
        while (matcher.find()) {
            text = matcher.group(1);
            System.out.println(matcher.group(1));
        }
       log.warn("ZLOG=>errorMsg:"+text);
        log.warn(httpResponse);
        System.out.println(text);
        if(text==null||!text.equals("ok")){

            return 1;
        }
        return 0;
    }


    private  static String  getactID(String activity){
        String longID = SessionUtil.getMD5(activity);
        String actID = longID.substring(0,6);
        return actID;
    }


    @Async
    public void executeAsyncTask(Integer n){
        System.out.println("异步任务执行："+n);
    }



}
