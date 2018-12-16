package team.redrock.prize.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import team.redrock.prize.mapper.UserMapper;
import team.redrock.prize.bean.UserInfo;
import team.redrock.prize.pojo.response.UserResponse;
import team.redrock.prize.utils.SessionUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {

    @Autowired
     private UserMapper userMapper;
    @Autowired
    StringRedisTemplate stringRedisTemplate ;

        public UserResponse Login(String username, String password, HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession(true);
        System.out.println("---------------------------------------------"+System.currentTimeMillis()+"-------------------------------------------------");



        if(IsNull(username)){
          return new UserResponse(-1,"用户名不能为空");

        }else if(IsNull(password)) {

            return new UserResponse(-2,"密码不能为空！");
        }else {

            UserInfo user =  userMapper.SelectByUsername(username);
            if(user ==null){
                return new UserResponse(-3,"用户名不存在");
            }
            if(!password.equals(user.getPassword())){
                return new UserResponse(-4,"密码错误！");

            }else{
                System.out.println("---------------------------------------------"+System.currentTimeMillis()+"-------------------------------------------------");
                long time =System.currentTimeMillis();
                if(session.getAttribute("SESSIONID")!=null&&stringRedisTemplate.opsForHash().hasKey("SESSIONID",username))
                {
                       return new UserResponse(1,"你已登录");

                }
                    SessionUtil sessionUtil = new SessionUtil();
                System.out.println("-----------------------"+(System.currentTimeMillis()-time)+"-------------------------");
                    String usersession = sessionUtil.createSessionId(username);
                    System.out.println("创建session: "+usersession);
                    //响应添加cookie
//                    Cookie cookie = new Cookie("SESSIONID",usersession);
//                    cookie.setPath(request.getContextPath());
//                    response.addCookie(cookie);
//                    //设置session
                System.out.println("-----------------------"+(System.currentTimeMillis()-time)+"-------------------------");
                    session.setAttribute("SESSIONID",usersession);
                    session.setAttribute("SESSIONNAME",username);
//                    session.setMaxInactiveInterval(30*60);
                System.out.println("-----------------------"+(System.currentTimeMillis()-time)+"-------------------------");
                    stringRedisTemplate.opsForHash().put("SESSIONID",username,usersession);
                    stringRedisTemplate.expire("SESSIONID",30,TimeUnit.MINUTES);
                System.out.println("-----------------------"+(System.currentTimeMillis()-time)+"-------------------------");
//                    response.addHeader("SESSIONID",usersession);
                System.out.println("---------------------------------------------"+System.currentTimeMillis()+"-------------------------------------------------");

                return new UserResponse(0,"登录成功");

                }

            }
        }



    private static boolean IsNull(String str) {
        return str == null || str.trim().equals("") || str.isEmpty();
    }
}
