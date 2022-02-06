package top.zyaire.webview.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author ZyaireShu
 * @Date 2022/1/12 14:32
 * @Version 1.0
 */
@CrossOrigin("*")
@Controller
public class PageController {

    @RequestMapping("/index")
    public String index(Model model){
        
        return "index";
    }
}
