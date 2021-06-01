package com.hanshin.shop.repository;

import com.hanshin.shop.entity.Goods;
import com.hanshin.shop.entity.GoodsDto;
import org.apache.ibatis.session.SqlSession;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
        Assertions.assertThat(listOfCategory.size()).isEqualTo(5);
    }


    @Test
    public void findOne() throws Exception {
        //given
        final Goods goods = mapper.findOne(1L);
        //when

        //then
        Assertions.assertThat(goods.getName()).isEqualTo("test1");
    }

    @Test
    public void 상품저장() throws Exception {
        //given
        GoodsDto dto = new GoodsDto("test", 40000, "Test 설명");
        Goods goods = Goods.create(dto);
        //when
        mapper.save(goods);
        //then
        Assertions.assertThat(mapper.findOne(goods.getId()).getName()).isEqualTo("test");
    }
}