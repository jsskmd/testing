package com.datalife.servicelayers;

import com.datalife.controller.EmailController;
import com.datalife.entities.*;
import com.datalife.repositories.*;
import com.datalife.services.*;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by supriya gondi on 11/26/2014.
 */
@RestController
@RequestMapping(value = "/common/")
public class CommonService {
    @Autowired
    LabTestRepository labTestRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UtilityServices utilityServices;

    @Autowired
    UserDetailsRepository userDetailsRepository;

    @Autowired
    UserContactRepository userContactRepository;

    @Autowired
    ClinicDoctorsRepository clinicDoctorsRepository;

    @Autowired
    DoctorInfoRepository doctorInfoRepository;

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    CommonRepository commonRepository;

    @Autowired
    EmailController emailController;

    @Autowired
    AlfrescoAuthDetails alfrescoAuthDetails;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private LabServiceProviderRepository labServiceProviderRepository;

    @Autowired
    private PharmacyServiceProviderRepository pharmacyServiceProviderRepository;

    /**
     * This method is for get List of states of particular country
     *
     * @param countryId
     * @return List<States> based on countryId
     */
    @RequestMapping(value = "getStates/{countryId}", method = RequestMethod.GET, produces = {"application/json"})
    public List<State> getStatesByCountryId(@PathVariable Long countryId) {
        List<State> stateList = commonRepository.getStates(countryId);
        List<State> states = new ArrayList<State>();
        for (State state : stateList) {
            State s = new State();
            s.setStateId(state.getStateId());
            s.setStateName(state.getStateName());
            states.add(s);
        }
        return states;
    }

    @RequestMapping(value = "getTempValues/{userId}", method = RequestMethod.GET, produces = {"application/json"})
    public Map<Date, String> getTempValues(@PathVariable Long userId) {
        return commonRepository.getTempValues(userId);
    }

    @RequestMapping(value = "getPulseValues/{userId}", method = RequestMethod.GET, produces = {"application/json"})
    public Map<Date, String> getPulseValues(@PathVariable Long userId) {
        return commonRepository.getPulseValues(userId);
    }

    @RequestMapping(value = "getRespValues/{userId}", method = RequestMethod.GET, produces = {"application/json"})
    public Map<Date, String> getRespValues(@PathVariable Long userId) {
        return commonRepository.getRespValues(userId);
    }

    @RequestMapping(value = "getBmiValues/{userId}", method = RequestMethod.GET, produces = {"application/json"})
    public Map<Date, String> getBmiValues(@PathVariable Long userId) {
        return commonRepository.getBmiValues(userId);
    }

    @RequestMapping(value = "getBpValues/{userId}", method = RequestMethod.GET, produces = {"application/json"})
    public List<Map<Date, String>> getBpValues(@PathVariable Long userId) {
        List<Map<Date, String>> result = new LinkedList<>();
        Map<Date, String> systolic = new LinkedHashMap<>();
        Map<Date, String> diastolic = new LinkedHashMap<>();

        Map<Date, String> values = commonRepository.getBpValues(userId);
        for (Map.Entry<Date, String> o : values.entrySet()) {
            String value = o.getValue();
            if (StringUtils.isNotBlank(value)) {
                String[] bps = value.split("/");
                systolic.put(o.getKey(), bps[0]);
                diastolic.put(o.getKey(), bps[1]);
            }
        }
        result.add(systolic);
        result.add(diastolic);
        return result;
    }

    @RequestMapping(value = "getSugarValues/{userId}", method = RequestMethod.GET, produces = {"application/json"})
    public Map<Date, String> getSugarValues(@PathVariable Long userId) {
        return commonRepository.getSugarValues(userId);
    }

    /**
     * This method is for get List of states of particular country
     *
     * @param name
     * @return List<States> based on countryId
     */
    @RequestMapping(value = "search/{name}", method = RequestMethod.GET, produces = {"application/json"})
    public List<DoctorSearch> getDoctorsByName(@PathVariable String name) {

        List<DoctorSearch> doctors = new ArrayList<>();
        Set<DoctorSearch> doctorSearchSet = new HashSet<>();

        List<Object> doctorList = commonRepository.getDoctors(name);
        for (Object aDoctorList : doctorList) {
            Object[] o = (Object[]) aDoctorList;
            DoctorSearch search = new DoctorSearch();
            search.setName(o[1] + " " + o[2]);
            search.setUserId(o[0]);
            search.setCategories("DOCTOR");
            doctorSearchSet.add(search);
        }

        List<Object> searchClinicsLike = commonRepository.searchClinicsLike(name);
        for (Object aSearchClinicsLike : searchClinicsLike) {
            Object[] o = (Object[]) aSearchClinicsLike;
            DoctorSearch search = new DoctorSearch();
            search.setName(o[1]);
            search.setUserId(o[0]);
            search.setCategories("CLINIC");
            doctorSearchSet.add(search);
        }

        List<Speciality> specialist = commonRepository.getSpecialistLike(name);
        for (Speciality speciality : specialist) {
            DoctorSearch search = new DoctorSearch();
            search.setName(speciality.getName());
            search.setUserId(speciality.getSpecialityId());
            search.setCategories("SPECIALTY");
            doctorSearchSet.add(search);
        }
        doctors.addAll(doctorSearchSet);
        return doctors;
    }

    @RequestMapping(value = "getSpeciality", method = RequestMethod.GET, produces = {"application/json"})
    public List<DoctorSearch> getSpeciality() {
        List<DoctorSearch> doctors = new ArrayList<>();
        Set<DoctorSearch> doctorSearchSet = new HashSet<>();

        List<Speciality> specialist = commonRepository.getSpecialities();
        for (Speciality speciality : specialist) {
            DoctorSearch search = new DoctorSearch();
            search.setName(speciality.getName());
            search.setUserId(speciality.getSpecialityId());
            search.setCategories("SPECIALTY");
            doctorSearchSet.add(search);
        }
        doctors.addAll(doctorSearchSet);
        return doctors;
    }


    @RequestMapping(value = "getLocationLike/{query}", method = RequestMethod.GET, produces = {"application/json"})
    public List<Location> getCitiesLike(@PathVariable String query) {
        return CommonServices.getOnlyLocation(commonRepository.getLocationLike(query));
    }

    @RequestMapping(value = "getLocations", method = RequestMethod.GET, produces = {"application/json"})
    public List<Location> getLocations() {
        return CommonServices.getOnlyLocation(commonRepository.getLocations());
    }


    @RequestMapping(value = "getState", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<Long, String> getStateByCountryId(@RequestBody Country country, BindingResult result, ModelMap modelMap) {
        List<State> stateList = commonRepository.getStates(country.getCountryId());
        Map<Long, String> states = new HashMap<>();
        for (State state : stateList) {
            states.put(state.getStateId(), state.getStateName());
        }
        return states;
    }

    /**
     * This method is for get List of Cities of particular State
     *
     * @param state
     * @return List<City> based on staetId
     */
    @RequestMapping(value = "getCities", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<Long, String> getCitiesByStateId(@PathVariable State state) {
        List<City> cityList = commonRepository.getCities(state.getStateId());
        Map<Long, String> cities = new HashMap<>();
        for (City city : cityList) {
            cities.put(city.getCityId(), city.getName());
        }
        return cities;
    }


    @RequestMapping(value = "getCitiesLike/{stateId}/{query}", method = RequestMethod.GET, produces = {"application/json"})
    public List<City> getCitiesLikeBystateId(@PathVariable Long stateId, @PathVariable String query) {
        return commonRepository.getCitiesLikeBystateId(stateId, query);
    }

    @RequestMapping(value = "getCities/{stateId}", method = RequestMethod.GET, produces = {"application/json"})
    public List<City> getCitiesLike(@PathVariable Long stateId) {
        return commonRepository.getCitiesLikeBystateId(stateId);
    }

    /**
     * This method is for List of Countries
     * @return List<Country>
     */
    @RequestMapping(value = "getCountries", method = RequestMethod.GET, produces = {"application/json"})
    public List<Country> getCountries() {
        List<Country> countryList = commonRepository.getCountries();
        List<Country> countries = new ArrayList<Country>();
        for (Country country : countryList) {
            Country c = new Country();
            c.setCountryId(country.getCountryId());
            c.setCountryName(country.getCountryName());
            countries.add(c);
        }
        return countries;
    }

    @RequestMapping(value = "getHospitalCountries", method = RequestMethod.GET, produces = {"application/json"})
    public List<Country> getHospitalCountries() {
        List<Long> countryIds = commonRepository.getHospitalCountries();
        List<Country> countries = new ArrayList<Country>();
        for (Long countryId : countryIds) {
            String countryName = commonRepository.getCountryById(countryId);
            Country c = new Country();
            c.setCountryId(countryId);
            c.setCountryName(countryName);
            countries.add(c);
        }
        return countries;
    }

    @RequestMapping(value = "getHospitalCities/{countryId}", method = RequestMethod.GET, produces = {"application/json"})
    public List<HospitalCity> getHospitalCities(@PathVariable Long countryId) {
        return commonRepository.getHospitalCities(countryId);
    }

    @RequestMapping(value = "getHospitalCitiesLike/{countryId}/{query}", method = RequestMethod.GET, produces = {"application/json"})
    public List<HospitalCity> getHospitalCitiesLikeByCountryId(@PathVariable Long countryId, @PathVariable String query) {
        return commonRepository.getHospitalCitiesLikeByCountryId(countryId, query);
    }

    @RequestMapping(value = "getHospitalCitiesLike/{query}", method = RequestMethod.GET, produces = {"application/json"})
    public List<HospitalCity> getHospitalCitiesLike(@PathVariable String query) {
        return commonRepository.getHospitalCitiesLike(query);
    }

    @RequestMapping(value = "getHospitalCities", method = RequestMethod.GET, produces = {"application/json"})
    public List<HospitalCity> getHospitalCities() {
        return commonRepository.getHospitalCities();
    }

    @RequestMapping(value = "getDoctorSpeciality", method = RequestMethod.GET, produces = {"application/json"})
    public List<Speciality> getDoctorSpeciality() {
        return commonRepository.getSpecialities();
    }

    @RequestMapping(value = "getLabTestCategories", method = RequestMethod.GET, produces = {"application/json"})
    public List<LabTestCategories> getLabTestCategories() {
        List<LabTestCategories> testCategoriesList = commonRepository.getLabTestCategories();
        List<LabTestCategories> categorieses = new ArrayList<LabTestCategories>();
        for (LabTestCategories testCategories : testCategoriesList) {
            LabTestCategories c = new LabTestCategories();
            c.setLabTestCategoriesId(testCategories.getLabTestCategoriesId());
            c.setLabTestName(testCategories.getLabTestName());
            categorieses.add(c);
        }
        return categorieses;
    }

    /**
     * This method is for List of BloodGroups
     * @return list<Bloodgroup>
     */
    @RequestMapping(value = "getBloodGroups", method = RequestMethod.GET, produces = {"application/json"})
    public List<BloodGroup> getBloodGroups() {
        return commonRepository.getBloodGroups();
    }

    @RequestMapping(value = "getRecordSpecialities", method = RequestMethod.GET, produces = {"application/json"})
    public List<RecordSpeciality> getSpecialities() {
        return CommonServices.getOnlyRecordSpeciality(commonRepository.getRecordSpecialities());
    }


    @RequestMapping(value = "getProcedureTypes/{specialityId}", method = RequestMethod.GET, produces = {"application/json"})
    public Map<String, Object> getProcedureType(@PathVariable Long specialityId) {
        Map<String, Object> response = new HashMap<String, Object>();
        List<TypeOfPRocedure> getProcedureType = CommonServices.getOnlyTypeOfProcedures(commonRepository.getProcedureType(specialityId));
        response.put("getProcedureType", getProcedureType);
        return response;
    }

    @RequestMapping(value = "getLabName/{query}", method = RequestMethod.GET, produces = {"application/json"})
    public Set<Lab> getLabNameLike(@PathVariable String  query) {

        Set<Lab> labSet = labServiceProviderRepository.getLabNameLike(query);
        Set<Lab> labs = new HashSet<>();
        for (Lab lab : labSet) {
            Lab lab1 = new Lab(lab.getUserId(),lab.getName(),lab.getUser().getUserContactInfo().getLocation());
            labs.add(lab1);
        }
        return labs;
    }

    @RequestMapping(value = "getLabName", method = RequestMethod.GET, produces = {"application/json"})
    public Set<Lab> getLabNameLike() {
        Set<Lab> labSet = labServiceProviderRepository.getLabNameLike();
        Set<Lab> labs = new HashSet<>();
        for (Lab lab : labSet) {
            Lab lab1 = new Lab(lab.getUserId(),lab.getName(),lab.getUser().getUserContactInfo().getLocation());
            labs.add(lab1);
        }
        return labs;
    }

    @RequestMapping(value = "getPharmacyName/{query}", method = RequestMethod.GET, produces = {"application/json"})
    public Set<Pharmacy> getPharmacyName(@PathVariable String  query) {

        Set<Pharmacy> pharmacySet = pharmacyServiceProviderRepository.getPharmacyNameLike(query);
        Set<Pharmacy> pharmacies = new HashSet<>();
        for (Pharmacy pharmacy : pharmacySet) {
            Pharmacy pharmacy1 = new Pharmacy(pharmacy.getUserId(),pharmacy.getName(),pharmacy.getUser().getUserContactInfo().getLocation());
            pharmacies.add(pharmacy1);
        }
        return pharmacies;
    }

    @RequestMapping(value = "getPharmacyName", method = RequestMethod.GET, produces = {"application/json"})
    public Set<Pharmacy> getPharmacyName() {

        Set<Pharmacy> pharmacySet = pharmacyServiceProviderRepository.getPharmacyName();
        Set<Pharmacy> pharmacies = new HashSet<>();
        for (Pharmacy pharmacy : pharmacySet) {
            Pharmacy pharmacy1 = new Pharmacy(pharmacy.getUserId(),pharmacy.getName(),pharmacy.getUser().getUserContactInfo().getLocation());
            pharmacies.add(pharmacy1);
        }
        return pharmacies;
    }



    /**
     * This method is for List of LabTests based on Lab Category
     * @return Map<String, Object> where the key is Lab Category and value is List of LabTests based on CategoryId
     */
    @RequestMapping(value = "getLabValues", method = RequestMethod.GET, produces = {"application/json"})
    public Map<String, Object> getLabOrderVlaues() {
        Map<String, Object> respose = new HashMap<String, Object>();

        respose.put("labCategories", commonRepository.getLabCategories());
        respose.put("hmtValues", labTestRepository.getLabCategoriesById(1L));
        respose.put("sgyValues", labTestRepository.getLabCategoriesById(2L));
        respose.put("cpgyValues", labTestRepository.getLabCategoriesById(3L));
        respose.put("cgyValues", labTestRepository.getLabCategoriesById(4L));
        respose.put("bcyValues", labTestRepository.getLabCategoriesById(5L));
        respose.put("mbgyValues", labTestRepository.getLabCategoriesById(6L));
        respose.put("hrtValues", labTestRepository.getLabCategoriesById(7L));
        return respose;
    }

    @RequestMapping(value = "getICD/{query}", method = RequestMethod.GET, produces = {"application/json"})
    public List<IcdCode> getICDValues(@PathVariable String query) {
        return commonRepository.getICDValues(query);
    }

    @RequestMapping(value = "getICDName/{query}", method = RequestMethod.GET, produces = {"application/json"})
    public List<IcdCode> getICDName(@PathVariable String query) {
        return commonRepository.getICDName(query);
    }


    @RequestMapping(value = "getRouteName/{query}", method = RequestMethod.GET, produces = {"application/json"})
    public List<Route> getRouteName(@PathVariable String query) {
        return commonRepository.getRouteName(query);
    }

    @RequestMapping(value = "getFormName/{query}", method = RequestMethod.GET, produces = {"application/json"})
    public List<Form> getFormName(@PathVariable String query) {
        return commonRepository.getFormName(query);
    }

    @RequestMapping(value = "getFrequencyName/{query}", method = RequestMethod.GET, produces = {"application/json"})
    public List<Frequency> getFrequencyName(@PathVariable String query) {
        return commonRepository.getFrequencyName(query);
    }

/*    @RequestMapping(value = "getBrandName/{query}", method = RequestMethod.GET, produces = {"application/json"})
    public List<Drug> getBrandNames(@PathVariable String query) {
        List<Drug> D=commonRepository.getBrandNames(query);
        return commonRepository.getBrandNames(query);
    }*/

    @RequestMapping(value = "getGenericName/{query}", method = RequestMethod.GET, produces = {"application/json"})
    public List<Drug> getGenericNames(@PathVariable String query) {
        List<Drug> D = commonRepository.getGenericNames(query);
        return commonRepository.getGenericNames(query);
    }

    @RequestMapping(value = "searchDoctor", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> getDoctorList(@RequestBody User user, BindingResult result) throws ParseException {
        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = utilityServices.getMessage("Doctor.notFound.MESSAGE");
        Date date = (Date) commonRepository.getDate();
        List<Object> searchResults = new LinkedList<>();
        List<Object> dbs = new ArrayList<>();
        List<Object> searchList = new ArrayList<>();

        String inputLocation = user.getLocation();
        String inputCriteria = user.getFlname();

        if (inputLocation != null && (!StringUtils.isBlank(inputLocation)) && (inputCriteria != null) && (!StringUtils.isBlank(inputCriteria))) {
            // location and criteria are both valid
            List<Speciality> specialityList = commonRepository.getSpecialistLike(inputCriteria);
            for (Speciality speciality : specialityList) {
                List<Object> list1 = commonRepository.searchBySpecialityLocation(speciality.getSpecialityId(), inputLocation);
                List<Object> list2 = commonRepository.searchByClinicDoctorNameAndLocation(inputCriteria, inputLocation);
                if (list1.size() > 0) {
                    searchList.addAll(list1);
                }

                if (list2.size() > 0) {
                    searchList.addAll(list2);
                }

                List<Object> dbList1 = commonRepository.searchBySpecialityLocInBangDb(speciality.getName(), inputLocation);
                List<Object> dbList2 = commonRepository.searchByDoctorClinicNameAndLocInBangDb(inputCriteria, inputLocation);

                if (dbList1.size() > 0) {
                    dbs.addAll(dbList1);
                }
                if (dbList2.size() > 0) {
                    dbs.addAll(dbList2);
                }
            }

        }
        else {
            if (inputCriteria != null && !StringUtils.isBlank(inputCriteria)) {
                // only name is valid but not location
                List<Speciality> specialityList = commonRepository.getSpecialistLike(inputCriteria);
                if(specialityList.size() > 0 ) {
                    for (Speciality speciality : specialityList) {
                        List<Object> list1 = commonRepository.searchBySpeciality(speciality.getSpecialityId());
                        List<Object> list2 = commonRepository.searchByDoctorAndClinicName(inputCriteria);


                        if (list1.size() > 0) {
                            searchList.addAll(list1);
                        }

                        if (list2.size() > 0) {
                            searchList.addAll(list2);
                        }

                        List<Object> dbList1 = commonRepository.searchBySpecialityInBangDb(speciality.getName());
                        List<Object> dbList2 = commonRepository.searchByDoctorAndClinicNameInBangDb(inputCriteria);

                        if (dbList1.size() > 0) {
                            dbs.addAll(dbList1);
                        }
                        if (dbList2.size() > 0) {
                            dbs.addAll(dbList2);
                        }
                    }
                }else {
                    List<Object> list2 = commonRepository.searchByDoctorAndClinicName(inputCriteria);
                    if (list2.size() > 0) {
                        searchList.addAll(list2);
                    }
                    List<Object> dbList2 = commonRepository.searchByDoctorAndClinicNameInBangDb(inputCriteria);
                    if (dbList2.size() > 0) {
                        dbs.addAll(dbList2);
                    }
                }

            }
            else {
                if (inputLocation != null && !StringUtils.isBlank(inputLocation)) {
                    // only location is valid but not name
                    searchList = commonRepository.searchByLocation(inputLocation);
                    dbs = commonRepository.searchByLocationInBangDb(inputLocation);
                }
                else {
                    // both location and criteria are invalid
                    Long patientId = user.getPatientId();
                    String patientLocation = commonRepository.getUserLocation(patientId);
                    searchList = commonRepository.searchByLocation(patientLocation);
                    dbs = commonRepository.searchByLocationInBangDb(patientLocation);
                }
            }
        }

        searchResults = searchList;
        searchResults = iterateTheList(searchResults);
        searchResults.addAll(iterateTheDBList(dbs));

        if (searchResults.size() > 0) {
            response.put("users", searchResults);
            status = HttpStatus.OK;
            message = "";
        }
        else {
            status = HttpStatus.CONTINUE;
            message = "Your search criteria did not find any matching records";
        }

        DateTime maxDateToShow = new DateTime(new Date()).withTimeAtStartOfDay().plusDays(180);
        response.put("maxDateToShow", DateService.dateToStringConversion(maxDateToShow.toDate()));
        response.put("serverDate", date);
        response.put("message", message);
        response.put("statusCode", status.value());
        return response;
    }

    public List<Object> iterateTheList(List<Object> searchList) {
        List<Object> searchResults = new ArrayList<>();

        if (searchList != null && !searchList.isEmpty()) {
            DoctorInfo doctorInfo;
            for (Object aSearchList : searchList) {
                Object[] obj = (Object[]) aSearchList;
                Search currentUser = new Search();

                currentUser.setDoctorName(obj[10] + " " + obj[9]);
                if (obj[0] != null) {
                    currentUser.setDoctorId(Long.parseLong(obj[0].toString()));
                }
                if (obj[1] != null) {
                    currentUser.setClinicId(Long.parseLong(obj[1].toString()));
                }

                if (obj[2] != null) {
                    currentUser.setSpecialityId(Long.parseLong(obj[2].toString()));
                }

                if (obj[4] != null) {
                    currentUser.setAddress(obj[4].toString());
                }

                if (obj[3] != null) {
                    currentUser.setLocation(obj[3].toString());
                }

                if (obj[6] != null) {
                    currentUser.setCity(obj[6].toString());
                }
                if (obj[7] != null) {
                    currentUser.setClinicName(obj[7].toString());
                }

                if (obj[11] != null) {
                    currentUser.setConsultationFee(obj[11].toString());
                }
                if (obj[12] != null) {
                    currentUser.setExperience(Byte.parseByte(obj[12].toString()));
                }

                Long doctorId = Long.parseLong(obj[0].toString());
                Long clinic = currentUser.getClinicId();
                doctorInfo = CommonServices.getOnlyDoctorInfo(doctorInfoRepository.findDoctor(doctorId));
                if (doctorInfo != null) {
                    if (doctorInfo.getQualification() != null) {
                        currentUser.setQualification(doctorInfo.getQualification());
                    }
                }

                Search search = commonRepository.getRegularTimeSlots(doctorId, clinic);
                if (search != null) {
                    currentUser.setCdGeneralTime(search.getCdGeneralTime());
                }
                Long specialityId = currentUser.getSpecialityId();
                if (specialityId != null) {
                    String specialityName = userRepository.getSpecialityNameById(specialityId);
                    if (specialityName != null) {
                        currentUser.setSpecialityName(specialityName);
                    }
                }
                String url = userRepository.findThubnailFileName(doctorId);
                if (url != null && !url.isEmpty()) {
                    currentUser.setDoctorImageUrl(utilityServices.getMessage("User.profileImage.Url") + doctorId);
                } else {
                    currentUser.setDoctorImageUrl(utilityServices.getMessage("Doctor.DefaultImage.Url"));
                }
                if (!searchResults.contains(currentUser)) {
                    searchResults.add(currentUser);
                }
            }
        }
        return searchResults;
    }

    public List<Object> iterateTheDBList(List<Object> dbs) {
        List<Object> searchResults = new LinkedList<>();
        if (dbs != null && !dbs.isEmpty()) {

            for (Object db : dbs) {
                Object[] obj = (Object[]) db;
                Search currentUser = new Search();

                if (obj[4] != null) {
                    currentUser.setClinicName(obj[4].toString());
                }
                if (obj[0] != null) {
                    currentUser.setDoctorId(Long.parseLong(obj[0].toString()));
                }

                if (obj[1] != null) {
                    currentUser.setSpecialityName(obj[1].toString());
                }
                if (obj[6] != null && StringUtils.isNotBlank(obj[6].toString())) {
                    currentUser.setExperience(Byte.parseByte(obj[6].toString()));
                }
                if (obj[3] != null) {
                    currentUser.setCity(obj[3].toString());
                }
                if (obj[2] != null) {
                    currentUser.setAddress(obj[2].toString());
                }
                if (obj[5] != null) {
                    currentUser.setDoctorName(obj[5].toString());
                }
                if (obj[7] != null) {
                    currentUser.setLocation(obj[7].toString());
                }
                if (obj[8] != null) {
                    currentUser.setQualification(obj[8].toString());
                }
                currentUser.setDoctorImageUrl(utilityServices.getMessage("Doctor.DefaultImage.Url"));
                if (!searchResults.contains(currentUser)) {
                    searchResults.add(currentUser);
                }
            }
        }
        return searchResults;
    }

    @RequestMapping(value = "getSelectedDoctor", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> getSelectedDoctor(@RequestBody User user, BindingResult result, ModelMap modelMap) {

        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = utilityServices.getMessage("Doctor.notFound.MESSAGE");
        if (user != null) {
            if (user.getDoctorId() != null && user.getClinicId() != null) {
                Object obj[] = (Object[]) commonRepository.findByDoctorAndClinic(user.getDoctorId(), user.getClinicId());
                Search search = new Search();

                search.setDoctorName(obj[10] + " " + obj[9]);
                search.setDoctorId(Long.parseLong(obj[0].toString()));
                search.setClinicId(Long.parseLong(obj[1].toString()));
                if (obj[2] != null) {
                    search.setSpecialityId(Long.parseLong(obj[2].toString()));
                }

                if (obj[4] != null) {
                    search.setAddress(obj[4].toString());
                }

                if (obj[3] != null) {
                    search.setLocation(obj[3].toString());
                }

                if (obj[6] != null) {
                    search.setCity(obj[6].toString());
                }
                if (obj[7] != null) {
                    search.setClinicName(obj[7].toString());
                }
                Long specialityId = search.getSpecialityId();
                if (specialityId != null) {
                    String specialityName = userRepository.getSpecialityNameById(specialityId);
                    if (specialityName != null) {
                        search.setSpecialityName(specialityName);
                    }
                }
                response.put("searchresult", search);
                status = HttpStatus.OK;
                message = "";
            }
        }
        response.put("message", message);
        response.put("statusCode", status.value());
        return response;
    }

    @RequestMapping(value = "saveConfirmedAppt", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public synchronized Map<String, Object> saveConfirmedAppt(@RequestBody ConfirmAppointment confirmAppointment, BindingResult result, ModelMap modelMap) throws IOException, IOException {

        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = utilityServices.getMessage("user.scheduled.success");
        String role = MyUserDetailsService.getUserFromSession().getRole();

        if ((confirmAppointment.getClinicId() != null) && (confirmAppointment.getDoctorId() != null) && (confirmAppointment.getPatientId() != null)) {
            confirmAppointment.setScheduledOn(new Date());
            List<ConfirmAppointment> appointment = appointmentRepository.checkPatientAppSched(confirmAppointment.getClinicId(), confirmAppointment.getDoctorId(), confirmAppointment.getPatientId(), confirmAppointment.getScheduledOn());
            String dateTime = confirmAppointment.getDate() + " " + confirmAppointment.getTime();

            if (appointment.size() <= 2) {
                ConfirmAppointment appointment1 = appointmentRepository.checkDateTimeHasbeenBookAlr(confirmAppointment.getClinicId(), confirmAppointment.getDoctorId(), confirmAppointment.getDate(), confirmAppointment.getTime());
                if (appointment1 != null && SchedulingStatus.CANCELLED.equals(appointment1.getStatus())) {

                    appointment1.setPatientId(confirmAppointment.getPatientId());
                    appointment1.setScheduledOn(DateService.stringToDateDMY(dateTime));
                    appointment1.setCreatedDateTime(DateService.getTodayDateTime());
                    appointment1.setStatus(SchedulingStatus.SCHEDULED);
                    if (confirmAppointment.getReasonForVisit() != null) {
                        appointment1.setReasonForVisit(confirmAppointment.getReasonForVisit());
                    }
                    appointment1.setScheduledBy(role);
                    appointmentRepository.save(appointment1);
                    response.put("role", role);
                    response.put("message", message);
                    status = HttpStatus.OK;

                } else if (appointment1 == null) {

                    confirmAppointment.setScheduledOn(DateService.stringToDateDMY(dateTime));
                    confirmAppointment.setCreatedDateTime(DateService.getTodayDateTime());
                    confirmAppointment.setStatus(SchedulingStatus.SCHEDULED);
                    confirmAppointment.setScheduledBy(role);
                    appointmentRepository.save(confirmAppointment);

                    Search docInf = commonRepository.convertObjToSearchEnty(confirmAppointment.getDoctorId(), confirmAppointment.getClinicId());
                    User patInf = commonRepository.getUserDetails(confirmAppointment.getPatientId());
                    SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy, hh:mm aaa");
                    String mobilePhone = null;
                    if (docInf.getMobilePhone() != null && !docInf.getMobilePhone().isEmpty() || docInf.getHomePhone() != null && !docInf.getHomePhone().isEmpty()) {
                        mobilePhone = docInf.getMobilePhone() + " " + docInf.getHomePhone();
                    }
                    String address = docInf.getAddress() + " " + docInf.getLocation() + " " + docInf.getCity();
                    emailController.sendAppointmentConfirmation(docInf.getDoctorName(), docInf.getClinicName(), mobilePhone, address, sdf.format(confirmAppointment.getScheduledOn()), patInf);

                    String messageBody = "Mr/Ms." + patInf.getFlname() + ", Your appointment is scheduled with " + docInf.getDoctorName() + " on " + sdf.format(confirmAppointment.getScheduledOn()) + ".ClinicName: " + docInf.getClinicName() + ",Address:" + address + ".";
                    alfrescoAuthDetails.sendMessage(patInf.getMobileNo(), messageBody);

                    Date curDate = DateService.stringToDateConversion(DateService.getTodayDate());
                    Date inputDate = DateService.stringToDateConversion(confirmAppointment.getDate());

                    if (curDate.equals(inputDate)) {
                        response.put("isCurDate", true);
                    }
                    response.put("role", role);
                    response.put("message", message);
                    status = HttpStatus.OK;

                } else {
                    message = "This time slot is already scheduled";
                    status = HttpStatus.CONTINUE;
                }
            } else {
                message = "Already three appointments are booked with this Doctor, please schedule after completion of two appointments";
                status = HttpStatus.CONTINUE;
            }

        }
        response.put("slotTime", confirmAppointment.getTime());
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    @RequestMapping(value = "getClinicDoctor", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> getClinicDoctors(@RequestBody User user, BindingResult result, ModelMap modelMap) {
        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        Date date = (Date) commonRepository.getDate();
        String message = "";
        if (user.getUserId() != null) {
            List<ClinicDoctors> doctorsByClinicId = clinicDoctorsRepository.getDoctorsClinicId(user.getUserId());
            List<DoctorInfo> list = new ArrayList<>();
            for (ClinicDoctors clinicDoctors : doctorsByClinicId) {
                DoctorInfo doctors = new DoctorInfo();

                DoctorInfo doctorInfo = doctorInfoRepository.findOne(clinicDoctors.getDoctorId());
                UserDetails userDetails = CommonServices.getOnlyUserDetails(userDetailsRepository.findByUserId(clinicDoctors.getDoctorId()));
                String name = userDetails.getFirstName() + " " + userDetails.getLastName();

                Long specialityId = clinicDoctors.getSpecialityId();
                if (specialityId != null) {
                    String specialityName = userRepository.getSpecialityNameById(specialityId);
                    if (specialityName != null) {
                        doctors.setSpecialist(specialityName);
                    }
                }

                doctors.setDoctorId(clinicDoctors.getDoctorId());
                doctors.setQualification(doctorInfo.getQualification());
                doctors.setName(name);

                list.add(doctors);
            }
            response.put("doctorsDetails", list);
            status = HttpStatus.OK;
            message = "";
        }

        response.put("dbDate", date);
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    @RequestMapping(value = "checkPatientExist", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> checkPatientExist(@RequestBody User user, BindingResult result, ModelMap modelMap) {

        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "Search result not found";
        User user1 = null;

        if (user.getUserId() != null) {
            user1 = commonRepository.getUserDetails(user.getUserId());
        }

        if (user.getUserName() != null && !user.getUserName().isEmpty()) {
            user1 = getUserDetails(user.getUserName());
        }
        if (user1 != null) {
            if ("ROLE_PATIENT".equals(user1.getRole())) {
                //fetch the doctor firstName and speciality
                Search doctorDetails = commonRepository.convertObjToSearchEnty(user.getDoctorId(), user.getClinicId());
                response.put("doctorDetails", doctorDetails);
                response.put("patientDetails", user1);
                message = "Search resulted in Success";
                status = HttpStatus.OK;
            }
        }
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    public User getUserDetails(String userName) {
        Object obj[] = (Object[]) commonRepository.getUserDetailsByUserNameInuserView(userName);
        User user = null;
        if (obj != null && obj.length > 0) {
            user = new User();
            user.setUserId(Long.parseLong(obj[0].toString()));
            user.setUserName(obj[1].toString());
            user.setRole(obj[2].toString());
            if (obj[3] != null && obj[4] != null) {
                user.setFlname(obj[3].toString() + " " + obj[4].toString());
            }
            user.setMobileNo(obj[5].toString());
            user.setEmail(obj[6].toString());
            if (obj[7] != null) {
                user.setUuid(obj[7].toString());
            }
            if (obj[8] != null) {
                user.setName(obj[8].toString());
            }
            if (obj[9] != null) {
                user.setImageThumbnailFileName(obj[9].toString());
            }
            if (user.getImageThumbnailFileName() != null && !user.getImageThumbnailFileName().isEmpty()) {
                user.setImageUrl(utilityServices.getMessage("User.profileImage.Url") + user.getUserId());
            } else {
                user.setImageUrl(utilityServices.getMessage("User.DefaultImage.Url"));
            }
        }
        return user;
    }

    public Map<String, Object> sendEmailActivationLink(User user) {
        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";
       /* user.setUuid(null);*/
        if (StringUtils.isBlank(user.getUuid())) {
            user.setUuid(UUID.randomUUID().toString());
            String email = null;
            if (user.getEmail() != null) {
                email = user.getEmail();
            } else {
                email = user.getUserContactInfo().getEmail();
            }
            boolean verdict = emailController.sendEmailActivationLink(user, email);
            if (verdict) {
                response.put("user", user);
                status = HttpStatus.OK;
                message = "Click on activation link sent to mail";
            } else {
                status = HttpStatus.NOT_ACCEPTABLE;
                message = "Invalid Email";
            }
        } else {
            status = HttpStatus.CONTINUE;
            message = "Activation link already sent";
        }
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    public Map<String, Object> sendCredentialsToUser(User user, String password) {
        Map<String, Object> response = new HashMap<String, Object>();
        String messageBody = "Dear User, Thank you for signing up with DataLife. Your username : " + user.getUserName() + " and password : " + password + ". DataLife";
        try {
            alfrescoAuthDetails.sendMessage(user.getUserContactInfo().getMobilePhone(), messageBody);
            response.put("statusCode", HttpStatus.OK.value());
        } catch (Exception e) {
            response.put("statusCode", HttpStatus.NOT_ACCEPTABLE.value());
            response.put("message", "Invalid Mobile Number");
            return response;
        }
        boolean verdict;
        switch(user.getRole()){
            case RolesInServices.ROLE_DOCTOR:
            case RolesInServices.ROLE_PATIENT:
                verdict = emailController.sendUserNameAndPassword(user.getUserDetails().getFirstName() + " " + user.getUserDetails().getLastName(), user.getUserContactInfo().getEmail(), user.getUserName(), password, null, null);
                if (verdict) {
                    response.put("statusCode", HttpStatus.OK.value());
                }else{
                    response.put("statusCode", HttpStatus.NOT_ACCEPTABLE.value());
                    response.put("message", "Invalid Email Address");
                }
                break;
            case RolesInServices.ROLE_CLINIC:
                verdict = emailController.sendUserNameAndPassword(user.getClinicInfo().getClinicName(),user.getUserContactInfo().getEmail(),user.getUserName(),password,null, null);
                if (verdict) {
                    response.put("statusCode", HttpStatus.OK.value());
                }else{
                    response.put("statusCode", HttpStatus.NOT_ACCEPTABLE.value());
                    response.put("message", "Invalid Email Address");
                }
                break;

            case RolesInServices.ROLE_DIAGNOSTICLABS:
            case RolesInServices.ROLE_HOSPITAL:
            case RolesInServices.ROLE_PHARMACY:
            case RolesInServices.ROLE_TELECONSULTANT:
            case RolesInServices.ROLE_REFERRALDOCTOR:
                verdict = emailController.sendUserNameAndPassword(user.getServiceProvider().getName(),user.getUserContactInfo().getEmail(),user.getUserName(),password,null, null);
                if (verdict) {
                    response.put("statusCode", HttpStatus.OK.value());
                }else{
                    response.put("statusCode", HttpStatus.NOT_ACCEPTABLE.value());
                    response.put("message", "Invalid Email Address");
                }
                break;
        }
        return response;
    }

    public String generatePassword(){
        Random randomGenerator = new Random();
        Integer randomInt = randomGenerator.nextInt(100000);
        return randomInt.toString();
    }

    public User createUserInAlresco(User user) {
        String ticket = alfrescoAuthDetails.getAlfrescoConnectionTicket(this.messageSource.getMessage("alfresco.admin.userName", null, "Default alfresco admin userName", null), this.messageSource.getMessage("alfresco.admin.password", null, "Default alfresco admin password", null));
        if (StringUtils.isBlank(user.getAlfrescoUrl())) {
            String url = alfrescoAuthDetails.addUser(user, ticket);
            user.setAlfrescoUrl(url);
        }
        //check whether user had created the folder in his alfresco account
        if (StringUtils.isBlank(user.getUserParentDir())) {
            try {
                //get user ticket from alfresco
                String userticket = alfrescoAuthDetails.getAlfrescoConnectionTicket(user.getUserName(), user.getPassword());

                //get repositoryId from alfresco based on user ticket
                String repositoryId = alfrescoAuthDetails.getRepositoryId(userticket, user.getUserName());
                String[] repId = repositoryId.split("Ref:");
                String[] repNode = repId[1].split("/");

                //user create a folder in his repository
                String usernodeRef = alfrescoAuthDetails.addFolder(user.getUserId(), userticket, repNode[3]);
                user.setUserParentDir(usernodeRef);

                String[] node = usernodeRef.split("/");

                //admin grant the permission as Collaborator for created folder to user
                alfrescoAuthDetails.grantPermission(ticket, user.getUserName(), node[3], "Collaborator");

                //invalidate the tickets
                User sessionUser = MyUserDetailsService.getUserFromSession();
                if (sessionUser != null) {
                    sessionUser.setAlfresAuthTicket(userticket);
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                alfrescoAuthDetails.logoutTicket(ticket);
            }
        }
        return user;
    }
}
