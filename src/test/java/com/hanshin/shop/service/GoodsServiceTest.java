package com.hanshin.shop.service;

import com.hanshin.shop.entity.Goods;
import com.hanshin.shop.entity.GoodsDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class GoodsServiceTest {

    private static Logger log = LoggerFactory.getLogger(GoodsServiceTest.class);

    @Autowired
    private GoodsService goodsService;


    @Test
    public void 화면에_뿌려줄_상품() throws Exception {
        //given
        final List<Goods> allIncludeAttach = goodsService.findAllList();
        Assertions.assertThat(allIncludeAttach.size()).isEqualTo(goodsService.findAllList().size());
        //then
    }
    @Test
    public void 세이브() throws Exception {
        //given
        GoodsDto dto = new GoodsDto("test", 40000, "Test 설명");
        Goods goods = Goods.create(dto);
        //when
        goodsService.save(goods);
        System.out.println("#"+ goods.getId());
        //then
        Assertions.assertThat(goodsService.findOne(goods.getId()).getName()).isEqualTo("test");
    }

}