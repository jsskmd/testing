package com.datalife.repositories;

import com.datalife.entities.Bills;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by Supriya on 8/29/2015.
 */
public interface BillRepository extends CrudRepository<Bills,Long> {

    @Query("from Bills as b where b.user.userId=:userId order by b.date desc ")
    public List<Bills> getBillsById(@Param(value = "userId") Long userId, Pageable pageable);


    @Query("from Bills as b where b.user.userId=:userId order by b.date desc ")
    public List<Bills> getAllBillsById(@Param(value = "userId") Long userId);

    @Query("select sum(amount) from Bills as b where b.user.userId=:userId ")
    public Double getTotalAmountById(@Param(value = "userId") Long userId);

      @Query("select sum(amount),count(b.id) from Bills as b where b.user.userId=:userId ")
    public String getTotalById(@Param(value = "userId") Long userId);

    @Query("select sum(amount) FROM Bills WHERE date>=:from and date<=:to and user.userId =:userId")
    public Double getTotalBetweenDates(@Param(value = "from") Date from, @Param(value = "to") Date to, @Param(value = "userId") Long userId);
}
