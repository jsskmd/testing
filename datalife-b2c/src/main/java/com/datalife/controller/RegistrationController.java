package com.datalife.controller;

import com.datalife.entities.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by supriya gondi on 11/19/2014.
 *
 *  Controller for handling User Registration related requests
 */
@Controller
@RequestMapping("/reg/")
public class RegistrationController {
    /**
     *
     * @param user
     * @param modelMap
     * @param result
     * @return view for Patient Registration
     */
    @RequestMapping(value = "patient", method = RequestMethod.GET)
    public String getPatientSignup(@ModelAttribute User user, ModelMap modelMap, BindingResult result) {
        return "patientRegistration";
    }

    @RequestMapping(value = "physican", method = RequestMethod.GET)
    public String getsDoctorSignup(@ModelAttribute User user, ModelMap modelMap, BindingResult result) {
        return "physicanRegistration";
    }

    /**
     *
     * @param user
     * @param modelMap
     * @param result
     * @return view for Clinic Registration
     */
    @RequestMapping(value = "clinic", method = RequestMethod.GET)
    public String getClinicSignup(@ModelAttribute User user, ModelMap modelMap, BindingResult result) {
        return "clinicRegistration";
    }


    @RequestMapping(value = "serviceprovider", method = RequestMethod.GET)
    public String getServiceProviderSignup(@ModelAttribute User user, ModelMap modelMap, BindingResult result) {
        return "serviceProviderReg";
    }


    @RequestMapping(value = "hospital", method = RequestMethod.GET)
    public String gethospitalReg(@ModelAttribute User user, ModelMap modelMap, BindingResult result) {
        return "surgeryReferral";
    }


    @RequestMapping(value = "secondOpinion", method = RequestMethod.GET)
    public String getSecondOpinionSignup(@ModelAttribute User user, ModelMap modelMap, BindingResult result) {
        return "secondOpinion";
    }


    @RequestMapping(value = "teleConsultation", method = RequestMethod.GET)
    public String getTeleConsultationSignup(@ModelAttribute User user, ModelMap modelMap, BindingResult result) {
        return "teleconsultation";
    }

    @RequestMapping(value = "lab", method = RequestMethod.GET)
    public String getLabSignup(@ModelAttribute User user, ModelMap modelMap, BindingResult result) {
        return "lab";
    }

    /**
     *
     * @param user
     * @param modelMap
     * @param result
     * @return view for Service Provider Registration
     */
    @RequestMapping(value = "referralDoctor", method = RequestMethod.GET)
    public String referralDoctor(@ModelAttribute User user, ModelMap modelMap, BindingResult result) {
        return "referralDoctorSignup";
    }

    @RequestMapping(value = "pharmacy", method = RequestMethod.GET)
    public String getPharmacySignup(@ModelAttribute User user, ModelMap modelMap, BindingResult result) {
        return "pharmacy";
    }
}
