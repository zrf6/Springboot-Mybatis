package com.controller;

import com.entity.User;
import com.service.UserService;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;


@Controller
public class UserController {

    private static Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping("login")
    @ResponseBody
    public String login(User user) {
        log.info("请求进入了，参数为：" + user);
        List<User> users = userService.login(user);
        if (users.size() != 0)
            return "success";
        else
            return "failed";
    }

    @GetMapping("selectAll")
    @ResponseBody
    public List<User> selectAll() {
        List<User> users = userService.selectAll();
        for (User user : users) {
            String up_path = user.getUpPath();
            if (up_path != null) {
                String url = "http://localhost:8080/" + up_path.split("webapps\\\\")[1].replace("\\", "/");
                log.info("拼接后的url为" + url);
                user.setUpPath(url);
            }
        }
        return users;
    }

    @PostMapping("addUser")
    @ResponseBody
    public String save(User user, @RequestParam MultipartFile upload, HttpServletRequest req, HttpServletResponse resp) {
        log.info("传入的参数为：" + user);
        log.info("传入的文件参数为：" + upload);
        //调用上传Img方法
        saveImg(user, upload, req, resp);
        //存储用户方法
        Integer result = userService.saveUser(user);
        if (result != 0)
            return "success";
        else
            return "failed";
    }

    @PostMapping("updateUser")
    @ResponseBody
    public String update(User user, HttpServletRequest req, HttpServletResponse resp, MultipartFile upload) {
        saveImg(user, upload, req, resp);
        //如果没修改图片，将路径修改为绝对路径
        if (upload == null || upload.isEmpty()) {
            User oldUser = userService.login(user).get(0);
            user.setUpPath(oldUser.getUpPath());
        }
        log.info("修改参数为：" + user + "upload是否为empty：" + upload.isEmpty());
        Integer result = userService.updateUser(user);
        if (result != 0)
            return "success";
        else
            return "failed";
    }

    @GetMapping("deleteUser")
    @ResponseBody
    public String delete(User user) {
        Integer result = userService.deleteUser(user);
        log.info("删除方法返回值为：" + result);
        if (result != 0)
            return "success";
        else
            return "failed";
    }


    /**
     * 上传图片的方法
     */
    private void saveImg(User user, MultipartFile upload, HttpServletRequest req, HttpServletResponse resp) {
        //如通上传文件不为null，处理上传文件
        if (upload != null && !upload.isEmpty()) {
            //获取临时文件路径
            String path = req.getSession().getServletContext().getRealPath("/upload" + File.separator + "img" + File.separator);
            log.info("存放文件path为：" + path);
            //存放于D盘
//			String path = "D:"+File.separator+"upload"+File.separator+"img"+File.separator+"user";
            //创建临时文件夹
            File uploadDir = new File(path);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            log.info("临时文件夹路径为：" + path);
            //获取源文件的文件名
            String oldFilename = upload.getOriginalFilename();
            log.info("OriginalFilename为：" + oldFilename);

            //获取文件后缀
            String suffix = FilenameUtils.getExtension(oldFilename);
            log.info("源文件的后缀名：" + suffix);
            //获取文件大小
            long size = upload.getSize();
            //判断文件大小
            if (size > 1024 * 1024 * 2) {
                log.info("文件大小超过2M");
                throw new RuntimeException("文件大小超过2M");
            } else if (oldFilename.matches(".*(.png|.jpg|.jpeg)$")) {
                //拼接文件名，防止重名
                String name = UUID.randomUUID().toString() + "_" + oldFilename;
                //new File文件对象存放上传的文件
                File file = new File(path, name);
                log.info("文件路径为：" + file.getAbsolutePath());
                user.setUpPath(file.getAbsolutePath());
                try {
                    upload.transferTo(file);
                } catch (IllegalStateException | IOException e) {
                    log.info("上传失败");
                    throw new RuntimeException(e.getMessage());
                }
            } else {
                throw new RuntimeException("不支持的格式上传");
            }
        }
    }
}
