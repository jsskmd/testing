package com.datalife.repositories;

import com.datalife.entities.History;
import com.datalife.entities.UserContactInfo;
import com.datalife.repositories.custom.HistoryRepositoryExtension;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by supriya gondi on 10/27/2014.
 * <p/>
 * This repository handles History entity related CRUD operations
 */
@Transactional
public interface HistoryRepository extends CrudRepository<History, Long>, HistoryRepositoryExtension {

    /**
     * This method is to get History based on userid
     * @param userId
     * @return History
     */
    @Query("from History as h where h.user.userId=:userId")
    public History findByUserId(@Param(value = "userId") Long userId);
}
