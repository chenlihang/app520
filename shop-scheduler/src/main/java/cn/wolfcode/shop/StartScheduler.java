package cn.wolfcode.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by wolfcode on 2018/03/29 0029.
 */
@SpringBootApplication
@EnableScheduling
public class StartScheduler {

    public static void main(String[] args) {

        SpringApplication.run(StartScheduler.class,args);
    }
}
