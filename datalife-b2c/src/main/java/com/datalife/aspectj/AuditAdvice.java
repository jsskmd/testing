package com.datalife.aspectj;

import com.datalife.entities.Audit;
import com.datalife.entities.User;
import com.datalife.repositories.AuditRepository;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;

/**
 * Created by Dipak on 2/5/2015.
 */
@Aspect
public class AuditAdvice {

    @Autowired
    AuditRepository auditRepository;

    @After("execution(public * com.datalife.servicelayers.*Service.*(..))")
    public void myAfterAdvice(JoinPoint joinPoint) {
       User user;
        Long patientUserId = 0L, doctorUserId = 0L;
        Long clinicId = 0L;
        Object[] signatureArgs = joinPoint.getArgs();


        for (Object signatureArg : signatureArgs) {
//            if (com.datalife.entities.User.class.equals(signatureArg.getClass()) ){
            if (signatureArg instanceof User) {
                user = (User) signatureArg;
                if (user.getPatientId() != null) {
                    patientUserId = user.getPatientId();
                }

                if (user.getClinicId() != null) {
                    clinicId = user.getClinicId();
                }

                if (user.getDoctorId() != null) {
                    doctorUserId = user.getDoctorId();
                }

            }
        }
        com.datalife.services.UserAuthDetails userDetails = null;
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        HttpServletRequest request = null;
        String ipAddress = null;
        if(sra != null){
            request = sra.getRequest();
            request.getHeader("VIA");
            ipAddress = request.getHeader("X-FORWARDED-FOR");
            if (ipAddress == null) {
                ipAddress = request.getRemoteAddr();
            }


        String currentDateTime = new Timestamp(new java.util.Date().getTime()).toString();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof com.datalife.services.UserAuthDetails) {
            userDetails = (com.datalife.services.UserAuthDetails) principal;
            if (userDetails.getUser().getClinicId() != null) {
                clinicId = userDetails.getUser().getClinicId();
            }
            if (userDetails.getUser().getUserId().equals(patientUserId)) {
                patientUserId = 0L;
            }

            if (userDetails.getUser().getPatientId() != null) {
                patientUserId = userDetails.getUser().getPatientId();
            }
        } else {
            String username = principal.toString();
            auditRepository.save(new Audit(username, clinicId, ipAddress, "", request.getServletPath(), currentDateTime, doctorUserId, patientUserId));
        }

        if (userDetails != null) {
            if ("ROLE_PATIENT".equals(userDetails.getUser().getRole())) {
                patientUserId = userDetails.getUser().getUserId();
            } else if ("ROLE_DOCTOR".equals(userDetails.getUser().getRole())) {
                doctorUserId = userDetails.getUser().getUserId();
            } else if ("ROLE_CLINIC".equals(userDetails.getUser().getRole())) {
                clinicId = userDetails.getUser().getUserId();
            } else if ("ROLE_PROVIDER".equals(userDetails.getUser().getRole())) {

            }
            auditRepository.save(new Audit(userDetails.getUser().getUserName(), clinicId, ipAddress, userDetails.getUser().getRole(), request.getServletPath(), currentDateTime, doctorUserId, patientUserId));
        }
        }
    }
}
