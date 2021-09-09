package com.hanshin.shop.service;

import com.hanshin.shop.vo.goods.Goods;
import com.hanshin.shop.repository.GoodsAttachMapper;
import com.hanshin.shop.repository.GoodsMapper;
import com.hanshin.shop.vo.paging.Criteria;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
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

    private static final int LIMIT_COUNT = 4;
    private static final int ZERO = 0;
    private final GoodsMapper goodsMapper;
    private final GoodsAttachMapper attachMapper;

    @Transactional
    public void save(Goods goods) {
        goodsMapper.save(goods);
        if (goods.getAttachList() == null || goods.getAttachList().size() <= ZERO) {
            return ;
        }
        goods.getAttachList().forEach(attach -> {
            attach.setGoodsId(goods.getId());
            attachMapper.insert(attach);
        });
    }

    public List<Goods> findAllList(Criteria criteria) {
        return goodsMapper.getListWithPaging(criteria);
    }

    @Cacheable(value = "recommendList")
    public List<Goods> findRecommendGoods() {
        log.info("## cache");
        final List<Goods> allGoodsList = goodsMapper.findAllList();

        return getShuffleList(allGoodsList);
    }

    public Goods findOne(Long goodsId) {
        return goodsMapper.findOne(goodsId);
    }

    public List<Goods> findListOfCategory(Long categoryId, boolean isRelated) {
        if (isRelated) {
            return getShuffleList(goodsMapper.findListOfCategory(categoryId));
        }
        return goodsMapper.findListOfCategory(categoryId);
    }

    private List<Goods> getShuffleList(List<Goods> allGoodsList) {
        Collections.shuffle(allGoodsList);
        int limitCount = getLimitCount(allGoodsList);
        return allGoodsList.stream()
                .limit(limitCount)
                .collect(Collectors.toList());
    }

    private int getLimitCount(List<Goods> allGoodsList) {
        int limitCount = LIMIT_COUNT;
        if (allGoodsList.size() < LIMIT_COUNT) {
            limitCount = allGoodsList.size();
        }
        return limitCount;
    }
}
