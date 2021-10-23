package com.qianfeng.shop.filters;


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


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by jackiechan on 2021/9/14 15:07
 *
 * @author jackiechan
 * @version 1.0
 * @since 1.0
 */
@WebFilter("/*")
public class MyLoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //这个对一些地址进行登录过滤,比如需要登录才能访问的进行未登录的拦截, 有些不允许在登录状态下访问的进行登录拦截
        String uri = ((HttpServletRequest) servletRequest).getRequestURI();
        if (uri.contains("login.jsp")) {
            //如果在登录的情况下访问登录页面,我们将拦截到首页
            if (((HttpServletRequest) servletRequest).getSession().getAttribute("loginUser") != null) {
                //登录了 拦截
                ((HttpServletResponse) servletResponse).sendRedirect("/index.jsp");
                return;
            }

        } else if (uri.contains("/cart")||uri.contains("/order")) {//所有和购物车相关的操作都必须要登录
            if (((HttpServletRequest) servletRequest).getSession().getAttribute("loginUser") == null) {
                //没有登录.跳转到登录的页面
                ((HttpServletResponse) servletResponse).sendRedirect("/login.jsp");
                return;
            }
        }

        filterChain.doFilter(servletRequest,servletResponse);
    }
}
