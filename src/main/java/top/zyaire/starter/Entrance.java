package top.zyaire.starter;


import top.zyaire.ui.UIMain;
import top.zyaire.serial.util.StaticUtils;


/**
 * @Author ZyaireShu
 * @Date 2022/1/12 14:13
 * @Version 1.0
 */
public class Entrance {
    public static void main(String[] args) {
        if (args.length<1){
            top.zyaire.webview.WebViewApplication.main(args);
        }else  if (StaticUtils.containKey(args,"--help") > -1){
            System.out.println("使用--gui来开启图形界面");
        }else if(StaticUtils.containKey(args,"--gui") > -1){
            UIMain.main(args);
        }
    }
}
