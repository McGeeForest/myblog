package com.foresttech.myblog.Platform.Service;

import com.foresttech.myblog.Platform.Api.testApi;
import com.foresttech.myblog.Platform.Model.Article;
import com.foresttech.myblog.Platform.Model.User;
import com.foresttech.myblog.Platform.Repository.ArticleRepo;
import com.foresttech.myblog.Platform.Repository.UserRepo;
import com.foresttech.myblog.Utils.Message;
import com.foresttech.myblog.Utils.ResponseHead;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepo userRepo;

    public void addUser(User user){
        userRepo.save(user);
    }

    //nickname唯一
    public Optional<User> findUserByNickname(String nickname) {
        Optional<User> user = userRepo.findByNickname(nickname);
        return user;
    }
}
