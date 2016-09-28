package com.datalife.servicelayers;

import com.datalife.entities.ClinicDoctors;
import com.datalife.entities.ConfirmAppointment;
import com.datalife.entities.SchedulingStatus;
import com.datalife.entities.Slots;
import com.datalife.repositories.AppointmentRepository;
import com.datalife.repositories.ClinicDoctorsRepository;
import com.datalife.repositories.CommonRepository;
import com.datalife.services.DateService;
import com.datalife.services.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by barath on 6/30/2015.
 */

@RestController
@RequestMapping(value = "/api/custcare/")
public class CustomerCareService {

    @Autowired
    CommonRepository commonRepository;

    @Autowired
    AppointmentService appointmentService;


    @Autowired
    ClinicDoctorsRepository clinicDoctorsRepository;

    @Autowired
    AppointmentRepository appointmentRepository;

/*
    @RequestMapping(value = "getDoctorSlotsOnDate", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> getDoctorSlotsOnDate(@RequestBody ConfirmAppointment confirmAppointment, BindingResult result) throws ParseException {

        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";
        boolean isDoctorOnLeave = false;
        String slotInterval = null;

        ClinicDoctors clinicTimeSlot = new ClinicDoctors();
        List<String> firsttimeSlot = new ArrayList<>();
        List<String> secoundtimeSlot = new ArrayList<>();


        String role = MyUserDetailsService.getUserFromSession().getRole();

        Map<String, Map<String, Slots>> mapMap = new LinkedHashMap<>();
        Map<String, Slots> stringMap = new TreeMap<>(new SlotTimeComparator());

        Format formatter = new SimpleDateFormat("E");

        if(confirmAppointment != null){
            List<ConfirmAppointment> doctorOnLeaveList = appointmentRepository.checkHasAnyMultipleDayCancelForScnOpnDoctor(confirmAppointment.getDoctorId());
            String date1 = DateService.dateToStringConversion(confirmAppointment.getScheduledOn());
            for (ConfirmAppointment appointment : doctorOnLeaveList){
                if(confirmAppointment.getScheduledOn().after(appointment.getVacaStartDate()) && confirmAppointment.getScheduledOn().before(appointment.getVacaEndDate())){
                    isDoctorOnLeave = true;
                }
                if(confirmAppointment.getScheduledOn().equals(appointment.getVacaStartDate()) || confirmAppointment.getScheduledOn().equals(appointment.getVacaEndDate())){
                    isDoctorOnLeave = true;
                }
            }
            if(!isDoctorOnLeave){
                List<ConfirmAppointment> scheduledList = appointmentRepository.getSndOpnDoctorSlotsOnDate(confirmAppointment.getDoctorId(),date1);

                String day = formatter.format(confirmAppointment.getScheduledOn());
                day = day.toUpperCase();
                List<ClinicDoctors> doctorTimeSlotList = clinicDoctorsRepository.getScnOpnDoctorTimeSlot(confirmAppointment.getDoctorId());
                */
/*String date = DateService.dateToStringDate(confirmAppointment.getScheduledOn());*//*

                if (doctorTimeSlotList != null) {
                    for (ClinicDoctors clinicDoctors : doctorTimeSlotList) {
                        if (clinicDoctors.getSlotIntervals() != null) {
                            slotInterval = clinicDoctors.getSlotIntervals().toString();
                        }
                        clinicTimeSlot = commonRepository.checkDayWiseSetting(clinicDoctors);
                    }
                    //get day wise slot times
                    Map<String, List<String>> getTimeSlot = commonRepository.getDailyShifts(clinicTimeSlot);
                    List slots = getTimeSlot.get(day);

                    if (slots.size() != 0) {
                        //get the morning slot time and divide the slot by given interval(slotInterval)
                        String firstSlot = slots.get(0).toString();
                        firsttimeSlot = appointmentService.getPerDaySlot(date1, firstSlot, slotInterval, role);
                        if (slots.size() == 2) {
                            //get the evening slot time and divide the slot by given interval(slotInterval)
                            String secoundSlot = slots.get(1).toString();
                            secoundtimeSlot = appointmentService.getPerDaySlot(date1, secoundSlot, slotInterval, role);
                        }
                    }
                }

                boolean b = appointmentService.checkForStatus(scheduledList, SchedulingStatus.CANCELFIRSTSLOT);
                boolean c = appointmentService.checkForStatus(scheduledList, SchedulingStatus.CANCELSECONDSLOT);
                boolean a = appointmentService.checkForStatus(scheduledList, SchedulingStatus.CANCELDAY);

                if (!(a || (b && c))) {
                    for(ConfirmAppointment appointment : scheduledList){
                        Slots slots = new Slots();
                        slots.setStatus(appointment.getStatus());
                        slots.setPatientId(appointment.getPatientId());
                        slots.setSlotTime(appointment.getTime());
                        slots.setUpdatedBy(appointment.getUpdatedBy());
                        slots.setScheduledBy(appointment.getScheduledBy());

                        boolean firstCancelled = false;
                        boolean secondCancelled = false;
                        switch (appointment.getStatus()) {

                            case CANCELDAY:
                                firstCancelled = true;
                                secondCancelled = true;
                                break;
                            case CANCELFIRSTSLOT:
                                firstCancelled = true;
                                break;
                            case CANCELSECONDSLOT:
                                secondCancelled = true;
                                break;
                        }
                        //which has been cancelled by doctor and clinic, will be blocked.Slot cancelled by patient will be opened
                        for (String time : firsttimeSlot) {
                            if (!stringMap.containsKey(time)) {
                                if (firstCancelled || time.equals(slots.getSlotTime())) {
                                    //if patient has updated the status
                                    if ("ROLE_PATIENT".equals(slots.getUpdatedBy()) || "ROLE_PATIENT".equals(slots.getScheduledBy())) {
                                        //get the status and is equal to scheduled mark slot has scheduled(true) or false(cancelled)
                                        if (SchedulingStatus.SCHEDULED == appointment.getStatus()) {
                                            slots.setValue(true);
                                        } else {
                                            slots.setValue(false);
                                        }
                                    } else {
                                        slots.setValue(true);
                                    }
                                    stringMap.put(time, slots);
                                }
                            }
                        }
                        for (String time : secoundtimeSlot) {
                            if (!stringMap.containsKey(time)) {
                                if (secondCancelled || time.equals(slots.getSlotTime())) {
                                    if ("ROLE_PATIENT".equals(slots.getUpdatedBy()) || "ROLE_PATIENT".equals(slots.getScheduledBy())) {
                                        if (SchedulingStatus.SCHEDULED == appointment.getStatus()) {
                                            slots.setValue(true);
                                        } else {
                                            slots.setValue(false);
                                        }
                                    } else {
                                        slots.setValue(true);
                                    }
                                    stringMap.put(time, slots);
                                }
                            }
                        }
                    }
                    //which has not be scheduled
                    for (String s : firsttimeSlot) {
                        if (!stringMap.containsKey(s)) {
                            Slots slots1 = new Slots(s, null, null, null,null,null);
                            slots1.setValue(false);
                            stringMap.put(s, slots1);
                        }
                    }

                    for (String s : secoundtimeSlot) {
                        if (!stringMap.containsKey(s)) {
                            Slots slots1 = new Slots(s, null, null, null,null,null);
                            slots1.setValue(false);
                            stringMap.put(s, slots1);
                        }
                    }
                    if(stringMap.size()!= 0){
                        mapMap.put(date1, stringMap);
                        response.put("resultedSlots", mapMap);
                        status = HttpStatus.OK;
                    }else{
                        message = "Doctor is not available on this day";
                        status = HttpStatus.CONTINUE;
                    }
                }else{
                    message = "Doctor is not available on this day";
                    status = HttpStatus.CONTINUE;
                }
            }else{
                message = "Doctor is not available on this day";
                status = HttpStatus.CONTINUE;
            }
        }
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }
*/

    class SlotTimeComparator implements Comparator<String> {

        public int compare(String s1, String s2) {

            Date d1 = DateService.stringToTime(s1);
            Date d2 = DateService.stringToTime(s2);
            //ascending order
            return d1.compareTo(d2);
        }
    }


}
