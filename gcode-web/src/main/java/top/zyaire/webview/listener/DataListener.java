package top.zyaire.webview.listener;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import org.springframework.stereotype.Component;

/**
 * @Author ZyaireShu
 * @Date 2022/1/28 16:19
 * @Version 1.0
 */
public class DataListener implements SerialPortDataListener {
    @Override
    public int getListeningEvents() {
        return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
    }

    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {
        byte[] newData = serialPortEvent.getReceivedData();
        System.out.println("Received data of size: " + newData.length);
        for (int i = 0; i < newData.length; ++i)
            System.out.print((char)newData[i]);
        System.out.println("\n");
    }
}
