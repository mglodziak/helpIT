package com.helpit.posts.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping({"/posts","posts"})
    public String getIndex(){
        return "foundation/index";
    }

}


