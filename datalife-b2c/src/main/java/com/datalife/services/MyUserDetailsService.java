
package com.datalife.services;


/**
 * This class is for setting and getting User from session
 *
 * Created by supriya gondi on 9/12/2014.
 */
import com.datalife.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MyUserDetailsService implements UserDetailsService {
    //get user from the database, via Hibernate

    /**
     * reference to userRepository
     */
    @Autowired
    private UserRepository userRepository;

    private com.datalife.entities.User user;

    public MyUserDetailsService() {
    }

    public MyUserDetailsService(com.datalife.entities.User user) {
        this.user = user;
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(final String username)
            throws UsernameNotFoundException {

        com.datalife.entities.User user = userRepository.findByUserName(username);
        if (user != null) {
            return new UserAuthDetails(user);
        } else {
            throw new UsernameNotFoundException("Username not found: ");
        }

    }

    public com.datalife.entities.User getUser() {
        return user;
    }

    public void setUser(com.datalife.entities.User user,UserRepository userRepository) {
        this.user = user;
    }

    public static void setUserInSession(com.datalife.entities.User user,UserRepository userRepository) {
        final SecurityContext context = SecurityContextHolder.getContext();
        final UserAuthDetails userDetails = new UserAuthDetails(user);
        final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, user.getPassword(), userDetails.getAuthorities());
        context.setAuthentication(authentication);
    }

    public static com.datalife.entities.User getUserFromSession() {
        final SecurityContext context = SecurityContextHolder.getContext();
        if (context != null) {
            final Authentication authentication = context.getAuthentication();
            if (authentication != null && authentication.getPrincipal() != null) {
                final Object principal = authentication.getPrincipal();
                if (principal instanceof UserAuthDetails) {
                    final UserAuthDetails userDetails = (UserAuthDetails) principal;
                    return userDetails.getUser();
                }
            }
        }
        return null;
    }

}
