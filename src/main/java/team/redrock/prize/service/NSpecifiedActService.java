package team.redrock.prize.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.redrock.prize.mapper.ActivityMapper;
import team.redrock.prize.mapper.SpecifiedTypeMapper;
import team.redrock.prize.pojo.response.NSpecifiedActResponse;
import team.redrock.prize.utils.SessionUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class NSpecifiedActService {

    @Autowired
    ActivityMapper activityMapper;
    @Autowired
    SpecifiedTypeMapper specifiedTypeMapper;

    public NSpecifiedActResponse createNSpecifiedAct(String activity, String url, HttpServletRequest request) {
        HttpSession session = request.getSession();

        List<String> actids = activityMapper.SelectActivityId(activity);
        if(!actids.isEmpty()){
            return new NSpecifiedActResponse(1, "Activity is exist", actids.get(0));
        }
        String actid = actids.get(0);

        if (!actid.equals("0")) {
            return new NSpecifiedActResponse(1, "Activity is exist", actid);
        } else {

            log.info("________________________________________sessionname = " + session.getAttribute("SESSIONNAME"));
            SimpleDateFormat f_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = f_date.format(new Date());

            actid = getactID(activity);
//            activityMapper.insert(new Activity(activity, (String) session.getAttribute("SESSIONNAME"), url, 0, 1, date, actid));
            return new NSpecifiedActResponse(0, "success", actid);
        }
    }

    private  static String  getactID(String activity){
        String longID = SessionUtil.getMD5(activity);
        String actID = longID.substring(0,6);
        return actID;
    }
}
