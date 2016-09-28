package com.datalife.repositories;

import com.datalife.entities.LabCategories;
import com.datalife.entities.LabTests;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

/**
 * Created by Dipak on 1/27/2015.
 */
@Transactional
public interface LabTestRepository extends CrudRepository<LabTests,Long> {

    /**
     * This method is to get LabCategories by labCategoriesId
     * @param labCategoriesId
     * @return List<LabCategories>
     */
    @Query("select lt.labTestsId,lt.name from LabTests as lt where lt.labCategories.labCategoriesId = :labCategoriesId")
    public List<Map<Long,String>> getLabCategoriesById(@Param(value = "labCategoriesId") Long labCategoriesId);
}
