package com.datalife.repositories;

import com.datalife.entities.Audit;
import com.datalife.repositories.custom.AuditRepositoryExtension;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Dipak on 2/6/2015.
 *
 *  This repository handles Audit entity related CRUD operations
 */
public interface AuditRepository extends CrudRepository<Audit,Long>,AuditRepositoryExtension {
}
