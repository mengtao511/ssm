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
import com.qianfeng.shop.exceptions.MyBaseException;
import com.qianfeng.shop.pojo.TbUser;
import com.qianfeng.shop.service.UserService;
import com.qianfeng.shop.service.impl.UserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by jackiechan on 2021/9/13 10:24
 *
 * @author jackiechan
 * @version 1.0
 * @since 1.0
 */
@RequestMapping("/user")
public class UserController {

    private UserService userService = new UserServiceImpl();


    /**
     * 退出
     *
     * @param req
     * @param resp
     */
    @RequestMapping("/logout")
    public String logOut(HttpServletRequest req, HttpServletResponse resp) {
        //清除用户的状态数据,重定向到首页或者登录页面
        req.getSession().invalidate();//过期
        return "redirect:/index.jsp";
    }


    /**
     * 登录操作
     *
     * @param req
     * @param resp
     */
    @RequestMapping("/login")
    public String login(HttpServletRequest req, HttpServletResponse resp) {

        //登录必须传递账号和密码

        //1 先获取参数,我们必须的参数 账号和密码
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String code = req.getParameter("code");//传递的验证码
        //获取到我们保存的验证码
        String savecode = (String) req.getSession().getAttribute("code");
        if (StringUtils.isEmpty(code) || !code.equalsIgnoreCase(savecode)) {
            //传递的验证码是空的或者是两个不一样,则代表验证码不一致
            resp.setContentType("text/html;charset=utf-8");
            return "验证码错误";
        }

        //2 调用业务查询用户数据
        try {
            TbUser user = userService.login(username, password);
            if (user == null) {
                resp.setContentType("text/html;charset=utf-8");
                return "用户名或密码错误";

            }
            //不为空说明是对的,保存状态,保存在 request 还是 session 中?
            req.getSession().setAttribute("loginUser", user);
            return "redirect:/index.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error/error.jsp";
        }

    }


    /**
     * 注册
     *
     * @param req
     * @param resp
     * @throws IOException
     */
    @RequestMapping("/register")
    public String register(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //1. 获取参数,参数的名字我们已经定义好了,格式也是普通的格式,所以直接获取就可以了
        try {
            TbUser user = new TbUser();
            Map<String, String[]> parameterMap = req.getParameterMap();//获取所有参数的键值对的集合
            BeanUtils.populate(user, parameterMap);//复制map 中的数据到对象对应的属性中,如果不出问题,则参数中的数据会保存到对象中
            //数据现在已经接收完成
            //System.err.println("收到的信息是:"+user);
            //因为用户还传递了重复密码, 防止两次密码不一致,所以我们应也要校验一下, 都放到 service 稍微麻烦,因为需要额外传递一个参数
            //所以重复密码的判断我们写在 servlet
            String repeatpassword = req.getParameter("repeatpassword");//传递的重复密码
            if (StringUtils.isEmpty(repeatpassword) || !repeatpassword.equals(user.getPassword())) {
                //没有传递重复密码,或者两次输入 不一致,则提示错误,
                req.setAttribute("msg", "两次输入的密码不一致");
                return "forward:/error/fail.jsp";
            }
            //代码执行到这里 意味着密码已经校验成功,进行后续的操作,交给业务处理
            userService.addUser(user);
            //因添加操作天然是非幂等的操作,我们又不允许刷新后多一条数据,所以我们的要求添加是幂等的,那么就必须重定向页面
            return "redirect:/registerSuccess.jsp";
        } catch (MyBaseException e) {
            e.printStackTrace();
            req.getSession().setAttribute("msg", e.getMessage());
            return "redirect:/error/fail.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error/error.jsp";
        }
    }
}
