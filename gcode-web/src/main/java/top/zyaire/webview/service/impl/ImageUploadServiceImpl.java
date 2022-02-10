package top.zyaire.webview.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.zyaire.webview.service.ImageUploadService;
import top.zyaire.webview.util.FileUtil;

import java.io.File;

import static top.zyaire.common.util.StaticUtils.imageStoragePath;

/**
 * @Author ZyaireShu
 * @Date 2022/1/18 10:05
 * @Version 1.0
 */
@Service("imageUpload")
public class ImageUploadServiceImpl implements ImageUploadService {

    ImageUploadServiceImpl(){
    }
    @Override
    public boolean upload(MultipartFile file) {
        String name = file.getOriginalFilename();
        try {
            FileUtil.upload(file.getBytes(),imageStoragePath,name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new File(imageStoragePath+name).exists();
    }
}
