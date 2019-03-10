package com.example.mvcbackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = {"com.example.mvcbackend.app.dao","com.example.mvcbackend.sys.security"})
public class MvcBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(MvcBackendApplication.class, args);
    }

}
