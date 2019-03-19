package team.redrock.prize.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import team.redrock.prize.exception.ValidException;
import team.redrock.prize.mapper.ActivityMapper;
import team.redrock.prize.pojo.response.GetPrizeResponse;

import javax.servlet.http.HttpServletRequest;

@RestController
public class EndActController {

    @Autowired
    ActivityMapper activityMapper;

    @PostMapping("/EndActivity")
    public GetPrizeResponse EndAct(@RequestParam(value = "token",required = false)String token,
                                   @RequestParam(value = "actid",defaultValue = "") String actid,
                                   HttpServletRequest request) throws ValidException {

        if(null==token||!request.getSession().getAttribute("SESSIONID").equals(token)){
            throw new ValidException("token验证无效");
        }
        if(actid.equals("")){
            return new GetPrizeResponse(-2,"Param cannnot be null");
        }

        int result = activityMapper.UpdateActStatus(actid,3);

        if(result == 0){
            return new GetPrizeResponse(-2,"no modify");
        }

        return new GetPrizeResponse(200,"success");
    }
}
