package com.hanshin.shop.entity;

import lombok.Getter;

@Getter
public class GoodsAttachVO {

    private String uuid;
    private String uploadPath;
    private String fileName;
    private boolean fileType;

    private Long goodsId;

}
