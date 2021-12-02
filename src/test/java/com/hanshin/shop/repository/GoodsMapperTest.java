package com.hanshin.shop.repository;

import com.hanshin.shop.vo.goods.Goods;
import com.hanshin.shop.vo.goods.GoodsAttachVO;
import com.hanshin.shop.vo.goods.GoodsDto;
import org.assertj.core.api.Assertions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@SpringBootTest
@Transactional
class GoodsMapperTest {

    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private GoodsAttachMapper attachMapper;

    @BeforeEach
    void setUp() {
        GoodsAttachVO attachVO = new GoodsAttachVO(UUID.randomUUID().toString(), "/path/", "파프리카");
        attachVO.setGoodsId(1L);
        GoodsAttachVO attachVO1 = new GoodsAttachVO(UUID.randomUUID().toString(), "/path/", "샤인머스켓");
        attachVO1.setGoodsId(2L);
        attachMapper.insert(attachVO);
        attachMapper.insert(attachVO1);
    }

    @Test
    @Transactional(readOnly = true)
    @DisplayName("category id가 1인 상품의 리스트를 반환")
    public void 카태고리별_리스트() throws Exception {
        //given
        final List<Goods> listOfCategory = goodsMapper.findListOfCategory(1L);

        //then
        Assertions.assertThat(1).isEqualTo(listOfCategory.size());
    }


    @Test
    @Transactional(readOnly = true)
    @DisplayName("GoodsId가 1인 상품의 이름을 조회한다.")
    public void goods_id_상품_파프리카() throws Exception {
        //given
        final Goods goods = goodsMapper.findOne(1L);
        //when

        //then
        Assertions.assertThat("파프리카").isEqualTo(goods.getName());
    }

    @Test
    @DisplayName("상품이 저장이 잘 되는지 테스트")
    void 상품_저장() {
        //given
        GoodsDto goodsDto = new GoodsDto("새송이버섯", 3000, "고기랑 찰떡인 송이버섯", 1L);
        Goods goods = Goods.create(goodsDto);
        goodsMapper.save(goods);

        GoodsAttachVO attachV3 = new GoodsAttachVO(UUID.randomUUID().toString(), "/path/", "새송이버섯");
        attachV3.setGoodsId(3L);
        attachMapper.insert(attachV3);
        //when
        Goods saveGoods = goodsMapper.findOne(3L);
        //then
        Assertions.assertThat("새송이버섯").isEqualTo(saveGoods.getName());
    }
}