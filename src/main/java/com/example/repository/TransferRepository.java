package com.example.repository;

import com.example.model.Transfer;
import com.example.model.dto.CustomerDTO;
import com.example.model.dto.TransferDTO;
import com.example.model.dto.TransferViewDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {

    @Query(value = "SELECT NEW com.example.model.dto.TransferViewDTO (" +
            "transf.id, " +
            "transf.sender.id, " +
            "transf.sender.fullName, " +
            "transf.recipient.id, " +
            "transf.recipient.fullName, " +
            "transf.transferAmount, " +
            "transf.feesAmount, " +
            "transf.totalAmount," +
            "transf.createdAt" +
            ") " +
            "FROM Transfer AS transf "
    )
    List<TransferViewDTO> findAllTransferViewDTO();
}
