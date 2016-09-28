package com.datalife.services;

/**
 * Created by barath on 1/13/2016.
 */
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DefaultUserDetails implements UserDetails{

    private static final List<GrantedAuthority> AUTHORITIES = new ArrayList<GrantedAuthority>();
    private String userNamePassword;

    public DefaultUserDetails(String userNamePassword) {
        this.userNamePassword = userNamePassword;
    }

    static {
        AUTHORITIES.add(new GrantedAuthorityImpl("ROLE_USER"));
    }

    /**
     * Returns the authorities granted to the user. Cannot return <code>null</code>.
     *
     * @return the authorities, sorted by natural key (never <code>null</code>)
     */
    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return AUTHORITIES;
    }

    /**
     * Returns the password used to authenticate the user. Cannot return <code>null</code>.
     *
     * @return the password (never <code>null</code>)
     */
    @Override
    public String getPassword() {
        return this.userNamePassword;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Returns the username used to authenticate the user. Cannot return <code>null</code>.
     *
     * @return the username (never <code>null</code>)
     */
    @Override
    public String getUsername() {
        return this.userNamePassword; //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Indicates whether the user's account has expired. An expired account cannot be authenticated.
     *
     * @return <code>true</code> if the user's account is valid (ie non-expired), <code>false</code> if no longer valid
     *         (ie expired)
     */
    @Override
    public boolean isAccountNonExpired() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Indicates whether the user is locked or unlocked. A locked user cannot be authenticated.
     *
     * @return <code>true</code> if the user is not locked, <code>false</code> otherwise
     */
    @Override
    public boolean isAccountNonLocked() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Indicates whether the user's credentials (password) has expired. Expired credentials prevent
     * authentication.
     *
     * @return <code>true</code> if the user's credentials are valid (ie non-expired), <code>false</code> if no longer
     *         valid (ie expired)
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Indicates whether the user is enabled or disabled. A disabled user cannot be authenticated.
     *
     * @return <code>true</code> if the user is enabled, <code>false</code> otherwise
     */
    @Override
    public boolean isEnabled() {
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
