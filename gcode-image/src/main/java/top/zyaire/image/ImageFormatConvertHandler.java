package top.zyaire.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @Author ZyaireShu
 * @Date 2022/1/11 16:54
 * @Version 1.0
 */
public class ImageFormatConvertHandler {
    private final File image;//文件的路径
    ImageFormatConvertHandler(String imagePath) throws FileNotFoundException {
      File a = new File(imagePath);
      if (!a.exists()){
          throw new FileNotFoundException();
      }else {
          image = a;
      }
    }
    ImageFormatConvertHandler(File image){
        this.image = image;
    }

    public static void image2RGB565Bmp(String filePath, String saveFileName) {
        try {
            BufferedImage sourceImg = ImageIO.read(new File(filePath));
            int h = sourceImg.getHeight(), w = sourceImg.getWidth();
            int[] pixel = new int[w * h];
            PixelGrabber pixelGrabber = new PixelGrabber(sourceImg, 0, 0, w, h, pixel, 0, w);
            pixelGrabber.grabPixels();

            MemoryImageSource m = new MemoryImageSource(w, h, pixel, 0, w);
            Image image = Toolkit.getDefaultToolkit().createImage(m);
            BufferedImage buff = new BufferedImage(w, h, BufferedImage.TYPE_USHORT_565_RGB);
            buff.createGraphics().drawImage(image, 0, 0 ,null);
            ImageIO.write(buff, "bmp", new File(saveFileName));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
