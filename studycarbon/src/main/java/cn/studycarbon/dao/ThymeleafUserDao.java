package cn.studycarbon.dao;


import cn.studycarbon.domain.ThymeleafUser;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ThymeleafUserDao {
    // 用于用户递增id
    private static Long counter = 0L;
    // 用于存储用户信息
    private static Map<Long, ThymeleafUser> thymeleafMap = new HashMap<>();

    public ThymeleafUser saveOrUpdateUser(ThymeleafUser user) {
        Long id = user.getId();
        if (id == null) {
            id = counter;
            user.setId(counter);
            counter++;
        }
        thymeleafMap.put(id, user);
        return user;
    }

    public void deleteUser(Long id) {
        thymeleafMap.remove(id);
    }

    public ThymeleafUser getUserById(Long id) {
        return thymeleafMap.get(id);
    }

    public List<ThymeleafUser> listUser() {
        return new ArrayList<ThymeleafUser>(thymeleafMap.values());
    }
}
