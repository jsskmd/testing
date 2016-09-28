package com.datalife.servicelayers;

import com.datalife.controller.EmailController;
import com.datalife.entities.ConfirmAppointment;
import com.datalife.entities.SchedulingStatus;
import com.datalife.entities.Search;
import com.datalife.entities.User;
import com.datalife.repositories.AppointmentRepository;
import com.datalife.repositories.CommonRepository;
import com.datalife.repositories.UserRepository;
import com.datalife.services.AlfrescoAuthDetails;
import com.datalife.services.DateService;
import com.datalife.services.UtilityServices;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Controller class that handles the activation related requests
 * <p/>
 * Created by supriya gondi on 11/25/2014.
 */
@RestController
@RequestMapping(value = "/api/user/activation")
public class ActivationService {
    /**
     * reference to userRepository
     */
    @Autowired
    UserRepository userRepository;
    /**
     * reference to emailController
     */
    @Autowired
    EmailController emailController;

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    CommonService commonService;

    @Autowired
    AlfrescoAuthDetails alfrescoAuthDetails;

    @Autowired
    UtilityServices utilityServices;

    @Autowired
    CommonRepository commonRepository;

    /**
     * This method will trigger if user selects activation through mobile while registering
     *
     * @param user
     * @param result
     * @param modelMap
     * @return Map<String, Object> signupDetails includes status,statusCode,message and userDetails
     */
    @RequestMapping(value = "/mobileOTP", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> userActivationThroughMobile(@RequestBody User user, BindingResult result, ModelMap modelMap) throws IOException {
        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "You have entered invalid OTP..Try again!!!";
        if (result.hasErrors()) {
            message = "Invalid User Data!!!";
        }
        Long userId = 0l;
        if (user != null) {
            User u = userRepository.findByUserName(user.getUserName());
            userId = u.getUserId();
            if (u.getOtp().equals(user.getOtp())) {
                userRepository.updateMobileVerified(u.getUserId(), true);/*
                if ("true".equals(user.getFromAppMobActivation())) {
                    u.setEnabled(true);
                    userRepository.updateEnabled(u.getUserId(), u.isEnabled());
                }*/
                response.put("userName", user.getUserName());
                message = "";
                status = HttpStatus.OK;

                if ("true".equals(user.getIsFromAppReg())) {

                    List<ConfirmAppointment> appointment = appointmentRepository.checkPatientAppSched(user.getClinicId(), user.getDoctorId(), userId, new Date());

                    if (appointment.size() <= 2) {
                        ConfirmAppointment appointment1 = appointmentRepository.checkDateTimeHasbeenBookAlr(user.getClinicId(), user.getDoctorId(), user.getDate(), user.getTime());

                        if (appointment1 == null) {
                            ConfirmAppointment confirmAppointment = new ConfirmAppointment();

                            confirmAppointment.setPatientId(userId);
                            confirmAppointment.setDoctorId(user.getDoctorId());
                            confirmAppointment.setClinicId(user.getClinicId());
                            confirmAppointment.setTokenNo(user.getTokenNo());
                            confirmAppointment.setDate(user.getDate());
                            confirmAppointment.setTime(user.getTime());
                            String dateTime = user.getDate() + " " + user.getTime();
                            confirmAppointment.setScheduledOn(DateService.stringToDateDMY(dateTime));
                            confirmAppointment.setCreatedDateTime(DateService.getTodayDateTime());
                            confirmAppointment.setStatus(SchedulingStatus.SCHEDULED);
                            confirmAppointment.setScheduledBy(user.getRole());
                            confirmAppointment.setReasonForVisit("");
                            appointmentRepository.save(confirmAppointment);

                            Search docInf = commonRepository.convertObjToSearchEnty(user.getDoctorId(), user.getClinicId());
                            User patInf = commonRepository.getUserDetails(userId);
                            SimpleDateFormat sdf = new SimpleDateFormat("EEE d MMM yyyy, hh:mm aaa");
                            String mobilePhone = "";
                            if (StringUtils.isNotBlank(docInf.getMobilePhone())) {
                                mobilePhone = docInf.getMobilePhone();
                            }
                            if (StringUtils.isNotBlank(docInf.getHomePhone())) {
                                mobilePhone += " " + docInf.getHomePhone();
                            }
                            String address = docInf.getAddress() + " " + docInf.getLocation() + " " + docInf.getCity();
                            emailController.sendAppointmentConfirmation(docInf.getDoctorName(), docInf.getClinicName(), mobilePhone, address, sdf.format(confirmAppointment.getScheduledOn()), patInf);

                            String messageBody = "Mr/Ms." + patInf.getFlname() + ", Your appointment is scheduled with " + docInf.getDoctorName() + " on " + sdf.format(confirmAppointment.getScheduledOn()) + ".ClinicName: " + docInf.getClinicName() + ",Address:" + address + ".";
                       /* alfrescoAuthDetails.sendMessage(patInf.getMobileNo(),messageBody);*/
                            response.put("slotTime", confirmAppointment.getTime());
                            message = utilityServices.getMessage("user.scheduled.success");
                            status = HttpStatus.CREATED;

                        } else {
                            message = "Time chosen by you is already scheduled";
                            status = HttpStatus.CONTINUE;
                        }
                    } else {
                        message = "You have already booked three appointments with this Doctor,You can book further Appointment after completion of two  scheduled appointments";
                        status = HttpStatus.CONTINUE;
                    }

                }
            } else {
                message = "Enter the correct OTP";
                status = HttpStatus.NON_AUTHORITATIVE_INFORMATION;
            }
        }
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        response.put("userId", userId);
        return response;
    }

    /**
     * This method will trigger if user clicks on Resend OTP
     *
     * @param user
     * @param modelMap
     * @return Map<String, Object> signupDetails includes status,statusCode,message and userDetails
     */
    @RequestMapping(value = "/resend", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> resendOtp(@RequestBody User user, BindingResult result, ModelMap modelMap) {
        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.OK;
        User u = userRepository.findByUserName(user.getUserName());
        if (u != null) {
            Random randomGenerator = new Random();
            Integer randomInt = randomGenerator.nextInt(100000);
            u.setOtp(randomInt.toString());
            emailController.sendActivationCode(u, result, modelMap);

        }
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("userName", user.getUserName());
        return response;
    }
}
