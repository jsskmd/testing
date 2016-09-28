package com.datalife.repositories;

import com.datalife.entities.Prescription;
import com.datalife.repositories.custom.PrescriptionRepositoryExtension;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

/**
 * Created by supriya gondi on 11/7/2014.
 *
 * This repository handles Prescription entity related CRUD operations
 */
@Transactional
public interface PrescriptionRepository extends CrudRepository<Prescription,Long>,PrescriptionRepositoryExtension {
}
