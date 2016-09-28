package com.datalife.servicelayers;

import com.datalife.controller.EmailController;
import com.datalife.entities.User;
import com.datalife.repositories.UserRepository;
import com.datalife.services.AlfrescoAuthDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Controller class that handles the password related requests
 * <p/>
 * Created by supriya gondi on 11/24/2014.
 */
@RestController
@RequestMapping(value = "/api/user/password/")
public class PasswordService {
    /**
     * reference to userRepository
     */
    @Autowired
    UserRepository userRepository;

    @Autowired
    CommonService commonService;

    /**
     * reference to emailController
     */
    @Autowired
    EmailController emailController;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AlfrescoAuthDetails alfrescoAuthDetails;

    @Autowired
    private MessageSource messageSource;

    /**
     * This method will trigger if user clicks on forgot password link
     *
     * @param userName
     * @param modelMap
     * @return Map<String, Object> signupDetails includes status,statusCode,message
     */
    @RequestMapping(value = "forgot/{userName}", method = RequestMethod.GET, produces = "application/json")
    public Map<String, Object> forgotPassword(@PathVariable String userName, ModelMap modelMap) {
        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "UserName is not valid !";
        if (userName != null && !userName.isEmpty()) {
            String uuid="";
            String email = userRepository.getEmailByUserName(userName);
            if (email != null) {
               uuid=UUID.randomUUID().toString();
                userRepository.updateUUID(userName,uuid);
                try{
                   boolean emailSent = emailController.forgotPassword(email, uuid, userName);
                    if(emailSent){
                        message = "Link has been sent to " + email + " to reset the password !";
                        status = HttpStatus.OK;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }


    /**
     * This method will trigger if user clicks on forgot password link
     * @param user
     * @param modelMap
     * @return Map<String, Object> signupDetails includes status,statusCode,message
     */
    @RequestMapping(value = "changePassword", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> changePassword(@RequestBody User user, BindingResult result, ModelMap modelMap) {
        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        if (user != null) {
            Long userId=user.getUserId();
            if (userId != null) {
                User u = userRepository.findOne(userId);

                if (u != null) {

                   String password = passwordEncoder.encode(user.getPassword());
                    u.setPassword(password);
                    u.setUuid("");
                    String ticket = alfrescoAuthDetails.getAlfrescoConnectionTicket(this.messageSource.getMessage("alfresco.admin.userName", null, "Default alfresco admin userName", null), this.messageSource.getMessage("alfresco.admin.password", null, "Default alfresco admin password", null));
                    String results = alfrescoAuthDetails.changePassword(u.getUserName(),password,u.getPassword(),ticket);
                    userRepository.save(u);
                    response.put("userName",u.getUserName());
                    status = HttpStatus.OK;
                }
            }
        }
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        return response;

    }

}
