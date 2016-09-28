package com.datalife.controller;

import com.datalife.entities.DoctorInfo;
import com.datalife.entities.ServiceProvider;
import com.datalife.entities.User;
import com.datalife.entities.UserContactInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by barath on 8/8/2016.
 */

@Controller
@RequestMapping("/lab/")
public class LabController {

    @RequestMapping(value = "patientSearch", method = RequestMethod.GET)
    public String getHomePage(ModelMap modelMap) {
        return "labPatientSearch";
    }

/*    @RequestMapping(value = "labProfile", method = RequestMethod.GET)
    public String getLabProfile(ModelMap modelMap) {
        return "labProfile";
    }*/

    @RequestMapping(value = "labProfile", method = RequestMethod.GET)
    public String demographics(@ModelAttribute User user, @ModelAttribute UserContactInfo userContactInfo, @ModelAttribute ServiceProvider serviceProvider, BindingResult result, ModelMap modelMap) {
        return "labProfile";
    }
    @RequestMapping(value = "sharePrescription", method = RequestMethod.GET)
    public String getsharePrescription(ModelMap modelMap) {
        return "shareRecordWithSp";
    }

    @RequestMapping(value = "labOrderStatus", method = RequestMethod.GET)
    public String getlabOrderStatus(ModelMap modelMap) {
        return "labOrderStatus";
    }


}
