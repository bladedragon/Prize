package team.redrock.prize.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import team.redrock.prize.mapper.ActivityMapper;
import team.redrock.prize.pojo.response.GetPrizeResponse;

@RestController
public class DeleteActController {

    @Autowired
    ActivityMapper activityMapper;

    @PostMapping("deleteActivity")
    public GetPrizeResponse deleteActivity(@RequestParam("activity") String activity){

        activityMapper.delete(activity);

        return new GetPrizeResponse(200,"success");
    }
}
