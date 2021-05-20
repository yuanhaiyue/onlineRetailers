package com.yuan.online.common;


import org.springframework.stereotype.Component;

/**
 * @author 14760
 */
@Component
public interface SaleStatus {
    //商品下架状态

    int NOT_SALE=0;

    //商品上架状态

    int SALE=1;


}
