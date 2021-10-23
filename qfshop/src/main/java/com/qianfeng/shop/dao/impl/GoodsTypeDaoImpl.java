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


import com.qianfeng.shop.dao.GoodsTypeDao;
import com.qianfeng.shop.pojo.TbGoodsType;
import com.qianfeng.shop.utils.DBUtils;
import com.qianfeng.shop.utils.SqlUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by jackiechan on 2021/9/15 09:18
 *
 * @author jackiechan
 * @version 1.0
 * @since 1.0
 */

public class GoodsTypeDaoImpl implements GoodsTypeDao {
    private QueryRunner runner = new QueryRunner(DBUtils.getSource());

    @Override
    public List<TbGoodsType> findAllSecondLevelType() throws SQLException {
        String sql = SqlUtils.getSql("goodstype.findallsecond");
        List<TbGoodsType> goodsTypeList = runner.query(sql, new BeanListHandler<>(TbGoodsType.class));
        return goodsTypeList;
    }

    @Override
    public void addType(String name, String parent) throws SQLException {
        String sql = "INSERT INTO tb_goods_type (name,level,Parent) VALUES(?,0,?)";
        runner.update(sql, name, parent);
    }
    /**
     * 删除数据,实际上是更新状态
     *
     * @param id
     * @return
     */
    @Override
    public int deleteType(String id) {
        String sql = "UPDATE tb_goods_type SET status= 0 WHERE id =?";
        try {
            return runner.update(sql, id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<TbGoodsType> findIdByPIdAndStaus(String pid, int status) {
        String sql = "SELECT id FROM tb_goods_type WHERE Parent=? AND status=?";
        List<TbGoodsType> typeList = null;
        try {
            typeList = runner.query(sql, new BeanListHandler<>(TbGoodsType.class), pid, status);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return typeList;
    }
}
