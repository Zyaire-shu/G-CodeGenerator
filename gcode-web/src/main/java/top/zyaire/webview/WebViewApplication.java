package top.zyaire.webview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import top.zyaire.common.util.StaticUtils;

import java.io.File;

import static top.zyaire.common.util.StaticUtils.imageStoragePath;

@SpringBootApplication
public class WebViewApplication {

    public static void main(String[] args) {
        creatFolder();
        SpringApplication.run(WebViewApplication.class, args);

    }
    private static void creatFolder(){
        File storeage = new File(imageStoragePath);
        if (!storeage.exists()){
            storeage.mkdirs();
        }else {
            try {
                StaticUtils.deleteFolder(storeage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
