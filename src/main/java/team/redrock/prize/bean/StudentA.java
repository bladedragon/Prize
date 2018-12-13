package team.redrock.prize.bean;

import lombok.Data;

@Data
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

    public StudentA(String openid,String stuname, String college, String stuid,int telephone,String actid, String add_time,  String reward,int status ) {
        this.stuname = stuname;
        this.college = college;
        this.stuid = stuid;
        this.telephone = telephone;
        this.reward = reward;

        this.openid = openid;
        this.actid = actid;
        this.add_time = add_time;
        this.status = status;

    }
}
