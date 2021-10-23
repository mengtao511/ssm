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


import com.qianfeng.shop.dao.UserDao;
import com.qianfeng.shop.pojo.TbUser;
import com.qianfeng.shop.utils.DBUtils;
import com.qianfeng.shop.utils.SqlUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;

/**
 * Created by jackiechan on 2021/9/13 15:03
 *
 * @author jackiechan
 * @version 1.0
 * @since 1.0
 */

public class UserDaoImpl implements UserDao {


    private QueryRunner queryRunner = new QueryRunner(DBUtils.getSource());

    @Override
    public void addUser(TbUser user) throws SQLException {
        String sql = SqlUtils.getSql("user.add");
        long result = queryRunner.insert(sql, new ScalarHandler<>(), user.getUsername(), user.getPassword(), user.getNickname(), user.getEmail(), user.getGender(), user.getCode());
        System.err.println(result);
    }

    @Override
    public int active(String username, String code) throws Exception {
        String sql = SqlUtils.getSql("user.active");
        int result = queryRunner.update(sql, username, code);
        return result;
    }

    @Override
    public TbUser findUserByName(String username) throws Exception {
        String sql = SqlUtils.getSql("user.find.by.name");
        return queryRunner.query(sql, new BeanHandler<>(TbUser.class), username);
    }
}
