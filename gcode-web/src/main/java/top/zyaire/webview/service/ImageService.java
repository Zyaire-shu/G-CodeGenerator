package top.zyaire.webview.service;

import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import top.zyaire.entities.ImageToConvert;

/**
 * @Author ZyaireShu
 * @Date 2022/1/18 10:04
 * @Version 1.0
 */
public interface ImageService {
    boolean upload(MultipartFile file, String tmpPath);
    void toSvg();
    ImageToConvert toSvg(String blacklevel, String turdsize , String alphamax, String opttolerance, String unit);
    public ImageToConvert getImageToConvert();
    public void setImageToConvert(ImageToConvert imageToConvert);
}
