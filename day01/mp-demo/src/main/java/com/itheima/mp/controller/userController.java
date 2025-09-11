package com.itheima.mp.controller;

import cn.hutool.core.bean.BeanUtil;
import com.itheima.mp.domain.dto.UserFormDTO;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.domain.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.itheima.mp.service.userService;
import java.util.ArrayList;
import java.util.List;

@Api(tags = "用户管理接口")
@RequestMapping("/users")
@RestController
// 使用loomk来
@RequiredArgsConstructor
public class userController {

//    spirng 不推荐使用自动注入,推荐只有构造器注入
//    @Autowired
//    private userService userService;

//    构造方法注入
//    private  userService userService;
//
//    public userController(userService userService) {
//        this.userService = userService;
//    }

//  使用loomk来注入,设置成final在类初始化的时候就注入@RequiredArgsConstructor
    private final userService userService;

    //新增用户
    @ApiOperation("新增用户接口")
    @PostMapping
    void addUser(@RequestBody UserFormDTO userFormDTO){
        // 将userFormDTO拷贝到user,使用hutool提供的工具
        User user = BeanUtil.copyProperties(userFormDTO, User.class);
        boolean save = userService.save(user);
    }

    //删除用户
    @ApiOperation("删除用户")
    @DeleteMapping("/{id}")
    void delUser(@ApiParam("用户id") @PathVariable("id") Long id){
        userService.removeById(id);
    }

    //根据id查询用户
    @ApiOperation("根据id查询用户")
    @GetMapping("/{id}")
    UserVO getUserById(@ApiParam("用户id") @PathVariable("id") Long id){
        User user = userService.getById(id);
        UserVO userVO = BeanUtil.copyProperties(user, UserVO.class);
        return userVO;
    }

    //根据id批量查询
    @ApiOperation("根据id批量查询")
    @GetMapping
    List<UserVO> getUserByIds(@ApiParam("用户id集合") @RequestParam List<Long> ids){
        List<User> users = userService.listByIds(ids);
        List<UserVO> userVOS = BeanUtil.copyToList(users,UserVO.class);
        return userVOS;
    }

    //根据id扣减余额
    @ApiOperation("根据id扣减余额")
    @PutMapping("{id}/deduction/{money}")
    void decucationBalanceById(@ApiParam("用户id") @PathVariable Long id,
                               @ApiParam("钱") @PathVariable int money){
        userService.deductionBalanceById(id,money);
    }
}
