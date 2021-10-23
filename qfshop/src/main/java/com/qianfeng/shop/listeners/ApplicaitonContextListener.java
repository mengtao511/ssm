package com.qianfeng.shop.listeners;


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


import com.qianfeng.shop.utils.ExcutorsUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.concurrent.Executors;

/**
 * Created by jackiechan on 2021/9/14 10:12
 *
 * @author jackiechan
 * @version 1.0
 * @since 1.0
 */
@WebListener
public class ApplicaitonContextListener  implements ServletContextListener {
    /**
     * 程序启动完成
     * @param sce
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //程序启动的时候创建一个长度为 10 的线程池
        System.err.println("创建线程池了");
        ExcutorsUtils.setExecutorService(Executors.newScheduledThreadPool(10));
    }

    /**
     * 程序要关闭,应该销毁连接池
     * @param sce
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.err.println("销毁线程池了");
        ExcutorsUtils.shutdown();
    }
}
