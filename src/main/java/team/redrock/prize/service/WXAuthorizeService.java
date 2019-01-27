package team.redrock.prize.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import team.redrock.prize.Access_token.Scheduler;
import team.redrock.prize.bean.WXAccount;
import team.redrock.prize.utils.HttpUtil;
import team.redrock.prize.utils.UserInfoUtil;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class WXAuthorizeService {

    @Value("${WX_APPID}")
    private String WX_APPID;
    @Value("${WX_APPSECRET}")
    private String WX_APPSECRET;
    @Value("${REDIRECT_URI}")
    private String REDIRECT_URI;

    @Autowired
    private  HttpUtil httpUtil;
    public WXAccount getWxInfo(String code,String state) throws NoSuchProviderException, NoSuchAlgorithmException {
        String userid=null;
        String nickname=null;

        // 用户同意授权,获取code
        log.info("用户同意授权,获取code:{} , state:{}", code, state);

        // 通过code换取网页授权access_token
        if (code != null || !(code.equals(""))) {

            String APPID = WX_APPID;
            String SECRET = WX_APPSECRET;
            String CODE = code;
            String WebAccessToken = "";
            String openId = "";
            String nickName, sex, openid = "";
            String SCOPE = "snsapi_userinfo";

            String getCodeUrl = UserInfoUtil.getCode(APPID, REDIRECT_URI, SCOPE);
            log.info("get Code URL:{}", getCodeUrl);

            // 替换字符串，获得请求access token URL
            String tokenUrl = UserInfoUtil.getWebAccess(APPID, SECRET, CODE);
            log.info("get Access Token URL:{}", tokenUrl);

            // 通过https方式请求获得web_access_token
            String response = httpUtil.httpRequestToString(tokenUrl, "GET", null);

            JSONObject jsonObject = JSON.parseObject(response);
            log.info("请求到的Access Token:{}", jsonObject.toJSONString());


            if (null != jsonObject) {
                try {

                    WebAccessToken = jsonObject.getString("access_token");
                    openId = jsonObject.getString("openid");
                    log.info("WebAccessToken:{} , openId:{}", WebAccessToken, openId);

                    // 3. 使用获取到的 Access_token 和 openid 拉取用户信息
                    String userMessageUrl = UserInfoUtil.getUserMessage(WebAccessToken, openId);
                    log.info("获取用户信息的URL:{}", userMessageUrl);

                    // 通过https方式请求获得用户信息响应
                    String userMessageResponse = httpUtil.httpRequestToString(userMessageUrl, "GET", null);

                    JSONObject userMessageJsonObject = JSON.parseObject(userMessageResponse);

                    String JsonStr =  userMessageJsonObject.toJSONString();
                    log.info("用户信息:{}",JsonStr);


                    Pattern pattern = Pattern.compile("\\\"openid\\\":\\\"(.*?)\\\",");
                    Matcher matcher = pattern.matcher(JsonStr);
                    while (matcher.find()) {
                     userid = matcher.group(1);
                    }
                    Pattern pattern1 = Pattern.compile("\\\"nickname\\\":\\\"(.*?)\\\",");
                    Matcher matcher1 = pattern1.matcher(JsonStr);
                    while (matcher1.find()) {
                        nickname = matcher1.group(1);
                    }

                } catch (JSONException e) {
                    log.error("获取Web Access Token失败");
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (NoSuchProviderException e) {
                    e.printStackTrace();
                }
            }
        }
        return new WXAccount(nickname,userid);
    }

}

