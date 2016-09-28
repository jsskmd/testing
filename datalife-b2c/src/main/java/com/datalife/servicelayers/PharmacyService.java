package com.datalife.servicelayers;


import com.datalife.entities.*;
import com.datalife.repositories.PharmacyRequestRepository;
import com.datalife.repositories.UploadFileRepository;
import com.datalife.repositories.UserRepository;
import com.datalife.services.CommonServices;
import com.datalife.services.DateService;
import com.datalife.services.UtilityServices;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by barath on 5/14/2016.
 */
@RestController
@RequestMapping(value = "/api/pharmacy/")
public class PharmacyService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UploadFileRepository uploadFileRepository;

    @Autowired
    UtilityServices utilityServices;

    @Autowired
    PharmacyRequestRepository pharmacyRequestRepository;

    protected static final Logger logger = Logger.getLogger(PharmacyService.class);

    @RequestMapping(value = "fetch/prescription", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> getPrescriptionsFiles(@RequestBody User user, BindingResult result, ModelMap modelMap) {

        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "No Records Found !!!";

        if (user.getUserId() != null) {
            Long userId = user.getUserId();
            User u = userRepository.findOne(userId);
            if (u != null) {
                List<UploadFile> fileList = uploadFileRepository.getAllFilesByTypeAndId(user.getUserId(), user.getSendType());
                if(fileList.size() > 0){
                    for (UploadFile file : fileList) {
                        if (file.getClinicId() != null) {
                            file.setUser(null);
                            file.setEncounter(null);
                            if(file.getServiceRequests() != null){
                                file.getServiceRequests().setUploadFile(null);
                            }
                        }
                        file.setVurl(utilityServices.getMessage("Application.Url") + "/api/user/records/view/" + file.getFileId() + "/" + userId);
                    }
                    response.put("requestedFiles", fileList);
                    status = HttpStatus.OK;
                }else{
                    status = HttpStatus.CONTINUE;
                }
            }
        }
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }


    @RequestMapping(value = "fetchReq/ByProviderId", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> getServiceReqByProviderId(@RequestBody ServiceRequests serviceRequests, BindingResult result, ModelMap modelMap) {

        logger.info("start of path api/lab/fetchReq/ByProviderId : "+serviceRequests);
        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        if(serviceRequests.getProviderId() != null){

            SimpleDateFormat dateFormat =new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date date = new DateTime().toDate();
            String s = dateFormat.format(date);
            java.util.Date mydate = DateService.stringToDateConversion(s);

            List<PharmacyRequests> serviceRequestsList = CommonServices.getPharmacyRequests(pharmacyRequestRepository.getOrdersByProviderId(serviceRequests.getProviderId(), mydate));
            response.put("requests",serviceRequestsList);
            String fileUrl = utilityServices.getMessage("Application.Url") + "/api/user/records/view/";
            response.put("viewFileUrl",fileUrl);
            DateTime dateTime = new DateTime().minusDays(90);
            response.put("maxDate",DateService.dateToStringConversion(dateTime.toDate()));
            response.put("curDate",new DateTime().toDate().toString());
            status = HttpStatus.OK;
        }
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        logger.info("end of path api/lab/labReq/ByProviderId : "+response);
        return response;
    }

    @RequestMapping(value = "search/pharmRequest", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> searchPharmRequest(@RequestBody ServiceRequests serviceRequests, BindingResult result, ModelMap modelMap) {

        logger.info("start of path api/lab/getLabReqBasedOnDate : "+serviceRequests);
        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        if(serviceRequests.getProviderId() != null){
            Date fromDate = DateService.stringToDateConversion(serviceRequests.getPharmacyRequests().getFromDate());
            Date toDate = DateService.stringToDateConversion(serviceRequests.getPharmacyRequests().getToDate());
            List<PharmacyRequests> serviceRequestsList = CommonServices.getPharmacyRequests(pharmacyRequestRepository.getOrdersByProviderIdBasedOnDate(serviceRequests.getProviderId(), fromDate, toDate));
            if(serviceRequestsList.size() > 0){
                response.put("requests",serviceRequestsList);
                String fileUrl = utilityServices.getMessage("Application.Url") + "/api/user/records/view/";
                response.put("viewFileUrl",fileUrl);
                status = HttpStatus.OK;
            }else{
                response.put("message","Records not found in the database");
                status = HttpStatus.CONTINUE;
            }
        }
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        logger.info("end of path api/lab/getLabReqBasedOnDate : "+response);
        return response;
    }
}
