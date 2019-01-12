package team.redrock.prize.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import team.redrock.prize.bean.TempAct;
import team.redrock.prize.exception.ValidException;
import team.redrock.prize.pojo.response.ShowPrizerBResponse;
import team.redrock.prize.pojo.response.ShowTempReponse;
import team.redrock.prize.service.ShowTempService;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ShowTempPrizerController {

    @Autowired
    private ShowTempService showTempService;

    @PostMapping("/showTemp")
    public ShowTempReponse showPrizerC(@RequestParam(value = "token",required = false) String token, @RequestParam(value = "actid",defaultValue = "") String actId, HttpServletRequest request) throws ValidException {
        if (null == token || !request.getSession().getAttribute("SESSIONID").equals(token)) {
            throw new ValidException("token验证无效");
        }
            if (actId.equals("")) {
                throw new ValidException("actId is null");
            }
            ShowTempReponse response = showTempService.showTemp(actId);

            return response;
        }

}
