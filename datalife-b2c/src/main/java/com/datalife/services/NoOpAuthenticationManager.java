package com.datalife.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * Created by barath on 1/13/2016.
 */
public class NoOpAuthenticationManager implements AuthenticationManager {

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        return authentication;
    }
}
