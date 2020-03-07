package com.jd.qz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(value = "com.jd.qz.mapper")
public class QzApplication {

    public static void main(String[] args) {
        SpringApplication.run(QzApplication.class, args);
    }


}
