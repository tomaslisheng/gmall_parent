package com.atguigu.gmall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * author lisheng
 * Date:2021/5/18
 * Description:
 */
public interface FastDfsService {
    String fileUpload(MultipartFile file) throws Exception;
}
