package team.redrock.prize.pojo.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpecifiedActResponse {

    int status;
    String info;
   private  String actid;
   private  List<Map<String ,String>> msg;




}
