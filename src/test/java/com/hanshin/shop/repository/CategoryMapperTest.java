package com.hanshin.shop.repository;

import com.hanshin.shop.entity.goods.CategoryVO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class CategoryMapperTest {

    @Autowired
    private CategoryMapper mapper;


    @Test
    public void 카테고리_정보() throws Exception {
        //given
        final CategoryVO categoryVO = mapper.get(2L);
        //when then
        Assertions.assertThat(categoryVO.getName()).isEqualTo("야채");

    }

    @Test
    public void 카테고리_정보_목록() throws Exception {

        //when

        final List<CategoryVO> select = mapper.list();
        //then
        Assertions.assertThat(select.size()).isEqualTo(5);
    }
}