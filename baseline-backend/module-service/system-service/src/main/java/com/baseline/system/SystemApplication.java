package com.baseline.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 系统模块
 * 
 * @author ruoyi
 */
@SpringBootApplication
@EnableScheduling
public class SystemApplication extends SpringBootServletInitializer
{

    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class,args);
        System.out.println("系统模块启动成功");
    }

}
