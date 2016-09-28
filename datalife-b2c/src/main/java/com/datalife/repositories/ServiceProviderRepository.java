package com.datalife.repositories;

import com.datalife.entities.ProviderDetails;
import com.datalife.entities.ServiceProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by dscblpo on 1/24/2016.
 */
@Transactional
public interface ServiceProviderRepository extends JpaRepository<ServiceProvider,Integer> {

    @Query("from ProviderDetails as pi where pi.mobilePhone=:mobilePhone")
    public  List<ProviderDetails> checkMobileExist(@Param(value = "mobilePhone") String mobilePhone);

    @Query("from ProviderDetails as pi where pi.email=:email")
    public  List<ProviderDetails> checkEmailExist(@Param(value = "email") String email);
}
