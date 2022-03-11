package top.zyaire.webview.util;

import lombok.Data;
import top.zyaire.entities.ImageToConvert;

import java.util.HashMap;
import java.util.List;

/**
 * @Author ZyaireShu
 * @Date 2022/3/10 16:53
 * @Version 1.0
 */
public class SessionUtils {
    private static final HashMap<String, ImageToConvert> imageToConvertMap = new HashMap<>();
    public static void insertImageToConvert(String tmpPath, ImageToConvert imageToConvert){
        imageToConvertMap.put(tmpPath, imageToConvert);
    }
    public static ImageToConvert getImagetToConvertByTmpPath(String tmpPath){
        return imageToConvertMap.get(tmpPath);
    }
}
