package com.qianfeng.shop.dao.impl;


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
import com.qianfeng.shop.pojo.TbOrder;
import com.qianfeng.shop.utils.DBUtils;
import com.qianfeng.shop.utils.SqlUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by jackiechan on 2021/9/18 11:09
 *
 * @author jackiechan
 * @version 1.0
 * @since 1.0
 */

public class OrderDaoImpl implements OrderDao {

    private QueryRunner queryRunner = new QueryRunner(DBUtils.getSource());
    @Override
    public void addOrder(String oid,long uid, long money, String aid) {
        String sql = SqlUtils.getSql("order.add");
        try {
           queryRunner.insert(sql, new ScalarHandler<>(), oid,uid, money, aid);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public long addOrderDetail(String oid, long kzid, int num, long money) {
        String sql = SqlUtils.getSql("orderdetail.add");
        try {
           long detailId= queryRunner.insert(sql, new ScalarHandler<>(), oid, kzid, num,money);
            return detailId;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void updateStaus2Success(String id) {
        String sql = SqlUtils.getSql("order.paysuccess");
        try {
            queryRunner.update(sql, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String findStatusById(String id) {
        String sql =SqlUtils.getSql("order.status");
        try {
            String result = queryRunner.query(sql, new ScalarHandler<>(), id);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<TbOrder> findOrderByUid(long uid) {
        String sql = SqlUtils.getSql("order.allfind");
        try {
            List<TbOrder> orderList = queryRunner.query(sql, new BeanListHandler<>(TbOrder.class), uid);
            return orderList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteOrderByAid(String aid) {
        String sql = SqlUtils.getSql("deleteorder.aid");
        try {
            queryRunner.update(sql,aid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteOrderById(String id) {
        String sql = SqlUtils.getSql("deleteorder.id");
        try {
            queryRunner.update(sql,id);
            return;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
