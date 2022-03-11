package top.zyaire.webview.service.impl;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;
import top.zyaire.common.util.StaticUtils;
import top.zyaire.convert.GcodeSVGConverter;
import top.zyaire.entities.ImageToConvert;
import top.zyaire.webview.service.SVGToGcodeService;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.stream.Stream;

/**
 * @Author ZyaireShu
 * @Date 2022/2/11 16:36
 * @Version 1.0
 */
@Service("gcodeConvertService")
public class SVGToGcodeServiceImpl implements SVGToGcodeService {
    private GcodeSVGConverter gcodeSVGConverter;
    private ImageToConvert imageToConvert;

    @Override
    public String convert(int smooth, boolean isLaser, int laserStrength) {
        if (!setConverter()){
            System.out.println(this.toString()+"设置失败");
            return "失败";
        }
        String g = gcodeSVGConverter.convertGcode(smooth,  isLaser,  laserStrength);
        //System.out.println(g);
        new File(StaticUtils.imageStoragePath+imageToConvert.getTmpPath()+imageToConvert.getFileName()+".svg").delete();
        return g;
    }

    @Override
    public String convert(int smooth, boolean isLaser, float moveHeight, float workDepth) {
        if (!setConverter()){
            System.out.println(this.toString()+"设置失败");
            return "失败";
        }
        String g = gcodeSVGConverter.convertGcode(smooth, isLaser, moveHeight, workDepth);
        //System.out.println(g);
        new File(StaticUtils.imageStoragePath+imageToConvert.getTmpPath()+imageToConvert.getFileName()+".svg").delete();
        return g;
    }

    private boolean setConverter(){
        System.out.println("设置文件路径"+StaticUtils.imageStoragePath+imageToConvert.getTmpPath()+imageToConvert.getFileName()+".svg");
        File svg = new File(StaticUtils.imageStoragePath+imageToConvert.getTmpPath()+imageToConvert.getFileName()+".svg");//得到svg文件
        if (!svg.exists()){
            return false;//如果svg文件不存在就没有必要继续下去
        }
        System.out.println(this.toString()+"文件存在");
        BufferedReader a = null;
        try {
            a = new BufferedReader(new FileReader(svg));
            Stream<String> lines = a.lines();
            System.out.println("读取的行数"+lines.count());
//            lines.forEach(s -> {
//                System.out.println("读取文件");
//                System.out.println(s);
//            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                a.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            gcodeSVGConverter = GcodeSVGConverter.getInstance(svg);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public String imageConvert() {
        return null;
    }

    public ImageToConvert getImageToConvert() {
        return imageToConvert;
    }

    public void setImageToConvert(ImageToConvert imageToConvert) {
        this.imageToConvert = imageToConvert;
    }
}
