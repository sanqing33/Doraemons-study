package com.doraemons.mapper;

import com.doraemons.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("select * from user where username = #{text} or email = #{text}")
    User findUserByNameOrEmail(String text);

}
