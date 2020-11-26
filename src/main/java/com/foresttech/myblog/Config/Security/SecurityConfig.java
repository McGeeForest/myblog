package com.foresttech.myblog.Config.Security;

import com.foresttech.myblog.Config.Security.Util.Filter.TokenAuthenticationFilter;
import com.foresttech.myblog.Config.Security.Util.Handler.UsernamePasswordLoginSuccessHandler;
import com.foresttech.myblog.Config.Security.Util.LoginMethodConfig;
import com.foresttech.myblog.Config.Security.Util.Manger.JwtTokenManager;
import com.foresttech.myblog.Config.Security.Util.Filter.EmailLoginFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private LoginMethodConfig loginMethodConfig;

    @Autowired
    private JwtTokenManager tokenManager;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
//                .antMatchers(HttpMethod.GET, securityIgnoreResource).permitAll()
                .antMatchers("/test/te","/user/login","/user/loginByEmail").permitAll()
                .anyRequest().authenticated()
                .and().formLogin().loginProcessingUrl("/user/login").successHandler(new UsernamePasswordLoginSuccessHandler(tokenManager))
                .and().cors()
                .and().csrf()
                .disable();
        http
                .addFilterBefore(loginMethodConfig.emailAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(loginMethodConfig.tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
