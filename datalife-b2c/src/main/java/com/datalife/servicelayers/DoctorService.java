package com.datalife.servicelayers;


import com.datalife.controller.EmailController;
import com.datalife.entities.*;
import com.datalife.repositories.*;

import com.datalife.services.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.datalife.entities.Encounter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.*;

/**
 * Controller class that handles the doctor related requests
 * <p/>
 * Created by supriya gondi on 12/9/2014.
 */
@RestController
@RequestMapping(value = "/api/user/doctor/")
public class DoctorService {
    /**
     * reference to userRepository
     */
    @Autowired
    UserRepository userRepository;

    /**
     * reference to authenticationRepository
     */
    @Autowired
    AuthenticationRepository authenticationRepository;
    /**
     * reference to userDetailsRepository
     */
    @Autowired
    UserDetailsRepository userDetailsRepository;
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
    @Autowired
    LabTestRepository labTestRepository;

    @Autowired
    UploadFileRepository uploadFileRepository;
    /**
     * reference to userContactRepository
     */
    @Autowired
    UserContactRepository userContactRepository;
    /**
     * reference to doctorInfoRepository
     */
    @Autowired
    DoctorInfoRepository doctorInfoRepository;

    /**
     * reference to commonService
     */
    @Autowired
    CommonService commonService;


    /**
     * reference to historyRepository
     */
    @Autowired
    HistoryRepository historyRepository;
    /**
     * reference to commonRepository
     */
    @Autowired
    CommonRepository commonRepository;
    /**
     * reference to clinicDoctorsRepository
     */
    @Autowired
    ClinicDoctorsRepository clinicDoctorsRepository;

    @Autowired
    ClinicPatientRepository clinicPatientRepository;

    @Autowired
    ClinicInfoRepository clinicInfoRepository;

    @Autowired
    AlfrescoAuthDetails alfrescoAuthDetails;

    @Autowired
    PDFGenerationService pdfGenerationService;

    @Autowired
    UtilityServices utilityServices;

    @Autowired
    EmailController emailController;

    @Autowired
    MessageRepository messageRepository;
    /**
     * Direcory to store uploaded files
     */
    @Value("D:\\images\\")
    private String uploadDirectory;

    private static final Logger logger = Logger.getLogger(HomeService.class);

    /**
     * This method is get Patient By userId as search Results
     *
     * @param user
     * @param modelMap
     * @return Map<String,Object> signupDetails includes status,statusCode,message
     */
    @RequestMapping(value = "getPatientById", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> getPatientById(@RequestBody User user, BindingResult result, ModelMap modelMap) {
        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = utilityServices.getMessage("Patient.notFound.MESSAGE");

        UserDetails userDetails;
        UserContactInfo userContactInfo;

            Long patientId = user.getPatientId();
            List<User> searchResults = new ArrayList<User>();
            List<User> users = userRepository.searchByUserId(patientId);
            if (users != null && !users.isEmpty()) {
                for (User currentUser : users) {
                    Long id = currentUser.getUserId();
                    User newUser = CommonServices.getOnlyUser(currentUser);
                    userDetails = userDetailsRepository.findByUserId(id);
                    newUser.setUserDetails(CommonServices.getOnlyUserDetails(userDetails));
                    userContactInfo = userContactRepository.findByUserId(id);
                    newUser.setUserContactInfo(CommonServices.getOnlyUserContactInfo(userContactInfo));
                    newUser.setPatientId(newUser.getUserId());
                    searchResults.add(newUser);
                    response.put("users", searchResults);
                }

                response.put("doctorId", user.getDoctorId());
                response.put("clinicId", user.getClinicId());
                status = HttpStatus.OK;
                message = "";
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
    @RequestMapping(value = "getPatientByUserName", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> getPatientByUserName(@RequestBody User user, BindingResult result, ModelMap modelMap) {
        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = utilityServices.getMessage("Patient.notFound.MESSAGE");
        UserDetails userDetails;
        UserContactInfo userContactInfo;

            String userName = user.getUserName();
            List<User> searchResults = new ArrayList<User>();
            List<User> users = userRepository.searchByUserName(userName);
            if (users != null && !users.isEmpty()) {
                for (User currentUser : users) {
                    Long id = currentUser.getUserId();
                    User newUser = CommonServices.getOnlyUser(currentUser);
                    userDetails = userDetailsRepository.findByUserId(id);
                    newUser.setUserDetails(CommonServices.getOnlyUserDetails(userDetails));
                    userContactInfo = userContactRepository.findByUserId(id);
                    newUser.setUserContactInfo(CommonServices.getOnlyUserContactInfo(userContactInfo));
                    newUser.setPatientId(newUser.getUserId());
                    searchResults.add(newUser);
                    response.put("users", searchResults);
                }

                response.put("doctorId", user.getDoctorId());
                response.put("clinicId", user.getClinicId());
                status = HttpStatus.OK;
                message = "";
            }
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    /**
     * This method is get Patients By multiple fields as search Results
     *
     * @param user
     * @param result
     * @param modelMap
     * @return Map<String, Object> signupDetails includes status,statusCode,message
     */
    @RequestMapping(value = "/searchPatient", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> searchPatient(@RequestBody User user, BindingResult result, ModelMap modelMap) {
        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "Patient does not exist in the database";

        UserDetails userDetails;
        UserContactInfo userContactInfo;

        List<User> searchResults = new ArrayList<User>();
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
                    newUser.setPatientId(id);
                    searchResults.add(newUser);
                    response.put("users", searchResults);
                }
                response.put("patientId", user.getUserId());
                response.put("doctorId", user.getDoctorId());
                status = HttpStatus.OK;
                message = "";
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
    @RequestMapping(value = "authenticate", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> getAuthenticationFromPatient(@RequestBody User user, BindingResult result, ModelMap modelMap) {
        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";
            Long patientId = user.getPatientId();
            User u = userRepository.findOne(patientId);
            if (u != null) {
                Random randomGenerator = new Random();
                Integer randomInt = randomGenerator.nextInt(100000);

                String messageBody = "Mr/ Ms. " + u.getUserDetails().getFirstName() + " " + u.getUserDetails().getLastName() + ", OTP to give access for consultation is " + randomInt + ". DataLife";

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
            } else {
                status = HttpStatus.NOT_ACCEPTABLE;
                message = "Patient does not exist in the Database";
            }
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    /**
     * This method is for User Authentication to add user to Favourites
     *
     * @param user
     * @param result
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "authenticateUser", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> getAuthenticationFromUser(@RequestBody User user, BindingResult result, ModelMap modelMap) {
        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";

            Long patientId = user.getPatientId();
            Long doctorId = user.getDoctorId();
            Long clinicId = user.getClinicId();
            if (patientId != null && doctorId != null && clinicId != null) {
                ClinicPatients clinicPatient = clinicPatientRepository.findByPatientId(patientId, clinicId, doctorId);
                if (clinicPatient != null) {
                    status = HttpStatus.FOUND;
                    message = "Patient already added in favourites";
                } else {
                    User u = userRepository.findOne(patientId);
                    if (u != null) {
                        Random randomGenerator = new Random();
                        Integer randomInt = randomGenerator.nextInt(100000);
                        Authentication auth = new Authentication(randomInt.toString(), u);
                        if (u.getAuthentication() != null) {
                            auth.setAuthId(u.getAuthentication().getAuthId());
                        }
                        authenticationRepository.save(auth);
                        status = HttpStatus.OK;
                        response.put("userId", u.getUserId());
                        response.put("role", u.getRole());
                        response.put("mobilePhone", u.getUserContactInfo().getMobilePhone());
                    } else {
                        status = HttpStatus.NOT_ACCEPTABLE;
                        message = "Patient does not exist in the Database";
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
    @RequestMapping(value = "/accessPatient", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> accessPatient(@RequestBody User user, BindingResult result, ModelMap modelMap) {
        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";
            Long patientId = user.getUserId();
            User u = userRepository.findOne(patientId);
            if (u != null) {
                String otp = authenticationRepository.findByUserId(patientId);
                if (user.getAuthentication().getOtp().equals(otp)) {
                    status = HttpStatus.OK;
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


    @RequestMapping(value = "/accessClinicPatient", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> accessClinicPatient(@RequestBody User user, BindingResult result, ModelMap modelMap) {
        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";
        String ticket = "";
            Long patientId = user.getPatientId();
            User u = userRepository.findOne(patientId);
            if (u != null) {

                User doctor = userRepository.findOne(user.getDoctorId());
                Long doctorId = doctor.getUserId();
                UserDetails ud = userDetailsRepository.findByUserId(doctorId);
                UserContactInfo uci = userContactRepository.findByUserId(doctorId);
                UserDetails pud = userDetailsRepository.findByUserId(patientId);
                response.put("doctorId", doctor.getUserId());
                response.put("patientName", pud.getFirstName() + " " + pud.getLastName());
                response.put("patientId", patientId);
                if (user.getClinicId() != null) {
                    ClinicInfo clinicInfo = clinicInfoRepository.findByUserId(user.getClinicId());
                    response.put("clinicId", user.getClinicId());
                    response.put("clinicName", clinicInfo.getClinicName());
                }
                response.put("userName", doctor.getUserName());

                ticket = alfrescoAuthDetails.getAlfrescoConnectionTicket(u.getUserName(), u.getPassword());
                if (!ticket.equals("403") && u.getUserParentDir() != null) {
                    String nodeRef = u.getUserParentDir();
                    String[] node = nodeRef.split("/");
                    alfrescoAuthDetails.grantPermission(ticket, doctor.getUserName(), node[3], "Collaborator");
                 /*   MyUserDetailsService.getUserFromSession().setPatientId(patientId);*/
                    status = HttpStatus.OK;
                } else {
                    message = "User activation required to access !!!";
                }
                status = HttpStatus.OK;

            }
        alfrescoAuthDetails.logoutTicket(ticket);
        Date date = (Date) commonRepository.getDate();
        response.put("dbDate", date);
        response.put("imageUrl", utilityServices.getMessage("User.profileImage.Url"));
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    /**
     * This method is get Encounter for particular Patient
     *
     * @param user
     * @param modelMap
     * @return Map<String, Object> signup Details includes status,statusCode,message
     */
    @RequestMapping(value = "/getEncounter", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> getEncounter(@RequestBody User user, BindingResult result, ModelMap modelMap) {
        Map<String, Object> response = new HashMap<>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";

            Long doctorId = user.getDoctorId();
            Long clinicId = user.getClinicId();
            Long patientId = user.getPatientId();
            User patient = userRepository.findOne(patientId);
            if (patient.getAlfrescoUrl() != null && patient.getUserParentDir()!= null) {
            if (doctorId != null) {
                String imageUrl = patient.getImageFileName();
                if (imageUrl != null && !imageUrl.isEmpty()) {
                    response.put("imageUrl", utilityServices.getMessage("User.profileImage.Url") + patientId);
                } else {
                    response.put("imageUrl", utilityServices.getMessage("Doctor.DefaultImage.Url"));
                }

                response.put("date", DateService.getTodayDate());
                response.put("patientId", patientId);
                UserDetails ud = patient.getUserDetails();
                response.put("patientName", ud.getFirstName() + " " + ud.getLastName());
                response.put("gender", ud.getGender());
                response.put("dob", ud.getDateofBirth());
                response.put("doctorId", doctorId);
                response.put("clinicId", clinicId);

                History history = historyRepository.findByUserId(patientId);
                if (history != null) {
                    response.put("medical", historyRepository.getHistoryData(history.getMedicalHistory()));
                    response.put("surgical", historyRepository.getHistoryData(history.getSurgicalHistory()));
                    response.put("family", historyRepository.getHistoryData(history.getFamilyHistory()));
                    response.put("social", historyRepository.getHistoryData(history.getSocialHistory()));
                    response.put("rf", historyRepository.getHistoryData(history.getRiskFactors()));
                    response.put("medications", historyRepository.getHistoryData(history.getPastMedications()));
                    response.put("allergies", historyRepository.getHistoryData(history.getAllergies()));
                } else {
                    response.put("history", "NOT_FOUND");
                }
                List<ROSCategory> rosCategoriesList = commonRepository.getRosCategories();
                response.put("rosCategories", rosCategoriesList);

                List<PETypes> peTypesList = commonRepository.getPECategories();
                response.put("peCategories", peTypesList);

                response.put("labCategories", commonRepository.getLabCategories());
                response.put("hmtValues", labTestRepository.getLabCategoriesById(1L));
                response.put("sgyValues", labTestRepository.getLabCategoriesById(2L));
                response.put("cpgyValues", labTestRepository.getLabCategoriesById(3L));
                response.put("cgyValues", labTestRepository.getLabCategoriesById(4L));
                response.put("bcyValues", labTestRepository.getLabCategoriesById(5L));
                response.put("mbgyValues", labTestRepository.getLabCategoriesById(6L));
                response.put("hrtValues", labTestRepository.getLabCategoriesById(7L));

                if (clinicId != null) {
                    Long specialityId = clinicDoctorsRepository.getSpecialityByIds(clinicId, doctorId);
                    String names = userDetailsRepository.getFullName(patientId);
                    String[] values = names.split(",");
                    response.put("patientName", values[0] + " " + values[1]);
                    if (specialityId != null) {
                        if (specialityId == 10) {
                            response.put("cardioLabels", commonRepository.getCardiovascularLabelList());
                            response.put("respiratoryLabels", commonRepository.getRespiratoryLabelList());
                            response.put("specialist", "Cardiology");
                        }
                        if (specialityId == 26) {
                            response.put("gastrointestinalLabels", commonRepository.getGastrointestinalLabelList());
             /*       response.put("genitourinaryFemLabels", commonRepository.getGenitourinaryFemLabelsList());
                    response.put("genitourinaryMaleLabels", commonRepository.getGenitourinaryMaleLabelsList());*/
                            response.put("specialist", "Gastroenterology");
                        }
                        if (specialityId == 47) {
                            response.put("muscoloskeletalLabels", commonRepository.getMuscoloskeletalLabelList());
                            response.put("specialist", "Orthopedic");
                        }
                        response.put("specialityId", specialityId);
                    } else {
                        response.put("specialityId", 0L);
                        response.put("specialist", "");
                    }
                } else {
                    response.put("specialityId", 0L);
                }
                status = HttpStatus.OK;
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                calendar.add(Calendar.DATE, 180);
                Date date = calendar.getTime();
                response.put("maxDateToShow", DateService.dateToStringConversion(date));
            }
            }else{
                status = HttpStatus.CONTINUE;
                message = utilityServices.getMessage("Patient.validate.Consultation");
            }
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;

    }

    @RequestMapping(value = "/getConsultation", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> getConsultation(@RequestBody Long userId, BindingResult result, ModelMap modelMap) {
        Map<String, Object> response = new HashMap<>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";
        if (userId != null) {
            User patient = userRepository.findOne(userId);
            if (patient != null) {
                response.put("date", DateService.getTodayDate());
                response.put("dob", patient.getUserDetails().getDateofBirth());
                History history = historyRepository.findByUserId(userId);
                if (history != null) {
                    response.put("medical", historyRepository.getHistoryData(history.getMedicalHistory()));
                    response.put("surgical", historyRepository.getHistoryData(history.getSurgicalHistory()));
                    response.put("family", historyRepository.getHistoryData(history.getFamilyHistory()));
                    response.put("social", historyRepository.getHistoryData(history.getSocialHistory()));
                    response.put("rf", historyRepository.getHistoryData(history.getRiskFactors()));
                    response.put("medications", historyRepository.getHistoryData(history.getPastMedications()));
                    response.put("allergies", historyRepository.getHistoryData(history.getAllergies()));
                } else {
                    response.put("history", "NOT_FOUND");
                }
                List<ROSCategory> rosCategoriesList = commonRepository.getRosCategories();
                response.put("rosCategories", rosCategoriesList);

                List<PETypes> peTypesList = commonRepository.getPECategories();
                response.put("peCategories", peTypesList);

                response.put("labCategories", commonRepository.getLabCategories());
                response.put("hmtValues", labTestRepository.getLabCategoriesById(1L));
                response.put("sgyValues", labTestRepository.getLabCategoriesById(2L));
                response.put("cpgyValues", labTestRepository.getLabCategoriesById(3L));
                response.put("cgyValues", labTestRepository.getLabCategoriesById(4L));
                response.put("bcyValues", labTestRepository.getLabCategoriesById(5L));
                response.put("mbgyValues", labTestRepository.getLabCategoriesById(6L));
                response.put("hrtValues", labTestRepository.getLabCategoriesById(7L));

                    response.put("specialityId", 0L);

                status = HttpStatus.OK;
            }
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, 180);
        Date date = calendar.getTime();
        response.put("maxDateToShow", DateService.dateToStringConversion(date));

        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;

    }


    @RequestMapping(value = "/getCardiology", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> getCardiology(@RequestBody String speciality, BindingResult result, ModelMap modelMap) {
        Map<String, Object> response = new HashMap<>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";
        if (speciality != null && !speciality.isEmpty()) {
            switch ((speciality)) {
                case "1":
                    response.put("cardioLabels", commonRepository.getCardiovascularLabelList());
                    response.put("respiratoryLabels", commonRepository.getRespiratoryLabelList());
                    response.put("dataStatus", "101");
                    break;
                case "2":
                    response.put("muscoloskeletalLabels", commonRepository.getMuscoloskeletalLabelList());
                    response.put("dataStatus", "201");
                    break;
                case "3":
                    response.put("gastrointestinalLabels", commonRepository.getGastrointestinalLabelList());
                    response.put("dataStatus", "301");
                    break;
            }
            status = HttpStatus.OK;
        }
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;

    }

    /**
     * The purpose of this controller is for saving Encounter for specific User of ROLE Patinet
     *
     * @param encounter
     * @param result
     * @param modelMap
     * @return Map<String,Object> signupDetails includes status,statusCode,message
     */
    @RequestMapping(value = "saveEncounter", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> saveEncounter(@RequestBody Encounter encounter,HttpServletRequest request, BindingResult result, ModelMap  modelMap) {

        logger.debug("Requesting for path /saveEncounter");

        Map<String, Object> response = new HashMap<>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";
        if (result.hasErrors()) {
            message = "User.invalid";
        }
        if (encounter != null) {
            Encounter enc = encounterRepository.saveEncounter(encounter);
            if (enc.getEncounterId() != 0L) {
                request.getSession().setAttribute(enc.getEncounterId().toString(),enc);
                response.put("encId", enc.getEncounterId());
                status = HttpStatus.OK;
                message = "Saved successfully !!!";
            } else {
                status = HttpStatus.NOT_ACCEPTABLE;
                message = "Not saved, Retry !!";
            }
        }

        response.put("statusCode", status.value());
        response.put("status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }


    @RequestMapping(value = "generatePdfByEncId", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> generatePdfByEncId(@RequestBody Encounter encounter,HttpServletRequest request, BindingResult result, ModelMap  modelMap) {

        logger.info("Requesting for path /generatePdfByEncId" +encounter.getEncounterId());

        Map<String, Object> response = new HashMap<>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";
        User doctor = null;
        encounter = (Encounter) request.getSession().getAttribute(encounter.getEncounterId().toString());
        if(encounter.getDoctorId() != null && encounter.getClinicId() != null){
            doctor = userRepository.findOne(encounter.getDoctorId());
            doctor.setClinicId(encounter.getClinicId());
        }
        User patient = userRepository.findOne(encounter.getUser().getUserId());
        String ticket = alfrescoAuthDetails.getAlfrescoConnectionTicket(patient.getUserName(), patient.getPassword());

        //get patient-user info from alfresco
        String statusCode = alfrescoAuthDetails.getAlfrescoUserDetails(patient.getAlfrescoUrl(), ticket);
        logger.debug("Subscriber id:"+patient.getUserId()+" avialable space is "+ statusCode);

        //check the patient-user has enough space to store the report
        if (statusCode != null) {

            String[] q = statusCode.split(",");
            int quota = Integer.parseInt(q[0]);
            int cz = Integer.parseInt(q[1]);
            if (quota > cz) {
                encounterRepository.generatePdfAndUploadToPatient(encounter, doctor, patient);
                logger.info("Subscriber with out pdf generation,after saving the encounter: encounterId :"+encounter.getEncounterId());
            }else{
                logger.info("Subscriber with out pdf generation,before saving the encounter");
                encounterRepository.save(encounter);
                logger.info("Subscriber with out pdf generation,after saving the encounter: encounterId :"+encounter.getEncounterId());
            }
        }
        request.getSession().removeAttribute(encounter.getEncounterId().toString());
        logger.info("End of path /generatePdfByEncId");
        response.put("statusCode", status.value());
        response.put("status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    @RequestMapping(value = "saveConsultation", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> saveConsultation(@RequestBody Encounter encounter, BindingResult result, ModelMap modelMap) {
        Map<String, Object> response = new HashMap<>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";

        if (result.hasErrors()) {
            message = "User.invalid";
        }
        if (encounter != null) {
            boolean verdict = encounterRepository.saveConsultation(encounter);
            if (verdict) {
                status = HttpStatus.OK;
                message = "Saved successfully !!!";
            } else {
                status = HttpStatus.NOT_ACCEPTABLE;
                message = "Not saved, Retry !!";
            }
        }

        response.put("statusCode", status.value());
        response.put("status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }


    @RequestMapping(value = "previewEncounter", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> previewEncounter(@RequestBody Encounter encounter, BindingResult result, ModelMap modelMap) {
        Map<String, Object> response = new HashMap<>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";
        if (result.hasErrors()) {
            message = "User.invalid";
        }
       /* Encounter enc = new Encounter();*/
        if (encounter != null) {

            Long patientId = encounter.getPatientId();
            User patient = userRepository.findOne(patientId);
            Date date = new Date();
            encounter.setDate(date);
            encounter.setUser(patient);

            User doctor = null;
            if(patient != null){
                Long doctorId = encounter.getDoctorId();
                Long clinicId = encounter.getClinicId();
                if(doctorId != null && clinicId != null){
                   doctor = userRepository.findOne(doctorId);
                }

               /* enc.setDate(date);
                enc.setUser(patient);
                enc.setAge(encounter.getAge());
                enc.setDoctorId(doctorId);
                enc.setClinicId(clinicId);*/

                /*MiniEncounter miniEncounter = encounter.getMiniEncounter();
                if (miniEncounter != null) {
                    enc.setMiniEncounter(miniEncounter);
                }*/
                Vitals vitals = encounter.getVitals();
                if (vitals != null) {
                    vitals.setDate(date);
                /* enc.setVitals(vitals);*/
                }
                List<Prescription> prescriptions = encounter.getPrescriptions();
                if (prescriptions != null && !prescriptions.isEmpty()) {
                    encounter.setPrescriptions(encounterRepository.getPrescriptions(prescriptions, encounter));
                }

                List<PhysicalExamination> physicalExaminations = encounter.getPhysicalExaminations();
                if (physicalExaminations != null && !physicalExaminations.isEmpty()) {
                    encounter.setPhysicalExaminations(encounterRepository.getPe(physicalExaminations, encounter));
                }

                List<ReviewofSystems> reviewofSystemses = encounter.getReviewofSystems();
                if (reviewofSystemses != null && !reviewofSystemses.isEmpty()) {
                    encounter.setReviewofSystems(encounterRepository.getRos(reviewofSystemses, encounter));
                }
                List<LabOrder> labOrders = encounter.getLabOrders();
                if (labOrders != null && !labOrders.isEmpty()) {
                    encounter.setLabOrders(encounterRepository.getLabOrders(labOrders, encounter));
                }
                History history = historyRepository.findByUserId(patientId);
                encounter.setHistory(history);
                String storageDirectory = utilityServices.getMessage("Encounter.share.preview");
                File newFile = null;
                String summaryFilePath = "";
                InputStream stream;

                    stream = this.getClass().getClassLoader().getResourceAsStream("summaryReport.html");
                    //generating file name for pdf
                    Random randomGenerator = new Random();
                    Integer randomInt = randomGenerator.nextInt(100000);
                    String fileName = "preview" + randomInt + ".pdf";

                    summaryFilePath = storageDirectory + fileName;
                    newFile = new File(summaryFilePath);

                    //generate the pdf and store in local path (newFile)
                    if (clinicId != null) {
                        User clinic = userRepository.searchById(clinicId);
                        pdfGenerationService.pdfGenerator(newFile, stream, encounter,doctor, clinic);
                    } else {
                        pdfGenerationService.pdfGenerator(newFile, stream, encounter,doctor, null);
                    }
                    response.put("pdfUrl", utilityServices.getMessage("Application.Url")+"/api/user/doctor/picture/" + randomInt);

                status = HttpStatus.OK;
                message = "Saved successfully !!!";

            }
        }

        response.put("statusCode", status.value());
        response.put("status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }


    @RequestMapping(value = "previewConsultation", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> previewConsultation(@RequestBody Encounter encounter, BindingResult result, ModelMap modelMap) {
        Map<String, Object> response = new HashMap<>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";
        if (result.hasErrors()) {
            message = "User.invalid";
        }

        Encounter enc = new Encounter();
        if (encounter != null) {
            Long patientId = encounter.getPatientId();
            User patient = userRepository.findOne(patientId);
            Date date = new Date();
            if (patient != null) {
                enc.setDate(date);
                enc.setUser(patient);
                enc.setDoctorName(encounter.getDoctorName());
                enc.setClinicName(encounter.getClinicName());
                enc.setMobileNumber(encounter.getMobileNumber());
                enc.setMciNumber(encounter.getMciNumber());
                enc.setAge(encounter.getAge());

                MiniEncounter miniEncounter = encounter.getMiniEncounter();
                if (miniEncounter != null) {
                    enc.setMiniEncounter(miniEncounter);
                }
                Vitals vitals = encounter.getVitals();
                if (vitals != null) {
                    vitals.setDate(date);
                    enc.setVitals(vitals);
                }
                List<Prescription> prescriptions = encounter.getPrescriptions();
                if (prescriptions != null && !prescriptions.isEmpty()) {
                    enc.setPrescriptions(encounterRepository.getPrescriptions(prescriptions, enc));
                }
                List<PhysicalExamination> physicalExaminations = encounter.getPhysicalExaminations();
                if (physicalExaminations != null && !physicalExaminations.isEmpty()) {
                    enc.setPhysicalExaminations(encounterRepository.getPe(physicalExaminations, enc));
                }
                List<ReviewofSystems> reviewofSystemses = encounter.getReviewofSystems();
                if (reviewofSystemses != null && !reviewofSystemses.isEmpty()) {
                    enc.setReviewofSystems(encounterRepository.getRos(reviewofSystemses, enc));
                }
                List<LabOrder> labOrders = encounter.getLabOrders();
                if (labOrders != null && !labOrders.isEmpty()) {
                    enc.setLabOrders(encounterRepository.getLabOrders(labOrders, enc));
                }
                History history = historyRepository.findByUserId(patientId);
                enc.setHistory(history);
                String storageDirectory = utilityServices.getMessage("Encounter.share.preview");
                File newFile = null;
                String summaryFilePath = "";
                InputStream stream;

                stream = this.getClass().getClassLoader().getResourceAsStream("summaryReport.html");
                //generating file name for pdf
                Random randomGenerator = new Random();
                Integer randomInt = randomGenerator.nextInt(100000);
                String fileName = "preview" + randomInt + ".pdf";

                summaryFilePath = storageDirectory + fileName;
                newFile = new File(summaryFilePath);

                boolean s = pdfGenerationService.pdfGenerator(newFile, stream, enc,null, null);
                if(s){
                    response.put("pdfUrl", utilityServices.getMessage("Application.Url")+"/api/user/doctor/picture/" + randomInt);
                    status = HttpStatus.OK;
                    message = "Saved successfully !!!";
                }

            }
        }

        response.put("statusCode", status.value());
        response.put("status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    @RequestMapping(value = "picture/{randomInt}", method = RequestMethod.GET)
    public void picture(@PathVariable String randomInt,HttpServletResponse response) {
        String storageDirectory = utilityServices.getMessage("Encounter.share.preview");
        File imageFile = new File(storageDirectory + "preview" + randomInt + ".pdf");
        InputStream is = null;
        try {
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache, must-revalidate");
            response.setDateHeader("Expires", 0);
            response.setContentType("application/pdf");
            is = new FileInputStream(imageFile);
            IOUtils.copy(is, response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {

            if (is != null) {
                try {
                    is.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * This method is get Encounter for particular Patient
     *
     * @param user
     * @param modelMap
     * @return Map<String, Object> signup Details includes status,statusCode,message
     */
    @RequestMapping(value = "/getQuickEncounter", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> getQuickEncounter(@RequestBody User user, BindingResult result, ModelMap modelMap) {
        Map<String, Object> response = new HashMap<>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";
        if (user != null) {
            Long doctorId = user.getDoctorId();
            Long clinicId = user.getClinicId();
            Long patientId = user.getPatientId();
            if (patientId != null && doctorId != null) {

                response.put("date", DateService.getTodayDate());
                response.put("patientId", patientId);
                UserDetails ud = userDetailsRepository.findByUserId(patientId);
                response.put("patientName", ud.getFirstName() + " " + ud.getLastName());
                response.put("doctorId", doctorId);
                if (clinicId != null) {
                    response.put("clinicId", clinicId);
                }

                response.put("labCategories", commonRepository.getLabCategories());
                response.put("hmtValues", labTestRepository.getLabCategoriesById(1L));
                response.put("sgyValues", labTestRepository.getLabCategoriesById(2L));
                response.put("cpgyValues", labTestRepository.getLabCategoriesById(3L));
                response.put("cgyValues", labTestRepository.getLabCategoriesById(4L));
                response.put("bcyValues", labTestRepository.getLabCategoriesById(5L));
                response.put("mbgyValues", labTestRepository.getLabCategoriesById(6L));
                response.put("hrtValues", labTestRepository.getLabCategoriesById(7L));

                status = HttpStatus.OK;
            }

        }
        Date date = (Date) commonRepository.getDate();
        response.put("dbDate", date);
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;

    }


    @RequestMapping(value = "basicInfo", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> getBasicInformation(@RequestBody User user, BindingResult result, ModelMap modelMap) {

        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";

        Long userId = user.getUserId();
        User u = userRepository.findOne(userId);

        if (u != null) {
            UserDetails userDetails = userDetailsRepository.findByUserId(userId);
            if (userDetails != null) {
                response.put("ud", CommonServices.getOnlyUserDetails(userDetails));
            }
            response.put("user", CommonServices.getOnlyUser(u));
            response.put("bloodGroups", commonService.getBloodGroups());

            status = HttpStatus.OK;
        }


        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    @RequestMapping(value = "contactInfo", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> getContactInformation(@RequestBody User user, BindingResult result, ModelMap modelMap) {

        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";

        Long userId = user.getUserId();
        User u = userRepository.findOne(userId);

        if (u != null) {


            UserContactInfo userContactInfo = userContactRepository.findByUserId(userId);
            if (userContactInfo != null) {
                response.put("contInfo", CommonServices.getOnlyUserContactInfo(userContactInfo));
            }
            response.put("userId", userId);
            status = HttpStatus.OK;
        }


        response.put("countries", commonService.getCountries());
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    @RequestMapping(value = "professionalInfo", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> getProfessionalInformation(@RequestBody User user, BindingResult result, ModelMap modelMap) {

        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";

        Long userId = user.getUserId();
        User u = userRepository.findOne(userId);

        if (u != null) {

            DoctorInfo doctorInfo = doctorInfoRepository.findByUserId(userId);
            if (doctorInfo != null) {
                response.put("doctorInfo", CommonServices.getOnlyDoctorInfo(doctorInfo));
            }
            response.put("userId", userId);
            status = HttpStatus.OK;
        }


        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    /**
     * The purpose of this controller is for saving Basic Information.
     *
     * @param user
     * @param result
     * @param modelMap
     * @return Map<String, Object> saveBasicInformation includes status,statusCode,message
     */
    @RequestMapping(value = "saveBasicInfo", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> saveBasicInfo(@RequestBody User user, BindingResult result, ModelMap modelMap) {

        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";
        Long userId = user.getUserId();
        User sessionUser = userRepository.findOne(userId);

        if (sessionUser != null) {
            UserDetails newUserDetails = user.getUserDetails();
            UserDetails userDetails = userDetailsRepository.findByUserId(userId);
            if (userDetails != null && newUserDetails != null) {
                newUserDetails.setUserDetailsId(userDetails.getUserDetailsId());
            }

            newUserDetails.setUser(sessionUser);
            userDetailsRepository.save(newUserDetails);
            sessionUser.setUserDetails(newUserDetails);

            response.put("ud", CommonServices.getOnlyUserDetails(newUserDetails));

            response.put("user", CommonServices.getOnlyUser(sessionUser));
            response.put("bloodGroups", commonService.getBloodGroups());


            status = HttpStatus.OK;
            message = "Saved Successfully.";
        }
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    /**
     * The purpose of this controller is save Contact Information of Doctor
     *
     * @param userContactInfo
     * @param result
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "saveContactInfo", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> saveContactInfo(@RequestBody UserContactInfo userContactInfo, BindingResult result, ModelMap modelMap) {

        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";

        if (userContactInfo != null) {
            Long userId = userContactInfo.getUser().getUserId();
            User sessionUser = userRepository.findOne(userId);
            UserContactInfo contactInfo = userContactRepository.findByUserId(userId);
            if (contactInfo != null) {
                userContactInfo.setUserContactInfoId(contactInfo.getUserContactInfoId());
            }
            try{
                userContactInfo.setUser(sessionUser);
                userContactRepository.save(userContactInfo);
                sessionUser.setUserContactInfo(userContactInfo);
                userRepository.save(sessionUser);
                response.put("contInfo", CommonServices.getOnlyUserContactInfo(userContactInfo));
                response.put("countries", commonService.getCountries());
                response.put("userId", userId);
                status = HttpStatus.OK;
                message = "Saved Successfully.";
            }catch (Exception exception){
                exception.printStackTrace();
            }
        }

        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    /**
     * The purpose of this controller is to save Doctor Professional Information
     *
     * @param doctorInfo
     * @param result
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "professionalInfo/save", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> saveDoctorInfo(@RequestBody DoctorInfo doctorInfo, BindingResult result, ModelMap modelMap) {

        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";
        Long userId = doctorInfo.getUser().getUserId();
        User sessionUser = userRepository.findOne(userId);
            DoctorInfo info = doctorInfoRepository.findByUserId(userId);
            if (info != null) {
                doctorInfo.setDoctorInfoId(info.getDoctorInfoId());
            }
            try{
                doctorInfo.setUser(sessionUser);
                doctorInfoRepository.save(doctorInfo);
                sessionUser.setDoctorInfo(doctorInfo);
                userRepository.save(sessionUser);
                response.put("doctorInfo", CommonServices.getOnlyDoctorInfo(doctorInfo));
                response.put("userId", userId);
                status = HttpStatus.OK;
                message = "Saved Successfully.";
            }catch (Exception exception){
                exception.printStackTrace();
            }


        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    /**
     * The purpose of this controller is to go back doctor dashboard from encounter page
     *
     * @param user
     * @param result
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/backtoHome", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> backToHome(@RequestBody User user, BindingResult result, ModelMap modelMap) {
        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";

            Long userId = user.getDoctorId();
            User u = userRepository.findOne(userId);
            if (u != null) {
                response.put("userName", u.getUserName());
                response.put("id", u.getUserId());
                response.put("imageUrl", utilityServices.getMessage("User.profileImage.Url"));
                status = HttpStatus.OK;
            }

        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    @RequestMapping(value = "/backtoClinic", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> backToClinic(@RequestBody User user, BindingResult result, ModelMap modelMap) {
        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";
            /*Integer userId = user.getDoctorId().intValue();
            User doctor = userRepository.findOne(userId);*/
            /*int pid = (int) user.getUid().intValue();
            User patient = userRepository.findOne((int) user.getUid().intValue());*/
           /* if (doctor != null) {*/
            if (MyUserDetailsService.getUserFromSession().getUserId().equals(user.getDoctorId())) {
                User doctor = MyUserDetailsService.getUserFromSession();

                response.put("userName", doctor.getUserName());
                response.put("id", doctor.getUserId());
               /* UserContactInfo contactInfo = userContactRepository.findByUserId(userId);*/
                response.put("mobile", doctor.getUserContactInfo().getMobilePhone());
                response.put("email", doctor.getUserContactInfo().getEmail());
                response.put("clinicId", user.getClinicId().intValue());
                String ticket = MyUserDetailsService.getUserFromSession().getAlfresAuthTicket();
                if (!alfrescoAuthDetails.isTicketValid(ticket)) {
                    alfrescoAuthDetails.logoutTicket(ticket);
                }
                MyUserDetailsService.getUserFromSession().setAlfresAuthTicket(null);
                MyUserDetailsService.getUserFromSession().setPatientId(null);

                status = HttpStatus.OK;
            }
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    /**
     * The purpose of this controller is to add patient to favourites
     *
     * @param user
     * @param result
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/addPatient", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> addPatientToList(@RequestBody User user, BindingResult result, ModelMap modelMap) {
        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";

            Long patientId = user.getPatientId();
            Long doctorId = user.getDoctorId();
            Long clinicId = user.getClinicId();
            User patient = userRepository.findOne(patientId);
            if (patient != null) {
                String otp = authenticationRepository.findByUserId(patientId);
                if (user.getAuthentication().getOtp().equals(otp)) {
                    ClinicPatients favourites = new ClinicPatients(patientId, clinicId, doctorId);
                    clinicPatientRepository.save(favourites);
                    response.put("patientId", patientId);
                    message = "Added Successfully";
                    status = HttpStatus.OK;
                } else {
                    message = "Enter valid OTP";
                    status = HttpStatus.NOT_ACCEPTABLE;
                    response.put("userId", patientId);
                }
            }
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    /**
     * The purpose of this controller is to get Favourite patients of doctor
     *
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/favourites", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> getFavourties(@RequestBody User user, BindingResult result, ModelMap modelMap) {
        Map<String, Object> response = new HashMap<>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";

        List<User> favourites = new ArrayList<>();
            Long doctorId = user.getDoctorId();
            User doctor = userRepository.findOne(doctorId);
            if (doctor != null) {
                Long clinicId = user.getClinicId();
                List<ClinicPatients> clinicPatientsList = clinicPatientRepository.getPatientsListByClinicAndDoctorId(clinicId, doctorId);
                if (clinicPatientsList != null && !clinicPatientsList.isEmpty()) {
                    for (ClinicPatients clinicPatients : clinicPatientsList) {
                        Long id = clinicPatients.getPatientId();
                        if (id != null) {
                            User patient = userRepository.findOne(id);
                            if (patient != null) {
                                User newPatient = CommonServices.getOnlyUser(patient);
                                UserDetails userDetails = CommonServices.getOnlyUserDetails(userDetailsRepository.findByUserId(id));
                                UserContactInfo contactInfo = CommonServices.getOnlyUserContactInfo(userContactRepository.findByUserId(id));
                                newPatient.setUserDetails(userDetails);
                                newPatient.setUserContactInfo(contactInfo);
                                newPatient.setDoctorId(doctorId);
                                favourites.add(newPatient);
                            }
                        }
                        response.put("favourites", favourites);
                        status = HttpStatus.OK;
                        message = "";
                    }
                } else {
                    status = HttpStatus.NOT_FOUND;
                }
            }

        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    /**
     * This method is for Doctor to access Favourite patient Information
     *
     * @param user
     * @param result
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/accessFavouritePatient", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> accessFavouritePatient(@RequestBody User user, BindingResult result, ModelMap
            modelMap) {
        Map<String, Object> response = new HashMap<>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";

            Long patientId = user.getPatientId();
            User u = userRepository.findOne(patientId);
            if (u != null) {
                /*User doctor = userRepository.findOne(user.getDoctorId().intValue());*/

                if (MyUserDetailsService.getUserFromSession().getUserId() == user.getDoctorId().intValue()) {
                    User doctor = MyUserDetailsService.getUserFromSession();
                    response.put("userId", doctor.getUserId());
                    /*UserDetails ud = userDetailsRepository.findByUserId(doctorId);*/
                    response.put("name", doctor.getUserDetails().getFirstName() + " " + doctor.getUserDetails().getLastName());
                   /* UserContactInfo uci = userContactRepository.findByUserId(doctorId);*/
                    response.put("mobileNumber", doctor.getUserContactInfo().getMobilePhone());
                    response.put("email", doctor.getUserContactInfo().getEmail());
                }
                UserDetails pud = userDetailsRepository.findByUserId(patientId);
                response.put("patientName", pud.getFirstName() + " " + pud.getLastName());
                response.put("patientId", patientId);
                response.put("clinicId", user.getClinicId());
                status = HttpStatus.OK;
            }
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    @RequestMapping(value = "/accessClinic", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> accessClinic(@RequestBody User clinic, BindingResult result, ModelMap modelMap) {
        Map<String, Object> response = new HashMap<>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";
            Long clinicId = clinic.getClinicId();
            User u = userRepository.findOne(clinicId);
            if (u != null) {
                User doctor = userRepository.findOne(clinic.getDoctorId());
                /*Long doctorId = doctor.getDoctorId();*/
                response.put("userName", doctor.getUserName());
                response.put("doctorId", doctor.getUserId());
                ClinicInfo clinicInfo = clinicInfoRepository.findByUserId(clinicId);
                response.put("clinicName", clinicInfo.getClinicName());
                response.put("clinicId", clinicId);
                status = HttpStatus.OK;
            }
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }


    /**
     * Te purpose of this controller is search doctor
     *
     * @param user
     * @param result
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> search(@RequestBody User user, BindingResult result, ModelMap modelMap) {
        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "Doctor does not exist in the Database";

        DoctorInfo doctorInfo;
        UserContactInfo userContactInfo;
        User clinic = CommonServices.getSessionUser();
        List<User> searchResults = new ArrayList<User>();
            DoctorInfo di = user.getDoctorInfo();
            UserContactInfo uci = user.getUserContactInfo();
            List<User> users = userRepository.searchDoctor(user.getUserId(), user.getUserName(), di.getMlrNumber(), uci.getMobilePhone(), uci.getEmail());
            if (users != null && !users.isEmpty()) {
                for (User currentUser : users) {
                    Long id = currentUser.getUserId();
                    User newUser = CommonServices.getOnlyUser(currentUser);
                    doctorInfo = doctorInfoRepository.findByUserId(id);
                    newUser.setDoctorInfo(CommonServices.getOnlyDoctorInfo(doctorInfo));
                    userContactInfo = userContactRepository.findByUserId(id);
                    newUser.setUserContactInfo(CommonServices.getOnlyUserContactInfo(userContactInfo));
                    searchResults.add(newUser);
                    response.put("users", searchResults);
                    response.put("clinicId", clinic.getUserId());
                }
                status = HttpStatus.OK;
                message = "";
            }
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    /**
     * The purpose of this controller is to add doctor clinic
     *
     * @param user
     * @param result
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/addDoctor", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> addDoctor(@RequestBody User user, BindingResult result, ModelMap modelMap) {
        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "Doctor was not added to your Clinic...Try Again !!!";
            Long userId = user.getUserId();
            if (userId != null && userId != 0) {
                User doctor = userRepository.findOne(userId);
                if (doctor != null) {
                    User currentUser = CommonServices.getSessionUser();
                    Long id = clinicDoctorsRepository.getIdByDoctorAndClinic(currentUser.getUserId(), userId);
                    if (id != null) {
                        message = "Doctor already exist in the clinic !!!";
                        status = HttpStatus.CONTINUE;

                    } else {
                        ClinicDoctors clinicDoctors = new ClinicDoctors(currentUser.getUserId(), userId);
                        clinicDoctorsRepository.save(clinicDoctors);

                        message = "Doctor added successfully to Clinic !!!";
                        status = HttpStatus.OK;
                    }

                }
            }
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    @RequestMapping(value = "clinicList", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> getClinicList(@RequestBody User user, BindingResult result, ModelMap modelMap) {
        Map<String, Object> response = new HashMap<>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = utilityServices.getMessage("Doctor.clinics.notfound.MESSAGE");

        List<User> clinics = new ArrayList<>();

            Long userId = user.getUserId();
            User doctor = userRepository.findOne(userId);
            if (doctor != null) {
                List<Long> clinicDoctorsList = clinicDoctorsRepository.getClinicsByDoctorId(userId);
                if (clinicDoctorsList != null && !clinicDoctorsList.isEmpty()) {
                    for (Long id : clinicDoctorsList) {
                        User clinic = userRepository.findClinic(id);
                        User newClinic = CommonServices.getOnlyUser(clinic);
                        UserContactInfo contactInfo = CommonServices.getOnlyUserContactInfo(userContactRepository.findByUserId(id));
                        ClinicInfo clinicInfo = CommonServices.getOnlyClinicInfo(clinicInfoRepository.findByUserId(id));
                        newClinic.setClinicInfo(clinicInfo);
                        newClinic.setUserContactInfo(contactInfo);
                        clinics.add(newClinic);
                    }
                    response.put("clinics", clinics);
                    status = HttpStatus.OK;
                    message = "";
                }
            }

        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    @RequestMapping(value = "clinicPatientList", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> getClinicPatientList(@RequestBody User user, BindingResult result, ModelMap  modelMap) {
        Map<String, Object> response = new HashMap<>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = utilityServices.getMessage("Doctor.clinics.patients.notfound.MESSAGE");

        List<User> patients = new ArrayList<>();

            Long doctorId = user.getDoctorId();
            User doctor = userRepository.findOne(doctorId);
            Long clinicId = user.getClinicId();
            User clinic = userRepository.findClinic(clinicId);
            if (doctor != null && clinic != null) {
                List<ClinicPatients> clinicPatientsList = clinicPatientRepository.getPatientsListByClinicAndDoctorId(clinicId, doctorId);
                if (clinicPatientsList.size() != 0) {
                    for (ClinicPatients cp : clinicPatientsList) {
                        Long id = cp.getPatientId();
                        User patient = userRepository.findOne(id);
                        if (patient != null) {
                            User newPatient = CommonServices.getOnlyUser(patient);
                            UserDetails userDetails = CommonServices.getOnlyUserDetails(userDetailsRepository.findByUserId(id));
                            UserContactInfo contactInfo = CommonServices.getOnlyUserContactInfo(userContactRepository.findByUserId(id));
                            newPatient.setUserDetails(userDetails);
                            newPatient.setUserContactInfo(contactInfo);
                            newPatient.setDoctorId(doctorId);
                            newPatient.setClinicId(clinicId);
                            newPatient.setPatientId(id);
                            patients.add(newPatient);
                        }
                    }

                }
                response.put("patients", patients);
                if (clinic.getImageFileName() != null && clinic.getImageFileName().isEmpty()) {
                    response.put("imageUrl", utilityServices.getMessage("User.profileImage.Url") + clinicId);
                } else {
                    response.put("imageUrl", utilityServices.getMessage("Clinic.DefaultImage.Url"));
                }

                status = HttpStatus.OK;
                message = "";
            }

        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    @RequestMapping(value = "clinicWisePatientList", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> getClinicWisePatientList(@RequestBody User user, BindingResult result, ModelMap modelMap) {
        Map<String, Object> response = new HashMap<>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = utilityServices.getMessage("Doctor.clinics.patients.notfound.MESSAGE");
        Map<String, List<User>> cwPatientList = new LinkedHashMap<>();

            Long doctorId = user.getDoctorId();
            User doctor = userRepository.findOne(doctorId);

            if (doctor != null) {
                List<Long> clinicIds = clinicDoctorsRepository.getClinicsByDoctorId(doctorId);
                for (Long clinicId : clinicIds) {
                    List<User> patients = new ArrayList<>();
                    String clinicName = clinicInfoRepository.getClinicName(clinicId);
                    List<ClinicPatients> clinicPatientsList = clinicPatientRepository.getPatientsListByClinicAndDoctorId(clinicId, doctorId);
                    if (clinicPatientsList != null && !clinicPatientsList.isEmpty()) {
                        for (ClinicPatients cp : clinicPatientsList) {
                            Long id = cp.getPatientId();
                            User patient = userRepository.findOne(id);
                            if (patient != null) {
                                User newPatient = CommonServices.getOnlyUser(patient);
                                UserDetails userDetails = CommonServices.getOnlyUserDetails(userDetailsRepository.findByUserId(id));
                                UserContactInfo contactInfo = CommonServices.getOnlyUserContactInfo(userContactRepository.findByUserId(id));
                                newPatient.setUserDetails(userDetails);
                                newPatient.setUserContactInfo(contactInfo);
                                newPatient.setDoctorId(doctorId);
                                newPatient.setClinicId(clinicId);
                                newPatient.setPatientId(id);
                                patients.add(newPatient);
                            }
                        }

                    }
                    cwPatientList.put(clinicName, patients);
                }
                response.put("cwPatientList", cwPatientList);
                response.put("imageUrl", utilityServices.getMessage("User.profileImage.Url"));
                status = HttpStatus.OK;
                message = "";

            }

        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    @RequestMapping(value = "getSettingsData", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> getSettingsData(@RequestBody ClinicDoctors clinicDoctors, BindingResult
            result, ModelMap modelMap) {

        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";
        Long clinicId = clinicDoctors.getClinicId();
        Long doctorId = clinicDoctors.getDoctorId();
        User doctor = userRepository.findOne(doctorId);
        User clinic = userRepository.findOne(clinicId);
        if (clinic != null && doctor != null) {
            ClinicDoctors cd = clinicDoctorsRepository.findByDoctorAndClinic(clinicId, doctorId);
            if (cd != null) {
                response.put("settings", cd);
                response.put("specialities", commonRepository.getSpecialities());
            }
            if (clinic.getImageFileName() != null && !clinic.getImageFileName().isEmpty()) {
                response.put("imageUrl", utilityServices.getMessage("User.profileImage.Url") + clinicId);
            } else {
                response.put("imageUrl", utilityServices.getMessage("Doctor.DefaultImage.Url"));
            }
            status = HttpStatus.OK;
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
        Long doctorId = user.getUserId();
        User doctor = userRepository.findOne(doctorId);
        if (doctor != null) {
            status = HttpStatus.OK;
            Map<Long, String> details = new LinkedHashMap<>();
            List<Long> clinicIds = clinicDoctorsRepository.getClinicsByDoctorId(doctorId);
            for (Long clinicId : clinicIds) {
                User clinic = userRepository.findClinic(clinicId);
                if (clinic != null) {
                    String clinicName = clinicInfoRepository.getClinicName(clinicId);
                    details.put(clinicId, clinicName);
                }
            }
            response.put("clinics", details);

        }
        Date dbDate = (Date) commonRepository.getDate();
        response.put("dbDate", dbDate);
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    @RequestMapping(value = "getOnlyDoctorSettingsData", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> getOnlyDoctorSettingsData(@RequestBody User user, BindingResult result, ModelMap  modelMap) {

        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";

        Long doctorId = user.getUserId();
        User doctor = userRepository.findsoProvider(doctorId);
        if (doctor != null) {
            ClinicDoctors cd = clinicDoctorsRepository.findonlyByDoctorAndClinic(doctorId);
            if (cd != null) {
                response.put("settings", cd);
            }
            response.put("imageUrl", utilityServices.getMessage("User.profileImage.Url"));
            response.put("specialities", commonRepository.getSpecialities());
            status = HttpStatus.OK;
        }
        Date dbDate = (Date) commonRepository.getDate();
        response.put("dbDate", dbDate);
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }


    @RequestMapping(value = "count/{id}", method = RequestMethod.GET)
    public Long messagesCount(@PathVariable(value = "id") Long id,ModelMap modelMap) {
        return messageRepository.countByDoctorId(id);
    }

}
