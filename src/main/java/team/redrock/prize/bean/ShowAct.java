package team.redrock.prize.bean;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowAct {

    private String actname;
    private String founder;
    private String url;
    private int status;
    private String time;
    private String actid;

}
