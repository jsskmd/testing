package com.datalife.repositories;

import com.datalife.entities.ProviderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Supriya on 1/10/2016.
 */
@Transactional
public interface ProviderDetailRepository extends JpaRepository<ProviderDetails, Long> {

    @Query("FROM ProviderDetails as pd WHERE pd.role =:role")
    public List<ProviderDetails> fetchProvidersByRole(@Param(value = "role") String role);


   /* @Modifying
    @Query("update ProviderDetails as pd set pd.verified=:verified where pd.id=:id")
    public void updateProviderVerified(@Param(value = "id") Long id, @Param(value = "verified") boolean verified);*/

   /* @Query("FROM ProviderDetails as pd WHERE pd.state=:state and pd.city=:city and pd.type=:type and pd.verified=:verified")
    public List<ProviderDetails> getProviderDetailsByAll(@Param(value = "state") String state, @Param(value = "city") String city, @Param(value = "type") String type, @Param(value = "verified") boolean verified);
*/
    /*@Query("FROM ProviderDetails as pd WHERE pd.state=:state and pd.type=:type and pd.verified=:verified")
    public List<ProviderDetails> getProviderDetailsByState(@Param(value = "state") String state, @Param(value = "type") String type, @Param(value = "verified") boolean verified);
*/
   /* @Query("FROM ProviderDetails as pd WHERE pd.type=:type and pd.verified=:verified")
    public List<ProviderDetails> getProviderDetailsByType(@Param(value = "type") String type, @Param(value = "verified") boolean verified);
*/
   /* @Query("FROM ProviderDetails as pd WHERE pd.state=:state and pd.city=:city and pd.type=:type")
    public List<ProviderDetails> getvProviderDetailsByAll(@Param(value = "state") String state, @Param(value = "city") String city, @Param(value = "type") String type);

    @Query("FROM ProviderDetails as pd WHERE pd.state=:state and pd.type=:type")
    public List<ProviderDetails> getvProviderDetailsByState(@Param(value = "state") String state, @Param(value = "type") String type);

    @Query("FROM ProviderDetails as pd WHERE pd.type=:type")
    public List<ProviderDetails> getvProviderDetailsByType(@Param(value = "type") String type);

    @Query("FROM ProviderDetails as pd WHERE pd.licNo=:licNo")
    public List<ProviderDetails> findByLicNo(@Param(value = "licNo") String licNo);*/

}
