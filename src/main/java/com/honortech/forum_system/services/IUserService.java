package com.honortech.forum_system.services;

import com.honortech.forum_system.model.User;

public interface IUserService {

    void createNormalUser (User user);

    /**
     *  根据⽤⼾⽤查询⽤⼾
     * @param username 用户名
     * @return 用户信息
     */
    User selectByUsername (String username);

    User login (String username, String password);

    User selectById (Long id);

    /**
     * 更新当前用户的发帖数
     * @param id 用户id
     */
    void addOneArticleCountById (Long id);
}
