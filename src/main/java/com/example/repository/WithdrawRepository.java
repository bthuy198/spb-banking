package com.example.repository;

import com.example.model.Withdraw;
import com.example.model.dto.WithdrawViewDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WithdrawRepository extends JpaRepository<Withdraw, Long> {
    @Query(value = "SELECT NEW com.example.model.dto.WithdrawViewDTO (" +
            "wd.id, " +
            "wd.customer.id, " +
            "wd.customer.fullName, " +
            "wd.createdAt," +
            "wd.transactionAmount" +
            ") " +
            "FROM Withdraw AS wd "
    )
    List<WithdrawViewDTO> findAllWithdrawViewDTO();
}
