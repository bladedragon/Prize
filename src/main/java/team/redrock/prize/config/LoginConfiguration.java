package team.redrock.prize.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import team.redrock.prize.interceptor.LoginInterceptor;

import javax.annotation.Resource;

@Configuration
public class LoginConfiguration extends WebMvcConfigurerAdapter {

    @Bean
    public  LoginInterceptor loginInterceptor(){
        return new LoginInterceptor();
    }


    public void addInterceptors(InterceptorRegistry registry) {
        //拦截所有的controller
//        registry.addInterceptor(loginInterceptor()).addPathPatterns("/**").excludePathPatterns("/wx/token").excludePathPatterns("/getPrizeA/**").excludePathPatterns("/getPrizeB/**");
    }

}
