package cn.studycarbon.service.impl;

import cn.studycarbon.dao.UserDao;
import cn.studycarbon.domain.User;
import cn.studycarbon.service.UserService;
import org.apache.juli.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    // 用户服务
    public static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserDao userDao;

    @Override
    public User userLogin(User user) {
        if (user == null) {
            logger.error("user is null.please check it.");
            return null;
        }

        User loginUser = userDao.getUserByUsername(user.getUsername());
        if (loginUser == null) {
            logger.error("can't find username"+user.getUsername());
            return null;
        }

        if (!loginUser.getPassword().equals(user.getPassword())) {
            logger.error("the username:"+user.getUsername()+" password is incorrect.");
            return null;
        }

        return  loginUser;
    }

    @Override
    public User userReg(User user) {
        if (user == null) {
            logger.error("userReg user error:user is empty.");
            return null;
        }

        User dataUser = userDao.getUserByUsername(user.getUsername());
        if (dataUser != null) {
            logger.warn("username repeat.");
            return dataUser;
        }

        int rows = userDao.createUser(user);
        if (rows == 0) {
            logger.warn("userReg user error:insert row data failed.");
            return null;
        }
        logger.info("userReg user suc:insert row data suc.");

        return  user;
    }
}
