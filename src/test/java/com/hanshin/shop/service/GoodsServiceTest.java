package com.hanshin.shop.service;

import com.hanshin.shop.IntegrationTests;
import com.hanshin.shop.config.exception.AttachmentNotExistException;
import com.hanshin.shop.vo.goods.Goods;
import com.hanshin.shop.vo.goods.GoodsAttachVO;
import com.hanshin.shop.vo.goods.GoodsDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@Slf4j
class GoodsServiceTest extends IntegrationTests {

    @Autowired
    private GoodsService goodsService;

    @Test
    @DisplayName("원하는 상품 잘 가져오는지 확인")
    void get_goods() {
        Goods getGoods = goodsService.findOne(1L);
        System.out.println(getGoods);
        assertThat("파프리카").isEqualTo(getGoods.getName());
    }

    @Test
    @Transactional
    @DisplayName("상품 등록이 잘되는지 확인")
    void create_goods() {
        GoodsDto goodsDto = new GoodsDto("새송이버섯", 3000, "고기랑 찰떡인 송이버섯", 1L);
        Goods goods = Goods.create(goodsDto);
        GoodsAttachVO attachVO = new GoodsAttachVO(UUID.randomUUID().toString(), "/path/", "새송이버섯");
        attachVO.setGoodsId(goods.getId());

        goods.addAttach(attachVO);
        goodsService.register(goods);

        assertThat("새송이버섯").isEqualTo(goodsService.findOne(goods.getId()).getName());
    }

    @Test
    @Transactional
    @DisplayName("상품 등록할 때 첨부파일이 없다면 에러발생")
    void create_goods_not_exist_attachment() {
        GoodsDto goodsDto = new GoodsDto("새송이버섯", 3000, "고기랑 찰떡인 송이버섯", 1L);
        Goods goods = Goods.create(goodsDto);
        assertThatThrownBy(() -> {
            goodsService.register(goods);
        }).isInstanceOf(AttachmentNotExistException.class);
    }

    @Test
    @DisplayName("각 카테고리별 상품 리스트 조회")
    void goods_list_by_categoryId() {
        List<Goods> listOfCategory = goodsService.findListOfCategory(1L, false);
        assertThat(1).isEqualTo(listOfCategory.size());
        assertThat("파프리카").isEqualTo(listOfCategory.get(0).getName());
    }

    @Test
    @DisplayName("추천 상품 리스트 조회")
    public void recommends_goods() throws Exception {
        //given
        List<Goods> recommendGoods = goodsService.findRecommendGoods();
        //then
        assertThat(2).isEqualTo(recommendGoods.size());
    }
}