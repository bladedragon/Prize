package team.redrock.prize.controller;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import team.redrock.prize.bean.WXAccount;
import team.redrock.prize.bean.WxUser;
import team.redrock.prize.exception.ValidException;
import team.redrock.prize.pojo.response.GetPrizeResponse;
import team.redrock.prize.service.GetprizeAService;
import team.redrock.prize.service.WXAuthorizeService;



@Controller
@Slf4j
public class GetPrizeController {


    /**
     * @param code  用户同意授权后,获取到的code
     * @param state 重定向状态参数
     * https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxaf05acd677a29f25&redirect_uri=http://65v7bh.natappfree.cc/getPrizeB/93e3ad/xbox&response_type=code&scope=snsapi_userinfo&state=STAT#wechat_redirect
     * https://wx.idsbllp.cn/game/api/index.php?redirect=http://hxmvcn.natappfree.cc/getPrizeB/3b454a/0b3f45
     * @return
     */

    @Autowired
    WXAuthorizeService wxAuthorizeServicew;
    @Autowired
    GetprizeAService getprizeAService;

//    @GetMapping("/getPrizeA/{actid}/{reward}")
//    @ResponseBody
//    public GetPrizeResponse getPrizeA(@RequestParam(name = "code", required = false) String code,
//                                      @RequestParam(name = "state") String state, @PathVariable(name = "actid") String actid, @PathVariable(name = "reward")String reward) throws NoSuchProviderException, NoSuchAlgorithmException, ValidException {
//
//
//            log.warn("jsonObject="+jsonObject);
////        String openid = "o08-k1CAlytHhsEFrnfnaA-W4FDo";
//
//
//                    WXAccount wxAccount = wxAuthorizeServicew.getWxInfo(code,state);
//
//        if(wxAccount==null){
//            throw new ValidException("Fail to authorize");
//        }
//
//        GetPrizeResponse response =  getprizeAService.getPrizeA(wxAccount.getOpenid(),actid,reward);
//
//               return  response;
//
//    }

//    @GetMapping("/getPrizeB/{actid}/{reward}")
//    public GetPrizeResponse getPrizeB(@RequestParam(name = "code", required = false) String code,
//                            @RequestParam(name = "state") String state,@PathVariable(name = "actid") String actid,@PathVariable(name = "reward") String reward) throws NoSuchProviderException, NoSuchAlgorithmException, ValidException {
//
//
//        //String openid = "o08-k1CAlytHhsEFrnfnaA-W4FDo";
//        //这里写获取openid的方法
//        WXAccount wxAccount = wxAuthorizeServicew.getWxInfo(code,state);
//
//        if(wxAccount==null){
//            log.error("ZLOG==>Fail to authorize");
//            throw new ValidException("Fail to authorize");
//        }
//
//
//
//       GetPrizeResponse response= getprizeAService.getPrizeB(wxAccount,actid,reward);
//
//        return response;
//
//    }

        @GetMapping("/getPrizeA/{actid}/{reward}")
        public String getPrizeA(WxUser wxUser, @PathVariable(name = "actid") String actid,
                                      @PathVariable(name = "reward")String reward) throws ValidException {

            if(wxUser==null){
                log.error("ZLOG==>Fail to authorize");
                throw new ValidException("Fail to authorize");
            }

        String openid = wxUser.getOpenid();
        log.info("接受到的openid==》"+openid);
        log.info("接受到的昵称==》"+wxUser.getNickname());
        log.info("接受到的城市==》"+wxUser.getCity());
        log.info("接受到的国家==》"+wxUser.getCountry());
        log.info("接受到的性别==》"+wxUser.getSex());
        log.info("接受到的unionid==》"+wxUser.getUnionid());
        log.info("接受到的头像地址=》"+wxUser.getHeadimgurl());

        GetPrizeResponse response = getprizeAService.getPrizeA(openid,actid,reward);

            if(response.getStatus()==200){

                return "successpage.html";
            }

            return "failpage.html";
    }

    @GetMapping("/getPrizeB/{actid}/{reward}")
    public String getPrizeB(WxUser wxUser, @PathVariable(name = "actid") String actid,
                                      @PathVariable(name = "reward")String reward) throws ValidException {
        if(wxUser==null){
            throw new ValidException("Fail to authorize");
        }


       GetPrizeResponse response= getprizeAService.getPrizeB(wxUser.getOpenid(),actid,reward);

        if(response.getStatus()==200){

            return "successpage.html";
        }

        return "failpage.html";


    }


}
