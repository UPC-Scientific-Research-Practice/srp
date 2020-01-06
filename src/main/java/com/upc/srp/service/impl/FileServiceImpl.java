package com.upc.srp.service.impl;

import cn.hutool.core.io.resource.InputStreamResource;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import com.upc.srp.dao.CTDao;
import com.upc.srp.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author by zhoutao
 * @description 文件上传
 * @date 2020/1/3 14:16
 */
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    CTDao CTDao;

    @Value("${file.upload_path}")
    private String UPLOAD_PATH;
    @Value("${file.path}")
    private String PATH;
    @Value("${file.scene}")
    private String SCENE;
    @Value("${file.output}")
    private String OUTPUT;


    /**
     * @param multipartFileList
     * @return int
     * @description 上传文件
     * @author zhoutao
     * @date 2020/1/3 14:14
     */
    @Override
    public int upload(List<MultipartFile> multipartFileList, Map<String, Object> map) {
        try{
            // 获取病人名称
            String name = map.get("name").toString();
            // 获取已有的CT图片数量
            int count = CTDao.getCTCount(map);
            // 遍历每一个文件上传
            for(MultipartFile multipartFile:multipartFileList){
                // 获取文件名
                String originalFilename = multipartFile.getOriginalFilename();
                // 获取文件后缀名
                assert originalFilename != null;
                String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
                // 生成上传后的文件名
                String filename = name + "_" +(count++) + suffix;
                // 获取输入上传图片输入流
                InputStreamResource isr = new InputStreamResource(multipartFile.getInputStream(), filename);
                // 文件上传
                Map<String, Object> params = new HashMap<>(5);
                params.put("file", isr);
                params.put("path", PATH);
                params.put("scene", SCENE);
                params.put("output", OUTPUT);
                String result = HttpUtil.post(UPLOAD_PATH, params);
                // 路径保存至数据库
                JSONObject jsonObject = new JSONObject(result);
                String url = jsonObject.getStr("url");
                // 插入数据库中
                params.clear();
                params.put("id", map.get("id"));
                params.put("filename", filename);
                params.put("url", url);
                CTDao.insertCT(params);
            }
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
        return 0;
    }

    /**
     * @description 文件下载，支持多个文件的
     * @param params
     * @return int
     * @author zhoutao
     * @date 2020/1/3 15:19
     */
    @Override
    public int download(Map<String, Object> params, OutputStream outputStream){
        @SuppressWarnings("unchecked")
        List<Map<String,Object>> list = (List<Map<String, Object>>) params.get("list");
        if(list.isEmpty()){
            throw new RuntimeException("链接请求转换成数组失败");
        }
        if(list.size() == 1){
            HttpUtil.download(list.get(0).get("url").toString(), outputStream, true);
        }else{
            int len;
            byte[] buffer = new byte[1024];
            ByteArrayInputStream byteArrayInputStream = null;
            ByteArrayOutputStream byteArrayOutputStream = null;
            // 创建压缩文件
            ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);
            try{
                for(Map<String, Object> map: list){
                    if(map.get("url")!=null){
                        // 获取文件路径
                        String path = map.get("url").toString();
                        // 创建zip文件流
                        zipOutputStream.putNextEntry(new ZipEntry(map.get("filename").toString()));
                        // 创建输出流
                        byteArrayOutputStream = new ByteArrayOutputStream();
                        // 获取下载的文件
                        HttpUtil.download(path, byteArrayOutputStream, true);
                        // 创建输入流
                        byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
                        // 输入流中信息导入压缩输出流中
                        while ((len = byteArrayInputStream.read(buffer)) != -1){
                            zipOutputStream.write(buffer, 0, len);
                        }
                        zipOutputStream.closeEntry();
                    }
                }
                outputStream = zipOutputStream;
                outputStream.close();
            }catch(Exception e){
                throw new RuntimeException("获取压缩文件失败");
            }
        }
        // 返回结果数据
        if(outputStream == null){
            return 1;
        }
        return 0;
    }

}
