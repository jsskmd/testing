package com.datalife.repositories;


import com.datalife.entities.User;
import com.datalife.entities.UserContactInfo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by supriya gondi on 10/27/2014.
 * <p/>
 * This repository handles UserConatctInfo entity related CRUD operations
 */
@Transactional
public interface UserContactRepository extends CrudRepository<UserContactInfo, Long> {

    /**
     * This method is to get UserConatctInfo based on userId
     *
     * @param userId
     * @return UserConatctInfo
     */
    @Query("from UserContactInfo as uci where uci.user.userId=:userId")
    public UserContactInfo findByUserId(@Param(value = "userId") Long userId);

    @Query("from UserContactInfo as u where u.email=:email and (u.user.role='ROLE_PATIENT')")
    public List<UserContactInfo> checkEmailIdExistsInPatient(@Param(value = "email") String email);

    @Query("from UserContactInfo as u where u.email=:email and (u.user.role !='ROLE_PATIENT')")
    public List<UserContactInfo> checkEmailIdExistsInClinic(@Param(value = "email") String email);

    @Query("from UserContactInfo as u where u.mobilePhone=:mobilePhone and (u.user.role !='ROLE_PATIENT')")
    public List<UserContactInfo> checkMobilePhoneExistsInClinic(@Param(value = "mobilePhone") String mobilePhone);

    @Query("from UserContactInfo as u where u.mobilePhone=:mobilePhone and (u.user.role='ROLE_PATIENT')")
    public List<UserContactInfo> checkMobilePhoneExistsInPatient(@Param(value = "mobilePhone") String mobilePhone);

    @Query("from UserContactInfo as u where u.email=:email and u.mobilePhone=:mobilePhone and (u.user.role='ROLE_CLINIC' or u.user.role='ROLE_DOCTOR' or u.user.role='ROLE_REFERRALDOCTOR')")
    public List<UserContactInfo> checkEmailIdMobileExistsInClinic(@Param(value = "email") String email,@Param(value = "mobilePhone") String mobilePhone);


/*    @Query("select distinct uci.country from UserContactInfo as uci where uci.user.role=:role")
    public List<String> getHospitalCountries(@Param(value = "role") String role);*/

   /* @Query("select DISTINCT  uci.city from UserContactInfo as uci where uci.user.role=:role and uci.country=:country")
    public List<String> getHospitalCitiesById(@Param(value = "role") String role, @Param(value = "country") String country);*/

}


