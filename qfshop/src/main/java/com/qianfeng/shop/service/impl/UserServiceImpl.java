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
import com.qianfeng.shop.dao.UserDao;
import com.qianfeng.shop.dao.impl.UserDaoImpl;
import com.qianfeng.shop.exceptions.MyBaseException;
import com.qianfeng.shop.pojo.TbUser;
import com.qianfeng.shop.service.UserService;
import com.qianfeng.shop.utils.ExcutorsUtils;
import com.qianfeng.shop.utils.MD5Utils;
import com.qianfeng.shop.utils.MailUtils;

import javax.mail.MessagingException;
import java.util.UUID;

/**
 * Created by jackiechan on 2021/9/13 11:29
 *
 * @author jackiechan
 * @version 1.0
 * @since 1.0
 */

public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();

    @Override
    public void addUser(TbUser user) throws Exception {
        long currentTimeMillis = System.currentTimeMillis();//开始业务的时间
        //添加数据之前需要先进行校验,比如用户名密码等是否符合我们的要求
        //校验需要规则,比如用户名的规则,密码的规则, 邮箱的规则
        if (!user.getEmail().matches("^[A-Za-z0-9\\.\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$")) {
            //邮箱不匹配
            System.err.println("邮箱不符合规则");
            throw new MyBaseException("邮箱不符合规则");
            //怎么做?
            //  return;
        }

        if (!user.getPassword().matches("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z\\\\W]{6,18}$")) {
            //密码不匹配
            System.err.println("密码不符合规则");
            throw new MyBaseException("密码不符合规则");
            //   return;
        }
        String password = user.getPassword();//获取用户输入的密码
        //密码用户传递过来是明文,比如是 123456,我们数据库不应该存明文,防止泄露,所以密码需要转换,密码不能加密,密码只能做 hash,当然我们也习惯称之为加密,但是实际上不是加密
        //我们可选的首选 MD 家族和 SHA 家族 ,不管选择 MD还是 SHA 他们都不属于可逆的,只能单向进行,不能反向解密
        //校验的手段 都是同样的策略, 将原始内容按照某种方式生成一个结果保存起来, 下次你给我一个数据,我再按照前面的方式再生成一个结果,然后和保存的结果进行比对,如果比对一直,则代表成功,不一致,则代表失败
        //采用的是消息摘要算法生成的hash 值
        //按照算法要求来说,任何加密手段都必须有解密手段,没有解密的都不属于加密, 加密手段也有,分为对称加密和非对称加密
        //对称加密就是加密密码和解密密码一样的, 常见的有 des  aes-----> wpa/wpa2--->WIFI密码加密方式
        //加密--->xxx锄奸队 地下xxx  xxx 交通站---->电台--->监听--->发的内容让他看不懂--->置换加密--->密码本
        //-->很容易破解--->区块加密
        //非对称加密: RSA 利用大素数进行乘积, 分为一对证书, 公钥,私钥 同时生成的. 公钥公开的,私钥是私有的  ,私钥加密的数据必须使用对应的公钥解密, 公钥加密的数据必须使用对应的私钥解密
        //TODO tomcat 启动非常慢的情况,系统噪声,熵

        //计算 MD5
        String md5 = MD5Utils.md5(password);//将密码转成 MD5
        user.setPassword(md5);//用户的密码变成 MD5 了
        String code = UUID.randomUUID().toString().replaceAll("-", "");
        user.setCode(code);//我们的账号按照需求需要激活,比如通过邮箱激活,我们必须确认你是真的激活而不会随便点一下就可以,所以我们会要求用户传递一个激活码过来,激活码是我们生成的
        userDao.addUser(user);//保存到数据库
        //激活,发邮件,发送邮件需要时间,下面的代码会进入等待, 导致用户等待,因为这些代码在同一个线程中
//        MailUtils.sendMail(user);
//       new Thread(){
//           @Override
//           public void run() {
//               try {
//                   MailUtils.sendMail(user);
//               } catch (MessagingException e) {
//                   e.printStackTrace();
//               }
//           }
//       }.start();
        //通过线程池来进行邮件的发送,异步,响应式编程(反应式), DDD领域驱动,真香
        ExcutorsUtils.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    MailUtils.sendMail(user);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        });
        System.err.println("总共消耗的时间:" + (System.currentTimeMillis() - currentTimeMillis));
    }

    @Override
    public int active(String username, String code) throws Exception {
        return userDao.active(username, code);
    }

    @Override
    public TbUser login(String username, String password) throws Exception {
        //如果连参数都没传递,那么肯定是查不到或者比较错误的,所以在参数不全的情况下,根本没必要请求数据库,以免数据库造成压力
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return null;
        }
        TbUser tbUser = userDao.findUserByName(username);//查询指定用户名的用户
        if (tbUser == null) {//用户名都是空的
            return null;
        }
        //比较密码
        //现在传递的密码是 123456 这种明文, 而数据库中查询出来的是 md5 是无法进行直接比较,需要先将明文转成 MD5
        String md5 = MD5Utils.md5(password);
        if (md5.equals(tbUser.getPassword())) {
            //密码一致,说明输入的信息正确
            return tbUser;
        }
        return null;
    }
}
