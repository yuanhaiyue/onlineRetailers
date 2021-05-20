package com.yuan.online.common;


import org.springframework.stereotype.Component;

/**
 * @author 14760
 */
@Component
public interface CartStatus {
    //购物车未选中状态

    int UN_CHECKED=0;

    //购物车选中状态

    int CHECKED=1;


}
