package team.redrock.prize.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import utils.HttpUtil;
import utils.UserInfoUtil;


import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

@RestController
public class WerixinRedrictController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    HttpUtil httpUtil = new HttpUtil();
    public static final String WX_APPID = "wxaf05acd677a29f25";
    public static final String WX_APPSECRET = "58e74928490b55bebb483fc526828bfc";


    /**
     * @param code  用户同意授权后,获取到的code
     * @param state 重定向状态参数
     * @return
     */
    @GetMapping("/url")
    public String wecahtLogin(@RequestParam(name = "code", required = false) String code,
                              @RequestParam(name = "state") String state) throws NoSuchProviderException, NoSuchAlgorithmException {

        // 用户同意授权,获取code
        logger.info("用户同意授权,获取code:{} , state:{}", code, state);

        // 通过code换取网页授权access_token
        if (code != null || !(code.equals(""))) {

            String APPID = WX_APPID;
            String SECRET = WX_APPSECRET;
            String CODE = code;
            String WebAccessToken = "";
            String openId = "";
            String nickName, sex, openid = "";
            String REDIRECT_URI = "http://6tkj65.natappfree.cc/url";
            String SCOPE = "snsapi_userinfo";

            String getCodeUrl = UserInfoUtil.getCode(APPID, REDIRECT_URI, SCOPE);
            logger.info("get Code URL:{}", getCodeUrl);

            // 替换字符串，获得请求access token URL
            String tokenUrl = UserInfoUtil.getWebAccess(APPID, SECRET, CODE);
            logger.info("get Access Token URL:{}", tokenUrl);

            // 通过https方式请求获得web_access_token
            String response = httpUtil.httpRequestToString(tokenUrl, "GET", null);

            JSONObject jsonObject = JSON.parseObject(response);
            logger.info("请求到的Access Token:{}", jsonObject.toJSONString());


            if (null != jsonObject) {
                try {

                    WebAccessToken = jsonObject.getString("access_token");
                    openId = jsonObject.getString("openid");
                    logger.info("WebAccessToken:{} , openId:{}", WebAccessToken, openId);

                    // 3. 使用获取到的 Access_token 和 openid 拉取用户信息
                    String userMessageUrl = UserInfoUtil.getUserMessage(WebAccessToken, openId);
                    logger.info("获取用户信息的URL:{}", userMessageUrl);

                    // 通过https方式请求获得用户信息响应
                    String userMessageResponse = httpUtil.httpRequestToString(userMessageUrl, "GET", null);

                    JSONObject userMessageJsonObject = JSON.parseObject(userMessageResponse);

                    logger.info("用户信息:{}", userMessageJsonObject.toJSONString());



//                    if (userMessageJsonObject != null) {
//                        try {
//                            //用户昵称
//                            nickName = userMessageJsonObject.getString("nickname");
//                            //用户性别
//                            sex = userMessageJsonObject.getString("sex");
//                            sex = (sex.equals("1")) ? "男" : "女";
//                            //用户唯一标识
//                            openid = userMessageJsonObject.getString("openid");
//
//                            logger.info("用户昵称:{}", nickName);
//                            logger.info("用户性别:{}", sex);
//                            logger.info("OpenId:{}", openid);
//                        } catch (JSONException e) {
//                            logger.error("获取用户信息失败");
//                        }
//                    }
                } catch (JSONException e) {
                    logger.error("获取Web Access Token失败");
                }
            }
        }
        return "登录成功";
    }

}
