package team.redrock.prize.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import team.redrock.prize.bean.WXAccount;
import team.redrock.prize.exception.ValidException;
import team.redrock.prize.pojo.response.GetPrizeResponse;
import team.redrock.prize.service.GetprizeAService;
import team.redrock.prize.service.WXAuthorizeService;


import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

@RestController
@Slf4j
public class GetPrizeController {


    /**
     * @param code  用户同意授权后,获取到的code
     * @param state 重定向状态参数
     * https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxaf05acd677a29f25&redirect_uri=http://67r96u.natappfree.cc/getPrizeB/93e3ad/xbox&response_type=code&scope=snsapi_userinfo&state=STAT#wechat_redirect
     * @return
     */

    @Autowired
    WXAuthorizeService wxAuthorizeServicew;
    @Autowired
    GetprizeAService getprizeAService;

    @GetMapping("/getPrizeA/{actid}/{reward}")
    @ResponseBody
    public GetPrizeResponse getPrizeA(@RequestParam(name = "code", required = false) String code,
                              @RequestParam(name = "state") String state,@PathVariable(name = "actid") String actid,@PathVariable(name = "reward")String reward) throws NoSuchProviderException, NoSuchAlgorithmException, ValidException {


String openid = "o08-k1CAlytHhsEFrnfnaA-W4FDo";
                    WXAccount wxAccount = wxAuthorizeServicew.getWxInfo(code,state);

        if(wxAccount==null){
            throw new ValidException("Fail to authorize");
        }

        GetPrizeResponse response =  getprizeAService.getPrizeA(openid,actid,reward);

               return  response;

    }

    @GetMapping("/getPrizeB/{actid}/{reward}")
    public GetPrizeResponse getPrizeB(@RequestParam(name = "code", required = false) String code,
                            @RequestParam(name = "state") String state,@PathVariable(name = "actid") String actid,@PathVariable(name = "reward") String reward) throws NoSuchProviderException, NoSuchAlgorithmException, ValidException {


        //String openid = "o08-k1CAlytHhsEFrnfnaA-W4FDo";
        WXAccount wxAccount = wxAuthorizeServicew.getWxInfo(code,state);

        if(wxAccount==null){
            log.error("ZLOG==>Fail to authorize");
            throw new ValidException("Fail to authorize");
        }


       GetPrizeResponse response= getprizeAService.getPrizeB(wxAccount,actid,reward);

        return response;

    }



}
