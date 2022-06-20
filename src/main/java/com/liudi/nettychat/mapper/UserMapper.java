package com.liudi.nettychat.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liudi.nettychat.entity.User;
import org.apache.ibatis.annotations.Mapper;

public interface UserMapper extends BaseMapper<User> {

    User selectByPrimaryKey(String id);
}