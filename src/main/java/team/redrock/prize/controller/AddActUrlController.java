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
public class AddActUrlController {

    @Autowired
    ActivityMapper activityMapper;

    @PostMapping("/addActUrl")
    public GetPrizeResponse actUrl(@RequestParam(value = "token",required = false)String token,
                                   @RequestParam(value = "acturl",defaultValue = "") String acturl,
                                   @RequestParam(value = "actid",defaultValue = "")String actid,
                                   @RequestParam(value = "rewardID",defaultValue = "")String rewardID,
                                   HttpServletRequest request) throws ValidException {

        if(acturl.equals("")||actid.equals("")||rewardID.equals("")){
            throw new ValidException("Param cannnot be null");
        }

        if(null==token||!request.getSession().getAttribute("SESSIONID").equals(token)){
            throw new ValidException("token验证无效");
        }

        int result = activityMapper.UpdateActUrl(actid,acturl,rewardID);
        if(result==0){
            return new GetPrizeResponse(-2,"cannot find activity");
        }
        return new GetPrizeResponse(200,"success!");
    }
}
