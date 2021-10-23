package com.qianfeng.shop.controller;


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
import com.qianfeng.shop.annotations.RequestMapping;
import com.qianfeng.shop.converts.MyDateConvert;
import com.qianfeng.shop.pojo.PageBean;
import com.qianfeng.shop.pojo.TbGoods;
import com.qianfeng.shop.pojo.TbGoodsKz;
import com.qianfeng.shop.service.GoodsService;
import com.qianfeng.shop.service.impl.GoodsServiceImpl;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by jackiechan on 2021/9/15 10:14
 *
 * @author jackiechan
 * @version 1.0
 * @since 1.0
 */
@RequestMapping("/goods")
@RestController
@Transactional
public class GoodsServlet {

    private GoodsService goodsService;

    @Autowired
    public void setGoodsService(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    /**
     * 添加商品
     *
     * @param req
     * @param resp
     */
    @RequestMapping("/add")
    public String addGoods(HttpServletRequest req, HttpServletResponse resp) throws FileUploadException, IOException {
        TbGoodsKz tbGoodsKz = new TbGoodsKz();
        DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
        ServletFileUpload servletFileUpload = new ServletFileUpload(fileItemFactory);//处理上传文件的独享
        List<FileItem> items = servletFileUpload.parseRequest(req);


        //先处理图片
        List<FileItem> picture = items.stream().filter(item -> {
            return item.getFieldName().equals("picture");//进行过滤,返回 true 的数据将会被保留,我们保留的是非图片的数据
        }).collect(Collectors.toList());
        if (picture == null || picture.size() == 0) {
            //没有传递图片
            resp.setContentType("text/html;charset=utf-8");

            return "没有传递图片";

        }
        //1 先确定图片保存在什么位置,我们约定在运行的是在项目的目录中创建一个 pic 目录,图片保存到这个目录里面
        String realPath = req.getServletContext().getRealPath("/pic/");//获取项目中 pic 目录的路径,注意 pic 可能不存在,但是不存在也会返回路径,返回的是如果存在的路径
        System.err.println("获取到的图片的路径....." + realPath);
        File path = new File(realPath);
        if (!path.exists()) {
            path.mkdirs();//创建目录
        }
        //2 确定图片的名字,因为上传图片的时候名字是随机的,可能会出现重复,所以保存的时候不能以原始名字作为存储名字,除非每一个商品一个单独的目录,但是现在商品还没有 id,也没有办法创建 id 目录
        String fileName = UUID.randomUUID().toString().replaceAll("-", "");
        //文件的后缀名
        String uploadFileName = picture.get(0).getName();//上传过来的文件的名字,包含了后缀名
        System.err.println("名字===>" + uploadFileName);//获取名字
        fileName = fileName + uploadFileName.substring(uploadFileName.lastIndexOf("."));//重新拼接生成新的文件名
        System.err.println("新的名字===>" + fileName);
        //3 将图片写到指定的位置
        IOUtils.copy(picture.get(0).getInputStream(), new FileOutputStream(realPath + fileName));
        //4 将地址保存到对象身上,最终保存到数据库
        String savePath = "/pic/" + fileName;//我们文件的访问的路径


        try {
            //将每一个表单 item的数据转换到 map 中,但是图片数据不转换,因为是二进制数据,需要保存为文件
            Map<String, String> map = items.stream().filter(item -> {
                return !item.getFieldName().equals("picture");//进行过滤,返回 true 的数据将会被保留,我们保留的是非图片的数据
            }).collect(Collectors.toMap(FileItem::getFieldName, fileItem -> {
                try {
                    return fileItem.getString("UTF-8");//防止中文乱码
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return null;
            }));//以getFieldName作为 key,以getString作为 value,将数据保存到 map 中

            //我们的价格传递过来是个小数,但是我们期望存 int, 扩大 100 倍,存分
            List<FileItem> priceList = items.stream().filter(item -> {
                return item.getFieldName().equals("price");//进行过滤,返回 true 的数据将会被保留,我们保留的是非图片的数据
            }).collect(Collectors.toList());

            double priceDouble = Double.parseDouble(priceList.get(0).getString());//价格因为可能是小数,先转 double

            //还差日期没处理
            ConvertUtils.register(new MyDateConvert(), Date.class);//添加自定义的转换器, 转换器实际上就把一个格式的数据转换为另外一个格式,比如把字符串转成数字或者日期都是转换
            BeanUtils.populate(tbGoodsKz, map);
            tbGoodsKz.setPrice((long) (priceDouble * 100));//重新设置价格为分
            tbGoodsKz.setPicture(savePath);//将上面文件的保存地址设置到对象上面, 最终插入到数据库
            System.err.println("封装后的数据:" + tbGoodsKz);
            goodsService.addGoods(tbGoodsKz);
            return "redirect:/admin/success.jsp";

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/admin/fail.jsp";
    }

    @RequestMapping("/show")
    public String show(HttpServletRequest request, HttpServletResponse response) {
        //查询数据,保存到 request中
        String id = request.getParameter("id");//分类 id
        String currentPage = request.getParameter("currentPage");//当前页码
        int currentPageNum = 1;//默认是第一页
        int typeId = Integer.parseInt(id);//将分类 id 转换为 int

        if (currentPage != null && !"".equalsIgnoreCase(currentPage.trim())) {
            currentPageNum = Integer.parseInt(currentPage);//传递了当前页的时候使用当前页
        }

        try {
            PageBean<TbGoods> pageBean = goodsService.getGoodsPageByTypeId(typeId, currentPageNum);
            request.setAttribute("pageBean", pageBean);//保存了数据,跳转页面
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "forward:/goodsList.jsp";
    }

    @RequestMapping("/detail")
    public String detail(HttpServletRequest request, HttpServletResponse response) {
        //查询数据,保存到 request中
        String id = request.getParameter("id");//分类 id
        if (StringUtils.isEmpty(id)) {
            return "redirect:/error/error.jsp";
        }
        TbGoods tbGoods = goodsService.getGoodById(Integer.parseInt(id));
        request.setAttribute("product", tbGoods);
        return "forward:/goodsDetail.jsp";
    }
}
