package team.redrock.prize.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import team.redrock.prize.exception.ValidException;
import team.redrock.prize.pojo.response.ShowActivityResponse;
import team.redrock.prize.service.ShowActivityService;
import team.redrock.prize.service.ShowPrizerService;

@RestController
public class ShowActivityController {

    @Autowired
    ShowActivityService showActivityService;

    @PostMapping("/showActivity")
    public ShowActivityResponse showActivity(@RequestParam(value = "page",defaultValue = "0") int page, @RequestParam(value = "pagesize",defaultValue = "5") int pagesize) throws ValidException {


        if(pagesize==0){
            throw new ValidException("Pagesize cannot be zero");
        }

        ShowActivityResponse showResponse;
        showResponse = showActivityService.showActivity(page,pagesize);

        return showResponse;
    }

}
