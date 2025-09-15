package com.itheima.mp.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.mp.domain.dto.PageDTO;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.domain.query.UserQuery;
import com.itheima.mp.domain.vo.UserVO;
import com.itheima.mp.emus.userStatus;
import com.itheima.mp.mapper.UserMapper;
import com.itheima.mp.service.userService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

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
        if (user == null || user.getStatus() == userStatus.FROZE){
            throw new RuntimeException("用户状体异常");
        }
        // 3.查询余额是否充足
        if (user.getBalance() < money){
            throw new RuntimeException("用户余额不足");
        }
        // 4.更新余额
        userMapper.deductionBalanceById( id,  money);
    }

    @Override
    public List<User> queryUsers(String name, Integer status, Integer maxBalance, Integer minBalance) {
        List<User> list = lambdaQuery().like(name != null, User::getUsername, name)
                .eq(status != null, User::getStatus, status)
                .gt(minBalance != null, User::getBalance, minBalance)
                .lt(maxBalance != null, User::getBalance, maxBalance)
                .list();
        return list;
    }

    @Override
    public PageDTO<UserVO> queryUsersPage(UserQuery userQuery) {
        String name = userQuery.getName();
        Integer status = userQuery.getStatus();
        //1.构建分页条件
        Page<User> page = Page.of(userQuery.getPageNO(), userQuery.getPageSize());
        if (StrUtil.isNotBlank(userQuery.getSortBy())){
            page.addOrder(
                    Boolean.TRUE.equals(userQuery.getIsAsc()) ?
                            OrderItem.asc(userQuery.getSortBy()) :
                            OrderItem.desc(userQuery.getSortBy()));
        }else {
            // 默认按照更新时间排序
             page.addOrder(
                     Boolean.TRUE.equals(userQuery.getIsAsc()) ?
                             OrderItem.asc("update_time") :
                             OrderItem.desc("update_time"));
        }

        //2.构建查询条件
        lambdaQuery().like(name!=null,User::getUsername,name)
                .eq(status != null,User::getStatus,status)
                .page(page);

        //3.封装vo结果
        PageDTO<UserVO> dto = new PageDTO<>();
        // 总条数
        dto.setTotal((int) page.getTotal());
        // 总页数
        dto.setPages((int) page.getPages());
        // 当前页数据
        List<User> records = page.getRecords();
        // 转成userVO对象
        if(records.isEmpty()){
            dto.setList(Collections.emptyList());
        }
        List<UserVO> userVOS = BeanUtil.copyToList(records, UserVO.class);
        dto.setList(userVOS);

        PageDTO.of()
        //4.返回
        return dto;
    }


}
