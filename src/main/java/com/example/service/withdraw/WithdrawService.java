package com.example.service.withdraw;

import com.example.model.Customer;
import com.example.model.Withdraw;
import com.example.model.dto.WithdrawViewDTO;
import com.example.repository.WithdrawRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class WithdrawService implements IWithdrawService{
    @Autowired
    private WithdrawRepository withdrawRepository;
    @Override
    public List<Withdraw> findAll() {
        return withdrawRepository.findAll();
    }

    @Override
    public List<WithdrawViewDTO> findAllWithdrawViewDTO() {
        return withdrawRepository.findAllWithdrawViewDTO();
    }

    @Override
    public Optional<Withdraw> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Boolean existById(Long id) {
        return null;
    }

    @Override
    public Withdraw save(Withdraw withdraw) {
        return withdrawRepository.save(withdraw);
    }

    @Override
    public void delete(Withdraw withdraw) {

    }

    @Override
    public Customer deleteById(Long id) {

        return null;
    }
}
