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
import org.springframework.boot.autoconfigure.template.TemplateLocation;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepo userRepo;

    public void addUser(User user){
        userRepo.save(user);
    }

    //username唯一
    public Optional<User> findUserByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userflag = userRepo.findByUsername(username);
        if (userflag.isPresent()) {
            User user = userflag.get();
            logger.info(user.getUsername());
            return user;
        }else {
            throw new UsernameNotFoundException("用户不存在");
        }
    }

    public User loadUserByEmail(String email) throws UsernameNotFoundException{
        Optional<User> userflag = userRepo.findByEmail(email);
        if (userflag.isPresent()) {
            User user = userflag.get();
            return user;
        }else {
            throw new UsernameNotFoundException("用户不存在");
        }
    }
}
