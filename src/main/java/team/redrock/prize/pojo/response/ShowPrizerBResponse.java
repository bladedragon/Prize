package team.redrock.prize.pojo.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import team.redrock.prize.bean.StudentA;
import team.redrock.prize.bean.StudentB;

import java.util.List;

@Data
@AllArgsConstructor
public class ShowPrizerBResponse {

    private int status;
    private String info;
    private int total;
    private String actId;
    private List<StudentB> data;
}