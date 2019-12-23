package com.upc.srp.service.impl;

import com.upc.srp.Bean.FTPBean;
import com.upc.srp.service.FTPService;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @Classname FTPServiceImpl
 * @Description FTP服务类
 * @Author by zhoutao
 * @Date 2019/12/22 18:11
 */
@Service
public class FTPServiceImpl implements FTPService {
    @Autowired
    private FTPBean ftpbean;

    /**
     * @description 构造器注入
     * @param ftpBean
     * @return void
     * @author zhoutao
     * @date 2019/12/22 18:52
     */
//    @Autowired
//    public void setFTPBean(FTPBean ftpBean){
//        this.ftpbean = ftpBean;
//    }

    /**
     * @description 从上传文件到FTP服务器上
     * @param multipartFile
     * @return
     * @author zhoutao
     * @date 2019/12/21 19:12
     */
    @Override
    public Boolean upload(MultipartFile multipartFile) {
        String fileName = multipartFile.getOriginalFilename();
        Boolean result = false;
        try {
            InputStream inputStream = multipartFile.getInputStream();
            result = ftpbean.getFtpClient().storeFile(fileName, inputStream);
        } catch (IOException e) {
            System.err.println("上传失败");
        }
        return result;
    }

    /**
     * @description 从FTP服务器上上传文件
     * @param fileName
     * @return map
     * @author zhoutao
     * @date 2019/12/21 19:12
     */
    @Override
    public Boolean download(String fileName, OutputStream outputStream){
        Boolean result = false;
        try {
            for (FTPFile ftpFile : ftpbean.getFtpClient().listFiles()) {
                if(fileName.equals(ftpFile.getName())){
                    result = ftpbean.getFtpClient().retrieveFile(fileName, outputStream);
                    outputStream.close();
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("下载失败");
        }
        return result;
    }

    @Override
    public Boolean downloadZip(String[] fileList, OutputStream outputStream){
        int len;
        Boolean result = false;
        byte[] buffer = new byte[1024];
        ByteArrayInputStream byteArrayInputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);
            // 遍历需要下载的文件列表
            for(String fileName: fileList){
                zipOutputStream.putNextEntry(new ZipEntry(fileName));
                byteArrayOutputStream = new ByteArrayOutputStream();
                // 获取ftp中文件到流中
                result = ftpbean.getFtpClient().retrieveFile(fileName, byteArrayOutputStream);
                // 判断是否获取成功
                if(!result){
                    System.err.println("收集 fileName 文件失败，请确认该文件是否存在");
                    return false;
                }
                System.out.println("收集 fileName 文件成功");
                // 将输出流变成输入流
                byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
                // 输入流中信息导入压缩输出流中
                while ((len = byteArrayInputStream.read(buffer)) != -1){
                    zipOutputStream.write(buffer, 0, len);
                }
                zipOutputStream.closeEntry();
            }
            outputStream = zipOutputStream;
            outputStream.close();
        } catch (IOException e) {
            System.err.println("下载失败");
        }
        return result;
    }


}
