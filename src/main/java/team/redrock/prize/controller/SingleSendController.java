package team.redrock.prize.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import team.redrock.prize.bean.SingleSendStudent;
import team.redrock.prize.exception.ValidException;
import team.redrock.prize.mapper.SpecifiedTypeMapper;
import team.redrock.prize.pojo.response.GetPrizeResponse;
import team.redrock.prize.service.TemplateMessageService;
import team.redrock.prize.utils.PosterUtil;
import team.redrock.prize.utils.SessionUtil;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@Slf4j
public class SingleSendController {

    @Autowired
    TemplateMessageService templateMessageService;
    @Autowired
    PosterUtil posterUtil;
    @Autowired
    SpecifiedTypeMapper specifiedTypeMapper;
    @PostMapping("/sendOne")
    public GetPrizeResponse SingleSend(
            @RequestBody SingleSendStudent singleSendStudent,
            HttpServletRequest request) throws ValidException {

        if(singleSendStudent==null){
            throw new ValidException("Param cannnot be null");
        }
        String token = singleSendStudent.getToken();
        if(null==token||!request.getSession().getAttribute("SESSIONID").equals(token)){
            throw new ValidException("token验证无效");
        }
        String stuid = singleSendStudent.getStuid();
        if(null==stuid||stuid.equals("")){
            throw new ValidException("stuid不能为空");
        }
        String openid = posterUtil.getOpenID(stuid);
        if (openid.equals("0")) {                            //正式版本要改成0
            throw new ValidException("Fail to get openid");
        }

        String msg = singleSendStudent.getMsg();
        String activity = singleSendStudent.getActivity();
        String reward = singleSendStudent.getReward();
        SimpleDateFormat f_date = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String date = f_date.format(new Date());
        String prize_date = singleSendStudent.getPrizeDate();
        String remark = singleSendStudent.getRemark();
        String result = ";";
        String text =  null;
        try {
             result = templateMessageService.sendMsg(openid,msg,activity,reward,date,prize_date,remark);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        Pattern pattern = Pattern.compile("\\\"errmsg\\\":\\\"(.*?)\\\"");
        Matcher matcher = pattern.matcher(result);
        while (matcher.find()) {
            text = matcher.group(1);
            System.out.println(matcher.group(1));
        }
        log.warn("ZLOG=>errorMsg:"+text);

        if(text==null||!text.equals("ok")){

            return new GetPrizeResponse(-2,"发送失败");
        }
        specifiedTypeMapper.updatePush_status(0,getID(activity),getID(reward),stuid);
        return new GetPrizeResponse(200,"发送成功");


    }

    private  static String  getID(String activity){
        String longID = SessionUtil.getMD5(activity);
        String actID = longID.substring(0,6);
        return actID;
    }
}
