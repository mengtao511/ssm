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


import com.qianfeng.shop.dao.GoodsDao;
import com.qianfeng.shop.pojo.TbGoods;
import com.qianfeng.shop.pojo.TbGoodsKz;
import com.qianfeng.shop.utils.DBUtils;
import com.qianfeng.shop.utils.SqlUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by jackiechan on 2021/9/15 17:20
 *
 * @author jackiechan
 * @version 1.0
 * @since 1.0
 */

public class GoodsDaoImpl implements GoodsDao {

    private QueryRunner queryRunner = new QueryRunner(DBUtils.getSource());

    @Override
    public long addGoodsKz(TbGoodsKz tbGoodsKz) throws SQLException {
        String sql = SqlUtils.getSql("goodskz.add");
        long id = queryRunner.insert(sql, new ScalarHandler<>(), tbGoodsKz.getName(), tbGoodsKz.getPubdate(), tbGoodsKz.getPicture(), tbGoodsKz.getPrice(), tbGoodsKz.getStar(), tbGoodsKz.getIntro(), tbGoodsKz.getTypeid());
        return id;
    }

    @Override
    public long addGoods(long kzid) throws SQLException {
        String sql = SqlUtils.getSql("goods.add");
        long id = queryRunner.insert(sql, new ScalarHandler<>(), kzid);
        return id;
    }
    @Override
    public int getGoodsCountByTypeId(int typeId) {
        try {
            String sql = "SELECT count(*) count from tb_goods tg LEFT JOIN tb_goods_kz tgz on tg.kzid =tgz.id  where typeid  =?";
            Map<String, Object> map = queryRunner.query(sql, new MapHandler(), typeId);
            //System.err.println(map);
            return ((Long) map.get("count")).intValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<TbGoods> getGoodsByTypeIdAndLimit(int typeid, int start, int rows) {

        try {
//            String sql = "SELECT  * FROM tb_goods WHERE typeid =? limit ?,?";
            //快照表的 id 不能返回,因为会被当做商品的 id 封装到对象中,所以我们返回了部分字段
            //解决方式 2, 将快照表的 id 变个名字
            String sql = "SELECT tg.*,tgz.name,tgz.pubdate,tgz.picture,tgz.price,tgz.star ,tgz.intro,tgz.typeid from tb_goods tg LEFT JOIN tb_goods_kz tgz on tg.kzid =tgz.id  where typeid =? limit ?,?";
            List<TbGoods> goodsList = queryRunner.query(sql, new BeanListHandler<>(TbGoods.class), typeid, start, rows);
            return goodsList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public TbGoods getGoodById(int id) {
        TbGoods goods = null;
        try {
            String sql = "SELECT  tg.*,tgz.name,tgz.pubdate,tgz.picture,tgz.price,tgz.star ,tgz.intro,tgz.typeid FROM tb_goods tg LEFT JOIN tb_goods_kz tgz on tg.kzid =tgz.id  WHERE tg.id=?";
            goods = queryRunner.query(sql, new BeanHandler<>(TbGoods.class), id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return goods;
    }
}
