package com.datalife.repositories;

import com.datalife.entities.Vitals;
import com.datalife.repositories.custom.VitalRepositoryExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by supriya gondi on 11/1/2014.
 * <p/>
 * This repository handles Vitals entity related CRUD operations
 */
@Transactional
public interface VitalRepository extends CrudRepository<Vitals, Long>, PagingAndSortingRepository<Vitals, Long>, VitalRepositoryExtension {

    /**
     * This query is to get last month four Vitals of User
     *
     * @param before
     * @param userId
     * @param pageable
     * @return List<Vitals>
     */
    @Query("FROM Vitals WHERE date>=:before and date<=current_timestamp and user.userId =:userId and monitored=true order by date ASC ")
    public List<Vitals> getPreviousVitals(@Param(value = "before") Date before, @Param(value = "userId") Long userId, Pageable pageable);


    @Query("FROM Vitals WHERE user.userId =:userId and monitored=true order by date ASC ")
    public List<Vitals> getDoctorVitals(@Param(value = "userId") Long userId, Pageable pageable);

    @Query("FROM Vitals WHERE date>=:before and date<=current_timestamp and user.userId =:userId and monitored=false order by date ASC ")
    public List<Vitals> getVitals(@Param(value = "before") Date before, @Param(value = "userId") Long userId, Pageable pageable);

    @Query("FROM Vitals WHERE user.userId =:userId and monitored=false order by date ASC ")
    public List<Vitals> getPatientVitals(@Param(value = "userId") Long userId, Pageable pageable);

    @Query("FROM Vitals as v WHERE v.user.userId =:userId and v.monitored=true order by date asc")
    public List<Vitals> getDoctorMonitoredVitals(@Param(value = "userId") Long userId);

    @Query("FROM Vitals as v WHERE v.user.userId =:userId and v.monitored=false order by date asc")
    public List<Vitals> getPatientMonitoredVitals(@Param(value = "userId") Long userId);
}
