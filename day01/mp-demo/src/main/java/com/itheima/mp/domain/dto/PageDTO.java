package com.itheima.mp.domain.dto;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.domain.vo.UserVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Data
@ApiModel(description = "分页结果")
public class PageDTO<T> {

    @ApiModelProperty("总条数")
    private Integer total;

    @ApiModelProperty("总页数")
    private Integer pages;

    @ApiModelProperty("结果集")
    private List<T> list;

    /**
     *
     * @param p page对象,包含的有分页查询的数据
     * @param clazz
     * @param convertor 将PO转成VO的函数用于字段名不是一一对应的情况
     * @return pageDTO
     * @param <PO> 查询时的封装对象
     * @param <VO> 返回给前端的对象
     */
    public static <PO,VO> PageDTO<VO> of(Page<PO> p, Class<VO> clazz, Function<PO,VO> convertor){
        //3.封装vo结果
        PageDTO<VO> dto = new PageDTO<>();
        // 总条数
        dto.setTotal((int) p.getTotal());
        // 总页数
        dto.setPages((int) p.getPages());
        // 当前页数据
        List<PO> records = p.getRecords();
        //转成userVO对象
//        if(records.isEmpty()){
//            dto.setList(Collections.emptyList());
//        }
//        List<VO> userVOS = BeanUtil.copyToList(records, clazz);
        // 使用传入的方法,将PO对象转成VO对象
        List<VO> userVOS = records.stream().map(convertor).collect(Collectors.toList());

        dto.setList(userVOS);
        return dto;
    }
}
