package com.hanshin.shop.repository;

import com.hanshin.shop.IntegrationTests;
import com.hanshin.shop.vo.goods.CategoryVO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

class CategoryMapperTest extends IntegrationTests {

    @Autowired
    private CategoryMapper categoryMapper;

    @Test
    @DisplayName("카테고리 리스트에 원소 1,2번 조회")
    public void category_name_element() throws Exception {
        //when then
        Assertions.assertThat("organic").isEqualTo(categoryMapper.get(1L).getName());
        Assertions.assertThat("fruit").isEqualTo(categoryMapper.get(2L).getName());
    }

    @Test
    @DisplayName("카테고리 리스트 사이즈")
    public void category_list_size() throws Exception {

        final List<CategoryVO> categories = categoryMapper.list();
        //then
        Assertions.assertThat(2).isEqualTo(categories.size());
    }
}