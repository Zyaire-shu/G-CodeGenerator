import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.junit.jupiter.api.Test;
import top.zyaire.utils.BezierToArcs;
import top.zyaire.utils.BiArc;
import top.zyaire.utils.CubicBezier;

import java.util.List;

/**
 * @Author ZyaireShu
 * @Date 2022/3/5 14:09
 * @Version 1.0
 */

public class BezlTest {
    @Test
    void b(){
       // CubicBezier{p1={2,810; 6,938}, p2={2,927; 6,858}, c1={2,840; 6,921}, c2={2,893; 6,885}}
        //CubicBezier{p1={2,927; 6,858}, p2={3,007; 6,845}, c1={2,990; 6,809}, c2={2,990; 6,809}}

        //CubicBezier cb = new CubicBezier(new Vector2D(2810,6938),new Vector2D(2927,6858),new Vector2D(2840,6921),new Vector2D(2893,6885));
        CubicBezier cb = new CubicBezier(new Vector2D(2927,6858),new Vector2D(2990,6809),new Vector2D(2990,6809),new Vector2D(3007,6845));
        //CubicBezier cb = CubicBezier.fromQuadratic(new Vector2D(2927,6858),new Vector2D(2990,6810),new Vector2D(3007,6845));
        List<BiArc> biArcs = BezierToArcs.ApproxCubicBezier(cb, 0.5f, 20);
        System.out.println(cb);
        biArcs.forEach(biArc -> {
            System.out.println(biArc.getArc1());
            System.out.println(biArc.getArc2());
        });
    }
}
