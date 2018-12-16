package team.redrock.prize.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import team.redrock.prize.exception.ValidException;
import team.redrock.prize.pojo.response.ShowPrizerAResponse;

import team.redrock.prize.service.ShowPrizerService;

@RestController
@Slf4j
public class ShowPrizerController {

    @Autowired
    ShowPrizerService showPrizerService;

    @PostMapping("/showPrizer")
    public ShowPrizerAResponse showPrizer(@RequestParam("actId") String actId, @RequestParam(value = "type",defaultValue = "0") int type, @RequestParam(value = "page",defaultValue = "0")int page, @RequestParam(value = "pagesize",defaultValue = "5")int pagesize) throws ValidException {

        if(pagesize==0){
            throw new ValidException("Pagesize cannot be zero");
        }

        ShowPrizerAResponse response = showPrizerService.showPrizer(actId,type,page,pagesize);

        return response;
    }
}
