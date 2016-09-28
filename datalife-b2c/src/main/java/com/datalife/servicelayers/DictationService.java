package com.datalife.servicelayers;

import com.datalife.entities.*;
import com.datalife.repositories.*;
import com.datalife.services.AlfrescoAuthDetails;
import com.datalife.services.DateService;
import com.datalife.services.MyUserDetailsService;
import com.datalife.services.UtilityServices;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * Created by Supriya on 12/5/2015.
 */
@RestController
@RequestMapping(value = "/dictation/")
public class DictationService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AlfrescoAuthDetails alfrescoAuthDetails;

    @Autowired
    UploadFileRepository uploadFileRepository;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    ClinicInfoRepository clinicInfoRepository;

    @Autowired
    UserDetailsRepository userDetailsRepository;

    @Autowired
    UtilityServices utilityServices;

    @Autowired
    EncounterRepository encounterRepository;

    /*@RequestMapping(value = "upload", method = RequestMethod.POST, produces = "application/json")
    public Map<String, Object> handleFileUpload(@ModelAttribute UploadFile uploadFile,@ModelAttribute MultiFileUpload multiFileUpload, BindingResult result, ModelMap modelMap) {
        Map<String, Object> files = new HashMap<String, Object>();
        Long userId = uploadFile.getUser().getUserId();
        User user = userRepository.findOne(userId);
        List<UploadFile> list = new LinkedList<UploadFile>();
        String adminTicket = null;
        if (user != null) {
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

                    //get the url from properties file
                    String url = this.messageSource.getMessage("alfresco.upload.files", null, "Default alfresco upload files", null);
                    String retriveUrl = this.messageSource.getMessage("alfresco.retrive.files", null, "Default alfresco retrive files", null);
                    String thumbnailUrl = this.messageSource.getMessage("alfresco.thumnail.files", null, "Default alfresco thumnail files", null);
                    String thumbnail = this.messageSource.getMessage("alfresco.thumnailUrl", null, "Default alfresco thumnailUrl", null);
                    String downloadUrl = this.messageSource.getMessage("alfresco.downloadUrl", null, "Default alfresco downloadUrl", null);
                    String view = this.messageSource.getMessage("alfresco.viewInBrowser", null, "Default alfresco viewInBrowser", null);
                    String viewIn = this.messageSource.getMessage("alfresco.fetchContent", null, "Default alfresco fetchContent", null);


                    adminTicket = alfrescoAuthDetails.getAlfrescoConnectionTicket("admin", "admin");
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
                            System.out.println(image.getVurl());
                            image.setTicket(ticket);
                            image.setError("Uploaded");
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

        }
        return files;
    }*/

    @RequestMapping(value = "upload", method = RequestMethod.POST, produces = "application/json")
    public Map<String, Object> handleFileUpload(HttpServletRequest request, HttpServletResponse res) throws IOException {
        //System.out.println("JYIIIIIIIIIIIIIIIIIIIII");
        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        OutputStream output = null;
        InputStream input = null;
        try {
            input = (InputStream) request.getInputStream();
            String serverpath = this.messageSource.getMessage("Doctor.dictated.audio.filepath", null, "Default dictated audio filepath", null);
            String s = request.getHeader("fname");
            System.out.println(serverpath);
            System.out.println(s);
            File dirct = new File(serverpath);
            if (!dirct.exists())
                dirct.mkdirs();
            File uploadedFile = new File(dirct, s);

            output = new FileOutputStream(uploadedFile);
            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = input.read(bytes)) != -1) {
                output.write(bytes, 0, read);
            }
            status = HttpStatus.OK;
            response.put("message","Dictation has been saved Successfully");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (input != null) {
                input.close();
            }
            if (output != null) {
                output.close();
            }
        }
        response.put("statusCode", status.value());
        return response;
    }


    @Scheduled(fixedDelay = 500000)
    public void demoServiceMethod() throws Exception {
        System.out.println("JYIIIIIIIIIIIIIIIIIIIII");
        /*final String[] SUFFIX = {"docx"};*/  // use the suffix to filter \\192.168.2.19\datascribelpo\DatalifePDFDocs\
        final String[] SUFFIX = {"pdf"};
        String path = this.messageSource.getMessage("Doctor.dictation.converted.pdf.filepath", null, "Default dictated converted pdf filepath", null);
        File rootDir = new File(path);
        Collection<File> files = FileUtils.listFiles(rootDir, SUFFIX, true);
        for (File file : files) {

            /*WordToPdfConverter wordToPdfConverter = new WordToPdfConverter();
            String src = file.getAbsolutePath();
            String desc = src.substring(0, src.indexOf('.')) + ".pdf";
            wordToPdfConverter.convertWordToPdf(src, desc);*/

            /*String lowerCaseInPath = file.getAbsolutePath().toLowerCase();
            InputStream inStream = getInFileStream(file.getAbsolutePath());
            String outPath = changeExtensionToPDF(file.getAbsolutePath());
            OutputStream outStream = getOutFileStream(outPath);
            Converter converter = null;
            converter = new DocToPDFConverter(inStream, outStream, true, true);
            converter.convert();*/

            File file1 = new File(file.getAbsolutePath());
            Encounter enc = new Encounter();

            String fileName = file1.getName();
            String[] strings = fileName.split("_");

            Long doctorId = Long.parseLong(strings[1]);
            Long patientId = Long.parseLong(strings[2]);
            Long clinicId = Long.parseLong(strings[0]);
            User doctor = userRepository.findOne(doctorId);
            User patient = userRepository.findOne(patientId);

            Date date = new Date();
            enc.setDate(date);
            enc.setUser(patient);
            enc.setShare(true);
            enc.setClinicName(clinicInfoRepository.getClinicName(clinicId));
            String name = userDetailsRepository.getFullName(doctorId);
            String[] names = name.split(",");
            enc.setDoctorName("Dr." + names[0] + " " + names[1]);

            String ticket = alfrescoAuthDetails.getAlfrescoConnectionTicket(patient.getUserName(), patient.getPassword());
            String url = patient.getAlfrescoUrl();
            //get patient-user info from alfresco
            String statusCode = alfrescoAuthDetails.getAlfrescoUserDetails(url, ticket);

            //check the patient-user has enough space to store the report
            if (statusCode != null) {

                String[] q = statusCode.split(",");
                int quota = Integer.parseInt(q[0]);
                int cz = Integer.parseInt(q[1]);

                if (quota > cz) {

                    enc.setDoctorId(doctorId);
                    enc.setClinicId(clinicId);

                    encounterRepository.save(enc);

                    String userParentDir = null;
                    String[] node = new String[]{};
                   /* String storageDirectory = "C:\\DatalifeLocalFolder\\pdf\\";*/

                    userParentDir = enc.getUser().getUserParentDir();
                    node = userParentDir.split("/");

                    // patient grant permission to doctor to upload a Encounter report
                    alfrescoAuthDetails.grantPermission(ticket, doctor.getUserName(), node[3], "Collaborator");
                    String doctorTicket = alfrescoAuthDetails.getAlfrescoConnectionTicket(doctor.getUserName(), doctor.getPassword());

                    Long id = enc.getEncounterId();
                    //generating file name for pdf
                    fileName = "summaryReport" + id + ".pdf";

                   /* String summaryFilePath = storageDirectory + fileName;
                    file1 = new File(summaryFilePath);*/

                    Map<String, String> stringMap = alfrescoAuthDetails.uploadFiles(file1, fileName, "application/pdf", userParentDir, doctorTicket);
                    int uploadStatusCode = Integer.parseInt(stringMap.get("statusCode"));

                    if (uploadStatusCode == 200) {
                        UploadFile uploadFile = new UploadFile(fileName, "application/pdf", "hospital", "", "", "General", stringMap.get("nodeRef"), patient, date, date, enc.isShare(), enc.getClinicId(), enc.getDoctorId());
                        uploadFileRepository.save(uploadFile);
                        //delete the file from local path
                        file1.delete();
                        file.delete();
                    }
                }
            }
        }
        System.out.println("Method executed at every 5 seconds. Current time is :: "+ new Date());
    }




    public static String changeExtensionToPDF(String originalPath) {

//		String separator = System.getProperty("file.separator");
        String filename = originalPath;

//		// Remove the path upto the filename.
//		int lastSeparatorIndex = originalPath.lastIndexOf(separator);
//		if (lastSeparatorIndex == -1) {
//			filename = originalPath;
//		} else {
//			filename = originalPath.substring(lastSeparatorIndex + 1);
//		}

        // Remove the extension.
        int extensionIndex = filename.lastIndexOf(".");

        String removedExtension;
        if (extensionIndex == -1){
            removedExtension =  filename;
        } else {
            removedExtension =  filename.substring(0, extensionIndex);
        }
        String addPDFExtension = removedExtension + ".pdf";

        return addPDFExtension;
    }

    protected static InputStream getInFileStream(String inputFilePath) throws FileNotFoundException{
        File inFile = new File(inputFilePath);
        FileInputStream iStream = new FileInputStream(inFile);
        return iStream;
    }

    protected static OutputStream getOutFileStream(String outputFilePath) throws IOException{
        File outFile = new File(outputFilePath);

        try{
            //Make all directories up to specified
            outFile.getParentFile().mkdirs();
        } catch (NullPointerException e){
            //Ignore error since it means not parent directories
        }

        outFile.createNewFile();
        FileOutputStream oStream = new FileOutputStream(outFile);
        return oStream;
    }



}
