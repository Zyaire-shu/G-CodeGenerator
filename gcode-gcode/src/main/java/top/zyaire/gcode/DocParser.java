package top.zyaire.gcode;

import org.apache.batik.parser.ParseException;
import org.apache.batik.parser.PathHandler;
import org.apache.batik.parser.PathParser;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import top.zyaire.utils.ToGCodeHandler;

import java.util.function.Consumer;

/**
 * The DocParser class holds methods for parsing SVG files as org.w3c.dom.Document objects to my custom Gcode objects.
 */
public class DocParser {
    /**
     * Doc to gcode.
     *
     * @param doc      the svg as a Document object
     * @param filename the filename to be given to the gcode file
     * @param options  the user given options for creating the gcode
     * @return the gcode
     */
    public static Gcode docToGcode(Document doc, String filename, Options options) {
        Gcode gcode = new Gcode(options);
        return getGcode(doc, filename, gcode);
    }

    private static Gcode getGcode(Document doc, String filename, Gcode gcode) {
        gcode.addCommand(new Command("Generated from: " + filename));
        iterateSvg(doc, node -> {
            if (node.getNodeName().equals("path")) {
                pathToGcode(node, gcode);
            }
            if (node.getNodeName().equals("rect")) {
                rectToGcode(node, gcode);
            }
        });
        return gcode;
    }

    /**
     * Doc to gcode.
     *
     * @param doc      the svg as a Document object
     * @param filename the filename to be given to the gcode file
     * @return the gcode
     */
    public static Gcode docToGcode(Document doc, String filename) {
        Options a = new Options();
        a.setLaser(false);
        a.setMoveHeight(10);
        a.setWorkDepth(3);
        Gcode gcode = new Gcode(a);
        return getGcode(doc, filename, gcode);
    }

    public static void rectToGcode(Node node, Gcode gcode) {
        NamedNodeMap map = node.getAttributes();
        Float x = 0f, y = 0f, width = 0f, height = 0f;
        x = Float.parseFloat(map.getNamedItem("x").getNodeValue());
        y = Float.parseFloat(map.getNamedItem("y").getNodeValue());
        width = Float.parseFloat(map.getNamedItem("width").getNodeValue());
        height = Float.parseFloat(map.getNamedItem("height").getNodeValue());
        System.out.println("x:" + x + "_y:" + y + "_width:" + width + "_height:" + height);
        final float y1 = y == null ? 0 : y;
        final float x1 = x == null ? 0 : x;
        gcode.addCommand(new Command(Code.G00, x1, y1));
        gcode.addCommand(Command.toCut(gcode.getOptions()));
        gcode.addCommand(new Command(Code.G01, x1 + width, y1));
        gcode.addCommand(new Command(Code.G01, x1 + width, y1 +height));
        gcode.addCommand(new Command(Code.G01, x1, y1 + height));
        gcode.addCommand(new Command(Code.G01, x1, y1));
        gcode.addCommand(Command.toRaise(gcode.getOptions()));
    }

    /**
     * Path to gcode is used by docToGcode() method to parse svg path to gcode commands.
     *
     * @param node  the svg path node from Document
     * @param gcode the gcode object to which the commands are added
     * @throws ParseException the parse exception
     */
    public static void pathToGcode(Node node, Gcode gcode) throws ParseException {

        NamedNodeMap map = node.getAttributes();
        if (map.getNamedItem("id") != null) {
            gcode.addCommand(new Command(map.getNamedItem("id").getNodeValue()));
        }
       // System.out.println(map.getNamedItem("d").getNodeValue());
        String str = map.getNamedItem("d").getNodeValue();

        PathParser pp = new PathParser();//用来抓换Svg里面的path
        PathHandler ph = new ToGCodeHandler(gcode,gcode.getOptions().getCurveAprTolerance(),gcode.getOptions().getCurveSamplingStep());
        pp.setPathHandler(ph);
       // System.out.println("路径是" + str);
        pp.parse(str);

    }

    /**
     * Iterates Document. Used by docToGcode() method to iterate svg.
     *
     * @param document the document to iterate
     * @param doThings the Consumer that holds operations to do to the node
     */
    public static void iterateSvg(Document document, Consumer<Node> doThings) {
        NodeList nodeList = document.getElementsByTagName("*");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                doThings.accept(node);
            }
        }
    }

}
