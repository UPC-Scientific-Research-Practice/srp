package com.upc.srp.controller;

import com.upc.srp.dto.Response;
import com.upc.srp.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * @Classname UploadController
 * @Description 文件上传控制器
 * @Date 2019/12/21 16:10
 * @Author zhoutao
 */
@Controller
public class UploadController {

    @Autowired
    FileService fileService;

    @PostMapping("/upload")
    public Response upload(@RequestParam("file") List<MultipartFile> multipartFileList, @RequestParam Map<String, Object> params){
        // 文件上传检验
        if(params.get("name")==null || params.get("id")== null){
            return Response.getBuilder().setCode(400).setMessage("Error").setData("参数错误，上传失败").build();
        }
        if (multipartFileList.isEmpty()) {
            return Response.getBuilder().setCode(400).setMessage("Error").setData("上传文件为空，上传失败").build();
        }
        // 返回结果
        int result = fileService.upload(multipartFileList, params);
        if(result == 0){
            return Response.getBuilder().setCode(200).setMessage("Success").setData("上传成功").build();
        }else{
            return Response.getBuilder().setCode(400).setMessage("Error").setData("上传失败").build();
        }
    }

    @GetMapping("/download")
    public Response download(@RequestBody Map<String, Object> params, HttpServletResponse httpServletResponse){
        try{
            // 校验参数
            if(params.get("list")==null || params.get("filename")==null){
                return Response.getBuilder().setCode(400).setMessage("Error").setData("参数错误").build();
            }
            // 设置请求头
            httpServletResponse.setContentType("application/octet-stream;charset=utf-8");
            // 设置文件下载的名称
            httpServletResponse.setHeader("Content-Disposition", "attachment;filename="+
                    new String(params.get("filename").toString().getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
            // 下载
            fileService.download(params, httpServletResponse.getOutputStream());
            return Response.getBuilder().setCode(200).setMessage("Success").setData("下载成功").build();
        }catch(IOException e){
            System.err.println("文件下载失败");
            return Response.getBuilder().setCode(400).setMessage("Error").setData("文件下载失败").build();
        }
    }

}
