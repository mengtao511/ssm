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


import com.qianfeng.shop.dao.CartDao;
import com.qianfeng.shop.pojo.CartItem;
import com.qianfeng.shop.utils.DBUtils;
import com.qianfeng.shop.utils.SqlUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jackiechan on 2021/9/17 10:20
 *
 * @author jackiechan
 * @version 1.0
 * @since 1.0
 */

public class CartDaoImpl implements CartDao {
    private QueryRunner queryRunner = new QueryRunner(DBUtils.getSource());

    @Override
    public Long findCountByUidAndPid(String pid, long uid) {
        String sql = SqlUtils.getSql("cart.selectcount");
        long count = 0;
        try {
            count = queryRunner.query(sql, new ScalarHandler<>(), uid, pid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public void addCart(String pid, long uid, int num) throws SQLException {
        String sql = SqlUtils.getSql("cart.add");
        queryRunner.insert(sql, new ScalarHandler<>(), uid, pid, num);
    }

    @Override
    public int updateNum(String pid, long uid, int num) throws SQLException {
        String sql = SqlUtils.getSql("cart.updatenum");
        return queryRunner.update(sql, num, uid, pid);
    }

    @Override
    public List<CartItem> findCartByUid(long uid) {
        String sql = SqlUtils.getSql("cart.selectbyuid");
        try {
            List<CartItem> cartItemList = queryRunner.query(sql, new BeanListHandler<>(CartItem.class), uid);
            return cartItemList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public void deleteByUid(long uid) {
        String sql = SqlUtils.getSql("cart.delete");
        try {
            queryRunner.update(sql, uid);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
