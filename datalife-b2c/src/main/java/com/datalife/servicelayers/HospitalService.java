package com.datalife.servicelayers;

import com.datalife.entities.*;
import com.datalife.repositories.CommonRepository;
import com.datalife.repositories.HospitalPackagesRepository;
import com.datalife.repositories.UserRepository;
import com.datalife.services.AlfrescoAuthDetails;
import com.datalife.services.CommonServices;
import com.datalife.services.UtilityServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Created by dscblpo on 2/4/2016.
 */
@RestController
@RequestMapping(value = "/services/hospital/")
public class HospitalService {

    @Autowired
    ProviderService providerService;

    @Autowired
    CommonRepository commonRepository;

    @Autowired
    HospitalPackagesRepository packagesRepository;

    @Autowired
    UtilityServices utilityServices;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AlfrescoAuthDetails alfrescoAuthDetails;

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = "getRecordSpecialities", method = RequestMethod.GET, produces = {"application/json"})
    public List<RecordSpeciality> getSpecialities() {
        return CommonServices.getOnlyRecordSpeciality(commonRepository.getRecordSpecialities());
    }

    @RequestMapping(value = "saveHospitalPackages", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> saveHospitalPackages(@RequestBody HospitalPackages hospitalPackages, BindingResult result, ModelMap modelMap) {

        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";
        if(hospitalPackages != null && hospitalPackages.getUserId() != 0L){
           User user = CommonServices.getOnlyUser(userRepository.findOne(hospitalPackages.getUserId()));
            hospitalPackages.setUser(user);
            packagesRepository.save(hospitalPackages);
            List<HospitalPackages>list = packagesRepository.getPackagesByUserId(hospitalPackages.getUserId());
            Map<String,List<HospitalPackages>> map = new HashMap<>();

            for(HospitalPackages packages : list){
                List<HospitalPackages> packagesList = new ArrayList<>();
                String specialityId = packages.getSpeciality();
                String specialityName = commonRepository.getRecordSpecialityById(Long.parseLong(specialityId));
                String procedureName = commonRepository.getTypeOfProcedureById(Long.parseLong(packages.getTypeOfProcedure()));

                packages.setProcedureName(procedureName);
                packages.setSpecialityName(specialityName);
                packages = CommonServices.getOnlyHospitalPackages(packages);
                packagesList.add(packages);
                if(map.containsKey(specialityName)){
                    List<HospitalPackages> list1 = map.get(specialityName);
                    list1.addAll(packagesList);
                    map.put(specialityName,list1);
                }else{
                    map.put(specialityName,packagesList);
                }
            }
            response.put("hospitalPackageList", map);
            status = HttpStatus.OK;
            message = utilityServices.getMessage("Clinic.saveProfile.Message");
        }

        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

}
