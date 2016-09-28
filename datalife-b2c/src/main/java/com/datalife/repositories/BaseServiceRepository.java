package com.datalife.repositories;

import com.datalife.entities.ServiceRequests;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Created by barath on 7/20/2016.
 */

@NoRepositoryBean
public interface BaseServiceRepository<T extends ServiceRequests> extends CrudRepository<T, Long> {

}
