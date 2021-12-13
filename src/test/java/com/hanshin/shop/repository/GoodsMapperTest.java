package com.hanshin.shop.repository;

import com.hanshin.shop.IntegrationTests;
import com.hanshin.shop.vo.goods.Goods;
import com.hanshin.shop.vo.goods.GoodsAttachVO;
import com.hanshin.shop.vo.goods.GoodsDto;
import org.assertj.core.api.Assertions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

class GoodsMapperTest extends IntegrationTests {

    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private GoodsAttachMapper attachMapper;

    @Test
    @DisplayName("category id가 1인 상품의 리스트를 반환")
    public void category_list_by_id_1() throws Exception {
        //given
        final List<Goods> listOfCategory = goodsMapper.findListOfCategory(1L);
        //then
        Assertions.assertThat(1).isEqualTo(listOfCategory.size());
    }

    @Test
    @DisplayName("GoodsId가 1인 상품의 이름을 조회한다.")
    public void goods_id_1_파프리카() throws Exception {
        //given
        final Goods goods = goodsMapper.findOne(1L);
        //when

        //then
        Assertions.assertThat("파프리카").isEqualTo(goods.getName());
    }

    @Test
    @DisplayName("상품저장")
    void goods_save() {
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

    @Test
    @DisplayName("상품 이미지 조회")
    void goods_image() {
        List<GoodsAttachVO> goodsImage = attachMapper.findByGoodsId(1L);

        Assertions.assertThat("/path/").isEqualTo(goodsImage.get(0).getUploadPath());
    }
}