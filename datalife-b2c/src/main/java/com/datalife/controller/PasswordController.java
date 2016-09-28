package com.datalife.controller;

import com.datalife.entities.User;
import com.datalife.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *  Controller class that handles the password related requests
 *
 * Created by supriya gondi on 11/24/2014.
 */
@Controller
@RequestMapping(value = "/password")
public class PasswordController {


    @Autowired
    UserRepository userRepository;


    @RequestMapping(value = "/{uuid}",method = RequestMethod.GET)
    public String changePassword(@PathVariable String uuid,ModelMap modelMap) {
        String page = null;
        if (uuid != null && !uuid.isEmpty()) {
            User u = userRepository.searchByUuid(uuid);
            if (u != null) {
                page = "resetPassword";
                if(u.getUserName() != null){
                    modelMap.put("userName",u.getUserName());
                    modelMap.put("userId",u.getUserId());
                }
            }else{
                page = "changePassword";
            }

        }
        return page;
    }

    /**
     *
     * @param modelMap
     * @return view if user clicks on forgot Password
     */
    @RequestMapping(value = "/afterResetPassword",method = RequestMethod.GET)
    public String afterResetPassword(ModelMap modelMap)
    {
        return "changePassword";
    }
}
