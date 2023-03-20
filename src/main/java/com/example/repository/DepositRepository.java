package com.example.repository;

import com.example.model.Deposit;
import com.example.model.dto.DepositViewDTO;
import com.example.model.dto.TransferViewDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DepositRepository extends JpaRepository<Deposit, Long> {
    @Query(value = "SELECT NEW com.example.model.dto.DepositViewDTO (" +
            "dep.id, " +
            "dep.customer.id, " +
            "dep.customer.fullName, " +
            "dep.createdAt," +
            "dep.transactionAmount" +
            ") " +
            "FROM Deposit AS dep "
    )
    List<DepositViewDTO> findAllDepositViewDTO();
}
