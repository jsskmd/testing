package com.datalife.repositories;

import com.datalife.entities.ServiceRequests;

import com.datalife.entities.*;
import com.datalife.repositories.custom.ServiceRequestsExtension;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by barath on 5/14/2016.
 */
@Transactional
public interface ServiceRequestsRepository extends BaseServiceRepository<ServiceRequests>,ServiceRequestsExtension  {


    @Query("FROM LabRequests as sr WHERE sr.orderId=:orderId")
    public LabRequests getLabRequestOnOrderId(@Param(value = "orderId") Long orderId);

}
