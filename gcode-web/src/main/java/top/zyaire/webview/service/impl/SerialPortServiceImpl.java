package top.zyaire.webview.service.impl;

import org.springframework.stereotype.Service;
import top.zyaire.serial.SerialConnect;
import top.zyaire.webview.listener.DataListener;
import top.zyaire.webview.service.SerialPortService;

/**
 * @Author ZyaireShu
 * @Date 2022/2/3 12:18
 * @Version 1.0
 */
@Service("serialPortService")
public class SerialPortServiceImpl implements SerialPortService {
    private SerialConnect serialConnect = new SerialConnect();//端口操作对象，封装了常用的端口操作方法
    @Override
    public boolean openPort(String port, int baudRate) {
        boolean open =  serialConnect.openPort(port,baudRate);//打开端口
        if (open){//如果端口打开成功，就设置接收数据的监听器
            serialConnect.setListener(new DataListener());
        }
        return open;
    }

    @Override
    public void sendCommand(String cmd) {
            serialConnect.sendMessage(cmd);
    }

    @Override
    public boolean closePort() {
        return serialConnect.closePort();
    }

    @Override
    public String[] getPortsName() {
        serialConnect.refreshPorts();//获取端口之前刷新端口
        return serialConnect.getPortNames();
    }
}
