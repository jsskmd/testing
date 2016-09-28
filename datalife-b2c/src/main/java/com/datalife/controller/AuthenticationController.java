package com.datalife.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *  Controller class that handles the authentication related requests.
 *
 * Created by supriya gondi on 11/25/2014.
 */
@Controller
@RequestMapping(value = "/auth/")
public class AuthenticationController {
    /**
     * @return view if login failed
     */
    @RequestMapping(value = "failed", method = RequestMethod.GET)
    public String authenticationFailed() {
        return "signin";
    }

    /**
     * @return view if user clicks on logout
     */
    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String authLogout() {
        return "index";
    }

    /**
     * @return view if logout action success
     */
    @RequestMapping(value = "/logout-success", method = RequestMethod.GET)
    public String logoutSuccess() {
        return "index";
    }
}
