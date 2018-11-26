package team.redrock.prize;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("team.redrock.prize.mapper")
public class PrizeApplication {

    public static void main(String[] args) {
        SpringApplication.run(PrizeApplication.class, args);
    }
}
