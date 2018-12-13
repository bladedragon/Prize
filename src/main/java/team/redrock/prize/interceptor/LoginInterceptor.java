package team.redrock.prize.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.AccessType;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import team.redrock.prize.exception.ValidException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ValidException {


        HttpSession session = request.getSession();

        // 初始化拦截器，设置不拦截路径
        String noMatchPath = ".*/(login).*";
        String path = request.getServletPath();

        System.out.println("资源请求路径：" + path);
        if (path.matches(noMatchPath)) {

            log.error("不拦截：" + noMatchPath);
            // 授权路径，不拦截
            return true;
        } else if (session.getAttribute("SESSIONID") != null) {
            Map<Object, Object> sessionMap =  stringRedisTemplate.opsForHash().entries("SESSIONID");
            for (Object sessionId:sessionMap.values() ) {
                    if (sessionId != null && sessionId.equals(session.getAttribute("SESSIONID"))) {
                        System.out.println("sessionId =="+sessionId);
                        stringRedisTemplate.expire("SESSIONID",30, TimeUnit.MINUTES);
                        return true;
                    }
            }

        }
        throw new ValidException("session不存在");
    }


    private void noSessionResponse(HttpServletResponse response){

    }
}
