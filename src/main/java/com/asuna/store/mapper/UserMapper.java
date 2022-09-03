package com.asuna.store.mapper;

import com.asuna.store.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;


/**
 * 用户模块的持久层接口
 */
@Repository
//@Mapper
public interface UserMapper {

    /**
     * 插入注册的用户数据
     * @param user 用户的数据
     * @return 受影响的行数(增、删、改，都受影响的行数作为返回值，可以根据返回值来判断SQL是否执行成功)
     */
    Integer insertUser(User user);

    /**
     * 根据用户名来查询用户的数据
     * @param username 用户名
     * @return 如果找到对应的用户则返回这个用户的数据，如果没有找到则返回Null值
     */
    User findByUsername(String username);

    /**
     * 根据用户的Uid来修改用户的登录密码
     * @param uid 用户的id
     * @param password 用户的密码
     * @param modifiedUser 当前修改的用户
     * @param modifiedTime 当前修改的时间
     * @return 受影响的行数
     */
    Integer updatePasswordByUid(Integer uid,
                                String password,
                                String modifiedUser,
                                Date modifiedTime);

    /**
     * 根据用户的id来查询用户的数据
     * @param uid 用户的id
     * @return 如果找到则返回用户的对象，反之返回null值
     */
    User findByUid(Integer uid);

    /**
     * 更新用户的数据
     * @param user 用户的数据
     * @return 受影响的行数
     */
    Integer updateInfoByUid(User user);
}
