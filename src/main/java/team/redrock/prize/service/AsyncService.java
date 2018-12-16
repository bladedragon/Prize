package team.redrock.prize.service;

import team.redrock.prize.bean.PrizeList;
import team.redrock.prize.pojo.response.SpecifiedActResponse;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

public interface AsyncService {

    SpecifiedActResponse createSpecifiedAct(List<PrizeList> typeA, List<String> typeB, String activity, String url, HttpServletRequest request) throws SQLException;
}
