package com.datalife.servicelayers;


import com.datalife.entities.*;
import com.datalife.repositories.*;
import com.datalife.services.AlfrescoAuthDetails;
import com.datalife.services.CommonServices;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by Supriya on 1/2/2016.
 */
@RestController
@RequestMapping(value = "/services/customer/")
public class CustomerService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserDetailsRepository userDetailsRepository;

    @Autowired
    CommonRepository commonRepository;


    @Autowired
    LoginUserRepository loginUserRepository;


    @Autowired
    ServiceRequestsRepository serviceRequestsRepository;

    @Autowired
    HospitalInfoRepository hospitalInfoRepository;


    @Autowired
    AlfrescoAuthDetails alfrescoAuthDetails;

    protected static final Logger logger = Logger.getLogger(CustomerService.class);

    @RequestMapping(value = "getInitialData", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> getInitialData(@RequestBody String token, BindingResult result, ModelMap modelMap, HttpServletRequest request) {

        logger.info("inside the path /services/customer/getInitialData "+token);
        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";
        String s = request.getHeader("Authorization");
        logger.info(" s "+token);
        LoginUser user = loginUserRepository.findByToken(s);
        logger.info("LoginUser "+user);

            if (user != null && user.getUserId() != null) {
                String role = user.getRole();
                Long userId = user.getUserId();
                response.put("user", user);
                switch (role) {
                    case RolesInServices.ROLE_ADMIN:
                        response.put("total", userRepository.count());
                        status = HttpStatus.OK;
                        break;
                    case RolesInServices.ROLE_CSR:
                        status = HttpStatus.OK;
                        break;
                    case RolesInServices.ROLE_HOSPITAL:
                        response.put("hospitalInfo", CommonServices.getOnlyHospitalInfo(hospitalInfoRepository.findByUserId(userId)));
                        status = HttpStatus.OK;
                        break;

                    case RolesInServices.ROLE_DIAGNOSTICLABS:
                        status = HttpStatus.OK;
                        break;

                    case RolesInServices.ROLE_PHARMACY:
                        status = HttpStatus.OK;
                        break;
                }
            }
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        logger.info("end of /services/customer/getInitialData "+response);
        return response;
    }

    @RequestMapping(value = "getUsers", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> getCSRUsers(@RequestBody User user, BindingResult result, ModelMap modelMap) {

        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";
        if (user != null) {
            Long userId = user.getUserId();
                boolean verdict = userRepository.exists(userId);
                if (verdict) {
                    if (StringUtils.isNotBlank(user.getSendType())) {
                        response.put("users", commonRepository.getCSRUsers(user.getSendType(), "", "", 0L));
                        status = HttpStatus.OK;
                    }
                }
        }

        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

   /* @RequestMapping(value = "searchSubscribers", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> searchSubscribers(@RequestBody User user, BindingResult result, ModelMap modelMap) {

        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";
        if (user != null) {
            List<B2BUser> users=commonRepository.searchSubscribers(user.getUserId(),user.getUserName(),user.getEmail(),user.getMobileNo(),"ROLE_PATIENT");
            response.put("users",users );
            status = HttpStatus.OK;
        }

        response.put("statusCode", status.value());
        response.put("status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }*/


   /* @RequestMapping(value = "getGroupMembers", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> getGroupMembers(@RequestBody Long userId, BindingResult result, ModelMap modelMap) {

        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";

        if (userId != null && userId != 0L) {

            response.put("users", commonRepository.getCSRUsers("", "", "", userId));

            status = HttpStatus.OK;

        }


        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }


    @RequestMapping(value = "deleteUser", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> deleteUser(@RequestBody Long userId, BindingResult result, ModelMap modelMap) {

        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";
        if (userId != null && userId != 0L) {
            boolean verdict = userRepository.exists(userId);
            if (verdict) {
                userRepository.delete(userId);
                *//*response.put("users", commonRepository.getCSRUsers());*//*
                status = HttpStatus.OK;
            }
        }

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
            boolean verdict = userRepository.exists(userId);
            if (verdict) {
                response.put("userDetails", CommonServices.getOnlyUserDetails(userDetailsRepository.findByUserId(userId)));
                User user = commonRepository.getCSRUser(userId);
                if (user.getRole().equals("ROLE_HOS")) {
                    response.put("assignedTo", commonRepository.getCSRUsersByRole("ROLE_HOC", "", ""));
                }
                if (user.getRole().equals("ROLE_CSR")) {
                    response.put("assignedTo", commonRepository.getCSRUsersByRole("ROLE_HOS", user.getState(), user.getCity()));
                }
                response.put("user", user);
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
        String message = "";
        if (user != null && user.getUserId() != null && user.getUserId() != 0L) {
            User u = userRepository.findOne(user.getUserId());
            if (u != null) {
                UserDetails ud = u.getUserDetails();
                UserDetails newUserDetails = user.getUserDetails();
                if (ud != null && newUserDetails != null) {
                    newUserDetails.setUserDetailsId(ud.getUserDetailsId());
                }
                if (newUserDetails != null) {
                    newUserDetails.setUser(u);
                }
                UserContactInfo uci = u.getUserContactInfo();
                UserContactInfo newuci = user.getUserContactInfo();
                if (uci != null & newuci != null) {
                    newuci.setUserContactInfoId(uci.getUserContactInfoId());
                }
                if (newuci != null) {
                    newuci.setUser(u);
                }
                CSRInfo csrInfo = u.getCsrInfo();
                CSRInfo newcsrInfo = user.getCsrInfo();
                if (csrInfo != null && newcsrInfo != null) {
                    newcsrInfo.setId(csrInfo.getId());
                }
                if (newcsrInfo != null) {
                    newcsrInfo.setUser(u);
                }
                u.setUserDetails(newUserDetails);
                u.setUserContactInfo(newuci);
                u.setCsrInfo(newcsrInfo);
                userRepository.save(u);
                User newUser = commonRepository.getCSRUser(u.getUserId());
                response.put("user", newUser);
                response.put("userDetails", CommonServices.getOnlyUserDetails(userDetailsRepository.findByUserId(u.getUserId())));

                if (newUser.getRole().equals("ROLE_HOS")) {
                    response.put("assignedTo", commonRepository.getCSRUsersByRole("ROLE_HOC", "", ""));
                }
                if (newUser.getRole().equals("ROLE_CSR")) {
                    response.put("assignedTo", commonRepository.getCSRUsersByRole("ROLE_HOS", newUser.getState(), newUser.getCity()));
                }
                status = HttpStatus.OK;
            }
        }

        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }*/

}