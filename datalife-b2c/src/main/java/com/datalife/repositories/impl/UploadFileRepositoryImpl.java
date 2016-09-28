package com.datalife.repositories.impl;

import com.datalife.entities.UploadFile;
import com.datalife.repositories.custom.UploadFileRepositoryExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by supriya gondi on 12/10/2014.
 */
@Service
public class UploadFileRepositoryImpl implements UploadFileRepositoryExtension {

    @Autowired
    EntityManagerFactory em;

    @Override
    public List<UploadFile> getAllLabOrderByPatientId(Long userId, String fileType) {
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createNativeQuery("select * from records as rec where rec.userId =:input and rec.fileType =:fileType");
        query.setParameter("input", userId);
        query.setParameter("fileType", fileType);
        int b = ((BigInteger)  query.getSingleResult()).intValue();
        entityManager.close();
        return null;
    }
}
