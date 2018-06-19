package com.xl;

import com.xl.modules.sys.service.SystemService;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * xl-wx
 * springboot的启动类
 * Created by rishi
 * Created on 2018/6/18 16:20
 * Mail 77219569@qq.com
 */
@EnableCaching
@SpringBootApplication
@ServletComponentScan("com.xl")
@Configuration  
@EnableAutoConfiguration 
@ComponentScan(value = "com.xl",lazyInit = false)
public class WxApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(WxApplication.class).web(true).run(args);
        SystemService.printKeyLoadMessage();
    }
}
