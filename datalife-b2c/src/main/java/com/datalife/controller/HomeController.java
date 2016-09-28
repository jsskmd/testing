package com.datalife.controller;

import com.datalife.entities.User;
import com.datalife.repositories.UserRepository;
import com.datalife.services.MyUserDetailsService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;


/**
 * Controller class that handles the requests for the application home page.
 *
 * @author Supriya Gondi on 10/27/2014.
 */
@Controller
@RequestMapping("/")
public class HomeController {
    @Autowired
    EntityManagerFactory em;
    /**
     * reference to userRepository
     */
    @Autowired
    UserRepository userRepository;

    protected static final Logger logger = Logger.getLogger(HomeController.class);

    /**
     * @param modelMap
     * @return homepage of Datalife Application
     */
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String getHomePage(ModelMap modelMap) {
        return "test";
    }

    @RequestMapping(value = "/clinic", method = RequestMethod.GET)
    public String clinic(ModelMap modelMap) {
        return "clinic";
    }

    @RequestMapping(value = "/subscriber", method = RequestMethod.GET)
    public String subscriber(ModelMap modelMap) {
        return "subscriber";
    }

    @RequestMapping(value = "/doctor", method = RequestMethod.GET)
    public String doctor(ModelMap modelMap) {
        return "doctor";
    }

    @RequestMapping(value = "/services", method = RequestMethod.GET)
    public String getServicesPage(ModelMap modelMap) {
        return "services";
    }

    @RequestMapping(value = "/j_spring_security_check", method = RequestMethod.POST)
    public String springCheck(ModelMap modelMap) {
        return "signin";
    }

    @RequestMapping(value = "/")
    public String userlogin(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout, ModelMap modelMap) {

        logger.info("Inside the Home Controller User Login ");

        if (error != null) {
            modelMap.addAttribute("error", "Invalid username and password! or You are not authorized user for Datalife");
            return "signin";
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (logout != null) {
            if (!(authentication instanceof AnonymousAuthenticationToken)) {
                authentication.setAuthenticated(false);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            modelMap.addAttribute("message", "You have logged out successfully !");
            return "signin";
        }

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            UserDetails userDetails =
                    (UserDetails) authentication.getPrincipal();
            modelMap.addAttribute("userDetails", userDetails);
        }

        User user = MyUserDetailsService.getUserFromSession();
        logger.info("User Login Object"+user);
        return userRepository.authenticateUser(user,modelMap);
    }

    @RequestMapping(value = "/logout")
    public String logout(ModelMap modelMap) {

        EntityManager entityManager = em.createEntityManager();
        entityManager.close();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            authentication.setAuthenticated(false);
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }
       /* return "index";*/
         return "signin";
    }

    @RequestMapping(value = "/authFailed")
    public String loginErrorPage(ModelMap modelMap) {
        modelMap.addAttribute("error", "Invalid User Data !!!");
        return "signin";
    }

    @RequestMapping(value = "/invalidSession")
    public String invalidSessionPage(ModelMap modelMap) {
        return "invalidSession";
    }



    @RequestMapping(value = "hpBookAppt", method = RequestMethod.GET)
    public String gethpBookAppt(ModelMap modelMap) {
        return "hpBookAppt";
    }

    @RequestMapping(value = "appsignIn", method = RequestMethod.GET)
    public String getappsignIn(ModelMap modelMap) {
        return "appsignIn";
    }


    @RequestMapping(value = "terms", method = RequestMethod.GET)
    public String getTermsPage(ModelMap modelMap) {
        return "termsandconditions";
    }

    /**
     * @param modelMap
     * @return view for user to login
     */
    @RequestMapping(value = "forgotPwd", method = RequestMethod.GET)
    public String getForgotPwd(ModelMap modelMap) {
        return "forgotPassword";
    }

    /**
     * @param modelMap
     * @return view for user to login
     */
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String getLogin(ModelMap modelMap) {
        return "signin";
    }

    /**
     * @param modelMap
     * @return view if any error occurs
     */
    @RequestMapping(value = "403", method = RequestMethod.GET)
    public String errorPage(ModelMap modelMap) {
        return "403";
    }

    @RequestMapping(value = "profileImage", method = RequestMethod.GET)
    public String getProfileImage(ModelMap modelMap) {
        return "profileImage";
    }


    @RequestMapping(value = "/profile")
    public String getProfile(ModelMap modelMap) {
        return "";
    }

    @RequestMapping(value = "/vitals")
    public String getVitals(ModelMap modelMap) {
        return "";
    }

    @RequestMapping(value = "/b2blogin", method = RequestMethod.GET)
    public String b2blogin(ModelMap modelMap) {
        modelMap.addAttribute("message", "You've logged out successfully !!!");
        return "b2bsignin";
    }

}


