package team.redrock.prize.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class TempAct {

    private String activity;

//    private List<PrizeList> typeA;
//    private List<String> typeB;
    private TypeA_Temp typeA_temp;
    private TypeB_Temp typeB_temp;
}
