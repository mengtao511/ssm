package com.qianfeng.shop.controller;

import com.qianfeng.shop.annotations.RequestMapping;
import com.qianfeng.shop.annotations.RequestMapping;
import com.qianfeng.shop.pojo.CartItem;
import com.qianfeng.shop.pojo.TbUser;
import com.qianfeng.shop.service.CartService;
import com.qianfeng.shop.service.impl.CartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by jackiechan on 2021/9/17 09:59
 *
 * @author jackiechan
 * @version 1.0
 * @since 1.0
 */
@RequestMapping(("/cart"))
@RestController
public class CartController {
    private CartService cartService;

    @Autowired
    public void setCartService(CartService cartService) {
        this.cartService = cartService;
    }

    @RequestMapping("/create")
    public String addCart(HttpServletRequest request, HttpServletResponse response) {
        String goodId = request.getParameter("id");//获取用户想要添加到购物车的商品的 id
        //获取到当前的登录用户
        TbUser loginUser = (TbUser) request.getSession().getAttribute("loginUser");
        Long uid = loginUser.getId();//登陆用户的 id
        int num = 1;//现在固定数量 1,我们没有设置数量的操作,如果有了,只需要从参数中获取就可以
        try {
            cartService.addCart(goodId,uid,num);
            response.setContentType("text/html;charset=utf-8");
            return "redirect:/cartSuccess.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error/error.jsp";
        }
    }
    @RequestMapping("/show")
    public String show(HttpServletRequest request, HttpServletResponse response) {
       // if (request.getSession().getAttribute("cart") == null) {//如果没有查询过购物车,就先查询,再保存
            //获取到当前登录的用户,因为用户只能看自己的购物车
            TbUser loginUser = (TbUser) request.getSession().getAttribute("loginUser");
            Long uid = loginUser.getId();//登陆用户的 id
            List<CartItem> cartByUid = cartService.findCartByUid(uid);//获取到当前用户的购物车数据
            request.getSession().setAttribute("cart", cartByUid);
       // }
        return "forward:/cart.jsp";
    }

    /**
     * 实现了点击清空购物车,将用户id下的购物车内容清空
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/clear")
    public String clear(HttpServletRequest request,HttpServletResponse response){
        TbUser loginUser = (TbUser) request.getSession().getAttribute("loginUser");
        Long uid = loginUser.getId();//登陆用户的 id
        cartService.deleteByUid(uid);
        request.getSession().removeAttribute("cart");
        return "forward:/cart.jsp";
    }
}
