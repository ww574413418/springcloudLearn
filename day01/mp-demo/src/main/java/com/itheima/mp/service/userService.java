package com.itheima.mp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.mp.domain.po.User;
import org.springframework.stereotype.Service;

@Service
public interface userService extends IService<User> {
    void deductionBalanceById(Long id, int money);
}
