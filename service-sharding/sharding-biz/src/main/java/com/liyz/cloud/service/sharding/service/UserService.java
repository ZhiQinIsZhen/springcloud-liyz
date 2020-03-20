package com.liyz.cloud.service.sharding.service;

import com.liyz.cloud.service.sharding.dao.UserMapper;
import com.liyz.cloud.service.sharding.model.UserDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/3/20 11:10
 */
@Slf4j
@Service
public class UserService {

    @Resource
    UserMapper userMapper;

    public Long addUser(UserDO user) {
        return userMapper.addUser(user);
    }

    public List<UserDO> list() {
        return userMapper.list();
    }

    public UserDO findById(Long id) {
        return userMapper.findById(id);
    }

    public UserDO findByName(String name) {
        return userMapper.findByName(name);
    }
}
