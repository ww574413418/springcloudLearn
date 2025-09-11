package com.itheima.mp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.mapper.UserMapper;
import com.itheima.mp.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class userServiceImpl extends ServiceImpl<UserMapper,User> implements userService {

    // 构造函数注入
    private UserMapper userMapper;

    public userServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public void deductionBalanceById(Long id, int money) {
        // 1.查询用户
        User user = this.getById(id);
        // 2.查询用户状态
        if (user == null || user.getStatus() == 2){
            throw new RuntimeException("用户状体异常");
        }
        // 3.查询余额是否充足
        if (user.getBalance() < money){
            throw new RuntimeException("用户余额不足");
        }
        // 4.更新余额
        userMapper.deductionBalanceById( id,  money);
    }
}
