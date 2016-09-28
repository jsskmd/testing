package com.datalife.repositories;

import com.datalife.entities.UserDetails;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by supriya gondi on 10/27/2014.
 *
 * This repository handles UserDetails entity related CRUD operations
 */
@Transactional
public interface UserDetailsRepository extends CrudRepository<UserDetails,Integer> {

    /**
     * This query is to get Full Name (i.e. FirstName LastName) by userId
     *
     * @param userId
     * @return String i.e. FirstName,LastName
     */
    @Query("select firstName,lastName from UserDetails as ud where ud.user.userId=:userId")
    public String getFullName(@Param(value = "userId") Long userId);

    @Query("select firstName,lastName from UserDetails as ud where ud.user.userId=:userId and ud.user.role='ROLE_PATIENT'")
    public String getPatientFullName(@Param(value = "userId") Long userId);

    /**
     * This method is to get UserDetails based on userId
     * @param userId
     * @return UserDetails
     */
    @Query("from UserDetails as ud where ud.user.userId=:userId")
    public UserDetails findByUserId(@Param(value = "userId") Long userId);

    @Query("select ud.bloodGroup from UserDetails as ud where ud.user.userId=:userId")
    public Integer findBloodGroupByUserId(@Param(value = "userId") Long userId);
}
