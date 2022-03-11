package top.zyaire.webview;

import org.springframework.boot.test.context.SpringBootTest;
import top.zyaire.common.util.StaticUtils;

/**
 * @Author ZyaireShu
 * @Date 2022/3/10 16:27
 * @Version 1.0
 */

public class Test {
    @org.junit.jupiter.api.Test
    void randstring() {
        System.out.println(StaticUtils.randomString());
    }
}
