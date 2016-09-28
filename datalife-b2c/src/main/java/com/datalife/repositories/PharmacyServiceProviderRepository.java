package com.datalife.repositories;

import com.datalife.entities.Lab;
import com.datalife.entities.Pharmacy;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

/**
 * Created by barath on 8/6/2016.
 */
public interface PharmacyServiceProviderRepository  extends BaseServiceProviderRepository<Pharmacy>{

    @Query("select sp from Pharmacy as sp JOIN FETCH sp.user where sp.name like CONCAT('%',:name,'%')")
    public Set<Pharmacy> getPharmacyNameLike(@Param(value = "name") String name);

    @Query("select sp from Pharmacy as sp JOIN FETCH sp.user")
    public Set<Pharmacy> getPharmacyName();

    @Query("select sp from Pharmacy as sp where sp.userId =:userId and sp.name =:name")
    public Pharmacy fetchPharmacyByUserIdAndLabName(@Param(value = "userId") Long userId,@Param(value = "name") String name);
}
