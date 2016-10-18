package com.gutongxue.www.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Shadow on 2016/10/8.
 */
@Controller
public class indexController {
    @RequestMapping("/")
    public String printIndexPage(){
        return "index";
    }
}
