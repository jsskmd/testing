package com.datalife.repositories;

import com.datalife.entities.Message;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Supriya on 10/20/2015.
 */
@Transactional
public interface MessageRepository extends CrudRepository<Message, Long> {

    @Query("from Message as m where m.doctorId=:doctorId and m.patientId=:patientId and m.date > current_date - 15")
    public List<Message> getMessages(@Param(value = "patientId") Long patientId, @Param(value = "doctorId") Long doctorId);

    @Query("from Message as m where m.doctorId=:doctorId and m.patientId=:patientId and m.sent=false and m.date > current_date - 15")
    public List<Message> getMessagesFromPatient(@Param(value = "patientId") Long patientId, @Param(value = "doctorId") Long doctorId);

    @Query("from Message as m where m.doctorId=:doctorId and m.patientId=:patientId and m.sent=true and m.date > current_date - 15")
    public List<Message> getMessagesFromDoctor(@Param(value = "patientId") Long patientId, @Param(value = "doctorId") Long doctorId);

    @Query("select distinct m.patientId,count(patientId) as count from Message as m where m.doctorId=:doctorId and m.visited=false ")
    public List<String> getPatients(@Param(value = "doctorId") Long doctorId);

    @Query("select count(message) as count from Message as m where m.doctorId=:doctorId and m.visited=false and m.sent=true ")
    public Long countByDoctorId(@Param(value = "doctorId") Long doctorId);

    @Query("select count(message) as count from Message as m where m.patientId=:patientId and m.visited=false and m.sent=false ")
    public Long countByPatientId(@Param(value = "patientId") Long patientId);

    @Query("select count(message) as count from Message as m where m.doctorId=:doctorId and m.patientId=:patientId and m.visited=false and m.sent=false ")
    public Long countByPatientAndDoctorId(@Param(value = "patientId") Long patientId, @Param(value = "doctorId") Long doctorId);

    @Query("select count(message) as count from Message as m where m.doctorId=:doctorId and m.patientId=:patientId and m.visited=false and m.sent=true ")
    public Long countByDoctorAndPatientId(@Param(value = "doctorId") Long doctorId, @Param(value = "patientId") Long patientId);

}
