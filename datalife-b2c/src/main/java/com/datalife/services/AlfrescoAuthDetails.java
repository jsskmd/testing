package com.datalife.services;

import com.datalife.entities.AlfrescoAuthTicket;
import com.datalife.entities.RolesInServices;
import com.datalife.entities.User;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;



import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;





/**
 * Created by Dipak on 3/13/2015.
 */
@Service
public class AlfrescoAuthDetails {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    UtilityServices utilityServices;

    private static final int BUFFER_SIZE = 4096;

    private static final Logger logger = Logger.getLogger(AlfrescoAuthDetails.class);

    public boolean isTicketValid(String ticket) {
        HttpClient client = new HttpClient();

        String url = this.messageSource.getMessage("alfresco.login.logout", null, "Default alfresco login logout", null);
        String isTicketValid = url+"/" + ticket + "?alf_ticket=" + ticket;
        GetMethod method = null;

            method = new GetMethod(isTicketValid);
        boolean b = false;
        try {
            client.executeMethod(method);
            if (method.getStatusCode() == HttpStatus.SC_OK) {
                b = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            method.releaseConnection();
        }
        return b;
    }

    public String getAlfrescoConnectionTicket(String username, String pwd) {
        HttpClient client = new HttpClient();
        PostMethod method = null;
        String ticket1 = null;
        try {
            String url = this.messageSource.getMessage("alfresco.connection.ticket", null, "Default alfresco connection ticket", null);
            method = new PostMethod(url);
            String input = "{ \"username\" : \"" + username + "\", \"password\" : \"" + pwd + "\" }";
            method.setRequestEntity(new StringRequestEntity(input, "application/json", null));
            int statusCode = client.executeMethod(method);

            if (statusCode == HttpStatus.SC_OK) {
                String ticket = method.getResponseBodyAsString();
                AlfrescoAuthTicket authTicket = new ObjectMapper().readValue(ticket, AlfrescoAuthTicket.class);
                ticket1 = authTicket.getData().getTicket();
            }
            if (statusCode == 403) {
                ticket1 = "403";
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            assert method != null;
            method.releaseConnection();
        }
        return ticket1;
    }

    public void logoutTicket(String ticket) {

        HttpClient client = new HttpClient();
        DeleteMethod method = null;
        try {
            String url = this.messageSource.getMessage("alfresco.login.logout", null, "Default alfresco login logout", null);
            method = new DeleteMethod(url + ticket + "?alf_ticket=" + ticket);
            int statusCode = client.executeMethod(method);
            if (statusCode == HttpStatus.SC_OK) {
                System.out.println("logged out Success");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            assert method != null;
            method.releaseConnection();
        }
    }


    public String grantPermission(String ticket, String userName, String nodeRef, String role) {
        HttpClient client = new HttpClient();
        PostMethod mPost = null;

        String result = "";
        if ("Collaborator".equals(role)) {
            result = "{\n" +
                    "   \"permissions\":\n" +
                    "   [\n" +
                    "      {\n" +
                    "         \"authority\":\"" + userName + "\",\n" +
                    "         \"role\":\"All\",\n" +
                    "         \"remove\":true\n" +
                    "      },\n" +
                    "      {\n" +
                    "         \"authority\":\"" + userName + "\",\n" +
                    "         \"role\":\"Collaborator\"\n" +
                    "      }\n" +
                    "   ]\n" +
                    "   ,\"isInherited\":false\n" +
                    "}";
        } else {
            result = "{\n" +
                    "   \"permissions\":\n" +
                    "   [\n" +
                    "      {\n" +
                    "         \"authority\":\"" + userName + "\",\n" +
                    "         \"role\":\"All\",\n" +
                    "         \"remove\":true\n" +
                    "      },\n" +
                    "      {\n" +
                    "         \"authority\":\"" + userName + "\",\n" +
                    "         \"role\":\"Consumer\"\n" +
                    "      }\n" +
                    "   ]\n" +
                    "   ,\"isInherited\":false\n" +
                    "}";
        }
        try {
            String url = this.messageSource.getMessage("alfresco.grantPermission", null, "Default alfresco grantPermission", null);
            String urlString = url + nodeRef + "?alf_ticket=" + ticket;
            mPost = new PostMethod(urlString);
            String input = result;
            mPost.setRequestEntity(new StringRequestEntity(input, "application/json", null));
            int statusCode = client.executeMethod(mPost);
            if (statusCode == HttpStatus.SC_OK) {
                result = mPost.getResponseBodyAsString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            assert mPost != null;
            mPost.releaseConnection();
        }
        return result;
    }

    public String deletePermission(String ticket, String userName, String nodeRef, String role) {
        HttpClient client = new HttpClient();
        PostMethod mPost = null;
        String[] node = nodeRef.split("/");
        nodeRef = node[3];
        String result = "";
        result = "{\n" +
                "   \"permissions\":\n" +
                "   [\n" +
                "      {\n" +
                "         \"authority\":\"" + userName + "\",\n" +
                "         \"role\":\"" + role + "\",\n" +
                "         \"remove\":true\n" +
                "      },\n" +
                "   ]\n" +
                "   ,\"isInherited\":false\n" +
                "}";

        try {
            String url = this.messageSource.getMessage("alfresco.grantPermission", null, "Default alfresco grantPermission", null);
            String urlString = url + nodeRef + "?alf_ticket=" + ticket;
            mPost = new PostMethod(urlString);
            String input = result;
            mPost.setRequestEntity(new StringRequestEntity(input, "application/json", null));
            int statusCode = client.executeMethod(mPost);
            if (statusCode == HttpStatus.SC_OK) {
                result = mPost.getResponseBodyAsString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mPost != null) {
                mPost.releaseConnection();
            }
        }
        return result;
    }


    public Map<String, String> uploadFiles(File fileobj, String fileName, String filetype, String userParentDir) {

        logger.debug("method : uploadFiles start");
        HttpClient client = new HttpClient();
        PostMethod mPost = null;
        Map<String, String> strings = new HashMap<String, String>();
        try {
            String url = this.messageSource.getMessage("alfresco.upload.files", null, "Default alfresco upload files", null);
            String ticket=MyUserDetailsService.getUserFromSession().getAlfresAuthTicket();
            String urlString = url + ticket;
            logger.debug("uploadFiles : Alfresco upload Api :"+ urlString);
            mPost = new PostMethod(urlString);
            String result = null;
            Part[] parts = {
                    new FilePart("filedata", fileName, fileobj, filetype, null),
                    new StringPart("filename", fileName),
                    new StringPart("description", "description"),
                    new StringPart("destination", userParentDir),
            };
            mPost.setRequestEntity(new MultipartRequestEntity(parts, mPost.getParams()));
            int statusCode1 = client.executeMethod(mPost);
            logger.info("uploadFiles : Alfresco upload Api resulted status:"+ statusCode1);
            if (mPost.getStatusCode() == HttpStatus.SC_OK) {
                String nodeRef = mPost.getResponseBodyAsString();
                // Converting json to java object
                AlfrescoAuthTicket authTicket = new ObjectMapper().readValue(nodeRef, AlfrescoAuthTicket.class);

                String nodeRefThumnails = authTicket.getNodeRef();
                String statusCode = String.valueOf(mPost.getStatusCode());
                String[] node = nodeRefThumnails.split("/");
                result = node[3];
                strings.put("nodeRef", result);
                strings.put("statusCode", statusCode);
            }else{
                strings.put("statusCode", String.valueOf(statusCode1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mPost != null) {
                mPost.releaseConnection();
            }
        }
        logger.info("method : uploadFiles end");
        return strings;
    }

    public Map<String, String> uploadFiles(File fileobj, String fileName, String filetype, String userParentDir,String ticket) {
        HttpClient client = new HttpClient();
        PostMethod mPost = null;
        Map<String, String> strings = new HashMap<String, String>();
        try {
            String url = this.messageSource.getMessage("alfresco.upload.files", null, "Default alfresco upload files", null);
            /*String ticket=MyUserDetailsService.getUserFromSession().getAlfresAuthTicket();*/
            String urlString = url + ticket;
            mPost = new PostMethod(urlString);
            String result = null;
            Part[] parts = {
                    new FilePart("filedata", fileName, fileobj, filetype, null),

                    new StringPart("filename", fileName),
                    new StringPart("description", "description"),
                    new StringPart("destination", userParentDir),
            };
            mPost.setRequestEntity(new MultipartRequestEntity(parts, mPost.getParams()));
            int statusCode1 = client.executeMethod(mPost);
            if (mPost.getStatusCode() == HttpStatus.SC_OK) {
                String nodeRef = mPost.getResponseBodyAsString();
                System.out.println("statusLine>>>" + statusCode1 + "......" + "\n status line \n" + mPost.getStatusLine() + "\nbody \n" + mPost.getResponseBodyAsString());
                // Converting json to java object
                AlfrescoAuthTicket authTicket = new ObjectMapper().readValue(nodeRef, AlfrescoAuthTicket.class);

                String nodeRefThumnails = authTicket.getNodeRef();
                String statusCode = String.valueOf(mPost.getStatusCode());
                String[] node = nodeRefThumnails.split("/");
                result = node[3];
                strings.put("nodeRef", result);
                strings.put("statusCode", statusCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            assert mPost != null;
            mPost.releaseConnection();
        }
        return strings;
    }

    public String addFolder(long uid, String ticket, String repositoryId) {
        HttpClient client = new HttpClient();
        PostMethod postMethod = null;
        String result = null;
        try {
            String url = this.messageSource.getMessage("alfresco.addFolder", null, "Default alfresco addFolder", null);
            String parentDir = url + repositoryId + "?alf_ticket=" + ticket;
            postMethod = new PostMethod(parentDir);
            String userParentDir = String.valueOf(uid);
            String inputjson = "{ \"name\" :\"" + userParentDir + "\"}";
            postMethod.setRequestEntity(new StringRequestEntity(inputjson, "application/json", null));
            int statusCode1 = client.executeMethod(postMethod);
            if (statusCode1 == HttpStatus.SC_OK) {
                String nodeRef = postMethod.getResponseBodyAsString();
                AlfrescoAuthTicket alfreAuth = new ObjectMapper().readValue(nodeRef, AlfrescoAuthTicket.class);
                result = alfreAuth.getNodeRef();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            assert postMethod != null;
            postMethod.releaseConnection();
        }
        return result;
    }

    public String addUser(User user, String ticket) {
        HttpClient client = new HttpClient();
        PostMethod postMethod = null;
        String result = null;
        String inputjson = null;
        String quota = this.messageSource.getMessage("alfresco.userquota", null, "Default alfresco userquota", null);
        switch(user.getRole()){

            case RolesInServices.ROLE_DIAGNOSTICLABS:
            case RolesInServices.ROLE_PHARMACY:
            case RolesInServices.ROLE_HOSPITAL:
                inputjson = "{\"userName\" : \"" + user.getUserName() + "\" , \"password\": \"" + user.getPassword() + "\" , \"firstName\": \"" + user.getServiceProvider().getContactPerson() + "\" , \"lastName\" :\"" + user.getServiceProvider().getContactPerson() + "\" , \"jobtitle\": \"" + user.getRole() + "\", \"email\": \"" + user.getUserContactInfo().getEmail() + "\" ,\"organization\" : \"" + user.getServiceProvider().getName() + "\" , \"enabled\" : \"true\" , \"quota\": \""+quota+"\"}";
                break;

            case RolesInServices.ROLE_CLINIC:
                inputjson = "{\"userName\" : \"" + user.getUserName() + "\" , \"password\": \"" + user.getPassword() + "\" , \"firstName\": \"" + user.getClinicInfo().getClinicName() + "\" , \"lastName\" :\"" + user.getClinicInfo().getClinicName() + "\" , \"jobtitle\": \"" + user.getRole() + "\", \"email\": \"" + user.getUserContactInfo().getEmail() + "\" ,\"organization\" : \"" + user.getClinicInfo().getClinicName() + "\" , \"enabled\" : \"true\" , \"quota\": \""+quota+"\"}";
                break;
            case RolesInServices.ROLE_TELECONSULTANT:
            case RolesInServices.ROLE_DOCTOR:
            case RolesInServices.ROLE_REFERRALDOCTOR:
            case RolesInServices.ROLE_PATIENT:
                inputjson = "{\"userName\" : \"" + user.getUserName() + "\" , \"password\": \"" + user.getPassword() + "\" , \"firstName\": \"" + user.getUserDetails().getFirstName() + "\" , \"lastName\" :\"" + user.getUserDetails().getLastName() + "\" , \"jobtitle\": \"" + user.getRole() + "\", \"email\": \"" + user.getUserContactInfo().getEmail() + "\" ,\"organizationId\" : \"true\" , \"enabled\" : \"true\" , \"quota\": \""+quota+"\"}";
                break;
        }
        try {
            String url = this.messageSource.getMessage("alfresco.addUser", null, "Default alfresco addUser", null);
            url = url + ticket;
            postMethod = new PostMethod(url);
            /*inputjson = "{\"userName\" : \"" + user.getUserName() + "\" , \"password\": \"" + user.getPassword() + "\" , \"firstName\": \"" + user.getUserDetails().getFirstName() + "\" , \"lastName\" :\"" + user.getUserDetails().getLastName() + "\" , \"jobtitle\": \"" +user.getRole()+ "\", \"email\": \"" + user.getUserContactInfo().getEmail() + "\" ,\"organizationId\" : \"true\" , \"enabled\" : \"true\" , \"quota\": \"500000000\"}";*/
            postMethod.setRequestEntity(new StringRequestEntity(inputjson, "application/json", null));

            int statusCode1 = client.executeMethod(postMethod);
            if (statusCode1 == HttpStatus.SC_OK) {
                result = postMethod.getResponseBodyAsString();
                AlfrescoAuthTicket authTicket = new ObjectMapper().readValue(result, AlfrescoAuthTicket.class);
                result = authTicket.getUrl();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            assert postMethod != null;
            postMethod.releaseConnection();
        }
        return result;
    }

    public String getRepositoryId(String ticket, String userName) {
        HttpClient client = new HttpClient();
        PostMethod postMethod = null;
        String result = null;
        try {
            String url = this.messageSource.getMessage("alfresco.getRepositoryId", null, "Default alfresco getRepositoryId", null);
            url = url + ticket;
            postMethod = new PostMethod(url);
            String inputjson = "{\"userName\" : \"" + userName + "\" }";
            postMethod.setRequestEntity(new StringRequestEntity(inputjson, "application/json", null));

            int statusCode1 = client.executeMethod(postMethod);
            if (statusCode1 == HttpStatus.SC_OK) {
                result = postMethod.getResponseBodyAsString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            assert postMethod != null;
            postMethod.releaseConnection();
        }
        return result;
    }

    public String getAlfrescoUserDetails(String url, String ticket) {
        HttpClient client = new HttpClient();
        String input = url + "?alf_ticket=" + ticket;
        url = this.messageSource.getMessage("alfresco.url", null, "Default alfresco url", null);
        url = url + input;
        GetMethod method = new GetMethod(url);
        String b = null;
        try {
            client.executeMethod(method);
            if (method.getStatusCode() == HttpStatus.SC_OK) {
                b = method.getResponseBodyAsString();
                AlfrescoAuthTicket authTicket = new ObjectMapper().readValue(b, AlfrescoAuthTicket.class);
                b = authTicket.getQuota() + "," + authTicket.getSizeCurrent();

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            method.releaseConnection();
        }
        return b;
    }

    public String changePassword(String username, String pwd, String oldpw, String authenticate) {
        HttpClient client = new HttpClient();
        PostMethod method = null;
        String ticket1 = null;
        try {
            String url = this.messageSource.getMessage("alfresco.changePassword", null, "Default alfresco changePassword", null);
            String path = url + username + "?alf_ticket=" + authenticate;
            method = new PostMethod(path);
            String input = "{ \"username\" : \"" + username + "\", \"oldpw\" : \"" + oldpw + "\",\"newpw\" : \"" + pwd + "\" }";
            method.setRequestEntity(new StringRequestEntity(input, "application/json", null));
            int statusCode = client.executeMethod(method);

            if (statusCode == HttpStatus.SC_OK) {

                String ticket = method.getResponseBodyAsString();
                AlfrescoAuthTicket authTicket = new ObjectMapper().readValue(ticket, AlfrescoAuthTicket.class);
                ticket1 = authTicket.getSuccess();
            }
            if (statusCode == 403) {
                ticket1 = "403";
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            assert method != null;
            method.releaseConnection();
        }
        return ticket1;
    }

    public void downloadFile(String fileURL, String saveDir, String fileName) throws IOException {
        URL url = new URL(fileURL);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        int responseCode = httpConn.getResponseCode();

        // always check HTTP response code first
        if (responseCode == HttpURLConnection.HTTP_OK) {
            String disposition = httpConn.getHeaderField("Content-Disposition");
            String contentType = httpConn.getContentType();
            int contentLength = httpConn.getContentLength();


            System.out.println("Content-Type = " + contentType);
            System.out.println("Content-Disposition = " + disposition);
            System.out.println("Content-Length = " + contentLength);
            System.out.println("fileName = " + fileName);

            // opens input stream from the HTTP connection
            InputStream inputStream = httpConn.getInputStream();
            String saveFilePath = saveDir + fileName;

            // opens an output stream to save into file
            FileOutputStream outputStream = new FileOutputStream(saveFilePath);

            int bytesRead = -1;
            byte[] buffer = new byte[BUFFER_SIZE];
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.close();
            inputStream.close();

            System.out.println("File downloaded");
        } else {
            System.out.println("No file to download. Server replied HTTP code: " + responseCode);
        }
        httpConn.disconnect();
    }


    public String moveFiles(String destinationUrl, String authticket,String fileToMove) {
        HttpClient client = new HttpClient();
        PostMethod method = null;
        String ticket1 = null;
        try {
            String url = this.messageSource.getMessage("alfresco.moveFile", null, "Default alfresco moveFile", null);
            String parentDir = url + destinationUrl + "?alf_ticket=" + authticket;
            method = new PostMethod(parentDir);
            String result = "{" +
                    "   \"nodeRefs\":" +
                    "   [\"workspace://SpacesStore/"+ fileToMove + "\"]" +
                    "}";
            method.setRequestEntity(new StringRequestEntity(result, "application/json", null));
            int statusCode = client.executeMethod(method);

            if (statusCode == HttpStatus.SC_OK) {
                String ticket = method.getResponseBodyAsString();
                AlfrescoAuthTicket authTicket = new ObjectMapper().readValue(ticket, AlfrescoAuthTicket.class);
                ticket1 = authTicket.getData().getTicket();
            }
            if (statusCode == 403) {
                ticket1 = "403";
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (method != null) {
                method.releaseConnection();
            }
        }
        return ticket1;
    }

    public void sendMessgae(String smsUrl) throws IOException {
        URL url = new URL(smsUrl);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        int responseCode = httpConn.getResponseCode();

        // always check HTTP response code first
        if (responseCode == HttpURLConnection.HTTP_OK) {
            System.out.println("Message sent");
        } else {
            System.out.println("Message not sent");
        }
        httpConn.disconnect();
    }

    public void sendMessage(String mobile,String messageBody) throws IOException {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(utilityServices.getMessage("send.sms.url"));
        httpPost.addHeader("User-Agent", "Mozilla/5.0");

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("method", "sms"));
        urlParameters.add(new BasicNameValuePair("api_key",utilityServices.getMessage("sender.sms.api_key")));
        urlParameters.add(new BasicNameValuePair("to",mobile));
        urlParameters.add(new BasicNameValuePair("sender","DATLIF"));
        urlParameters.add(new BasicNameValuePair("message",messageBody));
        urlParameters.add(new BasicNameValuePair("format","json"));
        urlParameters.add(new BasicNameValuePair("custom","1,2"));
        urlParameters.add(new BasicNameValuePair("flash","0"));

        HttpEntity postParams = new UrlEncodedFormEntity(urlParameters);
        httpPost.setEntity(postParams);

        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);

        System.out.println("POST Response Status:: "+ httpResponse.getStatusLine().getStatusCode());

        BufferedReader reader = new BufferedReader(new InputStreamReader(
                httpResponse.getEntity().getContent()));

        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = reader.readLine()) != null) {
            response.append(inputLine);
        }
        reader.close();

        // print result
        System.out.println(response.toString());
        httpClient.close();
    }
}