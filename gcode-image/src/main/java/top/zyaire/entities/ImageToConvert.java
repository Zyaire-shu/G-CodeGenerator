package top.zyaire.entities;

import lombok.Data;
import lombok.Getter;
import top.zyaire.common.util.StaticUtils;
import top.zyaire.util.PotraceHandeler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;

import static top.zyaire.common.util.StaticUtils.imageStoragePath;
import static top.zyaire.common.util.StaticUtils.svgName;

/**
 * 此类是一个单例，一个程序只允许一个ImageToConvert对象存在，通过set和get方法设置此对象持有的Image
 * @Author ZyaireShu
 * @Date 2022/2/11 15:17
 * @Version 1.0
 */
@Data
public class ImageToConvert {

    private File image;
    private String tmpPath;
    private String fileName;//文件名不带后缀
    private final PotraceHandeler potraceHandeler;
    public ImageToConvert(File file,String tmpPath, String fileNme){
        this.image = file;
        this.tmpPath = tmpPath;
        this.fileName = fileNme;
        this.potraceHandeler = new PotraceHandeler();
    }

    public void imageToBmp(){
//        try {
//            BufferedImage sourceImg = ImageIO.read(imageToConvert.image);
//            int h = sourceImg.getHeight(), w = sourceImg.getWidth();
//            int[] pixel = new int[w * h];
//            PixelGrabber pixelGrabber = new PixelGrabber(sourceImg, 0, 0, w, h, pixel, 0, w);
//            pixelGrabber.grabPixels();
//            MemoryImageSource m = new MemoryImageSource(w, h, pixel, 0, w);
//            Image image = Toolkit.getDefaultToolkit().createImage(m);
//            BufferedImage buff = new BufferedImage(w, h, BufferedImage.TYPE_USHORT_565_RGB);
//            buff.createGraphics().drawImage(image, 0, 0 ,null);
//            File bmp = new File(StaticUtils.imageStoragePath+StaticUtils.bmpName);
//            ImageIO.write(buff, "bmp", bmp);
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
    /**
     * 此方法用于将上传的png,jpg图片转换为Bmp
     * @return: void
     * @date: 2022/3/1 8:45
     */
    public  void toBmp()   {
        //System.out.println("图片路径"+image.getName());
        try {
            BufferedImage sourceImg = ImageIO.read(this.image);
            File bmp = new File(StaticUtils.imageStoragePath+tmpPath+fileName+".bmp");
            ImageIO.write(sourceImg, "BMP", bmp);
//            this.image = bmp;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
//    public void bmpToSvg(){
//        potraceHandeler.tracer();
////        getImageToConvert().image = new File(StaticUtils.imageStoragePath+StaticUtils.svgName);
//    }
    public void bmpToSvg(String blacklevel, String turdsize , String alphamax, String opttolerance, String unit){
        String bmpPath = StaticUtils.imageStoragePath+tmpPath+fileName+".bmp";
        String svgPath = StaticUtils.imageStoragePath+tmpPath+fileName+".svg";
        potraceHandeler.setBmpPath(bmpPath);
        potraceHandeler.setSvgPath(svgPath);
        //System.out.println("文件路径"+this.image.getPath());
        potraceHandeler.tracer( blacklevel,  turdsize ,  alphamax,  opttolerance,  unit);
//        getImageToConvert().image = new File(StaticUtils.imageStoragePath+StaticUtils.svgName);
        while (!new File(svgPath).exists()){
            System.out.println("格式转换中");
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        this.image = new File(svgPath);
    }
}
