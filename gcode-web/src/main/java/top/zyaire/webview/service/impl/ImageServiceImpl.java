package top.zyaire.webview.service.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.zyaire.common.util.StaticUtils;
import top.zyaire.entities.ImageToConvert;
import top.zyaire.webview.service.ImageService;
import top.zyaire.webview.util.FileUtil;
import top.zyaire.webview.util.SessionUtils;

import java.io.File;

import static top.zyaire.common.util.StaticUtils.imageStoragePath;
import static top.zyaire.common.util.StaticUtils.svgName;

/**
 * @Author ZyaireShu
 * @Date 2022/1/18 10:05
 * @Version 1.0
 */
@Service("imageUpload")
public class ImageServiceImpl implements ImageService {
    private ImageToConvert imageToConvert;
    ImageServiceImpl() {
    }

    @Override
    public boolean upload(MultipartFile file,String tmpPath) {

        String name = file.getOriginalFilename();
        try {
            //StaticUtils.deleteFolder(new File(imageStoragePath));
            new File(imageStoragePath+tmpPath).mkdirs();
            FileUtil.upload(file.getBytes(), imageStoragePath+tmpPath, name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        File toConvert = new File(imageStoragePath+tmpPath + name);
        if (toConvert.exists()) {
            System.out.println("文件存在");
            ImageToConvert imageToConvert = new ImageToConvert(toConvert,tmpPath,name.substring(0,name.lastIndexOf('.')));
            SessionUtils.insertImageToConvert(tmpPath, imageToConvert);
            return true;
        }
        return false;
    }

    @Override
    public void toSvg() {
//        ImageToConvert.getImageToConvert().toBmp();
//        ImageToConvert.getImageToConvert().bmpToSvg();
//        File f = new File(imageStoragePath+svgName);
//        if (f.exists()){
//            ImageToConvert.setImageToConvert(f);
//        }
    }

    @Override
    public ImageToConvert toSvg(String blacklevel, String turdsize, String alphamax, String opttolerance, String unit) {
        imageToConvert.toBmp();
        imageToConvert.bmpToSvg(blacklevel, turdsize, alphamax, opttolerance, unit);
        return this.imageToConvert;

    }

    public ImageToConvert getImageToConvert() {
        return imageToConvert;
    }

    public void setImageToConvert(ImageToConvert imageToConvert) {
        this.imageToConvert = imageToConvert;
    }
}
