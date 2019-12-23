package com.upc.srp.controller;

import com.upc.srp.service.FTPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Classname UploadController
 * @Description 文件上传控制器
 * @Date 2019/12/21 16:10
 * @Author zhoutao
 */
@Controller
public class UploadController {

    @Autowired
    FTPService ftpService;


    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile multipartFile){
        if (multipartFile.isEmpty()) {
            return "上传失败，请选择文件";
        }
        Boolean result = ftpService.upload(multipartFile);
        System.out.printf(result.toString());
        return "上传失败！";
    }

    @GetMapping("/download")
    public String download(@RequestParam("fileName") String fileName, HttpServletResponse httpServletResponse){
        try{
            httpServletResponse.setContentType("application/octet-stream");
            httpServletResponse.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            ftpService.download(fileName, httpServletResponse.getOutputStream());
        }catch(IOException e){
            System.err.println("文件下载失败");
        }
        return "success";
    }

    @GetMapping("/downloadZip")
    public String downloadZip(@RequestParam("fileList") String[] fileList, HttpServletResponse httpServletResponse){
        try{
            httpServletResponse.setContentType("application/octet-stream");
            String fileName = fileList[0].split("\\.")[0];
            httpServletResponse.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".zip");
            ftpService.downloadZip(fileList, httpServletResponse.getOutputStream());
        }catch(IOException e){
            System.err.println("文件下载失败");
        }
        return "success";
    }

}
