package com.upc.srp.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;

/**
 * @Classname FTPService
 * @Description TODO
 * @Author by zhoutao
 * @Date 2019/12/22 18:09
 */
public interface FTPService {

    /**
     * @description 上传文件到FTP服务器上
     * @param multipartFile
     * @return
     * @author zhoutao
     * @date 2019/12/21 19:12
     */
    public Boolean upload(MultipartFile multipartFile);

    /**
     * @description 从FTP服务器上下载文件
     * @param fileName, outputStream
     * @return void
     * @author zhoutao
     * @date 2019/12/21 19:12
     */
    public Boolean download(String fileName, OutputStream outputStream);


    /**
     * @description 从FTP服务器上下载多个文件，压缩下载
     * @param fileList, outputStream
     * @return void
     * @author zhoutao
     * @date 2019/12/21 19:12
     */
    public Boolean downloadZip(String[] fileList, OutputStream outputStream);

}
