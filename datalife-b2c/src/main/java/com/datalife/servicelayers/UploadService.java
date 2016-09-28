package com.datalife.servicelayers;

import com.datalife.entities.*;
import com.datalife.repositories.*;
import com.datalife.services.*;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * Controller class that handles the uploading files related requests
 * <p/>
 * Created by supriya gondi on 12/4/2014.
 */
@RestController
@RequestMapping(value = "/api/user/records/")
public class UploadService {

    /**
     * refernce to uploadFileRepository
     */
    @Autowired
    UploadFileRepository uploadFileRepository;
    /**
     * reference to userDetailsRepository
     */
    @Autowired
    UserDetailsRepository userDetailsRepository;


    @Autowired
    BillRepository billRepository;
    /**
     * reference to userRepository
     */
    @Autowired
    UserRepository userRepository;

    @Autowired
    AlfrescoAuthDetails alfrescoAuthDetails;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    UtilityServices utilityServices;

    @Autowired
    CommonService commonService;

    @Autowired
    ClinicInfoRepository clinicInfoRepository;

    @Value("C:\\Users\\DATASCRIBE\\IdeaProjects\\cando\\src\\main\\webapp\\resources\\profileImages\\")
    private String uploadDirectory;

    /**
     * This method is store uploaded files in specifies directory and save files information in database
     * @param result
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "upload", method = RequestMethod.POST, produces = "application/json")
    public Map<String, Object> handleFileUpload(@ModelAttribute UploadFile uploadFile,@ModelAttribute MultiFileUpload multiFileUpload, BindingResult result, ModelMap modelMap) {
        Map<String, Object> files = new HashMap<String, Object>();
        Long userId = uploadFile.getUser().getUserId();
        User user = userRepository.findOne(userId);
        List<UploadFile> list = new LinkedList<UploadFile>();
        String adminTicket = null;
        if ( user.getAlfrescoUrl()!= null && user.getUserParentDir()!= null) {

            //get the ticket form patient user
            String ticket = alfrescoAuthDetails.getAlfrescoConnectionTicket(user.getUserName(), user.getPassword());
            PostMethod mPost = null;

            HttpClient client = new HttpClient();
            //check the patient-user has enough space to store the report

            try {
                String leftQuota = userRepository.getUserData(user, ticket);
                if (!leftQuota.equals("exceeded")) {

                    //get the url from properties file
                    String url = this.messageSource.getMessage("alfresco.upload.files", null, "Default alfresco upload files", null);
                    String retriveUrl = this.messageSource.getMessage("alfresco.retrive.files", null, "Default alfresco retrive files", null);
                    String thumbnailUrl = this.messageSource.getMessage("alfresco.thumnail.files", null, "Default alfresco thumnail files", null);
                    String thumbnail = this.messageSource.getMessage("alfresco.thumnailUrl", null, "Default alfresco thumnailUrl", null);
                    String downloadUrl = this.messageSource.getMessage("alfresco.downloadUrl", null, "Default alfresco downloadUrl", null);
                    String view = this.messageSource.getMessage("alfresco.viewInBrowser", null, "Default alfresco viewInBrowser", null);
                   /* String viewIn = this.messageSource.getMessage("alfresco.fetchContent", null, "Default alfresco fetchContent", null);*/

                    adminTicket = alfrescoAuthDetails.getAlfrescoConnectionTicket(this.messageSource.getMessage("alfresco.admin.userName", null, "Default alfresco admin userName", null), this.messageSource.getMessage("alfresco.admin.password", null, "Default alfresco admin password", null));
                    MyUserDetailsService.getUserFromSession().setAlfresAuthTicket(ticket);
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

                        if (statusCode1 == org.apache.commons.httpclient.HttpStatus.SC_OK) {
                            String nodeRef = mPost.getResponseBodyAsString();
                            // Converting json to java object
                            AlfrescoAuthTicket authTicket = new ObjectMapper().readValue(nodeRef, AlfrescoAuthTicket.class);
                            String nodeRefThumnails = authTicket.getNodeRef();
                            String[] node = nodeRefThumnails.split("/");

                            //grant permission to user as Consumer to the uploaded files
                            alfrescoAuthDetails.grantPermission(adminTicket, user.getUserName(), node[3], "Consumer");

                            image.setFileName(file);
                            image.setFileType(uploadFile.getFileType());
                            image.setSpeciality(uploadFile.getSpeciality());
                            image.setChiefComplaint(uploadFile.getChiefComplaint());
                            image.setDiagnosis(uploadFile.getDiagnosis());
                            Date date = DateService.stringToDate(uploadFile.getEncDate());
                            image.setEncounterDate(date);
                            image.setContentType(contentType);
                            image.setLastModifiedDate(new Date());
                            image.setUser(user);
                            image.setUrl(node[3]);
                            image.setShare(true);
                            uploadFileRepository.save(image);
                            image.setVurl(view + node[3] + "/content/" + file + "?c=force");
                            image.setVdownloadUrl(retriveUrl + node[3] + "/" + file + downloadUrl);
                            image.setVthumbnailUrl(thumbnailUrl + node[3] + thumbnail);
                            image.setFileName(fileName);
                            image.setError("Uploaded Successfully");
                            image.setUser(null);
                            list.add(image);
                            files.put("files", list);
                        } else {
                            image.setDataSize(leftQuota);
                            image.setError("You have exceeded your allotted space");
                            list.add(image);
                            files.put("files", list);
                        }
                    }

                } else {
                    UploadFile image = new UploadFile();
                    image.setDataSize(leftQuota);
                    image.setError("You have exceeded your allotted space");
                    list.add(image);
                    files.put("files", list);
                }

      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        alfrescoAuthDetails.logoutTicket(adminTicket);
        if (mPost != null) {
          mPost.releaseConnection();
        }
      }
    } else {
      UploadFile image = new UploadFile();
      image.setError("Before Uploading file ensure that Email and Mobile is Verified");
      list.add(image);
      files.put("files", list);
    }
    return files;
  }

  @RequestMapping(value = "saveBill", method = RequestMethod.POST, produces = "application/json")
  public Map<String, Object> saveBill(@RequestBody Bills bills, BindingResult result, ModelMap modelMap) {

    Map<String, Object> response = new HashMap<String, Object>();
    String message = "Not saved, try again !!!";
    HttpStatus status = HttpStatus.BAD_REQUEST;
    if (bills != null && bills.getUser() != null && bills.getUser().getUserId() != null) {
      Date date = DateService.stringToDateConversion(bills.getBillDate());
      bills.setDate(date);
      billRepository.save(bills);
      status = HttpStatus.OK;
      message = "Saved Successfully !";
    }
    response.put("statusCode", status.value());
    response.put("Status", status.getReasonPhrase());
    response.put("message", message);
    return response;
  }

  @RequestMapping(value = "saveBills", method = RequestMethod.POST, produces = "application/json")
  public Map<String, Object> saveBills(@ModelAttribute Bills bill, @ModelAttribute MultipartFile
      multipartFile, BindingResult result, ModelMap modelMap) {

    Map<String, Object> response = new HashMap<>();
    String message = "Not saved, try again !!!";
    HttpStatus status = HttpStatus.BAD_REQUEST;
    Bills bills = billRepository.findOne(bill.getId());
    User user = userRepository.findOne(bills.getUser().getUserId());
    if (user != null && user.isEnabled()) {

      String ticket = MyUserDetailsService.getUserFromSession().getAlfresAuthTicket();
      if (!alfrescoAuthDetails.isTicketValid(ticket)) {
        //get the ticket form patient user
        ticket = alfrescoAuthDetails.getAlfrescoConnectionTicket(user.getUserName(), user.getPassword());
      }

      PostMethod mPost = null;

      HttpClient client = new HttpClient();
      //check the patient-user has enough space to store the report

      try {
        String leftQuota = userRepository.getUserData(user, ticket);
        if (!leftQuota.equals("exceeded")) {

          String adminTicket = null;

          //get the url from properties file
          String url = this.messageSource.getMessage("alfresco.upload.files", null, "Default alfresco upload files", null);

          adminTicket = alfrescoAuthDetails.getAlfrescoConnectionTicket("admin", "admin");
          MyUserDetailsService.getUserFromSession().setAlfresAuthTicket(ticket);
          String uploadUrl = url + ticket;

          String contentType = multipartFile.getContentType();
          String fileName = multipartFile.getOriginalFilename().replaceAll("\\s", "");
          String ext = fileName.substring((fileName.lastIndexOf('.') + 1));
          String file = "bill" + bill.getId() + "." + ext;
          mPost = new PostMethod(uploadUrl);
          InputStream is = multipartFile.getInputStream();
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

          if (statusCode1 == org.apache.commons.httpclient.HttpStatus.SC_OK) {
            String nodeRef = mPost.getResponseBodyAsString();

            //check ticket is valid
            if (!alfrescoAuthDetails.isTicketValid(ticket)) {
              ticket = alfrescoAuthDetails.getAlfrescoConnectionTicket(user.getUserName(), user.getPassword());
              MyUserDetailsService.getUserFromSession().setAlfresAuthTicket(ticket);
            }
            // Converting json to java object
            AlfrescoAuthTicket authTicket = new ObjectMapper().readValue(nodeRef, AlfrescoAuthTicket.class);
            String nodeRefThumnails = authTicket.getNodeRef();
            String[] node = nodeRefThumnails.split("/");

            //grant permission to user as Consumer to the uploaded files
            alfrescoAuthDetails.grantPermission(adminTicket, user.getUserName(), node[3], "Consumer");

            bills.setUrl(node[3]);
            bills.setFileName(file);
            bills.setContentType(contentType);
            billRepository.save(bills);

            message = "Uploaded Successfully !!!";
            status = HttpStatus.OK;
          }
          if (statusCode1 == 413) {
            message = "Exceeded Storage Limit,Not saved, try again !!!";
            status = HttpStatus.OK;
          }
          alfrescoAuthDetails.logoutTicket(adminTicket);

        } else {
          message = "Exceeded Storage Limit,Not saved, try again !!!";
          status = HttpStatus.OK;
        }

      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        if (mPost != null) {
          mPost.releaseConnection();
        }
      }

    } else {
      message = "Before Uploading file ensure that Email and Mobile is Verified";
      status = HttpStatus.OK;
    }
    response.put("statusCode", status.value());
    response.put("Status", status.getReasonPhrase());
    response.put("message", message);
    return response;
  }

  @RequestMapping(value = "getFiles", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
  public Map<String, Object> gethsFiles(@RequestBody User user, BindingResult result, ModelMap modelMap) {
    Map<String, Object> response = new HashMap<String, Object>();
    HttpStatus status = HttpStatus.BAD_REQUEST;
    String message = "No Records Found !!!";

    String ticket = MyUserDetailsService.getUserFromSession().getAlfresAuthTicket();
    List<UploadFile> list = new LinkedList<UploadFile>();

    if (user != null) {
      Long userId = user.getUserId();
      User u = userRepository.findOne(userId);
     if (u != null) {
        if (ticket == null || "".equals(ticket)) {
          ticket = alfrescoAuthDetails.getAlfrescoConnectionTicket(u.getUserName(), u.getPassword());
        } else if (!alfrescoAuthDetails.isTicketValid(ticket)) {
          ticket = alfrescoAuthDetails.getAlfrescoConnectionTicket(u.getUserName(), u.getPassword());
        }
        PageRequest pageRequest = new PageRequest(0, 5);
        List<UploadFile> fileList = null;
        if (user.getDoctorId() != null && user.getClinicId() != null) {
          fileList = uploadFileRepository.getClinicFilesByTypeAndId(user.getUserId(), user.getSendType(), user.getClinicId(), user.getDoctorId(), pageRequest);
        } else {
          fileList = uploadFileRepository.getFilesByTypeAndId(user.getUserId(), user.getSendType(), pageRequest);
        }
        if (fileList != null && !fileList.isEmpty()) {
          for (UploadFile file : fileList) {
            if (file.getClinicId() != null) {
              String clinicName = clinicInfoRepository.getClinicName(file.getClinicId());
              file.setClinicName(clinicName);
              file.setUser(null);
              file.setEncounter(null);
              if(file.getServiceRequests()!= null){
                  file.getServiceRequests().setUploadFile(null);
              }
            }else{
                file.setEncounter(null);
                file.setUser(null);
                if(file.getServiceRequests()!= null){
                    file.getServiceRequests().setUploadFile(null);
                }
            }
            file.setVurl(utilityServices.getMessage("Application.Url") + "/api/user/records/view/" + file.getFileId() + "/" + userId);
            file.setVdownloadUrl(utilityServices.getMessage("Application.Url") + "/api/user/records/download/" + file.getFileId() + "/" + userId);
            list.add(file);
          }
        }
        response.put("requestedFiles", list);
        response.put("count", list.size());
        String size = userRepository.getUserData(u, null);
        if (size != null) {
          response.put("size", size);
        }
        status = HttpStatus.OK;
      }
    }
    response.put("ticket", ticket);
    response.put("statusCode", status.value());
    response.put("Status", status.getReasonPhrase());
    response.put("message", message);
    return response;
  }

    @RequestMapping(value = "getallFiles", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> getHospitalFiles(@RequestBody User user, BindingResult result, ModelMap modelMap) {
        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "No Records Found !!!";

        String ticket = MyUserDetailsService.getUserFromSession().getAlfresAuthTicket();

        List<UploadFile> list = new LinkedList<UploadFile>();

        if (user != null) {
            Long userId = user.getUserId();
            User u = userRepository.findOne(userId);
            if (u != null) {
                if (ticket == null || "".equals(ticket)) {
                    ticket = alfrescoAuthDetails.getAlfrescoConnectionTicket(u.getUserName(), u.getPassword());
                } else if (!alfrescoAuthDetails.isTicketValid(ticket)) {
                    ticket = alfrescoAuthDetails.getAlfrescoConnectionTicket(u.getUserName(), u.getPassword());
                }
                List<UploadFile> fileList = null;
                if (user.getDoctorId() != null && user.getClinicId() != null) {
                    fileList = uploadFileRepository.getAllClinicFilesByTypeAndId(user.getUserId(), user.getSendType(), user.getClinicId(), user.getDoctorId());
                } else {
                    fileList = uploadFileRepository.getAllFilesByTypeAndId(user.getUserId(), user.getSendType());

                }
                if (fileList != null && !fileList.isEmpty()) {
                    for (UploadFile file : fileList) {
                        file.setVurl(utilityServices.getMessage("Application.Url") + "/api/user/records/view/" + file.getFileId() + "/" + userId);
                        file.setVdownloadUrl(utilityServices.getMessage("Application.Url") + "/api/user/records/download/" + file.getFileId() + "/" + userId);
                        file.setEncounter(null);
                        file.setUser(null);
                        file.setServiceRequests(null);
                        list.add(file);
                    }
                }
                response.put("requestedFiles", list);
                String size = userRepository.getUserData(u, null);
                if (size != null) {
                    response.put("size", size);
                }

                status = HttpStatus.OK;
            }
        }
        response.put("ticket", ticket);
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }


    @RequestMapping(value = "getBills", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> geBills(@RequestBody User user, BindingResult result, ModelMap modelMap) {
        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "No Bills Found !!!";

        String ticket = MyUserDetailsService.getUserFromSession().getAlfresAuthTicket();
        List<Bills> list = new LinkedList<>();

        if (user != null) {
            Long userId = user.getUserId();
            User u = userRepository.findOne(userId);
            if (u != null) {
                if (ticket == null || "".equals(ticket)) {
                    ticket = alfrescoAuthDetails.getAlfrescoConnectionTicket(u.getUserName(), u.getPassword());
                } else if (!alfrescoAuthDetails.isTicketValid(ticket)) {
                    ticket = alfrescoAuthDetails.getAlfrescoConnectionTicket(u.getUserName(), u.getPassword());
                }
                PageRequest pageRequest = new PageRequest(0, 5);
                List<Bills> billsList = billRepository.getBillsById(user.getUserId(), pageRequest);
                for (Bills bill : billsList) {
                    bill.setUser(null);
                    String fileName = bill.getFileName();
                    if (StringUtils.isNotBlank(fileName)) {
                        bill.setvUrl(utilityServices.getMessage("Application.Url") + "/api/user/records/viewBill/" + bill.getId() + "/" + userId);
                        bill.setdUrl(utilityServices.getMessage("Application.Url") + "/api/user/records/view/" + bill.getId() + "/" + userId);
                    }
                    list.add(bill);
                }
                response.put("bills", list);
                String size = userRepository.getUserData(u, null);
                if (size != null) {
                    response.put("size", size);
                }
                String main = billRepository.getTotalById(userId);
                String[] sub = main.split(",");
                response.put("count", sub[1]);
                if (StringUtils.isNotBlank(sub[0]) && !sub[0].equals("null")) {
                    Double total = Double.parseDouble(sub[0]);
                    if (total != null) {
                        double rounded = (double) Math.round(total * 100) / 100;
                        response.put("total", rounded);
                    }
                } else {
                    response.put("total", 0);
                }
                status = HttpStatus.OK;
            }
        }
        response.put("ticket", ticket);
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    @RequestMapping(value = "getAllBills", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> getAllBills(@RequestBody User user, BindingResult result, ModelMap modelMap) {
        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "No Records Found !!!";

        String ticket = MyUserDetailsService.getUserFromSession().getAlfresAuthTicket();
        List<Bills> list = new LinkedList<>();

        if (user != null) {
            Long userId = user.getUserId();
            User u = userRepository.findOne(userId);
            if (u != null) {
                if (ticket == null || "".equals(ticket)) {
                    ticket = alfrescoAuthDetails.getAlfrescoConnectionTicket(u.getUserName(), u.getPassword());
                } else if (!alfrescoAuthDetails.isTicketValid(ticket)) {
                    ticket = alfrescoAuthDetails.getAlfrescoConnectionTicket(u.getUserName(), u.getPassword());
                }

                List<Bills> billsList = billRepository.getAllBillsById(user.getUserId());
                for (Bills bill : billsList) {
                    bill.setUser(null);
                    String fileName = bill.getFileName();
                    if (StringUtils.isNotBlank(fileName)) {
                        bill.setvUrl(utilityServices.getMessage("Application.Url") + "/api/user/records/viewBill/" + bill.getId() + "/" + userId);
                        bill.setdUrl(utilityServices.getMessage("Application.Url") + "/api/user/records/view/" + bill.getId() + "/" + userId);
                    }
                    list.add(bill);
                }
                response.put("bills", list);
                Double total = billRepository.getTotalAmountById(userId);
                if (total != null) {
                    System.out.println();
                    double rounded = (double) Math.round(total * 100) / 100;
                    response.put("total", rounded);
                } else {
                    response.put("total", 0);
                }
                String size = userRepository.getUserData(u, null);
                if (size != null) {
                    response.put("size", size);
                }
                status = HttpStatus.OK;
            }
        }

        response.put("ticket", ticket);
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }


    @RequestMapping(value = "getTotal", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> getTotal(@RequestBody Bills bill, BindingResult result, ModelMap modelMap) {
        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "No Records Found !!!";

        if (bill != null) {
            User user = bill.getUser();
            if (user != null) {
                Long userId = user.getUserId();
                Date from = DateService.stringToDateConversion(bill.getBillDate());
                Date to = DateService.stringToDateConversion(bill.getBillsDate());
                Double totalBill = billRepository.getTotalBetweenDates(from, to, userId);
                if (totalBill != null) {
                    double rounded = (double) Math.round(totalBill * 100) / 100;
                    response.put("amount", rounded);
                } else {
                    response.put("amount", 0);
                }

                status = HttpStatus.OK;

            }
        }
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    @RequestMapping(value = "view/{fileId}/{userId}", method = RequestMethod.GET)
    public void viewRecord(@PathVariable(value = "fileId") Long fileId, @PathVariable(value = "userId") Long userId,HttpServletResponse response) {
        HttpClient client = new HttpClient();
        GetMethod getMethod = null;
        InputStream inputStream = null;
        UploadFile file = uploadFileRepository.findOne(fileId);
        User u = userRepository.findOne(userId);
        String ticket = alfrescoAuthDetails.getAlfrescoConnectionTicket(u.getUserName(), u.getPassword());
        String retriveUrl = this.messageSource.getMessage("alfresco.retrive.files", null, "Default alfresco retrive files", null);
        String url = retriveUrl + file.getUrl() + "?alf_ticket="+ticket;
        try {
            getMethod = new GetMethod(url);
            int statusCode1 = client.executeMethod(getMethod);
            if (statusCode1 == org.apache.commons.httpclient.HttpStatus.SC_OK) {
                inputStream = getMethod.getResponseBodyAsStream();
                response.setHeader("Pragma", "no-cache");
                response.setHeader("Cache-Control", "no-cache, must-revalidate");
                response.setContentType(file.getContentType());
                response.setDateHeader("Expires", 0);
                /*response.setHeader("Content-disposition", "attachment; filename="+file.getFileName());*/
        IOUtils.copy(inputStream, response.getOutputStream());
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (getMethod != null) {
        getMethod.releaseConnection();
      }
      if (inputStream != null) {
        try {
          inputStream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
    }
      }
    }

  @RequestMapping(value = "viewBill/{billId}/{userId}", method = RequestMethod.GET)
  public void viewBill(HttpServletResponse response, @PathVariable(value = "billId") Long billId, @PathVariable(value = "userId") Long userId) {
    HttpClient client = new HttpClient();
    GetMethod getMethod = null;
    InputStream inputStream = null;
    Bills bill = billRepository.findOne(billId);
    User u = userRepository.findOne(userId);
    String ticket = alfrescoAuthDetails.getAlfrescoConnectionTicket(u.getUserName(), u.getPassword());
    String retriveUrl = this.messageSource.getMessage("alfresco.retrive.files", null, "Default alfresco retrive files", null);
    String url = retriveUrl + bill.getUrl() + "?alf_ticket=" + ticket;

    try {
      getMethod = new GetMethod(url);
      int statusCode1 = client.executeMethod(getMethod);
      if (statusCode1 == org.apache.commons.httpclient.HttpStatus.SC_OK) {
        inputStream = getMethod.getResponseBodyAsStream();
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache, must-revalidate");
        response.setDateHeader("Expires", 0);
        IOUtils.copy(inputStream, response.getOutputStream());
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (getMethod != null) {
        getMethod.releaseConnection();
      }
      if (inputStream != null) {
        try {
          inputStream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }

  }

   /* @RequestMapping(value = "downloadBill/{billId}/{userId}", method = RequestMethod.GET)
    public void downloadBill(HttpServletResponse response,@PathVariable(value = "billId") Long
            billId, @PathVariable(value = "userId") Long userId) {
        HttpClient client = new HttpClient();
        GetMethod getMethod = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        Bills bill = billRepository.findOne(billId);
        User u = userRepository.findOne(userId);
        String ticket = alfrescoAuthDetails.getAlfrescoConnectionTicket(u.getUserName(), u.getPassword());
        String retriveUrl = this.messageSource.getMessage("alfresco.retrive.files", null, "Default alfresco retrive files", null);
        String downloadUrl = this.messageSource.getMessage("alfresco.downloadUrl", null, "Default alfresco downloadUrl", null);
        String url = retriveUrl + bill.getUrl() + downloadUrl+"?alf_ticket="+ticket;
        try {
            getMethod = new GetMethod(url);
            int statusCode1 = client.executeMethod(getMethod);
            if (statusCode1 == org.apache.commons.httpclient.HttpStatus.SC_OK) {
                inputStream = getMethod.getResponseBodyAsStream();
                response.setHeader("Pragma", "no-cache");
                response.setHeader("Cache-Control", "no-cache, must-revalidate");
                response.setDateHeader("Expires", 0);
                IOUtils.copy(inputStream, response.getOutputStream());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            getMethod.releaseConnection();
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    // outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

    }
*/
}

