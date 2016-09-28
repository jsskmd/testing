package com.datalife.servicelayers;

import com.datalife.entities.User;
import com.datalife.repositories.UserRepository;
import com.datalife.services.AlfrescoAuthDetails;
import com.datalife.services.CommonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by barath on 5/14/2016.
 */

@RestController
@RequestMapping(value = "/services/common/")
public class ProviderCommonService {

    @Autowired
    AlfrescoAuthDetails alfrescoAuthDetails;

    @Autowired
    UserRepository userRepository;


    @RequestMapping(value = "getUserAlfrescoTicket", method = RequestMethod.POST,  headers = {"content-type=application/json"}, consumes = "application/json",produces = "application/json")
    public Map<String, Object> getUserServiceRequest(@RequestBody User user, BindingResult result, ModelMap modelMap) {
        Map<String, Object> response = new HashMap<>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";
        if(user.getUserId() != null){
            User user1 = CommonServices.getOnlyUser(userRepository.findOne(user.getUserId()));
            String ticket = alfrescoAuthDetails.getAlfrescoConnectionTicket(user1.getUserName(), user1.getPassword());
            response.put("ticket",ticket);
            status = HttpStatus.OK;
        }
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

}
