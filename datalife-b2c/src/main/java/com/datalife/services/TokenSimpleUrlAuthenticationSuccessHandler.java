package com.datalife.services;


import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by barath on 1/13/2016.
 */
public class TokenSimpleUrlAuthenticationSuccessHandler extends  SimpleUrlAuthenticationSuccessHandler  {

    protected static Logger LOGGER = Logger.getLogger(TokenSimpleUrlAuthenticationSuccessHandler.class);

    @Override
    protected String determineTargetUrl(HttpServletRequest request,HttpServletResponse response) {
        String context = request.getContextPath();
        String fullURL = request.getRequestURI();
        LOGGER.info(" fullURL : " + request.getRequestURL());
        return fullURL.substring(fullURL.indexOf(context)+context.length());
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        String url = determineTargetUrl(request,response);
        LOGGER.info(" URL : " + request.getRequestURL()+ " \n Data :  From " + request.getRemoteAddr());
        request.getRequestDispatcher(url).forward(request, response);
    }

}
