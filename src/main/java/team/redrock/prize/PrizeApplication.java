package team.redrock.prize;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.WebApplicationInitializer;
import team.redrock.prize.Access_token.Scheduler;
import team.redrock.prize.bean.TemplateMsg;
import team.redrock.prize.service.AsyncService;
import team.redrock.prize.service.TemplateMessageService;

import javax.annotation.PostConstruct;

@EnableAsync
@Configuration
@ServletComponentScan
@SpringBootApplication
@MapperScan("team.redrock.prize.mapper")
public class PrizeApplication extends SpringBootServletInitializer implements WebApplicationInitializer {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private TemplateMessageService templateMessageService;

    @Autowired
    private Scheduler scheduler;

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application){

        return application.sources(PrizeApplication.class);
    }




    public static void main(String[] args) {
        SpringApplication.run(PrizeApplication.class, args);
    }
}
