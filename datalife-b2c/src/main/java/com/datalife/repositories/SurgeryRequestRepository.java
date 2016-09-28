package com.datalife.repositories;

import com.datalife.entities.LabRequests;
import com.datalife.entities.SurgeryRequests;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by barath on 7/28/2016.
 */

@Transactional
public interface SurgeryRequestRepository extends BaseServiceRepository<SurgeryRequests>{

}