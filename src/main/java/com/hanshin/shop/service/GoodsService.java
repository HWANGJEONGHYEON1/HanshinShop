package com.hanshin.shop.service;

import com.hanshin.shop.entity.goods.Goods;
import com.hanshin.shop.repository.GoodsAttachMapper;
import com.hanshin.shop.repository.GoodsMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class GoodsService {

    private final GoodsMapper goodsMapper;
    private final GoodsAttachMapper attachMapper;

    @Transactional
    public void save(Goods goods) {
        goodsMapper.save(goods);
        if (goods.getAttachList() == null || goods.getAttachList().size() <= 0) {
            return ;
        }
        goods.getAttachList().forEach(attach -> {
            attach.setGoodsId(goods.getId());
            attachMapper.insert(attach);
        });
    }

    public List<Goods> findAllList() {
        return goodsMapper.findAllList();
    }

    public List<Goods> findRecommendGoods() {
        final List<Goods> allGoodsList = goodsMapper.findAllList();
        return getShuffleList(allGoodsList);

    }
    public Goods findOne(Long goodsId) {
        return goodsMapper.findOne(goodsId);
    }

    public List<Goods> findListOfCategory(Long categoryId) {
        return goodsMapper.findListOfCategory(categoryId);
    }

    public List<Goods> findListOfRelated(Long categoryId) {
        final List<Goods> listOfCategory = goodsMapper.findListOfCategory(categoryId);
        return getShuffleList(listOfCategory);
    }

    private List<Goods> getShuffleList(List<Goods> allGoodsList) {
        Collections.shuffle(allGoodsList);
        int limitCount = 4;
        if (allGoodsList.size() < 4 ) {
            limitCount = allGoodsList.size();
        }
        log.info("limit {} ", limitCount);
        return allGoodsList.stream()
                .limit(limitCount)
                .collect(Collectors.toList());
    }
}
