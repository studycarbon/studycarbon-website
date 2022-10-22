package cn.studycarbon.dao;

import cn.studycarbon.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDao {
    // 根据用户id查询用户
    @Select("select * from user where id=#{id}")
    public User getUserById(int id);

    // 根据用户名查找用户
    @Select("select * from user where username=#{username}")
    public User getUserByUsername(String username);

    @Insert("insert into user (rolename, username, email, password, sex, loginstate, logintime, createtime) " +
            "VALUES (#{rolename}, #{username}, #{email}, #{password}, #{sex}, #{loginstate}, #{logintime}, #{createtime})")
    public int createUser(User user);

}
