package top.zyaire.convert;

import lombok.Data;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import top.zyaire.common.util.StaticUtils;
import top.zyaire.gcode.DocParser;
import top.zyaire.gcode.Gcode;
import top.zyaire.gcode.Options;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * 这个类是生成Gcode的类用于对生成的方式进行配置，比如用多少段曲线来近似贝塞尔曲线
 * @Author ZyaireShu
 * @Date 2022/2/11 16:24
 * @Version 1.0
 */
@Data
public class GcodeSVGConverter {
    private DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
    private Document doc;
    private DocumentBuilder documentBuilder;
    private static GcodeSVGConverter gcodeSVGConverter;
    private GcodeSVGConverter(File file) throws ParserConfigurationException, IOException, SAXException {
        documentBuilderFactory.setValidating(false);
        documentBuilderFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        documentBuilder = documentBuilderFactory.newDocumentBuilder();
        doc = documentBuilder.parse(file);
    }
    public static GcodeSVGConverter getInstance(File file) throws ParserConfigurationException, IOException, SAXException {
        System.out.println("文件名"+file.getName());
        if (gcodeSVGConverter == null){
            gcodeSVGConverter = new GcodeSVGConverter(file);
            return gcodeSVGConverter;
        }
        gcodeSVGConverter.setDoc(file);
        return gcodeSVGConverter;
    }
    private void setDoc(File doc) throws IOException, SAXException {
        this.doc = documentBuilder.parse(doc);
    }
    public String convertGcode(int smooth, boolean isLaser, int laserStrength){
        Options a = new Options();
        a.setLaser(isLaser);
        a.setLaserPower(laserStrength);
        a.setCurveSamplingStep(smooth);
        Gcode g = DocParser.docToGcode(doc,"test", a);
        g.saveToFile(StaticUtils.imageStoragePath+"test.txt");
        return new String(g.commandsAsByteArray());
    }
    public String convertGcode(int smooth, boolean isLaser, float moveHeight, float workDepth){
        Options a = new Options();
        a.setLaser(isLaser);
        a.setMoveHeight(moveHeight);
        a.setWorkDepth(workDepth);
        a.setCurveSamplingStep(smooth);
        Gcode g = DocParser.docToGcode(doc,"test", a);
        g.saveToFile(StaticUtils.imageStoragePath+"test.txt");
        return new String(g.commandsAsByteArray());
    }

}
