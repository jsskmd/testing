package com.datalife.repositories;

import com.datalife.entities.Authentication;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

/**
 * Created by supriya gondi on 11/6/2014.
 *
 * This repository handles Authentication entity related CRUD operations
 */
@Transactional
public interface AuthenticationRepository extends CrudRepository<Authentication,Long> {
    /**
     *
     * @param userId
     * @return OTP from Authentication table
     */
    @Query("select a.otp from Authentication as a where a.user.userId=:userId")
    public String findByUserId(@Param(value = "userId") Long userId);
}
