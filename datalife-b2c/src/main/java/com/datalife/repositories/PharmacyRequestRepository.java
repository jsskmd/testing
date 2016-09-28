package com.datalife.repositories;

import com.datalife.entities.LabRequests;
import com.datalife.entities.PharmacyRequests;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by barath on 7/19/2016.
 */
@Transactional
public interface PharmacyRequestRepository extends BaseServiceRepository<PharmacyRequests> {

    @Query("FROM PharmacyRequests as sr WHERE sr.providerId=:providerId and sr.orderDate =:orderDate order by  sr.orderDate desc")
    public List<PharmacyRequests> getOrdersByProviderId(@Param(value = "providerId") Long providerId,@Param(value = "orderDate") Date orderDate);

    @Query("FROM PharmacyRequests as sr  WHERE sr.providerId=:providerId and sr.orderDate between :fromDate and :toDate order by  sr.orderDateTime desc")
    public List<PharmacyRequests> getOrdersByProviderIdBasedOnDate(@Param(value = "providerId") Long srProviderId,@Param(value = "fromDate") Date fromDate,@Param(value = "toDate") Date toDate);

}
