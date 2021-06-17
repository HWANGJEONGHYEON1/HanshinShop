package com.hanshin.shop.repository;

import com.hanshin.shop.vo.goods.Goods;
import com.hanshin.shop.vo.paging.Criteria;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
class GoodsMapperTest {

    @Autowired
    private GoodsMapper mapper;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }


    @Test
    public void 카태고리별_리스트() throws Exception {
        //given
        final List<Goods> listOfCategory = mapper.findListOfCategory(2L);

        //then
        Assertions.assertThat(listOfCategory.size()).isEqualTo(30);
    }


    @Test
    public void findOne() throws Exception {
        //given
        final Goods goods = mapper.findOne(27L);
        //when

        //then
        Assertions.assertThat(goods.getName()).isEqualTo("피망");
    }


    @Test
    public void testPaging() throws Exception {
        //given
        Criteria criteria = new Criteria(3, 10);

        List<Goods> list = mapper.getListWithPaging(criteria);

        Assertions.assertThat(list.size()).isEqualTo(10);

        //when

        //then
    }

}