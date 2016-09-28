package com.datalife.repositories;

import com.datalife.entities.Encounter;
import com.datalife.entities.Prescription;
import com.datalife.repositories.custom.EncounterRepositoryExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by supriya gondi on 11/7/2014.
 * <p/>
 * This repository handles Encounter entity related CRUD operations
 */
@Transactional
public interface EncounterRepository extends CrudRepository<Encounter, Long>, EncounterRepositoryExtension {
    /**
     * This method is to get Previous getEncounters of particular user
     *
     * @param userId
     * @param pageable
     * @return List of encounterIds
     */
    @Query("SELECT e.encounterId FROM Encounter as e WHERE e.user.userId =:userId order by date desc")
    public List<Long> getPreviousEncounters(@Param(value = "userId") Long userId, Pageable pageable);

    /**
     * This method is to get List of prescriptions by encounterId
     *
     * @param encounterId
     * @return List<Prescription>
     */
    @Query("SELECT e.prescriptions FROM Encounter as e WHERE e.encounterId=:encounterId")
    public List<Prescription> getPrescription(@Param(value = "encounterId") Long encounterId);

    /**
     * This method is to get encounter date based on encounterId
     *
     * @param encounterId
     * @return Encounter Date
     */
    @Query("SELECT e.date FROM Encounter as e WHERE e.encounterId=:encounterId")
    public Date getEncounterDate(@Param(value = "encounterId") Long encounterId);


    /**
     * This method is to get List of Encounters of particular user
     *
     * @param userId
     * @return List of Encounterids
     */
    @Query("SELECT e.encounterId FROM Encounter as e WHERE e.user.userId=:userId")
    public List<Long> getEncounters(@Param(value = "userId") Long userId);


    /**
     * This method is to get List of Encounters of particular user
     *
     * @return List of Encounterids
     */
    /*@Query("SELECT e.encounterId FROM Encounter as e WHERE e.clinicId=:clinicId and e.doctorId=:doctorId ")
    public List<Long> getClinicalEncounters(@Param(value = "clinicId") Long clinicId, @Param(value = "doctorId") Long doctorId);

    @Query("SELECT e.encounterId FROM Encounter as e WHERE e.doctorId=:doctorId ")
    public List<Long> getdoctorEncounters(@Param(value = "doctorId") Long doctorId);
*/


    @Query("select distinct  e.doctorId FROM Encounter as e WHERE user.userId =:userId order by encounterId desc ")
    public List<Long> getVisitedDoctors(@Param(value = "userId") Long userId, Pageable pageable);

    @Query("select  distinct  e.user.userId FROM Encounter as e WHERE e.doctorId =:doctorId order by encounterId desc ")
    public List<Long> getVisitedPatients(@Param(value = "doctorId") Long doctorId, Pageable pageable);

    /*@Query("select e FROM Encounter e join FETCH e.encounterId WHERE e.encounterId =:encounterId")
    public List<Long> fetchEncById(@Param(value = "encounterId") Long encounterId);*/


}
