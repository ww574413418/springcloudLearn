package com.itheima.mp.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.itheima.mp.domain.po.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class  UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void testInsert() {
        User user = new User();
        user.setId(5L);
        user.setUsername("Lucy");
        user.setPassword("123");
        user.setPhone("18688990011");
        user.setBalance(200);
        user.setInfo("{\"age\": 24, \"intro\": \"英文老师\", \"gender\": \"female\"}");
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        userMapper.insert(user);
    }

    @Test
    void testSelectById() {
        User user = userMapper.selectById(5L);
        System.out.println("user = " + user);
    }


    @Test
    void testQueryByIds() {
        List<User> users = userMapper.selectByIds(List.of(1L, 2L, 3L, 4L));
        users.forEach(System.out::println);
    }

    @Test
    void testUpdateById() {
        User user = new User();
        user.setId(5L);
        user.setBalance(20000);
        userMapper.updateById(user);
    }

    @Test
    void testDeleteUser() {
        userMapper.deleteById(5L);
    }

    @Test
    void testQueryWrapper(){
        //定义查询条件
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id","username","info")
                        .like("username","o")
                        // ge = greater and equal
                        .ge("balance",1000);
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }
    @Test
    void testUpdateByQueryWrapper(){
        User user = new User();
        user.setBalance(2000);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username","jack");
        int update = userMapper.update(user, queryWrapper);
        System.out.println(update);
    }

    @Test
    void testUpdateWrapper(){
        List<Long> idList = List.of(1L,2L,4L);
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        userUpdateWrapper.setSql("balance = balance - 200").in("id",idList);
        userMapper.update(userUpdateWrapper);
    }

    @Test
    void testLambdaQueryWrapper(){
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.select(User::getId,User::getUsername,User::getBalance)
                .like(User::getUsername,"o")
                .ge(User::getBalance,1000);
        userMapper.selectList(lambdaQueryWrapper);
    }

    @Test
    void testCustomSqlUpdate(){
        // 更新条件
        List<Long> idList = List.of(1L, 2L, 4L);
        int amount = 200;
        // 定义sql更新条件
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id",idList);
        // 调用自定义sql方法
        userMapper.updateBalanceByIds(queryWrapper,amount);
    }
}