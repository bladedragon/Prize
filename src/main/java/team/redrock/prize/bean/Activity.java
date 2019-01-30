package team.redrock.prize.bean;



import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Activity {

    private int id ;
    private String actname;
    private String founder;

    private int status;
    private String time;
    private String actid;
    private String reward;
    private String rewardID;
    private String mark;

    public Activity(String actname,String founder,int status,String time,String actid,String reward,String rewardID,String mark){
        this.actname = actname;
        this.founder  =founder;

        this.status = status;
        this.time = time;
        this.actid = actid;
        this.reward = reward;
        this.rewardID = rewardID;
        this.mark = mark;

    }
}
