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


import java.util.HashMap;
import java.util.Map;

/**
 * Created by jackiechan on 2021/9/2 15:10
 *
 * @author jackiechan
 * @version 1.0
 * @since 1.0
 */

public class RequestMappingUtils {



    private static Map<String, Object> targetObjectMap = new HashMap<>();

    private static Map<String, String> url2MethodMap = new HashMap<>();


    public static String getMethod(String key) {
        return url2MethodMap.get(key);
    }

    public static String addMethod(String url,String methodName) {
        return url2MethodMap.put(url, methodName);
    }


    public static Object getTarget(String uri) {
        return targetObjectMap.get(uri);
    }



    public static Object addTarget(String uri,Object target) {
        return targetObjectMap.put(uri, target);
    }
}
