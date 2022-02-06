package top.zyaire.serial.util;

import java.io.File;

/**
 * @Author ZyaireShu
 * @Date 2022/1/18 10:38
 * @Version 1.0
 */
public class StaticUtils {
    public static final String[] supportFormat = new String[]{"svg"};
    public static final String imageStoragePath = "./image/";
    public static int containKey(String []keys,String key){
        for (int i = 0; i < keys.length; i++) {
            if (key.equals(keys[i])){
                return i;
            }
        }
        return -1;
    }

    //需要注意的是当删除某一目录时，必须保证该目录下没有其他文件才能正确删除，否则将删除失败。
    public static void deleteFolder(File folder) throws Exception {
        if (!folder.exists()) {
            throw new Exception("文件不存在");
        }
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    //递归直到目录下没有文件
                    deleteFolder(file);
                } else {
                    //删除
                    file.delete();
                }
            }
        }
        //删除
        //folder.delete();

    }

}
