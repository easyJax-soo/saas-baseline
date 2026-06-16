package com.baseline.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * 认证授权中心
 *
 * @author
 */
@SpringBootApplication
public class AuthApplication extends SpringBootServletInitializer
{
    public static void main(String[] args)
    {
        SpringApplication.run(AuthApplication.class, args);
        System.out.println("认证授权中心启动成功");
    }
}
