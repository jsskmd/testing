package com.datalife.repositories;

import com.datalife.entities.ClinicInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by supriya gondi on 2/12/2015.
 *
 *  This repository handles ClinicInfo entity related CRUD operations
 */
@Transactional
public interface ClinicInfoRepository extends CrudRepository<ClinicInfo,Long> {
    /**
     *
     * @param userId
     * @return Clinic Information based on clinic Id
     */
    @Query("from ClinicInfo as c where c.user.userId=:userId")
    public ClinicInfo findByUserId(@Param(value = "userId") Long userId);

    @Query("select c.clinicName from ClinicInfo as c where c.user.userId=:userId")
    public String getClinicName(@Param(value = "userId") Long userId);

    @Query("from ClinicInfo as c where c.mlrNumber = :mlrNumber")
    public ClinicInfo searchByMlrNumber(@Param(value = "mlrNumber") String mlrNumber);

    @Query("from ClinicInfo c left join fetch c.lab left join fetch c.pharmacy where c.user.userId = :userId")
    public ClinicInfo getLabAndPharmacyByClinicId(@Param(value = "userId") Long userId);

    @Query("from ClinicInfo as c JOIN fetch c.lab where c.user.userId = :userId")
    public ClinicInfo getLabByClinicId(@Param(value = "userId") Long userId);

    @Query("from ClinicInfo as c JOIN fetch c.pharmacy where c.user.userId = :userId")
    public ClinicInfo getPharmacyByClinicId(@Param(value = "userId") Long userId);


}
