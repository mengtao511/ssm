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


import com.qianfeng.shop.dao.OrderDao;
import com.qianfeng.shop.dao.impl.OrderDaoImpl;
import com.qianfeng.shop.pojo.CartItem;
import com.qianfeng.shop.pojo.Order;
import com.qianfeng.shop.pojo.TbOrder;
import com.qianfeng.shop.service.OderService;

import java.util.List;
import java.util.UUID;

/**
 * Created by jackiechan on 2021/9/18 11:13
 *
 * @author jackiechan
 * @version 1.0
 * @since 1.0
 */

public class OderServiceImpl implements OderService {
    private OrderDao orderDao = new OrderDaoImpl();


    @Override
    public Order createOrder(List<CartItem> cartItemList, String aid) {
        if (cartItemList != null && cartItemList.size() > 0) {
            String oid = UUID.randomUUID().toString().replaceAll("-", "");
            //计算总价
            long total = cartItemList.stream().mapToLong(CartItem::getTotalPrice).sum();//整个订单的总价
            Long uid = cartItemList.get(0).getUid();//获取到用户的 id
            orderDao.addOrder(oid, uid, total, aid);//插入到订单表

            cartItemList.forEach(cartItem -> {
                //将每一个商品关联到当前的订单中
                long id = cartItem.getId();//kzid
                long totalPrice = cartItem.getTotalPrice();//当前商品的总价
                int num = cartItem.getNum();//获取数量
                orderDao.addOrderDetail(oid, id, num, totalPrice);
            });

            Order order = new Order();
            order.setMoney(total);
            order.setId(oid);
            return order;
        }
        return null;
    }

    @Override
    public void processPay(String id) {
        //这个订单如果是未支付状态才可以更新为已支付
        String status = orderDao.findStatusById(id);
        if ("0".equalsIgnoreCase(status)) {
            //才可以支付
            orderDao.updateStaus2Success(id);
        }else {
            System.err.println("当前订单状态异常,无法更新为支付状态");
        }
    }

    @Override
    public List<TbOrder> findOrderByUid(long uid) {
        List<TbOrder> orderByUid = orderDao.findOrderByUid(uid);

        return orderByUid;
    }

    @Override
    public void deleteOrderByAid(String aid) {
        orderDao.deleteOrderByAid(aid);
    }

    @Override
    public void deleteOrderById(String id) {
        orderDao.deleteOrderById(id);
    }
}
