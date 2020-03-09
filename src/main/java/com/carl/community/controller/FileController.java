package com.carl.community.controller;

import com.carl.community.dto.FileDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhaoq
 * @date 2020/3/9 16:14
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @PostMapping("/imageUpload")
    public FileDTO uploadImg() {
        FileDTO fileDTO = new FileDTO();
        fileDTO.setSuccess(1);
        fileDTO.setUrl("/images/img.png");
        return fileDTO;
    }

}
