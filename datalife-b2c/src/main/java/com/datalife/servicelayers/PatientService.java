package com.datalife.servicelayers;

import com.datalife.controller.EmailController;
import com.datalife.entities.*;
import com.datalife.repositories.*;
import com.datalife.services.*;
import org.apache.commons.lang.StringUtils;
import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

/**
 * Controller class that handles the Patient related requests
 * <p/>
 * <p/>
 * Created by supriya gondi on 11/25/2014.
 */
@RestController
@RequestMapping(value = "/api/user/patient/")
public class PatientService {

    /**
     * reference to userRepository
     */
    @Autowired
    UserRepository userRepository;
    /**
     * reference to userDetailsRepository
     */
    @Autowired
    UserDetailsRepository userDetailsRepository;
    /**
     * reference to idCardDetailsRepository
     */
    @Autowired
    IdCardDetailsRepository idCardDetailsRepository;
    /**
     * reference to insuranceDetailsRepository
     */
    @Autowired
    InsuranceDetailsRepository insuranceDetailsRepository;
    /**
     * reference to userAltContactRepository
     */
    @Autowired
    UserPreferenceRepository userPreferenceRepository;
    /**
     * reference to commonService
     */
    @Autowired
    UserEmergencyRepository userEmergencyRepository;

    @Autowired
    CommonService commonService;
    /**
     * reference to userContactRepository
     */
    @Autowired
    UserContactRepository userContactRepository;
    /**
     * reference to historyRepository
     */
    @Autowired
    HistoryRepository historyRepository;
    /**
     * reference to vitalRepository
     */
    @Autowired
    VitalRepository vitalRepository;
    /**
     * reference to encounterRepository
     */
    @Autowired
    EncounterRepository encounterRepository;
    /**
     * reference to commonRepository
     */
    @Autowired
    CommonRepository commonRepository;

    /**
     * reference to emailController
     */
    @Autowired
    EmailController emailController;

    @Autowired
    UploadFileRepository uploadFileRepository;

    @Autowired
    ServiceRequestsRepository serviceRequestsRepository;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    LabRequestsRepository labRequestsRepository;

    @Autowired
    AltServContactInfoRepository altServContactInfoRepository;

    @Autowired
    SurgeryRequestRepository surgeryRequestRepository;

    /**
     * reference to logger
     */
    private static final Logger logger = LoggerFactory.getLogger(PatientService.class);

    /**
     * reference to uploadDirectory
     */
    @Value("C:\\Users\\DATASCRIBE\\IdeaProjects\\cando\\src\\main\\webapp\\resources\\profileImages\\")
    private String uploadDirectory;

    @Autowired
    private MessageSource messageSource;


    @Autowired
    AlfrescoAuthDetails alfrescoAuthDetails;

    @Autowired
    UtilityServices utilityServices;

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    PharmacyRequestRepository pharmacyRequestRepository;


    @RequestMapping(value = "basicInfo", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> getBasicDetails(@RequestBody User user, BindingResult result, ModelMap modelMap) {

        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";
        Long userId = user.getUserId();
        User u = userRepository.findOne(userId);

        if (u != null) {

            if (u.getUserDetails() != null) {
                response.put("ud", CommonServices.getOnlyUserDetails(u.getUserDetails()));
            }
            if (u.getIdCardDetails() != null) {
                response.put("icd", CommonServices.getOnlyIdCardDetails(u.getIdCardDetails()));
                response.put("idImageUrl", utilityServices.getMessage("User.identityImage.mainUrl") + userId);
            }
            if (u.getInsuranceDetails() != null) {
                response.put("insd", CommonServices.getOnlyInsuranceDetails(u.getInsuranceDetails()));
            }
            if (u.getServiceProvider() != null) {
                response.put("sp", CommonServices.getOnlyServiceProvider(u.getServiceProvider()));
            }
            response.put("user", CommonServices.getOnlyUser(u));
            response.put("bloodGroups", commonService.getBloodGroups());
            response.put("countries", commonService.getCountries());
            status = HttpStatus.OK;
        }


        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    /**
     * This method is get Contact Information of User
     *
     * @param modelMap
     * @return Map<String,Object> getDemographics includes status,statusCode,message and userDetails
     */
    @RequestMapping(value = "contactInfo", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> getContactInformation(@RequestBody User user, BindingResult result, ModelMap modelMap) {

            Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";

        Long userId = user.getUserId();
        User u = userRepository.findOne(userId);

        if (u != null) {

            if (u.getUserContactInfo() != null) {
                response.put("uci", CommonServices.getOnlyUserContactInfo(u.getUserContactInfo()));
            }
            response.put("userId", userId);
            response.put("countries", commonService.getCountries());

            status = HttpStatus.OK;
        }

        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }


    /**
     * This method is for Alternate Conatct Information of User
     *
     * @param modelMap
     * @return Map<String, Object> getDemographics includes status,statusCode,message and Alternate contact information of user
     */
    @RequestMapping(value = "emergencyInfo", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> getEmergencyInfo(@RequestBody User user, BindingResult result, ModelMap modelMap) {

        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";
        Long userId = user.getUserId();
        User u = userRepository.findOne(userId);
        LinkedList<UserEmergencyInfo> userEmergencyInfos = userEmergencyRepository.findById(userId);

        if (u != null) {
            response.put("ueis", CommonServices.getOnlyEmergencyInfo(userEmergencyInfos));
            response.put("userId", userId);
            status = HttpStatus.OK;
        }
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    /**
     * This method is for Alternate Conatct Information of User
     *
     * @param modelMap
     * @return Map<String, Object> getDemographics includes status,statusCode,message and Alternate contact information of user
     */
    @RequestMapping(value = "preferences", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> getPreferences(@RequestBody User user, BindingResult result, ModelMap modelMap) {

        Map<String, Object> response = new HashMap<String, Object>();
        Map<Integer, List<UserPreferences>> list = new HashMap<>();
        String message = "";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        List<UserPreferences> uplist = new ArrayList<>();
        if (user.getUserId() != null) {
            uplist = CommonServices.getOnlyUserPreferences(userPreferenceRepository.findById(user.getUserId()));
            if (uplist.size() > 0) {
                for (UserPreferences preferedDoctList : uplist) {
                    List<UserPreferences> uplist1 = new ArrayList<>();
                    String specialityName = commonRepository.getRecordSpecialityById(Long.valueOf(preferedDoctList.getSpecialityId()));
                    preferedDoctList.setSpeciality(specialityName);
                    preferedDoctList.setUser(user);
                    uplist1.add(preferedDoctList);
                    if (list.containsKey(preferedDoctList.getSpecialityId())) {
                        List<UserPreferences> list1 = list.get(preferedDoctList.getSpecialityId());
                        list1.addAll(uplist1);
                        list.put(preferedDoctList.getSpecialityId(), list1);
                    } else {
                        list.put(preferedDoctList.getSpecialityId(), uplist1);
                    }
                }
                status = HttpStatus.OK;
                message = "Success";
                response.put("ups", list);
            } else {
                status = HttpStatus.OK;
                response.put("ups", uplist);
            }

        }
        response.put("statusCode", status.value());
        response.put("userId", user.getUserId());
        response.put("message", message);
        return response;
    }
    /**
     * The purpose of this controller is for saving Basic Information, Identity Card Details and Insurance Details.
     *
     * @param u
     * @param result
     * @param modelMap
     * @return Map<String, Object> saveBasicInformation includes status,statusCode,message
     */
    @RequestMapping(value = "basicInfo/save", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> saveBasicInformation(@RequestBody User u, BindingResult result, ModelMap modelMap) {

        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "Data Not Saved. Save Again";

        if (u != null) {
            Long userId = u.getUserId();
            User user = userRepository.findOne(userId);

            if (user != null) {
                UserDetails userDetails = userDetailsRepository.findByUserId(userId);
                UserDetails newUserDetails = u.getUserDetails();
                if (userDetails != null && newUserDetails != null) {
                    newUserDetails.setUserDetailsId(userDetails.getUserDetailsId());
                    newUserDetails.setUser(user);
                }

                if (user.getRole().equals("ROLE_PATIENT")) {
                    IdCardDetails idCardDetails = idCardDetailsRepository.findByUserId(userId);
                    IdCardDetails newIdCardDetails = u.getIdCardDetails();
                    if (idCardDetails != null && newIdCardDetails != null) {
                        String otherState = newIdCardDetails.getStateName();
                        if (otherState != null && !otherState.isEmpty()) {
                            newIdCardDetails.setOtherState(otherState);
                        }
                        newIdCardDetails.setCardDetailsId(idCardDetails.getCardDetailsId());
                        newIdCardDetails.setUser(user);
                    }

                    InsuranceDetails insuranceDetails = insuranceDetailsRepository.findByUserId(userId);
                    InsuranceDetails newInsuranceDetails = u.getInsuranceDetails();
                    if (insuranceDetails != null && newInsuranceDetails != null) {
                        newInsuranceDetails.setInsuranceId(insuranceDetails.getInsuranceId());
                        newInsuranceDetails.setUser(user);
                    }

                    user.setIdCardDetails(newIdCardDetails);
                    user.setInsuranceDetails(newInsuranceDetails);
                    user.setUserDetails(newUserDetails);
                    userRepository.save(user);
                    response.put("idImageUrl", utilityServices.getMessage("User.identityImage.mainUrl") + userId);
                    response.put("icd", CommonServices.getOnlyIdCardDetails(newIdCardDetails));
                    response.put("insd", CommonServices.getOnlyInsuranceDetails(newInsuranceDetails));

                } else {
                    user.setUserDetails(newUserDetails);
                    userRepository.save(user);
                }

                status = HttpStatus.OK;
                response.put("ud", CommonServices.getOnlyUserDetails(newUserDetails));
                response.put("user", CommonServices.getOnlyUser(user));
                response.put("bloodGroups", commonService.getBloodGroups());
                response.put("countries", commonService.getCountries());
                message = "Saved Successfully.";
            }
        }

        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    /**
     * /**
     * The purpose of this controller is for saving Contact Information.
     *
     * @param userContactInfo
     * @param result
     * @param modelMap
     * @return Map<String, Object> saveBasicInformation includes status,statusCode,message
     */
    @RequestMapping(value = "contactInfo/save", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> saveContactInformation(@RequestBody UserContactInfo userContactInfo, BindingResult result, ModelMap modelMap) {

        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";

        if (userContactInfo != null) {
            User user = userContactInfo.getUser();
            if (user != null) {
                Long userId = user.getUserId();
                UserContactInfo contactInfo = userContactRepository.findByUserId(userId);
                if (contactInfo != null) {
                    userContactInfo.setUserContactInfoId(contactInfo.getUserContactInfoId());
                }
                userContactInfo.setUser(user);
                userContactRepository.save(userContactInfo);

                response.put("uci", CommonServices.getOnlyUserContactInfo(userContactInfo));
                response.put("countries", commonService.getCountries());

                User u = userRepository.findOne(userId);
                Integer preferencesCount = userPreferenceRepository.getPreferencesCount(u.getUserId());
                userRepository.save(u);

                status = HttpStatus.OK;
                message = "Saved Successfully.";

            }
        }
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }


    /**
     * The purpose of this controller is for saving Patient Preferences
     *
     * @param result
     * @param modelMap
     * @return Map<String, Object> saveBasicInformation includes status,statusCode,message
     */
    @RequestMapping(value = "emergencyInfo/save", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> saveEmergencyContactInformation(@RequestBody LinkedList<UserEmergencyInfo> userEmergencyInfos, BindingResult result, ModelMap modelMap) {

        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";
        LinkedList<UserEmergencyInfo> userEmergencies = null;
        Long userId = null;
        if (userEmergencyInfos != null && !userEmergencyInfos.isEmpty()) {
            userId = userEmergencyInfos.get(0).getUser().getUserId();
            userEmergencies = userEmergencyRepository.findById(userId);
            if (userEmergencies != null && !userEmergencies.isEmpty()) {
                for (int i = 0; i < userEmergencyInfos.size(); i++) {
                    UserEmergencyInfo uei = userEmergencyInfos.get(i);
                    uei.setId(userEmergencies.get(i).getId());
                    userEmergencyRepository.save(uei);
                }
            } else {
                userEmergencyRepository.save(userEmergencyInfos);
            }
            response.put("userId", userId);
            response.put("ueis", userEmergencyInfos);
        }
        response.put("countries", commonService.getCountries());
        status = HttpStatus.OK;
        message = "Saved Successfully.";

        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }


    @RequestMapping(value = "preferences/save", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> saveUserPreferences(@RequestBody LinkedList<UserPreferences> userPreferenceses, BindingResult result, ModelMap modelMap) {


        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";
        Long userId = null;

        try {
            userId = userPreferenceses.get(0).getUser().getUserId();
            List<UserPreferences> dbUps = userPreferenceRepository.findById(userId);
            int dbPreferenceSize = dbUps.size();

            if (dbUps != null && !dbUps.isEmpty()) {
                int j = 0;
                int i = 0;
                List<Long> list = new ArrayList<Long>();

                while (i < dbPreferenceSize) {
                    UserPreferences inputUserPreference = userPreferenceses.get(j);

                    if (inputUserPreference.getSpecialityId() == dbUps.get(i).getSpecialityId()) {

                        if (dbUps.get(i).getId() != null && list.indexOf(dbUps.get(i).getId()) == -1) {
                            inputUserPreference.setId(dbUps.get(i).getId());
                            userPreferenceRepository.save(inputUserPreference);
                            list.add(dbUps.get(i).getId());
                            j = j + 1;
                            i = 0;
                        }
                        else {
                            i = i + 1;
                        }
                    }
                    else {
                        i = i + 1;
                    }
                }

                if (userPreferenceses.size() > dbPreferenceSize) {
                    UserPreferences userPreference1 = userPreferenceses.get(dbPreferenceSize);

                    if (userPreferenceses.get(dbPreferenceSize).getName() != "") {
                        userPreferenceRepository.save(userPreference1);
                    }
                }
            }
            else {

                for (UserPreferences userPreference : userPreferenceses) {

                    if (userPreference.getName() != "") {
                        userPreferenceRepository.save(userPreference);
                    }
                }
            }
            response.put("userId", userId);
            message = "Success";
        }
        catch (
            org.springframework.dao.DataIntegrityViolationException e) {
            message = "Failure";
            //message = "Clinic/DoctorName, ContactNumber, and Address already Exists.";
        }
        status = HttpStatus.OK;
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }
    /**
     * The purpose of this controller is for saving History of User
     *
     * @param history
     * @param result
     * @param modelMap
     * @return Map<String, Object> saveBasicInformation includes status,statusCode,message
     */
    @RequestMapping(value = "history/save", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> saveHistory(@RequestBody History history, BindingResult result, ModelMap modelMap) {

        Map<String, Object> response = new HashMap<>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";

        if (history != null) {

            User user = history.getUser();
            History savedHistory = encounterRepository.setHistory(history, user);

            if (savedHistory != null) {
                response.put("medical", historyRepository.getHistoryData(savedHistory.getMedicalHistory()));
                response.put("surgical", historyRepository.getHistoryData(savedHistory.getSurgicalHistory()));
                response.put("family", historyRepository.getHistoryData(savedHistory.getFamilyHistory()));
                response.put("social", historyRepository.getHistoryData(savedHistory.getSocialHistory()));
                response.put("rf", historyRepository.getHistoryData(savedHistory.getRiskFactors()));
                response.put("medications", historyRepository.getHistoryData(savedHistory.getPastMedications()));
                response.put("allergies", historyRepository.getHistoryData(savedHistory.getAllergies()));
            } else {
                response.put("history", "NOT_FOUND");
            }

            status = HttpStatus.OK;
            message = "Saved Successfully.";
        }
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    /**
     * This method is get History of User
     *
     * @param modelMap
     * @return Map<String, Object> getDemographics includes status,statusCode,message and History of user
     */
    @RequestMapping(value = "history", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> getHistory(@RequestBody User user, BindingResult result, ModelMap modelMap) {

        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";

        Long userId = user.getUserId();
        if (userId != null) {
            History history = historyRepository.findByUserId(userId);
            if (history != null) {
                response.put("his", CommonServices.getOnlyHistory(history));
            }
            response.put("userId", userId);

            status = HttpStatus.OK;
        }

        response.put("statusCode", status.value());
        response.put("status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    @RequestMapping(value = "records", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> getRecords(@RequestBody User user, BindingResult result, ModelMap modelMap) {

        Map<String, Object> response = new HashMap<>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";
        if (user != null) {
            Long userId = user.getUserId();
            String ticket = MyUserDetailsService.getUserFromSession().getAlfresAuthTicket();
            userRepository.getUserData(user, ticket);
            response.put("userId", userId);
            status = HttpStatus.OK;
        }
        response.put("statusCode", status.value());
        response.put("status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    /**
     * This method is to get Vitals of user
     *
     * @param modelMap
     * @return Map<String, Object> getDemographics includes status,statusCode,message and Vitals of user
     */
    @RequestMapping(value = "vitals", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> getPatientVitals(@RequestBody User user, BindingResult result, ModelMap modelMap) {

        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";
        if (user != null) {
            Long userId = user.getUserId();
            User u = userRepository.findOne(userId);

            if (u != null) {
                if (u.getVitals() != null) {
                    Map<String, Object> res = vitalRepository.getVitalValues(userId, false);
                    for (Map.Entry<String, Object> r : res.entrySet()) {
                        response.put(r.getKey(), r.getValue());
                    }
                }
                response.put("patientName", u.getUserDetails().getFirstName() + " " + u.getUserDetails().getLastName());
                response.put("patientId", userId);
                response.put("date", DateService.getTodayDate());
                status = HttpStatus.OK;
            }
        }
        response.put("statusCode", status.value());
        response.put("status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    @RequestMapping(value = "doctorVitals", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> getDoctorVitals(@RequestBody User user, BindingResult result, ModelMap modelMap) {

        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";
        if (user != null) {
            Long userId = user.getUserId();
            User u = userRepository.findOne(userId);

            if (u != null) {
                if (u.getVitals() != null) {
                    Map<String, Object> res = vitalRepository.getVitalValues(userId, true);
                    for (Map.Entry<String, Object> r : res.entrySet()) {
                        response.put(r.getKey(), r.getValue());
                    }
                }
                response.put("patientName", u.getUserDetails().getFirstName() + " " + u.getUserDetails().getLastName());
                response.put("patientId", userId);
                response.put("date", DateService.getTodayDate());
                status = HttpStatus.OK;
            }
        }
        response.put("statusCode", status.value());
        response.put("status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    /**
     * The purpose of this controller is for saving Vitals of User
     *
     * @param vitals
     * @param result
     * @param modelMap
     * @return Map<String, Object> saveBasicInformation includes status,statusCode,message
     */
    @RequestMapping(value = "vitals/save", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> saveVitals(@RequestBody Vitals vitals, BindingResult result, ModelMap modelMap) {

        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";

        User user = userRepository.findOne(vitals.getUser().getUserId());
        Long userId = user.getUserId();
        if (vitals != null) {
            vitals = vitalRepository.validateVitals(vitals);
            vitals.setDate(DateService.stringToDate(vitals.getDateString()));

        }
        vitalRepository.save(vitals);
        response.put("patientId", userId);
        response.put("date", DateService.getTodayDate());
        status = HttpStatus.OK;
        message = "Saved Successfully.";
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    /**
     * The purpose of this controller os to get all vitals of particular user
     *
     * @param user
     * @param result
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "getPatientMonitoredVitals", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> getPatientMonitoredVitals(@RequestBody User user, BindingResult result, ModelMap modelMap) {

        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";

        Long userId = user.getUserId();
        User u = userRepository.findOne(userId);
        if (u != null) {
            if (u.getVitals() != null) {

                List<Vitals> patientVitals = CommonServices.getOnlyVitals(vitalRepository.getPatientMonitoredVitals(userId));
                response.put("vitals", patientVitals);
                status = HttpStatus.OK;
            }
        }
        response.put("statusCode", status.value());
        response.put("status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    @RequestMapping(value = "getDoctorMonitoredVitals", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> getDoctorMonitoredVitals(@RequestBody User user, BindingResult result, ModelMap modelMap) {

        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";

        Long userId = user.getUserId();
        User u = userRepository.findOne(userId);

        if (u != null) {

            if (u.getVitals() != null) {
                List<Vitals> doctorVitals = CommonServices.getOnlyVitals(vitalRepository.getDoctorMonitoredVitals(userId));


                response.put("vitals", doctorVitals);

                status = HttpStatus.OK;
            }
        }
        response.put("statusCode", status.value());
        response.put("status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    @RequestMapping(value = "getDoctorVitals", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> getDoctorRecordedVitals(@RequestBody User user, BindingResult result, ModelMap modelMap) {

        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";

        Long userId = user.getUserId();
        User u = userRepository.findOne(userId);

        if (u != null) {

            if (u.getVitals() != null) {
                PageRequest pageRequest = new PageRequest(0, 4);
                List<Vitals> doctorVitals = CommonServices.getOnlyVitals(vitalRepository.getDoctorVitals(userId, pageRequest));


                response.put("vitals", doctorVitals);

                status = HttpStatus.OK;
            }
        }
        response.put("statusCode", status.value());
        response.put("status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    @RequestMapping(value = "getPatientVitals", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> getPatientRecordedVitals(@RequestBody User user, BindingResult result, ModelMap modelMap) {

        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";

        Long userId = user.getUserId();
        User u = userRepository.findOne(userId);

        if (u != null) {

            if (u.getVitals() != null) {
                PageRequest pageRequest = new PageRequest(0, 4);
                List<Vitals> doctorVitals = CommonServices.getOnlyVitals(vitalRepository.getPatientVitals(userId, pageRequest));


                response.put("vitals", doctorVitals);

                status = HttpStatus.OK;
            }
        }
        response.put("statusCode", status.value());
        response.put("status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    /**
     * The purpose of this controller is to get previous prescriptions of user
     *
     * @param user
     * @param result
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "getPreviousPrescriptions", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> getPreviousPrescriptions(@RequestBody User user, BindingResult result, ModelMap
            modelMap) {

        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";
        Map<String, List<Prescription>> encounterPrescriptions = new LinkedHashMap<>();
        Long userId = user.getUserId();
        if (userId != null) {
            PageRequest pageRequest =
                    new PageRequest(0, 10);
            List<Long> encounters = encounterRepository.getPreviousEncounters(userId, pageRequest);
            if (encounters != null && !encounters.isEmpty()) {
                for (Long encounter : encounters) {
                    List<Prescription> prescriptions = CommonServices.getOnlyPrescriptions(encounterRepository.getPrescription(encounter));
                    if (prescriptions != null && !prescriptions.isEmpty()) {
                        Date d = encounterRepository.getEncounterDate(encounter);
                        String date = DateService.dateToString(d);
                        encounterPrescriptions.put(date, prescriptions);
                    }
                }
                response.put("encounters", encounterPrescriptions);
            }
            status = HttpStatus.OK;
        }
        response.put("statusCode", status.value());
        response.put("status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    /**
     * The purpose of this controller is to patient details
     *
     * @param user
     * @param result
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "getPatientDetails", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> getViewDemographics(@RequestBody User user, BindingResult result, ModelMap modelMap) {

        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";
            Long userId = user.getPatientId();
            if (userId != null) {
                String names = userDetailsRepository.getFullName(userId);
                String[] values = names.split(",");
                response.put("patientName", values[0] + " " + values[1]);
                response.put("userId", userId);
                UserContactInfo contactInfo = CommonServices.getOnlyUserContactInfo(userContactRepository.findByUserId(userId));
                String countryId = contactInfo.getCountry();
                String country = "";
                if (countryId != null && !countryId.isEmpty()) {
                    country = commonRepository.getCountryById(Long.parseLong(countryId));
                } else {
                    country = commonRepository.getCountryById(2L);
                }

                if (country != null && !country.isEmpty()) {
                    if (country.equals("Others")) {
                        response.put("country", contactInfo.getOtherCountry());
                    } else {
                        response.put("country", country);
                    }
                }
                String stateId = contactInfo.getState();
                String state = "";
                if (stateId != null && !stateId.isEmpty()) {
                    state = commonRepository.getStateById(Long.parseLong(stateId));
                } else {
                    state = commonRepository.getStateById(13L);
                }

                if (state != null && !state.isEmpty()) {
                    if (state.equals("Others")) {
                        response.put("state", contactInfo.getOtherState());
                    } else {
                        response.put("state", state);
                    }
                }
                response.put("contactInfo", contactInfo);

                status = HttpStatus.OK;
            }

        response.put("statusCode", status.value());
        response.put("status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }


    /**
     * The purpose of this controller is to get picture of Summary report
     *
     * @param response
     * @param id
     */
    @RequestMapping(value = "summary/{id}", method = RequestMethod.GET)
    public void picturesummary(HttpServletResponse response, @PathVariable Long id) {
        File imageFile = new File(uploadDirectory + "summaryReport" + id + ".pdf");
        try {
            InputStream is = new FileInputStream(imageFile);
            IOUtils.copy(is, response.getOutputStream());
        } catch (IOException e) {
        e.printStackTrace();
        }
    }


    @RequestMapping(value = "test", method = RequestMethod.GET)
    public String test(ModelMap modelMap) {
        return "supriya";
    }

    @RequestMapping(value = "testp", method = RequestMethod.POST)
    public String testp(@RequestBody String name, BindingResult result, ModelMap modelMap) {
        return name;
    }

    @RequestMapping(value = "testu", method = RequestMethod.POST)
    public User testu(@RequestBody String user, BindingResult result, ModelMap modelMap) {
        return null;
    }

    /**
     * The purpose of this controller is to get picture of Prescription
     *
     * @param response
     * @param id
     */
    @RequestMapping(value = "prescription/{id}", method = RequestMethod.GET)
    public void pictureprescription(HttpServletResponse response, @PathVariable Long id) {
        File imageFile = new File(uploadDirectory + "prescriptionReport" + id + ".pdf");
        try {
            InputStream is = new FileInputStream(imageFile);
            IOUtils.copy(is, response.getOutputStream());
        } catch (IOException e) {
        e.printStackTrace();
        }
    }

    /**
     * The purpose of this controller is to get picture of Laborder
     *
     * @param response
     * @param id
     */
    @RequestMapping(value = "laborder/{id}", method = RequestMethod.GET)
    public void picturelaborder(HttpServletResponse response, @PathVariable Long id) {
        File imageFile = new File(uploadDirectory + "labOrderReport" + id + ".pdf");
        try {
            InputStream is = new FileInputStream(imageFile);
            IOUtils.copy(is, response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "shareRecord", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> shareRecord(@RequestBody User user, BindingResult result, ModelMap modelMap) {

        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";
            Long userId = user.getUserId();
            User u = userRepository.findOne(userId);
            if (u != null) {
                String email = user.getEmail();
                String[] emailIds;
                emailIds = email.split(",");
                String[] emails = emailIds;
                if (u.getRole().equals("ROLE_PATIENT")) {
                    emails = commonRepository.getEmails(emailIds);
                }

                List<String> fileNames = new ArrayList<>();
                String reports = user.getReports();
                String[] reportIds = reports.split(",");
                String saveDir = utilityServices.getMessage("Encounter.share.download");
                if (emailIds.length != 0 && reportIds.length != 0) {
                    for (String id : reportIds) {


                        String retriveUrl = this.messageSource.getMessage("alfresco.retrive.files", null, "Default alfresco retrive files", null);
                        String downloadUrl = this.messageSource.getMessage("alfresco.downloadUrl", null, "Default alfresco downloadUrl", null);


                        String queryResult = uploadFileRepository.getRecordInfoToShare(Long.parseLong(id));
                        String[] data = queryResult.split(",");
                        String fileURL = retriveUrl + data[1] + "/" + data[0] + downloadUrl + "&alf_ticket=" + user.getAlfresAuthTicket();

                        try {
                            String fileName = data[0];
                            fileNames.add(fileName);
                            alfrescoAuthDetails.downloadFile(fileURL, saveDir, fileName);
                            status = HttpStatus.OK;
                            message = "Shared Successfully.";
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }

                    }
                    emailController.sendRecord(userId, fileNames, emails, reportIds);

                } else {
                    status = HttpStatus.NOT_ACCEPTABLE;
                    message = "Not shared... Try again !!!";
                }

            }
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    @RequestMapping(value = "moveRecord", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> moveRecord(@RequestBody UploadFile uploadFile, BindingResult result, ModelMap modelMap) {

        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";
        if (uploadFile != null) {
            Long fileId = uploadFile.getFileId();
            String fileType = uploadFile.getFileType();
            if (fileId != null && fileType != null && !fileType.isEmpty()) {
                UploadFile file = uploadFileRepository.findOne(fileId);
                file.setFileType(fileType);
                uploadFileRepository.save(file);
                status = HttpStatus.OK;
                message = "Moved Successfully !!!";
            }
        }
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    @RequestMapping(value = "profileImageUpload", method = RequestMethod.POST, produces = "application/json")
    public Map<String, Object> profileImageUpload(@ModelAttribute User user,
                                                  @ModelAttribute MultiFileUpload multiFileUpload, BindingResult result, ModelMap modelMap) {

        User u = userRepository.findOne(user.getUserId());

        Map<String, Object> files = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";

        List<User> list = new LinkedList<>();

        for (MultipartFile mpf : multiFileUpload.getMultiUploadedFileList()) {
            String newFilenameBase = UUID.randomUUID().toString();
            String originalFileExtension = mpf.getOriginalFilename().substring(mpf.getOriginalFilename().lastIndexOf("."));
            String newFilename = "";
            newFilename = "user_" + u.getUserId() + originalFileExtension;

            String storageDirectory = utilityServices.getMessage("User.profile.imagesPath");
            String contentType = mpf.getContentType();

            File newFile = new File(storageDirectory + newFilename);
            try {
                mpf.transferTo(newFile);

                u.setImageFileName(mpf.getOriginalFilename());

                String thumbnailFilename = "";

                thumbnailFilename = "userthumbnail_" + u.getUserId() + ".png";

                new File(storageDirectory + thumbnailFilename).delete();
                File thumbnailFile = new File(storageDirectory + thumbnailFilename);
                if (contentType.equals("image/jpeg") ||
                        contentType.equals("image/png") || contentType.equals("image/gif")) {
                    BufferedImage thumbnail = Scalr.resize(ImageIO.read(newFile), 100);
                    ImageIO.write(thumbnail, "png", thumbnailFile);

                    u.setImageThumbnailFileName(thumbnailFilename);

                } else {
                    BufferedImage thumbnail = Scalr.resize(ImageIO.read(new File("D:\\nopreview.jpg")), 90);
                    ImageIO.write(thumbnail, "png", thumbnailFile);

                    u.setImageThumbnailFileName(thumbnailFilename);

                }

                u.setImageFileName(newFilename);
                userRepository.save(u);

                status = HttpStatus.OK;
                message = "Your request has been sent successfully !!!";

                list.add(u);
            } catch (IOException e) {
                message = "Your request has not been sent successfully. Try again !!!";
            }
        }
        files.put("imageUrl", utilityServices.getMessage("User.profileImage.Url") + u.getUserId());

        return files;
    }


    @RequestMapping(value = "identityImageUpload", method = RequestMethod.POST, produces = "application/json")
    public Map<String, Object> identityImageUpload(@ModelAttribute User user,
                                                   @ModelAttribute MultiFileUpload multiFileUpload, BindingResult result, ModelMap modelMap) throws IOException {


        Map<String, Object> files = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";
        /*IdCardDetails idCardDetails = idCardDetailsRepository.findByUserId(u.getUserId());*/
        User u = userRepository.findOne(user.getUserId());
        for (MultipartFile mpf : multiFileUpload.getMultiUploadedFileList()) {

            String originalFileExtension = mpf.getOriginalFilename().substring(mpf.getOriginalFilename().lastIndexOf("."));
            String newFilename = "";
            newFilename = "userIdentity_" + u.getUserId() + originalFileExtension;

            String storageDirectory = utilityServices.getMessage("User.profile.imagesPath");
            String contentType = mpf.getContentType();
            IdCardDetails idCardDetails = user.getIdCardDetails();
                if(u.getIdCardDetails() != null) {
                    String prevFileName = u.getIdCardDetails().getFileName();
                    File prevFile = new File(storageDirectory + prevFileName);
                    idCardDetails.setCardDetailsId(u.getIdCardDetails().getCardDetailsId());
                    boolean exists = prevFile.exists();
                    if (exists) {
                        boolean deleted = prevFile.delete();
                    }
                }
                try {
                    File newFile = new File(storageDirectory + newFilename);
                    mpf.transferTo(newFile);
                    u.setImageFileName(mpf.getOriginalFilename());
                    String thumbnailFilename = "";
                    thumbnailFilename = "Identitythumbnail_" + u.getUserId() + ".png";
                    new File(storageDirectory + thumbnailFilename).delete();
                    File thumbnailFile = new File(storageDirectory + thumbnailFilename);
                    if (contentType.equals("image/jpeg") || contentType.equals("image/png") || contentType.equals("image/gif")) {
                        BufferedImage thumbnail = Scalr.resize(ImageIO.read(newFile), 100);
                        ImageIO.write(thumbnail, "png", thumbnailFile);

                    } else {
                        BufferedImage thumbnail = Scalr.resize(ImageIO.read(new File("D:\\nopreview.jpg")), 90);
                        ImageIO.write(thumbnail, "png", thumbnailFile);
                    }

                    idCardDetails.setThumbnailFileName(thumbnailFilename);
                    idCardDetails.setFileName(newFilename);
                    idCardDetails.setUser(u);
                    idCardDetailsRepository.save(idCardDetails);
                    status = HttpStatus.OK;
                    message = utilityServices.getMessage("image.upload.successfully");
                }
             catch (IOException e) {
                message = "Your request has not been sent successfully. Try again !!!";
                e.printStackTrace();
            }
        }
        files.put("message", message);
        files.put("statusCode", status.value());
        files.put("imageUrl", utilityServices.getMessage("User.identityImage.Url") + u.getUserId());
        return files;
    }


    @RequestMapping(value = "userthumbnail/{userId}", method = RequestMethod.GET)
    public void picture(@PathVariable String userId,HttpServletResponse response) {
        String storageDirectory = utilityServices.getMessage("User.profile.imagesPath");
        String fileName = userRepository.findThubnailFileName(Long.parseLong(userId));
        File imageFile = new File(storageDirectory + fileName);
        InputStream is = null;
        try {
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache, must-revalidate");
            response.setDateHeader("Expires", 0);
            is = new FileInputStream(imageFile);
            IOUtils.copy(is, response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }

        }
    }

    @RequestMapping(value = "identityImage/{userId}", method = RequestMethod.GET)
    public void identityImage(HttpServletResponse response, @PathVariable String userId) {
        String storageDirectory = utilityServices.getMessage("User.profile.imagesPath");
        String fileName = idCardDetailsRepository.getFileName(Long.parseLong(userId));
        File imageFile = new File(storageDirectory + fileName);
        InputStream is = null;
        try {
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache, must-revalidate");
            response.setDateHeader("Expires", 0);
            is = new FileInputStream(imageFile);
            IOUtils.copy(is, response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert is != null;
                is.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }

        }
    }

    @RequestMapping(value = "identityThumbnail/{userId}", method = RequestMethod.GET)
    public void identityPiCTURE(HttpServletResponse response, @PathVariable String userId) {
        String storageDirectory = utilityServices.getMessage("User.profile.imagesPath");
        String fileName = idCardDetailsRepository.getThumbnailFileName(Long.parseLong(userId));
        File imageFile = new File(storageDirectory + fileName);
        InputStream is = null;
        try {
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache, must-revalidate");
            response.setDateHeader("Expires", 0);

            is = new FileInputStream(imageFile);
            IOUtils.copy(is, response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    @RequestMapping(value = "getProfileImage", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> getProfileImage(@RequestBody User user, BindingResult result, ModelMap modelMap) {
        Map<String, Object> response = new HashMap<>();

        User u = userRepository.findOne(user.getUserId());

        String thumbnailUrl = this.messageSource.getMessage("alfresco.thumnail.files", null, "Default alfresco thumnail files", null);
        String thumbnail = this.messageSource.getMessage("alfresco.thumnailUrl", null, "Default alfresco thumnailUrl", null);

        String ticket = alfrescoAuthDetails.getAlfrescoConnectionTicket(u.getUserName(), u.getPassword());
        MyUserDetailsService.getUserFromSession().setAlfresAuthTicket(ticket);

        u.setProfileImageUrl(thumbnailUrl + u.getImageFileName() + thumbnail + "&alf_ticket=" + ticket);
        response.put("url", u.getProfileImageUrl());
        return response;
    }

    @RequestMapping(value = "viewAppointment", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> getPatientAppointmentList(@RequestBody User user, BindingResult result, ModelMap modelMap) {
        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        List<ConfirmAppointment> list = new ArrayList<>();
        String message = "";

        if (user.getUserId() != null) {
            //db call to get all the past scheduled status
            List<ConfirmAppointment> pastScheduledList = appointmentRepository.updateStatusForPastDate(user.getUserId());
            //update the scheduled status to noshow and Arrived,Inprogess to Completed
            for (ConfirmAppointment appointment : pastScheduledList) {
                if (SchedulingStatus.SCHEDULED.equals(appointment.getStatus())) {
                    appointment.setStatus(SchedulingStatus.NOSHOW);
                    appointmentRepository.save(appointment);
                }
                if (SchedulingStatus.ARRIVED.equals(appointment.getStatus()) || SchedulingStatus.INPROGRESS.equals(appointment.getStatus())) {
                    appointment.setStatus(SchedulingStatus.COMPLETED);
                    appointmentRepository.save(appointment);
                }
            }
            list = userRepository.getPatientAppointmentList(user.getUserId());
            if (list.size() != 0) {
                response.put("appointmentList", list);
                status = HttpStatus.OK;
            } else {
                message = utilityServices.getMessage("user.notScheduledRCancel.success");
                status = HttpStatus.CONTINUE;
            }
        }
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    @RequestMapping(value = "validateIDCardDetails", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> validateIDCardDetails(@RequestBody Long userId, BindingResult result, ModelMap modelMap) {
        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = utilityServices.getMessage("user.validateIdCard");
        if (userId != null) {
            List<UserEmergencyInfo> info = userEmergencyRepository.findById(userId);
            Integer value = userDetailsRepository.findBloodGroupByUserId(userId);
            if (info != null && !info.isEmpty()) {
                String mobilePhone = info.get(0).getMobileNumber();
                String homePhone = info.get(0).getHomePhone();
                if ( value != null && value != 1  && value != 0 && (StringUtils.isNotBlank(mobilePhone) || StringUtils.isNotBlank(homePhone))) {
                    status = HttpStatus.OK;
                }
            }
        }
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    @RequestMapping(value = "validateNewConsultation", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> validateNewConsultation(@RequestBody Long userId, BindingResult result, ModelMap modelMap) {

        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = utilityServices.getMessage("user.validateNewConsultation");
        if (userId != null) {
            UserDetails userDetails  = userDetailsRepository.findByUserId(userId);
            Integer value = userDetails.getBloodGroup();
            String dob = userDetails.getDateofBirth();
            String gen = userDetails.getGender();
                if ( value != null && value != 1  && value != 0  && !StringUtils.isBlank(dob) && !StringUtils.isBlank(gen)) {
                    status = HttpStatus.OK;
                    message = "";
                }
        }
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    @RequestMapping(value = "saveServiceRequests", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> saveServiceRequests(@Valid @RequestBody ServiceRequests serviceRequests,BindingResult result, ModelMap modelMap){
        logger.info("Requested for path saveServiceRequests" + serviceRequests);
        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";
              try{
                    switch(serviceRequests.getServiceType()){

                        case RolesInServices.TYPE_DIAGNOSTIC :

                                LabRequests labRequest = serviceRequests.getLabRequests();
                                labRequest.setPatientId(serviceRequests.getPatientId());
                                labRequest.setOrderDate(new Date());
                                labRequest.setOrderDateTime(new Date());
                                User user = userRepository.findPatient(serviceRequests.getPatientId());

                                if(serviceRequests.getProviderId() != null){

                                    labRequest.setProviderId(serviceRequests.getProviderId());
                                    labRequest.setAssignedDate(new Date());
                                    labRequestsRepository.save(labRequest);
                                    UploadFile uploadFile = uploadFileRepository.findOne(labRequest.getRecordIds());
                                    labRequest.setUploadFile(uploadFile);
                                    uploadFile.setServiceRequests(labRequest);
                                    uploadFileRepository.save(uploadFile);

                                }else{
                                    labRequestsRepository.save(labRequest);
                                }

                                if(serviceRequests.getAlternateServiceContactInfo()!= null) {
                                    AlternateServiceContactInfo serviceContactInfo = serviceRequests.getAlternateServiceContactInfo();
                                    //oneTone mapping between labRequests and AlternateServiceContactInfo is bydirection so set object viceversa
                                    labRequest.setAlternateServiceContactInfo(serviceContactInfo);
                                    serviceContactInfo.setLabRequests(labRequest);
                                    altServContactInfoRepository.save(serviceContactInfo);
                                }
                                message = "Your request is received successfully and request ID for "+RolesInServices.String_DIAGNOSTIC+" is "+labRequest.getOrderId()+".";
                                alfrescoAuthDetails.sendMessage(user.getUserContactInfo().getMobilePhone(),message);
                                status = HttpStatus.OK;
                            break;

                        case RolesInServices.TYPE_PHARMACY:

                            PharmacyRequests pharmacyRequest = serviceRequests.getPharmacyRequests();
                            pharmacyRequest.setPatientId(serviceRequests.getPatientId());
                            pharmacyRequest.setOrderDate(new Date());
                            pharmacyRequest.setOrderDateTime(new Date());
                            pharmacyRequest.setStatus(serviceRequests.getStatus());
                            if(serviceRequests.getProviderId() != null){
                                pharmacyRequest.setProviderId(serviceRequests.getProviderId());
                                pharmacyRequest.setAssignedDate(new Date());
                                pharmacyRequestRepository.save(pharmacyRequest);
                                UploadFile uploadFile = uploadFileRepository.findOne(pharmacyRequest.getRecordIds());
                                pharmacyRequest.setUploadFile(uploadFile);
                                uploadFile.setServiceRequests(pharmacyRequest);
                                uploadFileRepository.save(uploadFile);
                                message = "Your transaction  is save successfully and transaction ID for "+RolesInServices.String_PHARMACY+" is "+pharmacyRequest.getOrderId()+".";

                            }else{
                                pharmacyRequestRepository.save(pharmacyRequest);
                                message = "Your request is received successfully and request ID for "+RolesInServices.String_PHARMACY+" is "+pharmacyRequest.getOrderId()+".";
                            }

                                if(serviceRequests.getAlternateServiceContactInfo()!= null) {
                                    AlternateServiceContactInfo serviceContactInfo = serviceRequests.getAlternateServiceContactInfo();
                                    pharmacyRequest.setAlternateServiceContactInfo(serviceContactInfo);
                                    altServContactInfoRepository.save(serviceContactInfo);
                                }

                                status = HttpStatus.OK;
                                break;

                        case RolesInServices.TYPE_SURGERYREFERRAL:
                                break;

                        case RolesInServices.TYPE_SECONDOPINION:
                            break;
                    }
              }catch (Exception e){
                  e.printStackTrace();
                  status = HttpStatus.CONTINUE;
                  message = "Oops something went wrong please contact admin";
                  logger.error(e.toString());
              }
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        logger.info("End of path saveServiceRequests" + response);
        return response;
    }


    @RequestMapping(value = "count/{id}", method = RequestMethod.GET)
    public Long messageCount(@PathVariable(value = "id") Long id, ModelMap modelMap) {
        return messageRepository.countByPatientId(id);

    }
}


