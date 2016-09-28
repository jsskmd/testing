
package com.datalife.services;

import com.datalife.entities.User;
import com.datalife.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * This class is for setting authorities of user
 *
 * Created by supriya gondi on 9/15/2014.
 */
@Service
public class UserAuthDetails implements UserDetails
{
   private User user=null;

    @Autowired
    UserRepository userRepository;

    public UserAuthDetails() {

    }

    public UserAuthDetails(User user)
    {
        this.user = user;
    }

    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        final List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();

        /*authList.add( new SimpleGrantedAuthority( "ROLE_USER") );
        authList.add( new SimpleGrantedAuthority( "ROLE_ANONYMOUS" ) );*/

        if ( user != null )
        {

            if ( getUsername() != null && getUsername().equals( "gkssupriya@yahoo.com" ) )
            {
                authList.add( new SimpleGrantedAuthority( "ROLE_ADMIN" ) );
            }
                try
                {
                              if ( user!=null )
                    {
                        if(user.getRole().equals("ROLE_PATIENT")) {
                            authList.add(new SimpleGrantedAuthority("ROLE_PATIENT"));
                        }
                        if(user.getRole().equals("ROLE_DOCTOR")) {
                            authList.add(new SimpleGrantedAuthority("ROLE_DOCTOR"));
                        }
                        if(user.getRole().equals("ROLE_CLINIC")) {
                            authList.add(new SimpleGrantedAuthority("ROLE_CLINIC"));
                        }
                        if(user.getRole().equals("ROLE_CSR")) {
                            authList.add(new SimpleGrantedAuthority("ROLE_CSR"));
                        }
                        if(user.getRole().equals("ROLE_PROVIDER")) {
                            authList.add(new SimpleGrantedAuthority("ROLE_PROVIDER"));
                        }
                    }

                }
                catch ( final Exception e )
                {
                    // Ignore the exception but go with limited rights.
                    System.out.println( e.getMessage() );
                }
        }
        return authList;
    }

    public String getPassword()
    {
        return user.getPassword();
    }

    public String getUsername()
    {
        return user.getUserName();
    }

    public boolean isAccountNonExpired()
    {
        return isAccountNonLocked();
    }

    public boolean isAccountNonLocked()
    {
        return true;
    }

    public boolean isCredentialsNonExpired()
    {
        return isAccountNonLocked();
    }

    public boolean isEnabled()
    {
        return isAccountNonLocked();
    }

    public User getUser()
    {
        return user;
    }
}

