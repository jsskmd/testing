package com.datalife.repositories;

import com.datalife.entities.DoctorInfo;
import com.datalife.entities.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

/**
 * Created by Dipak on 12/30/2014.
 *
 * This repository handles DoctorInfo entity related CRUD operations
 */
@Transactional
public interface DoctorInfoRepository extends CrudRepository<DoctorInfo,Long> {
    /**
     * This method is to get DoctorInfo by userId
     * @param userId
     * @return DoctorInfo
     */
    @Query("from DoctorInfo as d where d.user.userId=:userId")
    public DoctorInfo findByUserId(@Param(value = "userId") Long userId);

    @Query("select d.qualification from DoctorInfo as d where d.user.userId=:userId")
    public String getQualification(@Param(value = "userId") Long userId);


    @Query("from DoctorInfo as d where d.mlrNumber = :mlrNumber")
    public DoctorInfo searchByMlrNumber(@Param(value = "mlrNumber") String mlrNumber);

    /**
     * This method is get doctor based on userId and role of user
     * @param userId
     * @return User
     */
    @Query("from DoctorInfo as u where u.user.userId=:userId")
    public DoctorInfo findDoctor(@Param(value = "userId") Long userId);

}
