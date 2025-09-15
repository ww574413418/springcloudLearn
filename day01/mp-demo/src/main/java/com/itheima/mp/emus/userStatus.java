package com.itheima.mp.emus;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 用户状态枚举
 */
@Getter
public enum userStatus {
    NORMAL(1,"正常"),
    FROZE(2,"冻结"),
    ;

    @EnumValue
    private final int value;
    @JsonValue
    private final String desc;


    userStatus(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
