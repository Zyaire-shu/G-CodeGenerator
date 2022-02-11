package top.zyaire.webview;

import top.zyaire.serial.SerialConnect;
import top.zyaire.webview.listener.DataListener;

import java.util.Scanner;

/**
 * @Author ZyaireShu
 * @Date 2022/1/28 15:57
 * @Version 1.0
 */
public class ComTest {
    public static void main(String[] args) {
        SerialConnect cn = SerialConnect.getSerialConnect();
        Scanner sc = new Scanner(System.in);
        String [] a = cn.getPortNames();
        for (int i = 0; i < a.length; i++) {
            System.out.println(i+".端口："+a[i]);
        }
        System.out.println("请输入想要连接的端口：");
        int num = sc.nextInt();
        boolean isConnect = cn.openPort(a[num], 115200);
        cn.setListener(new DataListener());
        while (isConnect){
            System.out.println("请输入想要发送的内容：");
            String input = sc.nextLine();
            if (input.equals("close")){
                isConnect = false;
                cn.closePort();
            }
            cn.sendMessage(input);
        }
    }
}
