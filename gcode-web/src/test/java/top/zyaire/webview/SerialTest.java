package top.zyaire.webview;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.zyaire.serial.SerialConnect;
import top.zyaire.webview.listener.DataListener;
import top.zyaire.webview.service.SerialPortService;

import java.util.Scanner;

/**
 * @Author ZyaireShu
 * @Date 2022/2/3 13:46
 * @Version 1.0
 */
@SpringBootTest
public class SerialTest {
    @Autowired
    SerialPortService serialPortService;
    @Test
    void ss(){

        Scanner sc = new Scanner(System.in);
        String [] a = serialPortService.getPortsName();
        for (int i = 0; i < a.length; i++) {
            System.out.println(i+".端口："+a[i]);
        }
        System.out.println("请输入想要连接的端口：");
        int num = 0;
        boolean isConnect = serialPortService.openPort(a[num], 115200);
        while (isConnect){
            System.out.println("请输入想要发送的内容：");
            String input = sc.nextLine();
            if (input.equals("close")){
                isConnect = false;
                serialPortService.closePort();
            }
            serialPortService.sendCommand(input);
        }
    }
}
