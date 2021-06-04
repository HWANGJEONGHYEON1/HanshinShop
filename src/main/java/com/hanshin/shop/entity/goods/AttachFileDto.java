package com.hanshin.shop.entity.goods;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class AttachFileDto {
    private String fileName;
    private String uploadPath;
    private String uuid;

    public String getFileName() {
        return fileName;
    }

    public String getUploadPath() {
        return uploadPath;
    }

    public String getUuid() {
        return uuid;
    }

    public static AttachFileDto makeBinding(String uploadFileName, String uploadFolderPath, String uuid) {
        return new AttachFileDto(uploadFileName, uploadFolderPath, uuid);
    }
}
