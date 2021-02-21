package com.cg.bank.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.bank.entities.Transactions;

public interface TransactionsDao extends JpaRepository<Transactions, String> {

}
