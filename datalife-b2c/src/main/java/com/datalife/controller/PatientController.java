package com.datalife.controller;

import com.datalife.entities.*;
import com.datalife.repositories.*;
import com.datalife.services.UtilityServices;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Set;

/**
 * Controller class that handles the requests for the application Patient dashboard.
 * <p/>
 * Created by supriya gondi on 10/27/2014.
 */
@Controller
@RequestMapping("/patient/")
public class PatientController {
  @Autowired
  UserRepository userRepository;


  @Autowired
  CommonRepository commonRepository;

  @Autowired
  UserEmergencyRepository userEmergencyRepository;

  @Autowired
  UtilityServices utilityServices;

  /**
   * @param modelMap
   * @return view i.e Patient Dashboard, if patient logged in successfully
   */
  @RequestMapping(value = "dashboard", method = RequestMethod.GET)
  public String getDashboard(ModelMap modelMap) {
    return "patientDashboard";
  }

  /**
   * @param modelMap
   * @return view i.e Patient Demographics
   */
  @RequestMapping(value = "profile", method = RequestMethod.GET)
  public String getDemographics(@ModelAttribute User user, @ModelAttribute UserContactInfo userContactInfo, @ModelAttribute UserEmergencyInfo userEmergencyInfo, BindingResult result, ModelMap modelMap) {
    return "patientProfile";
  }

  @RequestMapping(value = "access", method = RequestMethod.GET)
  public String getaccess(@ModelAttribute User user, @ModelAttribute UserContactInfo userContactInfo, @ModelAttribute UserEmergencyInfo userEmergencyInfo, BindingResult result, ModelMap modelMap) {
    return "clinicPatient";
  }

  /**
   * @param vitals
   * @param result
   * @param modelMap
   * @return view i.e Patient Vitals
   */
  @RequestMapping(value = "vitals", method = RequestMethod.GET)
  public String getHistory(@ModelAttribute Vitals vitals, BindingResult result, ModelMap modelMap) {
    return "vitals";
  }

  /**
   * @param modelMap
   * @return view for user to upload Medical Records
   */
  @RequestMapping(value = "records", method = RequestMethod.GET)
  public String getRecords(ModelMap modelMap) {
    return "patientRecords";
  }

  @RequestMapping(value = "idCard/{userId}", method = RequestMethod.GET)
  public String getIdCard(@PathVariable(value = "userId") Long userId, ModelMap modelMap) {
    User u = userRepository.findPatient(userId);
    modelMap.put("user", u);
    modelMap.put("name", u.getUserDetails().getFirstName() + " " + u.getUserDetails().getLastName());
    List<UserEmergencyInfo> info = userEmergencyRepository.findById(u.getUserId());
    if (info != null && !info.isEmpty()) {
      String mobilePhone = info.get(0).getMobileNumber();
      String homePhone = info.get(0).getHomePhone();
      if (StringUtils.isNotBlank(mobilePhone)) {
        modelMap.put("number", mobilePhone);
      } else if (StringUtils.isNotBlank(homePhone)) {
        modelMap.put("number", homePhone);
      } else {
        modelMap.put("number", "-");
      }
    }

    if (u.getImageThumbnailFileName() != null && !u.getImageThumbnailFileName().isEmpty()) {
      modelMap.addAttribute("imageUrl", utilityServices.getMessage("User.profileImage.Url") + u.getUserId());
    } else {
      modelMap.addAttribute("imageUrl", utilityServices.getMessage("User.DefaultImage.Url"));
    }
    modelMap.put("bloodGroup", commonRepository.getBloodgroupById(u.getUserDetails().getBloodGroup()));
    return "test";

  }

  @RequestMapping(value = "bills", method = RequestMethod.GET)
  public String getBills(ModelMap modelMap) {
    return "bills";
  }

  @RequestMapping(value = "history", method = RequestMethod.GET)
  public String getHistory(ModelMap modelMap) {
    return "history";
  }

  /**
   * @param modelMap
   * @return view i.e for to display patient details
   */
  @RequestMapping(value = "appointments/{userId}", method = RequestMethod.GET)
  public String pFindaDoctor(@PathVariable(value = "userId") Long userId, ModelMap modelMap) {

    User u = userRepository.findPatient(userId);
    modelMap.put("user", u);
    return "appointments";
  }

  @RequestMapping(value = "viewAppointment", method = RequestMethod.GET)
  public String viewAppointment(ModelMap modelMap) {
    return "viewAllPatientAppt";
  }

  @RequestMapping(value = "viewLabOrders", method = RequestMethod.GET)
  public String viewLabOrders(ModelMap modelMap) {
    return "viewLabOrder";
  }

  @RequestMapping(value = "shareRecordsPopup", method = RequestMethod.GET)
  public String shareRecordsPopup(ModelMap modelMap) {
    return "shareRecordsPopup";
  }

  @RequestMapping(value = "shareRecords", method = RequestMethod.GET)
  public String shareRecords(ModelMap modelMap) {
    return "shareRecords";
  }

}
