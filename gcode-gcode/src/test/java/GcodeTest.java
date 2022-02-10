import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import top.zyaire.gcode.DocParser;
import top.zyaire.gcode.Gcode;
import top.zyaire.gcode.Options;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * @Author ZyaireShu
 * @Date 2022/2/4 11:39
 * @Version 1.0
 */
public class GcodeTest {
    @Test
    void start(){
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactory.setValidating(false);
            documentBuilderFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            String path = "C:\\Users\\ZyaireShu\\Pictures\\Saved Pictures\\纹身\\rect.svg";
            File x = new File(path);
            System.out.println(x.getName());
            Document doc = documentBuilder.parse(new File(path));
            System.out.println(doc.toString());
            System.out.println(doc.getDoctype().toString());
            System.out.println(doc.getNodeName());
            //FileConverter.printSvg(doc);
            Options options = new Options();
            options.setFeed(4000);
            options.setWorkDepth(0);
            options.setMoveDepth(2);
            options.setLaserPower(1000);
            options.setLaser(false);
            Gcode g = DocParser.docToGcode(doc,"test",options);
            System.out.println(new String(g.commandsAsByteArray()));
            g.saveToFile("C:\\Users\\ZyaireShu\\Pictures\\Saved Pictures\\纹身\\rect.txt");
        }catch (Exception e){
            e.printStackTrace();
        }
        
        
    }
    @Test
    void bez() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setValidating(false);
        documentBuilderFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        String path = "C:\\Users\\ZyaireShu\\Pictures\\Saved Pictures\\纹身\\simple.svg";
        File x = new File(path);
        System.out.println(x.getName());
        Document doc = documentBuilder.parse(new File(path));
        System.out.println(doc.toString());
        System.out.println(doc.getDoctype().toString());
        System.out.println(doc.getNodeName());
        //FileConverter.printSvg(doc);
        Gcode g = DocParser.docToGcode(doc,"test");
        System.out.println(g);
        g.saveToFile("C:\\Users\\ZyaireShu\\Pictures\\Saved Pictures\\纹身\\simple.txt");
    }
}
