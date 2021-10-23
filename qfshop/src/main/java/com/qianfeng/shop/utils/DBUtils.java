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


import com.alibaba.druid.pool.DruidDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by jackiechan on 2021/8/31 14:54
 *
 * @author jackiechan
 * @version 1.0
 * @since 1.0
 */

public class DBUtils {

    private static DataSource source;

    public static DataSource getSource() {
        return source;
    }

    static {
        try {
            InputStream inputStream = DBUtils.class.getResourceAsStream("/db.properties");
            Properties properties = new Properties();
            properties.load(inputStream);
            source = new DruidDataSource();
            ((DruidDataSource) source).setUsername(properties.getProperty("jdbc.username"));
            ((DruidDataSource) source).setPassword(properties.getProperty("jdbc.password"));
            ((DruidDataSource) source).setUrl(properties.getProperty("jdbc.url"));
            ((DruidDataSource) source).setDriverClassName(properties.getProperty("jdbc.driver"));
            ((DruidDataSource) source).setMaxActive(20);
            ((DruidDataSource) source).setMinIdle(3);
            ((DruidDataSource) source).setInitialSize(10);
            ((DruidDataSource) source).setMaxWait(5000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
