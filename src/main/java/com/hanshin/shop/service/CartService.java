package com.hanshin.shop.service;

import com.hanshin.shop.entity.cart.CartVO;
import com.hanshin.shop.repository.CartMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CartService {

    private final CartMapper cartMapper;

    @Transactional
    public void insert(CartVO cartVO) {
        final CartVO existCartVO = isExistCartOne(cartVO.getGoodsId(), cartVO.getUserId());
        log.info("existVO {}", existCartVO);

        if (Objects.isNull(existCartVO)) {
            cartMapper.insert(cartVO);
        } else {
            log.info("existVO {}", existCartVO);
            cartMapper.update(cartVO.getAmount(), existCartVO.getId());
        }
    }

    public List<CartVO> findAll(Long userId) {
        return cartMapper.findAll(userId);
    }

    public CartVO isExistCartOne(Long goodsId, Long userId) {
        return cartMapper.isExistCartOne(goodsId, userId);
    }

    @Transactional
    public int delete(Long id) {
        return cartMapper.delete(id);
    }

    @Transactional
    public int deleteAll(Long userId) {
        final List<CartVO> all = cartMapper.findAll(userId);
        log.info("deleteALl {}", all.size());
        if (all.isEmpty() || all.size() == 0) {
            throw new IllegalStateException("삭제할 상품이 없습니다.");
        }
        return cartMapper.deleteAll(userId);
    }
}
