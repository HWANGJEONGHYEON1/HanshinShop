package com.hanshin.shop.service;

import com.hanshin.shop.config.redis.CacheConfig;
import com.hanshin.shop.vo.cart.CartVO;
import com.hanshin.shop.repository.CartMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static com.hanshin.shop.config.redis.CacheConfig.CACHE_LIST_MANAGER;
import static com.hanshin.shop.config.redis.CacheConfig.CACHE_MANAGER;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ROLE_MEMBER','ROLE_ADMIN')")
public class CartService {

    private final CartMapper cartMapper;

    @Transactional
    public void add(CartVO cartVO) {
        final CartVO existCartVO = isExistCartOne(cartVO.getGoodsId(), cartVO.getUserId());
        log.info("#existVO {}", existCartVO);
        if (Objects.isNull(existCartVO)) {
            log.info("cartVO {}", cartVO);
            cartMapper.save(cartVO);
            return ;
        }
        log.info("existVO {}", existCartVO);
        cartMapper.update(cartVO.getAmount(), existCartVO.getId());
    }

    @Cacheable(value = "count")
    public int numberOfCart(Long userId) {
        return cartMapper.count(userId);
    }

    @Cacheable(key = "#userId", value = "cart", cacheManager = CACHE_LIST_MANAGER)
    public List<CartVO> findAll(Long userId) {
        return cartMapper.findAll(userId);
    }

    public CartVO isExistCartOne(Long goodsId, Long userId) {
        CartVO existCartOne = cartMapper.isExistCartOne(goodsId, userId);
        log.info("# existCartOne : {}", existCartOne);
        return existCartOne;
    }

    @Transactional
    public int delete(Long cartId) {
        return cartMapper.delete(cartId);
    }

    @Transactional
    @CacheEvict(key = "#userId", value = "cart")
    public int deleteAll(Long userId) {
        final List<CartVO> all = cartMapper.findAll(userId);

        if (all.isEmpty() || all.size() == 0) {
            throw new IllegalStateException("삭제할 상품이 없습니다.");
        }
        return cartMapper.deleteAll(userId);
    }
}
