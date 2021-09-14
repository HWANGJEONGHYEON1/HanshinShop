package com.hanshin.shop.vo.goods;

import lombok.*;


@Getter
@Setter
@ToString
@NoArgsConstructor
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
