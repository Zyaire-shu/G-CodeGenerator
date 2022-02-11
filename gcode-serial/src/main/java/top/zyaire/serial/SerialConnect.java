package top.zyaire.serial;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import org.jetbrains.annotations.NotNull;
import top.zyaire.common.util.StaticUtils;

import java.nio.charset.StandardCharsets;

/**
 * @Author ZyaireShu
 * @Date 2022/1/27 10:33
 * @Version 1.0
 */

public class SerialConnect {
    /**
     * SerialPort对象 一个SerialConnect保存一个SerialPort对象，程序整个生命周期内只允许存在一个SerialConnect对象
     */
    private SerialPort serialPort;
    private SerialPort[] serialPorts;//所有的串口对象
    private String[] name;//所有串口的名称COM形式
    private static SerialConnect serialConnect;//当前类的实例
    private String portName;
    private int baudRate;
    /**
     * 构造方法私有，此对象为单例
     *
     * @return: null
     * @date: 2022/2/11 12:59
     */
    private SerialConnect() {
        this.serialPorts = SerialPort.getCommPorts();//获取所有的端口
        this.name = new String[serialPorts.length];
        for (int i = 0; i < serialPorts.length; i++) {//将所有端口的名字保存到数组里
            this.name[i] = serialPorts[i].getSystemPortName();
        }
    }

    /**
     * 获得SerialConnect的对象
     *
     * @return: top.zyaire.serial.SerialConnect
     * @date: 2022/2/11 13:01
     */
    public static SerialConnect getSerialConnect() {
        if (serialConnect == null) {
            serialConnect = new SerialConnect();
        }
        return serialConnect;
    }

    /**
     * 获得本机所有串口的名称
     *
     * @return: java.lang.String[]
     * @date: 2022/2/11 13:05
     */
    public String[] getPortNames() {
        return name;
    }

    /**
     * 打开串口
     * @param port:     串口的名称 COM
     * @param baudRate: 串口的波特率
     * @return: boolean
     * @date: 2022/2/11 13:06
     */
    public boolean openPort(String port, int baudRate) {
        int a = StaticUtils.containKey(name, port);
        if (a == -1)
            return false;
        this.portName = port;
        this.baudRate = baudRate;
        serialPort = serialPorts[a];
        serialPort.setBaudRate(baudRate);//设置波特率为112500
        serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING | SerialPort.TIMEOUT_WRITE_BLOCKING, 1000, 1000);//设置超时
        serialPort.setRTS();//设置RTS。也可以设置DTR
        serialPort.setFlowControl(SerialPort.FLOW_CONTROL_DISABLED);//设置串口的控制流，可以设置为disabled，或者CTS, RTS/CTS, DSR, DTR/DSR, Xon, Xoff, Xon/Xoff等
        serialPort.setComPortParameters(baudRate, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);//一次性设置所有的串口参数，第一个参数为波特率，默认9600；第二个参数为每一位的大小，默认8，可以输入5到8之间的值；第三个参数为停止位大小，只接受内置常量，可以选择(ONE_STOP_BIT, ONE_POINT_FIVE_STOP_BITS, TWO_STOP_BITS)；第四位为校验位，同样只接受内置常量，可以选择 NO_PARITY, EVEN_PARITY, ODD_PARITY, MARK_PARITY,SPACE_PARITY。
        serialPort.openPort();
        return isOpen();
    }

    /**
     * 设置监听来自串口消息的监听器
     *
     * @param eventListener: 接收来自串口消息的监听器
     * @return: void
     * @date: 2022/2/11 13:06
     */
    public void setListener(SerialPortDataListener eventListener) {
        serialPort.addDataListener(eventListener);//设置数据监听器
    }

    /**
     * 调用此方法将数据发送到串口
     *
     * @param toSend: 将要发送到串口的数据
     * @return: boolean
     * @date: 2022/2/11 13:07
     */
    public boolean sendMessage(@NotNull String toSend) {//发送数据
        byte[] msg = toSend.getBytes(StandardCharsets.UTF_8);
        return serialPort.writeBytes(msg, msg.length) > -1;
    }

    /**
     * 关闭串口
     *
     * @return: boolean
     * @date: 2022/2/11 13:07
     */
    public boolean closePort() {//关闭端口
        return serialPort.closePort();
    }

    /**
     * 查询串口是否打开
     *
     * @return: boolean
     * @date: 2022/2/11 13:07
     */
    public boolean isOpen() {//判断端口是否开启
        if (serialPort == null)
            return false;
        return serialPort.isOpen();
    }

    /**
     * 刷新所有的串口
     *
     * @return: void
     * @date: 2022/2/11 13:08
     */
    public void refreshPorts() {
        for (SerialPort s : SerialPort.getCommPorts()) {
            System.out.print(s.getSystemPortName() + "_");
            System.out.println(s);
        }
        this.serialPorts = SerialPort.getCommPorts();//获取所有的端口
        this.name = new String[serialPorts.length];
        for (int i = 0; i < serialPorts.length; i++) {//将所有端口的名字保存到数组里
            this.name[i] = serialPorts[i].getSystemPortName();
        }
    }

    public static SerialPort[] getSerialPorts() {
        return SerialPort.getCommPorts();
    }

    public String getPortName() {
        return portName;
    }

    public int getBaudRate() {
        return baudRate;
    }
}
