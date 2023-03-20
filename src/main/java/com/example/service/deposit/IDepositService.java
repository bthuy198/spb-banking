package com.example.service.deposit;

import com.example.model.Deposit;
import com.example.model.dto.DepositViewDTO;
import com.example.service.IGeneralService;

import java.util.List;

public interface IDepositService extends IGeneralService<Deposit> {
    List<DepositViewDTO> findAllDepositViewDTO();
}
