package team.redrock.prize;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.WebApplicationInitializer;

@SpringBootApplication
@MapperScan("team.redrock.prize.mapper")
public class PrizeApplication extends SpringBootServletInitializer implements WebApplicationInitializer {

//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application){
//
//        return application.sources(PrizeApplication.class);
//    }


    public static void main(String[] args) {
        SpringApplication.run(PrizeApplication.class, args);
    }
}
