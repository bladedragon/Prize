package team.redrock.prize.pojo.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GlobalErrorResponse {

    private int status;
    private String msg;

}
