package com.hanshin.shop.service;

import com.hanshin.shop.vo.goods.Goods;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
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

}