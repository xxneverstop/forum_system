package com.honortech.forum_system.services.impl;

import com.honortech.forum_system.common.AppResult;
import com.honortech.forum_system.common.ResultCode;
import com.honortech.forum_system.dao.UserMapper;
import com.honortech.forum_system.exception.ApplicationException;
import com.honortech.forum_system.model.User;
import com.honortech.forum_system.services.IUserService;
import com.honortech.forum_system.utils.MD5Util;
import com.honortech.forum_system.utils.StringUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;


@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public void createNormalUser(User user) {
        if(user == null || StringUtil.isEmpty(user.getUsername()) || StringUtil.isEmpty(user.getNickname())
        || StringUtil.isEmpty(user.getPassword())
        || StringUtil.isEmpty(user.getSalt()) ) {
            log.warn(ResultCode.FAILED_PARAMS_VALIDATE.toString());
            throw new ApplicationException(AppResult.fail(ResultCode.FAILED_PARAMS_VALIDATE));
        }

        User existingUser = userMapper.selectByUsername(user.getUsername());

        // 用户已存在
        if(existingUser != null) {
            log.info("The username already exists.");
            throw new ApplicationException(AppResult.fail(ResultCode.FAILED_USER_EXISTS));
        }

        // 新增用户，设置默认值
        user.setGender((byte) 2);
        // 填充默认值
        user.setIsAdmin((byte) 0);
        user.setAvatarUrl(null);
        user.setArticleCount(0);
        user.setState((byte) 0);
        user.setDeleteState((byte) 0);
        Date date = new Date();
        user.setCreateTime(date);
        user.setUpdateTime(date);

        // 写入数据库
        int row =  userMapper.insertSelective(user);
        if (row != 1) {
            log.info(ResultCode.FAILED_CREATE.toString() + "username = " + user.getUsername());
            throw new ApplicationException(AppResult.fail(ResultCode.FAILED_CREATE));
        }

        log.info("Successfully created new user, username = " + user.getUsername());
    }

    @Override
    public User selectByUsername(String username) {
        if(username == null || StringUtil.isEmpty(username)) {
            log.warn(ResultCode.FAILED_PARAMS_VALIDATE.toString());
            throw new ApplicationException(AppResult.fail(ResultCode.FAILED_PARAMS_VALIDATE));
        }
        return userMapper.selectByUsername(username);
    }

    @Override
    public User login(String username, String password) {
        if(username == null || StringUtil.isEmpty(username) || StringUtil.isEmpty(password)) {
            log.warn(ResultCode.FAILED_LOGIN.toString());
            throw new ApplicationException(AppResult.fail(ResultCode.FAILED_LOGIN));
        }

        User user = userMapper.selectByUsername(username);
        if(user == null) {
            log.warn(ResultCode.FAILED_LOGIN.toString());
            throw new ApplicationException(AppResult.fail(ResultCode.FAILED_LOGIN));
        }

        String encryptPassword = MD5Util.md5(password,  user.getSalt());
        if(!user.getPassword().equals(encryptPassword)) {
            log.warn(ResultCode.FAILED_LOGIN.toString());
            throw new ApplicationException(AppResult.fail(ResultCode.FAILED_LOGIN));
        }

        log.info("Successfully login username = " + user.getUsername());

        return user;
    }

    @Override
    public User selectById(Long id) {
        if(id == null) {
            log.warn(ResultCode.FAILED_PARAMS_VALIDATE.toString());
            throw new ApplicationException(AppResult.fail(ResultCode.FAILED_PARAMS_VALIDATE));
        }
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public void addOneArticleCountById(Long id) {
        if (id == null ||  id <= 0) {
            log.warn(ResultCode.FAILED_BOARD_ARTICLE_COUNT.toString());
            throw new ApplicationException(AppResult.fail((ResultCode.FAILED_BOARD_ARTICLE_COUNT)));
        }

        User user = userMapper.selectByPrimaryKey(id);
        if (user == null) {
            log.warn(ResultCode.ERROR_IS_NULL.toString());
            throw new ApplicationException(AppResult.fail((ResultCode.ERROR_IS_NULL)));
        }

        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setArticleCount(user.getArticleCount()+1);

        // 更新数据库
        int row = userMapper.updateByPrimaryKeySelective(updateUser);
        if (row != 1) {
            log.warn(ResultCode.FAILED.toString() + ", will affect more than 1 row");
            throw new ApplicationException(AppResult.fail(ResultCode.FAILED));
        }
    }
}
