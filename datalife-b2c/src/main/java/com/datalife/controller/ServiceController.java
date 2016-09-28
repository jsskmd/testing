package com.datalife.controller;

import com.datalife.entities.B2BUser;
import com.datalife.entities.LoginUser;
import com.datalife.repositories.LoginUserRepository;
import com.datalife.servicelayers.HomeService;
import com.datalife.services.UtilityServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Supriya on 12/18/2015.
 */
@Controller
@RequestMapping("/services/")
public class ServiceController {

    @Autowired
    LoginUserRepository loginUserRepository;

    @Autowired
    private UserDetailsManager userDetailsManager;

    @Autowired
    MessageSource messageSource;

    @Autowired
    HomeService homeService;


    @RequestMapping(value = "logout", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json",produces = "application/json")
    public Map<String, Object> logout(@RequestBody B2BUser b2BUser,ModelMap modelMap,HttpServletRequest request,HttpServletResponse httpResponse) {
        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String s = UtilityServices.decodeData(b2BUser.getToken().getBytes());
        if(s!=null) {
            LoginUser loginUser = loginUserRepository.findByToken(s);
            loginUserRepository.delete(loginUser);
            userDetailsManager.deleteUser(s);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (!(authentication instanceof AnonymousAuthenticationToken)) {
                authentication.setAuthenticated(false);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                status = HttpStatus.OK;
            }
        }
        response.put("statusCode", status.value());
        return response;
    }

    @RequestMapping(value = "/services/{token}")
    public ModelAndView servicesPage(@PathVariable String token,ModelMap modelMap) {
        String contextUrl =  this.messageSource.getMessage("b2bservices.accept.url",null,"Default b2bservices accept url",null);
        ModelAndView mav = new ModelAndView(new RedirectView(contextUrl, true));
        mav.addObject("token", token);
        return mav;
    }
}
