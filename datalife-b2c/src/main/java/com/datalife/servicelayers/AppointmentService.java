package com.datalife.servicelayers;

import com.datalife.controller.EmailController;
import com.datalife.entities.*;
import com.datalife.repositories.*;
import com.datalife.services.AlfrescoAuthDetails;
import com.datalife.services.DateService;
import com.datalife.services.MyUserDetailsService;
import com.datalife.services.UtilityServices;
import org.apache.commons.lang.StringUtils;
import org.bouncycastle.crypto.tls.NewSessionTicket;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by barath on 9/18/2015.
 */
@RestController
@RequestMapping(value = "/api/user/appointment")
public class AppointmentService {

    @Autowired
    UtilityServices utilityServices;

    @Autowired
    ClinicDoctorsRepository clinicDoctorsRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    CommonRepository commonRepository;

    @Autowired
    CommonService commonService;

    @Autowired
    EmailController emailController;

    @Autowired
    AlfrescoAuthDetails alfrescoAuthDetails;

    @Autowired
    PasswordEncoder passwordEncoder;

    @RequestMapping(value = "getDoctorSlotsOnDate", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> getDoctorSlotsOnDate(@RequestBody ConfirmAppointment confirmAppointment, BindingResult result) throws ParseException {

        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";
        boolean isDoctorOnLeave = false;
        String slotInterval = "15";

        ClinicDoctors clinicTimeSlot = new ClinicDoctors();
        List<PerDaySlots> firsttimeSlot = new ArrayList<>();
        List<PerDaySlots> secoundtimeSlot = new ArrayList<>();
        String role = null;
        if(MyUserDetailsService.getUserFromSession() != null){
            role = MyUserDetailsService.getUserFromSession().getRole();
        }

        Map<String, Map<String, Slots>> mapMap = new LinkedHashMap<>();
        Format formatter = new SimpleDateFormat("E");

            List<ConfirmAppointment> doctorOnLeaveList = appointmentRepository.checkHasAnyMultipleDayCancel(confirmAppointment.getClinicId(),confirmAppointment.getDoctorId());
            List<ConfirmAppointment> scheduledList = new ArrayList<>();

            // value is true(indicate to fetch the current day slot)
            if(confirmAppointment.isValue()){
                confirmAppointment.setScheduledOn(new Date());
            }
            String date1 = DateService.dateToStringConversion(confirmAppointment.getScheduledOn());

            for (ConfirmAppointment appointment : doctorOnLeaveList){
                //check scheduled date ie(requested date) is in between of vacastartDate and vacaendDate
                if(confirmAppointment.getScheduledOn().after(appointment.getVacaStartDate()) && confirmAppointment.getScheduledOn().before(appointment.getVacaEndDate())){
                    isDoctorOnLeave = true;
                    message = "Doctor is not available from "+DateService.dateToStringConversion(appointment.getVacaStartDate())+ " to "+DateService.dateToStringConversion(appointment.getVacaEndDate());
                }
                //check scheduled date ie(requested date) is equal to vacastartDate and vacaendDate
                if(confirmAppointment.getScheduledOn().equals(appointment.getVacaStartDate()) || confirmAppointment.getScheduledOn().equals(appointment.getVacaEndDate())){
                    isDoctorOnLeave = true;
                    message = "Doctor is not available from "+DateService.dateToStringConversion(appointment.getVacaStartDate())+ " to "+DateService.dateToStringConversion(appointment.getVacaEndDate());
                }
            }
            //if Doctor is not on leave then get the scheduled slot
            if(!isDoctorOnLeave){
                //Db call to get the scheduled slot based on doctorId,clinicId,and date
                List<ConfirmAppointment>  arrayList = appointmentRepository.getDoctorSlotsOnDate(confirmAppointment.getClinicId(), confirmAppointment.getDoctorId(),date1);
               /* List<ConfirmAppointment> list = appointmentRepository.getDoctorBlocked(confirmAppointment.getClinicId(), confirmAppointment.getDoctorId(), date1);
                arrayList.addAll(list);*/

                TreeSet<ConfirmAppointment> set = new TreeSet<>(new GetLatestUpdateSlotAndRemoveOld());
                set.addAll(arrayList);
                scheduledList.addAll(set);

                String day = formatter.format(confirmAppointment.getScheduledOn());
                day = day.toUpperCase();

                //get the doctor time slot from clinicDoctor table
                List<ClinicDoctors> doctorTimeSlotList = clinicDoctorsRepository.getDoctorTimeSlot(confirmAppointment.getDoctorId(), confirmAppointment.getClinicId());

                if (doctorTimeSlotList != null) {
                    for (ClinicDoctors clinicDoctors : doctorTimeSlotList) {
                        if (clinicDoctors.getSlotTime() != null && !clinicDoctors.getSlotTime().isEmpty()) {
                            slotInterval = clinicDoctors.getSlotTime();
                        }

                        //check for change in slot timing and update it
                        if (clinicDoctors.getChangeSetEfectFrm() != null ) {
                            DateTime scheduledon = new DateTime(confirmAppointment.getScheduledOn()).withTimeAtStartOfDay();
                            DateTime setEffectedDate = new DateTime(clinicDoctors.getChangeSetEfectFrm()).withTimeAtStartOfDay();

                            if (setEffectedDate.compareTo(scheduledon) == 0 || scheduledon.compareTo(setEffectedDate) > 0) {
                                NewTimings timeSettings = clinicDoctors.getNewTimings();
                                if (timeSettings != null) {
                                    clinicDoctors.setSlotTime(timeSettings.getSlotTime());

                                    clinicDoctors.setMonStatus_1(timeSettings.isMonStatus_1());
                                    clinicDoctors.setMonst_1(timeSettings.getMonst_1());
                                    clinicDoctors.setMonet_1(timeSettings.getMonet_1());

                                    clinicDoctors.setMonStatus_2(timeSettings.isMonStatus_2());
                                    clinicDoctors.setMonst_2(timeSettings.getMonst_2());
                                    clinicDoctors.setMonet_2(timeSettings.getMonet_2());

                                    clinicDoctors.setTueStatus_1(timeSettings.isTueStatus_1());
                                    clinicDoctors.setTuest_1(timeSettings.getTuest_1());
                                    clinicDoctors.setTueet_1(timeSettings.getTueet_1());

                                    clinicDoctors.setTueStatus_2(timeSettings.isTueStatus_2());
                                    clinicDoctors.setTuest_2(timeSettings.getTuest_2());
                                    clinicDoctors.setTueet_2(timeSettings.getTueet_2());

                                    clinicDoctors.setWedStatus_1(timeSettings.isWedStatus_1());
                                    clinicDoctors.setWedst_1(timeSettings.getWedst_1());
                                    clinicDoctors.setWedet_1(timeSettings.getWedet_1());

                                    clinicDoctors.setWedStatus_2(timeSettings.isWedStatus_2());
                                    clinicDoctors.setWedst_2(timeSettings.getWedst_2());
                                    clinicDoctors.setWedet_2(timeSettings.getWedet_2());

                                    clinicDoctors.setThuStatus_1(timeSettings.isThuStatus_1());
                                    clinicDoctors.setThust_1(timeSettings.getThust_1());
                                    clinicDoctors.setThuet_1(timeSettings.getThuet_1());

                                    clinicDoctors.setThuStatus_2(timeSettings.isThuStatus_2());
                                    clinicDoctors.setThust_2(timeSettings.getThust_2());
                                    clinicDoctors.setThuet_2(timeSettings.getThuet_2());

                                    clinicDoctors.setFriStatus_1(timeSettings.isFriStatus_1());
                                    clinicDoctors.setFrist_1(timeSettings.getFrist_1());
                                    clinicDoctors.setFriet_1(timeSettings.getFriet_1());

                                    clinicDoctors.setFriStatus_2(timeSettings.isFriStatus_2());
                                    clinicDoctors.setFrist_2(timeSettings.getFrist_2());
                                    clinicDoctors.setFriet_2(timeSettings.getFriet_2());

                                    clinicDoctors.setSatStatus_1(timeSettings.isSatStatus_1());
                                    clinicDoctors.setSatst_1(timeSettings.getSatst_1());
                                    clinicDoctors.setSatet_1(timeSettings.getSatet_1());

                                    clinicDoctors.setSatStatus_2(timeSettings.isSatStatus_2());
                                    clinicDoctors.setSatst_2(timeSettings.getSatst_2());
                                    clinicDoctors.setSatet_2(timeSettings.getSatet_2());

                                    clinicDoctors.setSunStatus_1(timeSettings.isSunStatus_1());
                                    clinicDoctors.setSunst_1(timeSettings.getSunst_1());
                                    clinicDoctors.setSunet_1(timeSettings.getSunet_1());

                                    clinicDoctors.setSunStatus_2(timeSettings.isSunStatus_2());
                                    clinicDoctors.setSunst_2(timeSettings.getSunst_2());
                                    clinicDoctors.setSunet_2(timeSettings.getSunet_2());

                                    clinicDoctors.setSlotTime(timeSettings.getSlotTime());
                                    slotInterval = clinicDoctors.getSlotTime();
                                    Date curDate = new DateTime(new Date()).withTimeAtStartOfDay().toDate();

                                    if(setEffectedDate.toDate().compareTo(curDate) == 0 || curDate.compareTo(setEffectedDate.toDate()) > 0){
                                        /*clinicDoctors.setChangeTimingAfter(false);*/
                                        clinicDoctors.setChangeSetEfectFrm(null);
                                        clinicDoctors.setNewTimings(null);
                                        clinicDoctorsRepository.save(clinicDoctors);
                                       /* newTimingsRepository.delete(clinicDoctors.getNewTimings().getSettingsId());*/
                                    }
                                }
                            }
                        }
                        //pass the clinicdoctors object to check for null and to gat week days doctor is working
                        clinicTimeSlot = commonRepository.checkDayWiseSetting(clinicDoctors);
                    }
                    //get day wise slot times
                    Map<String, List<String>> getTimeSlot = commonRepository.getDailyShifts(clinicTimeSlot);
                    List<String> slots = getTimeSlot.get(day);

                    if (slots.size() != 0) {
                        int tokenNo = 0;
                        //get the morning slot time and divide the slot by given interval(slotInterval)
                        String firstSlot = slots.get(0);
                        firsttimeSlot = getPerDaySlot(date1, firstSlot, slotInterval,tokenNo,confirmAppointment.isValue());
                        PerDaySlots daySlots = firsttimeSlot.get(firsttimeSlot.size()-1);
                        tokenNo = daySlots.getTokenNo();
                        if (slots.size() == 2) {
                            //get the evening slot time and divide the slot by given interval(slotInterval)
                            String secoundSlot = slots.get(1);
                            secoundtimeSlot = getPerDaySlot(date1, secoundSlot, slotInterval,tokenNo,confirmAppointment.isValue());
                        }
                    }
                }

                Map<String, Slots> stringMap =  getslots(firsttimeSlot,secoundtimeSlot,scheduledList,isDoctorOnLeave);
                //stringMap.size() = 0 indicates that doctor will not be working on that day
                if (stringMap.size() != 0) {
                    mapMap.put(date1, stringMap);
                    response.put("resultedSlots", mapMap);
                    status = HttpStatus.OK;
                } else {
                    message = date1+'('+day+')'+" Doctor will not be available on this day";
                    status = HttpStatus.CONTINUE;
                }
            }else{
                status = HttpStatus.CONTINUE;
            }

        DateTime maxDateToShow = new DateTime(new Date()).withTimeAtStartOfDay().plusDays(180);
        response.put("maxDateToShow", DateService.dateToStringConversion(maxDateToShow.toDate()));

        DateTime pastDateToShow = new DateTime(new Date()).withTimeAtStartOfDay().minusDays(180);
        response.put("pastDateToShow", DateService.dateToStringConversion(pastDateToShow.toDate()));

        DateTime serverDate = new DateTime(new Date()).withTimeAtStartOfDay().plusDays(1);
        response.put("serverDate",  DateService.dateToStringConversion(serverDate.toDate()));

        DateTime pastDate = new DateTime(new Date()).withTimeAtStartOfDay().minusDays(1);
        response.put("pastDate",  DateService.dateToStringConversion(pastDate.toDate()));

        response.put("curDate",  DateService.dateToStringConversion(new Date()));
        //fetch the doctor firstName and speciality
        Search doctorDetails = commonRepository.convertObjToSearchEnty(confirmAppointment.getDoctorId(),confirmAppointment.getClinicId());
        response.put("doctorDetails", doctorDetails);

        if(role == null || "ROLE_DOCTOR".equals(role)){
            response.put("showNewEncounter", true);
        }

        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    @RequestMapping(value = "updateStatus", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public synchronized Map<String, Object> updateStatus(@RequestBody ConfirmAppointment appointment, BindingResult result, ModelMap modelMap) throws IOException {
        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";
        String role = MyUserDetailsService.getUserFromSession().getRole();
        if (appointment != null) {

            // value is true(indicate to fetch the current day slot)
            if(appointment.isValue()){
                appointment.setDate(DateService.getTodayDate());
            }
            if (appointment.getClinicId() != null && appointment.getDoctorId() != null && appointment.getPatientId() != null && appointment.getDate() != null && appointment.getTime() != null) {

                //db call to fetch the id of object that match the parameter
                ConfirmAppointment updateStatus = appointmentRepository.getId(appointment.getClinicId(), appointment.getDoctorId(), appointment.getPatientId(), appointment.getDate(), appointment.getTime());

                //if object is present update the status
                if (updateStatus != null) {
                    updateStatus.setUpdatedBy(role);
                    updateStatus.setStatus(appointment.getStatus());
                    appointmentRepository.save(updateStatus);

                    Search docInfo = commonRepository.convertObjToSearchEnty(appointment.getDoctorId(),appointment.getClinicId());
                    String mobilePhone = "";
                    if (StringUtils.isNotBlank(docInfo.getMobilePhone())) {
                        mobilePhone = docInfo.getMobilePhone();
                    }
                    if (StringUtils.isNotBlank(docInfo.getHomePhone())) {
                        mobilePhone += " " + docInfo.getHomePhone();
                    }

                    List<ConfirmAppointment> appointmentList = new ArrayList<>();
                    if(appointment.isValue()){
                        if(appointment.getStatus().equals(SchedulingStatus.INPROGRESS)  || appointment.getStatus().equals(SchedulingStatus.COMPLETED)){

                            //update the status for patients in next 1hr
                           /* Calendar calendar = Calendar.getInstance();
                            calendar.add(Calendar.HOUR_OF_DAY,1);
                            Date date2 = calendar.getTime();*/
                            DateTime dateTime = new DateTime(new Date()).plusHours(1);
                            appointmentList = appointmentRepository.getPatientToUpdateStatus(appointment.getClinicId(), appointment.getDoctorId(), appointment.getDate(),dateTime.toDate(),appointment.getCurTokenNo());

                            for(ConfirmAppointment appointment1 : appointmentList){
                                User user = commonRepository.getUserDetails(appointment1.getPatientId());
                                emailController.updateStatusToScheduledPatient(docInfo.getDoctorName(),mobilePhone,docInfo.getClinicName(),appointment1.getTokenNo(),appointment.getCurTokenNo(),user);
                                String messageBody =" Mr/Ms."+user.getFlname()+",The current token no."+appointment.getCurTokenNo()+" is in progress with "+docInfo.getDoctorName()+".Your Token# : "+appointment1.getTokenNo()+"";
                                /*String messageUrl = utilityServices.getMessage("send.sms.url")+mobilePhone+utilityServices.getMessage("sender.sms.id")+messageBody+utilityServices.getMessage("send.sms.format");*/
                                alfrescoAuthDetails.sendMessage(user.getMobileNo(),messageBody);
                            }
                        }
                    }
                    if(SchedulingStatus.CANCELLED.equals(appointment.getStatus())){
                        User patInfo = commonRepository.getUserDetails(appointment.getPatientId());
                        String date = appointment.getDate()+" "+appointment.getTime();
                        emailController.sendCancelConfirmation(docInfo.getDoctorName(),docInfo.getClinicName(),mobilePhone,date,patInfo.getFlname(),patInfo.getEmail());
                        String messageBody ="Mr/Ms. "+patInfo.getFlname()+", Your scheduled appointment with "+docInfo.getDoctorName()+" on "+date+" has been cancelled. DataLife";
                       /* String messageUrl = utilityServices.getMessage("send.sms.url")+mobilePhone+utilityServices.getMessage("sender.sms.id")+messageBody+utilityServices.getMessage("send.sms.format");*/
                        alfrescoAuthDetails.sendMessage(patInfo.getMobileNo(),messageBody);

                    }
                }
                status = HttpStatus.OK;
                message = "Status updated successfully !!!";
            }
        }
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    @RequestMapping(value = "getSlotsBtwDateForAllStatus", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> getTimeSlot(@RequestBody ConfirmAppointment user, BindingResult result) throws ParseException {

        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";
        Map<String, Map<String, Slots>> mapMap = new LinkedHashMap<>();
        ClinicDoctors clinicTimeSlot = new ClinicDoctors();
        String slotInterval = "15";

        List<PerDaySlots> firsttimeSlot = new ArrayList<>();
        List<PerDaySlots> secoundtimeSlot = new ArrayList<>();
        String[] days = {"", "MON", "TUE", "WED", "THU", "FRI", "SAT","SUN"};

       /*String role = MyUserDetailsService.getUserFromSession().getRole();*/

        boolean isvacaStartDate = false;

        List<ConfirmAppointment> confirmedAppointment = new ArrayList<>();
        List<ConfirmAppointment> doctorBlocked = new ArrayList<>();
        List<ConfirmAppointment> doctorOnLeave = new ArrayList<>();


            DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/YYYY");
            DateTime before = formatter.parseDateTime(user.getBefore()).withTimeAtStartOfDay();
            DateTime after = formatter.parseDateTime(user.getAfter()).withTimeAtStartOfDay();

            DateTime fromD = new DateTime(before).minusDays(1);
            DateTime toD = new DateTime(after).plusDays(1);
            int daysToDisplay = Days.daysBetween(before, after).getDays();

            doctorOnLeave = appointmentRepository.getMultipleDayBlocked(user.getDoctorId(),user.getClinicId(),fromD.toDate(),toD.toDate());
            doctorBlocked = appointmentRepository.getDoctorBlockedFromPrev(user.getDoctorId(), user.getClinicId(), fromD.toDate(),toD.toDate());

            List<ConfirmAppointment> arrayList = new ArrayList<ConfirmAppointment>();
            if(daysToDisplay == 0){
                String date1 = DateService.dateToStringConversion(before.toDate());
                arrayList = appointmentRepository.getDoctorSlotsOnDate(user.getClinicId(), user.getDoctorId(), date1);
            }else{
                arrayList = appointmentRepository.getConfirmedAppointmentFromPrev(user.getDoctorId(), user.getClinicId(),fromD.toDate(),toD.toDate());
            }

            TreeSet<ConfirmAppointment> set = new TreeSet<>(new GetLatestUpdateSlotAndRemoveOld());
            set.addAll(arrayList);
            confirmedAppointment.addAll(set);

            if (doctorBlocked.size() != 0) {
                confirmedAppointment.addAll(doctorBlocked);
            }


            Map<String, TreeMap<SchedulingStatus, List<Slots>>> confirmAppointmentMap = new LinkedHashMap<>();
            for (ConfirmAppointment appointment : confirmedAppointment) {
                String date = appointment.getDate();
                SchedulingStatus appStatus = appointment.getStatus();
                String updated = appointment.getUpdatedBy();
                String scheduledBy = appointment.getScheduledBy();
                String patientName = appointment.getPatientName();
                int tokenNo = appointment.getTokenNo();
                Slots slots = new Slots(appointment.getTime(), appointment.getPatientId(), updated,scheduledBy,appStatus,patientName,tokenNo);

                TreeMap<SchedulingStatus, List<Slots>> statusMap = new TreeMap<>();
                List<Slots> slotsList = new LinkedList<>();

                if (confirmAppointmentMap.containsKey(date)) {
                    TreeMap<SchedulingStatus, List<Slots>> innerstatusMap = confirmAppointmentMap.get(date);
                    if (innerstatusMap.containsKey(appStatus)) {
                        List<Slots> innerslotsList = innerstatusMap.get(appStatus);
                        if (!innerslotsList.isEmpty()) {
                            innerslotsList.add(slots);
                            slotsList = innerslotsList;
                        } else {
                            slotsList.add(slots);
                        }
                        innerstatusMap.put(appStatus, slotsList);
                        statusMap = innerstatusMap;
                    } else {
                        slotsList.add(slots);
                        statusMap = innerstatusMap;
                        statusMap.put(appStatus, slotsList);
                    }

                } else {
                    slotsList.add(slots);
                    statusMap.put(appStatus, slotsList);
                }
                confirmAppointmentMap.put(date, statusMap);
            }

            List<ClinicDoctors> doctorTimeSlotList = clinicDoctorsRepository.getDoctorTimeSlot(user.getDoctorId(), user.getClinicId());
            if (doctorTimeSlotList != null) {
                DateTime setEffectedDate = null;
                for (ClinicDoctors clinicDoctors : doctorTimeSlotList) {
                    if (clinicDoctors.getSlotTime() != null && !clinicDoctors.getSlotTime().isEmpty()) {
                        slotInterval = String.valueOf(clinicDoctors.getSlotTime());
                    }
                    clinicTimeSlot = commonRepository.checkDayWiseSetting(clinicDoctors);
                    if(clinicDoctors.getChangeSetEfectFrm()!= null ){
                        setEffectedDate = new DateTime(clinicDoctors.getChangeSetEfectFrm()).withTimeAtStartOfDay();
                    }
                }

                Map<String, Slots> stringMap = null;
                for (int i = 0; i <= daysToDisplay; i++) {
                    List<Slots> cancelSlots = new ArrayList<>();
                    stringMap = new TreeMap<>(new SlotTimeComparator());
                    DateTime dateTime = new DateTime(before).plusDays(i);
                    int day1 = dateTime.getDayOfWeek();

                    String date = dateTime.toString("dd/MM/YYYY");
                    String day = days[day1];
                    boolean isDoctorOnLeave = false;
                    if(setEffectedDate != null){
                        if (setEffectedDate.compareTo(dateTime) == 0 || dateTime.compareTo(setEffectedDate) > 0) {
                            System.out.print(setEffectedDate);
                            for (ClinicDoctors clinicDoctors : doctorTimeSlotList) {
                                clinicDoctors = getClinicDoctorsTimings(clinicDoctors);
                                clinicTimeSlot = commonRepository.checkDayWiseSetting(clinicDoctors);
                            }
                        }
                    }

                    //checking for multiple days Leave and put to confirmAppointmentMap
                    for(ConfirmAppointment dayCancel :doctorOnLeave){
                       /* Date date1 = DateService.stringToDateConversion(date);*/
                        if(dayCancel.getVacaStartDate().equals(dateTime.toDate()) || dayCancel.getVacaEndDate().equals(dateTime.toDate()) ){
                            Slots slots1 = new Slots();
                            slots1.setStatus(SchedulingStatus.ONLEAVE);
                            slots1.setAfter( dayCancel.getVacaEndDate());
                            slots1.setBefore(dayCancel.getVacaStartDate());
                            stringMap.put("12:00 AM",slots1);
                            isDoctorOnLeave = true;
                        }

                        if(dayCancel.getVacaStartDate().compareTo(dateTime.toDate()) * dateTime.toDate().compareTo(dayCancel.getVacaEndDate()) > 0){
                            Slots slots1 = new Slots();
                            slots1.setStatus(SchedulingStatus.ONLEAVE);
                            slots1.setAfter( dayCancel.getVacaEndDate());
                            slots1.setBefore(dayCancel.getVacaStartDate());
                            stringMap.put("12:00 AM",slots1);
                            isDoctorOnLeave = true;
                        }
                    }

                    if(!isDoctorOnLeave) {
                        //get day wise slot times
                        Map<String, List<String>> getTimeSlot = commonRepository.getDailyShifts(clinicTimeSlot);
                        List<String> slots = getTimeSlot.get(day);

                        if (slots.size() != 0) {
                            int tokenNo = 0;
                            //get the morning slot time and divide the slot by given interval(slotInterval)
                            String firstSlot = slots.get(0);
                            firsttimeSlot = getPerDaySlot(date, firstSlot, slotInterval,tokenNo,user.isValue());
                            PerDaySlots daySlots = firsttimeSlot.get(firsttimeSlot.size()-1);
                            tokenNo = daySlots.getTokenNo();
                            if (slots.size() == 2) {
                                //get the evening slot time and divide the slot by given interval(slotInterval)
                                String secoundSlot = slots.get(1);
                                secoundtimeSlot = getPerDaySlot(date, secoundSlot, slotInterval,tokenNo,user.isValue());
                            }
                        }

                        //get all the blocked date and slot by patient,doctor,clinic
                        TreeMap<SchedulingStatus, List<Slots>> appointment1 = confirmAppointmentMap.get(date);
                        List<Slots> schduledSlots = new ArrayList<>();
                        boolean isCancelDay = true;

                        if (appointment1 != null && !appointment1.isEmpty()) {
                            //check same day there is CANCELDAY or ONLEAVE
                            for (SchedulingStatus appStatus : appointment1.keySet()) {
                                Slots slots2 = new Slots();
                                slots2.setStatus(appStatus);
                                cancelSlots.add(slots2);
                            }

                            boolean a = containsStatus(cancelSlots, SchedulingStatus.CANCELDAY);
                            boolean m = containsStatus(cancelSlots, SchedulingStatus.ONLEAVE);

                            //checking for status
                           /* if (a || (b && c) || m) {*/
                            if (a || m) {
                                Slots slt = new Slots();
                                slt.setStatus(SchedulingStatus.CANCELDAY);
                                slt.setMessage("Doctor is not available");
                                stringMap.put("12:00 AM", slt);
                                isCancelDay = false;
                            } else {
                                for (SchedulingStatus appStatus : appointment1.keySet()) {
                                    schduledSlots = appointment1.get(appStatus);

                                    boolean firstCancelled = false;
                                    boolean secondCancelled = false;
                                    switch (appStatus) {

                                        case CANCELDAY:
                                            firstCancelled = true;
                                            secondCancelled = true;
                                            break;
                                    }
                                    //which has been cancelled by doctor and clinic, will be blocked.Slot cancelled by patient will be opened
                                    for (PerDaySlots time : firsttimeSlot) {
                                        if(time.getSlot() != null){
                                            if (!stringMap.containsKey(time.getSlot())) {
                                                for ( Slots slts1 : schduledSlots) {
                                                    if (firstCancelled || time.getSlot().equals(slts1.getSlotTime())) {

                                                        switch (slts1.getStatus()) {

                                                            case SCHEDULED:
                                                            case ARRIVED:
                                                            case INPROGRESS:
                                                            case COMPLETED:
                                                                slts1.setValue(true);
                                                                slts1.setStatus(slts1.getStatus());
                                                                break;
                                                            case CANCELLED:
                                                                if("ROLE_PATIENT".equals(slts1.getUpdatedBy())){
                                                                    slts1.setStatus(null);
                                                                    slts1.setValue(false);
                                                                }else{
                                                                    slts1.setStatus(slts1.getStatus());
                                                                    slts1.setValue(false);
                                                                }
                                                                break;
                                                        }

                                                        slts1.setTokenNo(time.getTokenNo());
                                                        stringMap.put(time.getSlot(), slts1);
                                                    }
                                                }
                                            }
                                        }

                                    }
                                    for (PerDaySlots time : secoundtimeSlot) {
                                        if(time.getSlot() != null){
                                            if (!stringMap.containsKey(time.getSlot())) {
                                                for (Slots slts1 : schduledSlots)
                                                    if (secondCancelled || time.getSlot().equals(slts1.getSlotTime())) {

                                                        switch (slts1.getStatus()) {

                                                            case SCHEDULED:
                                                            case ARRIVED:
                                                            case INPROGRESS:
                                                            case COMPLETED:
                                                                slts1.setValue(true);
                                                                slts1.setStatus(slts1.getStatus());
                                                                break;
                                                            case CANCELLED:
                                                                if("ROLE_PATIENT".equals(slts1.getUpdatedBy())){
                                                                    slts1.setStatus(null);
                                                                    slts1.setValue(false);
                                                                }else{
                                                                    slts1.setStatus(slts1.getStatus());
                                                                    slts1.setValue(false);
                                                                }
                                                                break;
                                                        }
                                                        slts1.setTokenNo(time.getTokenNo());
                                                        stringMap.put(time.getSlot(), slts1);
                                                    }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        //which has not be scheduled
                        if (isCancelDay) {
                            for (PerDaySlots s : firsttimeSlot) {
                                if(s.getSlot() != null){
                                    if (!stringMap.containsKey(s.getSlot())) {
                                        Slots confirmAppointment = new Slots(s.getSlot(), null, null, null, null,null,0);
                                        confirmAppointment.setValue(false);
                                        confirmAppointment.setTokenNo(s.getTokenNo());
                                        stringMap.put(s.getSlot(), confirmAppointment);
                                    }
                                }
                            }
                            for (PerDaySlots s : secoundtimeSlot) {
                                if(s.getSlot() != null){
                                    if (!stringMap.containsKey(s.getSlot())) {
                                        Slots confirmAppointment = new Slots(s.getSlot(), null, null, null, null,null,0);
                                        confirmAppointment.setValue(false);
                                        confirmAppointment.setTokenNo(s.getTokenNo());
                                        stringMap.put(s.getSlot(), confirmAppointment);
                                    }
                                }
                            }
                        }
                    }
                    if(!isDoctorOnLeave){
                        if(stringMap.size()!= 0){
                            mapMap.put(date, stringMap);
                        }
                        firsttimeSlot.clear();
                        secoundtimeSlot.clear();
                    } else{
                        if(!isvacaStartDate){
                            mapMap.put(date, stringMap);
                            isvacaStartDate = true;
                        }
                    }
                }
                if(mapMap.size()!= 0){

                    response.put("resultedSlots", mapMap);
                    status = HttpStatus.OK;

                }else{
                    message = "Doctor has not set the time slot";
                    status = HttpStatus.PROCESSING;
                }
            }

        response.put("message", message);
        response.put("statusCode", status.value());
        return response;
    }

    @RequestMapping(value = "getDoctorSlotsBasedOnDateAndStatus", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> getDoctorSlotsBasedOnDateAndStatus(@RequestBody ConfirmAppointment confirmAppointment, BindingResult result) throws ParseException {

        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";

            ClinicDoctors clinicDoctors = clinicDoctorsRepository.findByDoctorAndClinic(confirmAppointment.getClinicId(),confirmAppointment.getDoctorId());

            if(clinicDoctors.isMonStatus_1() || clinicDoctors.isMonStatus_2()
                    || clinicDoctors.isTueStatus_1() || clinicDoctors.isTueStatus_2() || clinicDoctors.isWedStatus_1() || clinicDoctors.isWedStatus_2()
                    || clinicDoctors.isThuStatus_1() || clinicDoctors.isThuStatus_2() || clinicDoctors.isFriStatus_1() || clinicDoctors.isFriStatus_2()
                    || clinicDoctors.isSatStatus_1() || clinicDoctors.isSatStatus_2() || clinicDoctors.isSunStatus_1() || clinicDoctors.isSunStatus_2()) {

                List<ConfirmAppointment> getScheduledpatients;

                Date before = DateService.stringToDateConversion(confirmAppointment.getBefore());
                Date after = DateService.stringToDateConversion(confirmAppointment.getAfter());


                if(confirmAppointment.isPastDate()){
                    //db call to get all the past scheduled status
                    List<ConfirmAppointment> pastScheduledList = appointmentRepository.updatescheduledToNoshowForPastDate(confirmAppointment.getClinicId(), confirmAppointment.getDoctorId());
                    //update the scheduled status to noshow and Arrived,Inprogess to Completed
                    for (ConfirmAppointment appointment : pastScheduledList) {
                        if(SchedulingStatus.SCHEDULED.equals(appointment.getStatus())){
                            appointment.setStatus(SchedulingStatus.NOSHOW);
                            appointmentRepository.save(appointment);
                        }
                        if(SchedulingStatus.ARRIVED.equals(appointment.getStatus()) || SchedulingStatus.INPROGRESS.equals(appointment.getStatus())){
                            appointment.setStatus(SchedulingStatus.COMPLETED);
                            appointmentRepository.save(appointment);
                        }
                    }
                }

                if (before.equals(after) ) {
                    getScheduledpatients = appointmentRepository.getDoctorSlotEqualOnDate(confirmAppointment.getClinicId(), confirmAppointment.getDoctorId(),confirmAppointment.getBefore(), confirmAppointment.getStatus());
                }  else {
                    Calendar calendar = Calendar.getInstance();
                        /*calendar.setTime(before);
                        calendar.add(calendar.DATE,-1);
                        Date fromD = calendar.getTime();*/
                    calendar.setTime(after);
                    calendar.add(calendar.DATE,1);
                    Date toD = calendar.getTime();

                    getScheduledpatients = appointmentRepository.getDoctorSlotBasedOnDateAndStatus(confirmAppointment.getClinicId(), confirmAppointment.getDoctorId(), before, toD, confirmAppointment.getStatus());
                }

                Map<String, List<ConfirmAppointment>> map = new LinkedHashMap<>();

                for (ConfirmAppointment appointment : getScheduledpatients) {
                    List<ConfirmAppointment> list = new ArrayList<ConfirmAppointment>();

                    if (map.containsKey(appointment.getDate())) {
                        list = map.get(appointment.getDate());
                        list.add(appointment);
                        Collections.sort(list, new CustomComparator());
                        map.put(appointment.getDate(), list);
                    } else {
                        list.add(appointment);
                        map.put(appointment.getDate(), list);
                    }
                    response.put("resultedSlots", map);
                    status = HttpStatus.OK;
                }
                if (getScheduledpatients.size() == 0) {
                    message = "Search Result not found.";
                    status = HttpStatus.CONTINUE;
                }

            }else{
                message = "Doctor has not set the time slot";
                status = HttpStatus.PROCESSING;
            }

        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    @RequestMapping(value = "getPatientsDetails", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> getPatientsDetails(@RequestBody ConfirmAppointment appointment, BindingResult result, ModelMap modelMap) {
        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";
            if (appointment.getClinicId() != null && appointment.getDoctorId() != null && appointment.getPatientId() != null && appointment.getDate() != null && appointment.getTime() != null) {
                ConfirmAppointment cancelAppointment = appointmentRepository.getId(appointment.getClinicId(), appointment.getDoctorId(), appointment.getPatientId(), appointment.getDate(), appointment.getTime());
                if (cancelAppointment != null) {
                    User patInfo = commonRepository.getUserDetails(appointment.getPatientId());
                    if(cancelAppointment.getReasonForVisit() != null && !"".equals(cancelAppointment.getReasonForVisit())){
                        patInfo.setPatRFV(cancelAppointment.getReasonForVisit());
                    }

                    response.put("patInfo", patInfo);
                    response.put("cancelAppointment", cancelAppointment);
                    status = HttpStatus.OK;
                }
            }
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    @RequestMapping(value = "checkHasAnyMultipleDayCancel", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> checkHasAnyMultipleDayCancel(@RequestBody ConfirmAppointment confirmAppointment, BindingResult result, ModelMap modelMap) {
        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
            //check cancelling date is already present in db
            List<ConfirmAppointment> getMultipleDayCancel = appointmentRepository.checkHasAnyMultipleDayCancel(confirmAppointment.getClinicId(), confirmAppointment.getDoctorId());

            if( getMultipleDayCancel.size() > 0 ){
                response.put("multipleDayCancel", getMultipleDayCancel);
                status=HttpStatus.CONFLICT;
            }else {
                status = HttpStatus.OK;
            }
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        return response;
    }

    @RequestMapping(value = "deleteMultipleDayCancel", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> deleteMultipleDayCancel(@RequestBody ConfirmAppointment confirmAppointment, BindingResult result, ModelMap modelMap) {
        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";

        if (confirmAppointment.getId() != null ) {
            ConfirmAppointment appointment = appointmentRepository.findOne(confirmAppointment.getId());

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(appointment.getVacaStartDate());
            calendar.add(calendar.DATE,-1);
            Date fromD = calendar.getTime();

            calendar.setTime(appointment.getVacaEndDate());
            calendar.add(calendar.DATE,1);
            Date toD = calendar.getTime();

            List<ConfirmAppointment> getScheduledpatients = appointmentRepository.getCancelledSlotBetweenDate(confirmAppointment.getClinicId(), confirmAppointment.getDoctorId(),fromD,toD);

            if(getScheduledpatients.size() > 0){
                String role = MyUserDetailsService.getUserFromSession().getRole();
                for(ConfirmAppointment confirmAppointment1 : getScheduledpatients){

                    confirmAppointment1.setStatus(SchedulingStatus.AVAILABLE);
                    confirmAppointment1.setCancelledOn(new Date());
                    confirmAppointment1.setUpdatedBy(role);
                    appointmentRepository.save(confirmAppointment1);
                }
            }
            //delete based on rowId in confirmAppointment table
            appointmentRepository.delete(confirmAppointment.getId());
            status = HttpStatus.OK;
            message = "You have successfully deleted the cancelled leaves !!!";
        }
        response.put("message", message);
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        return response;
    }


    @RequestMapping(value = "checkDayOrSlotsCnclInBtwDates", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> checkDayOrSlotsCnclInBtwDates(@RequestBody ConfirmAppointment confirmAppointment, BindingResult result, ModelMap modelMap) {
        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";


            List<ConfirmAppointment> getScheduledpatients = appointmentRepository.checkHasAnyScheduledStatus(confirmAppointment.getClinicId(), confirmAppointment.getDoctorId(), confirmAppointment.getVacaStartDate(), confirmAppointment.getVacaEndDate());
            //check cancelling date is already present in db
            List<ConfirmAppointment> getMultipleDayCancel = appointmentRepository.checkHasAnyMultipleDayCancel(confirmAppointment.getClinicId(), confirmAppointment.getDoctorId());
            List<ConfirmAppointment> getDaySlotCancel = appointmentRepository.checkForSingleDayOrSlotCancel(confirmAppointment.getClinicId(), confirmAppointment.getDoctorId(), confirmAppointment.getVacaStartDate(), confirmAppointment.getVacaEndDate());

            if(getScheduledpatients.size() > 0 || getMultipleDayCancel.size() > 0 || getDaySlotCancel.size() > 0){

                if(getDaySlotCancel.size() > 0){
                    Map<Date, List<ConfirmAppointment>> map = new LinkedHashMap<>();
                    for (ConfirmAppointment appointment : getDaySlotCancel) {
                        List<ConfirmAppointment> list = new ArrayList<ConfirmAppointment>();
                        if (map.containsKey(appointment.getCancelledOn())) {
                            list = map.get(appointment.getCancelledOn());
                            list.add(appointment);
                            Collections.sort(list, new CustomComparator());
                            map.put(appointment.getCancelledOn(), list);
                        } else {
                            list.add(appointment);
                            map.put(appointment.getCancelledOn(), list);
                        }
                    }
                    response.put("daySlotCancel", map);
                }

                response.put("patientScheduled", getScheduledpatients);
                response.put("multipleDayCancel", getMultipleDayCancel);
                status=HttpStatus.CONFLICT;
            }else {
                status = HttpStatus.OK;
                message = "Your Appointment has been cancelled successfully";
            }
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    @RequestMapping(value = "saveOrUpdateMultipleDayLeaves", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public synchronized Map<String, Object> saveOrUpdateMultipleDayLeaves(@RequestBody ConfirmAppointment confirmAppointment, BindingResult result, ModelMap modelMap) throws IOException {
        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";
        String role = MyUserDetailsService.getUserFromSession().getRole();
        boolean flag = false;
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(confirmAppointment.getVacaStartDate());
            calendar.add(calendar.DATE,-1);
            Date fromD = calendar.getTime();

            calendar.setTime(confirmAppointment.getVacaEndDate());
            calendar.add(calendar.DATE,1);
            Date toD = calendar.getTime();

            List<ConfirmAppointment> getScheduledpatients = appointmentRepository.checkHasAnyScheduledStatus(confirmAppointment.getClinicId(), confirmAppointment.getDoctorId(),fromD,toD);
            //check cancelling date is already present in db
            List<ConfirmAppointment> getMultipleDayCancel = appointmentRepository.checkHasAnyMultipleDayCancel(confirmAppointment.getClinicId(), confirmAppointment.getDoctorId());
            List<ConfirmAppointment> getDaySlotCancel = appointmentRepository.checkForSingleDayOrSlotCancel(confirmAppointment.getClinicId(), confirmAppointment.getDoctorId(), confirmAppointment.getVacaStartDate(), confirmAppointment.getVacaEndDate());

            if(getScheduledpatients.size() > 0 || getMultipleDayCancel.size() > 0 || getDaySlotCancel.size() > 0){

                Search docInfo = commonRepository.convertObjToSearchEnty(confirmAppointment.getDoctorId(),confirmAppointment.getClinicId());
                String mobilePhone = null;
                if(docInfo.getMobilePhone()!= null && !docInfo.getMobilePhone().isEmpty() || docInfo.getHomePhone() != null && !docInfo.getHomePhone().isEmpty()){
                    mobilePhone = docInfo.getMobilePhone()+" "+docInfo.getHomePhone();
                }

                if(getScheduledpatients.size() > 0){
                    for(ConfirmAppointment appointment : getScheduledpatients){

                        appointment.setStatus(SchedulingStatus.CANCELLED);
                        appointment.setCancelledOn(new Date());
                        appointment.setUpdatedBy(role);
                        appointmentRepository.save(appointment);

                        //get patient details to send mail
                        User patInfo = commonRepository.getUserDetails(appointment.getPatientId());
                        String dateTime = appointment.getDate();
                        emailController.infoToPatientOnDoctorCancelDays(docInfo.getDoctorName(),docInfo.getClinicName(),mobilePhone,dateTime, patInfo.getFlname(), patInfo.getEmail());

                        String messageBody = "Mr/Ms. "+patInfo.getFlname()+", All appointments with "+docInfo.getDoctorName()+" on "+dateTime+" for the day have been cancelled. DataLife";
                       /* String messageUrl = utilityServices.getMessage("send.sms.url")+mobilePhone+utilityServices.getMessage("sender.sms.id")+messageBody+utilityServices.getMessage("send.sms.format");*/
                        alfrescoAuthDetails.sendMessage(patInfo.getMobileNo(),messageBody);

                    }
                }

                if(getMultipleDayCancel.size() > 0){
                    for(ConfirmAppointment appointment : getMultipleDayCancel){
                        appointment.setVacaStartDate(confirmAppointment.getVacaStartDate());
                        appointment.setVacaEndDate(confirmAppointment.getVacaEndDate());
                        appointment.setCancelledOn(new Date());
                        appointment.setDate(DateService.getTodayDate());
                        appointment.setUpdatedBy(role);
                        appointmentRepository.save(appointment);
                        flag = true;
                        status = HttpStatus.OK;
                        message = "Your Appointment has been cancelled successfully";
                    }
                }

                if(getDaySlotCancel.size() > 0){
                    for(ConfirmAppointment appointment : getDaySlotCancel){
                        appointmentRepository.delete(appointment);
                    }
                }
            }
            if (!flag){
                confirmAppointment.setUpdatedBy(role);
                confirmAppointment.setCancelledOn(new Date());
                confirmAppointment.setStatus(SchedulingStatus.ONLEAVE);
                confirmAppointment.setCreatedDateTime(DateService.getTodayDateTime());
                confirmAppointment.setDate(DateService.getTodayDate());
                appointmentRepository.save(confirmAppointment);
                status = HttpStatus.OK;
                message = "Your Appointment has been cancelled successfully";
            }

        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }


    @RequestMapping(value = "cancelAppointmentonSlots", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> cancelAppointmentonSlots(@RequestBody ConfirmAppointment confirmAppointment, BindingResult result, ModelMap modelMap) throws IOException {
        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";
        String role = MyUserDetailsService.getUserFromSession().getRole();
        if (confirmAppointment.getClinicId() != null && confirmAppointment.getDoctorId() != null  && confirmAppointment.getDate() != null) {

            Search docInfo = commonRepository.convertObjToSearchEnty(confirmAppointment.getDoctorId(),confirmAppointment.getClinicId());
            String mobilePhone = null;
            if(docInfo.getMobilePhone()!= null && !docInfo.getMobilePhone().isEmpty() || docInfo.getHomePhone() != null && !docInfo.getHomePhone().isEmpty()){
                mobilePhone = docInfo.getMobilePhone()+" "+docInfo.getHomePhone();
            }
            List<ConfirmAppointment> appointmentListOnDate = appointmentRepository.getCancelledSlotOnDate(confirmAppointment.getClinicId(), confirmAppointment.getDoctorId(), confirmAppointment.getDate());
            if(confirmAppointment.getSlots() != null){
                for(Slots slots : confirmAppointment.getSlots()) {

                    for(int i= 0; i< appointmentListOnDate.size();i++) {
                        if (appointmentListOnDate.get(i).getTime().equals(slots.getSlotTime())) {
                            appointmentListOnDate.remove(i);
                        }
                    }
                    //check there is any appointment in the date of cancelled
                    ConfirmAppointment appointment = appointmentRepository.getConfirmAppId(confirmAppointment.getClinicId(), confirmAppointment.getDoctorId(), confirmAppointment.getDate(), slots.getSlotTime());

                    if (appointment != null) {
                        if (SchedulingStatus.SCHEDULED.equals(appointment.getStatus())) {
                            Date cancelledOndate = DateService.stringToDate(confirmAppointment.getDate() + " " + slots.getSlotTime());
                            appointment.setCancelledOn(cancelledOndate);
                            appointment.setUpdatedBy(role);
                            appointment.setStatus(confirmAppointment.getStatus());
                            //update the database
                            appointmentRepository.save(appointment);

                            User patInfo = commonRepository.getUserDetails(appointment.getPatientId());
                            String date = confirmAppointment.getDate() + " " + slots.getSlotTime();
                            emailController.infoToPatientOnDoctorCancelslot(docInfo.getDoctorName(), docInfo.getClinicName(), mobilePhone, date, patInfo.getFlname(), patInfo.getEmail());

                            String messageBody = "Mr/Ms. " + patInfo.getFlname() + ", Your scheduled appointment with " + docInfo.getDoctorName() + " on " + date + " has been cancelled. DataLife";
                            String messageUrl = utilityServices.getMessage("send.sms.url") + mobilePhone + utilityServices.getMessage("sender.sms.id") + messageBody + utilityServices.getMessage("send.sms.format");
                            alfrescoAuthDetails.sendMessage(patInfo.getMobileNo(), messageBody);

                        }
                    } else {
                        ConfirmAppointment appointment1 = new ConfirmAppointment();
                        appointment1.setDoctorId(confirmAppointment.getDoctorId());
                        appointment1.setClinicId(confirmAppointment.getClinicId());
                        appointment1.setStatus(confirmAppointment.getStatus());

                        Date cancelledOndate = DateService.stringToDate(confirmAppointment.getDate() + " " + slots.getSlotTime());
                        appointment1.setCancelledOn(cancelledOndate);
                        appointment1.setDate(DateService.dateToStringConversion(cancelledOndate));
                        appointment1.setTime(slots.getSlotTime());
                        appointment1.setUpdatedBy(role);
                        appointment1.setCreatedDateTime(DateService.getTodayDateTime());

                        //update the database
                        appointmentRepository.save(appointment1);
                    }
                    status = HttpStatus.OK;
                    message = "Appointment cancelled successfully";
                }
            }

            for (ConfirmAppointment anAppointmentListOnDate : appointmentListOnDate) {
                appointmentRepository.delete(anAppointmentListOnDate.getId());
                status = HttpStatus.OK;
                message = "Appointment cancelled successfully";
            }

        }
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;

    }


    @RequestMapping(value = "checkPatExistAndSaveApp", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> checkPatExistAndSaveApp(@RequestBody ConfirmAppointment confirmAppointment, BindingResult result, ModelMap modelMap) throws IOException {

        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = utilityServices.getMessage("user.scheduled.success");

        User u = userRepository.findByUserName(confirmAppointment.getUserName());
        //check user is present r not
        if (u != null && "ROLE_PATIENT".equals(u.getRole())) {
            boolean isValid = passwordEncoder.matches(confirmAppointment.getPassword(), u.getPassword());

            //check password is valid
            if (isValid && u.isEnabled()) {

                confirmAppointment.setPatientId(u.getUserId());
                if ((confirmAppointment.getClinicId() != null) && (confirmAppointment.getDoctorId() != null) && (confirmAppointment.getPatientId() != null)) {
                    confirmAppointment.setScheduledOn(new Date());
                    List<ConfirmAppointment> appointment = appointmentRepository.checkPatientAppSched(confirmAppointment.getClinicId(), confirmAppointment.getDoctorId(), confirmAppointment.getPatientId(),confirmAppointment.getScheduledOn());

                    if (appointment.size() <= 2) {
                        ConfirmAppointment appointment1 = appointmentRepository.checkDateTimeHasbeenBookAlr(confirmAppointment.getClinicId(), confirmAppointment.getDoctorId(),confirmAppointment.getDate(),confirmAppointment.getTime());
                        String dateTime = confirmAppointment.getDate() + " " + confirmAppointment.getTime();
                        if(appointment1 != null && SchedulingStatus.CANCELLED.equals(appointment1.getStatus())){

                            appointment1.setPatientId(confirmAppointment.getPatientId());
                            appointment1.setScheduledOn(DateService.stringToDateDMY(dateTime));
                            appointment1.setCreatedDateTime(DateService.getTodayDateTime());
                            appointment1.setStatus(SchedulingStatus.SCHEDULED);
                            if(confirmAppointment.getReasonForVisit() != null){
                                appointment1.setReasonForVisit(confirmAppointment.getReasonForVisit());
                            }else{
                                appointment1.setReasonForVisit("");
                            }

                            appointment1.setScheduledBy(u.getRole());
                            appointmentRepository.save(appointment1);
                            response.put("role",u.getRole());
                            response.put("message", message);
                            status = HttpStatus.OK;

                        }else if(appointment1 == null) {

                            confirmAppointment.setScheduledOn(DateService.stringToDateDMY(dateTime));
                            confirmAppointment.setCreatedDateTime(DateService.getTodayDateTime());
                            confirmAppointment.setStatus(SchedulingStatus.SCHEDULED);
                            confirmAppointment.setScheduledBy(u.getRole());
                            confirmAppointment.setReasonForVisit("");
                            appointmentRepository.save(confirmAppointment);

                            Search docInf = commonRepository.convertObjToSearchEnty(confirmAppointment.getDoctorId(),confirmAppointment.getClinicId());
                            User patInf = commonRepository.getUserDetails(confirmAppointment.getPatientId());
                            SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy, hh:mm aaa");
                            String mobilePhone = null;
                            if(docInf.getMobilePhone()!= null && !docInf.getMobilePhone().isEmpty() || docInf.getHomePhone() != null && !docInf.getHomePhone().isEmpty()){
                                mobilePhone = docInf.getMobilePhone()+" "+docInf.getHomePhone();
                            }
                            String address = docInf.getAddress()+" "+docInf.getLocation()+" "+docInf.getCity();
                            emailController.sendAppointmentConfirmation(docInf.getDoctorName(),docInf.getClinicName(),mobilePhone,address,sdf.format(confirmAppointment.getScheduledOn()), patInf);

                            String messageBody = "Mr/Ms."+patInf.getFlname()+", Your appointment is scheduled with "+docInf.getDoctorName()+" on "+sdf.format(confirmAppointment.getScheduledOn())+".ClinicName: "+docInf.getClinicName()+",Address:"+address+".";
                            alfrescoAuthDetails.sendMessage(patInf.getMobileNo(),messageBody);
                            response.put("message", message);
                            status = HttpStatus.OK;

                        }else{
                            message = "Time chosen by you is already scheduled";
                            status = HttpStatus.CONTINUE;
                        }
                    } else {
                        message = "You have already booked three appointments with this Doctor,You can book further Appointment after completion of two  scheduled appointments";
                        status = HttpStatus.CONTINUE;
                    }
                }
                response.put("slotTime",confirmAppointment.getTime());

            }else{
                message = utilityServices.getMessage("User.invalid.loginDetails.MESSAGE");
                status = HttpStatus.NON_AUTHORITATIVE_INFORMATION;
            }
        }else{
            message = utilityServices.getMessage("User.invalid.loginDetails.MESSAGE");
            status = HttpStatus.NON_AUTHORITATIVE_INFORMATION;
        }
        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }

    @RequestMapping(value = "getAllScheduledApp", method = RequestMethod.POST, headers = {"content-type=application/json"}, consumes = "application/json", produces = "application/json")
    public Map<String, Object> getAllScheduledApp(@RequestBody ConfirmAppointment confirmAppointment, BindingResult result, ModelMap modelMap) throws IOException {

        Map<String, Object> response = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = utilityServices.getMessage("user.scheduled.success");

            List<ConfirmAppointment> confirmAppointmentsList = appointmentRepository.getAppSchedBasedOnDoctIdAndClinicId(confirmAppointment.getClinicId(),confirmAppointment.getDoctorId(),confirmAppointment.getScheduledOn());
            if(confirmAppointmentsList.size() > 0){
                message = confirmAppointmentsList.size()+" patients have been scheduled";
                status = HttpStatus.OK;

            }else{
                message = "";
                status = HttpStatus.CONTINUE;
            }

        response.put("statusCode", status.value());
        response.put("Status", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }


    public class CustomComparator implements Comparator<ConfirmAppointment> {
        @Override
        public int compare(ConfirmAppointment o1, ConfirmAppointment o2) {
            Date d1 = DateService.stringToTime(o1.getTime());
            Date d2 = DateService.stringToTime(o2.getTime());
            return d1.compareTo(d2);
        }
    }

    public static boolean containsStatus(List<Slots> list, SchedulingStatus id) {
        for (Slots object : list) {
            if (object.getStatus() != null) {
                if (object.getStatus() == id) {
                    return true;
                }
            }
        }
        return false;
    }

    //to sort the slot time in order
    class SlotTimeComparator implements Comparator<String> {

        public int compare(String s1, String s2) {

            Date d1 = DateService.stringToTime(s1);
            Date d2 = DateService.stringToTime(s2);
            //ascending order
            return d1.compareTo(d2);
        }
    }

    public static boolean checkForStatus(List<ConfirmAppointment> list, SchedulingStatus id) {
        for (ConfirmAppointment object : list) {
            if (object.getStatus() != null) {
                if (object.getStatus() == id && !"ROLE_PATIENT".equals(object.getUpdatedBy())) {
                    return true;
                }
            }
        }
        return false;
    }

    //object with same date,Time and different status then get only latest updated
    class GetLatestUpdateSlotAndRemoveOld implements Comparator<ConfirmAppointment> {
        public int compare(ConfirmAppointment o1, ConfirmAppointment o2) {
            if(o1 != null && o1.getTime()!= null && !o1.getTime().isEmpty() && o2 != null && o2.getTime()!= null && !o2.getTime().isEmpty() ){
                if(o1.getDate().equals(o2.getDate()) && o1.getTime().equals(o2.getTime())){
                    Date date1 = DateService.stringToDateDMY(o1.getCreatedDateTime());
                    Date date2 = DateService.stringToDateDMY(o2.getCreatedDateTime());
                    if(date1.compareTo(date2) <0){
                        return 0;
                    }
                }
            }
            return 1;
        }
    }

    public List<PerDaySlots> getPerDaySlot(String dates, String slots, String slotInterval,int tokenNo,boolean isFromClinicDoc) {
        String[] time = slots.split("-");
        SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
        SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");
        List<PerDaySlots> list = new ArrayList<>();
        try {
            Date date = dateFormat.parse(String.format("%s %s", dates, time[0]));
            String startHrs = displayFormat.format(date);

            Date d = displayFormat.parse(startHrs);
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(d);

            date = dateFormat.parse(String.format("%s %s", dates, time[1]));
            String endHrs = displayFormat.format(date);

            d = displayFormat.parse(endHrs);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(d);


            //get the date time from server
            String curDate = DateService.getTodayDate();
            Date sysDate = DateService.stringToDateConversion(curDate);

            Date compDate = DateService.stringToDateConversion(dates);
            Calendar calendar3 = null;

            //check for cur date if true,assign time to calendar3 instance
            if (compDate.equals(sysDate)) {
                String curTime = DateService.getTodayDateTime();
                Date ds = new SimpleDateFormat("dd/MM/yyyy hh:mm a").parse(curTime);
                String cur = displayFormat.format(ds);
                DateTime dateTime;
                Date dd = displayFormat.parse(cur);
                calendar3 = Calendar.getInstance();
                if(isFromClinicDoc){
                    dateTime = new DateTime(dd).minusMinutes(Integer.parseInt(slotInterval));
                    calendar3.setTime(dateTime.toDate());
                }else{
                    calendar3.setTime(dd);
                }
            }
            //To generate token no
            int i =tokenNo;
            //compare the start with end time till start time is greater than end time
            while (calendar2.getTime().after(calendar1.getTime())) {
                i = i+1;
                PerDaySlots daySlots = new PerDaySlots();
                // for cur date
                if (calendar3 != null) {
                    //allow the slot greater then the current time
                    if (calendar1.getTime().after(calendar3.getTime())) {

                        Date timeslot = calendar1.getTime();
                        String addTime = parseFormat.format(timeslot);
                        daySlots.setSlot(addTime);
                        daySlots.setTokenNo(i);
                        list.add(daySlots);
                        //add the time intervals
                        calendar1.add(Calendar.MINUTE, Integer.parseInt(slotInterval));
                    }else{
                        calendar1.add(Calendar.MINUTE, Integer.parseInt(slotInterval));
                    }
                } else {
                    Date timeslot = calendar1.getTime();
                    String addTime = parseFormat.format(timeslot);
                    daySlots.setSlot(addTime);
                    daySlots.setTokenNo(i);
                    list.add(daySlots);
                    //add the time intervals
                    calendar1.add(Calendar.MINUTE, Integer.parseInt(slotInterval));
                }
            }
            if(list.size() == 0){
                PerDaySlots daySlots = new PerDaySlots();
                daySlots.setTokenNo(i);
                list.add(daySlots);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return list;
    }

    public Map<String, Slots> getslots(List<PerDaySlots> firsttimeSlot, List<PerDaySlots> secoundtimeSlot, List<ConfirmAppointment> scheduledList,boolean isDoctorOnLeave) {

        Map<String, Slots> stringMap = new TreeMap<>(new SlotTimeComparator());

        boolean a = checkForStatus(scheduledList, SchedulingStatus.CANCELDAY);
        boolean m = checkForStatus(scheduledList, SchedulingStatus.ONLEAVE);
        if(m && !isDoctorOnLeave){
            m = false;
        }
        //check is there any entire day is cancelled if not go head with checking slot
        if (!(a || m)) {
            for (ConfirmAppointment appointment : scheduledList) {
                Slots slots = new Slots();
                slots.setPatientId(appointment.getPatientId());
                slots.setSlotTime(appointment.getTime());
                slots.setUpdatedBy(appointment.getUpdatedBy());
                slots.setScheduledBy(appointment.getScheduledBy());

                User user = commonRepository.getUserDetails(appointment.getPatientId());

                if (user != null) {
                    slots.setPatientName(user.getFlname().toUpperCase());
                    slots.setImageUrl(user.getImageUrl());
                }

                boolean firstCancelled = false;
                boolean secondCancelled = false;
                switch (appointment.getStatus()) {

                    case CANCELDAY:
                        firstCancelled = true;
                        secondCancelled = true;
                        break;
                    case ARRIVED:
                    case SCHEDULED:
                    case INPROGRESS:
                    case COMPLETED:
                        slots.setStatus(appointment.getStatus());
                        slots.setValue(true);
                        break;
                    case CANCELLED:
                        if("ROLE_PATIENT".equals(slots.getUpdatedBy())){
                            slots.setStatus(null);
                            slots.setValue(true);
                        }else{
                            slots.setStatus(appointment.getStatus());
                            slots.setValue(false);
                        }
                        break;
                }
                //which has been cancelled by doctor and clinic, will be blocked.Slot cancelled by patient will be opened
                for (PerDaySlots time : firsttimeSlot) {
                    if(time.getSlot() != null){
                        if (!stringMap.containsKey(time.getSlot())) {
                            if (firstCancelled || time.getSlot().equals(slots.getSlotTime())) {
                               /* //if patient has updated the status
                                if ("ROLE_PATIENT".equals(slots.getUpdatedBy()) || "ROLE_PATIENT".equals(slots.getScheduledBy())) {
                                    //get the status and is equal to scheduled, mark slot has scheduled(true) or false(cancelled)
                                    if (SchedulingStatus.SCHEDULED == appointment.getStatus()) {
                                        slots.setValue(true);
                                        slots.setTokenNo(time.getTokenNo());
                                    } else {
                                        slots.setValue(false);
                                        slots.setTokenNo(time.getTokenNo());
                                    }
                                } else {
                                    slots.setValue(true);

                                }*/
                                slots.setTokenNo(time.getTokenNo());
                                stringMap.put(time.getSlot(), slots);
                            }
                        }
                    }
                }
                for (PerDaySlots time : secoundtimeSlot) {
                    if(time.getSlot() != null){
                        if (!stringMap.containsKey(time.getSlot())) {
                            if (secondCancelled || time.getSlot().equals(slots.getSlotTime())) {
                              /*  if ("ROLE_PATIENT".equals(slots.getUpdatedBy()) || "ROLE_PATIENT".equals(slots.getScheduledBy())) {
                                    if (SchedulingStatus.SCHEDULED == appointment.getStatus()) {
                                        slots.setValue(true);
                                        slots.setTokenNo(time.getTokenNo());
                                    } else {
                                        slots.setValue(false);
                                        slots.setTokenNo(time.getTokenNo());
                                    }
                                } else {
                                    slots.setValue(true);

                                }*/
                                slots.setTokenNo(time.getTokenNo());
                                stringMap.put(time.getSlot(), slots);
                            }
                        }
                    }

                }
            }
            //which has not be scheduled
            for (PerDaySlots s : firsttimeSlot) {
                if(s.getSlot() != null){
                    if (!stringMap.containsKey(s.getSlot())) {
                        Slots slots1 = new Slots(s.getSlot(), null, null, null, null, null,0);
                        slots1.setValue(false);
                        slots1.setTokenNo(s.getTokenNo());
                        stringMap.put(s.getSlot(), slots1);
                    }
                }
            }

            for (PerDaySlots s : secoundtimeSlot) {
                if(s.getSlot() != null){
                    if (!stringMap.containsKey(s.getSlot())) {
                        Slots slots1 = new Slots(s.getSlot(), null, null, null, null, null,0);
                        slots1.setValue(false);
                        slots1.setTokenNo(s.getTokenNo());
                        stringMap.put(s.getSlot(), slots1);
                    }
                }

            }
        }
        return stringMap;
    }

    public ClinicDoctors getClinicDoctorsTimings(ClinicDoctors clinicDoctors) {

        NewTimings timeSettings = clinicDoctors.getNewTimings();
        if (timeSettings != null) {
            clinicDoctors.setSlotTime(timeSettings.getSlotTime());

            clinicDoctors.setMonStatus_1(timeSettings.isMonStatus_1());
            clinicDoctors.setMonst_1(timeSettings.getMonst_1());
            clinicDoctors.setMonet_1(timeSettings.getMonet_1());

            clinicDoctors.setMonStatus_2(timeSettings.isMonStatus_2());
            clinicDoctors.setMonst_2(timeSettings.getMonst_2());
            clinicDoctors.setMonet_2(timeSettings.getMonet_2());

            clinicDoctors.setTueStatus_1(timeSettings.isTueStatus_1());
            clinicDoctors.setTuest_1(timeSettings.getTuest_1());
            clinicDoctors.setTueet_1(timeSettings.getTueet_1());

            clinicDoctors.setTueStatus_2(timeSettings.isTueStatus_2());
            clinicDoctors.setTuest_2(timeSettings.getTuest_2());
            clinicDoctors.setTueet_2(timeSettings.getTueet_2());

            clinicDoctors.setWedStatus_1(timeSettings.isWedStatus_1());
            clinicDoctors.setWedst_1(timeSettings.getWedst_1());
            clinicDoctors.setWedet_1(timeSettings.getWedet_1());

            clinicDoctors.setWedStatus_2(timeSettings.isWedStatus_2());
            clinicDoctors.setWedst_2(timeSettings.getWedst_2());
            clinicDoctors.setWedet_2(timeSettings.getWedet_2());

            clinicDoctors.setThuStatus_1(timeSettings.isThuStatus_1());
            clinicDoctors.setThust_1(timeSettings.getThust_1());
            clinicDoctors.setThuet_1(timeSettings.getThuet_1());

            clinicDoctors.setThuStatus_2(timeSettings.isThuStatus_2());
            clinicDoctors.setThust_2(timeSettings.getThust_2());
            clinicDoctors.setThuet_2(timeSettings.getThuet_2());

            clinicDoctors.setFriStatus_1(timeSettings.isFriStatus_1());
            clinicDoctors.setFrist_1(timeSettings.getFrist_1());
            clinicDoctors.setFriet_1(timeSettings.getFriet_1());

            clinicDoctors.setFriStatus_2(timeSettings.isFriStatus_2());
            clinicDoctors.setFrist_2(timeSettings.getFrist_2());
            clinicDoctors.setFriet_2(timeSettings.getFriet_2());

            clinicDoctors.setSatStatus_1(timeSettings.isSatStatus_1());
            clinicDoctors.setSatst_1(timeSettings.getSatst_1());
            clinicDoctors.setSatet_1(timeSettings.getSatet_1());

            clinicDoctors.setSatStatus_2(timeSettings.isSatStatus_2());
            clinicDoctors.setSatst_2(timeSettings.getSatst_2());
            clinicDoctors.setSatet_2(timeSettings.getSatet_2());

            clinicDoctors.setSunStatus_1(timeSettings.isSunStatus_1());
            clinicDoctors.setSunst_1(timeSettings.getSunst_1());
            clinicDoctors.setSunet_1(timeSettings.getSunet_1());

            clinicDoctors.setSunStatus_2(timeSettings.isSunStatus_2());
            clinicDoctors.setSunst_2(timeSettings.getSunst_2());
            clinicDoctors.setSunet_2(timeSettings.getSunet_2());

            clinicDoctors.setSlotTime(timeSettings.getSlotTime());

            Date curDate = new DateTime(new Date()).withTimeAtStartOfDay().toDate();
            Date setEffectedDate = new DateTime(clinicDoctors.getChangeSetEfectFrm()).withTimeAtStartOfDay().toDate();

            if (setEffectedDate.compareTo(curDate) == 0 || curDate.compareTo(setEffectedDate) > 0) {
                /*clinicDoctors.setChangeTimingAfter(false);*/
                clinicDoctors.setChangeSetEfectFrm(null);
                clinicDoctors.setNewTimings(null);
                clinicDoctorsRepository.save(clinicDoctors);
               /* newTimingsRepository.delete(clinicDoctors.getNewTimings().getSettingsId());*/
            }
        }
        return clinicDoctors;
    }
}
