package com.foresttech.myblog.Config.Security.Util.Filter;

import com.foresttech.myblog.Config.Security.Util.Filter.AuthToken.EmailPasswordAuthToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EmailLoginFilter extends AbstractAuthenticationProcessingFilter {
    public EmailLoginFilter() {
        super(new AntPathRequestMatcher("/user/loginByEmail"));
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        String email = httpServletRequest.getParameter("email");
        String password = httpServletRequest.getParameter("password");
//        System.out.println(email);
        EmailPasswordAuthToken token = new EmailPasswordAuthToken(email, password);
        token.setDetails(this.authenticationDetailsSource.buildDetails(httpServletRequest));
        return this.getAuthenticationManager().authenticate(token);
    }
}
