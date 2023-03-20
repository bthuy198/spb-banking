package com.example.service.withdraw;

import com.example.model.Withdraw;
import com.example.model.dto.WithdrawViewDTO;
import com.example.service.IGeneralService;

import java.util.List;

public interface IWithdrawService extends IGeneralService<Withdraw> {
    List<WithdrawViewDTO> findAllWithdrawViewDTO();
}
