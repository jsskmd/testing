package com.datalife.repositories;

import com.datalife.entities.UserPreferences;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.LinkedHashSet;
import java.util.LinkedList;

/**
 * Created by Dipak on 11/22/2014.
 * <p/>
 * This repository handles UserAltContactInfo entity related CRUD operations
 */
@Transactional
public interface UserPreferenceRepository extends CrudRepository<UserPreferences, Long>{

    @Query("from UserPreferences as p where p.user.userId=:userId")
    public LinkedList<UserPreferences> findById(@Param(value = "userId") Long userId);

    @Query("from UserPreferences as p where p.user.userId=:userId")
    public LinkedHashSet<UserPreferences> findByUserId(@Param(value = "userId") Long userId);

    @Query("select count(p) from UserPreferences as p where p.user.userId=:userId")
    public Integer getPreferencesCount(@Param(value = "userId") Long userId);
}
