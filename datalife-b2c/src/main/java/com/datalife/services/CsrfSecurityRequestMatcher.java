package com.datalife.services;

import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by barath on 12/29/2015.
 */
public class CsrfSecurityRequestMatcher implements RequestMatcher {
    /*private Pattern allowedMethods = Pattern.compile("^(GET|HEAD|TRACE|OPTIONS)$");*/
    private RegexRequestMatcher unprotectedMatcher = new RegexRequestMatcher("/services.*", null);

    @Override
    public boolean matches(HttpServletRequest request) {
        /*if(allowedMethods.matcher(request.getMethod()).matches()){
            return false;
        }*/
        boolean b = unprotectedMatcher.matches(request);
        return unprotectedMatcher.matches(request);

    }
}
