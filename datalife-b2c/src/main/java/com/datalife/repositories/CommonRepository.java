package com.datalife.repositories;

import com.datalife.entities.*;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by supriya gondi on 10/28/2014.
 * <p/>
 * This repository handles BloodGroup,Country,State,LabCategories,LabTests entities related Read operations
 */

public interface CommonRepository {

    public int anylicenceNumberExistInDb(String licNo);

    public int checkEmailIdExistAcceptPatientInDb(String email);

/*    public int checkMobileExistAcceptPatientInDb(String mobileNo);*/

    public List<Object> getDoctors(String name);

    public List<Speciality> getSpecialistLike(String input);

    public List<Speciality> getSpecialities();

    public Map<String, List<String>> getNewDoctorTimings(NewTimings newTimings);

    public List<Object> searchClinicsLike(String input);

    public List<Location> getLocationLike(String name);

    public List<Location> getLocations();

    public Object getUserDetailsByUserNameInuserView(String userName);

    public Object getDoctorDetailBydoctorIdInuserView(Long doctorId, Long clinicId);

    public Object getUserDetailsByUserIdInuserView(Long userId);

    public abstract List<City> getCities(Long stateId);

    public abstract List<BloodGroup> getBloodGroups();

    public Object getDate();

    public abstract List<RecordSpeciality> getRecordSpecialities();



    public abstract List<Country> getCountries();

   public abstract List<LabTestCategories> getLabTestCategories();

    public abstract List<State> getStates(Long countryId);

    public abstract List<String> getLabCategories();

    public abstract List<ROSCategory> getRosCategories();

    public abstract String getROSCategoryName(Byte id);
    public abstract String getBloodgroupById(Integer id);

    public abstract List<PETypes> getPECategories();

    public abstract List<IcdCode> getICDValues(String input);

    public abstract List<IcdCode> getICDName(String input);

    public abstract List<Route> getRouteName(String input);

    public abstract List<Form> getFormName(String input);

    public abstract List<Frequency> getFrequencyName(String input);

    public abstract List<Drug> getGenericNames(String input);

    public abstract List<PhysicalExamination> getPeName(Long encounterId);

    public abstract LabTests getLabTestsName(Long labTestId);

    public abstract List<CardiovascularLabels> getCardiovascularLabelList();

    public abstract List<RespiratoryLabels> getRespiratoryLabelList();

    public abstract List<GastrointestinalLabels> getGastrointestinalLabelList();

    public abstract List<MuscoloskeletalLabels> getMuscoloskeletalLabelList();

    public abstract String getCountryById(Long id);

    public abstract String getPENameById(Long id);

    public abstract PETypes getPETypesById(Long id);

    public abstract String getrespLableNameById(Long id);
    public abstract String getcardioLableNameById(Long id);
    public abstract String getmuscoloLableNameById(Long id);
    public abstract String getgastroLableNameById(Long id);

    public abstract String getStateById(Long stateId);

    public abstract String getSpecialityById(Long specialityId);

    public abstract String[] getEmails(String[] emailIds);

    public List<Object> searchByDoctorName(String firstName);

    public List<Object> searchDoctorById(Long id);

    public List<Object> searchByClinicName(String clinicName);

    public List<Object> searchClinicById(Long id);

    public List<Object> searchByLocation(String location);

    public List<Object> searchBySpeciality(Long speciality);

    public List<Object> searchBySpecialityLocation(Long speciality, String location);

    public List<Object> searchByClinicDoctorNameAndLocation(String name, String location);

    public Object findByDoctorAndClinic(Long doctorId, Long clinicId);

    public ClinicDoctors checkDayWiseSetting(ClinicDoctors clinicDoctors);

    public  Map<String, List<String>> getDailyShifts(ClinicDoctors clinicDoctors);

    public Search getRegularTimeSlots(Long doctorId, Long clinicId);

    public Map<Date,String> getTempValues(Long userId);

    public Map<Date,String> getPulseValues(Long userId);

    public Map<Date,String> getRespValues(Long userId);

    public Map<Date,String> getBpValues(Long userId);

    public Map<Date,String> getSugarValues(Long userId);

    public Map<Date,String> getBmiValues(Long userId);

    public List<User> getDoctorsBySearch(String input);

    public List<User> getPatientsBySearch(String input);

    public User getUsers(Long id);

    public Map<Long,String> getDoctorsFromClinic(Long id);

    public Map<String, List<String>> getDoctorTimings(ClinicDoctors clinicDoctors);

    public Map<Long,String> findClinicsByDoctorId(Long clinicId);

    public List<Object> searchByLocationInBangDb(String location);

    public List<Object> searchBySpecialityLocInBangDb(String speciality, String location);

    public List<Object> searchBySpecialityInBangDb(String speciality);

    public List<Object> searchByClinicNameInBangDb(String clinicName);

    public List<Object> searchByDoctorNameInBangDb(String doctorName);

    public List<Object> searchByDoctorClinicNameAndLocInBangDb(String doctorName, String location);

    public List<Object> searchByDoctorAndClinicNameInBangDb(String doctorName);

    public List<Object> searchByDoctorAndClinicName(String doctorName);

    public List<Speciality> getSpecialtyIdByName(String name);

    public User getUserDetails(Long userId);

    public Search convertObjToSearchEnty(Long doctorId,Long clinicId);


    public abstract Long getIdByCountryName(String name);

    public abstract String getRecordSpecialityById(Long specialityId);

    public String getUserLocation(Long patientId);

    public abstract String getTypeOfProcedureById(Long id);

    public String getRoleByType(String type);

    public List<Long> getHospitalCountries();

    public List<HospitalCity> getHospitalCities(Long countryId);

    public List<HospitalCity> getHospitalCities();

    public List<HospitalCity> getHospitalCitiesLike(String input);

    public List<HospitalCity> getHospitalCitiesLikeByCountryId(Long country,String input);

   /* public List<B2BUser> getServiceProviders(String speciality,String city,String role,String docName);*/

   /* public B2BUser getServiceProviderDetails(Long providerId);*/

    public List<TypeOfPRocedure> getProcedureType(Long specialityId);

    public String getProcedureTypeById(Long procedureTypeId);

    public List<Object> getSecoundOpinionDoctors(String name);

    public List<City> getCity();

    public List<City> getCityLike(String name);

    /*public List<Object> getLabTests(String name);*/

    public List<LabTests> getLabTestses();

    public List<User> getCSRUsers(String role,String state,String city,Long assignedTo);

    public User getCSRUser(Long userId);

    public Map<Long,String> getCSRUsersByRole(String role,String state,String city);

    /*public List<B2BUser> searchSubscribers(Long userId, String userName, String email, String mobileNo, String role);*/


    public List<City> getCitiesLikeBystateId(Long stateId);

    public List<City> getCitiesLikeBystateId(Long stateId,String input);


}
