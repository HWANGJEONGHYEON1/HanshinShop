package com.hanshin.shop.controller.file;

import com.hanshin.shop.entity.GoodsAttachVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
public class AttachmentController {

    static final String uploadFolder = "/Users/hwangjeonghyeon/IdeaProjects/shopUpload/";


    @PostMapping(value = "/uploadAjaxAction")
    @ResponseBody
    public void uploadAjaxPost(MultipartFile[] uploadFile){
        for (MultipartFile multipartFile : uploadFile) {
            log.info("================");
            log.info("uploadfileName " + multipartFile.getOriginalFilename());
            log.info("uploadfilesize " + multipartFile.getSize());

            File saveFile = new File(uploadFolder, multipartFile.getOriginalFilename());
            try {
                multipartFile.transferTo(saveFile);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }

   }
}
