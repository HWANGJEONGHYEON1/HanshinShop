package com.hanshin.shop.db;

import com.hanshin.shop.repository.TestDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestDAOTest {

    @Autowired
    private TestDao dao;

    @Test
    void test() {
        System.out.println(dao.getTest());
    }

    @Test
    void test1() {
        System.out.println(dao.selectTestName());
    }

}
