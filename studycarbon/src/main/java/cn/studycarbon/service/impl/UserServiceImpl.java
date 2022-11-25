package cn.studycarbon.service.impl;

import cn.studycarbon.domain.User;
import cn.studycarbon.repository.UserRepository;
import cn.studycarbon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService  {

    @Autowired
    private UserRepository userRepository;


    @Override
    @Transactional
    public User saveOrUpdateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User registerUser(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void removeUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.getReferenceById(id);
    }

    @Override
    public Page<User> listUsersByNameLike(String name, Pageable pageable) {
        name = "%"+name+"%";
        Page<User> users = userRepository.findByNameLike(name, pageable);
        return users;
    }
}
