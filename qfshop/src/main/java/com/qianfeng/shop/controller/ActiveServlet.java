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


import com.alibaba.druid.util.StringUtils;
import com.qianfeng.shop.annotations.RequestMapping;
import com.qianfeng.shop.service.UserService;
import com.qianfeng.shop.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by jackiechan on 2021/9/14 09:46
 *
 * @author jackiechan
 * @version 1.0
 * @since 1.0
 */
@RequestMapping("/active")
@RestController
public class ActiveServlet extends HttpServlet {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String code = req.getParameter("code");
        resp.setContentType("text/html;charset=utf-8");
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(code)) {
            //没有传递必须的参数
            resp.getWriter().write("玩呢?参数呢?");
            return;
        }

        //调用用户的业务,更新用户的状态
        try {
            int active = userService.active(username, code);
            if (active == 1) {
                resp.getWriter().write("激活成功,请开启您的被诈骗之旅");
            }else{
                resp.getWriter().write("激活失败,再重新试试,快点,我们着急诈骗你");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("/error/error.jsp");
        }

    }
}
