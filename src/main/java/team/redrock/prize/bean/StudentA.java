package team.redrock.prize.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudentA {

    private String openid;
    private String stuname;
    private String college;
    private String stuid;
    private int telephone;
    private String actid;
    private String add_time;
    private String reward;
    private int status;
    private String rewardID;

        public StudentA(String openid,String stuname, String stuid,String actid, String add_time,String reward,String rewardID) {
        this.stuname = stuname;
        this.college = college;
        this.stuid = stuid;
        this.telephone = telephone;
        this.reward = reward;
        this.openid = openid;
        this.actid = actid;
        this.add_time = add_time;
        this.status = status;
        this.rewardID = rewardID;

    }
}
