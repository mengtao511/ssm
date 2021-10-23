package com.qianfeng;

import com.qianfeng.shop.controller.GoodsTypeServlet;
import com.qianfeng.shop.dao.impl.CartDaoImpl;

/**
 * @ClassName test
 * @date 2021/9/15 15:22
 * @Version 1.0
 */
public class test {
    public static void main(String[] args) {


            CartDaoImpl cartDao=new CartDaoImpl();
            cartDao.findCartByUid(29);
//            cartDao.deleteByUid(29);

    }
}
