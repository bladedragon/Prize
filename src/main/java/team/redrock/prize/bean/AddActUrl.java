package team.redrock.prize.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddActUrl {
    private String token;
    private String actid;
    private List<Acturl> acturls;
}
