package com.upc.srp.Bean;

import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;

/**
 * @Classname FTPBean
 * @Description TODO
 * @Author by zhoutao
 * @Date 2019/12/21 20:32
 */
@Component
public class FTPBean {
    @Value("${ftp.ip}")
    private String ip;
    @Value("${ftp.port}")
    private int port;
    @Value("${ftp.username}")
    private String username;
    @Value("${ftp.password}")
    private String password;

    @Value("${ftp.remotePath}")
    private String remotePath;
    private FTPClient ftpClient;

    public FTPBean(){

    }

    private void createFTPClient(){
        try {
            ftpClient = new FTPClient();
            ftpClient.connect(ip, port);
            ftpClient.login(username, password);
            // 中文支持
            ftpClient.setControlEncoding("UTF-8");
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            // 被动模式
            ftpClient.enterLocalPassiveMode();
            // 主动模式
//            ftpClient.enterLocalActiveMode();
            ftpClient.changeWorkingDirectory(remotePath);
        }catch(IOException e){
            System.err.println("创建FTP客户端连接失败");
        }
    }

    @PostConstruct
    public void start() {
        createFTPClient();
    }

    @PreDestroy
    public void destroy() {
        if(ftpClient.isConnected()){
            try{
                ftpClient.disconnect();
            }catch(IOException e){
                System.err.println(e.toString());
            }

        }
    }

    public FTPClient getFtpClient(){
        if(ftpClient == null){
            createFTPClient();
        }
        return ftpClient;
    }
}
