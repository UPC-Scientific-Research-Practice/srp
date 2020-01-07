package com.upc.srp.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * @author by zhoutao
 * @description 文件上传
 * @date 2020/1/3 14:13
 */
public interface FileService {

    /**
     * @description 上传文件
     * @param multipartFileList
     * @return int
     * @author zhoutao
     * @date 2020/1/3 14:14
     */
    public int upload(List<MultipartFile> multipartFileList, Map<String, Object> params);

    /**
     * @description 文件下载
     * @param params
     * @return int
     * @author zhoutao
     * @date 2020/1/3 15:19
     */
    public int download(Map<String, Object> params, OutputStream outputStream);

}
