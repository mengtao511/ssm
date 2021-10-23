package com.qianfeng.shop.service.impl;


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


import com.qianfeng.shop.dao.CartDao;
import com.qianfeng.shop.dao.impl.CartDaoImpl;
import com.qianfeng.shop.pojo.CartItem;
import com.qianfeng.shop.service.CartService;

import java.util.List;

/**
 * Created by jackiechan on 2021/9/17 10:07
 *
 * @author jackiechan
 * @version 1.0
 * @since 1.0
 */

public class CartServiceImpl implements CartService {

    private CartDao cartDao=new CartDaoImpl();

    @Override
    public void addCart(String goodId, long uid, int num) throws Exception {
        //添加真的是添加吗?
        //经过我们测试,我们发现实际的情况应该是如果当前用户没有添加过这个商品到购物车,那么就是添加,如果添加过就是更新
        //包括商品可能会有限购,还有可能商品数量不足以够你添加的数量,还有可能你添加的时候数量是够的,等后面在看的时候数量不够了怎么显示
        //先进行数据的校验

        //首先查询数据库,看看当前用户有没有添加这个商品
        long count = cartDao.findCountByUidAndPid(goodId, uid);
        if (count > 0) {
            //如果有,就更新数量
            cartDao.updateNum(goodId, uid, num);
        }else{
            //如果没有 就添加新数据
            cartDao.addCart(goodId, uid, num);
        }

    }

    @Override
    public List<CartItem> findCartByUid(long uid) {

        return cartDao.findCartByUid(uid);
    }

    @Override
    public void  deleteByUid(long uid){
        cartDao.deleteByUid(uid);
    }
}
