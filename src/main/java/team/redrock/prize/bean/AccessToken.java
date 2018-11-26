package team.redrock.prize.bean;

import lombok.Data;

@Data
public class AccessToken {

    private String access_token;
    private int expires_in;


}
