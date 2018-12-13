package team.redrock.prize.bean;

import lombok.Data;

import java.util.List;

@Data
public class SpecifiedAct {
    private List<ReqStudent> reqStudents;
    private String activity;
    private String acturl;
}
