package top.zyaire.webview.controller;

import com.alibaba.fastjson.JSONObject;
import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.zyaire.serial.SerialConnect;
import top.zyaire.webview.service.ImageUploadService;
import top.zyaire.serial.util.StaticUtils;
import top.zyaire.webview.service.SerialPortService;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @Author ZyaireShu
 * @Date 2022/1/17 9:44
 * @Version 1.0
 */
@CrossOrigin("*")
@Controller
public class ApiController {
    private ImageUploadService imageUploadService;
    private SerialPortService serialPortService;
    @Autowired
    public void setImageUploadService(ImageUploadService imageUploadService, SerialPortService serialPortService){
        this.imageUploadService = imageUploadService;
        this.serialPortService = serialPortService;
    }

    @ResponseBody
    @RequestMapping("/upload")
    public String upload(@RequestParam("file")MultipartFile file, HttpServletRequest request){
        String format = file.getOriginalFilename();
        format = format.substring(format.lastIndexOf('.')+1);
        int re = StaticUtils.containKey(StaticUtils.supportFormat,format);
        if (re==-1){
            return "不支持的文件类型";
        }
        imageUploadService.upload(file);
        return "上传成功";
    }

    @ResponseBody
    @RequestMapping(value = "/getPorts", method = RequestMethod.POST)
    public String getPorts(){//获取电脑上端口的api，返回COM1，COM2数组
        JSONObject jb = new JSONObject();
        jb.put("ports",serialPortService.getPortsName());
        return jb.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/connectPort", method = RequestMethod.POST)//
    public String connectPort(@RequestBody JSONObject jsonObject){
        System.out.println("收到的消息："+jsonObject.toJSONString());
        String port = jsonObject.getString("port");//COM端口
        int baudRate = Integer.parseInt(jsonObject.getString("baudRate"));//波特率
        boolean connect = serialPortService.openPort(port, baudRate);
        JSONObject jb =  new JSONObject();
        jb.put("success",connect);
        return jb.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/move", method = RequestMethod.POST)//
    public String move(@RequestBody JSONObject jsonObject){
        String command = jsonObject.getString("command");//要发送的指令
        serialPortService.sendCommand(command);
        JSONObject jb =  new JSONObject();
        jb.put("success",false);
        return jb.toJSONString();
    }
}
