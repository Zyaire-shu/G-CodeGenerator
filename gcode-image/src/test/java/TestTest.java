import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author ZyaireShu
 * @Date 2022/2/28 14:25
 * @Version 1.0
 */
class TestTest {

    @org.junit.jupiter.api.Test
    void osInfo() {
        System.out.println("操作系统名称：" + System.getProperty("os.name")); // 操作系统名称
        System.out.println("操作系统的架构：" + System.getProperty("os.arch")); // 操作系统的架构
        System.out.println("操作系统版本号：" + System.getProperty("os.version")); // 操作系统版本号
        System.out.println("文件分隔符：" + System.getProperty("file.separator")); // 文件分隔符
        System.out.println("路径分隔符：" + System.getProperty("path.separator")); // 路径分隔符
        System.out.println("直线分隔符：" + System.getProperty("line.separator")); // 直线分隔符
        System.out.println("操作系统用户名：" + System.getProperty("user.name")); // 用户名
        System.out.println("操作系统用户的主目录：" + System.getProperty("user.home")); // 用户的主目录
        System.out.println("当前程序所在目录：" + System.getProperty("user.dir")); // 当前程序所在目录
        System.out.println("测试"+System.getProperty("sun.arch.data.model"));
        System.out.println("测试"+System.getProperty("sun.desktop"));
    }
}