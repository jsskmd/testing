package com.datalife.repositories;

import com.datalife.entities.HospitalInfo;
import com.datalife.entities.HospitalPackages;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by dscblpo on 2/4/2016.
 */
public interface HospitalPackagesRepository extends CrudRepository<HospitalPackages,Integer> {


    @Query("from HospitalPackages as hi where hi.user.userId=:userId")
    public List<HospitalPackages> getPackagesByUserId(@Param(value = "userId") Long userId);
/*

    @Query("from HospitalPackages as hi where hi.typeOfProcedure=:typeOfProcedure and hi.speciality=:speciality and hi.user.userContactInfo.city =: city")
    public List<HospitalPackages> getPackagesByTopSpecialityAndCity(@Param(value = "typeOfProcedure") String typeOfProcedure,@Param(value = "speciality") String speciality,@Param(value = "city") String city);
*/

    @Query("from HospitalPackages as hi where hi.typeOfProcedure=:typeOfProcedure and hi.speciality=:speciality")
    public List<HospitalPackages> getPackagesByTopAndSpeciality(@Param(value = "typeOfProcedure") String typeOfProcedure, @Param(value = "speciality") String speciality);

}
