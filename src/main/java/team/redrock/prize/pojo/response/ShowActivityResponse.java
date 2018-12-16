package team.redrock.prize.pojo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import team.redrock.prize.bean.Activity;
import team.redrock.prize.bean.ShowAct;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowActivityResponse {

    private  int status;
    private String info ;
    private int total;
    private List<ShowAct> data;
}
