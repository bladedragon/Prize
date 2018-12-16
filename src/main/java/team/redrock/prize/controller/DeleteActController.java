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
public class DeleteActController {

    @Autowired
    ActivityMapper activityMapper;

    @PostMapping("/deleteActivity")
    public GetPrizeResponse deleteActivity(@RequestParam(value = "token",required = false)String token, @RequestParam("actid") String actid, HttpServletRequest request) throws ValidException {

        if(null==token||!request.getSession().getAttribute("SESSIONID").equals(token)){
            throw new ValidException("token验证无效");
        }
        activityMapper.deleteAct(actid);
        activityMapper.deleteSpecifiedType(actid);
        activityMapper.deleteNoSpecifiedType(actid);

        return new GetPrizeResponse(200,"success");
    }
}
