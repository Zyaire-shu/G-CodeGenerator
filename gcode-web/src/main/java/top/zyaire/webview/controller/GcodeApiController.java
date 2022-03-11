package top.zyaire.webview.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.zyaire.common.util.StaticUtils;

import top.zyaire.entities.ImageToConvert;
import top.zyaire.webview.service.ImageService;
import top.zyaire.webview.service.SVGToGcodeService;
import top.zyaire.webview.util.SessionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @Author ZyaireShu
 * @Date 2022/2/11 15:04
 * @Version 1.0
 */
@CrossOrigin("*")
@RestController
public class GcodeApiController {
    private ImageService imageService;
    private SVGToGcodeService svgToGcodeService;
    @Autowired
    public void setImageUploadService(ImageService imageUploadService, SVGToGcodeService svgToGcodeService){
        this.imageService = imageUploadService;
        this.svgToGcodeService = svgToGcodeService;
    }
    @RequestMapping("/uploadSvg")
    public String upload(@RequestParam("file") MultipartFile file, HttpServletRequest request){//暂时不管
//        String format = file.getOriginalFilename();//获取文件格式
//        format = format.substring(format.lastIndexOf('.')+1);
//        int re = StaticUtils.containKey(StaticUtils.svgFormat,format);
//        JSONObject jb = new JSONObject();
//        if (re==-1){
//            jb.put("message","不支持的文件类型");
//            return jb.toJSONString();
//        }
//        boolean isUploaded = imageService.upload(file);
//        jb.put("message",isUploaded?"上传成功":"上传失败");
//        return jb.toJSONString();
        //return getString(file, re);
        return null;
    }

    @RequestMapping("/uploadImage")
    public String uploadImage(@RequestParam("files[]") MultipartFile file,  HttpSession httpSession){
        String format = file.getOriginalFilename();//获取文件格式
        format = format.substring(format.lastIndexOf('.')+1);
        int re = StaticUtils.containKey(StaticUtils.imgFormat,format);
        JSONObject jb = new JSONObject();
        if (re==-1){
            jb.put("message","不支持的文件类型");
            return jb.toJSONString();
        }
        System.out.println("session是"+ (String) httpSession.getAttribute("tmpPath"));
        boolean isUploaded = imageService.upload(file, (String) httpSession.getAttribute("tmpPath"));
       // imageService.toSvg();
        jb.put("message",isUploaded?"上传成功":"上传失败");
        return jb.toJSONString();
        //return getString(file, re);
    }

    @RequestMapping(value = "/svgConvert", method = RequestMethod.POST)
    public String convert(@RequestBody JSONObject jsonObject){
        int smooth = jsonObject.getIntValue("smooth");
        //System.out.println("平滑度："+smooth);
        //System.out.println(ImageToConvert.getImageToConvert().getImage().getName());
        //String gcode =svgToGcodeService.convert(smooth);
        JSONObject jb = new JSONObject();
        jb.put("gcode","gcode");
        return jb.toJSONString();
    }
    @RequestMapping(value = "/imageConvert", method = RequestMethod.POST)
    public String bmpToSvg(@RequestBody JSONObject jsonObject, HttpSession httpSession){
        JSONObject potrace = jsonObject.getJSONObject("potrace");
        String blacklevel = potrace.getString("blacklevel");
        String turdsize = potrace.getString("turdsize");
        String alphamax = potrace.getString("alphamax");
        ImageToConvert f = SessionUtils.getImagetToConvertByTmpPath((String) httpSession.getAttribute("tmpPath"));
        imageService.setImageToConvert(f);
        imageService.toSvg(blacklevel, turdsize, alphamax, "0.2", "10");
        svgToGcodeService.setImageToConvert(f);
        JSONObject generate = jsonObject.getJSONObject("generate");
        int smooth = generate.getIntValue("smooth");
        boolean isLaser = generate.getBoolean("isLaser");
        String gcode = null;
        if(isLaser){
            Integer laserStrength = generate.getInteger("laserStrength");
            gcode =svgToGcodeService.convert(smooth, true,laserStrength);
        }else {
            float moveHeight = generate.getFloat("moveHeight");
            float workDepth = generate.getFloat("workDepth");
            gcode = svgToGcodeService.convert(smooth,false,moveHeight,workDepth);
        }
        //imageService.toSvg();
        JSONObject jb = new JSONObject();
        jb.put("gcode",gcode);
        return jb.toJSONString();
    }

}
