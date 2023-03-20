package com.example.service.transfer;

import com.example.model.Transfer;
import com.example.model.dto.TransferViewDTO;
import com.example.service.IGeneralService;

import java.util.List;

public interface ITransferService extends IGeneralService<Transfer> {
    List<TransferViewDTO> findAllTransferViewDTO();
}
