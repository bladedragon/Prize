package team.redrock.prize.controller;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import team.redrock.prize.bean.PrizeList;
import team.redrock.prize.bean.RewardList;
import team.redrock.prize.bean.SpecifiedAct;
import team.redrock.prize.bean.TempAct;
import team.redrock.prize.exception.ValidException;
import team.redrock.prize.pojo.response.NSpecifiedActResponse;
import team.redrock.prize.service.TempActService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class TempActController {

    @Autowired
    TempActService tempActService;

    @PostMapping("/tempAct")
    public NSpecifiedActResponse modifyAct(@RequestBody SpecifiedAct specifiedAct, HttpServletRequest request) throws ValidException {

        String token = specifiedAct.getToken();
        if(null==token||(!request.getSession().getAttribute("SESSIONID").equals(token))){
            System.out.println(token);
            throw new ValidException("token验证无效");
        }

        if(specifiedAct==null){
            throw new ValidException("Param cannnot be null");
        }
        List<PrizeList> typeA = specifiedAct.getTypeA();
        List<RewardList>  typeB = specifiedAct.getTypeB();
        String activity = specifiedAct.getActivity();

        NSpecifiedActResponse response = tempActService.getTempAct(typeA,typeB,activity,request);

        return response;
    }


}
