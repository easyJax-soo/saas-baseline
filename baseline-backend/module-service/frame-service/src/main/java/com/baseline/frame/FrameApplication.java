package com.baseline.frame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
//@EnableCustomSwagger2
public class FrameApplication extends SpringBootServletInitializer
{

    public static void main(String[] args) {
        SpringApplication.run(FrameApplication.class,args);
        System.out.println("单体部署启动成功");
    }

}
