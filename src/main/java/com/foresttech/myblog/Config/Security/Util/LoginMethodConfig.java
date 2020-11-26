package com.foresttech.myblog.Config.Security.Util;

import com.foresttech.myblog.Config.Security.Util.Filter.EmailLoginFilter;
import com.foresttech.myblog.Config.Security.Util.Filter.TokenAuthenticationFilter;
import com.foresttech.myblog.Config.Security.Util.Handler.EmailPasswordLoginFailureHandler;
import com.foresttech.myblog.Config.Security.Util.Handler.EmailPasswordLoginSuccessHandler;
import com.foresttech.myblog.Config.Security.Util.Manger.JwtTokenManager;
import com.foresttech.myblog.Config.Security.Util.Provider.EmailPasswordLoginAuthenticationProvider;
import com.foresttech.myblog.Config.Security.Util.Provider.UserNameLoginAuthenticationProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;


@Configuration
public class LoginMethodConfig {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private JwtTokenManager jwtTokenManager;
    @Autowired
    private UserNameLoginAuthenticationProvider userNameLoginAuthenticationProvider;
    @Autowired
    private EmailPasswordLoginAuthenticationProvider emailPasswordLoginAuthenticationProvider;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(){
        List<AuthenticationProvider> providers = new ArrayList<>();
        providers.add(userNameLoginAuthenticationProvider);
        providers.add(emailPasswordLoginAuthenticationProvider);
        return new ProviderManager(providers);
    }


//    检查请求头的cookie中是否有token，如果有，则验证
    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(authenticationManager(), jwtTokenManager);
    }

    @Bean
    public EmailLoginFilter emailAuthenticationFilter() {
        EmailLoginFilter emailLoginFilter = new EmailLoginFilter();
        emailLoginFilter.setAuthenticationManager(authenticationManager());
        emailLoginFilter.setAuthenticationSuccessHandler(new EmailPasswordLoginSuccessHandler(jwtTokenManager));
        emailLoginFilter.setAuthenticationFailureHandler(new EmailPasswordLoginFailureHandler());
        return emailLoginFilter;
    }
}
