package com.doraemons.mapper;

import com.doraemons.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Select("select * from user where username = #{text} or email = #{text}")
    User findUserByNameOrEmail(String text);

    @Select("SELECT full_name FROM user WHERE username = #{text} OR email = #{text}")
    String findFullNameByUsernameOrEmail(String text);

    @Insert("INSERT INTO user (username, email, password) VALUES (#{username}, #{email}, #{password})")
    int addUser(User user);

    @Update("UPDATE user SET password = #{password} WHERE id = #{id}")
    int updateUserPassword(@Param("id") long id, @Param("password") String password);

    @Delete("DELETE FROM user WHERE id = #{id}")
    int deleteUser(@Param("id") long id);

}
