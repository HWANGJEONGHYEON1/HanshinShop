package com.hanshin.shop.controller.shop;

import com.hanshin.shop.entity.Goods;
import com.hanshin.shop.entity.GoodsAttachVO;
import com.hanshin.shop.repository.CategoryMapper;
import com.hanshin.shop.repository.GoodsAttachMapper;
import com.hanshin.shop.service.GoodsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class GoodsDetailController {
    private final GoodsService goodsService;
    private final GoodsAttachMapper goodsAttachMapper;
    private final CategoryMapper categoryMapper;

    @GetMapping("/goods/{id}")
    public String goodsDetail(@PathVariable Long id, Model model) {
        final Goods goods = goodsService.findOne(id);
        final List<GoodsAttachVO> attach = goodsAttachMapper.findByGoodsId(id);
        final String categoryName = categoryMapper.get(goods.getCategoryId()).getName();
        model.addAttribute("goods", goods);
        model.addAttribute("attach", attach.get(0));
        model.addAttribute("categoryName", categoryName);

        return "goods/goods-details";
    }

    @GetMapping("/goods-detail/category/{id}")
    @ResponseBody
    public ResponseEntity<List<Goods>> categories(@PathVariable Long id) {
        log.info("# id {}", id);
        return new ResponseEntity<>(goodsService.findListOfRelated(id), HttpStatus.OK);
    }

}
