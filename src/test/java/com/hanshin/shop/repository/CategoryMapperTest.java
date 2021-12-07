package com.hanshin.shop.repository;

import com.hanshin.shop.IntegrationTests;
import com.hanshin.shop.vo.goods.CategoryVO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

class CategoryMapperTest extends IntegrationTests {

    @Autowired
    private CategoryMapper categoryMapper;

    @Test
    public void 카테고리_정보() throws Exception {
        //given
        final CategoryVO categoryVO = categoryMapper.get(1L);
        //when then
        Assertions.assertThat("organic").isEqualTo(categoryVO.getName());

    }

    @Test
    public void 카테고리_정보_목록() throws Exception {
        //when

        final List<CategoryVO> categories = categoryMapper.list();
        //then
        Assertions.assertThat(2).isEqualTo(categories.size());
    }
}