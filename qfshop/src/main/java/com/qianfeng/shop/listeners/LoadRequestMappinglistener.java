package com.qianfeng.shop.listeners;


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


import com.qianfeng.shop.annotations.RequestMapping;
import com.qianfeng.shop.utils.RequestMappingUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

/**
 * Created by jackiechan on 2021/9/16 16:45
 *
 * @author jackiechan
 * @version 1.0
 * @since 1.0
 */
@WebListener
public class LoadRequestMappinglistener  implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //在这里将所有的注解上面的路径和方法建立映射关系
        //1 我们不知道什么类上面有注解,只能看看我们能看到的所有的类有没有
        Package aPackage = LoadRequestMappinglistener.class.getPackage();//找到当前类所在的包,注意这个包不一定是最顶层的包  com.qianfeng.shop.listeners
        //将包中. 替换为/  com/qianfeng/shop/listeners
        String dirPath = aPackage.toString().replace("package ", "").replaceAll("\\.", Matcher.quoteReplacement(File.separator));//替换路径的时候因为不同的系统会使用不同的分隔符,比如\和/  在windows中replaceAll使用File.separator有时候会出现问题,可以使用Matcher.quoteReplacement(File.separator)
        //向上找到最顶层包也就是 com
        String startPath = dirPath.substring(0, dirPath.indexOf(File.separator));//其实就是 com
        //查找 com 所在的绝对路径,因为运行的是不确定在哪,所以必须获取到绝对路径
        URL url = LoadRequestMappinglistener.class.getResource("/" + startPath);//获取到 com 的绝对路径,注意前面有一个 file: 需要替换掉 注意这里要使用/ 这个是java中的格式
        //找到绝对路径之后,将前面的 file:替换掉,因为是一个 url 会带着协议格式
        String startRealPath = url.toString().replace("file:", "");
        //以这个绝对路径为起点,开始向下扫描所有的类
        File file = new File(startRealPath);//以开始路径作为起点,开始扫描
        List<String> allClassNames = processFile(file, startPath);//递归扫描,并且把我们的起点的名字放进去,方便最后裁剪路径
//        for (File listFile : file.listFiles()) {
//
//        }
       // System.err.println("这是最终获取到的所有的类:"+allClassNames);
        //遍历每一个类,看看上面的注解
        allClassNames.forEach(className->{
            try {
                Class clazz = Class.forName(className);//以类的名字加载类
                //2 假设我们已经找到类了,下一步找注解,类上面有可能有注解,有可能没有,所以要先看类上面有没有,有的话提前获取到,然后在看方法有没有
                String pre = "";
                RequestMapping annotation = (RequestMapping) clazz.getAnnotation(RequestMapping.class);//从类上面找执行名字的注解
                if (annotation != null) {
                    //说明类上面有这个注解
                    // stringBuffer.append(annotation.value());//获取我们 value 设置的值
                    pre = annotation.value();//如果有值就拼接前缀
                }
                //找这个类里面的方法,看看有没有注解,有的话拼接
                Method[] methods = clazz.getMethods();//因为不知道什么方法上面有注解,所有直接获取所有的方法,挨个看看
                for (Method method : methods) {
                    //获取方法上面指定类型的注解
                    RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                    if (requestMapping != null) {
                        //有注解
                        String path = requestMapping.value();//获取到方法上面路径
                        path = pre + path;//加上类上面的前缀,拼接处最终的地址
                      //  System.err.println("解析到了一个路径"+path+"===>对应的方法是--->"+className+"."+method.getName());
                        // 将 url 地址和方法的全限定名称保存到 map 中,方便后面使用
                        RequestMappingUtils.addMethod(path, className + "." + method.getName());
                    }
                }
            }catch (Exception e){
               e.printStackTrace();
            }

        });

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContextListener.super.contextDestroyed(sce);
    }


    public List<String> processFile(File file, String startPath) {
        List<String> allClassNames = new ArrayList<>();
        File[] files = file.listFiles();
        for (File file1 : files) {
            if (file1.isDirectory()) {
                //如果是目录就递归扫描,将结果添加到当前的扫描及饿哦过
                allClassNames.addAll(processFile(file1,startPath));//将子目录的所有数据加载自己的集合中
            }else{
               // System.err.println(file1.getPath());
                String path = file1.getPath();//获取到每一个文件的路径,现在是/格式的,但是我们的包是. 所以重新替换为. 并将最后.class 删除掉,因为类的全限定名称中不包含后缀名
                String classPath = path.substring(path.indexOf(startPath), path.lastIndexOf("."));//获取到类路径,但是是以/开头的
                // 将所有的/替换为点,最终结果就是每一个类的全限定名称
                String refrencePath = classPath.replaceAll(Matcher.quoteReplacement(File.separator), ".");//拿到每个类的全限定名称
              //  System.err.println(refrencePath);
                allClassNames.add(refrencePath);//添加到集合
            }
        }
        return allClassNames;//返回
    }
}
