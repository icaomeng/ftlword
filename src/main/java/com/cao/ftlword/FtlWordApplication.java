package com.cao.ftlword;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.cao.ftlword.mapper")
public class FtlWordApplication {
    public static void main(String[] args) {
        SpringApplication.run(FtlWordApplication.class, args);
        System.err.println("*********ftl导出word项目启动成功*********");
    }
}
