package com.liyz.cloud.service.sharding.remote;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liyz.cloud.common.base.util.CommonConverterUtil;
import com.liyz.cloud.common.model.bo.page.PageBaseBO;
import com.liyz.cloud.common.model.bo.sharding.UserBO;
import com.liyz.cloud.service.sharding.model.UserDO;
import com.liyz.cloud.service.sharding.service.UserService;
import groovy.util.logging.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/3/20 21:00
 */
@Slf4j
@Service
public class RemoteUserService {

    @Autowired
    UserService userService;

    public Long addUser(UserDO user) {
        return userService.addUser(user);
    }

    public List<UserBO> list() {
        return CommonConverterUtil.ListTransform(userService.list(), UserBO.class);
    }

    public UserBO findById(Long id) {
        return CommonConverterUtil.beanCopy(userService.findById(id), UserBO.class);
    }

    public UserBO findByName(String name) {
        return CommonConverterUtil.beanCopy(userService.findByName(name), UserBO.class);
    }

    public PageInfo<UserBO> page(PageBaseBO pageBaseBO) {
        PageHelper.startPage(pageBaseBO.getPageNum(), pageBaseBO.getPageSize());
        List<UserDO> doList = userService.list();
        PageInfo<UserDO> doPageInfo = new PageInfo<>(doList);
        PageInfo<UserBO> boPageInfo = CommonConverterUtil.PageTransform(doPageInfo, UserBO.class);
        return boPageInfo;
    }
}
