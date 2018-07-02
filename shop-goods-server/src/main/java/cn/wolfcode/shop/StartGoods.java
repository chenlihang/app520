package cn.wolfcode.shop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by wolfcode on 2018/03/29 0029.
 */
@SpringBootApplication
@MapperScan("cn.wolfcode.shop.mapper")
@EnableTransactionManagement
@PropertySource("classpath:redis.properties")
public class StartGoods {

    public static void main(String[] args) {

        SpringApplication.run(StartGoods.class,args);
    }
}
