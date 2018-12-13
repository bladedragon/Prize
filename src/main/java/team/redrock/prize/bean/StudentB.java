package team.redrock.prize.bean;


import lombok.Data;

@Data
public class StudentB {
    private String stuname;
    private String college;
    private String stuid;
    private int telephone;
    private String reward;

    private String openid;
    private String actid;
    private String add_time;

    public StudentB(String stuname, String college, String stuid, int telephone, String reward, String actid, String openid, String add_time) {
        this.stuname = stuname;
        this.college = college;
        this.stuid = stuid;
        this.telephone = telephone;
        this.reward = reward;

        this.openid = openid;
        this.actid = actid;
        this.add_time = add_time;

    }
}
