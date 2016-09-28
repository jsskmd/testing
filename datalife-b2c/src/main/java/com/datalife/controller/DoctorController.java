
package com.datalife.controller;

import com.datalife.entities.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * Controller class that handles the Doctor Dashboard related requests.
 * Created by supriya gondi on 11/3/2014.
 */

@Controller
@RequestMapping(value = "/doctor/")
public class DoctorController {

    /**
     * @param user
     * @param result
     * @param modelMap
     * @return view i.e Doctor Dashboard after user successfully logged in to Datalife EMR.
     */
    @RequestMapping(value = "dashboard", method = RequestMethod.GET)
    public String getDashboard(@ModelAttribute User user, BindingResult result, ModelMap modelMap) {
        return "doctorDashboard";
    }


    @RequestMapping(value = "SearchReferralPatient", method = RequestMethod.GET)
    public String SearchReferralPatient(ModelMap modelMap) {
        return "refDoctorPatientSearch";
    }


    @RequestMapping(value = "patientSearch", method = RequestMethod.GET)
    public String patientSearch(ModelMap modelMap) {
        return "patientSearch";
    }


    /**
     * @param user
     * @param result
     * @param modelMap
     * @return Doctor demographics jsp page
     */
    @RequestMapping(value = "profile", method = RequestMethod.GET)
    public String demographics(@ModelAttribute User user, @ModelAttribute UserContactInfo userContactInfo, @ModelAttribute DoctorInfo doctorInfo, BindingResult result, ModelMap modelMap) {
        return "doctorprofile";
    }

    @RequestMapping(value = "patients", method = RequestMethod.GET)
    public String searchForPatient(ModelMap modelMap) {
        return "doctorPatients";
    }



    @RequestMapping(value = "physicianPatient", method = RequestMethod.GET)
    public String serachPatient(ModelMap modelMap) {
        return "physicianPatientSearch";
    }

    @RequestMapping(value = "addPatient", method = RequestMethod.GET)
    public String addPatient(ModelMap modelMap) {
        return "physicianAddPatient";
    }
    /**
     * @param encounter
     * @param result
     * @param modelMap
     * @return view i.e. Encounter Page if user clicks on Encounter Link
     */
    @RequestMapping(value = "encounter", method = RequestMethod.GET)
    public String getEncounter(@ModelAttribute Encounter encounter, BindingResult result, ModelMap modelMap) {
        return "h&p";
    }
    @RequestMapping(value = "consultation", method = RequestMethod.GET)
    public String getConsultation(@ModelAttribute Encounter encounter, BindingResult result, ModelMap modelMap) {
        return "consultation";
    }

    @RequestMapping(value = "settings", method = RequestMethod.GET)
    public String getSettings(@ModelAttribute User user, @ModelAttribute ClinicDoctors clinicDoctors, BindingResult result, ModelMap modelMap) {
        return "doctorSettings";
    }


    @RequestMapping(value = "spimage", method = RequestMethod.GET)
    public String spimage(@ModelAttribute Vitals vitals, BindingResult result, ModelMap modelMap) {
        return "specialtyImage";
    }


    @RequestMapping(value = "physicalExamination", method = RequestMethod.GET)
    public String physicalExamination(@ModelAttribute PhysicalExamination physicalExamination, BindingResult result, ModelMap modelMap) {
        return "PEdefault";
    }

    @RequestMapping(value = "livedictation", method = RequestMethod.GET)
    public String livedictation(ModelMap modelMap) {
        return "liveDictation";
    }
}
