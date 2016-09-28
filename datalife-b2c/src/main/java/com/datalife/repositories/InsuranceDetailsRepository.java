package com.datalife.repositories;

import com.datalife.entities.InsuranceDetails;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

/**
 * Created by supriya gondi on 10/27/2014.
 *
 * This repository handles InsuranceDetails entity related CRUD operations
 */
@Transactional
public interface InsuranceDetailsRepository extends CrudRepository<InsuranceDetails,Long> {
    /**
     * This method is to get InsuranceDetails based on userId
     * @param userId
     * @return InsuranceDetails
     */
    @Query("from InsuranceDetails as ins where ins.user.userId=:userId")
    public InsuranceDetails findByUserId(@Param(value = "userId") Long userId);
}
