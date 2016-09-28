package com.datalife.servicelayers;

import com.datalife.entities.*;
import com.datalife.repositories.*;
import com.datalife.services.*;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.*;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by barath on 5/14/2016.
 */
@RestController
@RequestMapping(value = "/api/lab/")
public class LabService {


    @Autowired
    LabRequestsRepository labRequestsRepository;

    @Autowired
    UtilityServices utilityServices;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AlfrescoAuthDetails alfrescoAuthDetails;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    UploadFileRepository uploadFileRepository;

    @Autowired
    ServiceRequestsRepository serviceRequestsRepository;

    @Autowired
    LabServiceProviderRepository labServiceProviderRepository;

    @Autowired
    ClinicInfoRepository clinicInfoRepository;

    @Autowired
    CommonRepository commonRepository;


    protected static final Logger logger = Logger.getLogger(LabService.class);

    @RequestMapping(value = "fetchReq/ByProviderId", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> getServiceReqByProviderId(@RequestBody ServiceRequests serviceRequests, BindingResult result, ModelMap modelMap) {

        logger.info("start of path api/lab/labReq/ByProviderId : "+serviceRequests);
        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        if(serviceRequests.getProviderId() != null){

            SimpleDateFormat dateFormat =new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date date = new DateTime().toDate();
            String s = dateFormat.format(date);
            java.util.Date mydate = DateService.stringToDateConversion(s);

            List<LabRequests> serviceRequestsList = CommonServices.getLabRequests(labRequestsRepository.getOrdersByProviderId(serviceRequests.getProviderId(),mydate));
            response.put("requests",serviceRequestsList);
            String fileUrl = utilityServices.getMessage("Application.Url") + "/api/user/records/view/";
            DateTime dateTime = new DateTime().minusDays(90);
            response.put("viewFileUrl",fileUrl);
            response.put("maxDate",DateService.dateToStringConversion(dateTime.toDate()));
            response.put("curDate",new DateTime().toDate().toString());
            status = HttpStatus.OK;
        }
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        logger.info("end of path api/lab/labReq/ByProviderId : "+response);
        return response;
    }

    @RequestMapping(value = "search/labOrder", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> getLabReqBasedOnDate(@RequestBody ServiceRequests serviceRequests, BindingResult result, ModelMap modelMap) {

        logger.info("start of path api/lab/search/labOrder : "+serviceRequests);
        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        if(serviceRequests.getProviderId() != null){

            List<LabRequests> serviceRequestsList = CommonServices.getLabRequests(labRequestsRepository.serachByOrderIdAndPatDetail(serviceRequests.getProviderId(),serviceRequests.getOrderId(),serviceRequests.getMobilePhone()));
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


    @RequestMapping(value = "fetch/labOrder", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> getlabOrderFiles(@RequestBody User user, BindingResult result, ModelMap modelMap) {

        logger.info("start of path api/lab/fetch/labOrder : "+user);
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
                            file.setUser(null);
                            file.setEncounter(null);
                            if(file.getServiceRequests() != null){
                                file.getServiceRequests().setUploadFile(null);
                            }
                        file.setVurl(utilityServices.getMessage("Application.Url") + "/api/user/records/view/" + file.getFileId() + "/" + userId);
                    }
                    response.put("requestedFiles", fileList);
                    status = HttpStatus.OK;
                }else{
                    message= utilityServices.getMessage("Patient.NoFile.MESSAGE");
                    status = HttpStatus.CONTINUE;
                }
            }
        }
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        logger.info("end of path api/lab/fetch/labOrder : "+response);
        return response;
    }


    @RequestMapping(value = "upload", method = RequestMethod.POST, produces = "application/json")
    public Map<String, Object> fileUpload(@ModelAttribute UploadFile uploadFile,@ModelAttribute MultiFileUpload multiFileUpload, BindingResult result, ModelMap modelMap) {

        logger.info("start of path /api/lab/upload "+uploadFile);

        Map<String, Object> files = new HashMap<String, Object>();
        List<UploadFile> list = new LinkedList<UploadFile>();
        String adminTicket = null;
        String ticket = null;
        PostMethod mPost = null;
        HttpClient client = new HttpClient();

            try {
                Long userId = uploadFile.getPatientId();
                User user = userRepository.findOne(userId);
                User provider = userRepository.findOne(uploadFile.getProviderId());

                //get the ticket form patient user
                ticket = alfrescoAuthDetails.getAlfrescoConnectionTicket(user.getUserName(), user.getPassword());
                String leftQuota = userRepository.getUserData(user, ticket);
                logger.debug("leftQuota of user :"+userId+" is " +leftQuota);
                //check the patient-user has enough space to store the report
                boolean quota = leftQuota.equals("exceeded");

                    //get the url from properties file
                    String url = this.messageSource.getMessage("alfresco.upload.files", null, "Default alfresco upload files", null);

                    adminTicket = alfrescoAuthDetails.getAlfrescoConnectionTicket(provider.getUserName(), provider.getPassword());

                    String uploadUrl = url + ticket;

                    for (MultipartFile mpf : multiFileUpload.getMultiUploadedFileList()) {
                        UploadFile image = new UploadFile();

                        String contentType = mpf.getContentType();
                        String fileName = mpf.getOriginalFilename().replaceAll("\\s", "");
                        String ext = fileName.substring((fileName.lastIndexOf('.') + 1));
                        String file = "record" + userId + "_" + UUID.randomUUID() + "." + ext;
                        mPost = new PostMethod(uploadUrl);
                        InputStream is = mpf.getInputStream();
                        byte[] bytes = IOUtils.toByteArray(is);
                        ByteArrayPartSource byteArrayPartSource = new ByteArrayPartSource(file, bytes);

                        String destinationUrl = user.getUserParentDir();
                        Part[] parts = {
                                new FilePart("filedata", byteArrayPartSource, contentType, null),
                                new StringPart("filename", file),
                                new StringPart("description", "description"),
                                new StringPart("destination", destinationUrl),
                                new StringPart("thumbnails", "yes"),
                        };

                        mPost.setRequestEntity(new MultipartRequestEntity(parts, mPost.getParams()));
                        int statusCode1 = client.executeMethod(mPost);
                        logger.debug("after file upload to alfresco status is :"+statusCode1);

                        if (statusCode1 == org.apache.commons.httpclient.HttpStatus.SC_OK) {

                            String nodeRef = mPost.getResponseBodyAsString();
                            // Converting json to java object
                            AlfrescoAuthTicket authTicket = new ObjectMapper().readValue(nodeRef, AlfrescoAuthTicket.class);
                            String nodeRefThumnails = authTicket.getNodeRef();
                            String[] node = nodeRefThumnails.split("/");

                            //grant permission to user as Consumer to the uploaded files
                            alfrescoAuthDetails.grantPermission(adminTicket, user.getUserName(), node[3], "Consumer");

                            //fetch the encounter based on orderId to set the encounter date and encounterId
                            UploadFile prescriptionFile = uploadFileRepository.findByOrderId(uploadFile.getOrderId());

                            image.setFileName(file);
                            image.setFileType(uploadFile.getFileType());
                            image.setContentType(contentType);
                            image.setLastModifiedDate(new Date());
                            image.setUser(user);
                            image.setUrl(node[3]);
                            image.setEncounterDate(prescriptionFile.getEncounterDate());
                            image.setEncounter(prescriptionFile.getEncounter());
                            if(quota){
                                image.setShare(false);
                            }else{
                                image.setShare(true);
                            }
                            uploadFileRepository.save(image);
                            logger.info("save the uploaded file data to records table is success");
                            //get the servicesRequest based on orderId to update the status
                            LabRequests labRequests = serviceRequestsRepository.getLabRequestOnOrderId(uploadFile.getOrderId());

                            //After labreport file uploaded to records table get generated fileId and adding to labRequests
                            labRequests.setLabReportFileId(image.getFileId());
                            labRequests.setCompletionDate(new Date());
                            labRequests.setStatus(RequestStatus.COMPLETED);
                            labRequests.setAmount(uploadFile.getAmount());
                            labRequestsRepository.save(labRequests);
                            image.setError("Uploaded Successfully");
                            image.setEncounter(null);
                            list.add(image);
                            files.put("files", list);
                        } else {
                            image.setError("Oops upload fails,Please try again later");
                            list.add(image);
                            files.put("files", list);
                        }
                    }

            } catch (Exception e) {
                e.printStackTrace();
                logger.error(e);
            } finally {
                alfrescoAuthDetails.logoutTicket(adminTicket);
                alfrescoAuthDetails.logoutTicket(ticket);
                if (mPost != null) {
                    mPost.releaseConnection();
                }
            }
        logger.info("end of path /api/lab/upload "+files);
        return files;
        }

   /* @RequestMapping(value = "basicInfo", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> getBasicDetails(@RequestBody User user, BindingResult result, ModelMap modelMap) {

        logger.info("start of path /api/lab/basicInfo "+user);
        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";
        Long userId = user.getUserId();
        User u = userRepository.findOne(userId);

        if (u != null) {

            if (u.getServiceProvider() != null) {
                response.put("sp", CommonServices.getOnlyProviderInfo(u.getServiceProvider()));
            }

            response.put("user", CommonServices.getOnlyUser(u));
            status = HttpStatus.OK;
        }


        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);

        logger.info("end of path api/lab/basicInfo : "+response);

        return response;

    }*/

}
