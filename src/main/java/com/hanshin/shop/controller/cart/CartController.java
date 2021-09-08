package com.hanshin.shop.controller.cart;

import com.hanshin.shop.vo.cart.CartDTO;
import com.hanshin.shop.vo.cart.CartVO;
import com.hanshin.shop.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
public class CartController {

    private final CartService cartService;

    @GetMapping("/cart/{userId}")
    public List<CartVO> shop(@PathVariable Long userId) {
        return cartService.findAll(userId);
    }

    @DeleteMapping("/cart/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        cartService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/carts/{userId}")
    public ResponseEntity<Void> deleteAll(@PathVariable Long userId) {
        cartService.deleteAll(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/cart/count/{userId}")
    public int count(@PathVariable Long userId) {
        return cartService.count(userId);
    }

    @PostMapping("/cart")
    public ResponseEntity<Void> cart(@RequestBody CartVO cartVO) {
        cartService.insert(cartVO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
