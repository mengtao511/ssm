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


import com.alibaba.druid.util.StringUtils;
import com.qianfeng.shop.dao.AddressDao;
import com.qianfeng.shop.pojo.TbAddress;
import com.qianfeng.shop.utils.DBUtils;
import com.qianfeng.shop.utils.SqlUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jackiechan on 2021/9/18 09:55
 *
 * @author jackiechan
 * @version 1.0
 * @since 1.0
 */

public class AddressDaoImpl  implements AddressDao {

    private QueryRunner queryRunne = new QueryRunner(DBUtils.getSource());

    @Override
    public List<TbAddress> findAllAddresByUid(long uid) {
        String sql = SqlUtils.getSql("address.selectbyuid");
        try {
            List<TbAddress> addressList = queryRunne.query(sql, new BeanListHandler<>(TbAddress.class), uid);
            return addressList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    @Override
    public TbAddress findAddress(long uid,String id){
        String sql = SqlUtils.getSql("address.find");
        try {
            TbAddress tbAddress = queryRunne.query(sql, new BeanHandler<>(TbAddress.class), uid, id);
            return tbAddress;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void addAddress(TbAddress tbAddress) {
        String sql = SqlUtils.getSql("address.add");
        try {
            queryRunne.insert(sql,new ScalarHandler<>(),tbAddress.getDetail(),tbAddress.getName(),tbAddress.getPhone(),tbAddress.getUid(),tbAddress.getLevel());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateAddress(TbAddress tbAddress) {
        //sql 需要动态生成,只有传递了参数的列才拼接
        //update xxx set xxx= xx xxxx=xxx,xxxx=xxx
        StringBuffer sqlBuffer = new StringBuffer();

        List params = new ArrayList();//保存参数的集合

        if (!StringUtils.isEmpty(tbAddress.getDetail())) {
            sqlBuffer.append("detail=?,");
            params.add(tbAddress.getDetail());//只有需要更新的列才会拼接具体值,先保存起来,后面挨个放
        }
        if (!StringUtils.isEmpty(tbAddress.getName())) {
            sqlBuffer.append("name=?,");
            params.add(tbAddress.getName());
        }
        if (!StringUtils.isEmpty(tbAddress.getPhone())) {
            sqlBuffer.append("phone=?,");
            params.add(tbAddress.getPhone());
        }
        if (tbAddress.getLevel()>=0) {
            sqlBuffer.append("level=?,");
            params.add(tbAddress.getLevel());
        }
        //然后拼接一下前半部分,还缺少后面的条件部分
        String setsql = sqlBuffer.toString();
        if (!StringUtils.isEmpty(setsql)) {
            setsql = setsql.substring(0, setsql.lastIndexOf(","));
            setsql = "UPDATE tb_address set " + setsql + " where uid=? and id=?";//拼接的 sql
            params.add(tbAddress.getUid());
            params.add(tbAddress.getId());

            try {
                queryRunne.update(setsql, params.toArray());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void deleteAddress(long uid ,long id) {
        String sql = SqlUtils.getSql("address.delete");
        try {
            queryRunne.update(sql,uid,id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
