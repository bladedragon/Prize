package team.redrock.prize.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import team.redrock.prize.bean.WXAccount;
import team.redrock.prize.service.GetprizeAService;
import team.redrock.prize.service.WXAuthorizeService;
import utils.HttpUtil;
import utils.UserInfoUtil;


import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

@RestController
public class GetPrizeController {


    /**
     * @param code  用户同意授权后,获取到的code
     * @param state 重定向状态参数
     * https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxaf05acd677a29f25&redirect_uri=http://aerg8g.natappfree.cc/getPrizeA/asd&response_type=code&scope=snsapi_userinfo&state=STAT#wechat_redirect
     * @return
     */

    @Autowired
    WXAuthorizeService wxAuthorizeServicew;
    @Autowired
    GetprizeAService getprizeAService;

    @GetMapping("/getPrizeA/{actid}")
    public String getPrizeA(@RequestParam(name = "code", required = false) String code,
                              @RequestParam(name = "state") String state,@PathVariable(name = "actid") String actid) throws NoSuchProviderException, NoSuchAlgorithmException {

//                    String activity ="室友帮帮忙";
//String openid = "o08-k1CAlytHhsEFrnfnaA-W4FDo";
                    WXAccount wxAccount = wxAuthorizeServicew.getWxInfo(code,state);


        //System.out.println(wxAccount.getOpenid());
                    getprizeAService.getPrizeA(wxAccount.getOpenid(),actid);

                    return "登录成功";

    }

    public String getPrizeB(@RequestParam(name = "code", required = false) String code,
                            @RequestParam(name = "state") String state,@PathVariable(name = "actid") String actid) throws NoSuchProviderException, NoSuchAlgorithmException {

//                    String activity ="室友帮帮忙";
//String openid = "o08-k1CAlytHhsEFrnfnaA-W4FDo";
        WXAccount wxAccount = wxAuthorizeServicew.getWxInfo(code,state);


        //System.out.println(wxAccount.getOpenid());
        getprizeAService.getPrizeB(wxAccount.getOpenid(),actid);

        return "登录成功";

    }



}
