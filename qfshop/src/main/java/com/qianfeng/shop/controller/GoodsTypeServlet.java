package com.qianfeng.shop.controller;


//
//                            _ooOoo_  
//                           o8888888o  
//                           88" . "88  
//                           (| -_- |)  
//                            O\ = /O  
//                        ____/`---'\____  
//                      .   ' \\| |// `.  
//                       / \\||| : |||// \  
//                     / _||||| -:- |||||- \  
//                       | | \\\ - /// | |  
//                     | \_| ''\---/'' | |  
//                      \ .-\__ `-` ___/-. /  
//                   ___`. .' /--.--\ `. . __  
//                ."" '< `.___\_<|>_/___.' >'"".  
//               | | : `- \`.;`\ _ /`;.`/ - ` : | |  
//                 \ \ `-. \_ __\ /__ _/ .-` / /  
//         ======`-.____`-.___\_____/___.-`____.-'======  
//                            `=---='  
//  
//         .............................................  
//                  佛祖镇楼            BUG辟易  
//          佛曰:  
//                  写字楼里写字间，写字间里程序员；  
//                  程序人员写程序，又拿程序换酒钱。  
//                  酒醒只在网上坐，酒醉还来网下眠；  
//                  酒醉酒醒日复日，网上网下年复年。  
//                  但愿老死电脑间，不愿鞠躬老板前；  
//                  奔驰宝马贵者趣，公交自行程序员。  
//                  别人笑我忒疯癫，我笑自己命太贱；  


import com.google.gson.Gson;
import com.qianfeng.shop.annotations.RequestMapping;
import com.qianfeng.shop.pojo.TbGoodsType;
import com.qianfeng.shop.service.GoodsTypeService;
import com.qianfeng.shop.service.impl.GoodsTypeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by jackiechan on 2021/9/15 09:26
 *
 * @author jackiechan
 * @version 1.0
 * @since 1.0
 */
@RequestMapping("/goodtypes") //我们自己约定,如果类上面有这个注解,就意味着,这个类里面的方法上面的指定路径前面都要拼接这个路径
@RestController
public class GoodsTypeServlet {
    private Gson gson = new Gson();
    private GoodsTypeService goodsTypeService;

    @Autowired
    public void setGoodsTypeService(GoodsTypeService goodsTypeService) {
        this.goodsTypeService = goodsTypeService;
    }

    //    public String findallsecond(HttpServletRequest req, HttpServletResponse resp) {
//        try {
//            List<TbGoodsType> goodsTypeList = goodsTypeService.findAllSecondLevelType();//查询所有的二级分类
//            System.err.println(goodsTypeList);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    @RequestMapping(("/add"))
    public String add(HttpServletRequest request, HttpServletResponse response) {
        try {
            goodsTypeService.addType(request.getParameter("name"), request.getParameter("Parent"));
            //更新 context 中的缓存数据
            List<TbGoodsType> goodsTypeList = (List<TbGoodsType>) request.getServletContext().getAttribute("goodsTypeList");
            List<TbGoodsType> allTypes = goodsTypeService.findAllSecondLevelType();
            goodsTypeList.clear();//清除原先的数据,一定要先清除,因为 list 允许重复
            goodsTypeList.addAll(allTypes);//重新添加
            return "redirect:/admin/showGoodsType.jsp";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/error/error.jsp";

    }
    @RequestMapping(("/delete"))
    public String delete(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");//要删除的 id
        int i = goodsTypeService.deleteType(id);
        if (i == -1) {
            response.setContentType("text/html;charset=utf-8");
            //不允许删除
            return "不允许删除非空节点";
        }

        try {
            //清空缓存, 重新查询
            List<TbGoodsType> goodsTypeList = (List<TbGoodsType>) request.getServletContext().getAttribute("goodsTypeList");
            List<TbGoodsType> allTypes = goodsTypeService.findAllSecondLevelType();
            goodsTypeList.clear();//清除原先的数据,一定要先清除,因为 list 允许重复
            goodsTypeList.addAll(allTypes);//重新添加
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/admin/showGoodsType.jsp";

    }

    @RequestMapping(("/findAll"))
    public String findAll(HttpServletRequest request, HttpServletResponse response) {

        List<TbGoodsType> allTypes = (List<TbGoodsType>) request.getServletContext().getAttribute("goodsTypeList");
        if (allTypes == null || allTypes.size() == 0) {
            try {
                allTypes = goodsTypeService.findAllSecondLevelType();
                request.getServletContext().setAttribute("goodsTypeList", allTypes);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String json = gson.toJson(allTypes);
//        System.err.println(json);
        response.setContentType("application/json;charset=utf-8");
        return json;
    }
}
