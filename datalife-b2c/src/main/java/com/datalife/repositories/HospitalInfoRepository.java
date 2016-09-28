package com.datalife.repositories;

import com.datalife.entities.HospitalInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by dscblpo on 1/24/2016.
 */
@Transactional
public interface HospitalInfoRepository extends CrudRepository<HospitalInfo,Integer> {


    @Query("from HospitalInfo as hi where hi.user.userId=:userId")
    public HospitalInfo findByUserId(@Param(value = "userId") Long userId);


}
