package team.redrock.prize.pojo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import team.redrock.prize.bean.TempAct;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowTempReponse {

    private int status;
    private String info;
    private TempAct data;
}
