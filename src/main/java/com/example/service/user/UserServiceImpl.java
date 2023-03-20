package com.example.service.user;

import com.example.model.Customer;
import com.example.model.User;
import com.example.model.UserPrinciple;
import com.example.model.dto.UserDTO;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements IUserService {

//    @Autowired
//    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> findAll() {
        return null;
//        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return null;
//        return userRepository.findById(id);
    }

    @Override
    public Boolean existById(Long id) {
        return null;
    }

    @Override
    public User getByUsername(String username) {
        return null;
//        return userRepository.getByUsername(username);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return null;
//        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<UserDTO> findUserDTOByUsername(String username) {
        return null;
//        return userRepository.findUserDTOByUsername(username);
    }

    @Override
    public Boolean existsByUsername(String username) {
        return null;
//        return userRepository.existsByUsername(username);
    }

    @Override
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return null;
//        return userRepository.save(user);
    }

    @Override
    public void delete(User user) {

    }

    @Override
    public Customer deleteById(Long id) {

        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<User> userOptional = userRepository.findByUsername(username);
//        if (!userOptional.isPresent()) {
//            throw new UsernameNotFoundException(username);
//        }
//        return UserPrinciple.build(userOptional.get());

        return null;
    }

}
