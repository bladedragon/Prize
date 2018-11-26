package team.redrock.prize.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.redrock.prize.mapper.UserMapper;
import team.redrock.prize.bean.UserInfo;
import team.redrock.prize.pojo.response.UserResponse;
import utils.SessionUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Service
public class UserService {

    @Autowired
     private UserMapper userMapper;

    public UserResponse Login(String username, String password, HttpServletRequest request, HttpServletResponse response){


        if(IsNull(username)){
          return new UserResponse("-1","用户名不能为空");

        }else if(IsNull(password)) {

            return new UserResponse("-2","密码不能为空！");
        }else {

            UserInfo user =  userMapper.SelectByUsername(username);
            if(!password.equals(user.getPassword())){
                return new UserResponse("-3","密码错误！");

            }else{
                if(request.getCookies()!=null){
                    return new UserResponse("1","你已登录");
                }else{
                    SessionUtil sessionUtil = new SessionUtil();
                    HttpSession session = request.getSession();
                    String usersession = sessionUtil.createSessionId(username);
//                    //响应添加cookie
//                    Cookie cookie = new Cookie("SESSIONID",usersession);
//                    cookie.setPath(request.getContextPath());
//                    response.addCookie(cookie);
                    //设置session
                    session.setAttribute("SESSIONID",usersession);
                    session.setMaxInactiveInterval(30*60);
                    return new UserResponse("0","登录成功");
                }

            }
        }


    }

    private static boolean IsNull(String str) {
        return str == null || str.trim().equals("") || str.isEmpty();
    }
}
