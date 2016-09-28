package com.datalife.servicelayers;

import com.datalife.controller.EmailController;
import com.datalife.entities.*;
import com.datalife.repositories.*;
import com.datalife.services.AlfrescoAuthDetails;
import com.datalife.services.UtilityServices;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * Created by dscblpo on 1/23/2016.
 */
@RestController
@RequestMapping(value = "/api/user/provider/")
public class RegistrationService {

    private static final Logger logger = Logger.getLogger(RegistrationService.class);

    @Autowired
    ProviderDetailRepository providerDetailRepository;

    @Autowired
    MessageSource messageSource;

    @RequestMapping(value = "submitDetails", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> submitDetails(@RequestBody ProviderDetails providerDetails, BindingResult result, ModelMap modelMap) {
       logger.info("submitDetails method start :"+providerDetails);
        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
       try{
           providerDetailRepository.save(providerDetails);
           if(StringUtils.isNotBlank(providerDetails.getContactPerson())){
               response.put("name", providerDetails.getContactPerson());
           }else {
               response.put("name",providerDetails.getFirstName()+" "+providerDetails.getLastName());
           }
           status = HttpStatus.OK;
           response.put("message",this.messageSource.getMessage("ServiceProvider.Registration.Success", null, "ServiceProvider Registration Success", null));

       }catch (Exception e){
           e.printStackTrace();
            logger.error(e);
           status = HttpStatus.BAD_REQUEST;
       }
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        logger.info("End of method submitDetails :"+response);
        return response;
    }
}
