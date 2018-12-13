package team.redrock.prize.pojo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import team.redrock.prize.bean.StudentA;
import team.redrock.prize.bean.StudentB;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class ShowPrizerResponse {

    private int status;
    private String info;
    private int total;
    private String actId;
    private List<StudentA> data;
}
