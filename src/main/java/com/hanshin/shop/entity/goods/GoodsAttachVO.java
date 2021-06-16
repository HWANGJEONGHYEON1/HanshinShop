package com.hanshin.shop.entity.goods;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GoodsAttachVO {

    private String uuid;
    private String uploadPath;
    private String fileName;

    private Long goodsId;

    public GoodsAttachVO(String uuid, String uploadPath, String fileName) {
        this.uuid = uuid;
        this.uploadPath = uploadPath;
        this.fileName = fileName;
    }
}
