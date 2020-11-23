package com.foresttech.myblog.Platform.Api;

import com.foresttech.myblog.Platform.Model.Article;
import com.foresttech.myblog.Platform.Model.User;
import com.foresttech.myblog.Platform.Service.ArticleService;
import com.foresttech.myblog.Platform.Service.UserService;
import com.foresttech.myblog.Utils.Message;
import com.foresttech.myblog.Utils.ResponseHead;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("user")
public class UserApi {

    Logger logger = LoggerFactory.getLogger(UserApi.class);

    @Autowired
    private UserService userService;

    @RequestMapping("add")
    public Message addUser(){
        User user = new User();
        user.setRealname("邹树林");
        user.setEmail("1454171568@qq.com");
        user.setCity("山东-青岛");
        user.setNickname("zoushulin");
        user.setSex("男");
        user.setAddtime("2020-11-22 22:20:00");
        BCryptPasswordEncoder passEncoder = new BCryptPasswordEncoder();
        user.setPassword(passEncoder.encode("123456"));
//        user.setPassword("123456");
        userService.addUser(user);

        Message message = new Message();
        message.setResHead(ResponseHead.SUCCESS);
        message.setResData("添加成功");
        return message;
    }

    @RequestMapping("login")
    public Message login(String username, String password) {
        Optional<User> user = userService.findUserByNickname(username);
        logger.info(username);
        user.ifPresent(value -> logger.info(value.getRealname()));      // ???
        Message message = new Message();
        try{
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            boolean matches = passwordEncoder.matches(password, user.get().getPassword());
            if (matches) {
                return message.setResHead(ResponseHead.SUCCESS).setResData("登录成功。");
            }else {
                return message.setResHead(ResponseHead.USER_LOGIN_ERROR).setResData("登录失败，密码错误。");
            }
        }catch (Exception e){
            return message.setResHead(ResponseHead.USER_NOT_EXIST).setResData("用户不存在");
        }
    }
}
