package com.datalife.services;

/**
 * Created by barath on 1/18/2016.
 */

import com.datalife.entities.LoginUser;
import com.datalife.repositories.LoginUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    @Autowired
    private UserDetailsManager userDetailsManager;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    LoginUserRepository loginUserRepository;

    protected TokenAuthenticationFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl); //defaultFilterProcessesUrl - specified in applicationContext.xml.
        super.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(defaultFilterProcessesUrl)); //Authentication will only be initiated for the request url matching this pattern
        setAuthenticationManager(new NoOpAuthenticationManager());
        setAuthenticationSuccessHandler(new TokenSimpleUrlAuthenticationSuccessHandler());
    }



    /**
     * Attempt to authenticate request
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException,
            IOException, ServletException {
        String token = request.getHeader("Authorization");
        logger.info("token found:"+token);
        AbstractAuthenticationToken userAuthenticationToken = null;
        try{
            userAuthenticationToken = authUserByToken(token);
            if(userAuthenticationToken == null){
                throw new AuthenticationServiceException("Invalid Token");
            }
        }catch (AuthenticationException e){
            request.getSession().invalidate();
            response.sendError( HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized" );
        }
        return userAuthenticationToken;
    }

    /**
     * authenticate the user based on token
     */
    private AbstractAuthenticationToken authUserByToken(String token) {
        if(token==null) return null;
        String username;
        String role;
        AbstractAuthenticationToken authToken = null;
        if(userDetailsManager.userExists(token)){
            LoginUser loginUser = loginUserRepository.findByToken(token);
            username = loginUser.getUserName();
            role = loginUser.getRole();

            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
            authorities.add(new SimpleGrantedAuthority(role));

            User principal = new User(username, "", authorities);
            authToken = new UsernamePasswordAuthenticationToken(principal, "", principal.getAuthorities());

        }
        return authToken;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain chain) throws IOException, ServletException {
        super.doFilter(req, res, chain);
    }
}

