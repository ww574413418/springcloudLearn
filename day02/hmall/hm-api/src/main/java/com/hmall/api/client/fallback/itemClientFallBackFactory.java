package com.hmall.api.client.fallback;


import com.hmall.api.client.itemClient;
import com.hmall.api.dto.ItemDTO;
import com.hmall.api.dto.OrderDetailDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.context.annotation.Bean;

import java.util.Collection;
import java.util.List;

@Slf4j
public class itemClientFallBackFactory implements FallbackFactory<itemClient> {
    @Override
    public itemClient create(Throwable cause) {
        return new itemClient() {
            @Override
            public List<ItemDTO> queryItemsByIds(Collection<Long> ids) {
                log.error("查询商品失败",cause);
                return List.of();
            }

            @Override
            public void deductStock(List<OrderDetailDTO> items) {
                log.error("扣件商品失败",cause);
                throw new RuntimeException(cause);
            }
        };
    }


}
