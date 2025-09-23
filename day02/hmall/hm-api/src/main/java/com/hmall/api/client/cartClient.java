package com.hmall.api.client;

import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("cart-service")
public interface cartClient {

    @DeleteMapping("/carts")
    public void deleteCartItemByIds(@RequestParam("ids") List<Long> ids);
}
