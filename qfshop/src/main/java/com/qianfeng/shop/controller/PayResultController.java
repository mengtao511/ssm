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
import com.qianfeng.shop.pojo.WXPayResult;
import com.qianfeng.shop.service.OderService;
import com.qianfeng.shop.service.impl.OderServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by jackiechan on 2021/9/18 14:05
 *
 * @author jackiechan
 * @version 1.0
 * @since 1.0
 */
@RequestMapping("/payment")
public class PayResultController {
    private Gson gson = new Gson();

    private OderService oderService = new OderServiceImpl();

    @RequestMapping("/payWeixin")
    public String startPay(HttpServletRequest request, HttpServletResponse response) {
        String oid = request.getParameter("oid");
        String url = "http://localhost:8080/payment/process";
        String price = "1";//支付一分钱
        String body = "wyf";
        String realpath = "http://ceshi.qfjava.cn:81/payment/weixinpay?orderId=" + oid + "&price=" + price + "&body=" + body + "&url=" + url;
        return "redirect:" + realpath;
    }



    @RequestMapping("/process")
    public String processPayResult(HttpServletRequest request, HttpServletResponse response) {
        String result = request.getParameter("result");
        System.err.println("支付的结果:"+result);
        WXPayResult wxPayResult = gson.fromJson(result, WXPayResult.class);
        String out_trade_no = wxPayResult.getResult().getOut_trade_no();//我们的订单号
        String result_code = wxPayResult.getResult().getResult_code();
        if ("success".equalsIgnoreCase(result_code)) {
            //支付成功,更新订单的状态
            oderService.processPay(out_trade_no);
        }

        return "ok";
    }


}
