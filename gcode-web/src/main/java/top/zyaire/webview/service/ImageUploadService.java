package top.zyaire.webview.service;

import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author ZyaireShu
 * @Date 2022/1/18 10:04
 * @Version 1.0
 */
public interface ImageUploadService {
    String upload(MultipartFile file);
}
