package org.mybatis.example.mapper;


import org.mybatis.example.pojo.User;

import java.util.List;

public interface UserMapper {

    public List<User> selectUserList();

}
