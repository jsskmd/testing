package com.datalife.repositories;

import com.datalife.entities.LoginUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by barath on 1/12/2016.
 */
@Transactional
public interface LoginUserRepository extends JpaRepository<LoginUser, Long>{

    @Query("from LoginUser as ud where ud.loginToken=:loginToken")
    public LoginUser findByToken(@Param(value = "loginToken") String loginToken);

    @Query("delete LoginUser as ud where ud.loginToken=:loginToken")
    public void invalidateToken(@Param(value = "loginToken") String loginToken);

}
