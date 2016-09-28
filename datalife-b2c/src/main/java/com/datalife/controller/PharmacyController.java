package com.datalife.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by barath on 8/17/2016.
 */

@Controller
@RequestMapping("/pharmacy/")
public class PharmacyController {

    @RequestMapping(value = "orderStatus", method = RequestMethod.GET)
    public String getsharePrescription(ModelMap modelMap) {
        return "pharmacyOrderStatus";
    }


}
