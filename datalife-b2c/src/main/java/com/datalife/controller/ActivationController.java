package com.datalife.controller;

import com.datalife.entities.RolesInServices;
import com.datalife.entities.User;
import com.datalife.repositories.UserRepository;
import com.datalife.servicelayers.CommonService;
import com.datalife.services.AlfrescoAuthDetails;
import com.datalife.services.MyUserDetailsService;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Locale;

/**
 * Controller class that handles the requests for the application Activation links for confirmation.
 * <p/>
 * Created by supriya gondi on 8/8/2014.
 */
@Controller
@RequestMapping(value = "/activate")
public class ActivationController {
    /**
     *
     */
    @Autowired
    UserRepository userRepository;

    @Autowired
    AlfrescoAuthDetails alfrescoAuthDetails;

    @Autowired
    EmailController emailController;


    @Autowired
    CommonService commonService;

    /**
     * @param userName
     * @param modelMap
     * @return view after activation is successful through email
     */
    @RequestMapping("/{username}/{uuid}")
    public String activateAccount(@PathVariable(value = "username") String userName, @PathVariable(value = "uuid") String uuid, ModelMap modelMap) {
        String uid = userRepository.getUUIDbyUsername(userName);
        if (uid != null && !uid.isEmpty()) {
            if (uuid.equals(uid)) {
                User u = userRepository.findByUserName(userName);
                u.setUuid(null);
                u.setEmailVerfied(true);
                if (RolesInServices.ROLE_PATIENT.equals(u.getRole()) || RolesInServices.ROLE_CLINIC.equals(u.getRole())) {
                    if (u.isMobileVerified()) {
                        u.setEnabled(true);
                    }
                    u = commonService.createUserInAlresco(u);
                    userRepository.save(u);
                }/*else{
                    emailController.sendToGetDocuments(u);
                }*/
                modelMap.put("userName", userName);
            }
        }
        return "afterActivation";
    }

    /**
     * @param modelMap
     * @return view if user selects mobile option for activation of DataLife account
     */
    @RequestMapping(value = "/mobile", method = RequestMethod.GET)
    public String activationThroughMobile(ModelMap modelMap) {
        return "mobileActivation";
    }

    /**
     * @param modelMap
     * @return view after registration was successful
     */
    @RequestMapping(value = "/email", method = RequestMethod.GET)
    public String activationThroughEmail(ModelMap modelMap) {
        return "beforeActivation";
    }

    @RequestMapping(value = "/afterRegistration", method = RequestMethod.GET)
    public String afterRegistration(ModelMap modelMap) {
        return "afterRegistration";
    }

    @RequestMapping(value = "/afterSubmission", method = RequestMethod.GET)
    public String afterSubmission(ModelMap modelMap) {
        return "afterSubmission";
    }


}
