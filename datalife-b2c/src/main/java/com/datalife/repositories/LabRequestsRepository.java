package com.datalife.repositories;

import com.datalife.entities.LabRequests;
import com.datalife.entities.RequestStatus;
import com.datalife.entities.ServiceRequests;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


/**
 * Created by barath on 7/15/2016.
 */
@Transactional
public interface LabRequestsRepository extends BaseServiceRepository<LabRequests>{

    @Query("FROM LabRequests as sr WHERE sr.providerId=:providerId and sr.orderDate =:orderDate order by  sr.orderDateTime desc")
    public List<LabRequests> getOrdersByProviderId(@Param(value = "providerId") Long providerId,@Param(value = "orderDate") Date orderDate);


    @Query("FROM LabRequests as sr  WHERE sr.providerId=:providerId and sr.status =:status and sr.orderDate between :fromDate and :toDate order by  sr.orderDateTime desc")
    public List<LabRequests> getOrdersByProviderIdBasedOnDate(@Param(value = "providerId") Long srProviderId,@Param(value = "fromDate") Date fromDate,@Param(value = "toDate") Date toDate,@Param(value = "status")RequestStatus status);

    @Query("select sr.serviceRequests FROM UploadFile as sr WHERE (sr.serviceRequests.providerId =:providerId) and (sr.user.userContactInfo.mobilePhone =:mobilePhone or sr.serviceRequests.orderId =:orderId)")
    public List<LabRequests>serachByOrderIdAndPatDetail(@Param(value = "providerId") Long providerId,@Param(value = "orderId") Long orderId,@Param(value = "mobilePhone") String mobilePhone);

}
