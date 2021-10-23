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
import com.qianfeng.shop.utils.RequestMappingUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * Created by jackiechan on 2021/9/16 09:19
 *
 * @author jackiechan
 * @version 1.0
 * @since 1.0
 */

/**
 * / 和/*的的区别 /不会拦截 jsp, /*会拦截 jsp
 */
//@WebServlet("/")
//public class MyBaseServlet extends HttpServlet {
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        doPost(req, resp);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//
//        try {
//            //返回结果是通过调用某个类中的某个方法来获取的,这个类和方法现在我们写死了,也就是说当我们请求/base这个地址的时候我们自己期望执行的BaseComtroller类中的 process 方法,写死了
//            //如果我要是不写死,可以通过某种机制找到这个方法然后执行就好了,我们现在要通过地址找方法,典型的 key-value ,key 是地址,value 是方法,我如果有个地方,保存了地址和方法直接的关系,我是不是就可以直接找到方法了,然后就可以执行了
//            // String process = new BaseController().process(req, resp);
//            String uri = req.getRequestURI();//获取实际的请求地址
//            System.err.println(uri);
//            String methodName = RequestMappingUtils.getMethod(uri);//在根据实际的请求地址去查找配置文件找到我们要执行的方法
//            //有了方法名要执行,反射我们学过方法的调用
//            //反射要找到方法并调用 需要几个条件,第一知道是哪个类的方法,第二必须知道是哪个对象在调用,第三必须知道参数
//            String className = methodName.substring(0, methodName.lastIndexOf("."));//将方法名裁剪,最后一个.前面的就是类名
//            Class<?> targetClass = Class.forName(className);//得到要执行的方法的类
//
//            Object target = RequestMappingUtils.getTarget(uri);// 从保存执行对象的地方获取对象
//
//            if (target == null) { //如果没有就创建
//                target = targetClass.newInstance();//创建要执行方法的对象, 我们发现一个问题,这个对象每次请求都会创建,但是这个对象实际上只需要一个,我们应该只创建一次,如果没有创建就创建,创建了就不创建
//                //保存起来,这样下次就不用再创建了
//                System.err.println("第一次请求,没有对象,创建对象");
//                RequestMappingUtils.addTarget(uri, target);//保存起来,下次就不需要创建了
//            }
//            //反射创建方法对象,最后一个.后面的就是方法名
//            Method targetMethod = targetClass.getMethod(methodName.substring(methodName.lastIndexOf(".") + 1), HttpServletRequest.class, HttpServletResponse.class);
//            String process = (String) targetMethod.invoke(target, req, resp);//执行方法并返回结果
//            //经过测试 我们的返回结果可以封装
//            if (!StringUtils.isEmpty(process)) {
//                if (process.startsWith("forward:")) {//forward:/user/aaa.jsp
//                    req.getRequestDispatcher(process.replace("forward:", "")).forward(req, resp);
//                } else if (process.startsWith("redirect:")) {
//                    resp.sendRedirect(process.replace("redirect:", ""));
//                } else {
//                    resp.getWriter().write(process);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            resp.sendError(404);//出现异常,我们可以认为是代码没找到地址,返回404
//        }
//    }
//
//
//}
