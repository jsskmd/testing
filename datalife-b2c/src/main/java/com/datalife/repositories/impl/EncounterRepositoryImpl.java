package com.datalife.repositories.impl;

import com.datalife.entities.*;
import com.datalife.repositories.*;
import com.datalife.repositories.custom.EncounterRepositoryExtension;
import com.datalife.servicelayers.HomeService;
import com.datalife.services.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.util.*;

/**
 * This class is implementation for EncounterRepositoryExtension
 * <p/>
 * Created by supriya gondi on 11/7/2014.
 */
@Service
public class EncounterRepositoryImpl implements EncounterRepositoryExtension {

    @Autowired
    CommonRepository commonRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AlfrescoAuthDetails alfrescoAuthDetails;

    @Autowired
    EncounterRepository encounterRepository;

    @Autowired
    UtilityServices utilityServices;

    @Autowired
    HistoryRepository historyRepository;

    @Autowired
    PDFGenerationService pdfGenerationService;

    @Autowired
    ClinicDoctorsRepository clinicDoctorsRepository;

    @Autowired
    UploadFileRepository uploadFileRepository;

    @Autowired
    UserDetailsRepository userDetailsRepository;

    @Autowired
    ClinicInfoRepository clinicInfoRepository;

    private static final Logger logger = Logger.getLogger(HomeService.class);

    @Override
    public List<PhysicalExamination> getPe(List<PhysicalExamination> physicalExaminations, Encounter enc) {
        List<PhysicalExamination> physicalExaminationList = new ArrayList<PhysicalExamination>();
        List<CardioVascular> cardioVascularsList = new ArrayList<CardioVascular>();
        List<Gastrointestinal> gastrointestinalList = new ArrayList<Gastrointestinal>();
        List<Muscoloskeletal> muscoloskeletalList = new ArrayList<Muscoloskeletal>();
        List<Respiratory> respiratoryList = new ArrayList<Respiratory>();
        for (PhysicalExamination physicalExamination : physicalExaminations) {

            Long peTypeId = physicalExamination.getPeTypeId();
            PETypes peTypes = commonRepository.getPETypesById(peTypeId);
            peTypes.setPeId(physicalExamination.getPeId());

            List<Respiratory> respiratory = physicalExamination.getRespiratory();
            List<CardioVascular> cardioVascular = physicalExamination.getCardioVascular();
            List<Gastrointestinal> gastrointestinal = physicalExamination.getGastrointestinal();
            List<Muscoloskeletal> muscoloskeletal = physicalExamination.getMuscoloskeletal();

            String desc = physicalExamination.getDescription();
            physicalExamination.setEncounter(enc);
            if (desc != null && !desc.isEmpty()) {
                physicalExaminationList.add(physicalExamination);
            }

            if (respiratory != null) {
                for (Respiratory resp : respiratory) {
                    String desp = resp.getDescriptions();
                    if(resp.getRespiratoryLabels() != null){
                        String type = resp.getRespiratoryLabels().getLabelType();
                        if (resp.getRespiratoryLabels().getRespiratoryLabelId() != null) {
                            if (type.equals("checkbox")) {
                                resp.setPhysicalExamination(physicalExamination);
                                respiratoryList.add(resp);
                            } else if ((desp != null && !"".equals(desp)) && (type.equals("textarea") || type.equals("text"))) {
                                resp.setPhysicalExamination(physicalExamination);
                                respiratoryList.add(resp);
                            }
                        }
                    }

                }
                physicalExamination.setRespiratory(respiratoryList);
                physicalExaminationList.add(physicalExamination);
            }

            if (cardioVascular != null) {
                for (CardioVascular vascular : cardioVascular) {
                    String desp = vascular.getDescriptions();
                    if(vascular.getCardiovascularLabels() != null){
                        String type = vascular.getCardiovascularLabels().getLabelType();
                        if (vascular.getCardiovascularLabels().getCardioLabelId() != null) {
                            if (type.equals("checkbox")) {
                                vascular.setPhysicalExamination(physicalExamination);
                                cardioVascularsList.add(vascular);
                            } else if ((desp != null && !"".equals(desp)) && (type.equals("textarea") || type.equals("text"))) {
                                vascular.setPhysicalExamination(physicalExamination);
                                cardioVascularsList.add(vascular);
                            }
                        }
                    }

                }
                physicalExamination.setCardioVascular(cardioVascularsList);
                physicalExaminationList.add(physicalExamination);
            }

            if (gastrointestinal != null) {
                for (Gastrointestinal gastro : gastrointestinal) {
                    String desp = gastro.getDescriptions();
                   if(gastro.getGastrointestinalLabels() != null){
                       String type = gastro.getGastrointestinalLabels().getLabelType();
                       if (gastro.getGastrointestinalLabels().getGastroLabelId() != null) {
                           if (type.equals("checkbox")) {
                               gastro.setPhysicalExamination(physicalExamination);
                               gastrointestinalList.add(gastro);
                           } else if ((desp != null && !"".equals(desp)) && (type.equals("textarea") || type.equals("text"))) {
                               gastro.setPhysicalExamination(physicalExamination);
                               gastrointestinalList.add(gastro);
                           }
                       }
                   }
                }
                physicalExamination.setGastrointestinal(gastrointestinalList);
                physicalExaminationList.add(physicalExamination);
            }

            if (muscoloskeletal != null) {
                for (Muscoloskeletal muscolo : muscoloskeletal) {
                    String desp = muscolo.getDescriptions();
                    if(muscolo.getMuscoloskeletalLabels() != null){
                        String type = muscolo.getMuscoloskeletalLabels().getLabelType();
                        if (muscolo.getMuscoloskeletalLabels().getMuscoloLabelId() != null) {
                            if (type.equals("checkbox")) {
                                muscolo.setPhysicalExamination(physicalExamination);
                                muscoloskeletalList.add(muscolo);
                            } else if ((desp != null && !"".equals(desp)) && (type.equals("textarea") || type.equals("text"))) {
                                muscolo.setPhysicalExamination(physicalExamination);
                                muscoloskeletalList.add(muscolo);
                            }
                        }
                    }
                }
                physicalExamination.setMuscoloskeletal(muscoloskeletalList);
                physicalExaminationList.add(physicalExamination);
            }

        }
        return physicalExaminationList;
    }

    @Override
    public List<LabOrder> getLabOrders(List<LabOrder> labOrders, Encounter enc) {
        List<LabOrder> labOrderList = new ArrayList<LabOrder>();
        for (LabOrder labOrder : labOrders) {
            if (labOrder.getLabTestsId() == null) {
                labOrder.setLabTestsId(29L);
            }
            labOrder.setEncounter(enc);
            labOrderList.add(labOrder);
        }
        return labOrderList;
    }

    @Override
    public List<Prescription> getPrescriptions(List<Prescription> prescriptions, Encounter enc) {
        List<Prescription> prescriptionList = new ArrayList<Prescription>();
        for (Prescription prescription : prescriptions) {
            prescription.setEncounter(enc);
            prescriptionList.add(prescription);
        }
        return prescriptionList;
    }

    @Override
    public List<ReviewofSystems> getRos(List<ReviewofSystems> reviewofSystemses, Encounter enc) {
        List<ReviewofSystems> reviewofSystemsList = new ArrayList<ReviewofSystems>();
        for (byte i = 1; i <= 14; i++) {
            ReviewofSystems reviewofSystem = reviewofSystemses.get(i - 1);
            if (reviewofSystem.getDescription() != null && !reviewofSystem.getDescription().isEmpty()) {
                reviewofSystem.setRosId(i);
                reviewofSystem.setEncounter(enc);
                reviewofSystemsList.add(reviewofSystem);
            }
        }
        return reviewofSystemsList;
    }

    @Override
    public boolean saveConsultation(Encounter encounter) {
        try {
            Encounter enc = new Encounter();
            Long patientId = encounter.getPatientId();
            User patient = userRepository.findOne(patientId);
            if (patient != null) {

                Date date = new Date();
                enc.setDate(date);
                enc.setUser(patient);
                enc.setShare(encounter.isShare());
                enc.setAge(encounter.getAge());
                enc.setDoctorName(encounter.getDoctorName());
                enc.setClinicName(encounter.getClinicName());
                enc.setMobileNumber(encounter.getMobileNumber());
                enc.setMciNumber(encounter.getMciNumber());
                String ticket = MyUserDetailsService.getUserFromSession().getAlfresAuthTicket();

                //check wheather ticket is valid
                if (!alfrescoAuthDetails.isTicketValid(ticket)) {
                    //get the ticket form patient user
                    ticket = alfrescoAuthDetails.getAlfrescoConnectionTicket(patient.getUserName(), patient.getPassword());
                    MyUserDetailsService.getUserFromSession().setAlfresAuthTicket(ticket);
                }

                String url = patient.getAlfrescoUrl();
                //get patient-user info from alfresco
                String statusCode = alfrescoAuthDetails.getAlfrescoUserDetails(url, ticket);

                //check the patient-user has enough space to store the report
                if (statusCode != null) {

                    String[] q = statusCode.split(",");
                    int quota = Integer.parseInt(q[0]);
                    int cz = Integer.parseInt(q[1]);

                    if (quota > cz) {

                        MiniEncounter miniEncounter = encounter.getMiniEncounter();
                        if (miniEncounter != null) {
                            miniEncounter.setEncounter(enc);
                            enc.setMiniEncounter(miniEncounter);
                        }

                        SOAP soap = encounter.getSoap();
                        if (soap != null) {
                            soap.setEncounter(enc);
                            enc.setSoap(soap);
                        }

                        Vitals vitals = encounter.getVitals();
                        if (vitals != null&&(StringUtils.isNotBlank(vitals.getTemp())||StringUtils.isNotBlank(vitals.getBp())||StringUtils.isNotBlank(vitals.getHeartRate())||StringUtils.isNotBlank(vitals.getRespRate())||StringUtils.isNotBlank(vitals.getBmi())||StringUtils.isNotBlank(vitals.getSugar()))) {
                            vitals.setDate(date);
                            vitals.setUser(patient);
                            vitals.setEncounter(enc);
                            enc.setVitals(vitals);
                        }
                        List<Prescription> prescriptions = encounter.getPrescriptions();
                        if (prescriptions != null && !prescriptions.isEmpty()) {
                            enc.setPrescriptions(getPrescriptions(prescriptions, enc));
                        }
                        List<PhysicalExamination> physicalExaminations = encounter.getPhysicalExaminations();
                        if (physicalExaminations != null && !physicalExaminations.isEmpty()) {
                            enc.setPhysicalExaminations(getPe(physicalExaminations, enc));
                        }
                        List<ReviewofSystems> reviewofSystemses = encounter.getReviewofSystems();
                        if (reviewofSystemses != null && !reviewofSystemses.isEmpty()) {
                            enc.setReviewofSystems(getRos(reviewofSystemses, enc));
                        }
                        List<LabOrder> labOrders = encounter.getLabOrders();
                        if (labOrders != null && !labOrders.isEmpty()) {
                            enc.setLabOrders(getLabOrders(labOrders, enc));
                        }
                        encounterRepository.save(enc);
                        History history = encounter.getHistory();
                        setHistory(history, patient);

                        String storageDirectory = utilityServices.getMessage("Encounter.share.pdf");
                        InputStream stream;
                       /* System.out.println(enc.isShare());*/
                        /* userParentDir = null;*/
                        /*String[] node = new String[]{};*/

                            //patients folder path
                        String userParentDir = enc.getUser().getUserParentDir();
                            /*node = userParentDir.split("/");*/
                        if (enc.getPrescriptions() != null) {
                            stream = this.getClass().getClassLoader().getResourceAsStream("prescriptionReport.html");
                            generatePrescription(stream, storageDirectory, enc, null,userParentDir,date,patient);
                        }

                        if (enc.getLabOrders() != null) {
                            stream = this.getClass().getClassLoader().getResourceAsStream("labOrderReport.html");
                            generateLabOrder(stream, storageDirectory, enc,null, userParentDir,date,patient);
                        }

                        if (enc != null ){
                            stream = this.getClass().getClassLoader().getResourceAsStream("summaryReport.html");
                            generateSummaryReport( stream, storageDirectory,  enc,null,  userParentDir,date,patient);
                        }
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

   /* @Override
    public boolean saveEncounter1(Encounter encounter) {
        try {
            Encounter enc = new Encounter();
            String ticket = null;
            String doctorTicket = null;
            Long doctorId = encounter.getDoctorId();
            Long patientId = encounter.getPatientId();
            Long clinicId = encounter.getClinicId();
            User doctor = userRepository.findOne(doctorId);
            User patient = userRepository.findOne(patientId);
            if (doctor != null && patient != null) {

                Date date = new Date();
                enc.setDate(date);
                enc.setUser(patient);
                enc.setShare(encounter.isShare());
                enc.setClinicName(clinicInfoRepository.getClinicName(clinicId));
                String name=userDetailsRepository.getFullName(doctorId);
                String[] names=name.split(",");
                enc.setDoctorName("Dr."+names[0]+" "+names[1]);
                User user = MyUserDetailsService.getUserFromSession();

                if(RolesInServices.ROLE_DOCTOR.equals(user.getRole())){
                    doctorTicket = MyUserDetailsService.getUserFromSession().getAlfresAuthTicket();
                    if (!alfrescoAuthDetails.isTicketValid(doctorTicket)) {
                        //get the ticket form patient user
                        doctorTicket = alfrescoAuthDetails.getAlfrescoConnectionTicket(doctor.getUserName(), doctor.getPassword());
                    }
                }
                        ticket = alfrescoAuthDetails.getAlfrescoConnectionTicket(patient.getUserName(), patient.getPassword());

                String url = patient.getAlfrescoUrl();
                //get patient-user info from alfresco
                String statusCode = alfrescoAuthDetails.getAlfrescoUserDetails(url, ticket);

                //check the patient-user has enough space to store the report
                if (statusCode != null) {

                    String[] q = statusCode.split(",");
                    int quota = Integer.parseInt(q[0]);
                    int cz = Integer.parseInt(q[1]);

                    if (quota > cz) {

                        enc.setDoctorId(doctorId);
                        enc.setClinicId(clinicId);

                        MiniEncounter miniEncounter = encounter.getMiniEncounter();
                        if (miniEncounter != null) {
                            miniEncounter.setEncounter(enc);
                            enc.setMiniEncounter(miniEncounter);
                        }

                        SOAP soap = encounter.getSoap();
                        if (soap != null) {
                            soap.setEncounter(enc);
                            enc.setSoap(soap);
                        }

                        Vitals vitals = encounter.getVitals();
                        if (vitals != null&&(StringUtils.isNotBlank(vitals.getTemp())||StringUtils.isNotBlank(vitals.getBp())||StringUtils.isNotBlank(vitals.getHeartRate())||StringUtils.isNotBlank(vitals.getRespRate())||StringUtils.isNotBlank(vitals.getBmi())||StringUtils.isNotBlank(vitals.getSugar()))) {
                            vitals.setDate(date);
                            vitals.setUser(patient);
                            vitals.setEncounter(enc);
                            enc.setVitals(vitals);
                        }
                        List<Prescription> prescriptions = encounter.getPrescriptions();
                        if (prescriptions != null && !prescriptions.isEmpty()) {
                            enc.setPrescriptions(getPrescriptions(prescriptions, enc));
                        }
                        List<PhysicalExamination> physicalExaminations = encounter.getPhysicalExaminations();
                        if (physicalExaminations != null && !physicalExaminations.isEmpty()) {
                            enc.setPhysicalExaminations(getPe(physicalExaminations, enc));
                        }
                        List<ReviewofSystems> reviewofSystemses = encounter.getReviewofSystems();
                        if (reviewofSystemses != null && !reviewofSystemses.isEmpty()) {
                            enc.setReviewofSystems(getRos(reviewofSystemses, enc));
                        }
                        List<LabOrder> labOrders = encounter.getLabOrders();
                        if (labOrders != null && !labOrders.isEmpty()) {
                            enc.setLabOrders(getLabOrders(labOrders, enc));
                        }
                        encounterRepository.save(enc);
                        History history = encounter.getHistory();
                       setHistory(history, patient);

                        String storageDirectory = utilityServices.getMessage("Encounter.share.pdf");
                        File newFile = null;
                        String prescriptionFilePath = "";
                        String labOrderFilePath = "";
                        String summaryFilePath = "";
                        InputStream stream;
                        String userParentDir = null;
                        String[] node = new String[]{};
                        //checks where to store Encounter report (patients folder or clinic's doctor folder)

                            //patients folder path
                            userParentDir = enc.getUser().getUserParentDir();
                            node = userParentDir.split("/");

                        // patient grant permission to doctor to upload a Encounter report
                        alfrescoAuthDetails.grantPermission(ticket, doctor.getUserName(), node[3], "Collaborator");
                        switch(doctor.getRole()){
                            case RolesInServices.ROLE_REFERRALDOCTOR :
                            case RolesInServices.ROLE_TELECONSULTANT :
                               //to store in patient get login connection
                                MyUserDetailsService.getUserFromSession().setAlfresAuthTicket(ticket);
                                break;
                            case RolesInServices.ROLE_DOCTOR :
                                doctorTicket = alfrescoAuthDetails.getAlfrescoConnectionTicket(doctor.getUserName(), doctor.getPassword());
                                MyUserDetailsService.getUserFromSession().setAlfresAuthTicket(doctorTicket);
                               break;
                        }

                        enc.setAge(encounter.getAge());
                        if (enc.getPrescriptions() != null) {
                            stream = this.getClass().getClassLoader().getResourceAsStream("prescriptionReport.html");
                            generatePrescription(stream, storageDirectory, enc,doctor, userParentDir,date,patient);

                        }

                        if (enc.getLabOrders() != null) {
                            stream = this.getClass().getClassLoader().getResourceAsStream("labOrderReport.html");
                            generateLabOrder(stream, storageDirectory, enc,doctor,  userParentDir,date,patient);

                        }
                        if (enc != null && soap != null) {
                            stream = this.getClass().getClassLoader().getResourceAsStream("soap.html");
                            generateSOAPReport( stream, storageDirectory,enc,doctor,userParentDir,date,patient);

                        } else {
                            stream = this.getClass().getClassLoader().getResourceAsStream("summaryReport.html");
                            generateSummaryReport( stream, storageDirectory,  enc,doctor,userParentDir,date,patient);

                            }
                        encounterRepository.save(enc);

                        //delete the grant permission to doctor by patient
                        alfrescoAuthDetails.deletePermission(ticket, doctor.getUserName(), userParentDir, "Collaborator");

                        //invalid the ticket
                        alfrescoAuthDetails.logoutTicket(ticket);
                        alfrescoAuthDetails.logoutTicket(doctorTicket);
                        return true;

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }*/

    @Override
    public Encounter saveEncounter(Encounter encounter) {
        logger.info("Start of Method saveEncounter parameter : "+encounter);
        try{
            User patient = userRepository.findOne(encounter.getPatientId());
            if (patient != null) {
                encounter.setDate(new Date());
                encounter.setUser(patient);
                encounter.setShare(encounter.isShare());

                if(encounter.getClinicId() != null && encounter.getDoctorId() != null){
                    User doctor = userRepository.findOne(encounter.getDoctorId());
                    encounter.setClinicName(clinicInfoRepository.getClinicName(encounter.getClinicId()));
                    encounter.setDoctorName("Dr."+doctor.getUserDetails().getFirstName()+" "+doctor.getUserDetails().getLastName());
                }

                MiniEncounter miniEncounter = encounter.getMiniEncounter();
                if (miniEncounter != null) {
                    miniEncounter.setEncounter(encounter);
                    encounter.setMiniEncounter(miniEncounter);
                }

                SOAP soap = encounter.getSoap();
                if (soap != null) {
                    soap.setEncounter(encounter);
                    encounter.setSoap(soap);
                }

                Vitals vitals = encounter.getVitals();
                if (vitals != null&&(StringUtils.isNotBlank(vitals.getTemp())||StringUtils.isNotBlank(vitals.getBp())||StringUtils.isNotBlank(vitals.getHeartRate())||StringUtils.isNotBlank(vitals.getRespRate())||StringUtils.isNotBlank(vitals.getBmi())||StringUtils.isNotBlank(vitals.getSugar()))) {
                    vitals.setDate(new Date());
                    vitals.setUser(patient);
                    vitals.setEncounter(encounter);
                    encounter.setVitals(vitals);
                }
                List<Prescription> prescriptions = encounter.getPrescriptions();
                if (prescriptions != null && !prescriptions.isEmpty()) {
                    encounter.setPrescriptions(getPrescriptions(prescriptions, encounter));
                }
                List<PhysicalExamination> physicalExaminations = encounter.getPhysicalExaminations();
                if (physicalExaminations != null && !physicalExaminations.isEmpty()) {
                    encounter.setPhysicalExaminations(getPe(physicalExaminations, encounter));
                }
                List<ReviewofSystems> reviewofSystemses = encounter.getReviewofSystems();
                if (reviewofSystemses != null && !reviewofSystemses.isEmpty()) {
                    encounter.setReviewofSystems(getRos(reviewofSystemses, encounter));
                }
                List<LabOrder> labOrders = encounter.getLabOrders();
                if (labOrders != null && !labOrders.isEmpty()) {
                    encounter.setLabOrders(getLabOrders(labOrders, encounter));
                }

                History history = encounter.getHistory();
                if (history != null) {
                    setHistory(history, patient);
                }
                try{
                    encounterRepository.save(encounter);
                }catch (Exception e){
                    e.printStackTrace();
                    logger.error(e);
                }

             logger.info("End of Method saveEncounter generated encounter-id"+encounter.getEncounterId());

               /* String ticket = alfrescoAuthDetails.getAlfrescoConnectionTicket(patient.getUserName(), patient.getPassword());
                //get patient-user info from alfresco
                String statusCode = alfrescoAuthDetails.getAlfrescoUserDetails(patient.getAlfrescoUrl(), ticket);
                logger.debug("Subscriber id:"+patient.getUserId()+" avialable space is "+ statusCode);

                //check the patient-user has enough space to store the report
                if (statusCode != null) {

                    String[] q = statusCode.split(",");
                    int quota = Integer.parseInt(q[0]);
                    int cz = Integer.parseInt(q[1]);
                    if (quota > cz) {
                        generatePdfAndUploadToPatient(encounter,doctor,patient);
                        encounterRepository.save(encounter);
                        logger.debug("Subscriber with out pdf generation,after saving the encounter: encounterId :"+encounter.getEncounterId());
                    }else{
                        logger.debug("Subscriber with out pdf generation,before saving the encounter");
                        encounterRepository.save(encounter);
                        logger.debug("Subscriber with out pdf generation,after saving the encounter: encounterId :"+encounter.getEncounterId());
                    }
                }*/
            }

        }catch (Exception e){
            e.printStackTrace();
        }

       return encounter;
    }

    @Override
    public History setHistory(History history, User patient) {

        String medical = "";
        String surgical = "";
        String family = "";
        String social = "";
        String allergies = "";
        String medications = "";
        String riskFactor = "";
        History previousHistory = userRepository.getHistoryById(patient.getUserId());
        if (previousHistory != null && history != null) {
            history.setHistoryId(previousHistory.getHistoryId());
            medical = previousHistory.getMedicalHistory();
            surgical = previousHistory.getSurgicalHistory();
            family = previousHistory.getFamilyHistory();
            social = previousHistory.getSocialHistory();
            riskFactor = previousHistory.getRiskFactors();
            medications = previousHistory.getPastMedications();
            allergies = previousHistory.getAllergies();
        }
        if (history != null) {

            String mh = history.getMedicalHistory();
            if (StringUtils.isNotBlank(mh)) {
                medical += "(" + mh + ")_" + DateService.getTodayDateTimeSec() + ";";
            }
            history.setMedicalHistory(medical);

            String sh = history.getSurgicalHistory();
            if (StringUtils.isNotBlank(sh)) {
                surgical += "(" + sh + ")_" + DateService.getTodayDateTimeSec() + ";";
            }
            history.setSurgicalHistory(surgical);

            String fh = history.getFamilyHistory();
            if (StringUtils.isNotBlank(fh)) {
                family += "(" + fh + ")_" + DateService.getTodayDateTimeSec() + ";";
            }
            history.setFamilyHistory(family);

            String sch = history.getSocialHistory();
            if (StringUtils.isNotBlank(sch)) {
                social += "(" + sch + ")_" + DateService.getTodayDateTimeSec() + ";";
            }
            history.setSocialHistory(social);

            String rf = history.getRiskFactors();
            if (StringUtils.isNotBlank(rf)) {
                riskFactor += "(" + rf + ")_" + DateService.getTodayDateTimeSec() + ";";
            }
            history.setRiskFactors(riskFactor);

            String pm = history.getPastMedications();
            if (StringUtils.isNotBlank(pm)) {
                medications += "(" + pm + ")_" + DateService.getTodayDateTimeSec() + ";";
            }
            history.setPastMedications(medications);

            String all = history.getAllergies();
            if (StringUtils.isNotBlank(all)) {
                allergies += "(" + all + ")_" + DateService.getTodayDateTimeSec() + ";";
            }
            history.setAllergies(allergies);
            history.setUser(patient);
           history= historyRepository.save(history);
        }
    return history;
    }

    @Override
    public void generatePdfAndUploadToPatient(Encounter enc,User doctor,User patient){

        logger.info("Start generatePdfAndUploadToPatient and encounterId : "+enc.getEncounterId());

        String patientTicket = alfrescoAuthDetails.getAlfrescoConnectionTicket(patient.getUserName(), patient.getPassword());
        String storageDirectory = utilityServices.getMessage("Encounter.share.pdf");
        InputStream stream;
        String userParentDir = null;

        //patients folder path
        userParentDir = enc.getUser().getUserParentDir();
        String[] node = userParentDir.split("/");
        String doctorTicket = null;

        if(doctor != null){
            doctorTicket = alfrescoAuthDetails.getAlfrescoConnectionTicket(doctor.getUserName(), doctor.getPassword());
            MyUserDetailsService.getUserFromSession().setAlfresAuthTicket(doctorTicket);
            // patient grant permission to doctor to upload a Encounter report
            alfrescoAuthDetails.grantPermission(patientTicket, doctor.getUserName(), node[3], "Collaborator");
        }

        logger.debug("Subscriber alfresco folder node:"+ node[3]);

        if (enc.getPrescriptions() != null) {
            stream = this.getClass().getClassLoader().getResourceAsStream("prescriptionReport.html");
            generatePrescription(stream, storageDirectory, enc,doctor, userParentDir,new Date(),patient);
        }

        if (enc.getLabOrders() != null && enc.getLabOrders().size() > 0) {
            stream = this.getClass().getClassLoader().getResourceAsStream("labOrderReport.html");
            generateLabOrder(stream, storageDirectory, enc, doctor,  userParentDir,new Date(),patient);

        }
        if (enc.getSoap() != null) {
            stream = this.getClass().getClassLoader().getResourceAsStream("soap.html");
            generateSOAPReport( stream, storageDirectory, enc,doctor, userParentDir,new Date(),patient);

        } else {
            stream = this.getClass().getClassLoader().getResourceAsStream("summaryReport.html");
            generateSummaryReport( stream, storageDirectory,  enc,doctor,userParentDir,new Date(),patient);
        }

        //invalid the ticket
        alfrescoAuthDetails.logoutTicket(patientTicket);
        if(doctorTicket != null) {
            //delete the grant permission to doctor by patient
            alfrescoAuthDetails.deletePermission(patientTicket, doctor.getUserName(), userParentDir, "Collaborator");
            alfrescoAuthDetails.logoutTicket(doctorTicket);
        }
        logger.info("End generatePdfAndUploadToPatient");
    }

    private void generatePrescription(InputStream stream, String storageDirectory, Encounter enc,User doctor, String userParentDir, Date date, User patient) {

        logger.debug("method : generatePrescription");

        Long id=enc.getEncounterId();
        //generating file name for pdf
        String fileName = "prescription" + id + ".pdf";
        logger.info("Prescription PDF : generating file name "+fileName);
        List<UploadFile> uploadFileList = new ArrayList<>();
        String prescriptionFilePath = storageDirectory + fileName;
        File newFile = new File(prescriptionFilePath);

        //generate the pdf and store in local path (newFile)
        if (doctor != null && doctor.getClinicId() != null) {
            User clinic = userRepository.searchById(doctor.getClinicId());
            pdfGenerationService.pdfGenerator(newFile, stream, enc,doctor, clinic);
        } else {
            pdfGenerationService.pdfGenerator(newFile, stream, enc,doctor, null);
        }

        //from local path(newFile) take the generated pdf and upload to alfresco (userParentDir)
        Map<String, String> stringMap = alfrescoAuthDetails.uploadFiles(newFile, fileName, "application/pdf", userParentDir);

        int uploadStatusCode = Integer.parseInt(stringMap.get("statusCode"));
        logger.info("Prescription PDF : generated pdf upload to alfresco (userParentDir) result "+uploadStatusCode);
        //since prescription need to be show in subscriber dashboard set the encounter share option true
        /* enc.setShare(true);*/
        if (uploadStatusCode == 200) {
            UploadFile uploadFile = new UploadFile(fileName, "application/pdf", "prescription", enc.getMiniEncounter().getChiefComplaint(), enc.getMiniEncounter().getImpression(), "General", stringMap.get("nodeRef"), patient, date, date,true,enc.getClinicId(),enc.getDoctorId());
            uploadFile.setEncounter(enc);
            uploadFileRepository.save(uploadFile);
            //delete the file from local path
            newFile.deleteOnExit();
         logger.info("method : generatePrescription End of method");
        }
    }

    private void generateLabOrder(InputStream stream, String storageDirectory, Encounter enc,User doctor, String userParentDir,Date date, User patient) {

        logger.info("method : generateLabOrder");

        Long id=enc.getEncounterId();
        //generating file name for pdf
        String fileName = "labOrder" + id + ".pdf";

        String labOrderFilePath = storageDirectory + fileName;
        File newFile = new File(labOrderFilePath);
        logger.info("LabOrder PDF : generating file name "+fileName);

        //generate the pdf and store in local path (newFile)
        if (doctor != null && doctor.getClinicId() != null) {
            User clinic = userRepository.searchById(doctor.getClinicId());
            pdfGenerationService.pdfGenerator(newFile, stream, enc,doctor, clinic);
        } else {
            pdfGenerationService.pdfGenerator(newFile, stream, enc,doctor, null);
        }

        //from local path(newFile) take the generated pdf and upload to alfresco (userParentDir)
        Map<String, String> stringMap = alfrescoAuthDetails.uploadFiles(newFile, fileName, "application/pdf", userParentDir);
        int uploadStatusCode = Integer.parseInt(stringMap.get("statusCode"));
        logger.info("LabOrder PDF : generated pdf upload to alfresco (userParentDir) result "+uploadStatusCode);

        if (uploadStatusCode == 200) {
            //since LabOrder need to be show in subscriber dashboard set the encounter share option true
            UploadFile uploadFile = new UploadFile(fileName, "application/pdf", "laborder", enc.getMiniEncounter().getChiefComplaint(),  enc.getMiniEncounter().getImpression(), "General", stringMap.get("nodeRef"), patient, date, date,true,enc.getClinicId(),enc.getDoctorId());
            uploadFile.setEncounter(enc);
            uploadFileRepository.save(uploadFile);
            //delete the file from local path
            newFile.deleteOnExit();
            logger.info("method : generateLabOrder End of method");
        }


    }

    private void generateSummaryReport(InputStream stream, String storageDirectory, Encounter enc,User doctor, String userParentDir,Date date, User patient) {

        logger.info("method : generateSummaryReport");

        Long id=enc.getEncounterId();
        //generating file name for pdf
        String fileName = "summaryReport" + id + ".pdf";
        String summaryFilePath = storageDirectory + fileName;
        File newFile = new File(summaryFilePath);

        logger.debug("summaryReport PDF : generating file name "+fileName);
        //generate the pdf and store in local path (newFile)
        if (doctor != null) {
            User clinic = userRepository.searchById(doctor.getClinicId());
            pdfGenerationService.pdfGenerator(newFile, stream, enc,doctor, clinic);
        } else {
            pdfGenerationService.pdfGenerator(newFile, stream, enc, null, null);
        }

        //from local path(newFile) take the generated pdf and upload to alfresco (userParentDir)
        Map<String, String> stringMap = alfrescoAuthDetails.uploadFiles(newFile, fileName, "application/pdf", userParentDir);
        int uploadStatusCode = Integer.parseInt(stringMap.get("statusCode"));
        logger.info("summaryReport PDF : generated pdf upload to alfresco (userParentDir) result "+uploadStatusCode);
        if (uploadStatusCode == 200) {
            UploadFile uploadFile = new UploadFile(fileName, "application/pdf", "hospital", enc.getMiniEncounter().getChiefComplaint(), enc.getMiniEncounter().getImpression(), "General", stringMap.get("nodeRef"), patient, date, date,enc.isShare(),enc.getClinicId(),enc.getDoctorId());
            uploadFile.setEncounter(enc);
            uploadFileRepository.save(uploadFile);
            //delete the file from local path
            newFile.deleteOnExit();
            logger.info("method : generateSummaryReport End");
        }

    }

    private void generateSOAPReport(InputStream stream, String storageDirectory, Encounter enc,User doctor, String userParentDir,Date date, User patient) {

        logger.info("method : generateSOAPReport");

        Long id=enc.getEncounterId();
        //generating file name for pdf
        String fileName = "soap" + id + ".pdf";

        String summaryFilePath = storageDirectory + fileName;
        File newFile = new File(summaryFilePath);

        logger.info("SOAPReport PDF : generating file name "+fileName);
        //generate the pdf and store in local path (newFile)
        if (doctor.getClinicId() != null) {
            User clinic = userRepository.searchById(doctor.getClinicId());
            pdfGenerationService.pdfGenerator(newFile, stream, enc, doctor, clinic);
        } else {
            pdfGenerationService.pdfGenerator(newFile, stream, enc, doctor, null);
        }

        //from local path(newFile) take the generated pdf and upload to alfresco (userParentDir)
        Map<String, String> stringMap = alfrescoAuthDetails.uploadFiles(newFile, fileName, "application/pdf", userParentDir);
        int uploadStatusCode = Integer.parseInt(stringMap.get("statusCode"));
        logger.info("SOAPReport PDF : generated pdf upload to alfresco (userParentDir) result "+uploadStatusCode);

        if (uploadStatusCode == 200) {
            UploadFile uploadFile = new UploadFile(fileName, "application/pdf", "hospital", enc.getSoap().getSubjective(), enc.getSoap().getAssessment(), "General", stringMap.get("nodeRef"), patient, date, date,enc.isShare(),enc.getClinicId(),enc.getDoctorId());
            uploadFile.setEncounter(enc);
            uploadFileRepository.save(uploadFile);
            //delete the file from local path
            newFile.deleteOnExit();
            logger.info("method : generateSOAPReport End");
        }
    }

}
