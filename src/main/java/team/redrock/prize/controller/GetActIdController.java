package team.redrock.prize.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import team.redrock.prize.exception.ValidException;
import team.redrock.prize.utils.SessionUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class GetActIdController {

    @PostMapping("/getActId")
    public Map<String, String> getActId(@RequestParam(value = "token",required = false) String token, @RequestParam("actname") String actname, HttpServletRequest request) throws ValidException {

        if(null==token||!request.getSession().getAttribute("SESSIONID").equals(token)){
            throw new ValidException("token验证无效");
        }

        String longID = SessionUtil.getMD5(actname);
        String actID = longID.substring(0,6);
        Map<String,String> map = new HashMap<>();
        map.put("status","200");
        map.put("info","success");
        map.put("actid",actID);
        return map;
    }
}
