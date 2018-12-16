package team.redrock.prize.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import team.redrock.prize.utils.SessionUtil;

import java.util.HashMap;
import java.util.Map;

@RestController
public class GetActIdController {

    @PostMapping("/getActId")
    public Map<String, String> getActId(@RequestParam("actname") String actname){
        String longID = SessionUtil.getMD5(actname);
        String actID = longID.substring(0,6);
        Map<String,String> map = new HashMap<>();
        map.put("status","200");
        map.put("info","success");
        map.put("actid",actID);
        return map;
    }
}
