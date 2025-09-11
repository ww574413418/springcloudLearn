package com.itheima.mp.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.itheima.mp.domain.po.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {

    // @param("ew") = @Param(Constants.WRAPPER)
    @Update("update user set balance = balance - #{amount} ${ew.customSqlSegment}")
    void updateBalanceByIds(@Param(Constants.WRAPPER) QueryWrapper<User> queryWrapper, @Param("amount") int amount);

    @Update("update user set balance = balance - #{money} where id = #{id}")
    void deductionBalanceById(@Param("id") Long id, @Param("money") int money);
}
