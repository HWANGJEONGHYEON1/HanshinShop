package com.hanshin.shop.service;


import com.hanshin.shop.IntegrationTests;
import com.hanshin.shop.vo.goods.Goods;
import com.hanshin.shop.vo.paging.Criteria;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

class GoodsServiceTest extends IntegrationTests {

    private static Logger log = LoggerFactory.getLogger(GoodsServiceTest.class);

    @Autowired
    private GoodsService goodsService;

    @Test
    @DisplayName("전체 상품 리스트 수는 2이다.")
    public void all_list_size_2() {
        Criteria criteria = new Criteria(1, 2);
        List<Goods> allList = goodsService.findAllList(criteria);
        allList.stream()
                .forEach(System.out::println);
        Assertions.assertThat(2).isEqualTo(allList.size());
    }
}