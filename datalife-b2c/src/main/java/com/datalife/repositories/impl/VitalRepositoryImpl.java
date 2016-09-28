package com.datalife.repositories.impl;

import com.datalife.entities.Vitals;
import com.datalife.repositories.VitalRepository;
import com.datalife.repositories.custom.VitalRepositoryExtension;
import com.datalife.services.CommonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by supriya gondi on 11/1/2014.
 */
@Service
public class VitalRepositoryImpl implements VitalRepositoryExtension {
    /**
     * Reference to vitalRepository
     */
    @Autowired
    VitalRepository vitalRepository;

    @Override
    public Map<String, Object> getVitalValues(Long id, boolean monitored) {
        Map<String, Object> response = new HashMap<String, Object>();
        Calendar c = Calendar.getInstance();
        Date now = c.getTime();
        c.add(Calendar.DAY_OF_YEAR, -30);
        Date before = c.getTime();
        PageRequest pageRequest = new PageRequest(0, 4);
        List<Vitals> vt = null;
        if (monitored == true) {
            vt = vitalRepository.getPreviousVitals(before, id, pageRequest);
        } else {
            vt = vitalRepository.getVitals(before, id, pageRequest);
        }
        if (vt != null && !vt.isEmpty()) {
            response.put("vitals", CommonServices.getOnlyVitals(vt));
            Map<Date, String> bpsvalues = new LinkedHashMap<Date, String>();
            Map<Date, String> bpdvalues = new LinkedHashMap<Date, String>();
            Map<Date, String> tempvalues = new LinkedHashMap<Date, String>();
            Map<Date, String> respvalues = new LinkedHashMap<Date, String>();
            Map<Date, String> heartratevalues = new LinkedHashMap<Date, String>();
            Map<Date, String> bmivalues = new LinkedHashMap<Date, String>();
            Map<Date, String> sugarvalues = new LinkedHashMap<Date, String>();
            for (Vitals s : vt) {
                Date d = s.getDate();
                if(s.getBp()!=null&&!s.getBp().isEmpty()){
                    String[] bps = s.getBp().split("/");
                    bpsvalues.put(d, bps[0]);
                    bpdvalues.put(d, bps[1]);
                } else{
                    bpsvalues.put(d, "");
                    bpdvalues.put(d, "");
                }
                tempvalues.put(d, s.getTemp());
                respvalues.put(d, s.getRespRate());
                heartratevalues.put(d, s.getHeartRate());
                bmivalues.put(d, s.getBmi());
                sugarvalues.put(d, s.getSugar());
            }
            response.put("bpsvalues", bpsvalues);
            response.put("bpdvalues", bpdvalues);
            response.put("tempvalues", tempvalues);
            response.put("respvalues", respvalues);
            response.put("heartratevalues", heartratevalues);
            response.put("bmivalues", bmivalues);
            response.put("sugarvalues", sugarvalues);

        }
        return response;
    }

    @Override
    public Vitals validateVitals(Vitals vitals) {
        vitals.setDate(new Date());
        if (vitals.getBmi() == null) {
            vitals.setBmi("-");
        }
        if (vitals.getBp() == null) {
            vitals.setBp("-");
        }
        if (vitals.getHeartRate() == null) {
            vitals.setHeartRate("-");
        }
        if (vitals.getRespRate() == null) {
            vitals.setRespRate("-");
        }
        if (vitals.getHeight() == null) {
            vitals.setHeight("-");
        }
        if (vitals.getSugar() == null) {
            vitals.setSugar("-");
        }
        if (vitals.getWeight() == null) {
            vitals.setWeight("-");
        }
        if (vitals.getTemp() == null) {
            vitals.setTemp("-");
        }
        return vitals;
    }
}
