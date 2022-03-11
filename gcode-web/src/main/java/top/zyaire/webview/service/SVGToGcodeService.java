package top.zyaire.webview.service;

import top.zyaire.entities.ImageToConvert;

/**
 * @Author ZyaireShu
 * @Date 2022/2/11 16:35
 * @Version 1.0
 */
public interface SVGToGcodeService {
    public String convert(int smooth,boolean isLaser,int laserStrength);
    public String convert(int smooth,boolean isLaser,float moveHeight, float workDepth);
    public String imageConvert();
    public ImageToConvert getImageToConvert();
    public void setImageToConvert(ImageToConvert imageToConvert);
}
