package team.redrock.prize.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.AccessType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import team.redrock.prize.exception.ValidException;
import team.redrock.prize.pojo.RequestWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Slf4j
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    RedisTemplate<String,String> stringRedisTemplate;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ValidException {


        HttpSession session = request.getSession();

        // 初始化拦截器，设置不拦截路径
        String noMatchPath = ".*/(login).*";
        String path = request.getServletPath();

        String errorPath = ".*/(error).*";

        System.out.println("资源请求路径：" + path);
        if(path.matches(errorPath)){


            throw new ValidException("Internal Server Error");
        }
        if (path.matches(noMatchPath)) {

            log.error("不拦截：" + noMatchPath);
            // 授权路径，不拦截
            return true;
        } else if (session.getAttribute("SESSIONID") != null) {
long time = System.currentTimeMillis();

            String token = request.getParameter("token");
            if(token==null||token.equals("")){
                RequestWrapper requestWrapper = new RequestWrapper(request);
                String body = requestWrapper.getBody();
            }


//            System.out.println("++++++++++++++++++++++"+body);

            Map<Object, Object> sessionMap =  stringRedisTemplate.opsForHash().entries("SESSIONID");
            for (Object sessionId:sessionMap.values() ) {
                    if (sessionId != null && sessionId.equals(session.getAttribute("SESSIONID"))) {
                        System.out.println("sessionId =="+sessionId);
                        stringRedisTemplate.expire("SESSIONID",30, TimeUnit.MINUTES);
                        return true;
                    }
            }

        }

//            if(stringRedisTemplate.hasKey("SESSIONID")){
//                System.out.println("has key");
//            }else{
//                System.out.println("has not key");
//            }

        System.out.println(session.getAttribute("SESSIONID"));
        throw new ValidException("session不存在");

    }

}
