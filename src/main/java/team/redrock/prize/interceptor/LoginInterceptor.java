package team.redrock.prize.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class LoginInterceptor extends HandlerInterceptorAdapter {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String sessionId = (String) request.getSession().getAttribute("SESSIONID");

        // 初始化拦截器，设置不拦截路径
        String noMatchPath=".*/(login).*" ;
        String path=request.getServletPath();

        System.out.println("资源请求路径："+path);
        if(path.matches(noMatchPath)){

            log.error("不拦截："+noMatchPath);
            // 授权路径，不拦截
            return  true;
        } else if(null == sessionId || "".equals(sessionId)) {
            // 找不到用户Token，重定位到登录
            log.info("拦截此页面");
            response.sendRedirect(request.getContextPath() + "/login");
            return  false;
        } else {
            // 设置扩展
            return  true;
        }
    }

}
