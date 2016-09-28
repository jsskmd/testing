package com.datalife.repositories.custom;

import com.datalife.entities.Vitals;

import java.util.Map;

/**
 * Created by supriya gondi on 11/1/2014.
 *
 * This Custom repository handles Vitals entity related Custom logics
 */
public interface VitalRepositoryExtension {

    /**
     * This method is to get Vitals of user from database by userId
     * @param id
     * @return Map<String,Object> i.e. Vitals
     */
    public Map<String,Object> getVitalValues(Long id, boolean monitored);



    /**
     * This method is for validate Vitals means if Vital value is null, then it will be replaced with '-'
     * @param vitals
     * @return Vitals
     */
    public Vitals validateVitals(Vitals vitals);
}
