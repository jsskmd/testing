package com.datalife.repositories;

import com.datalife.entities.MiniEncounter;
import com.datalife.repositories.custom.MiniEncounterRepositoryExtension;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by supriya gondi on 11/7/2014.
 *
 * This repository handles MiniEncounter entity related CRUD operations
 */
@Transactional
public interface MiniEncounterRepository extends CrudRepository<MiniEncounter, Long>, MiniEncounterRepositoryExtension {

    /**
     * This method is to get List of ICD based on input string
     * @param input
     * @return List of ICD
     */
    @Query("select ic.name from IcdCode as ic where ic.name like :input")
    public List<String> getICDNames(@Param(value = "input") String input);
}
