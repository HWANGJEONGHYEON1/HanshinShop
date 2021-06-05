package com.hanshin.shop.controller.shop;

import com.hanshin.shop.entity.goods.CategoryVO;
import com.hanshin.shop.entity.goods.Goods;
import com.hanshin.shop.entity.user.LoginUser;
import com.hanshin.shop.entity.user.User;
import com.hanshin.shop.repository.CategoryMapper;
import com.hanshin.shop.repository.GoodsAttachMapper;
import com.hanshin.shop.service.GoodsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/goods")
public class GoodsController {

    private final GoodsService goodsService;
    private final CategoryMapper categoryMapper;

    @GetMapping("/new")
    public String registerForm() {
        return "goods/createProduct";
    }

    @PostMapping("/new")
    public String register(Goods goods) {
        log.info("register {}" + goods);

        if (goods.getAttachList() != null) {
            goods.getAttachList()
                    .forEach(attach -> log.info("### {} , {}", attach.getFileName(), attach.getUploadPath()));
        }

        goodsService.save(goods);
        return "redirect:/";
    }

    @GetMapping("/main")
    @ResponseBody
    public ResponseEntity<List<Goods>> list() {
        final List<Goods> goodsAll = goodsService.findAllList();
        return new ResponseEntity<>(goodsAll, HttpStatus.OK);
    }

    @GetMapping("/recommend")
    @ResponseBody
    public ResponseEntity<List<Goods>> recommendList() {
        return new ResponseEntity<>(goodsService.findRecommendGoods(), HttpStatus.OK);
    }

    @GetMapping("/categories")
    @ResponseBody
    public ResponseEntity<List<CategoryVO>> categories() {
        return new ResponseEntity<>(categoryMapper.list(), HttpStatus.OK);
    }

    @GetMapping("/category/{id}")
    @ResponseBody
    public ResponseEntity<List<Goods>> categories(@PathVariable Long id) {
        return new ResponseEntity<>(goodsService.findListOfCategory(id), HttpStatus.OK);
    }

}
