package com.hanshin.shop.controller.shop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanshin.shop.IntegrationTests;
import com.hanshin.shop.exception.AttachmentNotExistException;
import com.hanshin.shop.vo.goods.Goods;
import com.hanshin.shop.vo.goods.GoodsAttachVO;
import com.hanshin.shop.vo.goods.GoodsDto;
import com.hanshin.shop.vo.paging.Criteria;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GoodsControllerTest extends IntegrationTests {

    public static ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("상품 추가 api")
    @WithMockUser(username = "admin", roles = "ADMIN")
    void goods_register() throws Exception {

        GoodsDto goodsDto = new GoodsDto("테스트 상품", 100000, "테스트", 1L);
        GoodsAttachVO attachVO = new GoodsAttachVO(UUID.randomUUID().toString(), "/path/", "새송이버섯");
        Goods goods = Goods.create(goodsDto);
        goods.addAttach(attachVO);

        mvc.perform(post("/api/goods/new")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(objectMapper.writeValueAsString(goods)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("상품 추가 api 에러")
    @Transactional
    @WithMockUser(username = "admin", roles = "ADMIN")
    void goods_register_exception() throws Exception {

        GoodsDto goodsDto = new GoodsDto("테스트 상품", 100000, "테스트", 1L);
        Goods goods = Goods.create(goodsDto);

        mvc.perform(post("/api/goods/new")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(objectMapper.writeValueAsString(goods)))
                .andExpect(result -> Assertions.assertThat(result.getResolvedException()
                        .getClass()
                        .isAssignableFrom(AttachmentNotExistException.class))
                        .isTrue()
                ).andExpect(status().is4xxClientError())
                .andDo(print());
    }

//    @Test
    @DisplayName("메인 api")
    void main() throws Exception {
        mvc.perform(get("/api/main")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(objectMapper.writeValueAsString(new Criteria(1,10))))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("recommend api")
    void recommend() throws Exception {
        mvc.perform(get("/api/recommend"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", Matchers.equalTo(2)));
    }

    @Test
    @DisplayName("categories api")
    void categories() throws Exception {
        mvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", Matchers.equalTo(2)));
    }

    @Test
    @DisplayName("categories id별 api")
    void categories_by_id() throws Exception {
        mvc.perform(get("/api/category/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", Matchers.equalTo("파프리카")));
    }
}