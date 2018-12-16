package team.redrock.prize.pojo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import team.redrock.prize.bean.StudentA;


import java.util.List;


@Data
@AllArgsConstructor
public class ShowPrizerAResponse {

    private int status;
    private String info;
    private int total;
    private String actId;
    private List<StudentA> data;
}
