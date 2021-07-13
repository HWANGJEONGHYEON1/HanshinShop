package com.hanshin.shop.controller.shop;

import com.hanshin.shop.vo.goods.CategoryVO;
import com.hanshin.shop.vo.goods.Goods;
import com.hanshin.shop.vo.paging.Criteria;
import com.hanshin.shop.vo.paging.PageDto;
import com.hanshin.shop.repository.CategoryMapper;
import com.hanshin.shop.service.GoodsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
public class GoodsController {

    private final GoodsService goodsService;
    private final CategoryMapper categoryMapper;

    @PostMapping("/goods/new")
    public ResponseEntity<Void> register(@RequestBody Goods goods) {
        if (goods.getAttachList() != null) {
            goods.getAttachList()
                    .forEach(attach -> log.info("### {} , {}", attach.getFileName(), attach.getUploadPath()));
        }

        goodsService.save(goods);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/main")
    public ResponseEntity<Map<String, Object>> list(Criteria cri) {
        Map<String, Object> map = new HashMap<>();
        final List<Goods> goodsAll = goodsService.findAllList(cri);
        map.put("goodsAll", goodsAll);
        map.put("pageMaker", new PageDto(123, cri));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/recommend")
    public List<Goods> recommendList() {
        return goodsService.findRecommendGoods();
    }

    @GetMapping("/categories")
    public List<CategoryVO> categories() {
        return categoryMapper.list();
    }

    @GetMapping("/category/{id}")
    public List<Goods> categories(@PathVariable Long id) {
        return goodsService.findListOfCategory(id, false);
    }

    @GetMapping("/goods/detail/category/{id}")
    public List<Goods> relatedCategoryGoods(@PathVariable Long id) {
        return goodsService.findListOfCategory(id, true);
    }

}
