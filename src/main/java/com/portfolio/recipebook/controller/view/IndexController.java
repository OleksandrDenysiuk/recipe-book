package com.portfolio.recipebook.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping({"","/","index.html"})
    public String getIndexPage(){
        return "index";
    }

}
