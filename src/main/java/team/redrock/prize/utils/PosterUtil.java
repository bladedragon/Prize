package team.redrock.prize.utils;

import com.alibaba.fastjson.JSON;
import team.redrock.prize.bean.OpenidResponseBean;
import team.redrock.prize.bean.StuInfoResponseBean;
import team.redrock.prize.exception.ValidException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class PosterUtil {
    //    @Value("${jwzx.verify}")
//    private String verUrl;
    private static String verUrl = "http://hongyan.cqupt.edu.cn/MagicLoop/index.php?s=/addon/Api/Api/getOpenidByStuId";
    private static String getuserinfoUrl="http://hongyan.cqupt.edu.cn/MagicLoop/index.php?s=/addon/Api/Api/bindVerify";
    private static String timestamp = "1505118409";
    private static String string = "asdfghjkl";
    private static String secret = "e49e89f65d0319bdce2d4740c5b2244af2e774e8";

    /**
     * 查询成绩身份验证
     *
     * @return
     */
    public static String getOpenID(String stuId) {

        System.out.println("调用此处"+stuId);
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
    return "0";
}

        String openid = "0";
        if(object.getStatus()!=0){
            openid = object.getOpenid().get(0);
        }


        return openid;


    }


    /**
     * 通过openid获取学生的个人信息
     * @param openId
     * @return
     */

    public static StuInfoResponseBean getStuInfo(String openId){

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
