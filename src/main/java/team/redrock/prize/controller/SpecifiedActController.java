package team.redrock.prize.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import team.redrock.prize.bean.ReqStudent;
import team.redrock.prize.bean.SpecifiedAct;
import team.redrock.prize.exception.ValidException;
import team.redrock.prize.pojo.response.SpecifiedActResponse;
import team.redrock.prize.service.SpecifiedActService;
import utils.HttpUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.sql.SQLException;

import java.util.List;

@RestController
@Slf4j
public class SpecifiedActController {

    @Autowired
    SpecifiedActService specifiedActService;
 @PostMapping(value = "/specifiedAct")
public SpecifiedActResponse SpecifiedActOperation(@RequestBody SpecifiedAct specifiedAct, HttpServletRequest request) throws ValidException {

     List<ReqStudent> reqStudents = specifiedAct.getReqStudents();
     String activity = specifiedAct.getActivity();
     String url = specifiedAct.getActurl();

     if(reqStudents.isEmpty()){
         throw  new ValidException("请求不能为空");

     }
     if(activity==null||activity.isEmpty()){
         throw new  ValidException("活动名称不能为空");
     }
     if(url==null||url.isEmpty()){
         throw new  ValidException("活动路由不能为空");
     }


     SpecifiedActResponse response = null;

     try {
         response = specifiedActService.createSpecifiedAct(reqStudents, activity, url, request);
     } catch (SQLException e) {
         e.printStackTrace();
         log.error("ZLOG==>Occur SQL_Ecxption!!");
     } catch (NoSuchAlgorithmException e) {
         e.printStackTrace();
         log.error("ZLOG==>Occur NoSuchAlgorithmException!");
     } catch (NoSuchProviderException e) {
         e.printStackTrace();
         log.error("ZLOG==>No Such ProviderException!");
     } catch (UnsupportedEncodingException e) {
         e.printStackTrace();
         log.error("ZLOG==>Occur UnsupportedEncodingException");
     }

     return response;
 }
}