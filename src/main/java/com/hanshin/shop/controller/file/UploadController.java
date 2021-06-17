package com.hanshin.shop.controller.file;

import com.hanshin.shop.vo.goods.AttachFileDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
public class UploadController {

    private static final String uploadFolder = "/Users/hwangjeonghyeon/IdeaProjects/HanshinShop/src/main/resources/static/img/";

    @PostMapping("/deleteFile")
    public ResponseEntity<Void> deleteFile(String fileName) throws UnsupportedEncodingException {
        log.info("fileName {}", fileName);

        File file;
        file = new File(uploadFolder + URLDecoder.decode(fileName, "UTF-8"));
        file.delete();

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/display")
    public ResponseEntity<byte[]> getFile(String fileName) {
        log.info("fileName {}", fileName);
        File file = new File(uploadFolder + fileName);
        log.info("file {}", file);
        ResponseEntity<byte[]> result = null;

        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Content-type", Files.probeContentType(file.toPath()));
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file),httpHeaders,HttpStatus.OK);
        } catch (Exception e){
            log.error("# {}", e);
        }
        return result;
    }

    @PostMapping(value = "/uploadAjaxAction")
    public ResponseEntity<List<AttachFileDto>> uploadAjaxPost(MultipartFile[] uploadFile) throws Exception{
        List<AttachFileDto> list = new ArrayList<>();
        String uploadFolderPath = getFolder();
        File uploadPath = new File(uploadFolder, uploadFolderPath);

        if(!uploadPath.exists()) {
            uploadPath.mkdirs();
        }

        for (MultipartFile multipartFile : uploadFile) {
            String uploadFileName = multipartFile.getOriginalFilename();
            UUID uuid = UUID.randomUUID();

            uploadFileName = uuid + "_" + uploadFileName;

            final AttachFileDto bindingDto = AttachFileDto.makeBinding(multipartFile.getOriginalFilename(), getFolder(), uuid.toString());
            File saveFile = new File(uploadPath, uploadFileName);
            multipartFile.transferTo(saveFile);

            list.add(bindingDto);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
   }

   private String getFolder() {
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
       final Instant instant = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant();
       Date date = Date.from(instant);
       String str = sdf.format(date);
       log.info("=== getFolder");
       log.info(str);
       return str.replace("-", File.separator);
   }
}
