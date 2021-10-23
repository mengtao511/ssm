package com.qianfeng.shop.controller;


import com.qianfeng.shop.annotations.RequestMapping;
import com.qianfeng.shop.pojo.*;
import com.qianfeng.shop.service.AddressService;
import com.qianfeng.shop.service.CartService;
import com.qianfeng.shop.service.OderService;
import com.qianfeng.shop.service.impl.AddressServiceImpl;
import com.qianfeng.shop.service.impl.CartServiceImpl;
import com.qianfeng.shop.service.impl.OderServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by jackiechan on 2021/9/18 09:46
 *
 * @author jackiechan
 * @version 1.0
 * @since 1.0
 */

@RequestMapping("/order")
public class OrderController {

    private CartService cartService = new CartServiceImpl();
    private AddressService addressService = new AddressServiceImpl();
    private OderService oderService = new OderServiceImpl();

    @RequestMapping("/preView")
    public String preView(HttpServletRequest request, HttpServletResponse response) {
        TbUser loginUser = (TbUser) request.getSession().getAttribute("loginUser");
        Long uid = loginUser.getId();
        if (request.getSession().getAttribute("cart") == null) {
            List<CartItem> cartItemList = cartService.findCartByUid(uid);//获取到购物车数据
            request.getSession().setAttribute("cart", cartItemList);
        }

        if (request.getSession().getAttribute("addressList") == null) {
            //获取购物车数据
            //获取地址数据
            List<TbAddress> addressList = addressService.findAllAddresByUid(uid);
            //携带数据
            //跳转到预览页面
            request.getSession().setAttribute("addressList", addressList);
        }

        return "forward:/order.jsp";

    }

    @RequestMapping(("/create"))
    public String createOrder(HttpServletRequest request, HttpServletResponse response) {
        //创建订单需要知道购买的商品和数量,我们直接把购物车里面所有的数据都下单
        List<CartItem> cart = (List<CartItem>) request.getSession().getAttribute("cart");//先获取到要下单的商品
        //还得知道地址选择的是哪个, 地址从前端传过来
        String aid = request.getParameter("aid");//用户选择的地址
        Order order = oderService.createOrder(cart, aid);
        request.getSession().setAttribute("order",order);

        Long uid = cart.get(0).getUid();
        //在这里实现了提交订单后删除所有的订单
        cartService.deleteByUid(uid);
        //删除数据库的内容
        request.getSession().removeAttribute("cart");
        //删除了session中的内容

        //携带数据 数据在哪?
        return "redirect:/orderSuccess.jsp";
    }

    @RequestMapping("/find")
    public String findOrder(HttpServletRequest request,HttpServletResponse response){
        TbUser loginUser = (TbUser) request.getSession().getAttribute("loginUser");
        Long uid = loginUser.getId();//登陆用户的 id
        List<TbOrder> orderByUid = oderService.findOrderByUid(uid);
        request.getSession().setAttribute("ordersList",orderByUid);
        return "redirect:/orderList.jsp";

    }

    @RequestMapping("/delete")
    public String deleteOrder(HttpServletRequest request,HttpServletResponse response){
        String id = request.getParameter("id");
        oderService.deleteOrderById(id);
        return "redirect:/order/find";
    }

}
