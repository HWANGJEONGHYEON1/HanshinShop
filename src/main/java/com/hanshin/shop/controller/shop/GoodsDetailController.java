package com.hanshin.shop.controller.shop;

import com.hanshin.shop.entity.CategoryVO;
import com.hanshin.shop.entity.Goods;
import com.hanshin.shop.entity.GoodsAttachVO;
import com.hanshin.shop.repository.CategoryMapper;
import com.hanshin.shop.repository.GoodsAttachMapper;
import com.hanshin.shop.repository.GoodsMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/shop-details/")
public class ShopDetailController {
    private final GoodsMapper goodsMapper;
    private final GoodsAttachMapper goodsAttachMapper;
    private final CategoryMapper categoryMapper;

    @GetMapping("")
    public String shopDetail() {
        return "shop/shop-details";
    }

    @GetMapping("/{id}")
    public String shopDetail(@PathVariable Long id, Model model) {
        log.info("#### {}", id);
        final Goods goods = goodsMapper.findOne(id);
        final List<GoodsAttachVO> attach = goodsAttachMapper.findByGoodsId(id);
        final String categoryName = categoryMapper.get(goods.getCategoryId()).getName();
        log.info("# attach {}",goods);
        log.info("# attach {}",attach);
        model.addAttribute("goods", goods);
        model.addAttribute("attach", attach.get(0));
        model.addAttribute("categoryName", categoryName);

        return "shop/shop-details";
    }

}
