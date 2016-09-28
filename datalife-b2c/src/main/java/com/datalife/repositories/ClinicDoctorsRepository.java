package com.datalife.repositories;

import com.datalife.entities.ClinicDoctors;
import com.datalife.entities.ClinicInfo;
import com.datalife.entities.ConfirmAppointment;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * Created by supriya gondi on 2/12/2015.
 * <p/>
 * This repository handles ClinicDoctors entity related CRUD operations
 */
@Transactional
public interface ClinicDoctorsRepository extends CrudRepository<ClinicDoctors, Long> {
    /**
     * @param userId
     * @return Doctors belongs to particular clinic based on clinic Id
     */
    @Query("select cd.doctorId from ClinicDoctors as cd where cd.clinicId=:userId")
    public List<Long> getDoctorsByClinicId(@Param(value = "userId") Long userId);

    @Query("select cd.clinicId from ClinicDoctors as cd where cd.doctorId=:userId and cd.activate=false")
    public List<Long> getClinicsByDoctorId(@Param(value = "userId") Long userId);

    @Query("from ClinicDoctors as cd where cd.clinicId=:userId")
    public List<ClinicDoctors> getDoctorsListByClinicId(@Param(value = "userId") Long userId);

    @Query("select cd.id from ClinicDoctors as cd where cd.clinicId=:clinicId and  cd.doctorId=:doctorId")
    public Long getIdByDoctorAndClinic(@Param(value = "clinicId") Long clinicId, @Param(value = "doctorId") Long doctorId);

    @Query("select cd.id from ClinicDoctors as cd where cd.doctorId=:doctorId")
    public Long getIdOnlyByDoctor(@Param(value = "doctorId") Long doctorId);

    @Query("from ClinicDoctors as cd where cd.doctorId=:doctorId")
    public List<ClinicDoctors> getScnOpnDoctorTimeSlot(@Param(value = "doctorId") Long doctorId);

    @Query("select cd.specialityId from ClinicDoctors as cd where cd.clinicId=:clinicId and  cd.doctorId=:doctorId")
    public Long getSpecialityByIds(@Param(value = "clinicId") Long clinicId, @Param(value = "doctorId") Long doctorId);

    @Query(" from ClinicDoctors as cd where cd.clinicId=:clinicId and  cd.doctorId=:doctorId")
    public ClinicDoctors findByDoctorAndClinic(@Param(value = "clinicId") Long clinicId, @Param(value = "doctorId") Long doctorId);

    @Query(" from ClinicDoctors as cd where  cd.doctorId=:doctorId")
    public ClinicDoctors findonlyByDoctorAndClinic(@Param(value = "doctorId") Long doctorId);

    @Query("from ClinicDoctors as cd where cd.doctorId=:userId")
    public List<ClinicDoctors> findClinicsByDoctorId(@Param(value = "userId") Long userId);


    @Query("from ClinicDoctors as cd where cd.doctorId=:doctorId and cd.clinicId=:clinicId")
    public List<ClinicDoctors> getDoctorTimeSlot(@Param(value = "doctorId") Long doctorId, @Param(value = "clinicId") Long clinicId);

    @Query("from ConfirmAppointment as cd where (cd.status = 4 or cd.status=5 or cd.status=6 or cd.status=7) and cd.doctorId=:doctorId and cd.clinicId=:clinicId and cd.scheduledOn >= current_date order by scheduledOn")
    public List<ConfirmAppointment> getConfirmedAppointment(@Param(value = "doctorId") Long doctorId, @Param(value = "clinicId") Long clinicId);

    @Query("from ConfirmAppointment as cd where cd.status = 0 and cd.updatedBy = 'ROLE_PATIENT' and cd.doctorId=:doctorId and cd.clinicId=:clinicId and cd.scheduledOn >= current_date order by scheduledOn")
    public List<ConfirmAppointment> getPatientBlocked(@Param(value = "doctorId") Long doctorId, @Param(value = "clinicId") Long clinicId);

    @Query("from ConfirmAppointment as cd where cd.status <= 3 and cd.updatedBy = 'ROLE_DOCTOR' and cd.doctorId=:doctorId and cd.clinicId=:clinicId and cd.scheduledOn >= current_date order by scheduledOn")
    public List<ConfirmAppointment> getDoctorBlocked(@Param(value = "doctorId") Long doctorId, @Param(value = "clinicId") Long clinicId);

    @Query("from ConfirmAppointment as cd where cd.status <= 3 and cd.updatedBy = 'ROLE_CLINIC' and cd.doctorId=:doctorId and cd.clinicId=:clinicId and cd.scheduledOn >= current_date order by scheduledOn")
    public List<ConfirmAppointment> getClinicBlocked(@Param(value = "doctorId") Long doctorId, @Param(value = "clinicId") Long clinicId);

 /*   @Query("from ConfirmAppointment as cd where cd.status > 4 and cd.doctorId=:doctorId and cd.clinicId=:clinicId and  cd.scheduledOn >= current_date order by scheduledOn")
    public List<ConfirmAppointment> updatedStatus(@Param(value = "doctorId") Long doctorId, @Param(value = "clinicId") Long clinicId);*/

    @Query("from ClinicDoctors as cd where cd.clinicId=:userId")
    public List<ClinicDoctors> getDoctorsClinicId(@Param(value = "userId") Long userId);

    @Query("from ConfirmAppointment as cd where (cd.status = 4 or cd.status=5 or cd.status=6 or cd.status=7) and cd.doctorId=:doctorId and cd.clinicId=:clinicId and cd.scheduledOn >= :scheduledOn order by scheduledOn")
    public List<ConfirmAppointment> getConfirmedAppointmentFromPrev(@Param(value = "doctorId") Long doctorId, @Param(value = "clinicId") Long clinicId, @Param(value = "scheduledOn") Date scheduledOn);

    @Query("from ConfirmAppointment as cd where cd.status = 0 and cd.updatedBy = 'ROLE_PATIENT' and cd.doctorId=:doctorId and cd.clinicId=:clinicId and cd.scheduledOn >= :scheduledOn order by scheduledOn")
    public List<ConfirmAppointment> getPatientBlockedFromPrev(@Param(value = "doctorId") Long doctorId, @Param(value = "clinicId") Long clinicId, @Param(value = "scheduledOn") Date scheduledOn);

    @Query("from ConfirmAppointment as cd where cd.status <= 3 and cd.updatedBy = 'ROLE_DOCTOR' and cd.doctorId=:doctorId and cd.clinicId=:clinicId and cd.scheduledOn >= :scheduledOn order by scheduledOn")
    public List<ConfirmAppointment> getDoctorBlockedFromPrev(@Param(value = "doctorId") Long doctorId, @Param(value = "clinicId") Long clinicId, @Param(value = "scheduledOn") Date scheduledOn);

    @Query("from ConfirmAppointment as cd where cd.status <= 3 and cd.updatedBy = 'ROLE_CLINIC' and cd.doctorId=:doctorId and cd.clinicId=:clinicId and cd.scheduledOn >= :scheduledOn order by scheduledOn")
    public List<ConfirmAppointment> getClinicBlockedFromPrev(@Param(value = "doctorId") Long doctorId, @Param(value = "clinicId") Long clinicId, @Param(value = "scheduledOn") Date scheduledOn);


   @Query("from ConfirmAppointment as cd where cd.status = 8 and cd.doctorId=:doctorId and cd.clinicId=:clinicId and (cd.vacaStartDate >= :before or cd.vacaStartDate between :before and :after or cd.vacaEndDate >= :before or cd.vacaEndDate between :before and :after)")
   public List<ConfirmAppointment> getMultipleDayBlocked(@Param(value = "doctorId") Long doctorId, @Param(value = "clinicId") Long clinicId, @Param(value = "before") Date before, @Param(value = "after") Date after);


}
