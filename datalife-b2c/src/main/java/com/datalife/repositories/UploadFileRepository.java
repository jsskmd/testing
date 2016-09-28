package com.datalife.repositories;

import com.datalife.entities.UploadFile;

import com.datalife.repositories.custom.UploadFileRepositoryExtension;
import com.datalife.repositories.custom.UserRepositoryExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by supriya gondi on 12/10/2014.
 * <p/>
 * This repository handles UploadFile entity related CRUD operations
 */
@Transactional
public interface UploadFileRepository extends CrudRepository<UploadFile, Long> {

    /**
     * This query is to get UploadFile by primary Key
     * @param fileId
     * @return Upload File
     */
    public UploadFile findOne(Long fileId);

    /**
     * This query is to get List of User Medical Records By Type i.e. Prescription, Diagnostic, MRI, X-Ray, Hospital and Others.
     *
     * @param userId
     * @param type
     * @return List<UploadFile>
     */
    @Query("from UploadFile as uf where uf.user.userId=:userId and uf.fileType=:type and uf.share=true order by uf.encounterDate desc ")
    public List<UploadFile> getFilesByTypeAndId(@Param(value = "userId") Long userId, @Param(value = "type") String type, Pageable pageable);

    @Query("from UploadFile as uf where (uf.user.userId=:userId and uf.fileType=:type and uf.share=true) or (uf.user.userId=:userId and uf.fileType=:type and uf.share=false and uf.clinicId=:clinicId and uf.doctorId=:doctorId) order by uf.encounterDate desc ")
    public List<UploadFile> getClinicFilesByTypeAndId(@Param(value = "userId") Long userId, @Param(value = "type") String type,@Param(value = "clinicId") Long clinicId,@Param(value = "doctorId") Long doctorId, Pageable pageable);

    @Modifying
    @Query("update UploadFile as uf set uf.fileType=:fileType where uf.fileId=:fileId")
    public void updateRecordType(@Param(value = "fileId") Long fileId, @Param(value = "fileType") String fileType);


    @Query("from UploadFile as uf where uf.user.userId=:userId and uf.fileType=:type and uf.share=true order by uf.encounterDate desc")
    public List<UploadFile> getAllFilesByTypeAndId(@Param(value = "userId") Long userId, @Param(value = "type") String type);


    @Query("from UploadFile as uf where (uf.user.userId=:userId and uf.fileType=:type and uf.share=true) or (uf.user.userId=:userId and uf.fileType=:type and uf.share=false and uf.clinicId=:clinicId and uf.doctorId=:doctorId) order by uf.encounterDate desc ")
    public List<UploadFile> getAllClinicFilesByTypeAndId(@Param(value = "userId") Long userId, @Param(value = "type") String type,@Param(value = "clinicId") Long clinicId,@Param(value = "doctorId") Long doctorId);


    @Query("from UploadFile as uf where uf.user.userId=:userId and uf.fileName=:fileName")
    public UploadFile checkFileExistByPatientId(@Param(value = "userId") Long userId, @Param(value = "fileName") String fileName);

    @Query("select  uf.fileName,uf.url from UploadFile as uf where uf.fileId=:fileId")
    public String getRecordInfoToShare(@Param(value = "fileId") Long fileId);

    @Query("from UploadFile as uf join fetch uf.encounter where uf.serviceRequests.orderId=:orderId")
    public UploadFile findByOrderId(@Param(value = "orderId") Long orderId);



}
