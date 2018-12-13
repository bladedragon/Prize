package team.redrock.prize.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class OpenidResponseBean {

    private int status;
    private String info;
    private  List<String> openid;
}
