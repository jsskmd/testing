package com.datalife.controller;

import com.datalife.entities.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller class that handles the requests for the application Clinic Dashboard.
 *
 * Created by supriya gondi on 2/1/2015.
 */
@Controller
@RequestMapping("/clinic/")
public class ClinicController {
    /**
     *
     * @param modelMap
     * @return Clinic Dashboard jsp page
     */
    @RequestMapping(value = "dashboard", method = RequestMethod.GET)
    public String getHomePage(ModelMap modelMap) {
        return "clinicDashboard";
    }

    @RequestMapping(value = "clinicdoctors", method = RequestMethod.GET)
    public String getClinicdoctors(@ModelAttribute User user,BindingResult result,ModelMap modelMap) {
        return "clinicdoctors";
    }
    /**
     *
     * @param modelMap
     * @return Doctors jsp page
     */
    @RequestMapping(value = "clinicDoctorPopup", method = RequestMethod.GET)
    public String getClinicDoctorPopup(@ModelAttribute User user,BindingResult result,ModelMap modelMap) {
        return "clinicDoctorPopup";
    }

    /**
     *
     * @param modelMap
     * @return Patients jsp page
     */
    @RequestMapping(value = "clinicpatientsPopup", method = RequestMethod.GET)
    public String getClinicpatientsPopup(ModelMap modelMap) {
        return "clinicpatientsPopup";
    }
    /**
     *
     * @param user
     * @param modelMap
     * @return Clinic Profile jsp page
     */
    @RequestMapping(value = "profile", method = RequestMethod.GET)
    public String getProfile(@ModelAttribute User user,BindingResult result,ModelMap modelMap) {
        return "clinicProfile";
    }

    /**
     *
     * @param modelMap
     * @return Clinic Settings jsp Page
     */
    @RequestMapping(value = "settings", method = RequestMethod.GET)
    public String getSettings(@ModelAttribute ClinicDoctors clinicDoctors,BindingResult result,ModelMap modelMap) {
        return "clinicSettings";
    }

    /**
     *
     * @param user
     * @param result
     * @param modelMap
     * @return Clinic Settings jsp Page
     */
    @RequestMapping(value = "getCDMainViewAppt", method = RequestMethod.GET)
    public String getAppointment(@ModelAttribute User user,@ModelAttribute ClinicDoctors clinicDoctors,BindingResult result,ModelMap modelMap) {
        return "viewCompleteAppt";
    }

    @RequestMapping(value = "preferrence", method = RequestMethod.GET)
    public String getPreferrence(@ModelAttribute User user,BindingResult result,ModelMap modelMap) {
        return "clinicpreferrence";
    }


}
