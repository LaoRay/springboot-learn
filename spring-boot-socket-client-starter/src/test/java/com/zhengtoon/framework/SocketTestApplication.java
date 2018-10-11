package com.zhengtoon.framework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class SocketTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SocketTestApplication.class, args);
        System.out.println("################主方法执行完毕###############");
    }

}
