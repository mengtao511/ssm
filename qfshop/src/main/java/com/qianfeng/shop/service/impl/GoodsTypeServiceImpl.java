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


import com.alibaba.druid.util.StringUtils;
import com.qianfeng.shop.dao.GoodsTypeDao;
import com.qianfeng.shop.dao.impl.GoodsTypeDaoImpl;
import com.qianfeng.shop.pojo.TbGoodsType;
import com.qianfeng.shop.service.GoodsTypeService;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by jackiechan on 2021/9/15 09:25
 *
 * @author jackiechan
 * @version 1.0
 * @since 1.0
 */

public class GoodsTypeServiceImpl  implements GoodsTypeService {

    private GoodsTypeDao goodTypeDao = new GoodsTypeDaoImpl();

    @Override
    public List<TbGoodsType> findAllSecondLevelType() throws SQLException {
        return goodTypeDao.findAllSecondLevelType();
    }

    @Override
    public void addType(String name, String parent) throws Exception {
        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(parent)) {
            throw new RuntimeException();
        }
        goodTypeDao.addType(name, parent);

    }

    @Override
    public int deleteType(String id) {
        //删除分类,但是分类会有子分类,所以我们需要确定删除父分类的时候需要怎么办
        //比如如果还有正常的子类,则不允许删除
        //或者删除的时候顺便删除所有的子类
        //此处我们选择不允许删除
        //1 先查询有没有正常的子分类
        List<TbGoodsType> ids = findTypeByPIdAndStaus(id, 1);
        if (ids == null||ids.size()==0) {
            //代表没有数据,可以删除
            return goodTypeDao.deleteType(id);
        }
        return -1;// -1代表不允许删除
    }

    @Override
    public List<TbGoodsType> findTypeByPIdAndStaus(String pid, int status) {
        return goodTypeDao.findIdByPIdAndStaus(pid,status);
    }
}
