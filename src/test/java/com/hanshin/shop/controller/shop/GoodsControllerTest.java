//package com.hanshin.shop.controller.shop;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MockMvcBuilder;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//
//@SpringBootTest
//class GoodsControllerTest {
//
//    @Autowired
//    private WebApplicationContext ctx;
//
//
//    private MockMvc mockMvc;
//
//    @BeforeEach
//    public void setup() {
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
//    }
//
//
//    @Test
//    public void paging() throws Exception {
//        final MvcResult mvcResult = mockMvc.perform(
//                get("/goods/main")
//                        .param("pagaNum", "2")
//                        .param("amount", "50")
//        ).andReturn();
//        System.out.println(mvcResult.getRequest());
//        System.out.println(mvcResult.getResponse());
//
//    }
//}