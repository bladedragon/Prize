package team.redrock.prize.controller;


import org.springframework.web.bind.annotation.RestController;
import team.redrock.prize.pojo.response.NSpecifiedActResponse;

@RestController
public class ModifyActController {

    public NSpecifiedActResponse modifyAct(String token){


        return new NSpecifiedActResponse();
    }
}
