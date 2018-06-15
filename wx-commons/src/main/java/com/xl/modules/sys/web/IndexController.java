package com.xl.modules.sys.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 主页
 * Created by dingrenxin
 * create on 2017/1/11 15:15
 * the email is dingrenxin@jyall.com.
 */
@Controller
public class IndexController {
    @Value("${adminPath:/a}")
    private String adminpath;
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "redirect:"+adminpath +"/login";
    }
}
