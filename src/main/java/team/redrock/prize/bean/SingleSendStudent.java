package team.redrock.prize.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SingleSendStudent {

    private String token;
    private String stuid;
    private String msg;
    private String activity;
    private String reward;
    private String prizeDate;
    private String remark;


}
