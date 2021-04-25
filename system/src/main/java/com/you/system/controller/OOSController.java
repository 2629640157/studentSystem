package com.you.system.controller;

import com.you.ssm.util.CrowdUtil;
import com.you.ssm.util.ResultEntity;
import com.you.system.config.OSSProperties;
import com.you.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author 游斌
 * @create 2021-03-02  17:19
 */
@Controller
public class OOSController {
    @Autowired
    private OSSProperties ossProperties;
    @Autowired
    private UserService userService;

    /**
     * @param returnPicture 接收用户上传的文件
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping("/create/upload/return/picture.json")
    public ResultEntity<String> uploadReturnPicture(@RequestParam("returnPicture") MultipartFile returnPicture) throws IOException {
        // 1.执行文件上传
        ResultEntity<String> uploadReturnPicResultEntity = CrowdUtil.uploadFileToOss(ossProperties.getEndPoint(),
                ossProperties.getAccessKeyId(), ossProperties.getAccessKeySecret(), returnPicture.getInputStream(), ossProperties.getBucketName(), ossProperties.getBucketDomain(), returnPicture.getOriginalFilename());
        // 2.返回上传的结果
        return uploadReturnPicResultEntity;
    }
}
