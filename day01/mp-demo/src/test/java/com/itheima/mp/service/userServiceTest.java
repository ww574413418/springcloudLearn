package com.itheima.mp.service;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.mp.domain.po.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class userServiceTest {

    @Autowired
    private userService userService;

    @Test
    void testSaveUser(){
        User user = new User();
        user.setId(5L);
        user.setUsername("Tom");
        user.setPassword("123");
        user.setPhone("18688990011");
        user.setBalance(200);
        user.setInfo("{\"age\": 24, \"intro\": \"英文老师\", \"gender\": \"female\"}");
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        userService.save(user);
    }

    @Test
    void pageQuerytest(){
        int pageNO = 1;
        int pageSize = 2;
        Page<User> page = Page.of(pageNO, pageSize);
        // 按照balance升序
        page.addOrder(OrderItem.asc("balance"));
        Page<User> userList = userService.page(page);
        System.out.println(userList.getTotal());
        System.out.println(userList.getPages());
        System.out.println(userList.getRecords());
    }

}