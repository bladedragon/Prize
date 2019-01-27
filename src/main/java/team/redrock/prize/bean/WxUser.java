package team.redrock.prize.bean;

import lombok.Data;

import java.util.List;

@Data
public class WxUser {
    private String openid;
    private String nickname;
    private  String sex;
    private String province;
    private String city;
    private String country;
    private String headimgurl;
    private List<String> privilege;
    private String unionid;


}
