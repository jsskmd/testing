package com.datalife.repositories.custom;

import com.datalife.entities.*;
import org.springframework.ui.ModelMap;
import java.util.List;


/**
 * Created by supriya gondi on 10/27/2014.
 *
 * This Custom repository handles User entity related Custom logics
 */
public interface UserRepositoryExtension {

    public abstract List<ConfirmAppointment> getPatientAppointmentList(Long userId);

    public  abstract String getUserData(User user, String ticket);

    public abstract String authenticateUser(User user, ModelMap modelMap);

}
