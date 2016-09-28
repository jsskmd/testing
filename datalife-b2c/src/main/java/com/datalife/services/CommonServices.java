package com.datalife.services;

import com.datalife.controller.EmailController;
import com.datalife.entities.*;
import com.datalife.repositories.CommonRepository;
import com.datalife.repositories.UserContactRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.*;

/**
 * This class has methods of returning individual Objects only.
 * <p/>
 * <p/>
 * Created by supriya gondi on 11/26/2014.
 */
public class CommonServices {

    @Autowired
    UserContactRepository userContactRepository;

    @Autowired
    CommonRepository commonRepository;

    @Autowired
    EmailController emailController;

    public static User getSessionUser() {
        return MyUserDetailsService.getUserFromSession();
    }

    public static User getOnlyUser(User u) {
        u.setUserDetails(null);
        u.setHistory(null);
        u.setIdCardDetails(null);
        u.setInsuranceDetails(null);
        u.setDoctorInfo(null);
        u.setUserEmergencyInfos(null);
        u.setUserContactInfo(null);
        u.setAuthentication(null);
        u.setUserPreferenceses(null);
        u.setVitals(null);
        u.setClinicInfo(null);
        u.setUploadFiles(null);
        u.setEncounters(null);
        u.setBills(null);
        u.setHospitalInfo(null);
        u.setServiceProvider(null);
        u.setHospitalPackages(null);
        return u;
    }
    public static ClinicDoctors getClinicDoctors(ClinicDoctors cd) {
        cd.setNewTimings(null);
        return cd;
    }

    public static User getUser(User u) {
        u.setHistory(null);
        u.setIdCardDetails(null);
        u.setInsuranceDetails(null);
        u.setAuthentication(null);
        u.setUserEmergencyInfos(null);
        u.setUserPreferenceses(null);
        u.setVitals(null);
        u.setEncounters(null);
        u.setBills(null);
        return u;
    }

    public static UserDetails getOnlyUserDetails(UserDetails ud) {
        ud.setUser(null);
        return ud;
    }

    public static ClinicInfo getOnlyClinicInfo(ClinicInfo ci) {
        ci.setUser(null);
        return ci;
    }

    public static IdCardDetails getOnlyIdCardDetails(IdCardDetails icd) {
        icd.setUser(null);
        return icd;
    }

    public static InsuranceDetails getOnlyInsuranceDetails(InsuranceDetails ins) {
        ins.setUser(null);
        return ins;
    }

    public static UserContactInfo getOnlyUserContactInfo(UserContactInfo uci) {
        uci.setUser(null);
        return uci;
    }

    public static CSRInfo getOnlyCSRInfo(CSRInfo csrInfo) {
        csrInfo.setUser(null);
        return csrInfo;
    }


    public static DoctorInfo getOnlyDoctorInfo(DoctorInfo doctorInfo) {
        doctorInfo.setUser(null);
        return doctorInfo;
    }

    public static History getOnlyHistory(History history) {
        history.setUser(null);
        return history;
    }

    public static LinkedList<UserEmergencyInfo> getOnlyEmergencyInfo(LinkedList<UserEmergencyInfo> userEmergencyInfos) {
        for (UserEmergencyInfo uei : userEmergencyInfos) {
            uei.setUser(null);
        }
        return userEmergencyInfos;
    }
    public static LinkedList<UserPreferences> getOnlyUserPreferences(LinkedList<UserPreferences> userPreferenceses) {
        for (UserPreferences up : userPreferenceses) {
            up.setUser(null);
        }
        return userPreferenceses;
    }


    public static List<Vitals> getOnlyVitals(List<Vitals> vitals) {
        for (Vitals v : vitals) {
            v.setUser(null);
            v.setEncounter(null);
        }
        return vitals;
    }


    public static List<Prescription> getOnlyPrescriptions(List<Prescription> prescriptions) {
        for (Prescription prescription : prescriptions) {
            prescription.setEncounter(null);
        }
        return prescriptions;
    }

    public static List<Location> getOnlyLocation(List<Location> location) {
        for (Location v : location) {
            v.setCity(null);
        }
        return location;
    }


    public List<Country> getListedCountries() {
        List<Long> countryIds=commonRepository.getHospitalCountries();
        List<Country> countries = new ArrayList<>();
        for (Long countryId : countryIds) {
            System.out.println(countryId);
            String countryName=commonRepository.getCountryById(countryId);
            Country c = new Country();
            c.setCountryId(countryId);
            c.setCountryName(countryName);
            countries.add(c);
        }
        return countries;
    }

    public static ServiceProvider getOnlyServiceProvider(ServiceProvider serviceProvider) {
        serviceProvider.setUser(null);
        return serviceProvider;
    }

    public static HospitalInfo getOnlyHospitalInfo(HospitalInfo hospitalInfo) {
        hospitalInfo.setUser(null);
        return hospitalInfo;
    }

    public static HospitalPackages getOnlyHospitalPackages(HospitalPackages hospitalPackages) {
        hospitalPackages.setUser(null);
        return hospitalPackages;
    }

    public static List<RecordSpeciality> getOnlyRecordSpeciality(List<RecordSpeciality> recordSpeciality) {

        for (RecordSpeciality speciality : recordSpeciality) {
            speciality.setTypeOfPRocedureList(null);
        }
        return recordSpeciality;

    }

    public static List<TypeOfPRocedure> getOnlyTypeOfProcedures(List<TypeOfPRocedure> typeOfPRocedures) {

        for (TypeOfPRocedure speciality : typeOfPRocedures) {
            speciality.setRecordSpeciality(null);
        }
        return typeOfPRocedures;
    }

    public static List<LabRequests> getLabRequests(List<LabRequests> labRequestsList) {

        for (LabRequests labRequests : labRequestsList) {
            if(labRequests.getUploadFile() != null){
                labRequests.setUploadFile(null);
            }
        }
        return labRequestsList;
    }

    public static List<PharmacyRequests> getPharmacyRequests(List<PharmacyRequests> pharmacyRequestsList) {

        for (PharmacyRequests pharmacyRequests : pharmacyRequestsList) {
            if(pharmacyRequests.getUploadFile() != null){
                pharmacyRequests.setUploadFile(null);
            }
        }
        return pharmacyRequestsList;
    }
}
