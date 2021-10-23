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


import javax.servlet.http.HttpServletRequest;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

/**
 * Created by jackiechan on 2021/9/2 11:25
 * 这个工具类的作用是将请求中的参数封装到对象中,要求请求参数的名字必须和对象的属性名保持一致,因为这样就有规律了
 *
 * @author jackiechan
 * @version 1.0
 * @since 1.0
 */

public class BeanUtils {

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static <T> T processParamters(HttpServletRequest request, Class<T> targetClass) throws Exception {
        //要求请求参数的名字必须和对象的属性名保持一致
        //实际上我们的操作就是获取到每一个参数,然后根据参数名找到类中的 set 方法,然后把参数的值设置过去\
        //比如 我们找到了 username 这个参数,要从类中找 setUsername()
        Enumeration<String> parameterNames = request.getParameterNames();//获取到所有的参数名
        T instance = targetClass.newInstance();//通过反射创建对象
        while (parameterNames.hasMoreElements()) {
            String name = parameterNames.nextElement();//获取到了每一个参数名
            String value = request.getParameter(name);//获取到这个参数对应的值
            if ("option".equals(name)) {
                //判断操作方式的参数,没有必要封装
                continue;
            }
//            char c = name.charAt(0);
//
//            Method method = targetClass.getMethod("set" + name.replaceFirst(c, ));
//            method.invoke(instance, value);
            //从指定类中找当前遍历到的参数名对应的属性描述
            PropertyDescriptor propertyDescriptor = new PropertyDescriptor(name, targetClass);
            if (propertyDescriptor != null) {
                //找到这个属性了
                //  Method readMethod = propertyDescriptor.getReadMethod();//get方法
                Method writeMethod = propertyDescriptor.getWriteMethod();//set 方法
                if (writeMethod != null) {
                    Class<?>[] parameterTypes = writeMethod.getParameterTypes();//获取 set 方法的参数类型,因为我们发现是 string 不报错,但是其他类型报错,需要单独处理一下
                    if (parameterTypes.length > 0) {
                        Class<?> parameterType = parameterTypes[0];//获取到具体的方法参数
                        if (parameterType == Date.class) {
                            //因为字节码文件只有一份,所以不管怎么获取都是一份 class,所以在这里可以通过==来判断 class
                            Date date = simpleDateFormat.parse(value);
                            //有 set 方法
                            writeMethod.invoke(instance, date);//将值设置过去
                            continue;//跳出当前循环,继续下一个循环
                        }

                        //String 类型可以直接赋值
                        if (parameterType == String.class) {
                            //有 set 方法
                            writeMethod.invoke(instance, value);//将值设置过去
                            continue;//跳出当前循环,继续下一个循环
                        }


                        if (parameterType == Integer.class || parameterType == int.class) {
                            //有 set 方法

                            try {
                                int i = Integer.parseInt(value);
                                writeMethod.invoke(instance, i);//将值设置过去
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            continue;//跳出当前循环,继续下一个循环
                        }


                        if (parameterType == Long.class || parameterType == long.class) {
                            //有 set 方法

                            try {
                                long i = Long.parseLong(value);
                                writeMethod.invoke(instance, i);//将值设置过去
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            continue;//跳出当前循环,继续下一个循环
                        }
                    }
                }
            }
        }

        return instance;
    }
}
