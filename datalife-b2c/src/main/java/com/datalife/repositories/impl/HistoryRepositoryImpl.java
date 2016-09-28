package com.datalife.repositories.impl;

import com.datalife.entities.History;
import com.datalife.repositories.custom.HistoryRepositoryExtension;
import org.springframework.stereotype.Service;

import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by supriya gondi on 10/27/2014.
 */
@Service
public class HistoryRepositoryImpl implements HistoryRepositoryExtension {
    @Override
    public Map<String,String> getHistoryData(String history){
        Map<String,String> result=new LinkedHashMap<>();
        if(history!=null&&!history.isEmpty()){
            String[] main=history.split(";");
            for(int i=0;i<main.length;i++){
                String[] sub=main[i].split("\\)_");
                result.put(sub[1],sub[0].replace("(","").replace(")",""));
            }
        }
        return result;
    }
}
