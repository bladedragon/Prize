package team.redrock.prize.utils;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import team.redrock.prize.bean.responseBean.OpenidResponseBean;
import team.redrock.prize.bean.responseBean.StuInfoResponseBean;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


@Component
@Slf4j
public class PosterUtil {

    @Value("${GetOpenidUrl}")
    private  String verUrl;
    @Value("${GetuserinfoUrl}")
    private  String getuserinfoUrl;
    @Value("${PosterUtil.timestamp}")
    private   String timestamp;
    @Value("${PosterUtil.string}")
    private   String string;
    @Value("${PosterUtil.secret}")
    private  String secret;


    /**
     * 查询成绩身份验证
     *
     * @return
     */

    public String getOpenID(String stuId){

        PrintWriter out = null;
        BufferedReader reader = null;
        HttpURLConnection connection = null;
        StringBuilder builder = null;
        try {
            //创建一个url
            System.out.println("url:" + verUrl);
            URL url = new URL(verUrl);
            connection = (HttpURLConnection) url.openConnection();
            // 发送POST请求必须设置如下两行
            connection.setRequestMethod("POST");
//            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");//发送请求参数类型
            connection.setConnectTimeout(1000);
            connection.setDoOutput(true);
            connection.setDoInput(true);


            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(connection.getOutputStream());
            String content = "stuId=" + URLEncoder.encode(stuId, "utf8") + "&timestamp=" + URLEncoder.encode(timestamp, "utf8")
                    + "&string=" + URLEncoder.encode(string, "utf8") + "&secret=" + URLEncoder.encode(secret, "utf8");
            out.print(content);
//            // 发送请求参数
//            out.print(page);
            // flush输出流的缓冲
            out.flush();

            //得到连接中输入流，缓存到bufferedReader
            reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), "UTF-8"));
            builder = new StringBuilder();
            String line = "";
            //每行每行地读出
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            out.close();
            reader.close();
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }

        OpenidResponseBean object = null;
try{
     object = JSON.parseObject(builder.toString(), OpenidResponseBean.class);

}catch (NullPointerException e){
    e.printStackTrace();
    log.error("ZLOG==>NullPointerError");
    log.error(e.toString());

    return "0";
}

        String openid = "0";
        if(object.getStatus()!=0){
            openid = object.getOpenid().get(0);
        }

        log.info("return openid:"+openid);
        return openid;

    }


    /**
     * 通过openid获取学生的个人信息
     * @param openId
     * @return
     */

    public  StuInfoResponseBean getStuInfo(String openId){

        PrintWriter out = null;
        BufferedReader reader = null;
        HttpURLConnection connection = null;
        StringBuilder builder = null;
        try {
            //创建一个url
            System.out.println("url:" + getuserinfoUrl);
            URL url = new URL(getuserinfoUrl);
            connection = (HttpURLConnection) url.openConnection();
            // 发送POST请求必须设置如下两行
            connection.setRequestMethod("POST");
//            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");//发送请求参数类型
            connection.setConnectTimeout(1000);
            connection.setDoOutput(true);
            connection.setDoInput(true);


            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(connection.getOutputStream());
            String content = "openid=" + URLEncoder.encode(openId, "utf8") + "&timestamp=" + URLEncoder.encode(timestamp, "utf8")
                    + "&string=" + URLEncoder.encode(string, "utf8") + "&secret=" + URLEncoder.encode(secret, "utf8");
            out.print(content);
//            // 发送请求参数
//            out.print(page);
            // flush输出流的缓冲
            out.flush();

            //得到连接中输入流，缓存到bufferedReader
            reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), "UTF-8"));
            builder = new StringBuilder();
            String line = "";
            //每行每行地读出
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            out.close();
            reader.close();
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }

        StuInfoResponseBean object = JSON.parseObject(builder.toString(), StuInfoResponseBean.class);

        return object;
    }


}
