package com.qianfeng.shop.controller;

import com.qianfeng.shop.annotations.RequestMapping;
import com.qianfeng.shop.pojo.TbAddress;
import com.qianfeng.shop.pojo.TbUser;
import com.qianfeng.shop.service.AddressService;
import com.qianfeng.shop.service.OderService;
import com.qianfeng.shop.service.impl.AddressServiceImpl;
import com.qianfeng.shop.service.impl.OderServiceImpl;
import com.qianfeng.shop.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @ClassName AddressServlet
 * @date 2021/9/18 20:40
 * @Version 1.0
 */
@RestController
@RequestMapping("/address")
public class AddressServlet {

    private AddressService addressService;

    @Autowired
    public void setAddressService(AddressService addressService) {
        this.addressService = addressService;
    }

    @RequestMapping("/show")
        public String show(HttpServletRequest request,HttpServletResponse response){
            TbUser loginUser = (TbUser) request.getSession().getAttribute("loginUser");
            Long uid = loginUser.getId();//登陆用户的 id
            //通过id查询用户的地址,因为可能拥有多个地址,所以返回的应该是一个对象集合
            List<TbAddress> allAddresByUid = addressService.findAllAddresByUid(uid);
            request.getSession().setAttribute("addressList", allAddresByUid);
            return "forward:/addresslist.jsp";
    }

    @RequestMapping("/add")
    public String addAddress(HttpServletRequest request, HttpServletResponse response){
        TbAddress tbAddress=new TbAddress();

        TbUser loginUser = (TbUser) request.getSession().getAttribute("loginUser");
        Long uid = loginUser.getId();//登陆用户的 id
        String detail = request.getParameter("detail");
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        tbAddress.setUid(uid);
        tbAddress.setDetail(detail);
        tbAddress.setName(name);
        tbAddress.setPhone(phone);
        //这里获取方式繁琐可以封装
        addressService.addAddress(tbAddress);
        return "redirect:/address/show";
    }

    @RequestMapping("/update")
    public String updateAddress(HttpServletRequest request, HttpServletResponse response){
        TbUser user = (TbUser) request.getSession().getAttribute("loginUser");
        Long uid = user.getId();
        String id = request.getParameter("id");
        TbAddress address = addressService.findAddress(uid, id);
        request.getSession().setAttribute("address",address);
        return "forward:/updateid.jsp";
    }
    @RequestMapping("/updateAddress")
    public String update(HttpServletRequest request, HttpServletResponse response){
        TbAddress tbAddress=new TbAddress();
        String detail = request.getParameter("detail");
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        TbUser user = (TbUser) request.getSession().getAttribute("loginUser");
        Long uid = user.getId();
//        String id = request.getParameter("id");
        long id = Long.parseLong(request.getParameter("id"));
        tbAddress.setUid(uid);
        tbAddress.setId(id);
        tbAddress.setDetail(detail);
        tbAddress.setName(name);
        tbAddress.setPhone(phone);

        addressService.updateAddress(tbAddress);
        return "redirect:/address/show";

    }

    @RequestMapping("/delete")
    public String deteleAddress(HttpServletRequest request, HttpServletResponse response){
        String idl = request.getParameter("id");
        long id = Long.parseLong(idl);
        System.err.println("查看==>"+id);
        TbUser loginUser = (TbUser) request.getSession().getAttribute("loginUser");
        Long uid = loginUser.getId();//登陆用户的 id
        addressService.deleteAddress(uid,id);
        return "redirect:/address/show";
    }

}
