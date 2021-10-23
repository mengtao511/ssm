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


import com.qianfeng.shop.dao.AddressDao;
import com.qianfeng.shop.dao.impl.AddressDaoImpl;
import com.qianfeng.shop.pojo.TbAddress;
import com.qianfeng.shop.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by jackiechan on 2021/9/18 09:57
 *
 * @author jackiechan
 * @version 1.0
 * @since 1.0
 */

@Service
@Transactional
public class AddressServiceImpl implements AddressService {

    private AddressDao addressDao;

    @Autowired
    public void setAddressDao(AddressDao addressDao) {
        this.addressDao = addressDao;
    }

    @Override
    public List<TbAddress> findAllAddresByUid(long uid) {
        return addressDao.findAllAddresByUid(uid);
    }

    @Override
    public TbAddress findAddress(long uid, String id) {

        return  addressDao.findAddress(uid,id);
    }

    @Override
    public void addAddress(TbAddress tbAddress) {
        addressDao.addAddress(tbAddress);
    }

    @Override
    public void updateAddress(TbAddress tbAddress) {
        addressDao.updateAddress(tbAddress);
    }

    @Override
    public void deleteAddress(long uid ,long id) {
        addressDao.deleteAddress(uid,id);
    }


}
