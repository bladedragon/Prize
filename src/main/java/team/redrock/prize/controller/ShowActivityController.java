package team.redrock.prize.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import team.redrock.prize.exception.ValidException;

import team.redrock.prize.pojo.response.ShowActivityResponse;
import team.redrock.prize.service.ShowActivityService;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ShowActivityController {

    @Autowired
    ShowActivityService showActivityService;

    @PostMapping("/showActivity")
    public ShowActivityResponse showActivity(@RequestParam(value = "token",required = false)String token, @RequestParam(value = "page",defaultValue = "0") int page, @RequestParam(value = "pagesize",defaultValue = "5") int pagesize, HttpServletRequest request) throws ValidException {


        if(null==token||!request.getSession().getAttribute("SESSIONID").equals(token)){
            throw new ValidException("token验证无效");
        }


        if(pagesize==0){
            throw new ValidException("Pagesize cannot be zero");
        }

        ShowActivityResponse showResponse;
        showResponse = showActivityService.showActivity(page,pagesize,request);

        return showResponse;
    }

}
