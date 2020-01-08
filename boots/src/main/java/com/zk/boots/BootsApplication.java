package com.zk.boots;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.zk.boots.mapper")
public class BootsApplication {

  public static void main(String[] args) {
    SpringApplication.run(BootsApplication.class, args);
  }


}
