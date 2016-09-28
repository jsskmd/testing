package com.datalife.repositories.custom;

import com.datalife.entities.History;

import java.util.List;
import java.util.Map;

/**
 * Created by supriya gondi on 10/27/2014.
 *
 * This Custom repository handles History entity related Custom logics
 */
public interface HistoryRepositoryExtension {
    public Map<String,String> getHistoryData(String history);
}
