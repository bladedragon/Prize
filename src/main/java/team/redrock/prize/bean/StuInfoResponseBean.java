package team.redrock.prize.bean;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class StuInfoResponseBean {
    private int status;
    private String info;
    private String stuId;
    private String realname;
}
