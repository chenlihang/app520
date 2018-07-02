package cn.wolfcode.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by wolfcode on 2018/03/29 0029.
 */
@Controller
public class MainController {

    @RequestMapping(value = "/main")
    public String main(){
        return "main";
    }

    @RequestMapping(value = "/")
    public String root(){
        return "forward:/main";
    }

    @RequestMapping(value = "/index")
    public String index(){
        return "index";
    }
}
