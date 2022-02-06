import org.junit.jupiter.api.Test;
import top.zyaire.gcode.Code;
import top.zyaire.gcode.SingleGcode;

/**
 * @Author ZyaireShu
 * @Date 2022/2/4 11:39
 * @Version 1.0
 */
public class GcodeTest {
    @Test
    void start(){
        SingleGcode s = new SingleGcode(Code.G00,0,0);

        System.out.println(s);
    }
}
