package com.datalife.servicelayers;

import com.datalife.controller.EmailController;
import com.datalife.entities.*;
import com.datalife.entities.RolesInServices;
import com.datalife.repositories.*;
import com.datalife.services.*;
import com.sun.mail.smtp.SMTPAddressFailedException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailSendException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.mail.SendFailedException;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

/**
 * Controller class that handles the application home page related requests
 * <p/>
 * Created by supriya gondi on 11/17/2014.
 */
@RestController
@RequestMapping(value = "/api/user/")
public class HomeService {

    /**
     * reference to userRepository
     */
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserContactRepository userContactRepository;

    /**
     * reference to emailController
     */
    @Autowired
    EmailController emailController;

    @Autowired
    CommonService commonService;
    /**
     * reference to passwordEncoder
     */
    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * reference to clinicInfoRepository
     */
    @Autowired
    ClinicInfoRepository clinicInfoRepository;

    /**
     * reference to clinicDoctorsRepository
     */
    @Autowired
    ClinicDoctorsRepository clinicDoctorsRepository;

    @Autowired
    ClinicPatientRepository clinicPatientRepository;

    @Autowired
    AlfrescoAuthDetails alfrescoAuthDetails;
    @Autowired
    UserPreferenceRepository userPreferenceRepository;

    @Autowired
    DoctorInfoRepository doctorInfoRepository;

    @Autowired
    UtilityServices utilityServices;

    @Autowired
    CommonRepository commonRepository;

    @Autowired
    EncounterRepository encounterRepository;

    @Autowired
    UserDetailsRepository userDetailsRepository;


    @Autowired
    MessageRepository messageRepository;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    UserEmergencyRepository userEmergencyRepository;

    @Autowired
    ServiceProviderRepository serviceProviderRepository;

    private static final Logger logger = Logger.getLogger(HomeService.class);


    @RequestMapping(value = "register", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> userRegistration(@Valid @RequestBody User user, BindingResult result, ModelMap modelMap) {
        logger.info("inside path /api/user/register" +user);

        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";

        if (result.hasErrors()) {
            message = utilityServices.getMessage("User.invalid.MESSAGE");
            response.put("message", message);
        }
        if (user != null) {
            Long doctorId = user.getDoctorId();
            Long clinicId = user.getClinicId();

            UserDetails userDetails = user.getUserDetails();
            if (userDetails != null) {
                user.getUserDetails().setUser(user);
            }

            UserContactInfo userContactInfo = user.getUserContactInfo();
            if (userContactInfo != null) {
                user.getUserContactInfo().setUser(user);
            }

            user.setCreationDate(new Date());
            User userExist = null;
            if(user.getUserName() != null){
                userExist = userRepository.findByUserName(user.getUserName());
            }
            logger.info("findByUserName" +userExist);
            if (userExist == null) {
                switch (user.getRole()) {
                    case RolesInServices.ROLE_PATIENT:

                        if (clinicId != null) {

                            User clinicUser = userRepository.findClinic(user.getClinicId());
                            String password = commonService.generatePassword();
                            user.setPassword(passwordEncoder.encode(password));
                            Map<String, Object> res = commonService.sendCredentialsToUser(user, password);

                            if("406".equals(res.get("statusCode").toString())){
                                status = HttpStatus.NOT_ACCEPTABLE;
                                response.put("message", res.get("message").toString());
                            }else{
                                user = userRepository.save(user);
                                user = commonService.createUserInAlresco(user);
                                ClinicPatients clinicPatients = new ClinicPatients(clinicUser.getUserId(), user.getUserId());
                                if (doctorId != null && doctorId != 0L) {
                                    clinicPatients = new ClinicPatients(user.getUserId(), clinicId, doctorId);
                                    User user1 = commonRepository.getUserDetails(user.getUserId());
                                    Search doctorDetails = commonRepository.convertObjToSearchEnty(user.getDoctorId(), user.getClinicId());
                                    response.put("doctorDetails", doctorDetails);
                                    response.put("patientDetails", user1);
                                }
                                clinicPatientRepository.save(clinicPatients);
                                userRepository.save(user);
                                response.put("message", utilityServices.getMessage("User.patient.create.MESSAGE"));
                                status = HttpStatus.OK;
                            }
                        } else {

                            String password = passwordEncoder.encode(user.getPassword());
                            user.setPassword(password);
                            userRepository.save(user);
                            status = HttpStatus.OK;
                            response.put("userName", user.getUserName());
                            response.put("message", utilityServices.getMessage("User.patient.create.MESSAGE"));
                        }
                        break;
                    case RolesInServices.ROLE_DOCTOR:
                        if (user.getDoctorInfo() != null) {
                            DoctorInfo u = doctorInfoRepository.searchByMlrNumber(user.getDoctorInfo().getMlrNumber());
                            List<UserContactInfo> emailMobile = userContactRepository.checkEmailIdMobileExistsInClinic(user.getUserContactInfo().getEmail(), user.getUserContactInfo().getMobilePhone());
                            if (u != null) {
                                response.put("message", utilityServices.getMessage("Doctor.Exits.MESSAGE"));
                                status = HttpStatus.NOT_ACCEPTABLE;
                            } else if (emailMobile.size() > 0) {
                                response.put("message", utilityServices.getMessage("Doctor.Email.Mobile.Exits"));
                                status = HttpStatus.NOT_ACCEPTABLE;
                            }
                            if ((u == null) && (emailMobile.size() == 0)) {

                                User clinicUser = userRepository.findClinic(user.getClinicId());
                                String password = commonService.generatePassword();
                                user.setPassword(passwordEncoder.encode(password));
                                /*commonService.sendCredentialsToUser(user, password);*/
                                Map<String, Object> res = commonService.sendCredentialsToUser(user, password);
                                if("406".equals(res.get("statusCode").toString())){
                                    status = HttpStatus.NOT_ACCEPTABLE;
                                    response.put("message", res.get("message").toString());
                                }else{
                                    user.getDoctorInfo().setUser(user);
                                    user = createDoctorUnderClinicInAlfresco(user, clinicUser);
                                    user.setEnabled(true);
                                    userRepository.save(user);
                                    response.put("message", utilityServices.getMessage("User.doctor.create.MESSAGE"));
                                    response.put("userName", user.getUserName());
                                    status = HttpStatus.OK;
                                }
                            }
                        }
                        break;
                    /*case RolesInServices.ROLE_CLINIC:

                        if (user.getClinicInfo() != null) {
                            user.getClinicInfo().setUser(user);
                            ClinicInfo u = clinicInfoRepository.searchByMlrNumber(user.getClinicInfo().getMlrNumber());
                            if (u != null) {
                                response.put("message", utilityServices.getMessage("Clinic.Exits.MESSAGE"));
                                status = HttpStatus.NOT_ACCEPTABLE;
                            } else {
                                String password = passwordEncoder.encode(user.getPassword());
                                user.setPassword(password);
                                userRepository.save(user);
                                response.put("userName", user.getUserName());
                                status = HttpStatus.OK;
                            }
                        }
                        break;*/
                    case RolesInServices.ROLE_CLINIC:
                        if (user.getClinicInfo() != null) {
                            user.getClinicInfo().setUser(user);
                            ClinicInfo u = clinicInfoRepository.searchByMlrNumber(user.getClinicInfo().getMlrNumber());
                            List<UserContactInfo> emailMobile = userContactRepository.checkEmailIdMobileExistsInClinic(user.getUserContactInfo().getEmail(), user.getUserContactInfo().getMobilePhone());
                            if (u != null) {
                                response.put("message", utilityServices.getMessage("Clinic.Exits.MESSAGE"));
                                status = HttpStatus.NOT_ACCEPTABLE;
                            }else if (emailMobile.size() > 0) {
                                response.put("message", utilityServices.getMessage("Doctor.Email.Mobile.Exits"));
                                status = HttpStatus.NOT_ACCEPTABLE;
                            }
                            if ((u == null) && (emailMobile.size() == 0)) {
                                user.setUserName(user.getUserContactInfo().getEmail());
                                String password = commonService.generatePassword();
                                user.setPassword(passwordEncoder.encode(password));
                                Map<String, Object> res = commonService.sendCredentialsToUser(user, password);
                                if("406".equals(res.get("statusCode").toString())){
                                    status = HttpStatus.NOT_ACCEPTABLE;
                                    response.put("message", res.get("message").toString());
                                }else{
                                    user = userRepository.save(user);
                                    user = createUserInAlfresco(user);
                                    user.setEnabled(true);
                                    user.setMobileVerified(true);
                                    user.setEmailVerfied(true);
                                    userRepository.save(user);
                                    response.put("message", utilityServices.getMessage("User.clinic.create.MESSAGE"));
                                    status = HttpStatus.OK;
                                }
                            }
                        }
                        break;
                }
            } else {
                response.put("message", utilityServices.getMessage("User.Exists.MESSAGE"));
                status = HttpStatus.NOT_ACCEPTABLE;
            }
        }
        response.put("statusCode", status.value());
        return response;
    }

    @RequestMapping(value = "verifyEmailAddress", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> verifyEmailAddress(@Valid @RequestBody User user, BindingResult result, ModelMap modelMap) {

        Map<String, Object> response = new HashMap<String, Object>();
        if (user != null && user.getRole()!= null) {
                Long id = user.getUserId();
                String userEmail = user.getEmail();
                User u = userRepository.findOne(id);
                String email = u.getUserContactInfo().getEmail();
                if (email.equals(userEmail)) {
                    response  = commonService.sendEmailActivationLink(u);
                } else{
                    response = isEmailIdExists(user,result,modelMap);
                    if("100".equals(response.get("statusCode").toString())){
                        u.getUserContactInfo().setEmail(userEmail);
                        response  = commonService.sendEmailActivationLink(u);
                    }
                }
            if("200".equals(response.get("statusCode").toString())){
                u = (User) response.get("user");
                userRepository.save(u);
                response.remove("user");
            }
        }
        return response;
    }

    @RequestMapping(value = "verifyMobileNumber", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> verifyMobileNumber(@Valid @RequestBody User user, BindingResult result, ModelMap modelMap) {

        Map<String, Object> response = new HashMap<String, Object>();

        if (user != null && user.getMobileNo()!= null) {
            Long id = user.getUserId();
            String userMobileNo = user.getMobileNo();
            User u = userRepository.findOne(id);
            String mobile = u.getUserContactInfo().getMobilePhone();
            if (mobile.equals(userMobileNo)) {
                response  = sendVerificationCode(u,result,modelMap);
            } else{
                response = isMobileNumberExists(user,result,modelMap);
                if("100".equals(response.get("statusCode").toString())){
                    u.getUserContactInfo().setMobilePhone(userMobileNo);
                    response  = sendVerificationCode(u,result,modelMap);

                }
            }
            if("200".equals(response.get("statusCode").toString())){
                u = (User) response.get("user");
                userRepository.save(u);
                response.remove("user");
            }
        }
        return response;
    }


    @RequestMapping(value = "verifyOTP", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> verifyOTP(@Valid @RequestBody User user, BindingResult result, ModelMap modelMap) {

        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";

        if (result.hasErrors()) {
            message = utilityServices.getMessage("User.invalid.MESSAGE");
        }
        if (user != null) {
            Long id = user.getUserId();
            synchronized (this) {
                User u = userRepository.findOne(id);
                String userOTP = u.getOtp();
                String otp = user.getOtp();
                if (StringUtils.isNotBlank(userOTP) && StringUtils.isNotBlank(otp)) {
                    if (otp.equals(userOTP)) {
                        u =commonService.createUserInAlresco(u);
                        u.setMobileVerified(true);
                        if (u.isEmailVerfied()) {
                            u.setEnabled(true);
                        }
                        userRepository.save(u);
                        status = HttpStatus.OK;
                        message = "Mobile Number verified";
                    } else {
                        message = "Invalid OTP";
                    }
                }
            }
        }
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);

        return response;
    }


    @RequestMapping(value = "isEmergencyMobileExit", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> isEmergencyMobileExit(@RequestBody UserEmergencyInfo userEmergencyInfo, BindingResult result, ModelMap modelMap) {

        logger.debug("Path: start of isEmergencyMobileExit and input : "+ userEmergencyInfo.getMobileNumber());

        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";

        if (result.hasErrors()) {
            message = utilityServices.getMessage("User.invalid.MESSAGE");
        }
        if (userEmergencyInfo.getMobileNumber() != null) {
            status = HttpStatus.OK;
            User user = userRepository.findOne(userEmergencyInfo.getUserId());
            if(user.getUserContactInfo().getMobilePhone().equals(userEmergencyInfo.getMobileNumber())){
                status = HttpStatus.NOT_ACCEPTABLE;
                message = utilityServices.getMessage("User.Emergency.mobileExists.MESSAGE");
            }else {
                LinkedList<UserEmergencyInfo> emergencyInfos = userEmergencyRepository.checkMobileNoExits(userEmergencyInfo.getMobileNumber(),userEmergencyInfo.getUserId());
                if(emergencyInfos.size() > 0){
                    for (UserEmergencyInfo info : emergencyInfos){
                        if(userEmergencyInfo.getMobileNumber().equals(info.getMobileNumber())){
                            status = HttpStatus.NOT_ACCEPTABLE;
                            message = utilityServices.getMessage("User.mobileExists.MESSAGE");
                        }
                    }
                 }
            }
        }
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }


    /**
     * This method handles signup activity of User
     *
     * @param user
     * @param result
     * @param modelMap
     * @return Map<String, Object> signupDetails includes status,statusCode,message and userDetails
     */
    @RequestMapping(value = "signup", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> userSignup(@Valid @RequestBody User user, BindingResult result, ModelMap modelMap) {

        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;

        String message = "";
        String ticket = "";

        if (result.hasErrors()) {
            message = utilityServices.getMessage("User.invalid.MESSAGE");
        }
        if (user != null) {
            User u = userRepository.findByUserName(user.getUserName());
            response.put("userName", user.getUserName());
            //check user Exists
            if (u != null) {
                status = HttpStatus.NOT_ACCEPTABLE;
                message = utilityServices.getMessage("User.Exists.MESSAGE");
            } else {

                String password = passwordEncoder.encode(user.getPassword());
                user.setPassword(password);
                user.getUserContactInfo().setUser(user);
                if (user.getUserDetails() != null) {
                    user.getUserDetails().setUser(user);
                }

                //send activation link to user email id
                if (user.getActivation().equals("email") && !"true".equals(user.getIsFromAppReg())) {
                    user.setUuid(UUID.randomUUID().toString());
                    String messageBody = user.getUserName() + ", Thank you for signing up with DataLife. To activate DataLife account click on activation link sent to your email. DataLife";
                    try {
                        alfrescoAuthDetails.sendMessage(user.getUserContactInfo().getMobilePhone(), messageBody);
                       /* emailController.sendActivationLink(user, result, modelMap);*/
                        message = utilityServices.getMessage("User.email.activation.MESSAGE");
                        status = HttpStatus.OK;
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (MailSendException e) {
                        Exception[] exceptionArray = e.getMessageExceptions();
                        e.getFailedMessages();
                        for (Exception e1 : exceptionArray) {
                            if (e1 instanceof SendFailedException) {
                                Exception e2 = ((SendFailedException) e1).getNextException();
                                if (e2 instanceof SMTPAddressFailedException) {
                                    message = utilityServices.getMessage("User.email.SMTPAddressFailedException.MESSAGE");
                                    status = HttpStatus.NOT_ACCEPTABLE;
                                    break;
                                }
                            }
                        }
                    }
                }

                //send otp  to user mobile
                if (user.getActivation().equals("mobile") || "true".equals(user.getIsFromAppReg())) {
                    Random randomGenerator = new Random();
                    Integer randomInt = randomGenerator.nextInt(100000);
                    user.setOtp(randomInt.toString());
                    String messageBody = "";
                    if (user.getRole().equals("ROLE_CLINIC")) {
                        messageBody = user.getClinicInfo().getClinicName() + " " + user.getUserDetails().getLastName() + ", Thank you for signing up with DataLife. Your verification code for activation is " + randomInt.toString() + ". DataLife";

                    } else {
                        messageBody = "Mr/ Ms." + user.getUserDetails().getFirstName() + " " + user.getUserDetails().getLastName() + ", Thank you for signing up with DataLife. Your verification code for activation is " + randomInt.toString() + ". DataLife";
                    }
                    try {

                        alfrescoAuthDetails.sendMessage(user.getUserContactInfo().getMobilePhone(), messageBody);
                        emailController.sendActivationCode(user, result, modelMap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    status = HttpStatus.CONTINUE;
                }


                ticket = alfrescoAuthDetails.getAlfrescoConnectionTicket("admin", "admin");
                String url = alfrescoAuthDetails.addUser(user, ticket);
                user.setAlfrescoUrl(url);
                alfrescoAuthDetails.logoutTicket(ticket);

                userRepository.save(user);

                //check whether user had created the folder in his alfresco account
                if (user.getUserParentDir() == null || "".equals(user.getUserParentDir())) {
                    String userticket = null;
                    try {

                        //get user ticket from alfresco
                        userticket = alfrescoAuthDetails.getAlfrescoConnectionTicket(user.getUserName(), user.getPassword());

                        //get admin ticket from alfresco
                        String adminticket = alfrescoAuthDetails.getAlfrescoConnectionTicket("admin", "admin");

                        //get repositoryId from alfresco based on user ticket
                        String repositoryId = alfrescoAuthDetails.getRepositoryId(userticket, user.getUserName());

                        String[] repId = repositoryId.split("Ref:");
                        String[] repNode = repId[1].split("/");

                        //user create a folder in his repository
                        String usernodeRef = alfrescoAuthDetails.addFolder(user.getUserId(), userticket, repNode[3]);
                        user.setUserParentDir(usernodeRef);

                        String[] node = usernodeRef.split("/");

                        //admin grant the permission as Collaborator for created folder to user
                        String res = alfrescoAuthDetails.grantPermission(adminticket, user.getUserName(), node[3], "Collaborator");

                        //invalidate the tickets
                        alfrescoAuthDetails.logoutTicket(adminticket);
                        User sessionUser = MyUserDetailsService.getUserFromSession();
                        if (sessionUser != null) {
                            sessionUser.setAlfresAuthTicket(userticket);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    userRepository.save(user);
                }
            }
        }
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);

        return response;
    }

    /**
     * This method will check whether the username is already exists or not
     *
     * @param userName
     * @param modelMap
     * @return Map<String, Object> signupDetails includes status,statusCode,message
     */
    @RequestMapping(value = "isExists/{userName}", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
    public Map<String, Object> userSignup(@PathVariable String userName, ModelMap modelMap) {
        logger.info("Requesting for path userSignup" + userName);

        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
        String message = "";
        User u = userRepository.findByUserName(userName);
        if (u != null) {
            message = utilityServices.getMessage("User.Exists.MESSAGE");
        } else {
            status = HttpStatus.CONTINUE;
        }
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }


    /**
     * This method handles signin activity of User
     *
     * @param user
     * @return Map<String, Object> signupDetails includes status,statusCode,message and userDetails
     */
    @RequestMapping(value = "signin", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> ValidateLogin(@Valid @RequestBody User user, BindingResult result) {
        Map<String, Object> response = new HashMap<String, Object>();

        logger.debug("Requesting for path /signin");

        Integer status = 400;
        String message = "";
        String ticket = null;
        if (result.hasErrors()) {
            message = utilityServices.getMessage("User.invalid.MESSAGE");
        }
        if (user.getLocation() != null) {
            MyUserDetailsService.getUserFromSession().setLocation(user.getLocation());
        }
        User u = userRepository.findByUserName(user.getUserName());
        //check user is present r not
        if (u != null) {
            boolean isValid = passwordEncoder.matches(user.getPassword(), u.getPassword());

            //check password is valid
            if (isValid) {

                //check whether user is activated this account
                if (u.isEnabled()) {

                    MyUserDetailsService.setUserInSession(u, userRepository);

                    if (u.getRole().equals("ROLE_PATIENT")) {
                        status = 101;
                        ticket = alfrescoAuthDetails.getAlfrescoConnectionTicket(u.getUserName(), u.getPassword());
                        MyUserDetailsService.getUserFromSession().setAlfresAuthTicket(ticket);

                        /*Integer preferencesCount = userPreferenceRepository.getPreferencesCount(u.getUserId());*/

                        if (u.getUserDetails() != null) {
                            response.put("ud", CommonServices.getOnlyUserDetails(u.getUserDetails()));
                        }
                        if (u.getIdCardDetails() != null) {
                            response.put("icd", CommonServices.getOnlyIdCardDetails(u.getIdCardDetails()));
                        }
                        if (u.getInsuranceDetails() != null) {
                            response.put("insd", CommonServices.getOnlyInsuranceDetails(u.getInsuranceDetails()));
                        }

                        response.put("user", CommonServices.getOnlyUser(u));
                        response.put("bloodGroups", commonService.getBloodGroups());
                        response.put("countries", commonService.getCountries());

                    }
                    if (u.getRole().equals("ROLE_DOCTOR")) {
                        status = 102;
                        if (ticket == null) {
                            ticket = alfrescoAuthDetails.getAlfrescoConnectionTicket(u.getUserName(), u.getPassword());
                            MyUserDetailsService.getUserFromSession().setAlfresAuthTicket(ticket);
                        }

                        UserDetails userDetails = u.getUserDetails();
                        if (userDetails != null) {
                            response.put("ud", CommonServices.getOnlyUserDetails(userDetails));
                        }
                        response.put("user", CommonServices.getOnlyUser(u));
                        response.put("bloodGroups", commonService.getBloodGroups());


                    }
                    if (u.getRole().equals("ROLE_PROVIDER")) {
                        status = 103;
                    }
                    if (u.getRole().equals("ROLE_CLINIC")) {
                        status = 104;
                        if (ticket == null) {
                            ticket = alfrescoAuthDetails.getAlfrescoConnectionTicket(u.getUserName(), u.getPassword());
                            MyUserDetailsService.getUserFromSession().setAlfresAuthTicket(ticket);
                        }

                        response.put("clinicName", u.getClinicInfo().getClinicName());


                        if (u.getUserContactInfo() != null) {
                            response.put("uci", CommonServices.getOnlyUserContactInfo(u.getUserContactInfo()));
                        }
                        if (u.getClinicInfo() != null) {
                            response.put("ci", CommonServices.getOnlyClinicInfo(u.getClinicInfo()));
                        }
                        response.put("user", CommonServices.getOnlyUser(u));
                        response.put("countries", commonService.getCountries());

                    }

                    response.put("userName", u.getUserName());
                    response.put("id", u.getUserId());

                   /* response.put("profileImageUrl", u.getProfileImageUrl());*/
                } else {
                    message = utilityServices.getMessage("user.activation.link.MESSAGE");
                }
            } else {
                message = utilityServices.getMessage("User.invalid.loginDetails.MESSAGE");
            }
        } else {
            message = utilityServices.getMessage("User.invalid.MESSAGE");
        }
        response.put("imageUrl", utilityServices.getMessage("User.profileImage.Url"));
        response.put("statusCode", status);
        response.put("message", message);
        return response;
    }


    @RequestMapping(value = "licRegExists/{mlrNumber}", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
    public Map<String, Object> licRegExists(@PathVariable String mlrNumber,ModelMap modelMap) {
        /*logger.debug("Requesting for path");*/

        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
        String message = "";
        int flag = commonRepository.anylicenceNumberExistInDb(mlrNumber);
        if (flag > 0) {
            message = utilityServices.getMessage("Registration.Exits.MESSAGE");
        } else {
            status = HttpStatus.CONTINUE;
        }
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    @RequestMapping(value = "medLicRegNoExists/{mlrNumber}", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
    public Map<String, Object> medLicRegNoExists(@PathVariable String mlrNumber, ModelMap modelMap) {
        logger.info("Requesting for path medLicRegNoExists/" + mlrNumber);

        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
        String message = "";
        DoctorInfo u = doctorInfoRepository.searchByMlrNumber(mlrNumber);
        if (u != null) {
            message = utilityServices.getMessage("Doctor.Exits.MESSAGE");
        } else {
            status = HttpStatus.CONTINUE;
        }
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);

        logger.info("End of method"+ response);
        return response;
    }


    @RequestMapping(value = "isMobileNumberExists", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public Map<String, Object> isMobileNumberExists(@RequestBody User user, BindingResult result, ModelMap modelMap) {
        logger.info("Requesting for path isMobileNumberExists" + user);
        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
        String message = "";
        boolean flag = false;
        final String mobileNo = user.getMobileNo();
        switch (user.getRole()){
            case RolesInServices.ROLE_PATIENT:
                List<UserContactInfo> u = userContactRepository.checkMobilePhoneExistsInPatient(mobileNo);
                if(u.size() > 0){
                    flag = true;
                }
                break;
            case RolesInServices.ROLE_CLINIC:
            case RolesInServices.ROLE_DOCTOR:
            case RolesInServices.ROLE_REFERRALDOCTOR:
            case RolesInServices.ROLE_TELECONSULTANT:
            case RolesInServices.ROLE_HOSPITAL:
            case RolesInServices.ROLE_PHARMACY:
            case RolesInServices.ROLE_DIAGNOSTICLABS:

                List<UserContactInfo> userContactInfos = userContactRepository.checkMobilePhoneExistsInClinic(mobileNo);
                List<ProviderDetails> providerList = serviceProviderRepository.checkMobileExist(mobileNo);
                if(userContactInfos.size() > 0 || providerList.size() > 0 ){
                    flag = true;
                }
              /*  flag = commonRepository.checkMobileExistAcceptPatientInDb(mobileNo);*/
                break;
        }
        if (flag) {
            message = utilityServices.getMessage("User.mobileExists.MESSAGE");
        } else {
            status = HttpStatus.CONTINUE;
        }

        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);

        logger.info("End of isMobileNumberExists"+response);
        return response;
    }


    @RequestMapping(value = "isEmailIdExists", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public Map<String, Object> isEmailIdExists(@RequestBody User user, BindingResult result, ModelMap modelMap) {
        logger.info("Requesting for path : isEmailIdExists " + user);

        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
        String message = "";
        final String email = user.getEmail();
        boolean flag = false;
        switch (user.getRole()){
            case RolesInServices.ROLE_PATIENT:
                List<UserContactInfo> u = userContactRepository.checkEmailIdExistsInPatient(email);
                if(u.size() > 0){
                    flag = true;
                }
                break;
            case RolesInServices.ROLE_CLINIC:
            case RolesInServices.ROLE_DOCTOR:
            case RolesInServices.ROLE_REFERRALDOCTOR:
            case RolesInServices.ROLE_TELECONSULTANT:
            case RolesInServices.ROLE_HOSPITAL:
            case RolesInServices.ROLE_PHARMACY:
            case RolesInServices.ROLE_DIAGNOSTICLABS:
                /*flag =commonRepository.checkEmailIdExistAcceptPatientInDb(email);*/
                List<UserContactInfo> userContactInfos = userContactRepository.checkEmailIdExistsInClinic(email);
                List<ProviderDetails> providerList = serviceProviderRepository.checkEmailExist(email);

                if(userContactInfos.size() > 0 || providerList.size() > 0 ){
                    flag = true;
                }
                break;
        }
        if (flag) {
            message = utilityServices.getMessage("User.emailExists.MESSAGE");
        } else {
            status = HttpStatus.CONTINUE;
        }
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        logger.info("End of path : isEmailIdExists "+ response);
        return response;
    }

    /**
     * The purpose of this controller is to close seeion of user
     *
     * @return
     */
    @RequestMapping(value = "logoutSuccess", method = RequestMethod.GET, produces = "application/json")
    public Map<String, Object> logoutSuccess() {
        Map<String, Object> response = new HashMap<String, Object>();
        if (MyUserDetailsService.getUserFromSession().getAlfresAuthTicket() != null)
            alfrescoAuthDetails.logoutTicket(MyUserDetailsService.getUserFromSession().getAlfresAuthTicket());
        String url = "/auth/logout-success";
        response.put("url", url);
        return response;
    }

    @RequestMapping(value = "checkPatientExist", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> checkPatientExist(@Valid @RequestBody User user, BindingResult result, ModelMap modelMap) {
        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.NOT_FOUND;
        String message = utilityServices.getMessage("User.invalid.MESSAGE");

        User u = userRepository.findPatientByUserName(user.getUserName());
        //check user is present r not


        if (u != null) {
            boolean isValid = passwordEncoder.matches(user.getPassword(), u.getPassword());
            if (isValid) {
                response.put("patientId", u.getUserId());
                status = HttpStatus.OK;
                message = "";
            }
        }
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);

        return response;
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> deleteUser(@Valid @RequestBody User user, BindingResult result, ModelMap modelMap) {
        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = utilityServices.getMessage("User.invalid.MESSAGE");

        String role = userRepository.getRole(user.getUserId());

        if (role != null) {
            if (role.equals("ROLE_PATIENT")) {
                Long id = clinicPatientRepository.getIdByPatientAndClinic(user.getClinicId(), user.getUserId());
                clinicPatientRepository.delete(id);
            }
            if (role.equals("ROLE_DOCTOR")) {
                Long id = clinicDoctorsRepository.getIdByDoctorAndClinic(user.getClinicId(), user.getUserId());
                clinicDoctorsRepository.delete(id);
            }
            message = "Successfully deleted !!!";
            status = HttpStatus.OK;
        }


        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);

        return response;
    }

    @RequestMapping(value = "getVisitedDoctors", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> getVisitedDoctors(@Valid @RequestBody User user, BindingResult result, ModelMap modelMap) {
        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = utilityServices.getMessage("User.invalid.MESSAGE");

        if (user != null) {
            Long userId = user.getUserId();
            if (userId != null) {
                Long count=0L;
                Map<Long, String> visitedDoctors = new LinkedHashMap<>();
                PageRequest pageRequest = new PageRequest(0, 4);
                List<Long> doctorIds = encounterRepository.getVisitedDoctors(userId, pageRequest);
                for (Long id : doctorIds) {
                    if (id != null) {
                        user = userRepository.findOne(id);
                        if (user != null) {
                            String fullName = userDetailsRepository.getFullName(id);
                            String[] names = fullName.split(",");
                            count=messageRepository.countByPatientAndDoctorId(userId,id);
                            visitedDoctors.put(id, "Dr. " + names[0] + " " + names[1]+","+count);
                        }
                    }
                }
                response.put("visitedDoctors", visitedDoctors);
                response.put("totalCount",messageRepository.countByPatientId(userId));
            }
            message = "Successfully deleted !!!";
            status = HttpStatus.OK;
        }

        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);

        return response;
    }


    @RequestMapping(value = "getVisitedPatients", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> getVisitedPatients(@Valid @RequestBody User user, BindingResult result, ModelMap modelMap) {
        Map<String, Object> response = new HashMap<>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = utilityServices.getMessage("User.invalid.MESSAGE");
        if (user != null) {
            Long userId = user.getUserId();
            if (userId != null) {
                Map<Long, String> visitedPatients = new LinkedHashMap<>();
                Long count=0L;
                PageRequest pageRequest = new PageRequest(0, 4);
                List<Long> patientIds = encounterRepository.getVisitedPatients(userId, pageRequest);
                for (Long id:patientIds) {
                    String fullName = userDetailsRepository.getPatientFullName(id);
                    if (StringUtils.isNotBlank(fullName)) {
                        String[] names = fullName.split(",");
                        count=messageRepository.countByDoctorAndPatientId(userId,id);
                        visitedPatients.put(id, names[0] + " " + names[1]+","+count);
                    }
                }
                response.put("visitedPatients", visitedPatients);
                response.put("totalCount",messageRepository.countByDoctorId(userId));
            }
            message = "Successfully deleted !!!";
            status = HttpStatus.OK;
        }


        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);

        return response;
    }


    @RequestMapping(value = "getMessagesFromPatient", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> getMessagesFromPatient(@Valid @RequestBody Message message, BindingResult result, ModelMap modelMap) {
        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String responseMessage = utilityServices.getMessage("User.invalid.MESSAGE");
        String imageURL = utilityServices.getMessage("User.profileImage.Url");
        if (message != null) {
            List<Message> messages = messageRepository.getMessages(message.getPatientId(), message.getDoctorId());
            response.put("messages", messages);
            List<Message> messageList = messageRepository.getMessagesFromPatient(message.getPatientId(), message.getDoctorId());
            for (Message m : messageList) {
                m.setVisited(true);
            }
            messageRepository.save(messageList);
            responseMessage = "Successfully deleted !!!";
            status = HttpStatus.OK;
        }
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", responseMessage);
        response.put("imageURL", imageURL);
        return response;
    }

    @RequestMapping(value = "getMessagesFromDoctor", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> getMessagesFromDoctor(@Valid @RequestBody Message message, BindingResult result, ModelMap modelMap) {
        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String responseMessage = utilityServices.getMessage("User.invalid.MESSAGE");
        String imageURL = utilityServices.getMessage("User.profileImage.Url");
        if (message != null) {
            List<Message> messages = messageRepository.getMessages(message.getPatientId(), message.getDoctorId());
            response.put("messages", messages);
            List<Message> messageList = messageRepository.getMessagesFromDoctor(message.getPatientId(), message.getDoctorId());
            for (Message m : messageList) {
                m.setVisited(true);
            }
            messageRepository.save(messageList);
            responseMessage = "Successfully deleted !!!";
            status = HttpStatus.OK;
        }
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", responseMessage);
        response.put("imageURL", imageURL);
        return response;
    }

    @RequestMapping(value = "getMessagesAfterSend", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> getMessagesAfterSend(@Valid @RequestBody Message message, BindingResult result, ModelMap modelMap) {
        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String responseMessage = utilityServices.getMessage("User.invalid.MESSAGE");
        String imageURL = utilityServices.getMessage("User.profileImage.Url");
        if (message != null) {
            List<Message> messages = messageRepository.getMessages(message.getPatientId(), message.getDoctorId());
            response.put("messages", messages);
            responseMessage = "Successfully deleted !!!";
            status = HttpStatus.OK;
        }
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", responseMessage);
        response.put("imageURL", imageURL);
        return response;
    }


    /*@RequestMapping(value = "getMessages", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> getMessages(@Valid @RequestBody Message message, BindingResult result, ModelMap modelMap) {
        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String responseMessage = utilityServices.getMessage("User.invalid.MESSAGE");

        if (message != null) {
            response.put("messages", messageRepository.getMessages(message.getPatientId(), message.getDoctorId()));
            responseMessage = "Successfully deleted !!!";
            status = HttpStatus.OK;
        }

        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", responseMessage);

        return response;
    }*/


    @RequestMapping(value = "sendMessage", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> sendMessage(@Valid @RequestBody Message message, BindingResult result, ModelMap modelMap) {
        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String responseMessage = "Message not sent";
        if (message != null) {
            message.setDate(new Date());
            messageRepository.save(message);
            responseMessage = "Message sent";
            status = HttpStatus.OK;
        }
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", responseMessage);

        return response;
    }

    @RequestMapping(value = "logout", method = RequestMethod.POST, headers = {"content-type=application/json"})
    public Map<String, Object> logout(ModelMap modelMap) {
        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            authentication.setAuthenticated(false);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            status = HttpStatus.OK;
        }
        response.put("statusCode", status.value());
        return response;
    }

    public User createDoctorUnderClinicInAlfresco(User doctorUser, User clinicUser) {

        //login as admin and add user to alfresco
        String adminticket = alfrescoAuthDetails.getAlfrescoConnectionTicket(this.messageSource.getMessage("alfresco.admin.userName", null, "Default alfresco admin userName", null), this.messageSource.getMessage("alfresco.admin.password", null, "Default alfresco admin password", null));
        String url = alfrescoAuthDetails.addUser(doctorUser, adminticket);
        doctorUser.setAlfrescoUrl(url);
        alfrescoAuthDetails.logoutTicket(adminticket);

        //login in to clinic to get the RepositoryId
        String ticket = alfrescoAuthDetails.getAlfrescoConnectionTicket(clinicUser.getUserName(), clinicUser.getPassword());
        String[] userdir = clinicUser.getUserParentDir().split("/");
        doctorUser = userRepository.save(doctorUser);
        ClinicDoctors clinicDoctors = new ClinicDoctors(clinicUser.getUserId(), doctorUser.getUserId());

        //add folder in alfresco under clinic and folder name will be doctor id
        String noderef = alfrescoAuthDetails.addFolder(doctorUser.getUserId(), ticket, userdir[3]);
        clinicDoctors.setDoctorNodeRef(noderef);
        clinicDoctorsRepository.save(clinicDoctors);

        //check whether user had created the folder in his alfresco account
        if (doctorUser.getUserParentDir() == null || "".equals(doctorUser.getUserParentDir())) {

            try {
                //get user ticket from alfresco
                String userticket = alfrescoAuthDetails.getAlfrescoConnectionTicket(doctorUser.getUserName(), doctorUser.getPassword());

                //get repositoryId from alfresco based on user ticket
                String repositoryId = alfrescoAuthDetails.getRepositoryId(userticket, doctorUser.getUserName());

                String[] repId = repositoryId.split("Ref:");
                String[] repNode = repId[1].split("/");

                //user create a folder in his repository
                String usernodeRef = alfrescoAuthDetails.addFolder(doctorUser.getUserId(), userticket, repNode[3]);
                doctorUser.setUserParentDir(usernodeRef);

                String[] node = usernodeRef.split("/");

                //admin grant the permission as Collaborator for created folder to user
                alfrescoAuthDetails.grantPermission(adminticket, doctorUser.getUserName(), node[3], "Collaborator");

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                alfrescoAuthDetails.logoutTicket(ticket);
            }
        }
        return doctorUser;
    }



    @RequestMapping(value = "sendEmailActivationLink", method = RequestMethod.POST, headers = {"content-type=application/json"})
    public Map<String, Object> sendEmailActivationLink(User user,BindingResult result,ModelMap modelMap){
        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";
        user.setUuid(null);
        if (StringUtils.isBlank(user.getUuid())) {
            user.setUuid(UUID.randomUUID().toString());
            String email = null;
            if(user.getEmail()!= null){
                email = user.getEmail();
            }else{
                email = user.getUserContactInfo().getEmail();
            }
            boolean verdict = emailController.sendActivationLink(user, email, result, modelMap);
            if (verdict) {
               /* response.put("user", user);*/
                status = HttpStatus.OK;
                message = "Click on activation link sent to mail";
            } else {
                status = HttpStatus.NOT_ACCEPTABLE;
                message = "Invalid Email";
            }
        } else {
            status = HttpStatus.OK;
            message = "Activation link already sent";
        }
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    @RequestMapping(value = "sendVerificationCode", method = RequestMethod.POST, headers = {"content-type=application/json"})
    public Map<String, Object> sendVerificationCode(User user,BindingResult result,ModelMap modelMap){
        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";
        String password = commonService.generatePassword();
        user.setOtp(password);
        String messageBody;
        if (RolesInServices.ROLE_CLINIC.equals(user.getRole())) {
            messageBody = user.getClinicInfo().getClinicName() + ", Thank you for signing up with DataLife. Your verification code for activation is " + password + ". DataLife";
        } else {
            messageBody = "Mr/ Ms." +user.getUserDetails().getFirstName() + " " + user.getUserDetails().getLastName() + ", Thank you for signing up with DataLife. Your verification code for activation is " + password + ". DataLife";
        }
        try {
            alfrescoAuthDetails.sendMessage(user.getUserContactInfo().getMobilePhone(), messageBody);
           response.put("user", user);
            status = HttpStatus.OK;
            message = "OTP sent to your mobile";
        } catch (Exception e) {
            message = "Invalid Mobile Number";
        }
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }


    public User createUserInAlfresco(User user) {

        try {
            //get admin ticket from alfresco
            String adminticket = alfrescoAuthDetails.getAlfrescoConnectionTicket(this.messageSource.getMessage("alfresco.admin.userName", null, "Default alfresco admin userName", null), this.messageSource.getMessage("alfresco.admin.password", null, "Default alfresco admin password", null));
            String url = alfrescoAuthDetails.addUser(user, adminticket);
            user.setAlfrescoUrl(url);

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
            String res = alfrescoAuthDetails.grantPermission(adminticket, user.getUserName(), node[3], "Collaborator");

            //invalidate the tickets
            alfrescoAuthDetails.logoutTicket(adminticket);
            User sessionUser = MyUserDetailsService.getUserFromSession();
            if (sessionUser != null) {
                sessionUser.setAlfresAuthTicket(userticket);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

}
