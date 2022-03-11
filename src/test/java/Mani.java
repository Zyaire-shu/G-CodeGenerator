import org.junit.jupiter.api.Test;
import top.zyaire.entities.ImageToConvert;


import java.io.*;

/**
 * @Author ZyaireShu
 * @Date 2022/1/16 15:04
 * @Version 1.0
 */
public class Mani {
    @Test
    void test() throws IOException {
        File file = new File("D:\\WorkSpace\\GraduateDesign\\Tests\\Alpha1.0\\out\\artifacts\\Alpha1_0_jar\\lib");
        File out = new File("D:\\WorkSpace\\GraduateDesign\\Tests\\Alpha1.0\\src\\main\\java\\META-INF\\MANIFEST.MF");
        FileWriter fileWriter = new FileWriter(out,true);
        for (File f : file.listFiles()) {
            String opt = " " +"lib/"+ f.getName() + " \n";
            System.out.println(opt);
            fileWriter.write(opt);
        }
        fileWriter.flush();
        fileWriter.close();
    }
    @Test
    void sout(){
        System.out.println("hello");
    }
    
    @Test
    void fileTest(){
//        ImageToConvert a = ImageToConvert.setImageToConvert("./images/b.svg");
//        ImageToConvert f = ImageToConvert.setImageToConvert("./images/b.svg");
//        ImageToConvert b = ImageToConvert.getImageToConvert();
//        ImageToConvert c = ImageToConvert.getImageToConvert();
//        ImageToConvert d = ImageToConvert.getImageToConvert();
//        System.out.println(d.equals(f));
    }
}
