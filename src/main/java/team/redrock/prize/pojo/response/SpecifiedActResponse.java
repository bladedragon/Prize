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

  private  int status;
    private String info;
   private  String actid;
   private Map<String ,String> AactID;
    private Map<String,String> BactID;
   private  List<Map<String ,String>> msg;




}
