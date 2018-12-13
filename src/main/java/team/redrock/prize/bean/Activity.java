package team.redrock.prize.bean;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@NoArgsConstructor
public class Activity {

    private int id ;
    private String actname;
    private String founder;
    private String url;
    private int type;
    private int status;
    private String time;
    private String actid;

    public Activity(String actname,String founder,String url,int type,int status,String time,String actid){
        this.actname = actname;
        this.founder  =founder;
        this.url = url;
        this.type = type;
        this.status = status;
        this.time = time;
        this.actid = actid;
    }
}
