package cn.studycarbon.service;

import cn.studycarbon.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;


// 服务接口
public interface UserService {

    User saveOrUpdateUser(User user);

    User registerUser(User user);

    void removeUser(Long id);

    User getUserById(Long id);

    User getUserByUsername(String username);

    Page<User> listUsersByNameLike(String name, Pageable pageable);

    List<User> listUsersByUsernames(Collection<String> usernameList);

}
