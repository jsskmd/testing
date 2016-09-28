package com.datalife.servicelayers;

import com.datalife.controller.EmailController;
import com.datalife.entities.*;
import com.datalife.repositories.*;
import com.datalife.services.AlfrescoAuthDetails;
import com.datalife.services.CommonServices;
import com.datalife.services.UtilityServices;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by Supriya on 1/10/2016.
 */
@RestController
@RequestMapping(value = "/api/provider/")
public class ProviderService {

    @Autowired
    LabServiceProviderRepository labServiceProviderRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CommonRepository commonRepository;

    @Autowired
    UtilityServices utilityServices;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AlfrescoAuthDetails alfrescoAuthDetails;


    @Autowired
    EmailController emailController;

    @Autowired
    ProviderDetailRepository providerDetailRepository;

    @Autowired
    HospitalInfoRepository hospitalInfoRepository;

    @Autowired
    HospitalPackagesRepository hospitalPackagesRepository;

    @Autowired
    UserContactRepository userContactRepository;

    @Autowired
    LabTestRepository labTestRepository;


    @Autowired
    LabTestCategoriesRepository labTestCategoriesRepository;

    @Autowired
    CommonService commonService;

    @Autowired
    HomeService homeService;

    protected static final Logger logger = Logger.getLogger(ProviderService.class);

    @RequestMapping(value = "fetch/enquiredProviders", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> getSubmittedDetails(@RequestBody ProviderDetails providerDetails, BindingResult result, ModelMap modelMap) {
        logger.info("inside the path /services/provider/getSubmittedDetails "+providerDetails);
        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";

        List<ProviderDetails> providers = providerDetailRepository.fetchProvidersByRole(providerDetails.getRole());
        if(providers.size() >0){
            response.put("providers",providers);
            status = HttpStatus.OK;
        }else{
            status = HttpStatus.CONTINUE;
            message = utilityServices.getMessage("provider.notFound");
        }

        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    @RequestMapping(value = "getProviderDetails", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> getProviderDetails(@RequestBody ProviderDetails providerDetails, BindingResult result, ModelMap modelMap) {

        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";

        if (providerDetails != null && providerDetails.getId() != null) {
            ProviderDetails pd = providerDetailRepository.findOne(providerDetails.getId());
            response.put("providerData", pd);
            status = HttpStatus.OK;
        }
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    @RequestMapping(value = "verifyProvider", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> verifyProvider(@RequestBody Long providerDetailsId, BindingResult result, ModelMap modelMap) {

        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";

        if (providerDetailsId != null && providerDetailsId != 0L) {
            ProviderDetails providerDetails = providerDetailRepository.findOne(providerDetailsId);
            /*providerDetails.setVerified(true);*/
            providerDetailRepository.save(providerDetails);
            status = HttpStatus.OK;
        }

        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }


    @RequestMapping(value = "userSignup", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> userSignup(@RequestBody ProviderDetails providerDetails, BindingResult result, ModelMap modelMap) {

        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";

        /*if (providerDetails.isAccountStatus()) {*/
            ProviderDetails details = providerDetailRepository.findOne(providerDetails.getId());
            if(details != null){
                User user = new User();
                user.setRole(commonRepository.getRoleByType(details.getType()));
                user.setCreationDate(new Date());
                String randomPassword = commonService.generatePassword();
                String password = passwordEncoder.encode(randomPassword);
                user.setPassword(password);

                UserContactInfo userContactInfo = new UserContactInfo();
                userContactInfo.setAddress(details.getAddress());
                userContactInfo.setCity(details.getCity());
                userContactInfo.setEmail(providerDetails.getEmail());
                userContactInfo.setLocation(details.getLocation());
                userContactInfo.setMobilePhone(providerDetails.getMobilePhone());
                userContactInfo.setWorkPhone(providerDetails.getWorkPhone());
                userContactInfo.setZipCode(details.getZipCode());

                if(details.getState() != null){
                    userContactInfo.setState(details.getState());
                }
                if(StringUtils.isNotBlank(details.getCountry())){
                    userContactInfo.setCountry(details.getCountry());
                }
                userContactInfo.setUser(user);
                user.setUserContactInfo(userContactInfo);

                switch ((details.getRole())) {

                    case RolesInServices.ROLE_DIAGNOSTICLABS :

                        Lab labsInfo = new Lab();
                        labsInfo.setAddedInfo(details.getAddedInfo());

                        if(details.getServicesOfLab()!= null && !details.getServicesOfLab().isEmpty()){

                            labsInfo.setName(details.getName());
                            labsInfo.setContactPerson(providerDetails.getContactPerson());
                            labsInfo.setEstablishmentYear(details.getYearofEstablishment());
                            labsInfo.setLicenceNumber(details.getLicNo());
                            labsInfo.setWebsite(providerDetails.getWebsite());
                            labsInfo.setFacilities(details.getFacilities());
                            labsInfo.setServices(details.getServicesOfLab());
                            user.setServiceProvider(labsInfo);
                            labsInfo.setUser(user);
                            User u = userRepository.save(user);
                            u.setUserName("DL" + labsInfo.getName().substring(0, 2) + u.getUserId());
                            u = commonService.createUserInAlresco(u);
                            commonService.sendCredentialsToUser(u,randomPassword);
                            u.setEmailVerfied(true);
                            u.setMobileVerified(true);
                            u.setEnabled(true);
                            userRepository.save(u);
                            providerDetailRepository.delete(details.getId());
                            message = utilityServices.getMessage("User.provider.create.MESSAGE");
                            status = HttpStatus.OK;
                        }
                        break;

                    case RolesInServices.ROLE_PHARMACY:
                            Pharmacy pharmacy = new Pharmacy();
                            pharmacy.setAddedInfo(details.getAddedInfo());
                            pharmacy.setName(details.getName());
                            pharmacy.setContactPerson(providerDetails.getContactPerson());
                            pharmacy.setEstablishmentYear(details.getYearofEstablishment());
                            pharmacy.setLicenceNumber(details.getLicNo());
                            pharmacy.setWebsite(providerDetails.getWebsite());
                            pharmacy.setFacilities(providerDetails.getFacilities());
                            user.setServiceProvider(pharmacy);
                            pharmacy.setUser(user);
                            User u = userRepository.save(user);
                            u.setUserName("Ph" + pharmacy.getName().substring(0, 2) + u.getUserId());
                            u = commonService.createUserInAlresco(u);
                            commonService.sendCredentialsToUser(u,randomPassword);
                            u.setEmailVerfied(true);
                            u.setMobileVerified(true);
                            u.setEnabled(true);
                            userRepository.save(u);
                            providerDetailRepository.delete(details.getId());
                            message = utilityServices.getMessage("User.provider.create.MESSAGE");
                            status = HttpStatus.OK;
                        break;

                    case RolesInServices.ROLE_HOSPITAL:
                        break;

                    case RolesInServices.ROLE_REFERRALDOCTOR:
                        break;

                }
            }
        /*}*/
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    @RequestMapping(value = "getHospitalCountries", method = RequestMethod.GET, produces = {"application/json"})
    public Map<String, Object> getHospitalCountries() {
        Map<String, Object> response = new HashMap<String, Object>();
        List<Long> countryIds = commonRepository.getHospitalCountries();
        List<Country> countries = new ArrayList<Country>();
        for (Long countryId : countryIds) {
            String countryName = commonRepository.getCountryById(countryId);
            Country c = new Country();
            c.setCountryId(countryId);
            c.setCountryName(countryName);
            countries.add(c);
        }
        response.put("countries", countries);
        return response;
    }

    @RequestMapping(value = "getHospitalCities/{countryId}", method = RequestMethod.GET, produces = {"application/json"})
    public Map<String, Object> getHospitalCities(@PathVariable Long countryId) {
        Map<String, Object> response = new HashMap<String, Object>();
        List<HospitalCity> hospitalCities = commonRepository.getHospitalCities(countryId);
        response.put("hospitalCities", hospitalCities);
        return response;
    }

    @RequestMapping(value = "getHospitalCities", method = RequestMethod.GET, produces = {"application/json"})
    public Map<String, Object> getHospitalCities() {
        Map<String, Object> response = new HashMap<String, Object>();
        List<HospitalCity> hospitalCities = commonRepository.getHospitalCities();
        response.put("hospitalCities", hospitalCities);
        return response;
    }

    @RequestMapping(value = "getHospitalCitiesLike/{query}", method = RequestMethod.GET, produces = {"application/json"})
    public Map<String, Object> getHospitalCities(@PathVariable String query) {
        Map<String, Object> response = new HashMap<String, Object>();
        List<HospitalCity> hospitalCities = commonRepository.getHospitalCitiesLike(query);
        response.put("hospitalCities", hospitalCities);
        return response;
    }

    @RequestMapping(value = "getProcedureTypes/{specialityId}", method = RequestMethod.GET, produces = {"application/json"})
    public Map<String, Object> getProcedureType(@PathVariable Long specialityId) {
        Map<String, Object> response = new HashMap<String, Object>();
        List<TypeOfPRocedure> getProcedureType = CommonServices.getOnlyTypeOfProcedures(commonRepository.getProcedureType(specialityId));
        response.put("getProcedureType", getProcedureType);
        return response;
    }

   /* @RequestMapping(value = "getListedCountries/{role}", method = RequestMethod.GET, produces = {"application/json"})
    public List<Country> getListedCountries(@PathVariable String role) {
        List<String> countryIds = userContactRepository.getHospitalCountries(role);
        List<Country> countries = new ArrayList<>();
        for (String countryId : countryIds) {
            System.out.println(countryId);
            String countryName = commonRepository.getCountryById(Long.parseLong(countryId));
            Country c = new Country();
            c.setCountryId(Long.parseLong(countryId));
            c.setCountryName(countryName);
            countries.add(c);
        }
        return countries;
    }*/

    /*@RequestMapping(value = "getListedCities/{countryId}/{role}", method = RequestMethod.GET, produces = {"application/json"})
    public List<String> getListedCities(@PathVariable Long countryId, @PathVariable String role) {
        return userContactRepository.getHospitalCitiesById(role, countryId.toString());
    }*/

    @RequestMapping(value = "getSecOpdocNameSplity", method = RequestMethod.GET, produces = {"application/json"})
    public Map<String, Object> getSecOpdocNameSplity() {
        Map<String, Object> response = new HashMap<String, Object>();
        List<DoctorSearch> doctors = new ArrayList<>();
        Set<DoctorSearch> doctorSearchSet = new HashSet<>();

        List<Speciality> specialist = commonRepository.getSpecialities();
        for (Speciality speciality : specialist){
            DoctorSearch search = new DoctorSearch();
            search.setName(speciality.getName());
            search.setUserId(speciality.getSpecialityId());
            search.setCategories("SPECIALTY");
            doctorSearchSet.add(search);
        }
        doctors.addAll(doctorSearchSet);
        response.put("specialist", doctors);
        return response;
    }

    @RequestMapping(value = "getSecOpDocNameSplitySrch/{name}", method = RequestMethod.GET, produces = {"application/json"})
    public Map<String, Object> getSecOpDocNameSplitySrch(@PathVariable String name) {
        Map<String, Object> response = new HashMap<String, Object>();
        List<DoctorSearch> doctors = new ArrayList<>();
        Set<DoctorSearch> doctorSearchSet = new HashSet<>();

        List<Object> doctorList = commonRepository.getSecoundOpinionDoctors(name);
        for (Object aDoctorList : doctorList) {
            Object[] o = (Object[]) aDoctorList;
            DoctorSearch search = new DoctorSearch();
            search.setName(o[1] + " " + o[2]);
            search.setUserId(o[0]);
            search.setCategories("DOCTOR");
            doctorSearchSet.add(search);
        }

        List<Speciality> specialist = commonRepository.getSpecialistLike(name);
        for (Speciality speciality : specialist){
            DoctorSearch search = new DoctorSearch();
            search.setName(speciality.getName());
            search.setUserId(speciality.getSpecialityId());
            search.setCategories("SPECIALTY");
            doctorSearchSet.add(search);
        }
        doctors.addAll(doctorSearchSet);
        response.put("searchRes", doctors);
        return response;
    }

    @RequestMapping(value = "getSecOpCitySrch/{query}", method = RequestMethod.GET, produces = {"application/json"})
    public Map<String, Object> getSecOpCitySrch(@PathVariable String query) {
        Map<String, Object> response = new HashMap<String, Object>();
        List<City> locationList = commonRepository.getCityLike(query);
        response.put("citySrch", locationList);
        return response;
    }

    @RequestMapping(value = "getSecOpCity", method = RequestMethod.GET, produces = {"application/json"})
    public Map<String, Object> getSecOpCity() {
        Map<String, Object> response = new HashMap<String, Object>();
        List<City> locationList = commonRepository.getCity();
        response.put("city", locationList);
        return response;
    }


    /*public static class CustomComparator implements Comparator<B2BUser> {
        @Override
        public int compare(B2BUser o1, B2BUser o2) {
            if(o1.getUserId() != null || o2.getUserId() != null  ){
                if (o1.getUserId().equals(o2.getUserId())) {
                    return 0;
                }
            }
            return 1;
        }
    }*/

    @RequestMapping(value = "getLocations", method = RequestMethod.GET, produces = {"application/json"})
    public Map<String, Object> getLocations() {
        Map<String, Object> response = new HashMap<String, Object>();
        List<Location> locations = commonRepository.getLocations();
        response.put("locationList", locations);
        return response;
    }

    @RequestMapping(value = "getLocationsLike/{query}", method = RequestMethod.GET, produces = {"application/json"})
    public Map<String, Object> getLocationsLike(@PathVariable String query) {
        Map<String, Object> response = new HashMap<String, Object>();
        List<Location> locationLike = commonRepository.getLocationLike(query);
        response.put("locationLike", locationLike);
        return response;
    }

    @RequestMapping(value = "getCity", method = RequestMethod.GET, produces = {"application/json"})
    public Map<String, Object> getCity() {
        Map<String, Object> response = new HashMap<String, Object>();
        List<City> cityList = commonRepository.getCity();
        response.put("cityList", cityList);
        return response;
    }

    @RequestMapping(value = "getCityLike/{query}", method = RequestMethod.GET, produces = {"application/json"})
    public Map<String, Object> getCityLike(@PathVariable String query) {
        Map<String, Object> response = new HashMap<String, Object>();
        List<City> cityLike = commonRepository.getCityLike(query);
        response.put("cityLike", cityLike);
        return response;
    }

    @RequestMapping(value = "getLabServicesName", method = RequestMethod.GET, produces = {"application/json"})
    public Map<String, Object> getLabServicesName() {
        List<DoctorSearch> doctors = new ArrayList<>();
        Set<DoctorSearch> doctorSearchSet = new HashSet<>();
        Map<String, Object> response = new HashMap<String, Object>();
        List<LabTests> labTestsList = commonRepository.getLabTestses();
        for (LabTests tests : labTestsList){
            DoctorSearch search = new DoctorSearch();
            search.setName(tests.getName());
            search.setUserId(tests.getLabTestsId());
            doctorSearchSet.add(search);
        }
        doctors.addAll(doctorSearchSet);
        response.put("labServicesName",doctors);
        return response;
    }

    @RequestMapping(value = "getLabTestCategories", method = RequestMethod.GET, produces = {"application/json"})
    public Map<String, Object> getLabTestCategories() {
        Map<String, Object> response = new HashMap<String, Object>();
        Iterable<LabTestCategories> labTestsList = labTestCategoriesRepository.findAll();
        response.put("labServicesName",labTestsList);
        return response;
    }


   /* public List<Country> getListedCountries() {
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
    }*/

}
