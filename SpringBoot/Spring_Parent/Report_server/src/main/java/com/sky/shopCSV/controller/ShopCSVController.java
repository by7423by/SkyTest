package com.sky.shopCSV.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.sky.shopCSV.service.ShopCSVService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/shop")
public class ShopCSVController {

    @Autowired
    private ShopCSVService shopCSVService;

    /**
     * 上传CSV文件并转化为SQL语句
     * @param multipartFile 上传的CSV文件
     */
    @PostMapping("/csv")
    public void shopCSV(MultipartFile multipartFile, HttpSession session ){
        session.setAttribute("errorSource","异常方法名称："+new Exception().getStackTrace()[0].getMethodName()
        +"异常控制器名称："+new Exception().getStackTrace()[0].getClassName());
        shopCSVService.ShopCSV(multipartFile);
    }


}
