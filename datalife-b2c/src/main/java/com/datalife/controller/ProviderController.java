package com.datalife.controller;

import com.datalife.repositories.CommonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

/**
 * Created by barath on 1/21/2016.
 */
@Controller
@RequestMapping("/service/")
public class ProviderController {

    @Autowired
    CommonRepository commonRepository;

   /* @RequestMapping(value = "home", method = RequestMethod.GET)
    public String getHomePage(ModelMap modelMap) {
        return "srHome";
    }

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String getIndexPage(ModelMap modelMap) {
        return "srIndex";
    }*/

   /* @RequestMapping(value = "pharmacy", method = RequestMethod.GET)
    public String getPharmacyPage(ModelMap modelMap) {
        return "srpharmacy";
    }*/

    @RequestMapping(value = "lab", method = RequestMethod.GET)
    public String getLabPage(ModelMap modelMap) {
        return "srlab";
    }

    /*@RequestMapping(value = "rehab", method = RequestMethod.GET)
    public String getrehab(ModelMap modelMap) {
        return "srrehab";
    }*/

    @RequestMapping(value = "secondOpinion", method = RequestMethod.GET)
    public String getSecondOpinionPage(ModelMap modelMap) {
        return "srSecondOpinion";
    }

    @RequestMapping(value = "teleConsultation", method = RequestMethod.GET)
    public String getTeleConsultationPage(ModelMap modelMap) {
        return "srTeleConsultation";
    }

    @RequestMapping(value = "surgeryReferral", method = RequestMethod.GET)
    public String getSurgeryReferralPage(ModelMap modelMap) {
        Date date = (Date) commonRepository.getDate();
        modelMap.addAttribute("curDate",date);
        return "srSurgeryReferral";
    }

/*    @RequestMapping(value = "medicalTransport", method = RequestMethod.GET)
    public String getMedicalTransportPage(ModelMap modelMap) {
        return "srMedicalTransport";
    }

    @RequestMapping(value = "medicalTourism", method = RequestMethod.GET)
    public String getMedicalTourismPage(ModelMap modelMap) {
        return "srMedicalTourism";
    }

    @RequestMapping(value = "providerHome", method = RequestMethod.GET)
    public String getProviderHomePage(ModelMap modelMap) {
        return "providerHome";
    }

    @RequestMapping(value = "pharmacyProvider", method = RequestMethod.GET)
    public String getPharmacyproviderPage(ModelMap modelMap) {
        return "pharmacyProvider";
    }

    @RequestMapping(value = "labProvider", method = RequestMethod.GET)
    public String getLabproviderPage(ModelMap modelMap) {
        return "labProvider";
    }

    @RequestMapping(value = "secondOpinionProvider", method = RequestMethod.GET)
    public String getSecondOpinionProviderPage(ModelMap modelMap) {
        return "secondOpinionProvider";
    }

    @RequestMapping(value = "teleConsultationProvider", method = RequestMethod.GET)
    public String getTeleConsultationProviderPage(ModelMap modelMap) {
        return "teleConsultationProvider";
    }

    @RequestMapping(value = "surgeryReferralProvider", method = RequestMethod.GET)
    public String getSurgeryReferralProviderPage(ModelMap modelMap) {

        modelMap.put("country",commonRepository.getCountries());
        modelMap.put("recordSpeciality",commonRepository.getRecordSpecialities());
        return "surgeryReferralProvider";
    }

    @RequestMapping(value = "medicalTransportProvider", method = RequestMethod.GET)
    public String getMedicalTransportProviderPage(ModelMap modelMap) {
        return "medicalTransportProvider";
    }*/
}
