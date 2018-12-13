package team.redrock.prize.service;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.redrock.prize.Access_token.Scheduler;
import team.redrock.prize.bean.TemplateData;
import team.redrock.prize.bean.TemplateMsg;
import utils.HttpUtil;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class TemplateMessageService {

    public static final String WX_APPID = "wxaf05acd677a29f25";
    public static final String WX_APPSECRET = "58e74928490b55bebb483fc526828bfc";

    //openId
    private static  String fromUserName="o08-k1CAlytHhsEFrnfnaA-W4FDo";
    //模板ID
    private static String template_id="z6DiIC3-M3RJikwYUPALN5nlMMYr1VrMFbVFHSsTEWo";
    //请求地址
    private static String sendurl ="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";

    @Autowired
    Scheduler scheduler;

        private  TemplateMsg getTemplate(String openid,String name,String activity,String award,String status) throws SQLException {

        TemplateMsg templateMsg = new TemplateMsg();

        Map<String,TemplateData>  dataMap = new HashMap<>();
            dataMap.put("first",new TemplateData("你有一份奖品需要领取","#173177"));
            dataMap.put("name",new TemplateData(name,"#173177"));
            dataMap.put("activity",new TemplateData(activity,"#173177"));
            dataMap.put("award",new TemplateData(award,"#173177"));
            dataMap.put("status",new TemplateData(status,"#173177"));
            templateMsg.setData(dataMap);
            templateMsg.setTemplate_id(template_id);
            templateMsg.setTopcolor("#173177");
        templateMsg.setTouser(openid);


        return templateMsg;
    }

    public String  sendMsg(String openid,String stuname,String actname,String award,String status) throws SQLException {
        String accesstoken= scheduler.getAccess_Token();
        String url = sendurl+accesstoken;
        String HttpResponse  = null;
//        TemplateMsg templateMsg = getTemplate("竹祯铮","红岩活动","大乱斗","未领取");
        TemplateMsg templateMsg = getTemplate(openid,stuname,actname,award,status);
        try {

             HttpResponse = HttpUtil.httpRequestToString(url,"POST", JSON.toJSONString(templateMsg));
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
           log.error("请求出错");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            log.error("请求出错");
        }

        return HttpResponse;

    }




}
