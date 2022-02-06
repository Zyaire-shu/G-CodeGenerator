package top.zyaire.webview.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.zyaire.webview.service.ImageUploadService;
import top.zyaire.webview.util.FileUtil;

import static top.zyaire.serial.util.StaticUtils.imageStoragePath;

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
    public String upload(MultipartFile file) {
        String name = file.getOriginalFilename();
        try {
            FileUtil.upload(file.getBytes(),imageStoragePath,name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
