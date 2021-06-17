package com.hanshin.shop.vo.goods;

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

}
