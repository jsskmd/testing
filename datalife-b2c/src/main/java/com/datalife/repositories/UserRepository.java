package com.datalife.repositories;

import com.datalife.entities.ConfirmAppointment;
import com.datalife.entities.History;
import com.datalife.entities.User;
import com.datalife.repositories.custom.UserRepositoryExtension;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by supriya gondi on 10/24/2014.
 * <p/>
 * This repository handles User entity related CRUD operations
 */
@Transactional
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryExtension {

    public User findByUserName(String userName);

    /**
     * This query is for update enabled field in user,after user clicks on Activation Link
     *
     * @param userId
     * @param mobileVerified
     */
    @Modifying
    @Query("update User as u set u.mobileVerified=:mobileVerified where u.userId=:userId")
    public void updateMobileVerified(@Param(value = "userId") Long userId, @Param(value = "mobileVerified") boolean mobileVerified);

    @Modifying
    @Query("update User as u set u.uuid=:uuid where u.userName=:userName")
    public void updateUUID(@Param(value = "userName") String userName, @Param(value = "uuid") String uuid);

    @Query("select u.userContactInfo.email from User as u where u.userName=:userName")
    public String getEmailByUserName(@Param(value = "userName") String userName);

    @Query("select u.history from User as u where u.userId=:userId")
    public History getHistoryById(@Param(value = "userId") Long userId);

    @Query("select u.imageThumbnailFileName from User as u where u.userId=:userId")
    public String findThubnailFileName(@Param(value = "userId") Long userId);


    @Query("select u.role from User as u where u.userId=:userId")
    public String getRole(@Param(value = "userId") Long userId);

    /**
     * This query is for search user by userName
     *
     * @param userName
     * @return List<User>
     */
    @Query("from User as u where u.userName=:userName and ( u.emailVerfied=true  or u.mobileVerified=true or u.enabled=true) and u.role='ROLE_PATIENT'")
    public List<User> searchByUserName(@Param(value = "userName") String userName);

    /**
     * This query is for search user by userId
     *
     * @param userId
     * @return List<User>
     */
    @Query("from User as u where u.userId=:userId and ( u.emailVerfied=true  or u.mobileVerified=true or u.enabled=true) and u.role='ROLE_PATIENT'")
    public List<User> searchByUserId(@Param(value = "userId") Long userId);

    @Query("from User as u where u.userId=:userId and ( u.emailVerfied=true  or u.mobileVerified=true or u.enabled=true) and u.role='ROLE_DOCTOR'")
    public List<User> searchDoctorByUserId(@Param(value = "userId") Long userId);


    @Query("from User as u where u.userName=:userName and ( u.emailVerfied=true  or u.mobileVerified=true or u.enabled=true) and u.role='ROLE_DOCTOR'")
    public List<User> searchDoctorByUserName(@Param(value = "userName") String userName);

    /**
     * This query is for search user by userId
     *
     * @param userId
     * @return List<User>
     */
    @Query("from User as u where u.userId=:userId and ( u.emailVerfied=true  or u.mobileVerified=true or u.enabled=true)")
    public User searchById(@Param(value = "userId") Long userId);

/*    @Query("from User as u where u.userId=:userId and u.enabled=true")
    public boolean checkUserExists(@Param(value = "userId") Long userId);*/

    /**
     * This method is get doctor based on userId and role of user
     *
     * @param userId
     * @return User
     */
    @Query("from User as u where u.userId=:userId and ( u.emailVerfied=true  or u.mobileVerified=true or u.enabled=true) and (u.role='ROLE_DOCTOR' or u.role='ROLE_REFERRALDOCTOR')")
    public User findDoctor(@Param(value = "userId") Long userId);

    @Query("from User as u where u.userId=:userId and ( u.emailVerfied=true  or u.mobileVerified=true or u.enabled=true) and u.role='ROLE_PROVIDER' and u.serviceType='secondOpinion'")
    public User findsoProvider(@Param(value = "userId") Long userId);


    @Query("from User as u where u.userId=:userId and ( u.emailVerfied=true  or u.mobileVerified=true or u.enabled=true) and u.role='ROLE_PATIENT'")
    public User findPatient(@Param(value = "userId") Long userId);

    @Query("from User as u where u.userId=:userId and ( u.emailVerfied=true  or u.mobileVerified=true or u.enabled=true) and u.role='ROLE_CLINIC'")
    public User findClinic(@Param(value = "userId") Long userId);

    /**
     * This query is for search user by either userId,userName,firstName,lastName,mobilePhone or email
     *
     * @param userId
     * @param userName
     * @param firstName
     * @param lastName
     * @param mobilePhone
     * @param email
     * @return List<User>
     */
    @Query("from User as u where (u.userId=:userId or u.userName=:userName or u.userDetails.firstName=:firstName " +
            "or u.userDetails.lastName=:lastName or u.userContactInfo.mobilePhone=:mobilePhone or u.userContactInfo.email=:email) and ( u.emailVerfied=true  or u.mobileVerified=true or u.enabled=true) and u.role='ROLE_PATIENT' ")
    public List<User> searchUser(@Param(value = "userId") Long userId, @Param(value = "userName") String userName, @Param(value = "firstName") String firstName
            , @Param(value = "lastName") String lastName, @Param(value = "mobilePhone") String mobilePhone, @Param(value = "email") String email);

    /**
     * This query is for search user by either userId,userName,Medical Reg./Lic. Number,mobilePhone or email
     *
     * @param userId
     * @param userName
     * @param mlrNumber
     * @param mobilePhone
     * @param email
     * @return List<user>
     */
    @Query("from User as u where u.userId=:userId or u.userName=:userName or u.doctorInfo.mlrNumber=:mlrNumber or u.userContactInfo.mobilePhone=:mobilePhone or u.userContactInfo.email=:email and ( u.emailVerfied=true  or u.mobileVerified=true or u.enabled=true) and u.role='ROLE_DOCTOR' ")
    public List<User> searchDoctor(@Param(value = "userId") Long userId, @Param(value = "userName") String userName
            , @Param(value = "mlrNumber") String mlrNumber, @Param(value = "mobilePhone") String mobilePhone, @Param(value = "email") String email);

    /**
     * This query is for search user by userId
     *
     * @param uuid
     * @return List<User>
     */
    @Query("from User as u where u.uuid=:uuid")
    public User searchByUuid(@Param(value = "uuid") String uuid);

    @Query("select  u.uuid from User as u where u.userName=:userName")
    public String getUUIDbyUsername(@Param(value = "userName") String userName);

    @Modifying
    @Query("update User as u set u.enabled=:enabled,u.uuid=null where u.userName=:userName")
    public void updateEnabledActivation(@Param(value = "userName") String userName, @Param(value = "enabled") boolean enabled);

    @Query("from User as u where u.enabled=true and u.role='ROLE_DOCTOR' and u.userContactInfo.location LIKE lower(concat('%',:location,'%'))")
    public List<User> searchByLocationAndSpecialist(@Param(value = "location") String location);

    @Query("select u.name from Speciality as u where u.specialityId=:specialityId")
    public String getSpecialityNameById(@Param(value = "specialityId") Long specialityId);

    @Query("from User as u where u.userContactInfo.email=:email and u.enabled=true and u.role='ROLE_CLINIC'")
    public User checkEmailIdExistsInClinic(@Param(value = "email") String email);

    @Query("FROM ConfirmAppointment e WHERE e.patientId =:patientId AND e.scheduledOn BETWEEN :before AND :after order by scheduledOn desc")
    public List<ConfirmAppointment> getAppiontmentList(@Param(value = "patientId") Long patientId, @Param(value = "before") Date before, @Param(value = "after") Date after);

    @Query("from User as u where u.userName=:userName and ( u.emailVerfied=true  or u.mobileVerified=true or u.enabled=true) and u.role='ROLE_PATIENT'")
    public User findPatientByUserName(@Param(value = "userName") String userName);

}
