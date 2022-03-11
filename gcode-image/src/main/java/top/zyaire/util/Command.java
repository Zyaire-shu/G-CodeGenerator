package top.zyaire.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.function.Consumer;

/**
 * @Author ZyaireShu
 * @Date 2022/2/28 15:46
 * @Version 1.0
 */
public class Command {
    public static Process process(List<String> command , Consumer<String> commandInfo) throws IOException {
        //System.out.println("开始执行");
//        command.forEach(s -> {
//            System.out.println(s);
//        });
        ProcessBuilder builder = new ProcessBuilder(command);
        builder.redirectErrorStream(true);
        Process pr = builder.start();
        new Thread(()->{
            try (BufferedReader br = new BufferedReader(new InputStreamReader(pr.getInputStream()))) {
                String line;
                while ((line = br.readLine()) != null) {
                    //System.out.println("执行命令"+line);
                    commandInfo.accept(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();//开启一个新的线程用于接受执行命令的输出
        return pr;
    }
}
