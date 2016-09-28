package com.datalife.repositories.impl;

import com.datalife.entities.*;
import com.datalife.repositories.ClinicDoctorsRepository;
import com.datalife.repositories.CommonRepository;
import com.datalife.repositories.DoctorInfoRepository;
import com.datalife.repositories.UserRepository;
import com.datalife.services.CommonServices;
import com.datalife.services.UtilityServices;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.*;

/**
 * Created by supriya gondi on 10/28/2014.
 */
@Repository
public class CommonRepositoryImpl implements CommonRepository {


    /**
     * Reference to em
     */
    @Autowired
    EntityManagerFactory em;

    @Autowired
    ClinicDoctorsRepository clinicDoctorsRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UtilityServices utilityServices;

    @Autowired
    DoctorInfoRepository doctorInfoRepository;


    @Override
    public int anylicenceNumberExistInDb(String licNo) {

        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createNativeQuery("SELECT EXISTS(\n" +
                "SELECT 1 FROM doctorinfo WHERE MRNumber =:input\n" +
                "UNION\n" +
                "SELECT 1 FROM clinicinfo WHERE MRNumber =:input\n" +
                "union\n" +
                "SELECT 1 FROM serviceprovider WHERE licenceNumber =:input\n" +
                "UNION\n" +
                "SELECT 1 FROM providerdetails WHERE licNo =:input)");
        query.setParameter("input", licNo);
        int b = ((BigInteger)  query.getSingleResult()).intValue();
        entityManager.close();
        return b;
    }

    @Override
    public int checkEmailIdExistAcceptPatientInDb(String email) {
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createNativeQuery("select Exists(select 1 from (select email as email from providerdetails  union all select email from userview where userview.role != 'ROLE_PATIENT')a where email =:input)");
        query.setParameter("input", email);
        int b = ((BigInteger)  query.getSingleResult()).intValue();
        entityManager.close();
        return b;
    }

    @Override
    public List<Object> searchByDoctorName(String name) {
        List<Object> objects;
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createNativeQuery("select * from search as s where s.activate=true and s.firstName LIKE :input OR s.lastName LIKE :input");
        query.setParameter("input", name + "%");
        objects = query.getResultList();
        entityManager.close();
        return objects;
    }

    @Override
    public Map<String, List<String>> getNewDoctorTimings(NewTimings newTimings) {

        NewTimings nt = checkNewDayWiseSetting(newTimings);
        // Map to store Working Days and Shifts;key=Shifts, value=Days
        Map<List<String>, List<String>> workingHoursMap = getNewWorkingHours(nt);
        //Mapto store the shortened days with timings
        return consolidateDays(workingHoursMap);
    }

    @Override
    public List<Object> searchClinicById(Long id) {
        List<Object> objects;
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createNativeQuery("select * from search as s where s.clinicId =:id and s.activate=true");
        query.setParameter("id", id);
        objects = query.getResultList();
        entityManager.close();
        return objects;
    }

    @Override
    public List<Object> searchByClinicName(String clinicName) {
        List<Object> objects;
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createNativeQuery("select * from search as s where s.activate=true and s.clinicName LIKE :input");
        query.setParameter("input", clinicName + "%");
        objects = query.getResultList();
        entityManager.close();
        return objects;
    }

    @Override
    public List<Object> searchDoctorById(Long id) {
        List<Object> objects;
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createNativeQuery("select * from search as s where s.doctorId =:id");
        query.setParameter("id", id);
        objects = query.getResultList();
        entityManager.close();
        return objects;
    }


    @Override
    @Transactional
    public List<BloodGroup> getBloodGroups() {
        List<BloodGroup> bloodGroups;
        EntityManager entityManager = em.createEntityManager();
        bloodGroups = entityManager.createQuery("from BloodGroup").getResultList();
        entityManager.close();
        return bloodGroups;
    }

    @Override
    public Object getDate() {
        Object object;
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createNativeQuery("select  current_date as DATE from Dual");
        object = query.getSingleResult();
        entityManager.close();
        return object;
    }

    @Override
    public List<Speciality> getSpecialities() {
        List<Speciality> specialities;
        EntityManager entityManager = em.createEntityManager();
        specialities = entityManager.createQuery("from Speciality ").getResultList();
        entityManager.close();
        return specialities;
    }

    @Override
    public List<RecordSpeciality> getRecordSpecialities() {
        List<RecordSpeciality> specialities;
        EntityManager entityManager = em.createEntityManager();
        specialities = entityManager.createQuery("from RecordSpeciality").getResultList();
        entityManager.close();
        return specialities;
    }

    @Override
    @Transactional
    public List<Country> getCountries() {
        List<Country> countries;
        EntityManager entityManager = em.createEntityManager();
        countries = entityManager.createQuery("from Country ").getResultList();
        entityManager.close();
        return countries;
    }

    @Override
    @Transactional
    public List<LabTestCategories> getLabTestCategories() {
        List<LabTestCategories> testCategoriesList;
        EntityManager entityManager = em.createEntityManager();
        testCategoriesList = entityManager.createQuery("from LabTestCategories ").getResultList();
        entityManager.close();
        return testCategoriesList;
    }

    @Override
    @Transactional
    public List<State> getStates(Long countryId) {
        List<State> states;
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createQuery("from State as s where s.country.countryId=:countryId");
        query.setParameter("countryId", countryId);
        states = query.getResultList();
        entityManager.close();
        return states;
    }

    @Override
    @Transactional
    public List<CardiovascularLabels> getCardiovascularLabelList() {
        List<CardiovascularLabels> labels;
        EntityManager entityManager = em.createEntityManager();
        labels = entityManager.createQuery("select cardioLabelId,cardioLabel, labelType from CardiovascularLabels").getResultList();
        entityManager.close();
        return labels;
    }

    public List<City> getCities(Long stateId) {
        List<City> cities;
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createQuery("from City as c where c.stateId=:stateId");
        query.setParameter("stateId", stateId);
        cities = query.getResultList();
        entityManager.close();
        return cities;
    }


    @Override
    public List<MuscoloskeletalLabels> getMuscoloskeletalLabelList() {
        List<MuscoloskeletalLabels> list;
        EntityManager entityManager = em.createEntityManager();
        list = entityManager.createQuery("select muscoloLabelId,muscoloLabel,labelType  from MuscoloskeletalLabels ").getResultList();
        entityManager.close();
        return list;
    }


    @Override
    public List<GastrointestinalLabels> getGastrointestinalLabelList() {
        List<GastrointestinalLabels> list;
        EntityManager entityManager = em.createEntityManager();
        list = entityManager.createQuery("select gastroLabelId,gastroLabel,labelType  from GastrointestinalLabels ").getResultList();
        entityManager.close();
        return list;
    }

    @Override
    @Transactional
    public List<RespiratoryLabels> getRespiratoryLabelList() {
        List<RespiratoryLabels> list;
        EntityManager entityManager = em.createEntityManager();
        list = entityManager.createQuery("select respiratoryLabelId,respiratoryLabel,labelType from RespiratoryLabels ").getResultList();
        entityManager.close();
        return list;
    }

    @Override
    @Transactional
    public List<String> getLabCategories() {
        List<String> list;
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createQuery("select name from LabCategories ");
        list = query.getResultList();
        entityManager.close();
        return list;
    }

    @Override
    @Transactional
    public List<ROSCategory> getRosCategories() {
        List<ROSCategory> list;
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createQuery("from ROSCategory ");
        list = query.getResultList();
        entityManager.close();
        return list;
    }

    @Override
    public String getROSCategoryName(Byte id) {
        String str;
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createQuery("select ros.name from ROSCategory as ros where ros.categoryId=:id");
        query.setParameter("id", id.longValue());
        str = query.getSingleResult().toString();
        entityManager.close();
        return str;
    }

    @Override
    public String getBloodgroupById(Integer id) {
        String str;
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createQuery("select b.type from BloodGroup as b where b.bloodGroupId=:id");
        query.setParameter("id", id.longValue());
        str = query.getSingleResult().toString();
        entityManager.close();
        return str;
    }

    @Override
    @Transactional
    public List<PETypes> getPECategories() {
        List<PETypes> types;
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createQuery("from PETypes ");
        types = query.getResultList();
        entityManager.close();
        return types;
    }

    @Override
    @Transactional
    public List<IcdCode> getICDValues(String input) {
        List<IcdCode> codes;
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createQuery("from IcdCode as icd where icd.name like :input", IcdCode.class);
        query.setParameter("input", input + "%");
        codes = query.getResultList();
        entityManager.close();
        return codes;
    }


    @Override
    @Transactional
    public List<IcdCode> getICDName(String input) {
        List<IcdCode> codes;
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createQuery("from IcdCode as icd where icd.icdCode like :input", IcdCode.class);
        query.setParameter("input", input + "%");
        codes = query.getResultList();
        entityManager.close();
        return codes;
    }

    @Override
    @Transactional
    public List<Route> getRouteName(String input) {
        List<Route> routes;
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createQuery("from Route as rt where rt.name like :input", Route.class);
        query.setParameter("input", input + "%");
        routes = query.getResultList();
        entityManager.close();
        return routes;
    }

    @Override
    @Transactional
    public List<Form> getFormName(String input) {
        List<Form> forms;
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createQuery("from Form as fr where fr.name like :input", Form.class);
        query.setParameter("input", input + "%");
        forms = query.getResultList();
        entityManager.close();
        return forms;
    }

    @Override
    public List<Frequency> getFrequencyName(String input) {
        List<Frequency> frequencies;
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createQuery("from Frequency as fq where fq.name like :input", Frequency.class);
        query.setParameter("input", input + "%");
        frequencies = query.getResultList();
        entityManager.close();
        return frequencies;
    }

    @Override
    public List<Drug> getGenericNames(String input) {
        List<Drug> drugs;
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createQuery("from Drug as d where d.brandName like :input", Drug.class);
        query.setParameter("input", input + "%");
        drugs = query.getResultList();
        entityManager.close();
        return drugs;
    }

    @Override
    public String getCountryById(Long countryId) {
        String str;
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createQuery("select c.countryName from Country as c where c.countryId=:countryId");
        query.setParameter("countryId", countryId);
        str = query.getSingleResult().toString();
        entityManager.close();
        return str;
    }


    @Override
    public String getStateById(Long stateId) {
        String str;
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createQuery("select s.stateName from State as s where s.stateId=:stateId");
        query.setParameter("stateId", stateId);
        str = query.getSingleResult().toString();
        entityManager.close();
        return str;
    }


    @Override
    public String getSpecialityById(Long specialityId) {
        String str;
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createQuery("select s.name from Speciality as s where s.specialityId=:specialityId");
        query.setParameter("specialityId", specialityId);
        str = query.getSingleResult().toString();
        entityManager.close();
        return str;
    }


    @Override
    @Transactional
    public List<Speciality> getSpecialistLike(String input) {
        List<Speciality> specialities;
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createQuery("from Speciality as splty where splty.name like :input", Speciality.class);
        query.setParameter("input", input + '%');
        specialities = query.getResultList();
        entityManager.close();
        return specialities;
    }

    @Override
    public String[] getEmails(String[] emailIds) {
        String[] emails = new String[emailIds.length];
        System.arraycopy(emailIds, 0, emails, 0, emailIds.length);
        return emails;
    }

    @Override
    @Transactional
    public LabTests getLabTestsName(Long labTestId) {
        LabTests labTests;
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createQuery("FROM LabTests as lab where lab.labTestsId =:labTestId");
        query.setParameter("labTestId", labTestId);
        labTests = (LabTests) query.getSingleResult();
        entityManager.close();
        return labTests;
    }

    @Override
    @Transactional
    public List<PhysicalExamination> getPeName(Long encounterId) {
        List<PhysicalExamination> list;
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createQuery("FROM PhysicalExamination as pe where pe.encounter.encounterId =:encounterId");
        query.setParameter("encounterId", encounterId);
        list = query.getResultList();
        entityManager.close();
        return list;
    }

    @Override
    public String getPENameById(Long id) {
        String str;
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createQuery("select pe.name from PETypes as pe where pe.peTypeId=:peTypeId");
        query.setParameter("peTypeId", id);
        str = query.getSingleResult().toString();
        entityManager.close();
        return str;
    }

    @Override
    public PETypes getPETypesById(Long id) {
        PETypes types;
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createQuery("from PETypes as pe where pe.peTypeId=:peTypeId");
        query.setParameter("peTypeId", id);
        types = (PETypes) query.getSingleResult();
        entityManager.close();
        return types;
    }

    @Override
    public String getrespLableNameById(Long id) {
        String str;
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createQuery("select r.respiratoryLabel from RespiratoryLabels as r where r.respiratoryLabelId=:respiratoryLabelId");
        query.setParameter("respiratoryLabelId", id);
        str = query.getSingleResult().toString();
        entityManager.close();
        return str;
    }

    @Override
    public String getcardioLableNameById(Long id) {
        String str;
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createQuery("select r.cardioLabel from CardiovascularLabels as r where r.cardioLabelId=:cardioLabelId");
        query.setParameter("cardioLabelId", id);
        str = query.getSingleResult().toString();
        entityManager.close();
        return str;
    }

    @Override
    public String getmuscoloLableNameById(Long id) {
        String str;
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createQuery("select r.muscoloLabel from MuscoloskeletalLabels as r where r.muscoloLabelId=:muscoloLabelId");
        query.setParameter("muscoloLabelId", id);
        str = query.getSingleResult().toString();
        entityManager.close();
        return str;
    }

    @Override
    public String getgastroLableNameById(Long id) {
        String str;
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createQuery("select r.gastroLabel from GastrointestinalLabels as r where r.gastroLabelId=:gastroLabelId");
        query.setParameter("gastroLabelId", id);
        str = query.getSingleResult().toString();
        entityManager.close();
        return str;
    }


    @Override
    @Transactional
    public List<Object> getDoctors(String name) {
        List<Object> objects;
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createNativeQuery("select d.doctorId ,d.firstName,d.lastName from search as d where d.docName like :input AND d.activate=true");
        query.setParameter("input","%"+ name + "%");
        objects = query.getResultList();
        entityManager.close();
        return objects;
    }


    @Override
    @Transactional
    public List<Object> searchClinicsLike(String name) {
        List<Object> objects;
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createNativeQuery("select distinct d.clinicId,d.clinicName from search as d where d.clinicName like :input");
        query.setParameter("input", name + "%");
        objects = query.getResultList();
        entityManager.close();
        return objects;
    }

    @Override
    public List<Location> getLocationLike(String input) {
        List<Location> locations;
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createQuery("from Location as loc where loc.locationName like :name", Location.class);
        query.setParameter("name", input + "%");
        locations = query.getResultList();
        entityManager.close();
        return locations;
    }

    public List<Location> getLocations() {
        List<Location> locations;
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createQuery("from Location", Location.class);
        locations = query.getResultList();
        entityManager.close();
        return locations;
    }


    @Override
    public Object getUserDetailsByUserNameInuserView(String userName) {

        EntityManager entityManager = em.createEntityManager();
        Object result = null;
        Query query = entityManager.createNativeQuery("select * from userview as s where s.userName =:input");
        query.setParameter("input", userName);
        try {
            result = query.getSingleResult();
        } catch (NoResultException exception) {
            exception.printStackTrace();
        }
        entityManager.close();
        return result;
    }

    @Override
    public Object getUserDetailsByUserIdInuserView(Long userId) {
        EntityManager entityManager = em.createEntityManager();
        Object result = null;
        Query query = entityManager.createNativeQuery("select * from userview as s where s.userId =:input");
        query.setParameter("input", userId);
        try {
            result = query.getSingleResult();
        } catch (NoResultException exception) {
            exception.printStackTrace();
        }
        entityManager.close();
        return result;

    }

    @Override
    public Object getDoctorDetailBydoctorIdInuserView(Long doctorId, Long clinicId) {
        EntityManager entityManager = em.createEntityManager();
        Object result = null;
        Query query = entityManager.createNativeQuery("select * from search as s where s.doctorId =:input and s.clinicId =:input2");
        query.setParameter("input", doctorId);
        query.setParameter("input2", clinicId);
        try {
            result = query.getSingleResult();
        } catch (NoResultException exception) {
            exception.printStackTrace();
        }
        entityManager.close();
        return result;
    }

    @Override
    public List<Object> searchByLocation(String location) {
        List<Object> objects;
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createNativeQuery("select * from search as s where s.activate=true and (s.address LIKE :input OR s.city LIKE :input OR s.location LIKE :input)");
        query.setParameter("input", location + "%");
        objects = query.getResultList();
        entityManager.close();
        return objects;
    }


    @Override
    public List<Object> searchBySpeciality(Long speciality) {
        List<Object> objects;
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createNativeQuery("select * from search as s where s.specialityId =:input and s.activate=true");
        query.setParameter("input", speciality);
        objects = query.getResultList();
        entityManager.close();
        return objects;
    }

    @Override
    public List<Object> searchBySpecialityLocation(Long speciality, String location) {
        List<Object> objects;
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createNativeQuery("select * from search as s where s.activate=true and (s.address LIKE :loc or s.city LIKE :loc or s.location like :loc) and (s.specialityId =:input)");
        query.setParameter("input", speciality);
        query.setParameter("loc", location + "%");
        objects = query.getResultList();
        entityManager.close();
        return objects;
    }


    @Override
    public List<Object> searchByClinicDoctorNameAndLocation(String name, String location) {
        List<Object> objects;
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createNativeQuery("select * from search as s where s.activate=true and (s.address LIKE :loc or s.city LIKE :loc or s.location like :loc )and (s.clinicName LIKE:input or s.docName LIKE :input)");
        query.setParameter("input", name+ "%");
        query.setParameter("loc", location + "%");
        objects = query.getResultList();
        entityManager.close();
        return objects;
    }


    @Override
    public Object findByDoctorAndClinic(Long doctorId, Long clinicId) {

        EntityManager entityManager = em.createEntityManager();
        Object result = null;
        Query query = entityManager.createNativeQuery("select * from search as s where s.doctorId=:doctorId and s.clinicId=:clinicId");
        query.setParameter("doctorId", doctorId + "%");
        query.setParameter("clinicId", clinicId + "%");
        try {
            result = query.getSingleResult();
        } catch (NoResultException exception) {
            exception.printStackTrace();
        }
        entityManager.close();
        return result;
    }

    @Override
    public Map<Long, String> findClinicsByDoctorId(Long doctorId) {
        Map<Long, String> clinics = new LinkedHashMap<>();
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createNativeQuery("select s.clinicId,s.clinicName from search as s where s.doctorId=:doctorId and s.activate=true");
        query.setParameter("doctorId", doctorId);
        List<Object> objects = query.getResultList();
        for (Object o : objects) {
            Object[] ob = (Object[]) o;
            clinics.put(((BigInteger) ob[0]).longValue(), ob[1].toString());
        }
        entityManager.close();
        return clinics;
    }

    @Override
    public Search getRegularTimeSlots(Long doctorId, Long clinicId) {
        ClinicDoctors clinicTimeSlot = null;
        Search search = new Search();
        List<ClinicDoctors> doctorTimeSlotList = clinicDoctorsRepository.getDoctorTimeSlot(doctorId, clinicId);
        if (doctorTimeSlotList != null) {
            for (ClinicDoctors clinicDoctors : doctorTimeSlotList) {
                clinicTimeSlot = checkDayWiseSetting(clinicDoctors);

                if (clinicTimeSlot != null) {

                    // Map to store Working Days and Shifts;key=Shifts, value=Days
                    Map<List<String>, List<String>> workingHoursMap = getWorkingHours(clinicTimeSlot);

                    //Mapto store the shortened days with timings
                    Map<String, List<String>> consolidatedWorkingHours = consolidateDays(workingHoursMap);
                    String generalTime = "";
                    for (Map.Entry<String, List<String>> entry : consolidatedWorkingHours.entrySet()) {
                        generalTime += entry.getKey() + entry.getValue();
                    }
                    if (generalTime != null && generalTime != "") {
                        search.setCdGeneralTime(generalTime);
                    }
                }
            }
        }
        return search;
    }

    public Map<String, List<String>> getDoctorTimings(ClinicDoctors clinicDoctors) {

        ClinicDoctors cd = checkDayWiseSetting(clinicDoctors);

        // Map to store Working Days and Shifts;key=Shifts, value=Days
        Map<List<String>, List<String>> workingHoursMap = getWorkingHours(cd);

        //Mapto store the shortened days with timings
        return consolidateDays(workingHoursMap);

    }

    public Map<List<String>, List<String>> getWorkingHours(
            ClinicDoctors clinicDoctors) {

        Map<List<String>, List<String>> workingHoursMap = new HashMap<List<String>, List<String>>();

        // Map to store shifts for each day
        Map<String, List<String>> dailyShiftsMap = getDailyShifts(clinicDoctors);

        for (Day dayEnum : Day.values()) {
            String day = dayEnum.toString();
            List<String> list = dailyShiftsMap.get(day);

            if (list != null && !list.isEmpty()) {
                List<String> daysList = workingHoursMap.get(list);
                if (daysList == null) {
                    daysList = new ArrayList<String>();
                    workingHoursMap.put(list, daysList);
                }
                daysList.add(day);
            }
        }

        return workingHoursMap;
    }

    /**
     * Groups all timings by days of the week
     *
     * @return Map<String, List<String>> getDailyShifts
     */
    @Override
    public Map<String, List<String>> getDailyShifts(ClinicDoctors clinicDoctors) {
        Map<String, List<String>> dailyShiftsMap = new HashMap<String, List<String>>();

        boolean status_1 = false;
        boolean status_2 = false;
        String st_1 = "";
        String et_1 = "";
        String st_2 = "";
        String et_2 = "";

        for (Day day : Day.values()) {
            String dayString = day.toString();

            switch (dayString) {
                case "MON":
                    status_1 = clinicDoctors.isMonStatus_1();
                    status_2 = clinicDoctors.isMonStatus_2();
                    st_1 = clinicDoctors.getMonst_1();
                    et_1 = clinicDoctors.getMonet_1();
                    st_2 = clinicDoctors.getMonst_2();
                    et_2 = clinicDoctors.getMonet_2();
                    break;
                case "TUE":
                    status_1 = clinicDoctors.isTueStatus_1();
                    status_2 = clinicDoctors.isTueStatus_2();
                    st_1 = clinicDoctors.getTuest_1();
                    et_1 = clinicDoctors.getTueet_1();
                    st_2 = clinicDoctors.getTuest_2();
                    et_2 = clinicDoctors.getTueet_2();
                    break;
                case "WED":
                    status_1 = clinicDoctors.isWedStatus_1();
                    status_2 = clinicDoctors.isWedStatus_2();
                    st_1 = clinicDoctors.getWedst_1();
                    et_1 = clinicDoctors.getWedet_1();
                    st_2 = clinicDoctors.getWedst_2();
                    et_2 = clinicDoctors.getWedet_2();
                    break;
                case "THU":
                    status_1 = clinicDoctors.isThuStatus_1();
                    status_2 = clinicDoctors.isThuStatus_2();
                    st_1 = clinicDoctors.getThust_1();
                    et_1 = clinicDoctors.getThuet_1();
                    st_2 = clinicDoctors.getThust_2();
                    et_2 = clinicDoctors.getThuet_2();
                    break;
                case "FRI":
                    status_1 = clinicDoctors.isFriStatus_1();
                    status_2 = clinicDoctors.isFriStatus_2();
                    st_1 = clinicDoctors.getFrist_1();
                    et_1 = clinicDoctors.getFriet_1();
                    st_2 = clinicDoctors.getFrist_2();
                    et_2 = clinicDoctors.getFriet_2();
                    break;
                case "SAT":
                    status_1 = clinicDoctors.isSatStatus_1();
                    status_2 = clinicDoctors.isSatStatus_2();
                    st_1 = clinicDoctors.getSatst_1();
                    et_1 = clinicDoctors.getSatet_1();
                    st_2 = clinicDoctors.getSatst_2();
                    et_2 = clinicDoctors.getSatet_2();
                    break;
                case "SUN":
                    status_1 = clinicDoctors.isSunStatus_1();
                    status_2 = clinicDoctors.isSunStatus_2();
                    st_1 = clinicDoctors.getSunst_1();
                    et_1 = clinicDoctors.getSunet_1();
                    st_2 = clinicDoctors.getSunst_2();
                    et_2 = clinicDoctors.getSunet_2();
                    break;

            }

            List<String> timingsList = dailyShiftsMap.get(dayString);
            if (timingsList == null) {
                timingsList = new ArrayList<String>();
                dailyShiftsMap.put(dayString, timingsList);
            }

            if (status_1) {
                if (st_1 != null && !st_1.isEmpty() && et_1 != null
                        && !et_1.isEmpty()) {
                    if (!(st_1.equals(et_1))) {
                        timingsList.add(st_1 + "-" + et_1);
                    }

                }
            }
            if (status_2) {
                if (st_2 != null && !st_2.isEmpty() && et_2 != null
                        && !et_2.isEmpty()) {
                    if (!(st_2.equals(et_2))) {
                        timingsList.add(st_2 + "-" + et_2);
                    }

                }
            }
        }

        return dailyShiftsMap;
    }

    @Override
    public ClinicDoctors checkDayWiseSetting(ClinicDoctors clinicDoctors) {
        ClinicDoctors clinicTimeSlot = new ClinicDoctors();
        if (clinicDoctors != null) {
            if (clinicDoctors.isMonStatus_1()) {
                if ((clinicDoctors.getMonst_1() != null && !clinicDoctors.getMonst_1().isEmpty()) && (clinicDoctors.getMonet_1() != null && !clinicDoctors.getMonet_1().isEmpty())) {
                    clinicTimeSlot.setMonst_1(clinicDoctors.getMonst_1());
                    clinicTimeSlot.setMonet_1(clinicDoctors.getMonet_1());
                    clinicTimeSlot.setMonStatus_1(clinicDoctors.isMonStatus_1());
                }
            }
            if (clinicDoctors.isMonStatus_2()) {
                if ((clinicDoctors.getMonst_2() != null && !clinicDoctors.getMonst_2().isEmpty()) && (clinicDoctors.getMonet_2() != null && !clinicDoctors.getMonet_2().isEmpty())) {
                    clinicTimeSlot.setMonst_2(clinicDoctors.getMonst_2());
                    clinicTimeSlot.setMonet_2(clinicDoctors.getMonet_2());
                    clinicTimeSlot.setMonStatus_2(clinicDoctors.isMonStatus_2());
                }
            }
            if (clinicDoctors.isTueStatus_1()) {
                if ((clinicDoctors.getTuest_1() != null && !clinicDoctors.getTuest_1().isEmpty()) && (clinicDoctors.getTueet_1() != null && !clinicDoctors.getTueet_1().isEmpty())) {
                    clinicTimeSlot.setTuest_1(clinicDoctors.getTuest_1());
                    clinicTimeSlot.setTueet_1(clinicDoctors.getTueet_1());
                    clinicTimeSlot.setTueStatus_1(clinicDoctors.isTueStatus_1());
                }
            }
            if (clinicDoctors.isTueStatus_2()) {
                if ((clinicDoctors.getTuest_2() != null && !clinicDoctors.getTuest_2().isEmpty()) && (clinicDoctors.getTueet_2() != null && !clinicDoctors.getTueet_2().isEmpty())) {
                    clinicTimeSlot.setTuest_2(clinicDoctors.getTuest_2());
                    clinicTimeSlot.setTueet_2(clinicDoctors.getTueet_2());
                    clinicTimeSlot.setTueStatus_2(clinicDoctors.isTueStatus_2());
                }
            }
            if (clinicDoctors.isWedStatus_1()) {
                if ((clinicDoctors.getWedst_1() != null && !clinicDoctors.getWedst_1().isEmpty()) && (clinicDoctors.getWedet_1() != null && !clinicDoctors.getWedet_1().isEmpty())) {
                    clinicTimeSlot.setWedst_1(clinicDoctors.getWedst_1());
                    clinicTimeSlot.setWedet_1(clinicDoctors.getWedet_1());
                    clinicTimeSlot.setWedStatus_1(clinicDoctors.isWedStatus_1());
                }
            }
            if (clinicDoctors.isWedStatus_2()) {
                if ((clinicDoctors.getWedst_2() != null && !clinicDoctors.getWedst_2().isEmpty()) && (clinicDoctors.getWedet_2() != null && !clinicDoctors.getWedet_2().isEmpty())) {
                    clinicTimeSlot.setWedst_2(clinicDoctors.getWedst_2());
                    clinicTimeSlot.setWedet_2(clinicDoctors.getWedet_2());
                    clinicTimeSlot.setWedStatus_2(clinicDoctors.isWedStatus_2());
                }
            }
            if (clinicDoctors.isThuStatus_1()) {
                if ((clinicDoctors.getThust_1() != null && !clinicDoctors.getThust_1().isEmpty()) && (clinicDoctors.getThuet_1() != null && !clinicDoctors.getThuet_1().isEmpty())) {
                    clinicTimeSlot.setThust_1(clinicDoctors.getThust_1());
                    clinicTimeSlot.setThuet_1(clinicDoctors.getThuet_1());
                    clinicTimeSlot.setThuStatus_1(clinicDoctors.isThuStatus_1());
                }
            }
            if (clinicDoctors.isThuStatus_2()) {
                if ((clinicDoctors.getThust_2() != null && !clinicDoctors.getThust_2().isEmpty()) && (clinicDoctors.getThuet_2() != null && !clinicDoctors.getThuet_2().isEmpty())) {
                    clinicTimeSlot.setThust_2(clinicDoctors.getThust_2());
                    clinicTimeSlot.setThuet_2(clinicDoctors.getThuet_2());
                    clinicTimeSlot.setThuStatus_2(clinicDoctors.isThuStatus_2());
                }
            }
            if (clinicDoctors.isFriStatus_1()) {
                if ((clinicDoctors.getFrist_1() != null && !clinicDoctors.getFrist_1().isEmpty()) && (clinicDoctors.getFriet_1() != null && !clinicDoctors.getFriet_1().isEmpty())) {
                    clinicTimeSlot.setFrist_1(clinicDoctors.getFrist_1());
                    clinicTimeSlot.setFriet_1(clinicDoctors.getFriet_1());
                    clinicTimeSlot.setFriStatus_1(clinicDoctors.isFriStatus_1());
                }
            }
            if (clinicDoctors.isFriStatus_2()) {
                if ((clinicDoctors.getFrist_2() != null && !clinicDoctors.getFrist_2().isEmpty()) && (clinicDoctors.getFriet_2() != null && !clinicDoctors.getFriet_2().isEmpty())) {
                    clinicTimeSlot.setFrist_2(clinicDoctors.getFrist_2());
                    clinicTimeSlot.setFriet_2(clinicDoctors.getFriet_2());
                    clinicTimeSlot.setFriStatus_2(clinicDoctors.isFriStatus_2());
                }
            }
            if (clinicDoctors.isSatStatus_1()) {
                if ((clinicDoctors.getSatst_1() != null && !clinicDoctors.getSatst_1().isEmpty()) && (clinicDoctors.getSatet_1() != null && !clinicDoctors.getSatet_1().isEmpty())) {
                    clinicTimeSlot.setSatst_1(clinicDoctors.getSatst_1());
                    clinicTimeSlot.setSatet_1(clinicDoctors.getSatet_1());
                    clinicTimeSlot.setSatStatus_1(clinicDoctors.isSatStatus_1());
                }
            }
            if (clinicDoctors.isSatStatus_2()) {
                if ((clinicDoctors.getSatst_2() != null && !clinicDoctors.getSatst_2().isEmpty()) && (clinicDoctors.getSatet_2() != null && !clinicDoctors.getSatet_2().isEmpty())) {
                    clinicTimeSlot.setSatst_2(clinicDoctors.getSatst_2());
                    clinicTimeSlot.setSatet_2(clinicDoctors.getSatet_2());
                    clinicTimeSlot.setSatStatus_2(clinicDoctors.isSatStatus_2());
                }
            }
            if (clinicDoctors.isSunStatus_1()) {
                if ((clinicDoctors.getSunst_1() != null && !clinicDoctors.getSunst_1().isEmpty()) && (clinicDoctors.getSunet_1() != null && !clinicDoctors.getSunet_1().isEmpty())) {
                    clinicTimeSlot.setSunst_1(clinicDoctors.getSunst_1());
                    clinicTimeSlot.setSunet_1(clinicDoctors.getSatet_1());
                    clinicTimeSlot.setSunStatus_1(clinicDoctors.isSunStatus_1());
                }
            }
            if (clinicDoctors.isSunStatus_2()) {
                if ((clinicDoctors.getSunst_2() != null && !clinicDoctors.getSunst_2().isEmpty()) && (clinicDoctors.getSunet_2() != null && !clinicDoctors.getSunet_2().isEmpty())) {
                    clinicTimeSlot.setSunst_2(clinicDoctors.getSunst_2());
                    clinicTimeSlot.setSunet_2(clinicDoctors.getSunet_2());
                    clinicTimeSlot.setSunStatus_2(clinicDoctors.isSunStatus_2());
                }
            }
        }
        return clinicTimeSlot;
    }

    private static Map<String, List<String>> consolidateDays(
            Map<List<String>, List<String>> workingHoursMap) {

        Map<String, List<String>> consolidatedWorkingHours = new LinkedHashMap<>();

        for (List<String> shiftList : workingHoursMap.keySet()) {

            String key = "";

            List<String> days = workingHoursMap.get(shiftList);
            for (String day : days) {

                if (key.isEmpty()) {
                    key = day;
                } else {
                    String separator = ",";
                    String currentDay = "";
                    String previousDay = "";
                    switch (day) {
                        case "TUE":
                            currentDay = Day.TUE.toString();
                            previousDay = Day.MON.toString();
                            break;
                        case "WED":
                            currentDay = Day.WED.toString();
                            previousDay = Day.TUE.toString();
                            break;
                        case "THU":
                            currentDay = Day.THU.toString();
                            previousDay = Day.WED.toString();
                            break;
                        case "FRI":
                            currentDay = Day.FRI.toString();
                            previousDay = Day.THU.toString();
                            break;
                        case "SAT":
                            currentDay = Day.SAT.toString();
                            previousDay = Day.FRI.toString();
                            break;
                        case "SUN":
                            currentDay = Day.SUN.toString();
                            previousDay = Day.SAT.toString();
                            break;
                    }

                    if (key.contains(previousDay)) {
                        separator = "- ";
                        key = key.replaceAll(separator + previousDay, " ");
                    }
                    key = key + separator + currentDay;
                }

            }

            consolidatedWorkingHours.put(key, shiftList);
        }

        return consolidatedWorkingHours;
    }

    @Override
    public Map<Date, String> getTempValues(Long userId) {
        Map<Date, String> tempValues = new LinkedHashMap<>();
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createNativeQuery("select tg.date,tg.temperature from tempgraph as tg where tg.userId=:userId");
        query.setParameter("userId", userId);
        List<Object> objects = query.getResultList();
        for (Object o : objects) {
            Object[] ob = (Object[]) o;
            tempValues.put((Date) ob[0], ob[1].toString());
        }
        entityManager.close();
        return tempValues;
    }

    @Override
    public Map<Date, String> getPulseValues(Long userId) {
        Map<Date, String> pulseValues = new LinkedHashMap<>();
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createNativeQuery("select tg.date,tg.heartRate from pulsegraph as tg where tg.userId=:userId");
        query.setParameter("userId", userId);
        List<Object> objects = query.getResultList();
        for (Object o : objects) {
            Object[] ob = (Object[]) o;
            pulseValues.put((Date) ob[0], ob[1].toString());
        }
        entityManager.close();
        return pulseValues;
    }

    @Override
    public Map<Date, String> getRespValues(Long userId) {
        Map<Date, String> pulseValues = new LinkedHashMap<>();
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createNativeQuery("select tg.date,tg.respRate from respgraph as tg where tg.userId=:userId");
        query.setParameter("userId", userId);
        List<Object> objects = query.getResultList();
        for (Object o : objects) {
            Object[] ob = (Object[]) o;
            pulseValues.put((Date) ob[0], ob[1].toString());
        }
        entityManager.close();
        return pulseValues;
    }

    @Override
    public Map<Date, String> getBpValues(Long userId) {
        Map<Date, String> pulseValues = new LinkedHashMap<>();
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createNativeQuery("select tg.date,tg.bp from bpgraph as tg where tg.userId=:userId");
        query.setParameter("userId", userId);
        List<Object> objects = query.getResultList();
        for (Object o : objects) {
            Object[] ob = (Object[]) o;
            pulseValues.put((Date) ob[0], ob[1].toString());
        }
        entityManager.close();
        return pulseValues;
    }

    @Override
    public Map<Date, String> getSugarValues(Long userId) {
        Map<Date, String> pulseValues = new LinkedHashMap<>();
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createNativeQuery("select tg.date,tg.sugar from sugargraph as tg where tg.userId=:userId");
        query.setParameter("userId", userId);
        List<Object> objects = query.getResultList();
        for (Object o : objects) {
            Object[] ob = (Object[]) o;
            pulseValues.put((Date) ob[0], ob[1].toString());
        }
        entityManager.close();
        return pulseValues;
    }

    @Override
    public Map<Date, String> getBmiValues(Long userId) {
        Map<Date, String> pulseValues = new LinkedHashMap<>();
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createNativeQuery("select tg.date,tg.bmi from bmigraph as tg where tg.userId=:userId");
        query.setParameter("userId", userId);
        List<Object> objects = query.getResultList();
        for (Object o : objects) {
            Object[] ob = (Object[]) o;
            pulseValues.put((Date) ob[0], ob[1].toString());
        }
        entityManager.close();
        return pulseValues;
    }

    @Override
    public List<User> getDoctorsBySearch(String input) {
        List<User> users = new ArrayList<>();
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createNativeQuery("select uv.userId,uv.userName,uv.firstName,uv.lastName,uv.email,uv.mobilePhone from userview as uv" +
                " where uv.role='ROLE_DOCTOR' AND (uv.userId=:id or " +"uv.mobilePhone=:mobile)");
        query.setParameter("mobile", input);
        boolean isNumeric = isNumeric(input);
        if (isNumeric) {
            query.setParameter("id", Long.parseLong(input));
        } else {
            query.setParameter("id", 0L);
        }

        List<Object> objects = query.getResultList();

        for (Object o : objects) {
            Object[] ob = (Object[]) o;
            User user = new User(((BigInteger) ob[0]).longValue(), ob[1].toString(), ob[2].toString() + " " + ob[3].toString(), ob[4].toString(), ob[5].toString());
            users.add(user);
        }
        entityManager.close();
        return users;
    }


    @Override
    public List<User> getPatientsBySearch(String inputs) {
        List<User> users = new ArrayList<>();
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createNativeQuery("select uv.userId,uv.userName,uv.firstName,uv.lastName,uv.email,uv.mobilePhone from userview as uv" +
                " where uv.role='ROLE_PATIENT' AND (uv.userId=:input or uv.mobilePhone=:input or uv.userName=:input)");
        query.setParameter("input", inputs);
       /* boolean isNumeric = isNumeric(input);
        if (isNumeric) {
            query.setParameter("id", Long.parseLong(input));
        } else {
            query.setParameter("id", 0L);
        }*/

        List objects = query.getResultList();
        for (Object o : objects) {
            Object[] ob = (Object[]) o;
            User user = new User(((BigInteger) ob[0]).longValue(), ob[1].toString(), ob[2].toString() + " " + ob[3].toString(), ob[4].toString(), ob[5].toString());
            users.add(user);
        }
        entityManager.close();
        return users;
    }


    @Override
    public User getUsers(Long id) {
        User user;
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createNativeQuery("select uv.userId,uv.userName,uv.firstName,uv.lastName,uv.email,uv.mobilePhone from userview as uv" +
                " where uv.role='ROLE_PATIENT' AND uv.userId=:id");
        query.setParameter("id", id);

        Object[] ob = (Object[]) query.getSingleResult();
        user = new User(((BigInteger) ob[0]).longValue(), ob[1].toString(), ob[2].toString() + " " + ob[3].toString(), ob[4].toString(), ob[5].toString());
        entityManager.close();
        return user;
    }


    @Override
    public Map<Long, String> getDoctorsFromClinic(Long id) {
        Map<Long, String> users = new LinkedHashMap<>();
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createNativeQuery("select s.doctorId,s.firstName,s.lastName from search as s" +
                " where s.clinicId=:id");
        query.setParameter("id", id);
        List<Object> objects = query.getResultList();
        for (Object o : objects) {
            Object[] ob = (Object[]) o;
            users.put(((BigInteger) ob[0]).longValue(), ob[1].toString() + " " + ob[2].toString());
        }
        entityManager.close();
        return users;
    }


    public static boolean isNumeric(String str) {
        try {
            Long.parseLong(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    @Override
    public List<Object> searchByLocationInBangDb(String location) {
        List<Object> objects = new ArrayList<Object>();

        if (location != "") {
            EntityManager entityManager = em.createEntityManager();
            Query query = entityManager.createNativeQuery("select * from  bangaloredoctorDb as s where s.address LIKE :input or s.city like :input or s.location like :input");
            query.setParameter("input", location + "%");
            objects = query.getResultList();
            entityManager.close();
        }
        return objects;
    }

    @Override
    public List<Object> searchBySpecialityLocInBangDb(String speciality, String location) {
        List<Object> objects;
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createNativeQuery("select * from bangaloredoctordb as s where s.location LIKE :loc and s.Specialty like :input");
        query.setParameter("input", speciality+ "%");
        query.setParameter("loc", location + "%");
        objects = query.getResultList();
        entityManager.close();
        return objects;
    }

    @Override
    public List<Object> searchBySpecialityInBangDb(String speciality) {
        List<Object> objects;
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createNativeQuery("select * from bangaloredoctordb as s where  s.specialty like :input");
        query.setParameter("input", speciality+ "%");
        objects = query.getResultList();
        entityManager.close();
        return objects;
    }

    @Override
    public List<Object> searchByClinicNameInBangDb(String clinicName) {
        List<Object> objects;
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createNativeQuery("select * from bangaloredoctordb as s where  s.clinicName like :input");
        query.setParameter("input", "%" + clinicName + "%");
        objects = query.getResultList();
        entityManager.close();
        return objects;
    }

    @Override
    public List<Object> searchByDoctorNameInBangDb(String doctorName) {
        List<Object> objects;
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createNativeQuery("select * from bangaloredoctordb as s where  s.docName like :input");
        query.setParameter("input", "%" + doctorName + "%");
        objects = query.getResultList();
        entityManager.close();
        return objects;
    }

    public List<Object> searchByDoctorClinicNameAndLocInBangDb(String doctorName,String location) {
        List<Object> objects;
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createNativeQuery("select * from bangaloredoctordb as s where  (s.docName like :input or s.clinicName like :input) and s.location like :loc");
        query.setParameter("input", doctorName + "%");
        query.setParameter("loc", location + "%");
        objects = query.getResultList();
        entityManager.close();
        return objects;
    }


    public List<Object> searchByDoctorAndClinicNameInBangDb(String doctorName) {
        List<Object> objects;
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createNativeQuery("select * from bangaloredoctordb as s where  s.docName like :input or s.clinicName like :input");
        query.setParameter("input", doctorName + "%");
        objects = query.getResultList();
        entityManager.close();
        return objects;
    }


    public List<Object> searchByDoctorAndClinicName(String input) {
        List<Object> objects;
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createNativeQuery("select * from search as s where (s.docName LIKE :input OR s.clinicName like :input) AND s.activate=true");
        query.setParameter("input", input + "%");
        objects = query.getResultList();
        entityManager.close();
        return objects;
    }


    @Override
    public List<Speciality> getSpecialtyIdByName(String name) {
        List<Speciality> specialities;
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createQuery("from Speciality as s where s.name=:name");
        query.setParameter("name", name);
        specialities = query.getResultList();
        entityManager.close();
        return specialities;
    }
    public NewTimings checkNewDayWiseSetting(NewTimings newTimings) {
        NewTimings clinicTimeSlot = new NewTimings();
        if (newTimings != null) {
            if (newTimings.isMonStatus_1()) {
                if ((newTimings.getMonst_1() != null && !newTimings.getMonst_1().isEmpty()) && (newTimings.getMonet_1() != null && !newTimings.getMonet_1().isEmpty())) {
                    clinicTimeSlot.setMonst_1(newTimings.getMonst_1());
                    clinicTimeSlot.setMonet_1(newTimings.getMonet_1());
                    clinicTimeSlot.setMonStatus_1(newTimings.isMonStatus_1());
                }
            }
            if (newTimings.isMonStatus_2()) {
                if ((newTimings.getMonst_2() != null && !newTimings.getMonst_2().isEmpty()) && (newTimings.getMonet_2() != null && !newTimings.getMonet_2().isEmpty())) {
                    clinicTimeSlot.setMonst_2(newTimings.getMonst_2());
                    clinicTimeSlot.setMonet_2(newTimings.getMonet_2());
                    clinicTimeSlot.setMonStatus_2(newTimings.isMonStatus_2());
                }
            }
            if (newTimings.isTueStatus_1()) {
                if ((newTimings.getTuest_1() != null && !newTimings.getTuest_1().isEmpty()) && (newTimings.getTueet_1() != null && !newTimings.getTueet_1().isEmpty())) {
                    clinicTimeSlot.setTuest_1(newTimings.getTuest_1());
                    clinicTimeSlot.setTueet_1(newTimings.getTueet_1());
                    clinicTimeSlot.setTueStatus_1(newTimings.isTueStatus_1());
                }
            }
            if (newTimings.isTueStatus_2()) {
                if ((newTimings.getTuest_2() != null && !newTimings.getTuest_2().isEmpty()) && (newTimings.getTueet_2() != null && !newTimings.getTueet_2().isEmpty())) {
                    clinicTimeSlot.setTuest_2(newTimings.getTuest_2());
                    clinicTimeSlot.setTueet_2(newTimings.getTueet_2());
                    clinicTimeSlot.setTueStatus_2(newTimings.isTueStatus_2());
                }
            }
            if (newTimings.isWedStatus_1()) {
                if ((newTimings.getWedst_1() != null && !newTimings.getWedst_1().isEmpty()) && (newTimings.getWedet_1() != null && !newTimings.getWedet_1().isEmpty())) {
                    clinicTimeSlot.setWedst_1(newTimings.getWedst_1());
                    clinicTimeSlot.setWedet_1(newTimings.getWedet_1());
                    clinicTimeSlot.setWedStatus_1(newTimings.isWedStatus_1());
                }
            }
            if (newTimings.isWedStatus_2()) {
                if ((newTimings.getWedst_2() != null && !newTimings.getWedst_2().isEmpty()) && (newTimings.getWedet_2() != null && !newTimings.getWedet_2().isEmpty())) {
                    clinicTimeSlot.setWedst_2(newTimings.getWedst_2());
                    clinicTimeSlot.setWedet_2(newTimings.getWedet_2());
                    clinicTimeSlot.setWedStatus_2(newTimings.isWedStatus_2());
                }
            }
            if (newTimings.isThuStatus_1()) {
                if ((newTimings.getThust_1() != null && !newTimings.getThust_1().isEmpty()) && (newTimings.getThuet_1() != null && !newTimings.getThuet_1().isEmpty())) {
                    clinicTimeSlot.setThust_1(newTimings.getThust_1());
                    clinicTimeSlot.setThuet_1(newTimings.getThuet_1());
                    clinicTimeSlot.setThuStatus_1(newTimings.isThuStatus_1());
                }
            }
            if (newTimings.isThuStatus_2()) {
                if ((newTimings.getThust_2() != null && !newTimings.getThust_2().isEmpty()) && (newTimings.getThuet_2() != null && !newTimings.getThuet_2().isEmpty())) {
                    clinicTimeSlot.setThust_2(newTimings.getThust_2());
                    clinicTimeSlot.setThuet_2(newTimings.getThuet_2());
                    clinicTimeSlot.setThuStatus_2(newTimings.isThuStatus_2());
                }
            }
            if (newTimings.isFriStatus_1()) {
                if ((newTimings.getFrist_1() != null && !newTimings.getFrist_1().isEmpty()) && (newTimings.getFriet_1() != null && !newTimings.getFriet_1().isEmpty())) {
                    clinicTimeSlot.setFrist_1(newTimings.getFrist_1());
                    clinicTimeSlot.setFriet_1(newTimings.getFriet_1());
                    clinicTimeSlot.setFriStatus_1(newTimings.isFriStatus_1());
                }
            }
            if (newTimings.isFriStatus_2()) {
                if ((newTimings.getFrist_2() != null && !newTimings.getFrist_2().isEmpty()) && (newTimings.getFriet_2() != null && !newTimings.getFriet_2().isEmpty())) {
                    clinicTimeSlot.setFrist_2(newTimings.getFrist_2());
                    clinicTimeSlot.setFriet_2(newTimings.getFriet_2());
                    clinicTimeSlot.setFriStatus_2(newTimings.isFriStatus_2());
                }
            }
            if (newTimings.isSatStatus_1()) {
                if ((newTimings.getSatst_1() != null && !newTimings.getSatst_1().isEmpty()) && (newTimings.getSatet_1() != null && !newTimings.getSatet_1().isEmpty())) {
                    clinicTimeSlot.setSatst_1(newTimings.getSatst_1());
                    clinicTimeSlot.setSatet_1(newTimings.getSatet_1());
                    clinicTimeSlot.setSatStatus_1(newTimings.isSatStatus_1());
                }
            }
            if (newTimings.isSatStatus_2()) {
                if ((newTimings.getSatst_2() != null && !newTimings.getSatst_2().isEmpty()) && (newTimings.getSatet_2() != null && !newTimings.getSatet_2().isEmpty())) {
                    clinicTimeSlot.setSatst_2(newTimings.getSatst_2());
                    clinicTimeSlot.setSatet_2(newTimings.getSatet_2());
                    clinicTimeSlot.setSatStatus_2(newTimings.isSatStatus_2());
                }
            }
            if (newTimings.isSunStatus_1()) {
                if ((newTimings.getSunst_1() != null && !newTimings.getSunst_1().isEmpty()) && (newTimings.getSunet_1() != null && !newTimings.getSunet_1().isEmpty())) {
                    clinicTimeSlot.setSunst_1(newTimings.getSunst_1());
                    clinicTimeSlot.setSunet_1(newTimings.getSatet_1());
                    clinicTimeSlot.setSunStatus_1(newTimings.isSunStatus_1());
                }
            }
            if (newTimings.isSunStatus_2()) {
                if ((newTimings.getSunst_2() != null && !newTimings.getSunst_2().isEmpty()) && (newTimings.getSunet_2() != null && !newTimings.getSunet_2().isEmpty())) {
                    clinicTimeSlot.setSunst_2(newTimings.getSunst_2());
                    clinicTimeSlot.setSunet_2(newTimings.getSunet_2());
                    clinicTimeSlot.setSunStatus_2(newTimings.isSunStatus_2());
                }
            }
        }
        return clinicTimeSlot;
    }
    public Map<List<String>, List<String>> getNewWorkingHours(
            NewTimings newTimings) {

        Map<List<String>, List<String>> workingHoursMap = new HashMap<List<String>, List<String>>();

        // Map to store shifts for each day
        Map<String, List<String>> dailyShiftsMap = getNewDailyShifts(newTimings);

        for (Day dayEnum : Day.values()) {
            String day = dayEnum.toString();
            List<String> list = dailyShiftsMap.get(day);

            if (list != null && !list.isEmpty()) {
                List<String> daysList = workingHoursMap.get(list);
                if (daysList == null) {
                    daysList = new ArrayList<String>();
                    workingHoursMap.put(list, daysList);
                }
                daysList.add(day);
            }
        }

        return workingHoursMap;
    }
    public Map<String, List<String>> getNewDailyShifts(NewTimings newTimings) {
        Map<String, List<String>> dailyShiftsMap = new HashMap<String, List<String>>();

        boolean status_1 = false;
        boolean status_2 = false;
        String st_1 = "";
        String et_1 = "";
        String st_2 = "";
        String et_2 = "";

        for (Day day : Day.values()) {
            String dayString = day.toString();

            switch (dayString) {
                case "MON":
                    status_1 = newTimings.isMonStatus_1();
                    status_2 = newTimings.isMonStatus_2();
                    st_1 = newTimings.getMonst_1();
                    et_1 = newTimings.getMonet_1();
                    st_2 = newTimings.getMonst_2();
                    et_2 = newTimings.getMonet_2();
                    break;
                case "TUE":
                    status_1 = newTimings.isTueStatus_1();
                    status_2 = newTimings.isTueStatus_2();
                    st_1 = newTimings.getTuest_1();
                    et_1 = newTimings.getTueet_1();
                    st_2 = newTimings.getTuest_2();
                    et_2 = newTimings.getTueet_2();
                    break;
                case "WED":
                    status_1 = newTimings.isWedStatus_1();
                    status_2 = newTimings.isWedStatus_2();
                    st_1 = newTimings.getWedst_1();
                    et_1 = newTimings.getWedet_1();
                    st_2 = newTimings.getWedst_2();
                    et_2 = newTimings.getWedet_2();
                    break;
                case "THU":
                    status_1 = newTimings.isThuStatus_1();
                    status_2 = newTimings.isThuStatus_2();
                    st_1 = newTimings.getThust_1();
                    et_1 = newTimings.getThuet_1();
                    st_2 = newTimings.getThust_2();
                    et_2 = newTimings.getThuet_2();
                    break;
                case "FRI":
                    status_1 = newTimings.isFriStatus_1();
                    status_2 = newTimings.isFriStatus_2();
                    st_1 = newTimings.getFrist_1();
                    et_1 = newTimings.getFriet_1();
                    st_2 = newTimings.getFrist_2();
                    et_2 = newTimings.getFriet_2();
                    break;
                case "SAT":
                    status_1 = newTimings.isSatStatus_1();
                    status_2 = newTimings.isSatStatus_2();
                    st_1 = newTimings.getSatst_1();
                    et_1 = newTimings.getSatet_1();
                    st_2 = newTimings.getSatst_2();
                    et_2 = newTimings.getSatet_2();
                    break;
                case "SUN":
                    status_1 = newTimings.isSunStatus_1();
                    status_2 = newTimings.isSunStatus_2();
                    st_1 = newTimings.getSunst_1();
                    et_1 = newTimings.getSunet_1();
                    st_2 = newTimings.getSunst_2();
                    et_2 = newTimings.getSunet_2();
                    break;

            }

            List<String> timingsList = dailyShiftsMap.get(dayString);
            if (timingsList == null) {
                timingsList = new ArrayList<String>();
                dailyShiftsMap.put(dayString, timingsList);
            }

            if (status_1) {
                if (st_1 != null && !st_1.isEmpty() && et_1 != null
                        && !et_1.isEmpty()) {
                    if (!(st_1.equals(et_1))) {
                        timingsList.add(st_1 + "-" + et_1);
                    }

                }
            }
            if (status_2) {
                if (st_2 != null && !st_2.isEmpty() && et_2 != null
                        && !et_2.isEmpty()) {
                    if (!(st_2.equals(et_2))) {
                        timingsList.add(st_2 + "-" + et_2);
                    }

                }
            }
        }

        return dailyShiftsMap;
    }
    public User getUserDetails(Long userId) {
        Object obj[] = (Object[])getUserDetailsByUserIdInuserView(userId);
        User user = null;
        if (obj != null && obj.length > 0) {
            user = new User();
            user.setUserId(Long.parseLong(obj[0].toString()));
            user.setUserName(obj[1].toString());
            user.setRole(obj[2].toString());
            if(obj[3] != null && obj[4] != null){
                user.setFlname(obj[3].toString()+" "+obj[4].toString());
            }
            if(obj[5] != null) {
                user.setMobileNo(obj[5].toString());
            }
            if(obj[6] != null) {
                user.setEmail(obj[6].toString());
            }
            if (obj[7] != null){
                user.setUuid(obj[7].toString());
            }
            if(obj[8] != null){
                user.setName(obj[8].toString());
            }
            if(obj[9] != null){
                user.setImageThumbnailFileName(obj[9].toString());
            }
            if(user.getImageThumbnailFileName()!= null && !user.getImageThumbnailFileName().isEmpty()){
                user.setImageUrl(utilityServices.getMessage("User.profileImage.Url")+user.getUserId());
               /* user.setImageUrl(utilityServices.getMessage("Doctor.DefaultImage.Url"));*/
            }else{
                user.setImageUrl(utilityServices.getMessage("User.DefaultImage.Url"));
            }
        }
        return user;
    }

    public Search convertObjToSearchEnty(Long doctorId,Long clinicId) {
        Object obj[] = (Object[])getDoctorDetailBydoctorIdInuserView(doctorId,clinicId);
        Search search = null;
        if (obj != null && obj.length > 0) {
            search = new Search();

            search.setDoctorName("Dr. "+obj[10]+" "+obj[9]);
            search.setDoctorId(Long.parseLong(obj[0].toString()));
            search.setClinicId(Long.parseLong(obj[1].toString()));
            if(obj[2] != null){
                search.setSpecialityId(Long.parseLong(obj[2].toString()));
            }

            if(obj[4] != null){
                search.setAddress(obj[4].toString());
            }

            if(obj[3] != null){
                search.setLocation(obj[3].toString());
            }

            if(obj[6] != null){
                search.setCity(obj[6].toString());
            }
            if(obj[7] != null){
                search.setClinicName(obj[7].toString());
            }

            if(obj[11] != null){
                search.setConsultationFee(obj[11].toString());
            }
            if(obj[12] != null){
                search.setExperience(Byte.parseByte(obj[12].toString()));
            }
            if(obj[14] != null){
                search.setMobilePhone(obj[14].toString());
            }

            if(obj[15] != null){
                search.setHomePhone(obj[15].toString());
            }

            Long clinic = search.getClinicId();
            DoctorInfo doctorInfo = CommonServices.getOnlyDoctorInfo(doctorInfoRepository.findDoctor(doctorId));
            if (doctorInfo != null) {
                if (doctorInfo.getQualification() != null) {
                    search.setQualification(doctorInfo.getQualification());
                }
            }

            Search search1 = getRegularTimeSlots(doctorId, clinic);
            if (search1 != null) {
                search.setCdGeneralTime(search1.getCdGeneralTime());
            }
            Long specialityId = search.getSpecialityId();
            if (specialityId != null) {
                String specialityName = userRepository.getSpecialityNameById(specialityId);
                if (specialityName != null) {
                    search.setSpecialityName(specialityName);
                }
            }
            String url=userRepository.findThubnailFileName(doctorId);
            if(url!=null&&!url.isEmpty()){
                search.setDoctorImageUrl(utilityServices.getMessage("User.profileImage.Url")+doctorId);
            }else{
                search.setDoctorImageUrl(utilityServices.getMessage("Doctor.DefaultImage.Url"));
            }

            String clnUrl=userRepository.findThubnailFileName(clinic);
            if(clnUrl!=null && !clnUrl.isEmpty()){
                search.setClinicImageUrl(utilityServices.getMessage("User.profileImage.Url")+clinic);
            }else{
                search.setDoctorImageUrl(utilityServices.getMessage("Clinic.DefaultImage.Url"));
            }
        }
        return search;
    }

    @Override
    public Long getIdByCountryName(String name) {
        Long str;
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createQuery("select c.countryId from Country as c where c.countryName=:name");
        query.setParameter("name", name);
        str = Long.parseLong(query.getSingleResult().toString());
        entityManager.close();
        return str;
    }


    @Override
    public String getRecordSpecialityById(Long specialityId) {
        String str;
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createQuery("select s.name from RecordSpeciality as s where s.specialityId=:specialityId");
        query.setParameter("specialityId", specialityId);
        str = query.getSingleResult().toString();
        entityManager.close();
        return str;
    }

    @Override
    public String getUserLocation(Long patientId) {
        String location;
        String results ="";

        EntityManager em1 = em.createEntityManager();
        Object temp = em1.createNativeQuery("select uc.location from user as u , usercontactinfo as uc  where  u.userId =uc.userId and uc.userId=" + patientId).getSingleResult();

        if (temp == null) {
            results = "";
        }
        else {
            results = temp.toString();
        }
        em1.close();
        return results;
    }

    @Override
    public String getTypeOfProcedureById(Long id) {
        String str;
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createQuery("select s.procedureName from TypeOfPRocedure as s where s.id=:id");
        query.setParameter("id", id);
        str = query.getSingleResult().toString();
        entityManager.close();
        return str;
    }



    @Override
    public String getRoleByType(String type) {
        String role;
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createQuery("select r.name from Role as r where r.role=:type");
        query.setParameter("type", type);
        role = query.getSingleResult().toString();
        entityManager.close();
        return role;
    }

    @Override
    @Transactional
    public List<Object> getSecoundOpinionDoctors(String name) {
        List<Object> objects;
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createNativeQuery("select * from referraldoctors as d where d.name like :input");
        query.setParameter("input", name + "%");
        objects = query.getResultList();
        entityManager.close();
        return objects;
    }


    public List<City> getCity() {
        List<City> cities;
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createQuery("from City ", City.class);
        cities = query.getResultList();
        entityManager.close();
        return cities;
    }

    @Override
    public List<City> getCityLike(String input) {
        List<City> cityList;
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createQuery("from City as loc where loc.name like :name", City.class);
        query.setParameter("name", input + "%");
        cityList = query.getResultList();
        entityManager.close();
        return cityList;
    }

    @Override
    public List<City> getCitiesLikeBystateId(Long stateId,String input) {
        List<City> cityList;
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createQuery("from City as h where h.stateId=:stateId and h.name like :input");
        query.setParameter("stateId", stateId);
        query.setParameter("input", input + "%");
        cityList=query.getResultList();
        entityManager.close();
        return cityList;
    }

    @Override
    public List<City> getCitiesLikeBystateId(Long input) {
        List<City> cityList;
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createQuery("from City as h where h.stateId=:input");
        query.setParameter("input", input);
        cityList=query.getResultList();
        entityManager.close();
        return cityList;
    }


    @Override
    public List<Long> getHospitalCountries() {
        List<Long> countryIds;
        EntityManager entityManager = em.createEntityManager();
        countryIds = entityManager.createQuery("select distinct hc.countryId from HospitalCity as hc").getResultList();
        entityManager.close();
        return countryIds;
    }

    @Override
    public List<HospitalCity> getHospitalCities(Long countryId) {
        List<HospitalCity> hospitalCities;
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createQuery("from HospitalCity as hc where hc.countryId=:countryId");
        query.setParameter("countryId", countryId);
        hospitalCities=query.getResultList();
        entityManager.close();
        return hospitalCities;
    }

    @Override
    public List<HospitalCity> getHospitalCities() {
        List<HospitalCity> hospitalCities;
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createQuery("from HospitalCity");
        hospitalCities=query.getResultList();
        entityManager.close();
        return hospitalCities;
    }

    @Override
    public List<HospitalCity> getHospitalCitiesLike(String input) {
        List<HospitalCity> hospitalCities;
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createQuery("from HospitalCity as h where h.name like :input");
        query.setParameter("input", input + "%");
        hospitalCities=query.getResultList();
        entityManager.close();
        return hospitalCities;
    }

    @Override
    public List<HospitalCity> getHospitalCitiesLikeByCountryId(Long countryId,String input) {
        List<HospitalCity> hospitalCities;
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createQuery("from HospitalCity as h where h.countryId=:countryId and h.name like :input");
        query.setParameter("countryId", countryId);
        query.setParameter("input", input + "%");
        hospitalCities=query.getResultList();
        entityManager.close();
        return hospitalCities;
    }

    @Override
    public List<TypeOfPRocedure> getProcedureType(Long specialityId) {
        List<TypeOfPRocedure> hospitalCities;
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createQuery("from TypeOfPRocedure as top where top.recordSpeciality.specialityId=:specialityId");
        query.setParameter("specialityId", specialityId);
        hospitalCities=query.getResultList();
        entityManager.close();
        return hospitalCities;
    }

    @Override
    public String getProcedureTypeById(Long procedureTypeId) {
        String procedureType;
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createQuery("select  top.procedureName  from TypeOfPRocedure as top where top.id=:procedureTypeId");
        query.setParameter("procedureTypeId", procedureTypeId);
        procedureType=query.getSingleResult().toString();
        entityManager.close();
        return procedureType;
    }


    @Override
    public List<LabTests> getLabTestses() {
        List<LabTests> labTestses;
        EntityManager entityManager = em.createEntityManager();
        labTestses = entityManager.createQuery("from LabTests as lt group by lt.name").getResultList();
        entityManager.close();
        return labTestses;
    }

    @Override
    public List<User> getCSRUsers(String role, String state, String city, Long assignedTo) {
        List<User> users = new ArrayList<>();
        EntityManager entityManager = em.createEntityManager();
        String queryString = "select * from csrview as cv where cv.role=:role";
        if (StringUtils.isNotBlank(state) && StringUtils.isNotBlank(city)) {
            queryString = "select * from csrview as cv where cv.role=:role and cv.state=:state and cv.city=:city";
        }
        if (assignedTo != null && assignedTo != 0L) {
            queryString = "select * from csrview as cv where cv.assignedTo=:assignedTo";
        }
        Query query = entityManager.createNativeQuery(queryString);
        if (StringUtils.isNotBlank(role)) {
            query.setParameter("role", role);
        }
        if (StringUtils.isNotBlank(state) && StringUtils.isNotBlank(city)) {
            query.setParameter("state", state);
            query.setParameter("city", city);
        }
        if (assignedTo != null && assignedTo != 0L) {
            query.setParameter("assignedTo", assignedTo);
        }
        List<Object> objects = query.getResultList();
        if (objects != null && !objects.isEmpty()) {
            for (Object o : objects) {
                Object[] ob = (Object[]) o;
                String serviceType = "";
                String skype = "";
                Long assignedId = 0L;
                if (ob[8] != null) {
                    serviceType = ob[8].toString();
                }
                if (ob[9] != null) {
                    skype = ob[9].toString();
                }
                if (ob[11] != null) {
                    assignedId = ((BigInteger) ob[11]).longValue();
                }
                User user = new User(((BigInteger) ob[0]).longValue(), ob[1].toString(), ob[2].toString(), ob[3].toString(), ob[4].toString(), ob[5].toString(),
                        ob[6].toString(), ob[7].toString(), serviceType, skype, Boolean.parseBoolean(ob[10].toString()), assignedId);
                users.add(user);
            }
        }

        entityManager.close();
        return users;
    }

    @Override
    public User getCSRUser(Long userId) {
        EntityManager entityManager = em.createEntityManager();
        Query query = entityManager.createNativeQuery("select * from csrview as cv where cv.userId=:userId");
        query.setParameter("userId", userId);
        Object o = query.getSingleResult();
        if (o != null) {
            Object[] ob = (Object[]) o;
            String serviceType = "";
            String skype = "";
            Long assignedTo = 0L;
            if (ob[8] != null) {
                serviceType = ob[8].toString();
            }
            if (ob[9] != null) {
                skype = ob[9].toString();
            }
            if (ob[11] != null) {
                assignedTo = ((BigInteger) ob[11]).longValue();
            }
            entityManager.close();
            return new User(((BigInteger) ob[0]).longValue(), ob[1].toString(), ob[2].toString(), ob[3].toString(), ob[4].toString(), ob[5].toString(),
                    ob[6].toString(), ob[7].toString(), serviceType, skype, Boolean.parseBoolean(ob[10].toString()), assignedTo);
        }

        return null;
    }

    @Override
    public Map<Long, String> getCSRUsersByRole(String role, String state, String city) {
        Map<Long, String> result = new LinkedHashMap<>();
        EntityManager entityManager = em.createEntityManager();
        String queryString = "select cv.userId,cv.name from csrview as cv where cv.role=:role";
        if (StringUtils.isNotBlank(state) && StringUtils.isNotBlank(city)) {
            queryString = "select cv.userId,cv.name from csrview as cv where cv.role=:role and cv.state=:state and cv.city=:city";
        }
        Query query = entityManager.createNativeQuery(queryString);
        query.setParameter("role", role);
        if (StringUtils.isNotBlank(state) && StringUtils.isNotBlank(city)) {
            query.setParameter("state", state);
            query.setParameter("city", city);
        }
        List<Object> objects = query.getResultList();
        if (objects != null && !objects.isEmpty()) {
            for (Object o : objects) {
                Object[] ob = (Object[]) o;
                result.put(((BigInteger) ob[0]).longValue(), ob[1].toString());
            }
        }
        return result;
    }

}
