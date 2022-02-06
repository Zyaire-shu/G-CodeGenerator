package top.zyaire.serial;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import org.jetbrains.annotations.NotNull;
import top.zyaire.serial.util.StaticUtils;

import java.nio.charset.StandardCharsets;

/**
 * @Author ZyaireShu
 * @Date 2022/1/27 10:33
 * @Version 1.0
 */
public class SerialConnect {
    private SerialPort serialPort;
    private SerialPort[] serialPorts;//所有的端口
    private String[] name;
    public SerialConnect(){
        this.serialPorts = SerialPort.getCommPorts();//获取所有的端口
        this.name = new String[serialPorts.length];
        for (int i=0; i<serialPorts.length; i++){//将所有端口的名字保存到数组里
            this.name[i] = serialPorts[i].getSystemPortName();
        }
    }
    public String[] getPortNames(){
        return name;
    }

    public boolean openPort(String port,int baudRate){
        int a = StaticUtils.containKey(name,port);
        if(a == -1)
            return false;
        serialPort = serialPorts[a];
        serialPort.setBaudRate(baudRate);//设置波特率为112500
        serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING | SerialPort.TIMEOUT_WRITE_BLOCKING, 1000, 1000);//设置超时
        serialPort.setRTS();//设置RTS。也可以设置DTR
        serialPort.setFlowControl(SerialPort.FLOW_CONTROL_DISABLED);//设置串口的控制流，可以设置为disabled，或者CTS, RTS/CTS, DSR, DTR/DSR, Xon, Xoff, Xon/Xoff等
        serialPort.setComPortParameters(baudRate, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);//一次性设置所有的串口参数，第一个参数为波特率，默认9600；第二个参数为每一位的大小，默认8，可以输入5到8之间的值；第三个参数为停止位大小，只接受内置常量，可以选择(ONE_STOP_BIT, ONE_POINT_FIVE_STOP_BITS, TWO_STOP_BITS)；第四位为校验位，同样只接受内置常量，可以选择 NO_PARITY, EVEN_PARITY, ODD_PARITY, MARK_PARITY,SPACE_PARITY。
        serialPort.openPort();
        return isOpen();
    }
    public void setListener(SerialPortDataListener eventListener){
        serialPort.addDataListener(eventListener);//设置数据监听器
    }
    public boolean sendMessage(@NotNull String toSend){//发送数据
        byte[] msg = toSend.getBytes(StandardCharsets.UTF_8);
        return serialPort.writeBytes(msg, msg.length) > -1;
    }
    public boolean closePort(){//关闭端口
        return serialPort.closePort();
    }
    public boolean isOpen(){//判断端口是否开启
        return serialPort.isOpen();
    }
    public void refreshPorts(){
        for(SerialPort s:SerialPort.getCommPorts()){
            System.out.print(s.getSystemPortName()+"_");
            System.out.println(s);
        }
        this.serialPorts = SerialPort.getCommPorts();//获取所有的端口
        this.name = new String[serialPorts.length];
        for (int i=0; i<serialPorts.length; i++){//将所有端口的名字保存到数组里
            this.name[i] = serialPorts[i].getSystemPortName();
        }
    }
    public static SerialPort[] getSerialPorts(){
        return SerialPort.getCommPorts();
    }
}
