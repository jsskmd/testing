package com.datalife.repositories;

import com.datalife.entities.ServiceProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by barath on 8/1/2016.
 */

@NoRepositoryBean
@Transactional
public interface BaseServiceProviderRepository<T extends ServiceProvider> extends JpaRepository<T, Long> {

}
