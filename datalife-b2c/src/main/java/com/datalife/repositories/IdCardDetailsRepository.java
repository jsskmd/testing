package com.datalife.repositories;

import com.datalife.entities.IdCardDetails;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

/**
 * Created by supriya gondi on 10/27/2014.
 *
 * This repository handles IdCardDetails entity related CRUD operations
 */
@Transactional
public interface IdCardDetailsRepository extends CrudRepository<IdCardDetails,Long> {
    /**
     * This method is to get IdCardDetails based on userid
     * @param userId
     * @return IdCardDetails
     */
    @Query("from IdCardDetails as id where id.user.userId=:userId")
    public IdCardDetails findByUserId(@Param(value = "userId") Long userId);

    @Query("select id.thumbnailFileName from IdCardDetails as id where id.user.userId=:userId")
    public String getThumbnailFileName(@Param(value = "userId") Long userId);


    @Query("select id.fileName from IdCardDetails as id where id.user.userId=:userId")
    public String getFileName(@Param(value = "userId") Long userId);

}
