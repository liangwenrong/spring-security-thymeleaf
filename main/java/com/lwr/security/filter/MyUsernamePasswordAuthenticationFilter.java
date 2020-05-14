package com.lwr.security.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class MyUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
    /**
     * 重写登录方法，会被doFilter调用
     * return null直接验证失败
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        
        String username = request.getParameter("name");
        String password = obtainPassword(request);
        
        if (username == null) {
            username = "";
        }

        if (password == null) {
            password = "";
        }
        
        
        
        
        
        username = username.trim();
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                username, password);

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);
        
/*        if(验证失败) {
            throw new AuthenticationServiceException("Cannot authenticate "
                    + authRequest);
        }
        if(验证不确定) {
            return null;
        }
        if(验证成功) {
            return new new UsernamePasswordAuthenticationToken(principal, credentials, authorities);
        }*/

        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
