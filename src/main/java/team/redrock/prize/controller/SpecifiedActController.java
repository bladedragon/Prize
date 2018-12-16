package team.redrock.prize.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import team.redrock.prize.bean.PrizeList;
import team.redrock.prize.bean.SpecifiedAct;
import team.redrock.prize.exception.ValidException;
import team.redrock.prize.pojo.response.SpecifiedActResponse;
import team.redrock.prize.service.AsyncService;
import team.redrock.prize.service.SpecifiedActService;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

import java.util.List;

@RestController
@Slf4j

public class SpecifiedActController {

    @Autowired
    SpecifiedActService specifiedActService;
//    @Autowired
//    AsyncService asyncService;

 @PostMapping(value = "/specifiedAct")
public SpecifiedActResponse SpecifiedActOperation(@RequestBody SpecifiedAct specifiedAct, HttpServletRequest request) throws ValidException {

     System.out.println("-------------------------"+System.currentTimeMillis()+"-------------------------------------");
     List<PrizeList> typeA = specifiedAct.getTypeA();
     List<String>  typeB = specifiedAct.getTypeB();
     String activity = specifiedAct.getActivity();
     String url = specifiedAct.getActurl();


     if(activity==null||activity.isEmpty()){
         throw new  ValidException("活动名称不能为空");
     }
     if(url==null||url.isEmpty()){
         throw new  ValidException("活动路由不能为空");
     }


     SpecifiedActResponse response = null;
     long time = System.currentTimeMillis();
     System.out.println("-----------controller-------------"+System.currentTimeMillis()+"-------------------------------------");
     try {
         response = specifiedActService.createSpecifiedAct(typeA,typeB, activity, url, request);

         System.out.println("----------after service---------------"+(System.currentTimeMillis()-time)+"-------------------------------------");
     } catch (SQLException e) {
         e.printStackTrace();
         log.error("ZLOG==>Occur SQL_Ecxption!!");
     }
     System.out.println("-------------------------"+(System.currentTimeMillis()-time)+"-------------------------------------");
     return response;
 }
}