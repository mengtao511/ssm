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


import com.qianfeng.shop.cache.GoodTypeCache;
import com.qianfeng.shop.pojo.TbGoodsType;
import com.qianfeng.shop.service.GoodsTypeService;
import com.qianfeng.shop.service.impl.GoodsTypeServiceImpl;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by jackiechan on 2021/9/14 10:12
 *
 * @author jackiechan
 * @version 1.0
 * @since 1.0
 */
@WebListener
public class LoadTypeCacheContextListener implements ServletContextListener {
    private GoodsTypeService goodsTypeService = new GoodsTypeServiceImpl();


    /**
     * 程序启动完成
     * @param sce
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            System.err.println("加载分类缓存");
            List<TbGoodsType> goodsTypeList = goodsTypeService.findAllSecondLevelType();
            GoodTypeCache.setAllSecondTypes(goodsTypeList);
            sce.getServletContext().setAttribute("goodsTypeList",GoodTypeCache.getAllSecondTypes());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * 程序要关闭,清空缓存
     * @param sce
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        GoodTypeCache.clearCache();
    }
}
