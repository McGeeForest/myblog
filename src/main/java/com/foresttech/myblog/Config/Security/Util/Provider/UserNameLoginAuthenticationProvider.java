package com.foresttech.myblog.Config.Security.Util.Provider;

import com.foresttech.myblog.Platform.Service.UserService;
import com.foresttech.myblog.Utils.Message;
import com.foresttech.myblog.Utils.ResponseHead;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class UserNameLoginAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
//    @Autowired
//    private ApplicationEventPublisher publisher;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        Message message = new Message();
        if(username == null || username.equals("")){
            throw new UsernameNotFoundException(message.setResHead(ResponseHead.USER_NOT_EXIST).toJsonString());
        }
        if(password == null || password.equals("")){
            throw new UsernameNotFoundException(message.setResHead(ResponseHead.USER_LOGIN_ERROR).toJsonString());
        }
        //获取用户信息
        UserDetails user = userService.loadUserByUsername(username);
        //比较前端传入的密码明文和数据库中加密的密码是否匹配
        if (!passwordEncoder.matches(password, user.getPassword())) {
            //发布密码不正确事件
            // publisher.publishEvent(new UserLoginFailedEvent(authentication));
            throw new BadCredentialsException(message.setResHead(ResponseHead.USER_LOGIN_ERROR).toJsonString());
        }
        //获取用户权限信息
        // Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        // return new UsernamePasswordAuthenticationToken(user, password, authorities);
        return new UsernamePasswordAuthenticationToken(user, password, new ArrayList<>());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(aClass);
    }

}
