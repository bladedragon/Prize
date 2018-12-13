package team.redrock.prize.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.redrock.prize.bean.Activity;
import team.redrock.prize.bean.ReqStudent;
import team.redrock.prize.bean.StudentA;
import team.redrock.prize.mapper.ActivityMapper;
import team.redrock.prize.mapper.SpecifiedTypeMapper;
import team.redrock.prize.pojo.response.InfoResponse;
import team.redrock.prize.pojo.response.SpecifiedActResponse;
import utils.HttpUtil;
import utils.PosterUtil;
import utils.SessionUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class SpecifiedActService {

    private static String OPENID ="o08-k1CAlytHhsEFrnfnaA-W4FDo";


    @Autowired
    ActivityMapper activityMapper;
    @Autowired
    SpecifiedTypeMapper specifiedTypeMapper;
    @Autowired
    TemplateMessageService templateMessageService;

    public SpecifiedActResponse createSpecifiedAct(List<ReqStudent> reqStudents, String activity, String url, HttpServletRequest request) throws NoSuchProviderException, NoSuchAlgorithmException, UnsupportedEncodingException, SQLException {

          String  actid = activityMapper.SelectActivityId(activity);
        SimpleDateFormat f_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = f_date.format(new Date());

            if (!actid .equals("0")) {
                System.out.println("活动已存在");
        }else {

                HttpSession session = request.getSession();
                log.info("________________________________________sessionname = " + session.getAttribute("SESSIONNAME"));


                    actid = getactID(activity);
                    activityMapper.insert(new Activity(activity, (String) session.getAttribute("SESSIONNAME"), url, 1, 1, date, actid));
                    log.info("ZLOG==>____________________________________create new activity!________________________________");

            }

        List<Map<String, String>> failedMsg = new ArrayList<>();
        for (int i = 0; i < reqStudents.size(); i++) {
            ReqStudent reqStudent = reqStudents.get(i);

            String openid = PosterUtil.getOpenID(reqStudent.getStuid());
            if (openid.equals("0")) {
                return new SpecifiedActResponse(-2, "Fail to get openid",actid,null);
            }
            System.out.println(openid);

            StudentA student = new StudentA(openid, reqStudent.getStuname(), reqStudent.getCollege(), reqStudent.getStuid(), Integer.parseInt(reqStudent.getTelephone()), actid, date, reqStudent.getReward(), 0);
            specifiedTypeMapper.insert(student);


            int result = getFailedSend(templateMessageService.sendMsg(openid, reqStudent.getStuname(), activity, reqStudent.getReward(), "未领取"));
            if (result == 1) {
                Map<String, String> stuMsg = new HashMap<>();
                stuMsg.put("stuname", student.getStuname());
                stuMsg.put("college", student.getCollege());
                stuMsg.put("stuid", student.getStuid());
                stuMsg.put("telephone", reqStudent.getTelephone());
                failedMsg.add(stuMsg);
            }
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
        }
       log.warn("ZLOG=>errorMsg:"+text);
        if(text!=null||!text.equals("ok")){
            return 1;
        }
        return 0;
    }


    private  static String  getactID(String activity){
        String longID = SessionUtil.getMD5(activity);
        String actID = longID.substring(0,6);
        return actID;
    }






}
