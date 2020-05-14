package com.lwr.security.handler;

import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

public class LoginFailHandler extends SimpleUrlAuthenticationFailureHandler{
    
    public LoginFailHandler() {
    }
    public LoginFailHandler(String defaultFailureUrl, boolean forwardToDestination) {
        setDefaultFailureUrl(defaultFailureUrl);
        setUseForward(forwardToDestination);
    }
}
