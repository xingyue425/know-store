package com.zk.better;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zk.better.mapper")
public class BetterApplication {

    public static void main(String[] args) {
        SpringApplication.run(BetterApplication.class, args);
    }

}
