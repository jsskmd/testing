package com.datalife.repositories.custom;

import com.datalife.entities.UploadFile;

import java.util.List;

/**
 * Created by supriya gondi on 12/10/2014.
 *
 * This Custom repository handles UploadFile entity related Custom logics
 */
public interface UploadFileRepositoryExtension {

    List<UploadFile> getAllLabOrderByPatientId(Long userId,String fileType);

}
