package top.zyaire.webview.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.zyaire.webview.service.SerialPortService;

/**
 * @Author ZyaireShu
 * @Date 2022/1/17 9:44
 * @Version 1.0
 */
@CrossOrigin("*")
@RestController
public class SerialApiController {
    private SerialPortService serialPortService;
    @Autowired
    public void setImageUploadService( SerialPortService serialPortService){
        this.serialPortService = serialPortService;
    }

    @RequestMapping(value = "/getPorts", method = RequestMethod.POST)
    public String getPorts(){//获取电脑上端口的api，返回COM1，COM2数组
        JSONObject jb = new JSONObject();
        jb.put("ports",serialPortService.getPortsNames());
        return jb.toJSONString();
    }


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


    @RequestMapping(value = "/move", method = RequestMethod.POST)//
    public String move(@RequestBody JSONObject jsonObject){
        String command = jsonObject.getString("command");//要发送的指令
        serialPortService.sendCommand(command);
        JSONObject jb =  new JSONObject();
        jb.put("success",false);
        return jb.toJSONString();
    }
    @RequestMapping(value = "/disConnect", method = RequestMethod.POST)
    public String disConnect(){
        boolean isClosed = serialPortService.closePort();
        JSONObject jb = new JSONObject();
        jb.put("success",isClosed);
        return jb.toJSONString();
    }

}
