package team.redrock.prize.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import team.redrock.prize.pojo.response.ShowPrizerResponse;
import team.redrock.prize.service.ShowPrizerService;

@RestController
@Slf4j
public class ShowPrizerController {

    @Autowired
    ShowPrizerService showPrizerService;

    @PostMapping("/showPrizer")
    public ShowPrizerResponse showPrizer(String actId,int page,int type){

        ShowPrizerResponse response = showPrizerService.showPrizer(actId,0,0);

        return response;
    }
}
