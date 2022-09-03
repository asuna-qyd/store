package com.asuna.store.service;

import com.asuna.store.entity.User;

/**
 * 用户模块业务层接口
 */
public interface IUserService {

    /**
     * 用户注册方法
     * @param user 用户的数据对象
     */
    void reg(User user);

    /**
     * 用户登录方法
     * @param user 用户的数据对象
     * @return 当前匹配的用户数据，如果没有则返回null
     */
    User login(User user);

    /**
     * 用户修改密码方法
     * @param uid 用户的id
     * @param username 用户名
     * @param oldPassword 原密码
     * @param newPassword 新密码
     */
    void changePassword(Integer uid,
                        String username,
                        String oldPassword,
                        String newPassword);

    /**
     * 根据用户的uid查询用户数据
     * @param uid 用户uid
     * @return 用户数据
     */
    User getByUid(Integer uid);

    /**
     * uid通过控制层在session中获取然后传递给业务层,并在业务层封装到User对象中
     * */
    void changeInfo(Integer uid,User user);

}
