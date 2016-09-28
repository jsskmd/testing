package com.datalife.repositories;

import com.datalife.entities.Lab;
import com.datalife.entities.ServiceProvider;
import com.datalife.entities.UserContactInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Created by barath on 8/1/2016.
 */
@Transactional
public interface LabServiceProviderRepository extends BaseServiceProviderRepository<Lab>{

    @Query("select sp from Lab as sp JOIN FETCH sp.user where sp.name like CONCAT('%',:name,'%')")
    public Set<Lab> getLabNameLike(@Param(value = "name") String name);

    @Query("select sp from Lab as sp join fetch sp.user")
    public Set<Lab> getLabNameLike();

    @Query("select sp from Lab as sp where sp.userId =:userId and sp.name =:name")
    public Lab fetchLabByUserIdAndLabName(@Param(value = "userId") Long userId,@Param(value = "name") String name);

}
