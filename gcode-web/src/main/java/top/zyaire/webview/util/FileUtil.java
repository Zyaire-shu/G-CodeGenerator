package top.zyaire.webview.util;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @Author ZyaireShu
 * @Date 2022/1/18 10:06
 * @Version 1.0
 */
public class FileUtil {
    /**
     *
     * @param file 文件
     * @param filePath 文件存放路径
     * @param fileName 源文件名
     * @return
     */
    public static void upload(byte[] file, String filePath, String fileName) throws Exception{
        File targetFile = new File(filePath);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        System.out.println("文件路径"+filePath+fileName);
        FileOutputStream out = new FileOutputStream(filePath+fileName);
        out.write(file);
        out.flush();
        out.close();
    }

}
