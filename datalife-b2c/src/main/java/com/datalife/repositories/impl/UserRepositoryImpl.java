package com.datalife.repositories.impl;

import com.datalife.entities.*;
import com.datalife.repositories.*;
import com.datalife.repositories.custom.UserRepositoryExtension;
import com.datalife.services.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import java.math.BigDecimal;
import java.util.*;
import java.util.List;

/**
 * Created by supriya gondi on 10/24/2014.
 */
@Service
public class UserRepositoryImpl implements UserRepositoryExtension {


    @Autowired
    CommonRepository commonRepository;

    @Autowired
    ClinicDoctorsRepository clinicDoctorsRepository;

    @Autowired
    UtilityServices utilityServices;

    @Autowired
    UserRepository userRepository;

    @Autowired
    DoctorInfoRepository doctorInfoRepository;

    @Autowired
    ClinicInfoRepository clinicInfoRepository;

    @Autowired
    UserContactRepository userContactRepository;

    @Autowired
    UserDetailsRepository userDetailsRepository;

    @Autowired
    AlfrescoAuthDetails alfrescoAuthDetails;

    @Autowired
    private MessageSource messageSource;

    protected static final Logger logger = Logger.getLogger(UserRepositoryImpl.class);


    @Override
    public List<ConfirmAppointment> getPatientAppointmentList(Long userId) {

        List<ConfirmAppointment> list = new ArrayList<>();

        if (userId != 0) {

            Calendar now = Calendar.getInstance();

            now.add(Calendar.MONTH,-1);
            Date before = now.getTime();

            now = Calendar.getInstance();
            now.add(Calendar.MONTH, 3);
            Date after = now.getTime();

            List<ConfirmAppointment> appiontmentList = userRepository.getAppiontmentList(userId, before, after);

            for (ConfirmAppointment appointment : appiontmentList) {

                ClinicInfo clinicInfo = clinicInfoRepository.findByUserId(appointment.getClinicId());
                appointment.setClinicName(clinicInfo.getClinicName());
                DoctorInfo doctorInfo = doctorInfoRepository.findByUserId(appointment.getDoctorId());
                appointment.setDoctorName("Dr. "+doctorInfo.getUser().getUserDetails().getFirstName() + " " + doctorInfo.getUser().getUserDetails().getLastName());

                //server current date
                Date date1 = DateService.stringToDateConversion(DateService.getTodayDate());
                // scheduled date
                Date date2 = DateService.stringToDateConversion(appointment.getDate());


                Calendar cal1 = Calendar.getInstance();
                cal1.setTime(date1);
                Calendar cal2 = Calendar.getInstance();
                cal2.setTime(date2);

                if (cal1.equals(cal2)) {
                    appointment.setCurDate(true);
                }

                if (cal1.after(cal2)) {
                    appointment.setPastDate(false);
                }
                if (cal1.before(cal2)) {
                    appointment.setPastDate(true);
                }
                list.add(appointment);
            }
        }
        return list;
    }

    @Override
    public String getUserData(User user, String ticket) {
        String response = "";
        if (!alfrescoAuthDetails.isTicketValid(ticket)) {
            //get the ticket form patient user
            ticket = alfrescoAuthDetails.getAlfrescoConnectionTicket(user.getUserName(), user.getPassword());
        }
        int bufferQuota = Integer.parseInt(this.messageSource.getMessage("alfresco.userBufferquota", null, "Default alfresco userBufferquota in MB", null));
        String alfrescoUrl = user.getAlfrescoUrl();
        //get patient-user info from alfresco
        String statusCode = alfrescoAuthDetails.getAlfrescoUserDetails(alfrescoUrl, ticket);

        //check the patient-user has enough space to store the report
        if (statusCode != null) {
            try {
                String[] q = statusCode.split(",");
                int MEGABYTES = 1048576;
                Float quota = (float) (Integer.parseInt(q[0]) / MEGABYTES);
                quota = quota - bufferQuota;
                BigDecimal bd = new BigDecimal(Float.parseFloat(q[1]) / MEGABYTES);
                bd = bd.setScale(2, BigDecimal.ROUND_UP);
                Float cz = bd.floatValue();
                if (cz >= quota) {
                    user.setExceeded(true);
                    response = "Your allotted space is exceeded, contact customer care";
                } else {
                    response = cz + " MB of " + quota + " MB is used";
                }
                user.setDataLimit(quota-cz);
                userRepository.save(user);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return response;
    }

    @Override
    public String authenticateUser(User user, ModelMap modelMap) {
        logger.info(" inside method : authenticateUser"+user);
        if (user != null) {
            User u = userRepository.findOne(user.getUserId());
            Map<String, Object> response = new HashMap<String, Object>();
            Integer status = 400;
            String message = "";
            String ticket;
            if (u != null) {
                logger.info(" user Role :"+u.getRole());
                switch(u.getRole()){
                    case RolesInServices.ROLE_PHARMACY:
                        modelMap.addAttribute("user",u);
                        return "pharmacyDashboard";
                    case RolesInServices.ROLE_DIAGNOSTICLABS:
                        modelMap.addAttribute("user", u);
                        return "labDashboard";
                    case RolesInServices.ROLE_ADMIN:
                        return "adminDashboard";
                }

                MyUserDetailsService.setUserInSession(u, userRepository);
                if (RolesInServices.ROLE_HOSPITAL.equals(u.getRole())){
                    return "redirect:" + utilityServices.initialAuthentication(user);
                }

                if (RolesInServices.ROLE_CSADMIN.equals(u.getRole())) {
                    return "csrAdminDashboard";

                }
                if (RolesInServices.ROLE_HOC.equals(u.getRole())) {
                    return "csrHocDashboard";
                }
                if (RolesInServices.ROLE_HOS.equals(u.getRole())) {
                    return "csrHosDashboard";
                }

                modelMap.addAttribute("userId", u.getUserId());
                modelMap.addAttribute("userName", u.getUserName());

                Date creationDate=u.getCreationDate();
                Calendar c = Calendar.getInstance();
                Date now = c.getTime();
                c.add(Calendar.DAY_OF_YEAR, -1);
                Date before = c.getTime();
                if(creationDate!=null){
                    int i= before.compareTo(creationDate);
                    if(i>0){
                        modelMap.addAttribute("grant", false);
                    }else{
                        modelMap.addAttribute("grant", true);
                    }
                }

                modelMap.addAttribute("emailVerified", u.isEmailVerfied());
                modelMap.addAttribute("mobileVerified", u.isMobileVerified());

                MyUserDetailsService.setUserInSession(u, userRepository);
               /* modelMap.addAttribute("userId", u.getUserId());
                modelMap.addAttribute("userName", u.getUserName());*/
                modelMap.addAttribute("role", u.getRole());
                modelMap.addAttribute("email", u.getUserContactInfo().getEmail());
                modelMap.addAttribute("mobilePhone", u.getUserContactInfo().getMobilePhone());
                modelMap.addAttribute("imageUrl", utilityServices.getMessage("User.profileImage.Url") + u.getUserId());
                modelMap.addAttribute("idimageUrl", utilityServices.getMessage("User.identityImage.Url"));
                if (u.getImageThumbnailFileName() != null && !u.getImageThumbnailFileName().isEmpty()) {
                    modelMap.addAttribute("validImage", "true");
                } else {
                    modelMap.addAttribute("validImage", "false");
                }

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                calendar.add(Calendar.DATE, 180);
                Date date = calendar.getTime();
                modelMap.addAttribute("maxDateToShow", DateService.dateToStringConversion(date));

                Calendar calendar2 = Calendar.getInstance();
                calendar2.setTime(new Date());
                calendar2.add(Calendar.DATE, 1);
                Date date1 = calendar2.getTime();
                modelMap.addAttribute("serverDateOne", DateService.dateToStringConversion(date1));

                modelMap.addAttribute("serverDate", DateService.getTodayDate());
                if (u.isEnabled()) {
                    ticket = alfrescoAuthDetails.getAlfrescoConnectionTicket(u.getUserName(), u.getPassword());
                    MyUserDetailsService.getUserFromSession().setAlfresAuthTicket(ticket);
                }

                if (RolesInServices.ROLE_PATIENT.equals(u.getRole())) {
                    return "patientDashboard";
                }

                if (RolesInServices.ROLE_PHARMACY.equals(u.getRole())) {
                    return "pharmacyDashboard";
                }

                if (RolesInServices.ROLE_DOCTOR.equals(u.getRole())) {
                    Map<Long, String> clinics = commonRepository.findClinicsByDoctorId(u.getUserId());
                    /*if (clinics.isEmpty()) {
                        return "physicianDashboard";
                    }*/
                    if (!clinics.isEmpty()) {
                        modelMap.put("clinics", clinics);
                        return "doctorDashboard";
                    }
                }

                if (RolesInServices.ROLE_REFERRALDOCTOR.equals(u.getRole())) {
                    return "referralDoctorDashboard";
                }

                if (RolesInServices.ROLE_CLINIC.equals(u.getRole())) {
                    modelMap.addAttribute("user",u);
                    List<ClinicDoctors> clinicDoctorsList = clinicDoctorsRepository.getDoctorsListByClinicId(u.getUserId());
                    List<User> doctors = new ArrayList<>();
                    if (clinicDoctorsList != null && !clinicDoctorsList.isEmpty()) {
                        for (ClinicDoctors clinicDoctors : clinicDoctorsList) {
                            Long id = clinicDoctors.getDoctorId();
                            if (id != null) {
                                User doctor = userRepository.findOne(id);
                                if (doctor != null) {
                                    User newDoctor = CommonServices.getOnlyUser(doctor);
                                    UserDetails userDetails = CommonServices.getOnlyUserDetails(userDetailsRepository.findByUserId(id));
                                    DoctorInfo doctorInfo = CommonServices.getOnlyDoctorInfo(doctorInfoRepository.findByUserId(id));
                                    UserContactInfo contactInfo = CommonServices.getOnlyUserContactInfo(userContactRepository.findByUserId(id));
                                    newDoctor.setUserDetails(userDetails);
                                    newDoctor.setDoctorInfo(doctorInfo);
                                    newDoctor.setUserContactInfo(contactInfo);
                                    newDoctor.setActivate(clinicDoctors.isActivate());
                                    if (clinicDoctors.getSpecialityId() != null) {
                                        newDoctor.setSpeciality(commonRepository.getSpecialityById(clinicDoctors.getSpecialityId()));
                                    }
                                    if (StringUtils.isNotBlank(doctor.getImageFileName())) {
                                        newDoctor.setSendType(utilityServices.getMessage("User.profileImage.Url") + id);
                                        newDoctor.setDoctorId(id);
                                    } else {
                                        newDoctor.setSendType(utilityServices.getMessage("Doctor.DefaultImage.Url"));
                                        newDoctor.setDoctorId(null);
                                    }
                                    doctors.add(newDoctor);
                                }
                            }
                        }
                        modelMap.addAttribute("doctors", doctors);
                    }

                    return "clinicDashboard";
                }
                if (u.getRole().equals("ROLE_CSR")) {
                    return "csrDashboard";
                }
                response.put("statusCode", status);
                response.put("message", message);
            }
        }
        return "signin";
    }

}
