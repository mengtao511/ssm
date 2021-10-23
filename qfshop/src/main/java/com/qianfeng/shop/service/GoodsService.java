package com.qianfeng.shop.service;
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


import com.qianfeng.shop.pojo.PageBean;
import com.qianfeng.shop.pojo.TbGoods;
import com.qianfeng.shop.pojo.TbGoodsKz;
import com.qianfeng.shop.pojo.TbOrder;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by jackiechan on 2021/9/15 09:18
 *
 * @author jackiechan
 * @version 1.0
 * @since 1.0
 */

public interface GoodsService {
    /**
     * 添加商品
     * @param tbGoodsKz
     */
   void  addGoods(TbGoodsKz tbGoodsKz) throws Exception;
    PageBean<TbGoods> getGoodsPageByTypeId(int typeId, int currentPage) throws Exception;

    TbGoods getGoodById(int id);





}
