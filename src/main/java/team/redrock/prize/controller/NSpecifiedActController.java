package team.redrock.prize.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import team.redrock.prize.bean.NSpecifiedAct;
import team.redrock.prize.exception.ValidException;
import team.redrock.prize.pojo.response.NSpecifiedActResponse;

import team.redrock.prize.service.NSpecifiedActService;

import javax.servlet.http.HttpServletRequest;


@Controller
@Slf4j
public class NSpecifiedActController {

    @Autowired
    NSpecifiedActService specifiedActService;

    @PostMapping(value = "/noSpecifiedAct")
    @ResponseBody
    public NSpecifiedActResponse NSpecifiedActOperation(@RequestBody NSpecifiedAct nSpecifiedAct, HttpServletRequest request) throws ValidException {

        String activity = nSpecifiedAct.getActivity();
        String url = nSpecifiedAct.getActurl();
        if(activity==null||activity.isEmpty()){
            throw new ValidException("活动名称不能为空");
        }
        if(url==null||url.isEmpty()){
            throw new  ValidException("活动路由不能为空");
        }

        NSpecifiedActResponse response = null;

        response = specifiedActService.createNSpecifiedAct(activity, url, request);

        return response;
    }
}
