package com.datalife.repositories;

import com.datalife.entities.ClinicDoctors;
import com.datalife.entities.ClinicPatients;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by DATASCRIBE on 2/18/2015.
 */
@Transactional
public interface ClinicPatientRepository extends CrudRepository<ClinicPatients,Long> {

    @Query("from ClinicPatients as cp where cp.clinicId=:clinicId")
    public List<ClinicPatients> getPatientsListByClinicId(@Param(value = "clinicId") Long clinicId);

    @Query("from ClinicPatients as cp where cp.clinicId=:clinicId and cp.doctorId=:doctorId")
    public List<ClinicPatients> getPatientsListByClinicAndDoctorId(@Param(value = "clinicId") Long clinicId, @Param(value = "doctorId") Long doctorId);

    @Query("from ClinicPatients as cp where cp.patientId=:patientId and cp.clinicId=:clinicId")
    public ClinicPatients findByPatientId(@Param(value = "patientId") Long patientId, @Param(value = "clinicId") Long clinicId);

    @Query("from ClinicPatients as cp where cp.patientId=:patientId and cp.clinicId=:clinicId and cp.doctorId=:doctorId")
    public ClinicPatients findByPatientId(@Param(value = "patientId") Long patientId, @Param(value = "clinicId") Long clinicId, @Param(value = "doctorId") Long doctorId);

    @Query("select cp.id from ClinicPatients as cp where cp.patientId=:patientId and cp.clinicId=:clinicId and cp.doctorId=:doctorId")
    public Long validate(@Param(value = "patientId") Long patientId, @Param(value = "clinicId") Long clinicId, @Param(value = "doctorId") Long doctorId);

    @Query("select cd.id from ClinicPatients  as cd where cd.clinicId=:clinicId and  cd.patientId=:patientId")
    public Long getIdByPatientAndClinic(@Param(value = "clinicId") Long clinicId, @Param(value = "patientId") Long patientId);

    @Query(" delete from ClinicDoctors as cd where cd.clinicId=:clinicId and  cd.doctorId=:doctorId")
    public boolean deleteUser(@Param(value = "clinicId") Long clinicId, @Param(value = "doctorId") Long doctorId);
}

