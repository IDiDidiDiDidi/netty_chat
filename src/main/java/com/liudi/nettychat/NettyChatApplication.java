package com.liudi.nettychat;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@MapperScan("com.liudi.nettychat.mapper")
@EnableAsync
public class NettyChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(NettyChatApplication.class, args);
    }

}
