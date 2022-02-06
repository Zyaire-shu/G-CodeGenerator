package top.zyaire.webview.service;

/**
 * @Author ZyaireShu
 * @Date 2022/2/3 12:17
 * @Version 1.0
 */
public interface SerialPortService {
    /**
     * @param port: 传入需要连接的串口口名称
     * @param baudRate: 串口的波特率
     * @return: boolean
     * @author: ZyaireShu
     * @date: 2022/2/4 10:42
     * @description: 用于连接串口
     */
    public boolean openPort(String port, int baudRate);

    /**
     * @param cmd: 需要用串口发送的指令
     * @return: void
     * @author: ZyaireShu
     * @date: 2022/2/4 10:42
     * @description: 向串口发送指令
     */
    public void sendCommand(String cmd);

    /**
     * @return: boolean
     * @author: ZyaireShu
     * @date: 2022/2/4 10:43
     * @description: 关闭串口
     */
    public boolean closePort();

    /**
     * @return: java.lang.String[]
     * @author: ZyaireShu
     * @date: 2022/2/4 10:43
     * @description: 获取当前计算机所有的串口的串口名
     */
    public String[] getPortsName();
}
