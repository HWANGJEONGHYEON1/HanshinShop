package com.hanshin.shop.service;

import com.hanshin.shop.vo.goods.Goods;
import com.hanshin.shop.vo.goods.GoodsAttachVO;
import com.hanshin.shop.vo.paging.Criteria;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.util.Arrays;
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
        Criteria criteria = new Criteria(1,10);
        final List<Goods> allIncludeAttach = goodsService.findAllList(criteria);
        Assertions.assertThat(allIncludeAttach.size()).isEqualTo(goodsService.findAllList(criteria).size());
        //then
    }

    @Test
    public void 저장() throws Exception {
        //given
        List<GoodsAttachVO> list = Arrays.asList(new GoodsAttachVO("22dacfc8-b9c0-4771-bc37-0bfd86874f24", "2021/05/31", "product-4.jpg"));
//        Goods goods = c
        //when

        //then
    }
}