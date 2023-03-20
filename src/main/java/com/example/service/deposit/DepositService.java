package com.example.service.deposit;

import com.example.model.Customer;
import com.example.model.Deposit;
import com.example.model.dto.DepositViewDTO;
import com.example.repository.DepositRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DepositService implements IDepositService{
    @Autowired
    private DepositRepository depositRepository;
    @Override
    public List<Deposit> findAll() {
        return depositRepository.findAll();
    }

    @Override
    public List<DepositViewDTO> findAllDepositViewDTO() {
        return depositRepository.findAllDepositViewDTO();
    }

    @Override
    public Optional<Deposit> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Boolean existById(Long id) {
        return depositRepository.existsById(id);
    }

    @Override
    public Deposit save(Deposit deposit) {
        return depositRepository.save(deposit);
    }

    @Override
    public void delete(Deposit deposit) {

    }

    @Override
    public Customer deleteById(Long id) {
        return null;
    }
}
