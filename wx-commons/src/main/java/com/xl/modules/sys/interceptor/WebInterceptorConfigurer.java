package com.xl.modules.sys.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by dingrenxin
 * Created on 2017/1/15 20:21
 * Mail 77219569@qq.com
 */
@Component
public class WebInterceptorConfigurer extends WebMvcConfigurerAdapter{
    @Autowired
    private LogInterceptor logInterceptor;
    @Autowired
    private LogThread logThread;

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logInterceptor).addPathPatterns("/**");
        super.addInterceptors(registry);
        logThread.start();
    }
}
