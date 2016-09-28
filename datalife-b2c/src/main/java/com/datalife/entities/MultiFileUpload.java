package com.datalife.entities;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by supriya gondi on 8/6/2014.
 *
 * MultiFileUpload is to accept Multipart request from client while uploading files
 */

public class MultiFileUpload {

    private List<MultipartFile> multiUploadedFileList;

    public List<MultipartFile> getMultiUploadedFileList() {
        return multiUploadedFileList;
    }

    public void setMultiUploadedFileList(List<MultipartFile>
                                                 multiUploadedFileList) {
        this.multiUploadedFileList = multiUploadedFileList;
    }


}

