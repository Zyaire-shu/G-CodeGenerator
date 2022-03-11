package top.zyaire.util;

import lombok.Data;
import top.zyaire.common.util.StaticUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static top.zyaire.util.Command.process;

/**
 * @Author ZyaireShu
 * @Date 2022/2/27 14:50
 * @Version 1.0
 */
@Data
public class PotraceHandeler {

    private String potracePath = StaticUtils.execPath;
    private String bmpPath;
    private String svgPath;
    public PotraceHandeler(){
        String arch = System.getProperty("os.arch");
        if (arch.equals("64") || arch.equals("amd64") || arch.equals("x64") || arch.equals("x_64")){
            this.potracePath += StaticUtils.win64;
        }else {
            this.potracePath += StaticUtils.win32;
        }
    }

    public void tracer(){
        //System.out.println(potracePath);
        //potrace.exe .\树莓派.bmp  -b svg -k 0.1  -r 1000 --tight
        List<String> potrace = new ArrayList<>();
        potrace.add(potracePath);
        potrace.add(StaticUtils.imageStoragePath+StaticUtils.bmpName);
        potrace.add("-b");
        potrace.add("svg");
        potrace.add("-k");
        potrace.add("0.5");
        potrace.add("-r");
        potrace.add("150");
        potrace.add("--tight");
        potrace.add("-o");
        potrace.add(StaticUtils.imageStoragePath+StaticUtils.svgName);
        try {
            process(potrace, line -> {
                System.out.println(line);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void tracer(String blacklevel, String turdsize , String alphamax, String opttolerance, String unit){
        List<String> potrace = new ArrayList<>();
        potrace.add(potracePath);
        potrace.add(bmpPath);
        potrace.add("-b");
        potrace.add("svg");
        potrace.add("-k");
        potrace.add(blacklevel);
        potrace.add("--turdsize");
        potrace.add(turdsize);
        potrace.add("--alphamax");
        potrace.add(alphamax);
        potrace.add("--opttolerance");
        potrace.add(opttolerance);
        potrace.add("--unit");
        potrace.add(unit);
        potrace.add("--tight");
        potrace.add("-o");
        potrace.add(svgPath);
        try {
            process(potrace, System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
