package com.qianfeng.shop.utils;


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


import com.qianfeng.shop.pojo.TbUser;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/**
 * Created by jackiechan on 2021/9/14 09:07
 *
 * @author jackiechan
 * @version 1.0
 * @since 1.0
 */

public class MailUtils {


    public static void sendMail(TbUser user) throws MessagingException {
//        try {
//            Thread.sleep(30000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        //获取邮箱的会话
        //我们需要知道登陆的邮箱是哪个,因为邮箱实在太多了
        Properties properties = new Properties();
        properties.setProperty("mail.host","smtp.qq.com");
        properties.setProperty("mail.transport.protocol","smtp");//协议格式 smtp
        properties.setProperty("mail.smtp.auth","true");//当前邮箱要发送邮件需要认证(登录)
        Session session = Session.getDefaultInstance(properties);
        session.setDebug(true);//开启调试模式,会显示日志
        //创建邮件,需要用到用户名密码,收件人, 标题, 内容等信息
        Message message =new MimeMessage(session);///创建邮件
        message.setFrom(new InternetAddress("5433708@qq.com"));//设置发送者
        message.setSubject("欢迎注册就骗你网");//邮件的主题
        message.addRecipient(Message.RecipientType.TO,new InternetAddress(user.getEmail()));//以用户的邮箱作为收件人
        message.setContent("欢迎注册就骗你网,本网站本着不骗你骗谁的原则,感谢您为诈骗事业做出的伟大贡献,就骗你网温馨提醒" +
                ",请下载反诈中心 app,点击下面链接让我们骗你 http://localhost:8080/active?username="+user.getUsername()+"&code="+user.getCode(),"text/html;charset=utf-8");
        message.setSentDate(new Date());//立即发送
        message.saveChanges();//保存数据

        Transport transport = session.getTransport();
        //gsmndfkbdhnscbde
        transport.connect("5433708@qq.com","gsmndfkbdhnscbde");//设置账号和密码
        transport.sendMessage(message,message.getAllRecipients());//发送
        transport.close();
    }

}
