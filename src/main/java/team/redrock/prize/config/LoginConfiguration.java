package team.redrock.prize.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import team.redrock.prize.interceptor.LoginInterceptor;

@Configuration
public class LoginConfiguration extends WebMvcConfigurerAdapter {
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截所有的controller
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**");
    }

}
