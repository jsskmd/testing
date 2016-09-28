package com.datalife.repositories;

import com.datalife.entities.ConfirmAppointment;
import com.datalife.entities.DoctorInfo;
import com.datalife.entities.SchedulingStatus;
import com.datalife.entities.Search;
import com.datalife.repositories.custom.ConfirmAppointmentRepositoryExtension;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by Dipak on 5/5/2015.
 */
@Transactional
public interface ConfirmAppointmentRepository extends CrudRepository<ConfirmAppointment, Long>, ConfirmAppointmentRepositoryExtension {



    @Query("from ConfirmAppointment as d where d.clinicId=:clinicId and d.doctorId=:doctorId and d.date =:date and d.scheduledOn between current_timestamp and :before")
    public List<ConfirmAppointment> getPatientToUpdateStatus(@Param(value = "clinicId") Long clinicId, @Param(value = "doctorId") Long doctorId, @Param(value = "date") String date, @Param(value = "before") Date before);

    @Query("from ConfirmAppointment as cd where cd.status = 8 and cd.doctorId=:doctorId and cd.clinicId=:clinicId and (cd.vacaStartDate >= :before or cd.vacaStartDate between :before and :after or cd.vacaEndDate >= :before or cd.vacaEndDate between :before and :after)")
    public List<ConfirmAppointment> getMultipleDayBlocked(@Param(value = "doctorId") Long doctorId, @Param(value = "clinicId") Long clinicId, @Param(value = "before") Date before, @Param(value = "after") Date after);

    @Query("from ConfirmAppointment as cd where cd.status <= 3 and cd.doctorId=:doctorId and cd.clinicId=:clinicId and  cd.scheduledOn between :before and :after  order by scheduledOn")
    public List<ConfirmAppointment> getDoctorBlockedFromPrev(@Param(value = "doctorId") Long doctorId, @Param(value = "clinicId") Long clinicId, @Param(value = "before") Date before, @Param(value = "after") Date after);

    @Query("from ConfirmAppointment as cd where cd.status = 0 and cd.updatedBy = 'ROLE_PATIENT' and cd.doctorId=:doctorId and cd.clinicId=:clinicId and cd.scheduledOn between :before and :after  order by scheduledOn")
    public List<ConfirmAppointment> getPatientBlockedFromPrev(@Param(value = "doctorId") Long doctorId, @Param(value = "clinicId") Long clinicId, @Param(value = "before") Date before, @Param(value = "after") Date after);

   /* @Query("from ConfirmAppointment as d where d.clinicId=:clinicId and d.doctorId=:doctorId and d.date=:date and d.status > 4")
    public List<ConfirmAppointment> getUpdatedStatus(@Param(value = "clinicId") Long clinicId, @Param(value = "doctorId") Long doctorId, @Param(value = "date") String date);*/

    @Query("from ConfirmAppointment as cd where cd.doctorId=:doctorId and cd.clinicId=:clinicId and  cd.scheduledOn between :before and :after order by scheduledOn")
    public List<ConfirmAppointment> getConfirmedAppointmentFromPrev(@Param(value = "doctorId") Long doctorId, @Param(value = "clinicId") Long clinicId, @Param(value = "before") Date before, @Param(value = "after") Date after);

    /*@Query("from ConfirmAppointment as cd where cd.status > 4 and cd.doctorId=:doctorId and cd.clinicId=:clinicId and cd.scheduledOn between :before and :after order by scheduledOn")
    public List<ConfirmAppointment> updatedStatusFromPrev(@Param(value = "doctorId") Long doctorId, @Param(value = "clinicId") Long clinicId, @Param(value = "before") Date before, @Param(value = "after") Date after);
*/
    @Query("from ConfirmAppointment as d where d.clinicId=:clinicId and d.doctorId=:doctorId and d.date =:date and d.status =:status")
    public List<ConfirmAppointment> getDoctorSlotEqualOnDate(@Param(value = "clinicId") Long clinicId, @Param(value = "doctorId") Long doctorId, @Param(value = "date") String date, @Param(value = "status") SchedulingStatus status);

    @Query("from ConfirmAppointment as d where d.clinicId=:clinicId and d.doctorId=:doctorId and d.status =:status and d.scheduledOn between :vacaStartDate and :vacaEndDate order by scheduledOn asc")
    public List<ConfirmAppointment> getDoctorSlotBasedOnDateAndStatus(@Param(value = "clinicId") Long clinicId, @Param(value = "doctorId") Long doctorId, @Param(value = "vacaStartDate") Date vacaStartDate, @Param(value = "vacaEndDate") Date vacaEndDate, @Param(value = "status") SchedulingStatus status);

    @Query("from ConfirmAppointment as d where d.clinicId=:clinicId and d.doctorId=:doctorId and d.scheduledOn < current_date and d.status = 4 or d.status = 5 or d.status = 10")
    public List<ConfirmAppointment> updatescheduledToNoshowForPastDate(@Param(value = "clinicId") Long clinicId, @Param(value = "doctorId") Long doctorId);

    @Query("from ConfirmAppointment as d where d.clinicId=:clinicId and d.doctorId=:doctorId and ( d.status <= 3) and (d.cancelledOn between :vacaStartDate and :vacaEndDate) or (d.cancelledOn =:vacaStartDate  and  d.status <= 3) or (d.cancelledOn =:vacaEndDate and d.status <= 3) order by cancelledOn")
    public List<ConfirmAppointment> checkForSingleDayOrSlotCancel(@Param(value = "clinicId") Long clinicId, @Param(value = "doctorId") Long doctorId, @Param(value = "vacaStartDate") Date vacaStartDate, @Param(value = "vacaEndDate") Date vacaEndDate);

    @Query("from ConfirmAppointment as d where d.clinicId=:clinicId and d.doctorId=:doctorId and d.date=:date and d.time=:time")
    public ConfirmAppointment getConfirmAppId(@Param(value = "clinicId") Long clinicId, @Param(value = "doctorId") Long doctorId, @Param(value = "date") String date, @Param(value = "time") String time);

    @Query("from ConfirmAppointment as d where d.patientId=:patientId and d.scheduledOn < current_date and d.status = 4 or d.status = 5 or d.status = 10")
    public List<ConfirmAppointment> updateStatusForPastDate(@Param(value = "patientId") Long patientId);









    @Query("from ConfirmAppointment as d where d.clinicId=:clinicId and d.doctorId=:doctorId and d.scheduledOn =:scheduledOn and d.status =:status")
    public List<ConfirmAppointment> getDoctorSlotOnDate(@Param(value = "clinicId") Long clinicId, @Param(value = "doctorId") Long doctorId, @Param(value = "scheduledOn") Date scheduledOn, @Param(value = "status") SchedulingStatus status);


    @Query("from ConfirmAppointment as d where d.clinicId=:clinicId and d.doctorId=:doctorId and d.patientId=:patientId and d.date=:date and d.time=:time")
    public ConfirmAppointment getId(@Param(value = "clinicId") Long clinicId, @Param(value = "doctorId") Long doctorId, @Param(value = "patientId") Long patientId, @Param(value = "date") String date, @Param(value = "time") String time);

    @Query("from ConfirmAppointment as d where d.clinicId=:clinicId and d.doctorId=:doctorId and d.date=:date and d.time=:time")
    public ConfirmAppointment getPatientsDetails(@Param(value = "clinicId") Long clinicId, @Param(value = "doctorId") Long doctorId, @Param(value = "date") String date, @Param(value = "time") String time);

    @Query("from ConfirmAppointment as d where d.clinicId=:clinicId and d.doctorId=:doctorId and d.date=:date and d.status = 4 and d.scheduledOn >= current_timestamp order by scheduledOn")
    public List<ConfirmAppointment> getDoctorAppointmentList(@Param(value = "clinicId") Long clinicId, @Param(value = "doctorId") Long doctorId, @Param(value = "date") String date);


    @Query("from ConfirmAppointment as d where d.clinicId=:clinicId and d.doctorId=:doctorId and d.patientId=:patientId and d.status=4 and d.scheduledOn >= current_timestamp order by scheduledOn")
    public List<ConfirmAppointment> checkPatientAppSched(@Param(value = "clinicId") Long clinicId, @Param(value = "doctorId") Long doctorId, @Param(value = "patientId") Long patientId);

    @Query("from ConfirmAppointment as d where d.clinicId=:clinicId and d.doctorId=:doctorId and d.status = 4 and d.scheduledOn between :vacaStartDate and :vacaEndDate order by scheduledOn")
    public List<ConfirmAppointment> getDoctorAppointmentListOnMultipleDays(@Param(value = "clinicId") Long clinicId, @Param(value = "doctorId") Long doctorId, @Param(value = "vacaStartDate") Date vacaStartDate, @Param(value = "vacaEndDate") Date vacaEndDate);


    @Query("from ConfirmAppointment as d where d.clinicId=:clinicId and d.doctorId=:doctorId and d.status = 8 and d.vacaEndDate >= current_date")
    public List<ConfirmAppointment> checkHasAnyMultipleDayCancel(@Param(value = "clinicId") Long clinicId, @Param(value = "doctorId") Long doctorId);


    @Query("from ConfirmAppointment as d where d.clinicId=:clinicId and d.doctorId=:doctorId and d.status = 4 and d.scheduledOn between :vacaStartDate and :vacaEndDate order by scheduledOn")
    public List<ConfirmAppointment> checkHasAnyScheduledStatus(@Param(value = "clinicId") Long clinicId, @Param(value = "doctorId") Long doctorId, @Param(value = "vacaStartDate") Date vacaStartDate, @Param(value = "vacaEndDate") Date vacaEndDate);

    @Query("from ConfirmAppointment as d where d.clinicId=:clinicId and d.doctorId=:doctorId and d.status = 0 or d.status = 1 or d.status = 2 and d.vacaEndDate >= current_date")
    public List<ConfirmAppointment> checkHasAnyMultipleDayCancelStatus(@Param(value = "clinicId") Long clinicId, @Param(value = "doctorId") Long doctorId);

    @Query("from ConfirmAppointment as d where d.clinicId=:clinicId and d.doctorId=:doctorId and d.vacaStartDate=:vacaStartDate and d.vacaEndDate=:vacaEndDate ")
    public ConfirmAppointment updateCancelledMultipleDates(@Param(value = "clinicId") Long clinicId, @Param(value = "doctorId") Long doctorId, @Param(value = "vacaStartDate") Date vacaStartDate, @Param(value = "vacaEndDate") Date vacaEndDate);

    @Query("from ConfirmAppointment as d where d.clinicId=:clinicId and d.doctorId=:doctorId and d.date=:date and d.status <= 4")
    public List<ConfirmAppointment> getDoctorSlotsOnDate(@Param(value = "clinicId") Long clinicId, @Param(value = "doctorId") Long doctorId, @Param(value = "date") String date);

    @Query("from ConfirmAppointment as d where d.doctorId=:doctorId and d.status = 8 and d.vacaEndDate >= current_date")
    public List<ConfirmAppointment> checkHasAnyMultipleDayCancelForScnOpnDoctor(@Param(value = "doctorId") Long doctorId);

    @Query("from ConfirmAppointment as d where d.doctorId=:doctorId and d.date=:date and d.status <= 4")
    public List<ConfirmAppointment> getSndOpnDoctorSlotsOnDate(@Param(value = "doctorId") Long doctorId, @Param(value = "date") String date);

    @Query("from ConfirmAppointment as d where d.clinicId=:clinicId and d.doctorId=:doctorId and d.status=4 and d.date =:date and d.time =:time order by scheduledOn")
    public ConfirmAppointment checkDateTimeHasbeenBookAlr(@Param(value = "clinicId") Long clinicId, @Param(value = "doctorId") Long doctorId, @Param(value = "date") String date, @Param(value = "time") String time);


}
