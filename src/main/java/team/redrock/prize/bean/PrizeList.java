package team.redrock.prize.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrizeList {
    private String reward;
    private String sendmsg;
    private List<ReqStudent> reqStudents;
}
