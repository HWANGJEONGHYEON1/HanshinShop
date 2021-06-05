package com.hanshin.shop.controller.cart;

import com.hanshin.shop.entity.cart.CartDTO;
import com.hanshin.shop.entity.cart.CartVO;
import com.hanshin.shop.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/member")
public class CartController {

    private final CartService cartService;

    @GetMapping("/cart")
    public String cart(@RequestParam(value = "userId") String userId, Model model) {
        model.addAttribute("userId", userId);
        return "cart/shopping-cart";
    }

    @GetMapping("/cart/{userId}")
    @ResponseBody
    public ResponseEntity<List<CartVO>> shop(@PathVariable("userId") Long userId) {
        final List<CartVO> carts = cartService.findAll(userId);
        log.info("carts {}", carts);
        return new ResponseEntity<>(carts, HttpStatus.OK);
    }


    @DeleteMapping("/cart/{id}")
    @ResponseBody
    public ResponseEntity<String> delete(@PathVariable Long id) {
        log.info("cartId : {}", id);
        cartService.delete(id);
        return new ResponseEntity<>("deleted", HttpStatus.OK);
    }

    @DeleteMapping("/carts/{userId}")
    @ResponseBody
    public ResponseEntity<String> deleteAll(@PathVariable Long userId) {
        log.info("userID : {}", userId);
        cartService.deleteAll(userId);
        return new ResponseEntity<>("deleted all", HttpStatus.OK);
    }

    @GetMapping("/cartCount/{userId}")
    @ResponseBody
    public int count(@PathVariable Long userId) {
        return cartService.findAll(userId).size();
    }

    @PostMapping("/cart/{id}")
    @ResponseBody
    public ResponseEntity<String> cart(@PathVariable Long id, @RequestBody CartDTO cartDTO) {
        log.info("cartDto = {} ", cartDTO);
        log.info("id = {} ", id);
        final CartVO save = CartVO.save(cartDTO);
        cartService.insert(save);
        return new ResponseEntity<String>("success", HttpStatus.OK);
    }

}
