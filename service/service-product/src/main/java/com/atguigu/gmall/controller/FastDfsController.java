package com.atguigu.gmall.controller;

import com.atguigu.gmall.service.CateGoryService;
import com.atguigu.gmall.service.FastDfsService;
import com.atguigu.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * author lisheng
 * Date:2021/5/18
 * Description:
 */
@RestController
@CrossOrigin
@RequestMapping("admin/product")
public class FastDfsController {


    @Autowired
    private FastDfsService fastDfsService;
    @RequestMapping("fileUpload")
    public Result fileUpload(MultipartFile file) throws Exception {
        String imgUrl = fastDfsService.fileUpload(file);
        return Result.ok(imgUrl);
    }
}
