package team.redrock.prize.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import team.redrock.prize.exception.ValidException;
import team.redrock.prize.pojo.response.ShowPrizerAResponse;

import team.redrock.prize.pojo.response.ShowPrizerBResponse;
import team.redrock.prize.service.ShowPrizerService;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
public class ShowPrizerController {

    @Autowired
    ShowPrizerService showPrizerService;

    @PostMapping("/showPrizerA")
    public ShowPrizerAResponse showPrizerA(@RequestParam(value = "actid", defaultValue = "") String actId, @RequestParam(value = "type", defaultValue = "0") int type, @RequestParam(value = "page", defaultValue = "0") int page,
                                           @RequestParam(value = "pagesize", defaultValue = "5") int pagesize, @RequestParam(value = "token", required = false) String token, HttpServletRequest request) throws ValidException {
        if (null == token || !request.getSession().getAttribute("SESSIONID").equals(token)) {
            throw new ValidException("token验证无效");
        }

        if (pagesize == 0) {
            throw new ValidException("Pagesize cannot be zero");
        }

        ShowPrizerAResponse response = showPrizerService.showPrizerA(actId, page, pagesize);

        return response;
    }


    @PostMapping("/showPrizerB")
    public ShowPrizerBResponse showPrizerB(@RequestParam(value = "token", required = false) String token, @RequestParam(value = "actid", defaultValue = "") String actId, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "pagesize", defaultValue = "5") int pagesize, HttpServletRequest request) throws ValidException {

        if (null == token || !request.getSession().getAttribute("SESSIONID").equals(token)) {
            throw new ValidException("token验证无效");
        }
        if (pagesize == 0) {
            throw new ValidException("Pagesize cannot be zero");
        }

        ShowPrizerBResponse response = showPrizerService.showPrizerB(actId, page, pagesize);

        return response;
    }
}


