package com.atguigu.gmall.service.impl;

import com.atguigu.gmall.service.FastDfsService;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;

/**
 * author lisheng
 * Date:2021/5/18
 * Description:
 */
@Service
public class FastDfsServiceImpl implements FastDfsService {
    @Value("${tracker.ip}")
    private String imgUrl;
    @Override
    public String fileUpload(MultipartFile file) throws Exception{
        String resource = FastDfsServiceImpl.class.getClassLoader().getResource("track.conf").getPath();
        //初始化全局配置
        ClientGlobal.init(resource);
        //获取一个track连接
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer connection = trackerClient.getConnection();
        //获取一个storage
        StorageClient storageClient = new StorageClient(connection,null);
        //上传文件
        String filenameExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        String[] strings = storageClient.upload_file(file.getBytes(), filenameExtension, null);
        String urlName = "";
        for (String url : strings) {
            imgUrl=imgUrl+"/"+url;
        }
        return imgUrl;
    }
}
