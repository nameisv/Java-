package com.xiaoniucr;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 */
@MapperScan("com.xiaoniucr.mapper")
@SpringBootApplication
public class CarnieMgrApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarnieMgrApplication.class, args);
    }

}
