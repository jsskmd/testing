package com.datalife.repositories;

import com.datalife.entities.ProviderFile;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

/**
 * Created by dscblpo on 2/12/2016.
 */
@Transactional
public interface ProviderFileRepository extends CrudRepository<ProviderFile, Long> {
}
