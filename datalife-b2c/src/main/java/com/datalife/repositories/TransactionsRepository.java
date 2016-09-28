package com.datalife.repositories;


import com.datalife.entities.Transaction;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by barath on 5/17/2016.
 */
public interface TransactionsRepository extends CrudRepository<Transaction,Long> {
}
