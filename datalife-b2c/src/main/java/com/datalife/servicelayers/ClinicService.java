package com.datalife.servicelayers;

import com.datalife.controller.EmailController;
import com.datalife.entities.*;
import com.datalife.repositories.*;
import com.datalife.services.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by supriya gondi on 2/12/2015.
 */
@RestController
@RequestMapping(value = "/api/user/clinic/")
public class ClinicService {
    /**
     * Reference to userRepository
     */
    @Autowired
    UserRepository userRepository;
    /**
     * Reference to clinicDoctorsRepository
     */
    @Autowired
    ClinicDoctorsRepository clinicDoctorsRepository;
    /**
     * Reference to userDetailsRepository
     */
    @Autowired
    UserDetailsRepository userDetailsRepository;
    /**
     * Reference to doctorInfoRepository
     */
    @Autowired
    DoctorInfoRepository doctorInfoRepository;
    /**
     * Reference to userContactRepository
     */
    @Autowired
    UserContactRepository userContactRepository;
    /**
     * Reference to clinicInfoRepository
     */
    @Autowired
    ClinicInfoRepository clinicInfoRepository;
    @Autowired
    CommonService commonService;

    @Autowired
    ClinicPatientRepository clinicPatientRepository;

    @Autowired
    CommonRepository commonRepository;

    @Autowired
    UtilityServices utilityServices;

    @Autowired
    AuthenticationRepository authenticationRepository;


    @Autowired
    LabServiceProviderRepository labServiceProviderRepository;

    @Autowired
    PharmacyServiceProviderRepository pharmacyServiceProviderRepository;

    @Autowired
    AlfrescoAuthDetails alfrescoAuthDetails;

    @Autowired
    EncounterRepository encounterRepository;


    @Autowired
    EmailController emailController;

    private static final Logger logger = LoggerFactory.getLogger(ClinicService.class);

    /**
     * This method is to add Doctor to clinic
     *
     * @param result
     * @param modelMap
     * @return json contains status ,statuscode and message etc...
     */
    @RequestMapping(value = "getDoctors", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> addDoctor(@RequestBody Long userId, BindingResult result, ModelMap modelMap) {
        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "No Doctor Found !!!";


        if (userId != null && userId != 0L) {
            List<ClinicDoctors> clinicDoctorsList = clinicDoctorsRepository.getDoctorsListByClinicId(userId);
            List<User> doctors = new ArrayList<>();
            if (clinicDoctorsList != null && !clinicDoctorsList.isEmpty()) {
                for (ClinicDoctors clinicDoctors : clinicDoctorsList) {
                    Long id = clinicDoctors.getDoctorId();
                    if (id != null) {
                        User doctor = userRepository.findOne(id);
                        if (doctor != null) {
                            User newDoctor = CommonServices.getOnlyUser(doctor);
                            UserDetails userDetails = CommonServices.getOnlyUserDetails(userDetailsRepository.findByUserId(id));
                            DoctorInfo doctorInfo = CommonServices.getOnlyDoctorInfo(doctorInfoRepository.findByUserId(id));
                            UserContactInfo contactInfo = CommonServices.getOnlyUserContactInfo(userContactRepository.findByUserId(id));
                            newDoctor.setUserDetails(userDetails);
                            newDoctor.setDoctorInfo(doctorInfo);
                            newDoctor.setUserContactInfo(contactInfo);
                            if (clinicDoctors.getSpecialityId() != null) {
                                newDoctor.setSpeciality(commonRepository.getSpecialityById(clinicDoctors.getSpecialityId()));
                            }
                            newDoctor.setActivate(clinicDoctors.isActivate());
                            if (doctor.getImageFileName() != null && !doctor.getImageFileName().isEmpty()) {
                                newDoctor.setSendType(utilityServices.getMessage("User.profileImage.Url") + id);
                                newDoctor.setDoctorId(id);
                            } else {
                                newDoctor.setSendType(utilityServices.getMessage("Doctor.DefaultImage.Url"));
                                newDoctor.setDoctorId(null);
                            }
                            doctors.add(newDoctor);
                        }
                    }
                }
                response.put("doctors", doctors);

            }
            status = HttpStatus.OK;
        }


        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    @RequestMapping(value = "getDoctorSignup", method = RequestMethod.GET, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> getDoctorRegistration(ModelMap modelMap) {
        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.OK;
        String message = "";

        response.put("specialities", commonRepository.getSpecialities());

        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    @RequestMapping(value = "getProfile", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> getProfile(@RequestBody Long userId, BindingResult result, ModelMap modelMap) {

        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";

        if (userId != null && userId != 0L) {

                ClinicInfo clinicInfo = clinicInfoRepository.findByUserId(userId);
                if (clinicInfo != null) {
                    clinicInfo.setPharmacy(null);
                    clinicInfo.setLab(null);
                    UserContactInfo contactInfo = CommonServices.getOnlyUserContactInfo(clinicInfo.getUser().getUserContactInfo());
                    User user = CommonServices.getOnlyUser(clinicInfo.getUser());
                    user.setClinicInfo(null);
                    user.setUserContactInfo(contactInfo);
                    clinicInfo.setUser(user);

                    response.put("clinic", clinicInfo);
                    response.put("countries", commonService.getCountries());
                    status = HttpStatus.OK;
                }
        }

        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    @RequestMapping(value = "saveProfile", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> saveProfile(@RequestBody User user, BindingResult result, ModelMap modelMap) {

        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "Not Saved. Try again !!!";
        User u = CommonServices.getSessionUser();

        if (u != null) {
            Long userId = u.getUserId();
            ClinicInfo clinicInfo = clinicInfoRepository.findByUserId(userId);
            ClinicInfo newClinicInfo = user.getClinicInfo();
            if (clinicInfo != null && newClinicInfo != null) {
                newClinicInfo.setUserId(clinicInfo.getUserId());
                user.setRole("ROLE_CLINIC");
                newClinicInfo.setUser(u);
            }

            UserContactInfo contactInfo = userContactRepository.findByUserId(userId);
            UserContactInfo userContactInfo = user.getUserContactInfo();
            if (userContactInfo != null && contactInfo != null) {
                userContactInfo.setUserContactInfoId(contactInfo.getUserContactInfoId());
                userContactInfo.setUser(u);
            }

            u.setClinicInfo(newClinicInfo);
            u.setUserContactInfo(userContactInfo);

            userRepository.save(u);

            response.put("ci", CommonServices.getOnlyClinicInfo(newClinicInfo));
            response.put("uci", CommonServices.getOnlyUserContactInfo(userContactInfo));
            response.put("user", CommonServices.getOnlyUser(u));
            response.put("countries", commonService.getCountries());

            message = utilityServices.getMessage("Clinic.saveProfile.Message");
            status = HttpStatus.OK;
        }

        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    @RequestMapping(value = "saveSettings", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> saveSettings(@RequestBody ClinicDoctors clinicDoctors, BindingResult result, ModelMap modelMap) {

        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";
        Long clinicId = clinicDoctors.getClinicId();
        Long doctorId = clinicDoctors.getDoctorId();
        User doctor = userRepository.findOne(doctorId);
        User clinic = userRepository.findOne(clinicId);
        if (clinic != null && doctor != null) {
            Long id = clinicDoctorsRepository.getIdByDoctorAndClinic(clinicId, doctorId);
            if (id != null && id != 0L) {
                clinicDoctors.setId(id);
            }
            ClinicDoctors cd = null;

            if (DateService.getTodayDate().equals(clinicDoctors.getDate()) || id == null || id == 0L) {
                clinicDoctors.setChangeSetEfectFrm(new Date());
                cd = clinicDoctorsRepository.save(clinicDoctors);
            } else {
                cd = clinicDoctorsRepository.findOne(id);
                if (StringUtils.isNotBlank(clinicDoctors.getDate())) {
                    NewTimings newTimings = new NewTimings();
                    if (cd.getNewTimings() != null) {
                        newTimings.setSettingsId(cd.getNewTimings().getSettingsId());
                    }
                    newTimings.setMonst_1(clinicDoctors.getMonst_1());
                    newTimings.setMonst_2(clinicDoctors.getMonst_2());
                    newTimings.setMonet_1(clinicDoctors.getMonet_1());
                    newTimings.setMonet_2(clinicDoctors.getMonet_2());
                    newTimings.setMonStatus_1(clinicDoctors.isMonStatus_1());

                    newTimings.setTuest_1(clinicDoctors.getTuest_1());
                    newTimings.setTuest_2(clinicDoctors.getTuest_2());
                    newTimings.setTueet_1(clinicDoctors.getTueet_1());
                    newTimings.setTueet_2(clinicDoctors.getTueet_2());
                    newTimings.setTueStatus_1(clinicDoctors.isTueStatus_1());

                    newTimings.setWedst_1(clinicDoctors.getWedst_1());
                    newTimings.setWedst_2(clinicDoctors.getWedst_2());
                    newTimings.setWedet_1(clinicDoctors.getWedet_1());
                    newTimings.setWedet_2(clinicDoctors.getWedet_2());
                    newTimings.setWedStatus_1(clinicDoctors.isWedStatus_1());

                    newTimings.setThust_1(clinicDoctors.getThust_1());
                    newTimings.setThust_2(clinicDoctors.getThust_2());
                    newTimings.setThuet_1(clinicDoctors.getThuet_1());
                    newTimings.setThuet_2(clinicDoctors.getThuet_2());
                    newTimings.setThuStatus_1(clinicDoctors.isThuStatus_1());

                    newTimings.setFrist_1(clinicDoctors.getFrist_1());
                    newTimings.setFrist_2(clinicDoctors.getFrist_2());
                    newTimings.setFriet_1(clinicDoctors.getFriet_1());
                    newTimings.setFriet_2(clinicDoctors.getFriet_2());
                    newTimings.setFriStatus_1(clinicDoctors.isFriStatus_1());

                    newTimings.setSatst_1(clinicDoctors.getSatst_1());
                    newTimings.setSatst_2(clinicDoctors.getSatst_2());
                    newTimings.setSatet_1(clinicDoctors.getSatet_1());
                    newTimings.setSatet_2(clinicDoctors.getSatet_2());
                    newTimings.setSatStatus_1(clinicDoctors.isSatStatus_1());

                    newTimings.setSunst_1(clinicDoctors.getSunst_1());
                    newTimings.setSunst_2(clinicDoctors.getSunst_2());
                    newTimings.setSunet_1(clinicDoctors.getSunet_1());
                    newTimings.setSunet_2(clinicDoctors.getSunet_2());
                    newTimings.setSunStatus_1(clinicDoctors.isSunStatus_1());

                    newTimings.setSlotTime(clinicDoctors.getSlotTime());
                    newTimings.setClinicDoctors(clinicDoctors);
                    cd.setNewTimings(newTimings);

                        cd.setChangeSetEfectFrm(DateService.stringToDateConversion(clinicDoctors.getDate()));

                }
                cd.setSlotTime(clinicDoctors.getSlotTime());
                cd.setSpecialityId(clinicDoctors.getSpecialityId());
                cd.setExperience(clinicDoctors.getExperience());
                cd.setConsultationFee(clinicDoctors.getConsultationFee());
                cd.setQualification(clinicDoctors.getQualification());
                if (StringUtils.isNotBlank(clinicDoctors.getQualification())) {
                    cd.setActivate(clinicDoctors.isActivate());
                }
                cd = clinicDoctorsRepository.save(cd);
            }

            response.put("newtimings", commonRepository.getNewDoctorTimings(cd.getNewTimings()));
            response.put("timings", commonRepository.getDoctorTimings(cd));
            if (cd.getChangeSetEfectFrm() != null) {
                response.put("changeSetEfectFrm", DateService.dateToStringConversion(cd.getChangeSetEfectFrm()));
            } else {
                response.put("changeSetEfectFrm", DateService.getTodayDate());
            }
            String qualification = clinicDoctors.getQualification();
            if (qualification != null && !qualification.isEmpty()) {
                DoctorInfo doctorInfo = doctorInfoRepository.findByUserId(doctorId);
                doctorInfo.setQualification(qualification);
                doctorInfoRepository.save(doctorInfo);
            }
            status = HttpStatus.OK;
            String names = userDetailsRepository.getFullName(doctorId);
            String[] name = names.split(",");
            message = " Dr. " + name[0] + " " + name[1] + " settings are saved successfully !!!";
            response.put("settings", clinicDoctors);
            response.put("specialities", commonRepository.getSpecialities());

        }

        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    @RequestMapping(value = "saveOnlyDoctorSettings", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> saveOnlyDoctorSettings(@RequestBody ClinicDoctors clinicDoctors, BindingResult result, ModelMap modelMap) {

        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";

        Long doctorId = clinicDoctors.getDoctorId();
        User doctor = userRepository.findsoProvider(doctorId);

        if (doctor != null) {
            Long id = clinicDoctorsRepository.getIdOnlyByDoctor(doctorId);
            if (id != null && id != 0) {
                clinicDoctors.setId(id);
            }
            clinicDoctorsRepository.save(clinicDoctors);
            status = HttpStatus.OK;
            String names = userDetailsRepository.getFullName(doctorId);
            String[] name = names.split(",");
            message = " Dr. " + name[0] + " " + name[1] + " settings are saved successfully !!!";
            response.put("settings", clinicDoctors);
            response.put("specialities", commonRepository.getSpecialities());
            response.put("doctorId", doctorId);
        }

        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    @RequestMapping(value = "getSettings", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> getSettings(@RequestBody User user, BindingResult result, ModelMap modelMap) {

        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";
        Long clinicId = user.getUserId();
        User clinic = userRepository.findOne(clinicId);
        if (clinic != null) {
            status = HttpStatus.OK;
            Map<Long, String> details = new LinkedHashMap<>();
            List<Long> doctorIds = clinicDoctorsRepository.getDoctorsByClinicId(clinicId);
            for (Long doctorId : doctorIds) {
                User doctor = userRepository.findOne(doctorId);
                if (doctor != null) {
                    String fullName = userDetailsRepository.getFullName(doctorId);
                    String[] names = fullName.split(",");
                    details.put(doctorId, "Dr. " + names[0] + " " + names[1]);
                }
            }
            response.put("doctors", details);

        }
        Date dbDate = (Date) commonRepository.getDate();
        response.put("dbDate", dbDate);

        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    @RequestMapping(value = "getSettingsData", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> getSettingsData(@RequestBody ClinicDoctors clinicDoctors, BindingResult result, ModelMap modelMap) {

        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";
        Long clinicId = clinicDoctors.getClinicId();
        Long doctorId = clinicDoctors.getDoctorId();
        User doctor = userRepository.findOne(doctorId);
        User clinic = userRepository.findOne(clinicId);
        if (clinic != null && doctor != null) {
            ClinicDoctors cd = clinicDoctorsRepository.findByDoctorAndClinic(clinicId, doctorId);
            cd.setQualification(doctorInfoRepository.getQualification(doctorId));
            response.put("specialities", commonRepository.getSpecialities());
            response.put("newtimings", commonRepository.getNewDoctorTimings(cd.getNewTimings()));
            response.put("settings", CommonServices.getClinicDoctors(cd));
            response.put("timings", commonRepository.getDoctorTimings(cd));
            if (cd.getChangeSetEfectFrm() != null) {
                response.put("changeSetEfectFrm", DateService.dateToStringConversion(cd.getChangeSetEfectFrm()));
            } else {
                response.put("changeSetEfectFrm", DateService.getTodayDate());
            }

            status = HttpStatus.OK;
        }
        Map<Long, String> details = new LinkedHashMap<>();
        List<Long> doctorIds = clinicDoctorsRepository.getDoctorsByClinicId(clinicId);
        for (Long id : doctorIds) {
            User user = userRepository.findOne(id);
            if (user != null) {
                String fullName = userDetailsRepository.getFullName(id);
                String[] names = fullName.split(",");
                details.put(id, "Dr. " + names[0] + " " + names[1]);
            }
        }
        response.put("clinicDoctors", details);
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    @RequestMapping(value = "/assignPatient", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> assignPatient(@RequestBody User user, BindingResult result, ModelMap modelMap) {
        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";
        if (user != null) {
            Long patientId = user.getPatientId();
            Long doctorId = user.getDoctorId();
            Long clinicId = user.getClinicId();
            if (patientId != null && patientId != 0 && doctorId != null && doctorId != 0) {
                ClinicPatients clinicPatients = clinicPatientRepository.findByPatientId(patientId, clinicId);
                if (clinicPatients != null) {
                    clinicPatients.setDoctorId(doctorId);
                    clinicPatientRepository.save(clinicPatients);
                    status = HttpStatus.OK;
                    message = "Patient assigned successfully!!! ";
                }
            }
        }
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    @RequestMapping(value = "getPatients", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> getPatients(@RequestBody User user, BindingResult result, ModelMap modelMap) {
        Map<String, Object> response = new HashMap<>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "No Patient Found !!!";
        List<User> patients = new ArrayList<>();
        if (user != null) {
            Long clinicId = user.getClinicId();
            Long docId = user.getDoctorId();
            if (clinicId != null && clinicId != 0L) {
                List<ClinicPatients> clinicPatientsList = null;
                if (docId != null && docId != 0L) {
                    clinicPatientsList = clinicPatientRepository.getPatientsListByClinicAndDoctorId(clinicId, docId);
                    if (clinicPatientsList != null && !clinicPatientsList.isEmpty()) {
                        for (ClinicPatients clinicPatients : clinicPatientsList) {
                            Long id = clinicPatients.getPatientId();
                            if (id != null) {
                                User newPatient = commonRepository.getUsers(id);
                                patients.add(newPatient);
                            }
                        }
                    }
                    response.put("role", "doctor");
                } else {
                    clinicPatientsList = clinicPatientRepository.getPatientsListByClinicId(clinicId);
                    if (clinicPatientsList != null && !clinicPatientsList.isEmpty()) {
                        for (ClinicPatients clinicPatients : clinicPatientsList) {
                            Long id = clinicPatients.getPatientId();
                            String fullName = "No doctor assigned";
                            Long doctorId = 0L;
                            if (clinicPatients.getDoctorId() != null && clinicPatients.getDoctorId() != 0L) {
                                doctorId = clinicPatients.getDoctorId();
                                String[] names = userDetailsRepository.getFullName(doctorId).split(",");
                                fullName = "Dr. " + names[0] + " " + names[1];
                            }
                            if (id != null) {
                                User newPatient = commonRepository.getUsers(id);
                                newPatient.setName(fullName);
                                patients.add(newPatient);
                            }
                        }
                    }
                    response.put("role", "clinic");

                }
                response.put("patients", patients);
                status = HttpStatus.OK;
                message = "";
            }
        }


        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    @RequestMapping(value = "/searchPatient", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> searchPatient(@RequestBody User user, BindingResult result, ModelMap modelMap) {
        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "Patient does not exist in the Database";

        UserDetails userDetails;
        UserContactInfo userContactInfo;

        User sessionUser = MyUserDetailsService.getUserFromSession();

        List<User> searchResults = new ArrayList<>();
        if (user != null) {
            UserDetails ud = user.getUserDetails();
            UserContactInfo uci = user.getUserContactInfo();
            List<User> users = userRepository.searchUser(user.getUserId(), user.getUserName(), ud.getFirstName(), ud.getLastName(), uci.getMobilePhone(), uci.getEmail());
            if (users != null && !users.isEmpty()) {
                for (User currentUser : users) {
                    Long id = currentUser.getUserId();
                    User newUser = CommonServices.getOnlyUser(currentUser);
                    userDetails = userDetailsRepository.findByUserId(id);
                    newUser.setUserDetails(CommonServices.getOnlyUserDetails(userDetails));
                    userContactInfo = userContactRepository.findByUserId(id);
                    newUser.setUserContactInfo(CommonServices.getOnlyUserContactInfo(userContactInfo));
                    searchResults.add(newUser);
                    response.put("users", searchResults);
                }
                response.put("clinicId", sessionUser.getUserId());
                status = HttpStatus.OK;
                message = "";
            }
        }
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    @RequestMapping(value = "/accessPatient", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> accessPatient(@RequestBody User user, BindingResult result) {
        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";
        User clinic = CommonServices.getSessionUser();
        if (user != null && clinic != null) {
            User u = userRepository.findOne(user.getUserId());
            if (u != null) {
                if (u.getUserDetails() != null) {
                    response.put("ud", CommonServices.getOnlyUserDetails(u.getUserDetails()));
                }
                if (u.getIdCardDetails() != null) {
                    response.put("icd", CommonServices.getOnlyIdCardDetails(u.getIdCardDetails()));
                    if (u.getIdCardDetails().getThumbnailFileName() != null && !u.getIdCardDetails().getThumbnailFileName().isEmpty()) {
                        response.put("idImage", "true");
                    } else {
                        response.put("idImage", "false");
                    }
                }
                if (u.getInsuranceDetails() != null) {
                    response.put("insd", CommonServices.getOnlyInsuranceDetails(u.getInsuranceDetails()));
                }
                if (u.getImageThumbnailFileName() != null && !u.getImageThumbnailFileName().isEmpty()) {
                    response.put("validImage", "true");
                } else {
                    response.put("validImage", "false");
                }
                response.put("user", CommonServices.getOnlyUser(u));
                response.put("bloodGroups", commonService.getBloodGroups());
                response.put("countries", commonService.getCountries());
                response.put("imageUrl", utilityServices.getMessage("User.profileImage.Url"));
                response.put("userName", u.getUserName());
                response.put("id", u.getUserId());
                response.put("clinicId", clinic.getUserId());
                status = HttpStatus.OK;
            }
        }
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    @RequestMapping(value = "/backtoHome", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> backToHome(@RequestBody User user, BindingResult result, ModelMap modelMap) {
        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";
        if (user != null) {
            Long userId = user.getUserId();
            User u = userRepository.findOne(userId);
            if (u != null) {
                response.put("userName", u.getUserName());
                response.put("id", u.getUserId());
                UserContactInfo contactInfo = userContactRepository.findByUserId(userId);
                response.put("mobile", contactInfo.getMobilePhone());
                response.put("email", contactInfo.getEmail());
                status = HttpStatus.OK;
            }
        }
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }


    /**
     * This method is get Patient By userId as search Results
     *
     * @param user
     * @param modelMap
     * @return Map<String, Object> signupDetails includes status,statusCode,message
     */
    @RequestMapping(value = "getDoctorById", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> getDoctorById(@RequestBody User user, BindingResult result, ModelMap modelMap) {
        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = utilityServices.getMessage("Doctor.notFound.MESSAGE");

        UserDetails userDetails;
        UserContactInfo userContactInfo;
        DoctorInfo doctorInfo;

        if (user != null) {
            Long doctorId = user.getDoctorId();
            List<User> searchResults = new ArrayList<User>();
            List<User> users = userRepository.searchDoctorByUserId(doctorId);
            if (users != null && !users.isEmpty()) {
                for (User currentUser : users) {
                    Long id = currentUser.getUserId();
                    doctorInfo = CommonServices.getOnlyDoctorInfo(currentUser.getDoctorInfo());
                    User newUser = CommonServices.getOnlyUser(currentUser);
                    userDetails = userDetailsRepository.findByUserId(id);
                    if (userDetails != null) {
                        newUser.setUserDetails(CommonServices.getOnlyUserDetails(userDetails));
                    }
                    userContactInfo = userContactRepository.findByUserId(id);
                    if (userContactInfo != null) {
                        newUser.setUserContactInfo(CommonServices.getOnlyUserContactInfo(userContactInfo));
                    }
                    if (doctorInfo != null) {
                        newUser.setDoctorInfo(doctorInfo);
                    }
                    searchResults.add(newUser);
                    response.put("users", searchResults);
                }
                response.put("clinicId", user.getClinicId());
                response.put("doctorId", user.getDoctorId());
                status = HttpStatus.OK;
                message = "";
            }
        }

        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    /**
     * This method is get Patient By userName as search Results
     *
     * @param user
     * @param modelMap
     * @return Map<String, Object> signupDetails includes status,statusCode,message
     */
    @RequestMapping(value = "getDoctorByUserName", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> getDoctorByUserName(@RequestBody User user, BindingResult result, ModelMap modelMap) {
        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = utilityServices.getMessage("Doctor.notFound.MESSAGE");
        UserDetails userDetails;
        UserContactInfo userContactInfo;
        DoctorInfo doctorInfo;

        if (user != null) {
            String userName = user.getUserName();
            List<User> searchResults = new ArrayList<User>();
            List<User> users = userRepository.searchDoctorByUserName(userName);
            if (users != null && !users.isEmpty()) {
                for (User currentUser : users) {
                    doctorInfo = CommonServices.getOnlyDoctorInfo(currentUser.getDoctorInfo());
                    Long id = currentUser.getUserId();
                    User newUser = CommonServices.getOnlyUser(currentUser);
                    userDetails = userDetailsRepository.findByUserId(id);
                    if (userDetails != null) {
                        newUser.setUserDetails(CommonServices.getOnlyUserDetails(userDetails));
                    }
                    userContactInfo = userContactRepository.findByUserId(id);
                    if (userContactInfo != null) {
                        newUser.setUserContactInfo(CommonServices.getOnlyUserContactInfo(userContactInfo));
                    }

                    if (doctorInfo != null) {
                        newUser.setDoctorInfo(doctorInfo);
                    }

                    searchResults.add(newUser);
                    response.put("users", searchResults);
                }
                response.put("clinicId", user.getClinicId());
                response.put("doctorId", user.getDoctorId());
                status = HttpStatus.OK;
                message = "";
            }
        }
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    /**
     * This methos is for Patient Authentication for doctor to view patient Details
     *
     * @param user
     * @param modelMap
     * @return Map<String, Object> signupDetails includes status,statusCode,message
     */
    @RequestMapping(value = "addDoctorToClinicAuthenticate", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> addDoctorToClinicAuthenticate(@RequestBody User user, BindingResult result, ModelMap modelMap) {
        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";
        if (user != null) {
            User currentUser = CommonServices.getSessionUser();
            Long addId = clinicDoctorsRepository.getIdByDoctorAndClinic(currentUser.getUserId(), user.getDoctorId());
            if (addId != null) {
                message = "Doctor already exists in the clinic!!!";
                status = HttpStatus.NOT_ACCEPTABLE;
            } else {
                Long doctorId = user.getDoctorId();
                User u = userRepository.findOne(doctorId);

                Random randomGenerator = new Random();
                Integer randomInt = randomGenerator.nextInt(100000);

                String messageBody = "Mr/ Ms. " + u.getUserDetails().getFirstName() + " " + u.getUserDetails().getLastName() + ", Your OTP for addition in clinic is " + randomInt + ". DataLife";

                try {
                    alfrescoAuthDetails.sendMessage(u.getUserContactInfo().getMobilePhone(), messageBody);
                } catch (Exception e) {
                    e.printStackTrace();
                }


                Authentication auth = new Authentication(randomInt.toString(), u);
                if (u.getAuthentication() != null) {
                    auth.setAuthId(u.getAuthentication().getAuthId());
                }
                authenticationRepository.save(auth);
                status = HttpStatus.OK;

            }
        }
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    /**
     * This methos is for Patient Authentication for doctor to view patient Details
     *
     * @param user
     * @param modelMap
     * @return Map<String, Object> signupDetails includes status,statusCode,message
     */
    @RequestMapping(value = "addPatientToClinicAuthenticate", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> addPatientToClinicAuthenticate(@RequestBody User user, BindingResult result, ModelMap modelMap) {
        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";
        if (user != null) {
            Long  addId = clinicPatientRepository.getIdByPatientAndClinic(user.getClinicId(), user.getPatientId());
            if (addId != null) {
                message = "Patient already exists in the clinic!!!";
                status = HttpStatus.NOT_ACCEPTABLE;
            } else {
                Long patientId = user.getPatientId();
                User u = userRepository.findOne(patientId);

                Random randomGenerator = new Random();
                Integer randomInt = randomGenerator.nextInt(100000);
                String messageBody = "Mr/ Ms. " + u.getUserDetails().getFirstName() + " " + u.getUserDetails().getLastName() + ", Your OTP for addition in clinic is " + randomInt + ". DataLife";

                try {
                    alfrescoAuthDetails.sendMessage(u.getUserContactInfo().getMobilePhone(), messageBody);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Authentication auth = new Authentication(randomInt.toString(), u);
                if (u.getAuthentication() != null) {
                    auth.setAuthId(u.getAuthentication().getAuthId());
                }
                authenticationRepository.save(auth);
                status = HttpStatus.OK;
                message = utilityServices.getMessage("Patient added Successfully");
            }
        }
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    /**
     * This method is for doctor to access patient Records and Encounter,etc... after successful authentication
     *
     * @param user
     * @param result
     * @param modelMap
     * @return Map<String, Object> signupDetails includes status,statusCode,message
     */
    @RequestMapping(value = "/addDoctorToClinic", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> addDoctorToClinic(@RequestBody User user, BindingResult result, ModelMap modelMap) {
        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";
        Long id = 0L;
        User u = null;
        if (user != null) {

            if (user.getDoctorId() != null) {
                id = user.getDoctorId();
                u = userRepository.findOne(id);
            }

            if (u != null) {
                String otp = authenticationRepository.findByUserId(id);
                if (user.getAuthentication().getOtp().equals(otp)) {
                    User currentUser = userRepository.findClinic(user.getClinicId());
                    synchronized (this) {
                        Long addId = clinicDoctorsRepository.getIdByDoctorAndClinic(currentUser.getUserId(), id);
                        if (addId != null) {
                            status = HttpStatus.CONTINUE;
                        } else {
                            ClinicDoctors clinicDoctors = new ClinicDoctors(currentUser.getUserId(), id);
                            clinicDoctorsRepository.save(clinicDoctors);

                            message = "Doctor added successfully !!!";
                            status = HttpStatus.OK;

                            userRepository.save(currentUser);

                        }
                    }
                } else {
                    message = "Enter valid OTP";
                    status = HttpStatus.NOT_ACCEPTABLE;

                }
            }
        }
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    /**
     * This method is for doctor to access patient Records and Encounter,etc... after successful authentication
     *
     * @param user
     * @param result
     * @param modelMap
     * @return Map<String, Object> signupDetails includes status,statusCode,message
     */
    @RequestMapping(value = "/addPatientToClinic", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> addPatientToClinic(@RequestBody User user, BindingResult result, ModelMap modelMap) {
        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";

        Long id = 0L;
        User u = null;
            if (user.getPatientId() != null) {
                id = user.getPatientId();
                u = userRepository.findOne(id);
            }
            if (u != null) {
                String otp = authenticationRepository.findByUserId(id);
                if (user.getAuthentication().getOtp().equals(otp)) {

                    synchronized (this) {
                        Long addId = clinicPatientRepository.getIdByPatientAndClinic(user.getClinicId(), id);
                        if (addId != null) {
                            message = "Patient already exists in the clinic !!!";
                            status = HttpStatus.CONTINUE;
                        } else {
                            ClinicPatients clinicPatients = null;
                            if (user.getDoctorId() != null) {
                                clinicPatients = new ClinicPatients(id, user.getClinicId(), user.getDoctorId());
                            } else {
                                clinicPatients = new ClinicPatients(user.getClinicId(), id);
                            }
                            clinicPatientRepository.save(clinicPatients);
                            message = "Patient added successfully !!!";
                            status = HttpStatus.OK;
                        }
                    }
                } else {
                    message = "Enter valid OTP";
                    status = HttpStatus.NOT_ACCEPTABLE;
                }
            }

        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }


    @RequestMapping(value = "/validateClinicPatientExists", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> validateClinicPatientExists(@RequestBody User user, BindingResult result, ModelMap modelMap) {
        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";
        boolean verdict = false;
        boolean flag = false;
            synchronized (this) {
                Long addId = clinicPatientRepository.validate(user.getPatientId(), user.getClinicId(), user.getDoctorId());
                User user1 = userRepository.findOne(user.getPatientId());
                if(user1.getAlfrescoUrl() == null && user1.getUserParentDir()== null){
                    flag = true;
                    message = utilityServices.getMessage("Patient.validate.Consultation");
                }
                if (addId != null) {
                    verdict = true;
                }
                status = HttpStatus.OK;
            }
        response.put("exists", verdict);
        response.put("flag", flag);
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    @RequestMapping(value = "/searchDoctors", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> searchDoctors(@RequestBody String input, BindingResult result, ModelMap modelMap) {
        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";
        if (input != null && !input.isEmpty()) {
            List<User> users = commonRepository.getDoctorsBySearch(input.trim());

            response.put("users", users);
            status = HttpStatus.OK;
        }

        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    @RequestMapping(value = "/searchPatients", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> searchPatients(@RequestBody String input, BindingResult result, ModelMap modelMap) {
        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";
        if (input != null && !input.isEmpty()) {
            List<User> users = commonRepository.getPatientsBySearch(input.trim());
            if(users.size() > 0){
                response.put("users", users);
                status = HttpStatus.OK;
            }else{
                message = utilityServices.getMessage("Patient.notFound.MESSAGE");
                status = HttpStatus.CONTINUE;
            }
        }
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    @RequestMapping(value = "/searchClinicPatients", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> searchClinicPatients(@RequestBody String input, BindingResult result, ModelMap modelMap) {
        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";
        if (input != null && !input.isEmpty()) {
            List<User> users = commonRepository.getPatientsBySearch(input.trim());
            response.put("users", users);
            status = HttpStatus.OK;
        }
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    @RequestMapping(value = "/getDoctorsFromClinic", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<Long, String> getDoctorsFromClinic(@RequestBody Long userId, BindingResult result, ModelMap modelMap) {
        if (userId != null && userId != 0L) {
            return commonRepository.getDoctorsFromClinic(userId);
        }
        return null;
    }

    @RequestMapping(value = "/getPreference", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> getPreference(@RequestBody Long userId, BindingResult result, ModelMap modelMap){

        logger.info("Requested for path /getPreference : "+userId);

        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status;
        String message = "";
        ClinicInfo clinicInfo =  clinicInfoRepository.getLabAndPharmacyByClinicId(userId);
        Lab lab = clinicInfo.getLab();
        Pharmacy pharmacy = clinicInfo.getPharmacy();

        if(lab == null && pharmacy == null){
            status = HttpStatus.CONTINUE;
        }else{
            if(lab != null){

                UserContactInfo contactInfo = lab.getUser().getUserContactInfo();
                User user = CommonServices.getOnlyUser(lab.getUser());
                user.setUserContactInfo(CommonServices.getOnlyUserContactInfo(contactInfo));
                lab.setClinic(null);
                lab.setUser(user);
                response.put("lab", lab);
            }

            if(pharmacy != null){
                UserContactInfo contactInfo = pharmacy.getUser().getUserContactInfo();
                User user = CommonServices.getOnlyUser(pharmacy.getUser());
                user.setUserContactInfo(CommonServices.getOnlyUserContactInfo(contactInfo));
                pharmacy.setClinic(null);
                pharmacy.setUser(user);
                response.put("pharmacy", pharmacy);
            }
            status = HttpStatus.OK;
        }

        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        logger.info("End of path /getPreference : "+response);
        return response;
    }

    @RequestMapping(value = "/addLabToClinic", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> addLabToClinic(@RequestBody Lab lab, BindingResult result, ModelMap modelMap){

        logger.info("Requested for path /addLabToClinic : "+lab);

        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";
        try{
            Set<ClinicInfo> clinicInfoSet = new HashSet<>();
            ClinicInfo clinicInfo = clinicInfoRepository.getLabByClinicId(lab.getClinicId());
            if(clinicInfo == null || clinicInfo.getLab() == null || !(clinicInfo.getLab().getUserId()).equals(lab.getUserId())){
                Lab lab1 = labServiceProviderRepository.fetchLabByUserIdAndLabName(lab.getUserId(),lab.getName());
                clinicInfo = clinicInfoRepository.findOne(lab.getClinicId());
                clinicInfo.setLab(lab1);
                clinicInfoSet.add(clinicInfo);
                lab1.setClinic(clinicInfoSet);
                labServiceProviderRepository.save(lab1);
                status = HttpStatus.OK;

                UserContactInfo contactInfo = lab1.getUser().getUserContactInfo();
                User user = CommonServices.getOnlyUser(lab1.getUser());
                user.setUserContactInfo(CommonServices.getOnlyUserContactInfo(contactInfo));
                lab1.setClinic(null);
                lab1.setUser(user);
                response.put("lab", lab1);
                message = utilityServices.getMessage("Clinic.saveProfile.Message");
            }else{
                status = HttpStatus.CONTINUE;
                message = utilityServices.getMessage("Lab.Exits.Message");
            }

        }catch (Exception e){
            e.printStackTrace();
            logger.error(String.valueOf(e));
        }
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        logger.info("End of path /addLabToClinic : "+response);
        return response;
    }


    @RequestMapping(value = "/addPharmacyToClinic", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> addPharmacyToClinic(@RequestBody Pharmacy pharmacy, BindingResult result, ModelMap modelMap){

        logger.info("Requested for path /addPharmacyToClinic : "+pharmacy);

        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";

        try{
            Set<ClinicInfo> clinicInfoSet = new HashSet<>();
            ClinicInfo clinicInfo = clinicInfoRepository.getPharmacyByClinicId(pharmacy.getClinicId());
            if(clinicInfo == null || clinicInfo.getPharmacy() == null || !(clinicInfo.getPharmacy().getUserId()).equals(pharmacy.getUserId())){
                Pharmacy pharmacy1 = pharmacyServiceProviderRepository.fetchPharmacyByUserIdAndLabName(pharmacy.getUserId(),pharmacy.getName());
                clinicInfo = clinicInfoRepository.findOne(pharmacy.getClinicId());
                clinicInfo.setPharmacy(pharmacy1);
                clinicInfoSet.add(clinicInfo);
                pharmacy1.setClinic(clinicInfoSet);
                pharmacyServiceProviderRepository.save(pharmacy1);
                status = HttpStatus.OK;

                UserContactInfo contactInfo = pharmacy1.getUser().getUserContactInfo();
                User user = CommonServices.getOnlyUser(pharmacy1.getUser());
                user.setUserContactInfo(CommonServices.getOnlyUserContactInfo(contactInfo));
                pharmacy1.setClinic(null);
                pharmacy1.setUser(user);
                response.put("pharmacy", pharmacy1);
                message = utilityServices.getMessage("Clinic.saveProfile.Message");
            }else{
                status = HttpStatus.CONTINUE;
                message = utilityServices.getMessage("Pharmacy.Exits.Message");
            }

        }catch (Exception e){
            e.printStackTrace();
            logger.error(String.valueOf(e));
        }

        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        logger.info("End of path /addPharmacyToClinic : "+response);
        return response;
    }
}