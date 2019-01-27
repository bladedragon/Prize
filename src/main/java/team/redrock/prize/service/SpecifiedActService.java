package team.redrock.prize.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import team.redrock.prize.bean.Activity;
import team.redrock.prize.bean.PrizeList;
import team.redrock.prize.bean.ReqStudent;
import team.redrock.prize.bean.StudentA;
import team.redrock.prize.exception.ValidException;
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
    @Autowired
    AsyncTaskService asyncTaskService;



    public SpecifiedActResponse createSpecifiedAct(List<PrizeList> typeA, List<String> typeB,String activity, String url, HttpServletRequest request) throws SQLException, ValidException {
          final int[] result = {-1};
        SimpleDateFormat f_date = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String date = f_date.format(new Date());

        HttpSession session = request.getSession();
        String actid;
        List<String>  actids = activityMapper.SelectActivityId(activity);

          if(actids.isEmpty()){
            actid = getID(activity);
          }else{
               actid = actids.get(0);
          }

          if(actid.equals("0")){
              actid = getID(activity);
          }


          log.info("________________________________________sessionname = " + session.getAttribute("SESSIONNAME"));

          Map<String,String> Arewards = new HashMap<>();
        List<Map<String, String>> failedMsg = new ArrayList<>();
        String rewardID;
        for (int i = 0; i < typeA.size(); i++) {

            PrizeList prizeList = typeA.get(i);
            rewardID = getID(prizeList.getReward());
            activityMapper.insert(new Activity(activity, (String) session.getAttribute("SESSIONNAME"), url, 1, date, actid, prizeList.getReward(),rewardID));

            for (int j = 0; j < prizeList.getReqStudents().size(); j++) {
                ReqStudent reqStudent = prizeList.getReqStudents().get(j);
                String msg = prizeList.getSendmsg();
                String openid = PosterUtil.getOpenID(reqStudent.getStuid());

                if (openid.equals("0")) {
                    throw new ValidException("Fail to get openid");
                }
                System.out.println("---------" + openid + "---------------");
                StudentA student = new StudentA(openid, reqStudent.getStuname(), reqStudent.getCollege(), reqStudent.getStuid(), Integer.parseInt(reqStudent.getTelephone()), actid, date, prizeList.getReward(), 0,rewardID);
                activityMapper.deleteActTemp(actid);
                specifiedTypeMapper.insert(student);
                Arewards.put(prizeList.getReward(),rewardID);

//
//                try {
//                    String response = asyncTaskService.executeAsyncTask(openid,msg, activity, prizeList.getReward(), date, prizeList.getPrizeDate(), prizeList.getRemark()).get();
//                    result = getFailedSend(response);
//
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                }
                    new Thread(() ->{
                        try {
                            result[0]=getFailedSend(templateMessageService.sendMsg(openid,msg, activity, prizeList.getReward(), date, prizeList.getPrizeDate(), prizeList.getRemark()));
                            Thread.sleep(1000);
                            log.error("内部地result"+result[0]);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                }).start();

                log.error("result:"+result[0]);
                if (result[0] == 1) {          //每个线程都会加1
                    Map<String, String> stuMsg = new HashMap<>();
                    stuMsg.put("stuname", student.getStuname());
                    stuMsg.put("college", student.getCollege());
                    stuMsg.put("stuid", student.getStuid());
                    stuMsg.put("telephone", reqStudent.getTelephone());
                    stuMsg.put("reward",prizeList.getReward());
                    failedMsg.add(stuMsg);
                    log.info("发送失败+1");

                }
            }
        }

        Map<String,String> Brewards = new HashMap<>();
            for(int m =0;m<typeB.size();m++ ){
                rewardID = getID(typeB.get(m));
                activityMapper.insert(new Activity(activity, (String) session.getAttribute("SESSIONNAME"), url,  1, date, actid,typeB.get(m),rewardID));
                Brewards.put(typeB.get(m),rewardID);
            }


        if (failedMsg.isEmpty()) {
            return new SpecifiedActResponse(200, "success", actid, Arewards,Brewards,failedMsg);
        }

        return new SpecifiedActResponse(200,"have fail sending!",actid,Arewards,Brewards,failedMsg);
    }


    public int getFailedSend(String httpResponse){
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


    private  static String  getID(String activity){
        String longID = SessionUtil.getMD5(activity);
        String actID = longID.substring(0,6);
        return actID;
    }






}
