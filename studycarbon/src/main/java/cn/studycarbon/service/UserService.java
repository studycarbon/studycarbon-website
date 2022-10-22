package cn.studycarbon.service;

import cn.studycarbon.domain.User;

public interface UserService {
    public User userLogin(User user);

    public User userReg(User user);
}
