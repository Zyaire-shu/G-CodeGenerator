package top.zyaire.webview;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.zyaire.webview.service.SerialPortService;

import java.io.File;

@SpringBootTest
class WebViewApplicationTests {
    @Autowired
    SerialPortService serialPortService;
    @Test
    void contextLoads() {
        File a  = new File("./images");
        if (!a.exists()){
            a.mkdirs();
        }
        System.out.println(a.getAbsolutePath());
    }
    @Test
    void serialTest(){
        String[] a = serialPortService.getPortsNames();
        for (String p :a){
            System.out.println(p);
        }
    }
}
