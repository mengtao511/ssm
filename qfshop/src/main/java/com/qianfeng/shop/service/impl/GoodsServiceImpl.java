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


import com.qianfeng.shop.dao.GoodsDao;
import com.qianfeng.shop.dao.impl.GoodsDaoImpl;
import com.qianfeng.shop.pojo.PageBean;
import com.qianfeng.shop.pojo.TbGoods;
import com.qianfeng.shop.pojo.TbGoodsKz;
import com.qianfeng.shop.pojo.TbOrder;
import com.qianfeng.shop.service.GoodsService;

import java.util.List;

/**
 * Created by jackiechan on 2021/9/15 17:14
 *
 * @author jackiechan
 * @version 1.0
 * @since 1.0
 */

public class GoodsServiceImpl implements GoodsService {

    private GoodsDao goodsDao = new GoodsDaoImpl();
    @Override
    public void addGoods(TbGoodsKz tbGoodsKz) throws Exception {
        //先校验数据,判空,格式判断

        //1 将数据添加到快照表, 得到一个快照 id
        long kzid = goodsDao.addGoodsKz(tbGoodsKz);
        //2 将快照 id 插入到商品表,得到一个商品 id
        long itemid = goodsDao.addGoods(kzid);

        System.err.println("快照 id===>" + kzid + "-- 商品 id=====>" + itemid);
    }


    @Override
    public PageBean<TbGoods> getGoodsPageByTypeId(int typeId, int currentPage) throws Exception {
        PageBean<TbGoods> pageBean = new PageBean<>();
        //查询所有的数量
        int goodsCountByTypeId = goodsDao.getGoodsCountByTypeId(typeId);
        //先算出来当前应该加载的数据是哪些先算出来当前应该加载的数据是哪些,然后进行查询 sql
        int pageSize = pageBean.getPageSize();//每页的数量
        List<TbGoods> goodsList = goodsDao.getGoodsByTypeIdAndLimit(typeId, (currentPage - 1) * pageSize, pageSize);
        pageBean.setTotalPage(goodsCountByTypeId);//设置总条数
        pageBean.setList(goodsList);//设置查询到数据
        System.err.println(goodsCountByTypeId);
        return pageBean;
    }

    @Override
    public TbGoods getGoodById(int id) {
        if (id<=0) {
            //id 不存在,不查询
            return null;
        }
        return goodsDao.getGoodById(id);
    }




}
