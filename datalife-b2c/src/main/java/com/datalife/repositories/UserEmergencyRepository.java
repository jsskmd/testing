package com.datalife.repositories;

import com.datalife.entities.UserEmergencyInfo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by Supriya on 8/21/2015.
 */
@Transactional
public interface UserEmergencyRepository extends CrudRepository<UserEmergencyInfo,Long>{

    @Query("from UserEmergencyInfo as uei where uei.user.userId=:userId")
    public LinkedList<UserEmergencyInfo> findById(@Param(value = "userId") Long userId);


    @Query("from UserEmergencyInfo as uei where uei.mobileNumber =:mobileNumber and uei.user.userId =:userId")
    public LinkedList<UserEmergencyInfo> checkMobileNoExits(@Param(value = "mobileNumber") String mobileNumber,@Param(value = "userId") Long userId);
}
