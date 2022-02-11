package top.zyaire.webview.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import top.zyaire.webview.service.ImageUploadService;
import top.zyaire.webview.service.SerialPortService;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author ZyaireShu
 * @Date 2022/1/12 14:32
 * @Version 1.0
 */
@CrossOrigin("*")
@Controller
public class PageController {
    private SerialPortService serialPortService;

    @Autowired
    public void setImageUploadService(SerialPortService serialPortService){
        this.serialPortService = serialPortService;
    }
    @RequestMapping("/index")
    public String index(Model model){
        
        return "index";
    }
    @RequestMapping("/svg")
    public String svg(Model model){
        model.addAttribute("isOpen",serialPortService.isOpen());
        model.addAttribute("connectedPort",serialPortService.getConnectedPortName());
        model.addAttribute("connectedBaudRate",serialPortService.getConnectedBaudRate());
        return "svg";
    }
    @RequestMapping("/edge")
    public String edge(Model model){

        return "edge";
    }
}
