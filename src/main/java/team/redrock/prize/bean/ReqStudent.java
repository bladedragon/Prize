package team.redrock.prize.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqStudent {
    private String stuname;
    private String college;
    private String stuid;
    private String telephone;
    private String reward;


}
