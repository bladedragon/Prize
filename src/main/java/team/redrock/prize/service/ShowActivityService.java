package team.redrock.prize.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.data.redis.core.RedisTemplate;


import org.springframework.stereotype.Service;
import team.redrock.prize.bean.ShowAct;
import team.redrock.prize.bean.TempAct;
import team.redrock.prize.mapper.ActivityMapper;
import team.redrock.prize.pojo.response.ShowActivityResponse;
import team.redrock.prize.utils.SessionUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class ShowActivityService {

    @Autowired
    ActivityMapper activityMapper;
    @Autowired
    private RedisTemplate<String, TempAct> tempActStringRedisTemplate;
    @Autowired
    private RedisTemplate<Object,TempAct> tempActRedisTemplate;


//
//    Set<Object> execute = tempActSringRedisTemplate.execute(new RedisCallback<Set<Object>>() {
//
//        @Override
//        public Set<Object> doInRedis(RedisConnection connection) throws DataAccessException{
//
//            Set<Object> binaryKeys = new HashSet<>();
//
//         Cursor<byte[]> cursor = connection.scan( new ScanOptions.ScanOptionsBuilder().match("CACHE_*").count(1000).build());
//          while (cursor.hasNext()) {
//            binaryKeys.add(new String(cursor.next()));
//      }
//
//            return binaryKeys;
//        }
//    });

    public ShowActivityResponse showActivity(int start, int pagesize, HttpServletRequest request){

        PageHelper.startPage(start,pagesize);
        List<ShowAct> activities =activityMapper.SelectActAll();

        Set<String> keys = tempActStringRedisTemplate.keys("*");
        HttpSession session = request.getSession();
//            Set<Object> keys = execute;


        for(String key:keys){
            String matcherStr = getMatcher(key);
            if(!matcherStr.equals("")) {
                TempAct tempAct = tempActRedisTemplate.opsForValue().get(matcherStr);
                String activity = tempAct.getActivity();
                activities.add(new ShowAct(activity, (String) session.getAttribute("SESSIONNAME"), 2, "", getID(activity), null));
            }
        }

        PageInfo<ShowAct> page = new PageInfo<>(activities);

        int sum = (int) page.getTotal();
        int total = 0;
        if(sum%pagesize!=0){
            total = sum/pagesize+1;
        }else{
            total = sum/pagesize;
        }

        return new ShowActivityResponse(200,"success",total ,page.getList());
    }

    private  static String  getID(String activity){
        String longID = SessionUtil.getMD5(activity);
        String actID = longID.substring(0,6);
        return actID;
    }

    private static String getMatcher(String str){
        Pattern pattern = Pattern.compile("CACHE_(.*)");
        Matcher matcher = pattern.matcher(str);
        String result = "";
        String reStr = "";
        while(matcher.find()){
            reStr = matcher.group(0);
           result = reStr.substring(0,reStr.length()-1);

        }

        return result;
    }

//    public static void main(String[] args) {
//        String str = "\"CACHE_1\"";
//
//        System.out.println(getMatcher(str));
//    }

}
