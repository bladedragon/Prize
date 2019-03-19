package team.redrock.prize.Access_token;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import team.redrock.prize.exception.ValidException;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;

@Component
@Slf4j
public class Scheduler {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    RedisTokenHelper redisTokenHelper = new RedisTokenHelper();
    @Value("${WX_APPID}")
   private String WX_APPID ;
    @Value("${WX_APPSECRET}")
private String WX_APPSECRET;

    @Value("${AccessTokenApi}")
    private String AccessTokenApi;

    /**
     * 定时获取access_token
     *
     * @throws SQLException
     */
//    @Scheduled(fixedDelay = 7180000)
    public String getAccessToken()  {
      // logger.info("==============开始获取access_token===============");
        String access_token = null;
        String grant_type = "client_credential";
        String AppId = WX_APPID;
        String secret = WX_APPSECRET;
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=" + grant_type + "&appid=" + AppId + "&secret=" + secret;
        logger.info("getUrl==>"+url);

        try {
            URL urlGet = new URL(url);
            HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
            http.setRequestMethod("GET"); // 必须是get方式请求
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            http.connect();
            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message = new String(jsonBytes, "UTF-8");
            JSONObject demoJson = JSONObject.fromObject(message);

            access_token = demoJson.getString("access_token");
        //logger.info("get access_token==>"+access_token);
        is.close();
        //System.out.println("==============结束获取access_token===============");
    } catch (Exception e) {
        e.printStackTrace();
    }

//        System.out.println("==============开始写入access_token===============");
        redisTokenHelper.save("global_token", access_token);

//        System.out.println("==============写入access_token成功===============");
        return access_token;
    }

    public String getAccess_Token() {
            String access_token =  redisTokenHelper.getObject("global_token");
            if (access_token.equals("1")) {
//            getAccessToken();
                getAccessTokenApi();
                access_token = (String) redisTokenHelper.getObject("global_token");
            }
            return (String) access_token;
    }



    public  String getAccessTokenApi() {
//        System.out.println("------------------------getaccessTokenApi()-------------------------");
        String access_token = null;

        URL urlGet = null;
        try {
            urlGet = new URL(AccessTokenApi);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection http = null;
        try {
            http = (HttpURLConnection) urlGet.openConnection();
            http.setRequestMethod("GET"); // 必须是get方式请求
            http.setRequestProperty("Authorization", "Redrock");
            http.setDoOutput(true);
            http.setDoInput(true);
            http.connect();
            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message = new String(jsonBytes, "UTF-8");

            JSONObject demoJson = JSONObject.fromObject(message);

            System.out.println("message:"+message);
            if(demoJson.getString("status").equals("200")){
                access_token = demoJson.getString("data");

                redisTokenHelper.save("global_token", access_token);
            }else{
                log.error("获取AccessToken失败");
                access_token = "";
            }

            is.close();

        } catch (Exception e) {
            e.printStackTrace();

        }

        log.info("getAccesstoken=>"+access_token);


        return access_token;
    }





}
